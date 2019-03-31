package com.bttc.HappyGraduation.scheduler;


import com.bttc.HappyGraduation.scheduler.pojo.JobDefinePO;
import com.bttc.HappyGraduation.scheduler.service.AbstractScanService;
import com.bttc.HappyGraduation.scheduler.util.ExceptionUtil;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jiajt on 2018/11/1.
 */
@Component
public class OsrdcScanJobProcess {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(OsrdcScanJobProcess.class);

    public void process(AbstractScanService abstractScanService) throws IOException, SQLException, ClassNotFoundException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobDefinePO> jobDefinePOS = abstractScanService.getjobData();
        for(JobDefinePO jobDefinePO : jobDefinePOS){
            try{
                LOGGER.info("加入调度器，jobdefineId："+jobDefinePO.getJobDefineId());
                JobDetail jobDetail = abstractScanService.formateJob(jobDefinePO);
                Trigger trigger = abstractScanService.formateTrigger(jobDefinePO);
                scheduler.scheduleJob(jobDetail, trigger);
            }catch (Throwable e){
                LOGGER.error("进程加入异常："+ExceptionUtil.getStackTrace(e));
            }
        }
    }
}
