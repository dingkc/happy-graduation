package com.bttc.HappyGraduation.scheduler;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by jiajt on 2018/10/24.
 */
@Configuration
public class SchedulerConfiguration {
    @Autowired
    private OsrdcSchedulerJobFactory osrdcSchedulerJobFactory;

    /**
     * 当前系统环境
     */
    @Autowired
    private Environment environment;

    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException, SchedulerException {
        if(StringUtils.isBlank(environment.getProperty(JobConstants.CIRCLE_SCAN_JOB_PROPERTY))&&
                StringUtils.isBlank(environment.getProperty(JobConstants.SINGLE_SCAN_JOB_PROPERTY))&&
                    !JobConstants.DATA_TRANSFER_OPEN_FLAG.equals(environment.getProperty(JobConstants.DATA_TRANSFER))){
            return new SchedulerFactoryBean();
        }
        //获取配置属性
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        String[] arr = environment.getActiveProfiles();
        ClassPathResource[] classPathResources = new ClassPathResource[arr.length+1];
        classPathResources[0] = new ClassPathResource("quartz.properties");
        for(int i=0;i<arr.length;i++){
            String path = "quartz-"+arr[i]+".properties";
            classPathResources[i+1] = new ClassPathResource(path);
        }
        propertiesFactoryBean.setLocations(classPathResources);
        propertiesFactoryBean.setIgnoreResourceNotFound(true);
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        //创建SchedulerFactoryBean
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        Properties pro = propertiesFactoryBean.getObject();
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setQuartzProperties(pro);
        factory.setJobFactory(osrdcSchedulerJobFactory);
        return factory;
    }

}
