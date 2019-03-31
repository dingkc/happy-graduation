package com.bttc.HappyGraduation.scheduler.job;

import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.JobTypeFactory;
import com.bttc.HappyGraduation.scheduler.pojo.JobDefinePO;
import com.bttc.HappyGraduation.scheduler.service.AbstractScanService;
import com.bttc.HappyGraduation.scheduler.service.JobDefineService;
import com.bttc.HappyGraduation.scheduler.util.ExceptionUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by jiajt on 2018/10/25.
 */
public abstract class AbstractJob implements Job {

    @Autowired
    private JobTypeFactory jobTypeFactory;

    @Autowired
    private JobDefineService jobDefineService;

    private JobExecutionContext context = null;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);

    @Override
    public final void execute(JobExecutionContext context) throws JobExecutionException {
        this.context = context;
        AbstractScanService abstractScanService = jobTypeFactory.getBaseJobInstance(context.getJobDetail().getDescription());
        abstractScanService.beforeExcuted(context);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String message = null;
        boolean isSucess = true;
        try{
            message = this.execute(context.getJobDetail().getJobDataMap());
        }catch (Exception e){
            message= ExceptionUtil.getStackTrace(e);
            LOGGER.error(message);
            isSucess = false;
        }finally {
            stopWatch.stop();
            if(message==null){
                message="";
            }
            abstractScanService.afterExcuted(context,message,stopWatch.getNanoTime()/1000L,isSucess);
            this.context = null;
        }
    }

    public abstract String execute(Map map);

    public void delete(){
        if(this.context!=null){
            String jobDefineId = (String) this.context.getJobDetail().getJobDataMap().get(JobConstants.COL_JOB_DEFINE_ID);
            JobDefinePO jobDefinePO = new JobDefinePO();
            jobDefinePO.setJobDefineId(Long.valueOf(jobDefineId));
            jobDefinePO.setDoneDate(new Date());
            jobDefineService.deleteJob(jobDefinePO);
            try {
                this.context.getScheduler().deleteJob(this.context.getJobDetail().getKey());
            } catch (SchedulerException e) {
                LOGGER.error("删除进程异常："+ExceptionUtil.getStackTrace(e));
            }
        }
    }

}
