package com.bttc.HappyGraduation.scheduler.transfer;


import com.alibaba.fastjson.JSON;
import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.transfer.pojo.DataTransferDefinePO;
import com.bttc.HappyGraduation.scheduler.transfer.service.interfaces.IDataTransferDefineSV;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Title: 数据转移启动类</p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 14:12 2019/3/18.
 */
@Component
@Order(value=100)
public class DataTransferRunner implements CommandLineRunner {

    @Autowired
    private IDataTransferDefineSV iDataTransferConfigSV;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        if(!JobConstants.DATA_TRANSFER_OPEN_FLAG.equals(env.getProperty(JobConstants.DATA_TRANSFER))){
            return ;
        }
        List<DataTransferDefinePO> dataTransferConfigPOS = iDataTransferConfigSV.queryAllbyStatusEffect();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for(DataTransferDefinePO dataTransferDefinePO:dataTransferConfigPOS){
            JobDetail jobDetail = JobBuilder.newJob(DataTransferJob.class)
                    .withDescription(JobConstants.JOB_TYPE_DATA_TRANSFER).requestRecovery(false)
                    .withIdentity(String.valueOf(dataTransferDefinePO.getId()), JobConstants.JOB_TYPE_DATA_TRANSFER)
                    .build();
            jobDetail.getJobDataMap().put(JobConstants.JOB_PARAM_DATA_TRANSFER_KEY,JSON.toJSONString(dataTransferDefinePO));
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(dataTransferDefinePO.getCron());
            scheduler.scheduleJob(jobDetail,TriggerBuilder.newTrigger() //定义name/group
                    .withSchedule(scheduleBuilder)//使用SimpleTrigger
                    .build());
        }
    }
}
