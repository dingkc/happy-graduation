package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.doc.service.interfaces.IOnlineDocumentSV;
import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IFtpFileSV;
import com.bttc.HappyGraduation.scheduler.job.AbstractJob;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Dk
 * @date 22:35 2019/4/2.
 */
@Component
public class DocumentTask extends AbstractJob {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(DocumentTask.class);

    @Autowired
    private IFtpFileSV iFtpFileSV;
    @Autowired
    private IOnlineDocumentSV iOnlineDocumentSV;

    @Override
    public String execute(Map map) {
        logger.info("DocumentTask()开始执行！");
        List<FtpFilePO> ftpFilePOList = iFtpFileSV.queryByXmlIsNull();
        if (CollectionUtils.isNotEmpty(ftpFilePOList)) {
            ftpFilePOList.forEach(ftpFilePO -> {
                try {
                    iOnlineDocumentSV.parserDocument(ftpFilePO.getFtpFileId());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
            });
        }
        logger.info("DocumentTask()执行完成！");
        return "end";
    }
}
