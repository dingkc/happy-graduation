package com.bttc.HappyGraduation.scheduler.job;


import com.bttc.HappyGraduation.scheduler.JobTypeFactory;
import com.bttc.HappyGraduation.scheduler.OsrdcScanJobProcess;
import com.bttc.HappyGraduation.scheduler.util.ExceptionUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bttc.HappyGraduation.scheduler.JobConstants.COL_JOB_DEFINE_TYPE;


/**
 * Created by jiajt on 2018/11/1.
 */
public class ScanJob implements Job {

    @Autowired
    private OsrdcScanJobProcess osrdcScanJobProcess;

    @Autowired
    private JobTypeFactory jobTypeFactory;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScanJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            osrdcScanJobProcess.process(jobTypeFactory.getBaseJobInstance((String) context.getJobDetail().getJobDataMap().get(COL_JOB_DEFINE_TYPE)));
        } catch (Exception e) {
            LOGGER.error("扫描进程执行异常！");
            LOGGER.error(ExceptionUtil.getStackTrace(e));
        }
    }
}
