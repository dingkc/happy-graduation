package com.bttc.HappyGraduation.scheduler.transfer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 15:46 2019/3/20.
 */
@Component
public class DataSourceHolder implements ApplicationContextAware,DataTransferSourceBuilder {

    // Spring应用上下文环境
    private ApplicationContext applicationContext;

    private Integer initState = 0;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private Map<String,DataSource> dataSources = new HashMap<>();

    public synchronized DataSource getDataSource(String dataSource){
        if(initState==0){
            this.initDataSource();
        }
        return this.dataSources.get(dataSource);
    }

    private void initDataSource(){
        Map<String, DataTransferSourceBuilder> beans = applicationContext.getBeansOfType(DataTransferSourceBuilder.class);
        for(Map.Entry<String,DataTransferSourceBuilder> entry:beans.entrySet()){
            entry.getValue().addDataSource(this.dataSources);
        }
        this.initState=1;
    }

    @Override
    public void addDataSource(Map<String, DataSource> datasource) {
        Map<String, DataSource> beans = applicationContext.getBeansOfType(DataSource.class);
        datasource.putAll(beans);
    }
}
