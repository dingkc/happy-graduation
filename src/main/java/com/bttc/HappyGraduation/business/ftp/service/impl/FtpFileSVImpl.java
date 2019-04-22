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
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.drawingml.x2006.main.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
        //获取文件的内容
        InputStream is = file.getInputStream();
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //生成一个uuid名称出来
        String uuidFilename = UploadUtils.getUUIDName(originalFilename);
        //产生一个随机目录
        String randomDir = UploadUtils.getDir();
        File fileDir = new File(fileLocation + randomDir);
        //若文件夹不存在,则创建出文件夹
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File newFile = new File(fileLocation + randomDir, uuidFilename);
        FileInputStream fileInputStream = new FileInputStream(newFile);
        long size = fileInputStream.getChannel().size();
//        System.out.println(newFile.getPath());
//        System.out.println(newFile.getAbsolutePath());
//        System.out.println(newFile.getCanonicalPath());
        //将文件输出到目标的文件中
//        file.transferTo(newFile);
        String[] split = UploadUtils.getRealName(originalFilename).split("\\.");
        file.transferTo(Paths.get(newFile.getPath()).toFile());
        String savePath = randomDir + "/" + uuidFilename;

        FtpFilePO ftpFilePO = new FtpFilePO();
        ftpFilePO.setFileName(originalFilename);
        ftpFilePO.setFileUuidName(uuidFilename);
        ftpFilePO.setFilePath(savePath);
        ftpFilePO.setFileSize(size);
        ftpFilePO.setFileType(split[split.length - 1]);
        ftpFilePO.setParentFileId(parentFileId);
        saveUploadFile(ftpFilePO);
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
        Page<FtpFilePO> beans = ftpFileDao.getBeansAutoExceptNull(ftpFilePO, queryParams, pageNumber - 1, pageSize);
        FtpFileListVO ftpFileListVO = new FtpFileListVO();
        List<FtpFilePO> ftpFilePOS = beans.getContent();
        List<FtpFilePO> collect;
        if (null == parentFileId) {
            collect = ftpFilePOS.stream().filter(ftpFile -> ftpFile.getParentFileId() == null).collect(toList());
        } else {
            collect = ftpFilePOS;
        }
        ftpFileListVO.setRows(BeanMapperUtil.mapList(collect, FtpFilePO.class, FtpFileVO.class));
        ftpFileListVO.setTotal(collect.size());
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
}
