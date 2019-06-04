package com.bttc.HappyGraduation.scheduler.transfer;

import com.alibaba.fastjson.JSON;
import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.transfer.pojo.DataTransferDefinePO;
import com.bttc.HappyGraduation.scheduler.transfer.service.interfaces.IDataTransferSV;
import com.bttc.HappyGraduation.scheduler.util.ExceptionUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 15:17 2019/3/20.
 */
@Component
public class DataTransferJob implements Job {

    @Autowired
    private IDataTransferSV iDataTransferSV;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTransferJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String define = (String) context.getJobDetail().getJobDataMap().get(JobConstants.JOB_PARAM_DATA_TRANSFER_KEY);
        DataTransferDefinePO dataTransferDefinePO = JSON.parseObject(define,DataTransferDefinePO.class);
        try {
            iDataTransferSV.dataTransfer(dataTransferDefinePO);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtil.getStackTrace(e));
        }
    }
}
