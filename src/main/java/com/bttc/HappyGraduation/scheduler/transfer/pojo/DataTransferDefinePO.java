package com.bttc.HappyGraduation.scheduler.transfer.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 16:07 2019/3/19.
 */
@Entity
@Table(name = "DATA_TRANSFER_DEFINE")
public class DataTransferDefinePO implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private Integer id;
    private String srcConnectionCode;
    private String srcTableName;
    private String dbType;
    private String selectSql;
    private String srcAfterSql;
    private String desConnectionCode;
    private String desTableName;
    private String desBeforeSql;
    private String desAfterSql;
    private String cron;
    private String remark;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrcConnectionCode() {
        return srcConnectionCode;
    }

    public void setSrcConnectionCode(String srcConnectionCode) {
        this.srcConnectionCode = srcConnectionCode;
    }

    public String getSrcTableName() {
        return srcTableName;
    }

    public void setSrcTableName(String srcTableName) {
        this.srcTableName = srcTableName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public String getSrcAfterSql() {
        return srcAfterSql;
    }

    public void setSrcAfterSql(String srcAfterSql) {
        this.srcAfterSql = srcAfterSql;
    }

    public String getDesConnectionCode() {
        return desConnectionCode;
    }

    public void setDesConnectionCode(String desConnectionCode) {
        this.desConnectionCode = desConnectionCode;
    }

    public String getDesTableName() {
        return desTableName;
    }

    public void setDesTableName(String desTableName) {
        this.desTableName = desTableName;
    }

    public String getDesBeforeSql() {
        return desBeforeSql;
    }

    public void setDesBeforeSql(String desBeforeSql) {
        this.desBeforeSql = desBeforeSql;
    }

    public String getDesAfterSql() {
        return desAfterSql;
    }

    public void setDesAfterSql(String desAfterSql) {
        this.desAfterSql = desAfterSql;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
