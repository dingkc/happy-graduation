package com.bttc.HappyGraduation.business.ftp.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface IFtpFileSV {

    void uploadFile(MultipartFile file, Integer parentFileId) throws Exception;

    void saveUploadFile(FtpFilePO ftpFilePO) throws BusinessException;

    void deleteFile(FtpFileVO ftpFileVO) throws BusinessException;

    void updateFile(FtpFilePO ftpFilePO) throws BusinessException;

    FtpFileVO queryFileByFileId(Integer fileId);

    FtpFileListVO queryFileByConditions(Integer userId, Integer parentFileId, String fileType, Integer pageNumber, Integer pageSize);

    String previewFile(Integer ftpFileId);

    List<FtpFilePO> queryByXmlIsNull();

//    FtpFileVO parserDocument(FtpFilePO ftpFilePO);
//
//    void saveFullDocument(FtpFileVO ftpFileVO);
}
