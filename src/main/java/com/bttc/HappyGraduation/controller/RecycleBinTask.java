package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IRecycleBinSV;
import com.bttc.HappyGraduation.scheduler.job.AbstractJob;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author Dk
 * @date 10:27 2019/5/14.
 */
public class RecycleBinTask extends AbstractJob {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(DocumentTask.class);

    @Autowired
    private IRecycleBinSV iRecycleBinSV;

    @Override
    public String execute(Map map) {
        logger.info("RecycleBinTask()开始执行！");
        iRecycleBinSV.dealState4Task();
        logger.info("RecycleBinTask()执行完成！");
        return "end";
    }
}
