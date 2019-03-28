package com.bttc.HappyGraduation.business.ftp.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface IFtpFileSV {

    void uploadFile(MultipartFile file) throws Exception;

    void saveUploadFile(FtpFilePO ftpFilePO) throws BusinessException;

    void deleteFile(FtpFileVO ftpFileVO) throws BusinessException;
}
