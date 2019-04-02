package com.bttc.HappyGraduation.business.ftp.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface IFtpFileSV {

    File uploadFile(MultipartFile file) throws Exception;

    void saveUploadFile(FtpFilePO ftpFilePO) throws BusinessException;

    void deleteFile(FtpFileVO ftpFileVO) throws BusinessException;

    FtpFileVO queryFileByFileId(Integer fileId);

    FtpFileListVO queryFileByUserId(Integer userId, Integer parentFileId, Integer pageNumber, Integer pageSize);
}
