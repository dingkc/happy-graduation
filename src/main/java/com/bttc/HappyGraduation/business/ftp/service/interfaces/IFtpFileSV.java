package com.bttc.HappyGraduation.business.ftp.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface IFtpFileSV {

    void uploadFile(MultipartFile file, Integer parentFileId) throws Exception;

    void downloadFile(String fileUuidName, String fileName, HttpServletResponse response) throws Exception;

    void saveUploadFile(FtpFilePO ftpFilePO) throws BusinessException;

    void deleteFile(Integer ftpFileId) throws BusinessException;

    void updateFile(FtpFilePO ftpFilePO) throws BusinessException;

    FtpFileVO queryFileByFileId(Integer fileId);

    FtpFileListVO queryFileByConditions(Integer parentFileId, String fileType, Integer pageNumber, Integer pageSize, String fileName) throws BusinessException;

    String previewFile(Integer ftpFileId);

    List<FtpFilePO> queryByXmlIsNull();

    void addDir( FtpFileVO ftpFileVO) throws BusinessException;

    void updateFtpFile(Integer ftpFileId, FtpFileVO ftpFileVO) throws BusinessException;

//    FtpFileVO parserDocument(FtpFilePO ftpFilePO);
//
//    void saveFullDocument(FtpFileVO ftpFileVO);
}
