package com.bttc.HappyGraduation.business.ftp.service.impl;

import com.bttc.HappyGraduation.business.ftp.constant.FtpFileConstant;
import com.bttc.HappyGraduation.business.ftp.dao.FtpFileDao;
import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IFtpFileSV;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IRecycleBinSV;
import com.bttc.HappyGraduation.business.ftp.utils.FTPConfig;
import com.bttc.HappyGraduation.business.ftp.utils.FTPConstant;
import com.bttc.HappyGraduation.business.ftp.utils.FTPUtil;
import com.bttc.HappyGraduation.business.ftp.utils.UploadUtils;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import com.bttc.HappyGraduation.common.DateUtil;
import com.bttc.HappyGraduation.session.web.SessionManager;
import com.bttc.HappyGraduation.utils.base.Filter;
import com.bttc.HappyGraduation.utils.base.QueryParams;
import com.bttc.HappyGraduation.utils.constant.CommonConstant;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @Value("${spring.servlet.multipart.location}")
    private String fileLocation;

    @Override
    public void uploadFile(MultipartFile file, Integer parentFileId) throws Exception {
        // 上传文件输入流
        InputStream inputStream = null;
        //上传文件的名称
        String filename = "";
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //生成一个uuid名称出来
        String uuidFilename = UploadUtils.getUUIDName(originalFilename);
        //产生一个随机目录
        String randomDir = UploadUtils.getDir();
        try{
            if (null != file){
                inputStream = file.getInputStream();
                filename = file.getOriginalFilename().substring(originalFilename.lastIndexOf("\\") + 1);
            } else {
                BusinessException.throwBusinessException(ErrorCode.CORE_UPLOADFILE_FAILED);
            }
        }catch (Exception e){
            BusinessException.throwBusinessException(ErrorCode.CORE_UPLOADFILE_FAILED);
        }

        long size = file.getSize();
        //ftp服务器上的文件路径
        String fullPathNoName = rootPath;//+ randomDir;

        FTPUtil ftpUtil = new FTPUtil(ftpConfig);
        ftpUtil.open();
        ftpUtil.setMode(FTPConstant.PASSIVE_MODE);
        ftpUtil.setFileType(FTPConstant.BIN);
        ftpUpload(ftpUtil, inputStream, fullPathNoName, uuidFilename);

        FtpFilePO ftpFilePO = new FtpFilePO();
        ftpFilePO.setFileName(filename);
        ftpFilePO.setFileUuidName(uuidFilename);
        ftpFilePO.setFilePath(fullPathNoName);
        ftpFilePO.setFileSize(size);
        ftpFilePO.setFileType(originalFilename.split("[.]")[1]);
        ftpFilePO.setParentFileId(parentFileId);
        saveUploadFile(ftpFilePO);
    }

    void ftpUpload(FTPUtil ftpUtil,InputStream inputStream, String fullPathNoName, String fileName) throws IOException {
        fullPathNoName = modifyFullPathNoName(fullPathNoName);
        try{
            ftpUtil.changeDirectory("/");
            // 保证可以创建多层目录
            StringTokenizer s = new StringTokenizer(fullPathNoName, "/");
            s.countTokens();
            String pathName = "";
            while (s.hasMoreElements()) {
                pathName = pathName + "/" + (String) s.nextElement();
                try {
                    ftpUtil.mkdir(pathName);
                } catch (Exception e) {
                    logger.error("ftp文件夹创建失败");
                }
            }
            if(fullPathNoName != null){
                ftpUtil.changeDirectory(fullPathNoName);
            }
            if(fileName != null){
                ftpUtil.upload(fileName,inputStream);
            }
        }catch (Exception e){
            logger.error("ftp上载出错");
        } finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
    }

    @Override
    public void downloadFile(FtpFileVO ftpFileVO, HttpServletResponse response) throws Exception {
        OutputStream outputStream = null;
        String fullPathNoName = rootPath;
        FTPUtil ftpUtil = new FTPUtil(ftpConfig);
        ftpUtil.open();
        ftpUtil.setMode(FTPConstant.PASSIVE_MODE);
        ftpUtil.setFileType(FTPConstant.BIN);
        try {
            InputStream inputStream = downloadDocumentFromFtp(ftpUtil, fullPathNoName, ftpFileVO.getFileUuidName());
            if (null == inputStream) {
                BusinessException.throwBusinessException(ErrorCode.CODE_FILE_NOTFOUND);
            }
            outputStream = response.getOutputStream();
            String newDocumentName = URLEncoder.encode(ftpFileVO.getFileName(), "UTF-8").replaceAll("\\+", "%20").
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

    public InputStream downloadDocumentFromFtp(FTPUtil ftpUtil, String fullPathNoName, String fileName) throws Exception {
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

    private InputStream downloadFromFtp(FTPUtil ftpUtil, String fullPathNoFileName, String fileName) throws Exception {
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
    public void deleteFile(Integer ftpFileId) throws BusinessException {
        //ftp删除

        //文件表删除记录
        FtpFilePO ftpFilePO = ftpFileDao.queryAllByFtpFileIdAndState(ftpFileId, CommonConstant.CommonState.EFFECT.getValue());
        ftpFilePO.setState(CommonConstant.CommonState.INVALID.getValue());
        ftpFileDao.save(ftpFilePO);
        //回收站新增记录
        RecycleBinVO recycleBinVO = new RecycleBinVO();
        recycleBinVO.setFileName(ftpFilePO.getFileName());
        recycleBinVO.setFileType(ftpFilePO.getFileType());
        recycleBinVO.setFilePath(ftpFilePO.getFilePath());
        recycleBinVO.setFileSize(ftpFilePO.getFileSize());
        recycleBinVO.setDoneDate(DateUtil.getNowDate());
        recycleBinVO.setExpireDate(getStatetime());
        recycleBinVO.setOperatorId(SessionManager.getUserInfo().getUserId());
        recycleBinVO.setState(CommonConstant.CommonState.EFFECT.getValue());
        iRecycleBinSV.addRecord(recycleBinVO);
    }

    /**
     * 获取未来 第 past 天的日期
     * @return
     */
    public Date getStatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, +7);
        Date time = c.getTime();
        return time;
    }

    @Override
    public FtpFileVO queryFileByFileId(Integer fileId) {
        return BeanMapperUtil.map(ftpFileDao.queryAllByFtpFileIdAndState(fileId, CommonConstant.CommonState.EFFECT.getValue()), FtpFileVO.class);
    }

    @Override
    public FtpFileListVO queryFileByConditions(Integer parentFileId, String fileType, Integer pageNumber, Integer pageSize, String fileName) throws BusinessException {
        QueryParams<FtpFilePO> queryParams = new QueryParams<>(FtpFilePO.class);
        FtpFilePO ftpFilePO = new FtpFilePO();
        ftpFilePO.setState(CommonConstant.CommonState.EFFECT.getValue());
        queryParams.and(Filter.eq("creatorId", SessionManager.getUserInfo().getUserId()))
                .and(Filter.eq("parentFileId", parentFileId))
                .and(Filter.like("fileName", fileName))
                .and(Filter.eq("fileType", fileType));
        List<FtpFilePO> beansAutoExceptNull = ftpFileDao.getBeansAutoExceptNull(ftpFilePO, queryParams);
        FtpFileListVO ftpFileListVO = new FtpFileListVO();
        List<FtpFilePO> collect;
        if (null == parentFileId) {
            collect = beansAutoExceptNull.stream().filter(ftpFile -> ftpFile.getParentFileId() == null).collect(toList());
        } else {
            collect = beansAutoExceptNull;
        }
        List<FtpFileVO> ftpFileVOS = BeanMapperUtil.mapList(collect, FtpFilePO.class, FtpFileVO.class);
        if (pageNumber != null && pageSize != null) {
            int size = collect.size();
            ftpFileListVO.setTotal(size);
            int end = pageNumber * pageSize;
            int start = end - pageSize;
            if (size <= start) {
                ftpFileListVO.setRows(null);
            } else {
                ftpFileListVO.setRows(ftpFileVOS.subList(start, Math.min(end, size)));
            }
        } else {
            ftpFileListVO.setRows(ftpFileVOS);
        }
        return ftpFileListVO;
    }

    @Override
    public List<FtpFilePO> queryByXmlIsNull() {
        return ftpFileDao.queryAllByStateAndFilePreviewIsNull(CommonConstant.CommonState.EFFECT.getValue());
    }

    @Override
    public String previewFile(Integer ftpFileId) {
        FtpFilePO ftpFilePO = ftpFileDao.queryAllByFtpFileIdAndState(ftpFileId, CommonConstant.CommonState.EFFECT.getValue());
        return ftpFilePO.getFilePreview();
    }

    /**
     * <p>Title:calculatFolderSize</p>
     * <p>Description: 计算文件夹内所有文件大小</p>
     * @Author: Dk
     * @param ftpFileId : 文件编号
     * @return: java.lang.Long
     * @Date: 2019/4/16 17:31
     **/
    private Long calculatFolderSize(Integer ftpFileId) throws BusinessException {
        FtpFilePO ftpFilePO = ftpFileDao.queryAllByFtpFileIdAndState(ftpFileId, CommonConstant.CommonState.EFFECT.getValue());
        if (FtpFileConstant.FileType.DIR.equals(ftpFilePO.getFileType())) {
            return ftpFilePO.getFileSize();
        }
        List<FtpFilePO> ftpFilePOS = ftpFileDao.queryAllByStateAndParentFileIdAndCreatorId(CommonConstant.CommonState.EFFECT.getValue(), ftpFileId, SessionManager.getUserInfo().getUserId());
        Long dirSize = 0L;
        for (FtpFilePO ftpFile : ftpFilePOS) {
            dirSize += ftpFile.getFileSize() == null ? 0L : ftpFile.getFileSize();
        }
        return dirSize;
    }

    @Override
    public void updateFile(FtpFilePO ftpFilePO) throws BusinessException {
        ftpFilePO = Optional.ofNullable(ftpFilePO).orElseThrow(() -> new BusinessException(ErrorCode.FILE_IS_EMPTY));
        ftpFilePO.setDoneDate(DateUtil.getNowDate());
        ftpFilePO.setOperatorId(1);
        ftpFileDao.updateBeans(ftpFilePO);
    }

    @Override
    public void addDir(FtpFileVO ftpFileVO) throws BusinessException {
        Integer userId = SessionManager.getUserInfo().getUserId();
        Date nowDate = DateUtil.getNowDate();
        FtpFilePO ftpFilePO = BeanMapperUtil.map(ftpFileVO, FtpFilePO.class);
        ftpFilePO.setCreatorId(userId);
        ftpFilePO.setCreateDate(nowDate);
        ftpFilePO.setDoneDate(nowDate);
        ftpFilePO.setOperatorId(userId);
        ftpFilePO.setState(CommonConstant.CommonState.EFFECT.getValue());
        ftpFileDao.save(ftpFilePO);
    }

    @Override
    public void updateFtpFile(Integer ftpFileId, FtpFileVO ftpFileVO) throws BusinessException {
        if (null != ftpFileVO.getNewParentFileId()) {
            //移动到
            FtpFileVO parentFileVO = queryFileByFileId(ftpFileVO.getNewParentFileId());
            if (!FtpFileConstant.FileType.DIR.getName().equals(parentFileVO.getFileType())) {
                BusinessException.throwBusinessException(ErrorCode.TARGET_FILE_IS_NOT_DIR);
            }
            //更新父文件大小
            long newFileSize = Long.valueOf(parentFileVO.getFileSize()) + Long.valueOf(ftpFileVO.getFileSize());
            parentFileVO.setFileSize(String.valueOf(newFileSize));
            parentFileVO.setDoneDate(DateUtil.getNowDate());
            parentFileVO.setOperatorId(SessionManager.getUserInfo().getUserId());
            ftpFileDao.save(BeanMapperUtil.map(parentFileVO, FtpFilePO.class));
        }else {
            //编辑
            FtpFilePO ftpFilePO = BeanMapperUtil.map(ftpFileVO, FtpFilePO.class);
            ftpFilePO.setDoneDate(DateUtil.getNowDate());
            ftpFilePO.setOperatorId(SessionManager.getUserInfo().getUserId());
            ftpFileDao.save(ftpFilePO);
        }
    }
}
