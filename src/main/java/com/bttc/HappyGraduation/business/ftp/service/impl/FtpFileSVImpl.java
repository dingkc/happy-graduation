package com.bttc.HappyGraduation.business.ftp.service.impl;

import com.bttc.HappyGraduation.business.ftp.dao.FtpFileDao;
import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.po.RecycleBinPO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IFtpFileSV;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IRecycleBinSV;
import com.bttc.HappyGraduation.business.ftp.utils.FTPConfig;
import com.bttc.HappyGraduation.business.ftp.utils.FTPConstant;
import com.bttc.HappyGraduation.business.ftp.utils.FTPUtil;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import com.bttc.HappyGraduation.common.DateUtil;
import com.bttc.HappyGraduation.session.web.SessionManager;
import com.bttc.HappyGraduation.utils.constant.CommonConstant;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author Dk
 * @date 23:09 2019/3/25.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class FtpFileSVImpl implements IFtpFileSV {

    private static final Logger logger = LoggerFactory.getLogger(FtpFileSVImpl.class);

    @Autowired
    private FtpFileDao ftpFileDao;

    @Autowired
    private IRecycleBinSV iRecycleBinSV;

    @Autowired
    private FTPConfig ftpConfig;//注入配置

    @Value("${local.ftp.username}")
    private String ftpUsername;//ftp用户名
    @Value("${local.ftp.password}")
    private String ftpPassword;//ftp密码
    @Value("${local.ftp.port}")
    private int ftpPort; //ftp端口
    @Value("${local.ftp.ip}")
    private String ftpHost; //ip
    @Value("${local.ftp.root}")
    private String rootPath;

    @Override
    public void uploadFile(MultipartFile file) throws Exception {
        // 上传文件输入流
        InputStream inputStream = null;
        //上传文件的名称
        String filename = "";
        try {
            if (null != file) {
                inputStream = file.getInputStream();
                filename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("\\") + 1);
            } else {
                BusinessException.throwBusinessException(ErrorCode.CORE_UPLOADFILE_FAILED);
            }
        } catch (Exception e) {
            BusinessException.throwBusinessException(ErrorCode.CORE_UPLOADFILE_FAILED);
        }

    }

    public void downLoadOnline(String documentName, String documentRealName, String documentPath, String sourceType, HttpServletResponse response) throws Exception {
        OutputStream outputStream = null;
        String fullPathNoName = rootPath + sourceType + documentPath;
        FTPUtil ftpUtil = new FTPUtil(ftpConfig);
        ftpUtil.open();
        ftpUtil.setMode(FTPConstant.PASSIVE_MODE);
        ftpUtil.setFileType(FTPConstant.BIN);
        try {
            InputStream inputStream = downloadDocumentFromFtp(ftpUtil, fullPathNoName, documentRealName);
            if (null == inputStream) {
                BusinessException.throwBusinessException(ErrorCode.CODE_FILE_NOTFOUND);
            }
            outputStream = response.getOutputStream();
            String newDocumentName = URLEncoder.encode(documentName, "UTF-8").replaceAll("\\+", "%20").
                    replaceAll("%28", "\\(").replaceAll("%29", "\\)").replaceAll("%3B", ";").
                    replaceAll("%40", "@").replaceAll("%23", "\\#").replaceAll("%26", "\\&");
            response.reset();
            response.setContentType("multipart/from-date");
            response.setHeader("Content-Disposition", "attachment;filename=" + newDocumentName);
            byte[] b = new byte[1024];
            int n;// 每次读取到的字节数组的长度
            while ((n = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error("文件下载失败", e.getMessage());
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            ftpUtil.close();
        }
    }

    public InputStream downloadDocumentFromFtp(FTPUtil ftpUtil, String fullPathNoName, String fileName) {
        fullPathNoName = modifyFullPathNoName(fullPathNoName);
        InputStream inputStream = null;
        try {
            inputStream = downloadFromFtp(ftpUtil, fullPathNoName, fileName);
        } catch (Exception e) {
            logger.error("从ftp下载失败" + e.getMessage());
        }
        return inputStream;
    }

    private String modifyFullPathNoName(String fullPathNoName) {
        if (fullPathNoName != null) {
            if (!fullPathNoName.startsWith("/")) {
                fullPathNoName = "/" + fullPathNoName;
            }
            if (!fullPathNoName.endsWith("/")) {
                fullPathNoName = fullPathNoName + "/";
            }
        }
        return fullPathNoName;
    }

    private InputStream downloadFromFtp(FTPUtil ftpUtil, String fullPathNoFileName,String fileName) throws Exception {
        ftpUtil.changeDirectory(fullPathNoFileName);
        InputStream inputStream = ftpUtil.download(fileName);
        return inputStream;
    }

    @Override
    public void saveUploadFile(FtpFilePO ftpFilePO) throws BusinessException {
        Date nowDate = DateUtil.getNowDate();
        Integer userId = SessionManager.getUserInfo().getUserId();
        ftpFilePO.setCreatorId(userId);
        ftpFilePO.setCreateDate(nowDate);
        ftpFilePO.setOperatorId(userId);
        ftpFilePO.setDoneDate(nowDate);
        ftpFilePO.setState(CommonConstant.CommonState.EFFECT.getValue());
        ftpFileDao.save(ftpFilePO);
    }

    @Override
    public void deleteFile(FtpFileVO ftpFileVO) throws BusinessException {
        //ftp删除

        //文件表删除记录
        FtpFilePO ftpFilePO = BeanMapperUtil.map(ftpFileVO, FtpFilePO.class);
        ftpFilePO.setState(CommonConstant.CommonState.INVALID.getValue());
        ftpFileDao.save(ftpFilePO);
        //回收站新增记录
        RecycleBinVO recycleBinVO = new RecycleBinVO();
        recycleBinVO.setFileName(ftpFilePO.getFileName());
        recycleBinVO.setFileType(ftpFilePO.getFileType());
        recycleBinVO.setFilePath(ftpFilePO.getFilePath());
        recycleBinVO.setFileSize(ftpFilePO.getFileSize());
        recycleBinVO.setDoneDate(DateUtil.getNowDate());
        recycleBinVO.setOperatorId(SessionManager.getUserInfo().getUserId());
        recycleBinVO.setState(CommonConstant.CommonState.EFFECT.getValue());
        iRecycleBinSV.addRecord(recycleBinVO);
    }
}