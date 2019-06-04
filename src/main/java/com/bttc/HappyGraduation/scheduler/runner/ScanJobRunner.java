package com.bttc.HappyGraduation.scheduler.runner;


import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.job.ScanJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by jiajt on 2018/10/25.
 */
@Component
@Order(value=100)
public class ScanJobRunner implements CommandLineRunner {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private Environment env;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanJobRunner.class);

    @Override
    public void run(String... args) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        LOGGER.info("初始化加载扫描进程");
        initSingleScanJob(scheduler);
        initCircleScanJob(scheduler);
    }

    private void  initSingleScanJob(Scheduler scheduler) throws SchedulerException {
        String singleCron = env.getProperty(JobConstants.SINGLE_SCAN_JOB_PROPERTY);
        if(StringUtils.isBlank(singleCron)){
            return ;
        }
        if(scheduler.checkExists(JobKey.jobKey(JobConstants.SINGLE_SCAN_JOB,JobConstants.JOB_TYPE_BASE))){
            LOGGER.info("SingleScanJob已存在，不再初始化加载扫描进程");
            return ;
        }
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(singleCron);
        scheduler.scheduleJob(getBaseJob(JobConstants.SINGLE_SCAN_JOB, JobConstants.JOB_TYPE_SINGLE),
                getBaseTrigger(JobConstants.SINGLE_SCAN_JOB + JobConstants.JOB_TRIGGER,scheduleBuilder));
    }

    private void initCircleScanJob(Scheduler scheduler) throws SchedulerException {
        String circleCron = env.getProperty(JobConstants.CIRCLE_SCAN_JOB_PROPERTY);
        if(StringUtils.isBlank(circleCron)){
            return ;
        }
        if(scheduler.checkExists(JobKey.jobKey(JobConstants.CIRCLE_SCAN_JOB,JobConstants.JOB_TYPE_BASE))){
            LOGGER.info("CircleScanJob已存在，不再初始化加载扫描进程");
            return ;
        }
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(circleCron);
        scheduler.scheduleJob(getBaseJob(JobConstants.CIRCLE_SCAN_JOB, JobConstants.JOB_TYPE_CIRCLE),
                getBaseTrigger(JobConstants.CIRCLE_SCAN_JOB + JobConstants.JOB_TRIGGER,scheduleBuilder));
    }

    private JobDetail getBaseJob(String jobName, String jobType){
        JobDetail jobDetail = JobBuilder.newJob(ScanJob.class)
                .withDescription(JobConstants.JOB_TYPE_BASE).requestRecovery(false)
                .withIdentity(jobName, JobConstants.JOB_TYPE_BASE)
                .build();
        jobDetail.getJobDataMap().put(JobConstants.COL_JOB_DEFINE_TYPE,jobType);
        return jobDetail;
    }

    private Trigger getBaseTrigger(String triggerName, ScheduleBuilder scheduleBuilder){
        return TriggerBuilder.newTrigger().startNow()
                .withIdentity(triggerName + JobConstants.JOB_TRIGGER, JobConstants.JOB_TYPE_BASE + JobConstants.JOB_TRIGGER)
                .withSchedule(scheduleBuilder).build();
    }
}
