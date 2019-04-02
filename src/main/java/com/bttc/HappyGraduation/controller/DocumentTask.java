package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IFtpFileSV;
import com.bttc.HappyGraduation.scheduler.job.AbstractJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Dk
 * @date 22:35 2019/4/2.
 */
@Component
public class DocumentTask extends AbstractJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IFtpFileSV iFtpFileSV;

    @Override
    public String execute(Map map) {
//        logger.info("DocumentTask()开始执行！");
//        List<FtpFilePO> ftpFilePOList = iFtpFileSV.queryByXmlIsNull();
//        if (!ftpFilePOList.isEmpty()) {
//            FtpFileVO ftpFileVO = null;
//            try {
//                ftpFileVO = iFtpFileSV.parserDocument(ftpFilePOList.get(0));
//                iFtpFileSV.saveFullDocument(ftpFileVO);
//            } catch (IOException e) {
//                return e.getMessage();
//            }
//        }
//        logger.info("DocumentTask()执行完成！");
        return "end";
    }
}
