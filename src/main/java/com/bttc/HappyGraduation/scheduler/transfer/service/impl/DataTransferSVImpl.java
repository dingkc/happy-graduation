package com.bttc.HappyGraduation.scheduler.transfer.service.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.bttc.HappyGraduation.scheduler.transfer.DataSourceHolder;
import com.bttc.HappyGraduation.scheduler.transfer.pojo.DataTransferDefinePO;
import com.bttc.HappyGraduation.scheduler.transfer.service.interfaces.IDataTransferSV;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 16:16 2019/3/20.
 */
@Service
public class DataTransferSVImpl implements IDataTransferSV {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTransferSVImpl.class);

    @Autowired
    private DataSourceHolder dataSourceHolder;

    @Override
    public void dataTransfer(DataTransferDefinePO dataTransferDefinePO) throws Exception {
        DataSource srcDataSource = dataSourceHolder.getDataSource(dataTransferDefinePO.getSrcConnectionCode());
        DataSource desDataSource = dataSourceHolder.getDataSource(dataTransferDefinePO.getDesConnectionCode());
        String selectSql = dataTransferDefinePO.getSelectSql();
        String afterSelectSql = dataTransferDefinePO.getSrcAfterSql();
        String beforeInsertSql = dataTransferDefinePO.getDesBeforeSql();
        String afterInsertSql = dataTransferDefinePO.getDesAfterSql();
        // 同步字段
        Set<String> fields = getFileds(selectSql, dataTransferDefinePO.getDbType());
        if (CollectionUtils.isEmpty(fields)) {
            throw new Exception(" 查询sql：" + selectSql + " 不存在查询字段，无法同步");
        }
        String insertSql = this.parseInsertSql(fields,dataTransferDefinePO.getDesTableName());
        ResultSet rs = null;
        try (Connection srcConn = srcDataSource.getConnection();
             Connection desConn = desDataSource.getConnection();
             PreparedStatement srcPreparedStatement = srcConn.prepareStatement(selectSql);
             PreparedStatement afterSrcPreparedStatement = StringUtils.isNotBlank(afterSelectSql)?srcConn.prepareStatement(afterSelectSql):null;
             PreparedStatement beforeDesPreparedStatement = StringUtils.isNotBlank(beforeInsertSql)?desConn.prepareStatement(beforeInsertSql):null;
             PreparedStatement desPreparedStatement = desConn.prepareStatement(insertSql);
             PreparedStatement afterPreparedStatement = StringUtils.isNotBlank(afterInsertSql)?desConn.prepareStatement(afterInsertSql):null;
        ) {
            srcConn.setAutoCommit(false);
            desConn.setAutoCommit(false);
            rs = srcPreparedStatement.executeQuery();
            if(afterSrcPreparedStatement!=null){
                afterSrcPreparedStatement.execute();
            }
            if(beforeDesPreparedStatement!=null){
                beforeDesPreparedStatement.execute();
            }

            while (rs.next()){
                int i =0 ;
                for(String s:fields){
                    desPreparedStatement.setObject(i+1,rs.getObject(s));
                    i++;
                }
                desPreparedStatement.addBatch();
            }
            desPreparedStatement.executeBatch();
            if(afterPreparedStatement!=null){
                afterPreparedStatement.execute();
            }

            desConn.commit();
            desPreparedStatement.clearBatch();

        } catch (Exception e) {
            LOGGER.error("插入失敗"+e);
            throw e;
        }
        finally {
            rs.close();
        }
    }

    /**
     * 获取sql中的字段
     *
     * @param sql
     * @param dbType
     * @return
     */
    public Set<String> getFileds(String sql, String dbType) {

        Set<String> fields = new LinkedHashSet<String>();// 存放字段
        try {
            // 解析sql
            SQLSelectStatement statment = getSqlStatement(sql, dbType);

            if (statment != null) {
                // 获取所有的字段
                List<SQLSelectItem> columns = statment.getSelect().getQueryBlock().getSelectList();

                if (CollectionUtils.isNotEmpty(columns)) {

                    for (SQLSelectItem column : columns) {
                        if (column != null) {
                            fields.add(column.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("【" + sql + "】语法有误！getFileds执行失败！" + e.getMessage());
            throw e;
        }
        return fields;
    }

    /**
     * 把sql解析成对象
     *
     * @param sql
     * @param dbType
     * @return
     */
    private SQLSelectStatement getSqlStatement(String sql, String dbType) {

        SchemaStatVisitor schemaStatVisitor;
        SQLSelectStatement sqlStatement;
        try {
            schemaStatVisitor = getInstance(dbType);
            List<SQLStatement> stmtList;
            stmtList = SQLUtils.parseStatements(sql, dbType);
            sqlStatement = (SQLSelectStatement) stmtList.get(0);
            sqlStatement.accept(schemaStatVisitor);
        } catch (Exception e) {
            LOGGER.error("【" + sql + "】语法有误！" + e.getMessage());
            throw e;
        }

        return sqlStatement;

    }

    public String parseInsertSql(Set<String> fields, String tableName) {

        StringBuilder result = new StringBuilder();

        if (StringUtils.isNoneBlank(tableName) && CollectionUtils.isNotEmpty(fields)) {

            StringBuilder resultTablePart = new StringBuilder();
            StringBuilder resultValuePart = new StringBuilder();
            int num = fields.size();
            for (String field : fields) {
                if (num == 1) {
                    resultTablePart.append(field);
                    resultValuePart.append("?");
                } else {
                    resultTablePart.append(field).append(",");
                    resultValuePart.append("?,");
                }
                num--;
            }
            result.append("INSERT INTO ").append(tableName).append(" (").append(resultTablePart).append(")")
                    .append(" values (").append(resultValuePart).append(")");

        }

        return result.toString();
    }

    public SchemaStatVisitor getInstance(String dbType) {

        if (JdbcConstants.ORACLE.equalsIgnoreCase(dbType)) {
            return new OracleSchemaStatVisitor();
        }
        if (JdbcConstants.MYSQL.equalsIgnoreCase(dbType)) {
            return new MySqlSchemaStatVisitor();
        }
        return null;
    }

}
