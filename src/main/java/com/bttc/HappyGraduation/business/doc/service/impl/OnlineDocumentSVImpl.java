package com.bttc.HappyGraduation.business.doc.service.impl;

import com.bttc.HappyGraduation.business.doc.service.interfaces.IOnlineDocumentSV;
import com.bttc.HappyGraduation.business.doc.service.interfaces.IWordToHtml;
import com.bttc.HappyGraduation.business.ftp.constant.FtpFileConstant;
import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IFtpFileSV;
import com.bttc.HappyGraduation.business.ftp.utils.FTPConfig;
import com.bttc.HappyGraduation.business.ftp.utils.FTPConstant;
import com.bttc.HappyGraduation.business.ftp.utils.FTPUtil;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.drawingml.x2006.main.*;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.bttc.HappyGraduation.business.doc.service.impl.POIReadExcel.getSheetPictrues;

/**
 * @author Dk
 * @date 11:08 2019/4/17.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class OnlineDocumentSVImpl implements IOnlineDocumentSV {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(OnlineDocumentSVImpl.class);
    private static final String underline = "_";
    private static final String comma = ",";
    //excel
    private static String[] bordesr={"border-top:","border-right:","border-bottom:","border-left:"};
    private static String[] borderStyles={"solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid ","solid","solid","solid","solid","solid"};
    private static final String color = "#d0d7e5 1px;";
    private static final String size = " 1px;";
    //ppt
    static String imgHead = "<img src=\'";
    static String style = "\' style=\'width:1200px;height:830px;vertical-align:text-bottom;\'><br><br><br><br>";
    static String pptHtmlHead = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>";
    static String pptHtmlEnd = "</body></html>";
    static String spot = ".";

    @Autowired
    private IWordToHtml iWordToHtml;

    @Autowired
    private IFtpFileSV iFtpFileSV;

    @Autowired
    private FTPConfig ftpConfig;

    //svn和ftp之间传递文件的中间层
    @Value("${local.file.save}")
    private String temporaryStoragePath;
    //ftp存放图片的url
    @Value("${remote.ftp.pictureUrl}")
    private String pictureUrl;
    @Value("${local.ftp.root}")
    private String rootPath;
    @Value("${local.ftp.picturePath}")
    private String picturePath;

    @Override
    public void parserDocument(Integer ftpFileId) throws Exception {
        FtpFileVO ftpFileVO = Optional.ofNullable(iFtpFileSV.queryFileByFileId(Optional.ofNullable(ftpFileId).orElseThrow(() -> new BusinessException(ErrorCode.PARAMETER_NULL, "文件编号")))).orElseThrow(() -> new BusinessException(ErrorCode.FILE_IS_EMPTY));
        FtpFilePO ftpFilePO = BeanMapperUtil.map(ftpFileVO, FtpFilePO.class);
        //判断文件种类并解析
        String fullPathNoName = rootPath  + ftpFilePO.getFilePath();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //检查文件输入流的大小
        checkFileInputStreamSize(ftpFilePO, fullPathNoName);
        FTPUtil ftpUtil = new FTPUtil(ftpConfig);
        ftpUtil.open();
        ftpUtil.setMode(FTPConstant.PASSIVE_MODE);
        ftpUtil.setFileType(FTPConstant.BIN);
        inputStream = downloadDocumentFromFtp(ftpUtil, fullPathNoName, ftpFilePO.getFileName());
        File imageFolder = new File(getTemplateFilePath());
        if (!imageFolder.exists()) {
            //本地图片存放目录不存在则创建
            imageFolder.mkdir();
        }
        //获取文件类型
        String fileType = ftpFilePO.getFileType();
        FtpFileVO parseFile = new FtpFileVO();
        //调用对应service进行解析
        if (FtpFileConstant.FileType.DOC.getName().equals(fileType)){
            parseFile = parserDoc(ftpFilePO, inputStream);
        }else if (FtpFileConstant.FileType.DOCX.getName().equals(fileType)){
            parseFile = parserDocx(ftpFilePO, inputStream);
        }else if (FtpFileConstant.FileType.XLS.getName().equals(fileType)){
            parseFile = parserXls(ftpFilePO, inputStream, true);
        }else if (FtpFileConstant.FileType.XLSX.getName().equals(fileType)){
            parseFile = parserXlsx(ftpFilePO, inputStream,true);
        }else if (FtpFileConstant.FileType.PPT.getName().equals(fileType)){
            parseFile = parserPPT(ftpFilePO, inputStream, "png");
        }else if (FtpFileConstant.FileType.PPTX.getName().equals(fileType)){
            parseFile = parserPPTX(ftpFilePO, inputStream, "png");
        }else {
            BusinessException.throwBusinessException(ErrorCode.CORE_DOCUMENT_TRANSFORMATION,"该文档类型不支持解析!");
        }
        //将解析好的保存到表中
        ftpFilePO.setFilePreview(parseFile.getFilePreview());
        iFtpFileSV.updateFile(ftpFilePO);
    }

    //上传本地图片到ftp服务器并删除图片文件夹
    private void uploadImageToFtp(Map<String, String> imageMap, Map<String, String> map)throws Exception{
        if (null != imageMap){
            for (Map.Entry<String, String> entry : imageMap.entrySet()) {
                InputStream picInputStream = null;
                FTPUtil ftpUtil = new FTPUtil(ftpConfig);
                ftpUtil.open();
                ftpUtil.setMode(FTPConstant.PASSIVE_MODE);
                ftpUtil.setFileType(FTPConstant.BIN);
                try{
                    File picFile = new File(entry.getValue() + entry.getKey());
                    picInputStream = new FileInputStream(picFile);
                    ftpUpload(ftpUtil, picInputStream,rootPath + picturePath, map.get(entry.getKey()));
                    //加上确保文件能删除，不然可能删不掉
                    System.gc();
                    boolean delete = picFile.delete();
                    if(!delete){
                        logger.error("删除图片失败！");
                    }
                }finally {
                    if (null != picInputStream) {
                        picInputStream.close();
                    }
                    ftpUtil.close();
                }
            }
        }
    }

    @Override
    public void ftpUpload(FTPUtil ftpUtil,InputStream inputStream, String fullPathNoName,String fileName) throws IOException {
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
            if(StringUtils.isNotEmpty(fullPathNoName)){
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
    public InputStream downloadDocumentFromFtp(FTPUtil ftpUtil,String fullPathNoName,String fileName) {
        fullPathNoName = modifyFullPathNoName(fullPathNoName);
        InputStream inputStream = null;
        try{
            inputStream = downloadFromFtp(ftpUtil,fullPathNoName,fileName);
        } catch (Exception e) {
            logger.error("从ftp下载失败" + e.getMessage());
        }
        return inputStream;
    }

    /**
     * @Description: 下载路径下的文件
     * @param fullPathNoFileName  单一文件全路径(不包含文件名）
     */
    private InputStream downloadFromFtp(FTPUtil ftpUtil, String fullPathNoFileName,String fileName) throws Exception {
        ftpUtil.changeDirectory(fullPathNoFileName);
        InputStream inputStream = ftpUtil.download(fileName);
        return inputStream;
    }

    private String modifyFullPathNoName(String fullPathNoName){
        if(fullPathNoName != null){
            if(!fullPathNoName.startsWith("/")){
                fullPathNoName = "/" + fullPathNoName;
            }
            if(!fullPathNoName.endsWith("/")){
                fullPathNoName = fullPathNoName + "/";
            }
        }
        return fullPathNoName;
    }

    /**
     * 检查文件输入流的大小
     * @param ftpFilePO 需解析的文件对象
     * @param fullPathNoName 不带文件名的全路径
     */
    private void checkFileInputStreamSize(FtpFilePO ftpFilePO, String fullPathNoName) throws Exception {
        FTPUtil ftpUtil = new FTPUtil(ftpConfig);
        ftpUtil.open();
        ftpUtil.setMode(FTPConstant.PASSIVE_MODE);
        ftpUtil.setFileType(FTPConstant.BIN);
        InputStream inputStream = downloadDocumentFromFtp(ftpUtil, fullPathNoName, ftpFilePO.getFileName());
        if (null != inputStream) {
            long fileSize = 0;
            byte[] b = new byte[1024];
            int n;// 每次读取到的字节数组的长度
            try {
                while ((n = inputStream.read(b)) != -1) {
                    fileSize = fileSize + n;
                }
                if (fileSize >= 10485760) {
                    BusinessException.throwBusinessException(ErrorCode.CORE_DOCUMENT_NOPARSING);
                }
            } finally {
                if (null != inputStream) {
                    inputStream.close();
                }
            }
        }else {
            BusinessException.throwBusinessException(ErrorCode.CORE_FILEINPUTSTREAM_NULL);
        }
    }

    public String getTemplateFilePath(){
        //确认临时文件路径
        String path = System.getProperty("user.dir");
        String templateFilePath = path + temporaryStoragePath;
        File files = new File(templateFilePath);
        if(!files.exists()){
            files.mkdirs();
        }
        return templateFilePath;
    }

    //根据指定长度拆分字符串
    private List<String> getStringList(String content, int length){
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            String contentSplitStr = substring(content, i * length, (i + 1) * length);
            stringList.add(contentSplitStr);
        }
        return stringList;
    }

    private String substring(String str, int start, int end) {
        if (start > str.length()) {
            return null;
        }
        if (end > str.length()) {
            return str.substring(start, str.length());
        } else {
            return str.substring(start, end);
        }
    }

    //获取完整的文件对象
    private FtpFileVO setFtpFileInfoVO(FtpFilePO ftpFilePO, String documentContent){
        FtpFileVO ftpFileVO = BeanMapperUtil.map(ftpFilePO, FtpFileVO.class);
        ftpFileVO.setFilePreview(documentContent);
        return ftpFileVO;
    }

    /**
     * 根据Word的版本分配不同的读取方法进行处理
     * 解析后缀为doc的Word文件
     * @param ftpFilePO 需解析的文件对象
     * @param inputStream 需解析的文件输入流
     * @return OnlineDocumentInfoPO 完整的文件对象
     */
    private FtpFileVO parserDoc(FtpFilePO ftpFilePO, InputStream inputStream) throws Exception {
        String tempFile = getTemplateFilePath();
        String documentSource = "";
        String documentContent = "";
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStream outStream = null;
        try {
            HWPFDocument wordDocument = new HWPFDocument(inputStream);
            documentContent = wordDocument.getText().toString();
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            //存放文档source中首次设置的图片原名称集合
            List<String> suggestedNameList = new ArrayList<>();
            //设置图片存放的位置
            wordToHtmlConverter.setPicturesManager( new PicturesManager(){
                @Override
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                    suggestedNameList.add(suggestedName);
                    return pictureUrl + suggestedName;
                }
            });
            //解析word文档
            wordToHtmlConverter.processDocument(wordDocument);
            List<Picture> pictureList = wordDocument.getPicturesTable().getAllPictures();
            Map<String,String> imageMap = new HashMap<>();
            //存放新旧图片相互对应的集合
            Map<String,String> renameImageMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(pictureList)){
                for (Picture picture : pictureList) {
                    FileOutputStream fileOutputStream = null ;
                    try{
                        //获取图片格式
                        String extension = picture.suggestPictureType().getExtension();
                        fileOutputStream = new FileOutputStream(tempFile + picture.suggestFullFileName());
                        picture.writeImageContent(fileOutputStream);
                        if (!"".equals(extension)){
                            renameImageMap.put(picture.suggestFullFileName(), new StringBuffer().append(System.currentTimeMillis()).append(UUID.randomUUID().toString()).append(picture.suggestFullFileName()).toString());
                            imageMap.put(picture.suggestFullFileName(),tempFile);
                        }
                    }finally {
                        if(fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    }
                }
            }
            //首次在文档source中设置的图片原名称
            String imageOriginalName = "";
            for (String suggestedName : suggestedNameList) {
                if (null == renameImageMap.get(suggestedName)){
                    imageOriginalName = suggestedName;
                }
            }
            //替换后文档source中的图片原名称
            String replaceImageOriginalName = "";
            for (Map.Entry<String,String> entry : renameImageMap.entrySet()){
                if (!suggestedNameList.contains(entry.getKey())){
                    replaceImageOriginalName = entry.getKey();
                }
            }
            //上传本地图片到ftp服务器
            uploadImageToFtp(imageMap, renameImageMap);
            Document htmlDocument = wordToHtmlConverter.getDocument();
            DOMSource domSource = new DOMSource(htmlDocument);
            //使用字符数组流获取解析的内容
            byteArrayOutputStream = new ByteArrayOutputStream();
            outStream = new BufferedOutputStream(byteArrayOutputStream);
            StreamResult streamResult = new StreamResult(outStream);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer serializer = transformerFactory.newTransformer();
            //设置解析文档内容的条件
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            documentSource = byteArrayOutputStream.toString();
            //替换图片后的文档source
            if (!"".equals(imageOriginalName) && !"".equals(replaceImageOriginalName)){
                documentSource = documentSource.replace(imageOriginalName, replaceImageOriginalName);
            }
            for (Map.Entry<String, String> entry : renameImageMap.entrySet()) {
                documentSource = documentSource.replace(entry.getKey(), entry.getValue());
            }
            return setFtpFileInfoVO(ftpFilePO, documentContent);
        }finally{
            if (null != byteArrayOutputStream){
                byteArrayOutputStream.close();
            }
            if (null != outStream){
                outStream.close();
            }
            if (null != inputStream){
                inputStream.close();
            }
        }
    }

    /**
     * 根据Word的版本分配不同的读取方法进行处理
     * 解析后缀为docx的Word文件
     * @param ftpFilePO 需解析的文件对象
     * @param inputStream 需解析的文件输入流
     * @return OnlineDocumentInfoPO 完整的文件对象
     */
    private FtpFileVO parserDocx(FtpFilePO ftpFilePO, InputStream inputStream) throws Exception {
        String tempFile = getTemplateFilePath();
        String documentContent = "";
        String documentSource = "";
        ByteArrayOutputStream byteArrayOutputStream = null;
        XWPFWordExtractor xwpfWordExtractor = null;
        try {
            //加载word文档生成 XWPFDocument对象
            String docxImageSubPath = "word/media/";
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            xwpfWordExtractor = new XWPFWordExtractor(xwpfDocument);
            byteArrayOutputStream = new ByteArrayOutputStream();
            //解析 XHTML配置 (这里用IURIResolver来设置图片存放的目录)
            File imageFolderFile = new File(tempFile);
            XHTMLOptions xhtmlOptions = XHTMLOptions.create();
            xhtmlOptions.setExtractor(new FileImageExtractor(imageFolderFile));
            xhtmlOptions.setIgnoreStylesIfUnused(false);
            xhtmlOptions.setFragment(true);
            xhtmlOptions.URIResolver( new BasicURIResolver(pictureUrl));
            try{
                XHTMLConverter.getInstance().convert(xwpfDocument, byteArrayOutputStream, xhtmlOptions);
            }catch (Exception e){
                logger.error(e.getMessage());
                BusinessException.throwBusinessException(ErrorCode.CORE_DOCUMENTSECTION_NULL, "设置页眉页脚的节点为空---" + e.getMessage());
            }
            documentSource = byteArrayOutputStream.toString();
            documentContent = xwpfWordExtractor.getText();
            //上传本地图片到ftp服务器
            Map<String,String> imageMap = new HashMap<>();
            //存放新旧图片相互对应的集合
            Map<String,String> renameImageMap = new HashMap<>();
            String imagePath = tempFile + docxImageSubPath;
            File imageFolder = new File(imagePath);
            File[] imageFiles = imageFolder.listFiles();
            if (null != imageFiles){
                for (File imageFile : imageFiles) {
                    if (null != imageFile){
                        renameImageMap.put(imageFile.getName(), new StringBuffer().append(System.currentTimeMillis()).append(UUID.randomUUID().toString()).append(imageFile.getName()).toString());
                        imageMap.put(imageFile.getName(), imagePath);
                    }
                }
            }
            uploadImageToFtp(imageMap, renameImageMap);
            for (Map.Entry<String, String> entry : renameImageMap.entrySet()) {
                documentSource = documentSource.replace(new StringBuffer().append("/").append(docxImageSubPath).append(entry.getKey()).toString(), entry.getValue());
            }
            return setFtpFileInfoVO(ftpFilePO, documentContent);
        }finally {
            if (null != byteArrayOutputStream){
                byteArrayOutputStream.close();
            }
            if (null != inputStream){
                inputStream.close();
            }
            if (null != xwpfWordExtractor){
                xwpfWordExtractor.close();
            }
        }
    }

    /**
     * 根据excel的版本分配不同的读取方法进行处理（将指定路径的excel文件读取成字符串）
     * 解析后缀为xls的Excel文件
     * @param ftpFilePO 需解析的文件对象
     * @param inputStream 需解析的文件输入流
     * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
     * @return OnlineDocumentInfoPO 完整的文件对象
     */
    private FtpFileVO parserXls(FtpFilePO ftpFilePO, InputStream inputStream, boolean isWithStyle)throws Exception{
        FtpFileVO ftpFileVO = null;
        try {
            HSSFWorkbook hssfWorkbook = (HSSFWorkbook) WorkbookFactory.create(inputStream);
            if (null != hssfWorkbook){
                ftpFileVO = getExcelInfo(ftpFilePO, hssfWorkbook, isWithStyle);
            }
        }finally {
            if (null != inputStream){
                inputStream.close();
            }
        }
        return ftpFileVO;
    }

    /**
     * 读取excel成string
     *
     * @param ftpFilePO  需解析的文件对象
     * @param workbook 工作簿对象
     * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
     * @return OnlineDocumentInfoPO  完整的文件对象
     */
    private FtpFileVO getExcelInfo(FtpFilePO ftpFilePO, Workbook workbook, boolean isWithStyle)throws Exception {
        String excelHtmlHead = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><table style='border-collapse:collapse;width:100%;'>";
        String excelTrAndTd = "<tr><td >  </td></tr>";
        String excelTd = "<td>  </td>";
        String excelTr = "<tr>";
        String excelImageStart = "0_";
        String imageLabelStart = "<img src='";
        String imageLabelMiddle = "' style='height:";
        String imageLabelEnd = "px;'>";
        String pictureSuffix = ".jpeg";
        String excelTdLabel = "<td rowspan= '";
        String excelTdLabel2 = "' colspan= '";
        String singleQuotes = "' ";
        String label1 = "<td ";
        String label2 = ">";
        String label3 = "</td>";
        String label4 = "</tr>";
        String label5 = "</table>";

        StringBuffer sbSource = new StringBuffer();
        StringBuffer sbContent = new StringBuffer();
        StringBuffer imageRowNum = new StringBuffer();
        StringBuffer imageHtml = new StringBuffer();
        StringBuffer imagePath = new StringBuffer();

        if (null != workbook){
            int maxSheetNum = 4;
            if(workbook.getNumberOfSheets() < maxSheetNum){
                maxSheetNum = workbook.getNumberOfSheets();
            }
            for (int i = 0; i < maxSheetNum; i++){
                //获取第一个Sheet的内容
                Sheet sheet = workbook.getSheetAt(i);
                if (null != sheet){
                    //map等待存储excel图片
                    Map<String,String> imageMap = new HashMap<>();
                    //存放新旧图片相互对应的集合
                    Map<String,String> renameImageMap = new HashMap<>();
                    Map<String, PictureData> sheetIndexPicMap = getSheetPictrues(i, sheet, workbook);
                    //临时保存位置，正式环境根据部署环境存放其他位置
                    if (null != sheetIndexPicMap) {
                        imageMap = printImg(sheetIndexPicMap);
                        for (Map.Entry<String, String> entry : imageMap.entrySet()){
                            renameImageMap.put(entry.getKey(), new StringBuffer().append(System.currentTimeMillis()).append(UUID.randomUUID().toString()).append(entry.getKey()).toString());
                        }
                    }
                    //上传图片到ftp服务器
                    uploadImageToFtp(imageMap, renameImageMap);
                    //记录合并单元格相关的参数
                    Map<String, Object>[] rowSpanColSpanMap = getRowSpanColSpanMap(sheet);
                    sbSource.append(excelHtmlHead);
                    //每一行
                    Row row = null;
                    //每一格
                    Cell cell = null;
                    int maxRowNum = 3000;//设置读取单张表读取的最大行数
                    if(sheet.getLastRowNum() < maxRowNum) {
                        maxRowNum = sheet.getLastRowNum();
                    }
                    for (int rowNum = sheet.getFirstRowNum(); rowNum <= maxRowNum; rowNum++) {
                        if (rowNum > 10000) {
                            break;
                        }
                        row = sheet.getRow(rowNum);
                        int lastColNum = 0;
                        int rowHeight = 0;
                        if (null != row) {
                            lastColNum = 20;//最大解析列数
                            if(row.getLastCellNum() < lastColNum) {
                                lastColNum = row.getLastCellNum();
                            }
                            rowHeight = row.getHeight();
                        }

                        if (null == row) {
                            sbSource.append(excelTrAndTd);
                            continue;
                        } else if (row.getZeroHeight()) {
                            continue;
                        } else if (0 == rowHeight) {
                            //针对jxl的隐藏行（此类隐藏行只是把高度设置为0，但getZeroHeight无法识别）
                            continue;
                        }
                        //拼接一行
                        sbSource.append(excelTr);
                        for (int colNum = 0; colNum <= lastColNum; colNum++) {
                            if (sheet.isColumnHidden(colNum)) {
                                continue;
                            }
                            imageRowNum.setLength(0);
                            imageRowNum.append(excelImageStart).append(rowNum).append(underline).append(colNum);
                            cell = row.getCell(colNum);
                            //特殊情况 空白的单元格会返回null
                            //判断该单元格是否包含图片，为空时也可能包含图片
                            if ((null != sheetIndexPicMap && !sheetIndexPicMap.containsKey(imageRowNum.toString()) || null == sheetIndexPicMap) && null == cell) {
                                sbSource.append(excelTd);
                                continue;
                            }
                            if (null != sheetIndexPicMap && sheetIndexPicMap.containsKey(imageRowNum.toString())) {
                                imagePath.setLength(0);
                                //获取图片格式
                                String ext = sheetIndexPicMap.get(imageRowNum.toString()).suggestFileExtension();
                                imagePath.append(pictureUrl).append(renameImageMap.get(imageRowNum.append(spot).append(ext).toString()));
                                imageHtml.setLength(0);
                                imageHtml.append(imageLabelStart).append(imagePath).append(imageLabelMiddle).append(rowHeight/20).append(imageLabelEnd);
                                sbSource.append(imageHtml);
                            }
                            String stringValue = "";
                            if (null != cell){
                                stringValue = getCellValue(cell);
                            }
                            if (null != rowSpanColSpanMap){
                                if (rowSpanColSpanMap[0].containsKey(new StringBuffer().append(rowNum).append(comma).append(colNum).toString())) {
                                    String pointString = (String) rowSpanColSpanMap[0].get(new StringBuffer().append(rowNum).append(comma).append(colNum).toString());
                                    int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
                                    int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
                                    int rowSpan = bottomeRow - rowNum + 1;
                                    int colSpan = bottomeCol - colNum + 1;
                                    if (rowSpanColSpanMap[2].containsKey(new StringBuffer().append(rowNum).append(comma).append(colNum).toString())) {
                                        rowSpan = rowSpan - (Integer) rowSpanColSpanMap[2].get(new StringBuffer().append(rowNum).append(comma).append(colNum).toString());
                                    }
                                    sbSource.append(excelTdLabel).append(rowSpan).append(excelTdLabel2).append(colSpan).append(singleQuotes);
                                    if (rowSpanColSpanMap.length > 3 && rowSpanColSpanMap[3].containsKey(new StringBuffer().append(rowNum).append(comma).append(colNum).toString())) {
                                        //此类数据首行被隐藏，value为空，需使用其他方式获取值
                                        stringValue = getMergedRegionValue(sheet, rowNum, colNum);
                                    }
                                } else if (rowSpanColSpanMap[1].containsKey(new StringBuffer(rowNum).append(comma).append(colNum).toString())) {
                                    rowSpanColSpanMap[1].remove(new StringBuffer(rowNum).append(comma).append(colNum).toString());
                                    continue;
                                } else {
                                    //add by yulang 解决Excel错位问题  会出现为空列错位现象
                                    if(" ".equals(stringValue)|| StringUtils.isEmpty(stringValue)){
                                        continue;
                                    }
                                    //end
                                    sbSource.append(label1);
                                }
                            }
                            sbContent.append(stringValue);
                            //判断是否需要样式
                            if (isWithStyle) {
                                if (null != cell){
                                    dealExcelStyle(workbook, sheet, cell, sbSource);//处理单元格样式
                                }
                            }
                            sbSource.append(label2);
                            if (null != sheetIndexPicMap && sheetIndexPicMap.containsKey(imageRowNum.toString())) {
                                sbSource.append(imageHtml);
                            }
                            if (null == stringValue || "".equals(stringValue.trim())) {
                                sbSource.append("   ");
                            } else {
                                // 将ascii码为160的空格转换为html下的空格（ ）
                                sbSource.append(stringValue.replace(String.valueOf((char) 160), " "));
                            }
                            sbSource.append(label3);
                        }
                        sbSource.append(label4);
                        continue;
                    }
                }
                sbSource.append(label5);
            }
        }
        //为解决excel解析后，打开html内容中无法识别script标签问题
        String sbSourceString = sbSource.toString().replace("<%2fscript>","</script>");
        List<String> stringList = getStringList(sbContent.toString(), 17500);
        return setFtpFileInfoVO(ftpFilePO, sbContent.toString());
    }

    /**
     * 保存excel图片到指定位置
     *
     * @param map
     */
    private Map<String, String> printImg(Map<String, PictureData> map) throws IOException {
        String tempFile = getTemplateFilePath();
        StringBuffer imgName = new StringBuffer();
        Map<String,String> imageMap = new HashMap<>();
        if (null != map){
            Object key[] = map.keySet().toArray();
            for (int i = 0; i < map.size(); i++) {
                // 获取图片流
                PictureData pictureData = map.get(key[i]);
                // 获取图片索引
                String picName = key[i].toString();
                FileOutputStream fileOutputStream = null;
                byte[] data = null;
                if (null != pictureData){
                    // 获取图片格式
                    String ext = pictureData.suggestFileExtension();
                    data = pictureData.getData();
                    imgName.setLength(0);
                    imgName.append(picName).append(spot).append(ext);
                    imageMap.put(imgName.toString(), tempFile);
                }
                try{
                    fileOutputStream = new FileOutputStream(tempFile + imgName);
                    fileOutputStream.write(data);
                    fileOutputStream.flush();
                }finally {
                    if (null != fileOutputStream){
                        fileOutputStream.close();
                    }
                }
            }
        }
        return imageMap;
    }

    /**
     * 分析excel表格，记录合并单元格相关的参数，用于之后html页面元素的合并操作
     *
     * @param sheet
     * @return
     */
    private Map<String, Object>[] getRowSpanColSpanMap(Sheet sheet) {

        //保存合并单元格的对应起始和截止单元格
        Map<String, String> startAndEndCellMap = new HashMap<>();
        //保存被合并的那些单元格
        Map<String, String> cellMergedMap = new HashMap<>();
        //记录被隐藏的单元格个数
        Map<String, Integer> cellNumHidenMap = new HashMap<>();
        //记录合并了单元格，但是合并的首行被隐藏的情况
        Map<String, String> firstRowCellHidenMap = new HashMap<>();

        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        Row row = null;
        StringBuffer firstRowCellHidenMapKey = new StringBuffer();
        StringBuffer firstRowCellHidenMapValue = new StringBuffer();
        StringBuffer cellNumHidenMapKey = new StringBuffer();
        StringBuffer startAndEndCellMapKey = new StringBuffer();
        StringBuffer startAndEndCellMapValue = new StringBuffer();
        StringBuffer cellMergedMapKey = new StringBuffer();
        StringBuffer cellMergedMapValue = new StringBuffer();

        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            //第一行和第一列
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            //最后一行和最后一列
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            //此类数据为合并了单元格的数据
            //处理隐藏（只处理行隐藏，列隐藏poi已经处理）
            if (topRow != bottomRow) {
                int zeroRoleNum = 0;
                int tempRow = topRow;
                for (int j = topRow; j <= bottomRow; j++) {
                    row = sheet.getRow(j);
                    if (row.getZeroHeight() || row.getHeight() == 0) {
                        if (j == tempRow) {
                            //首行就进行隐藏，将rowTop向后移
                            tempRow++;
                            //由于top下移，后面计算rowSpan时会扣除移走的列，所以不必增加zeroRoleNum;
                            continue;
                        }
                        zeroRoleNum++;
                    }
                }
                if (tempRow != topRow) {
                    firstRowCellHidenMapKey.setLength(0);
                    firstRowCellHidenMapValue.setLength(0);
                    firstRowCellHidenMap.put(firstRowCellHidenMapKey.append(tempRow).append(comma).append(topCol).toString(), firstRowCellHidenMapValue.append(topRow).append(comma).append(topCol).toString());
                    topRow = tempRow;
                }
                if (zeroRoleNum != 0) {
                    cellNumHidenMapKey.setLength(0);
                    cellNumHidenMap.put(cellNumHidenMapKey.append(topRow).append(comma).append(topCol).toString(), zeroRoleNum);
                }
            }
            startAndEndCellMapKey.setLength(0);
            startAndEndCellMapValue.setLength(0);
            startAndEndCellMap.put(startAndEndCellMapKey.append(topRow).append(comma).append(topCol).toString(), startAndEndCellMapValue.append(bottomRow).append(comma).append(bottomCol).toString());
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    cellMergedMapKey.setLength(0);
                    cellMergedMapValue.setLength(0);
                    cellMergedMap.put(cellMergedMapKey.append(tempRow).append(comma).append(tempCol).toString(), cellMergedMapValue.append(topRow).append(comma).append(topCol).toString());
                    tempCol++;
                }
                tempRow++;
            }
            cellMergedMapKey.setLength(0);
            cellMergedMap.remove(cellMergedMapKey.append(topRow).append(comma).append(topCol));
        }

        Map[] map = {startAndEndCellMap, cellMergedMap, cellNumHidenMap, firstRowCellHidenMap};
        return map;
    }

    /**
     * 处理表格样式
     *
     * @param wb
     * @param sheet
     * @param cell
     * @param sb
     */
    private void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell, StringBuffer sb) {

        String valign = "valign='";
        String singleMark = "' ";
        String style = "style='";
        String fontWeight = "font-weight:";
        String semicolon = ";";
        String percentage = "%;";
        String fontSize = "font-size: ";
        String px = "px;";
        String width = "width:";
        String textAlign = "text-align:";
        String color = "color:#";
        String backgroundColor = "background-color:#";

        CellStyle cellStyle = cell.getCellStyle();
        if (null != cellStyle) {
            short alignment = cellStyle.getAlignment();
            //单元格内容的水平对齐方式
            String  align = convertAlignToHtml(alignment);
            short verticalAlignment = cellStyle.getVerticalAlignment();
            //单元格中内容的垂直排列方式
            sb.append(valign).append(convertVerticalAlignToHtml(verticalAlignment)).append(singleMark);
            if (wb instanceof XSSFWorkbook) {
                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
                short boldWeight = xf.getBoldweight();
                sb.append(style);
                // 字体加粗
                sb.append(fontWeight).append(boldWeight).append(semicolon);
                // 字体大小
                sb.append(fontSize).append(xf.getFontHeight() / 2).append(percentage);
//                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
//                sb.append(width).append(columnWidth).append(px);//去掉宽度显示，add by yulang 如果列宽太大，则需要去掉此项
                //表头排版样式
                sb.append(textAlign).append(align).append(semicolon);
                XSSFColor xc = xf.getXSSFColor();
                if (null != xc) {
                    if(null != xc.getARGBHex()){
                        // 字体颜色
                        sb.append(color).append(xc.getARGBHex().substring(2)).append(semicolon);
                    }
                }
                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                if (null != bgColor) {
                    // 背景颜色
                    sb.append(backgroundColor).append(bgColor.getARGBHex().substring(2)).append(semicolon);
                }
                sb.append(getBorderStyle(0,cellStyle.getBorderTop(), ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
                sb.append(getBorderStyle(1,cellStyle.getBorderRight(), ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
                sb.append(getBorderStyle(2,cellStyle.getBorderBottom(), ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
                sb.append(getBorderStyle(3,cellStyle.getBorderLeft(), ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));
            }else if(wb instanceof HSSFWorkbook){
                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short boldWeight = hf.getBoldweight();
                short fontColor = hf.getColor();
                sb.append(style);
                // 类HSSFPalette用于取得颜色的国际标准形式
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
                HSSFColor hc = palette.getColor(fontColor);
                // 字体加粗
                sb.append(fontWeight).append(boldWeight).append(semicolon);
                // 字体大小
                sb.append(fontSize).append(hf.getFontHeight() / 2).append(percentage);
                //表头排版样式
                sb.append(textAlign).append(align).append(semicolon);
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                    // 字体颜色
                    sb.append(color).append(fontColorStr).append(semicolon);
                }
//                int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
//                sb.append(width).append(columnWidth).append(px);//去掉宽度显示，add by yulang 如果列宽太大，则需要去掉此项
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                    // 背景颜色
                    sb.append(backgroundColor).append(bgColorStr).append(semicolon);
                }
                sb.append( getBorderStyle(palette,0,cellStyle.getBorderTop(),cellStyle.getTopBorderColor()));
                sb.append( getBorderStyle(palette,1,cellStyle.getBorderRight(),cellStyle.getRightBorderColor()));
                sb.append( getBorderStyle(palette,3,cellStyle.getBorderLeft(),cellStyle.getLeftBorderColor()));
                sb.append( getBorderStyle(palette,2,cellStyle.getBorderBottom(),cellStyle.getBottomBorderColor()));
            }
            sb.append("' ");
        }
    }

    private String convertToStardColor(HSSFColor hssfColor) {

        StringBuffer sb = new StringBuffer("");
        if (null != hssfColor) {
            if (HSSFColor.AUTOMATIC.index == hssfColor.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hssfColor.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hssfColor.getTriplet()[i])));
            }
        }
        return sb.toString();
    }

    private String fillWithZero(String str) {
        if (null != str && str.length() < 2) {
            return new StringBuffer().append("0").append(str).toString();
        }
        return str;
    }

    /**
     * 获取边界样式
     *
     * @param palette 国际标准形式
     * @return String
     */
    private String getBorderStyle(HSSFPalette palette ,int b, short s, short t){

        if(s == 0){
            return new StringBuffer().append(bordesr[b]).append(borderStyles[s]).append(color).toString();
        }
        String borderColorStr = convertToStardColor(palette.getColor(t));
        borderColorStr = (null == borderColorStr || borderColorStr.length() < 1) ? "#000000" : borderColorStr;
        return new StringBuffer().append(bordesr[b]).append(borderStyles[s]).append(borderColorStr).append(size).toString();
    }

    private String getBorderStyle(int b, short s, XSSFColor xc){

        if(s == 0){
            return new StringBuffer().append(bordesr[b]).append(borderStyles[s]).append(color).toString();
        }
        if (null != xc) {
            String borderColorStr = xc.getARGBHex();
            borderColorStr = (null == borderColorStr || borderColorStr.length() < 1) ? "#000000" : borderColorStr.substring(2);
            return new StringBuffer().append(bordesr[b]).append(borderStyles[s]).append(borderColorStr).append(size).toString();
        }
        return "";
    }

    /**
     * 单元格内容的水平对齐方式
     *
     * @param alignment
     * @return String
     */
    private String convertAlignToHtml(short alignment) {

        String align = "left";
        switch (alignment) {
            case CellStyle.ALIGN_LEFT:
                align = "left";
                break;
            case CellStyle.ALIGN_CENTER:
                align = "center";
                break;
            case CellStyle.ALIGN_RIGHT:
                align = "right";
                break;
            default:
                break;
        }
        return align;
    }

    /**
     * 单元格中内容的垂直排列方式
     *
     * @param verticalAlignment
     * @return String
     */
    private String convertVerticalAlignToHtml(short verticalAlignment) {
        String valign = "middle";
        switch (verticalAlignment) {
            case CellStyle.VERTICAL_BOTTOM:
                valign = "bottom";
                break;
            case CellStyle.VERTICAL_CENTER:
                valign = "center";
                break;
            case CellStyle.VERTICAL_TOP:
                valign = "top";
                break;
            default:
                break;
        }
        return valign;
    }

    /**
     * 获取表格单元格Cell内容
     *
     * @param cell
     * @return String
     */
    private String getCellValue(Cell cell) {
        String result = "";
        if (null != cell){
            switch (cell.getCellType()) {
                // 数字类型
                case Cell.CELL_TYPE_NUMERIC:
                    // 处理日期格式、时间格式
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = null;
                        if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                            sdf = new SimpleDateFormat("HH:mm");
                        } else {
                            // 日期
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        Date date = cell.getDateCellValue();
                        result = sdf.format(date);
                    } else if (cell.getCellStyle().getDataFormat() == 58) {
                        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                        result = sdf.format(date);
                    } else {
                        double value = cell.getNumericCellValue();
                        CellStyle style = cell.getCellStyle();
                        DecimalFormat format = new DecimalFormat();
                        String temp = style.getDataFormatString();
                        // 单元格设置成常规
                        if ("General".equals(temp)) {
                            format.applyPattern("#");
                        }
                        result = format.format(value);
                    }
                    break;
                // String类型
                case Cell.CELL_TYPE_STRING:
                    result = cell.getRichStringCellValue().toString();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    result = "";
                    break;
                default:
                    result = "";
                    break;
            }
        }
        return result;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return String
     */
    private String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (null != cellRangeAddress){
                int firstColumn = cellRangeAddress.getFirstColumn();
                int lastColumn = cellRangeAddress.getLastColumn();
                int firstRow = cellRangeAddress.getFirstRow();
                int lastRow = cellRangeAddress.getLastRow();
                if (row >= firstRow && row <= lastRow) {
                    if (column >= firstColumn && column <= lastColumn) {
                        Row fRow = sheet.getRow(firstRow);
                        Cell fCell = fRow.getCell(firstColumn);
                        return getCellValue(fCell);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据excel的版本分配不同的读取方法进行处理（将指定路径的excel文件读取成字符串）
     * 解析后缀为xlsx的Excel文件
     * @param ftpFilePO 需解析的文件对象
     * @param inputStream 需解析的文件输入流
     * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
     * @return OnlineDocumentInfoPO 完整的文件对象
     */
    private FtpFileVO parserXlsx(FtpFilePO ftpFilePO, InputStream inputStream, boolean isWithStyle) throws Exception{

        FtpFileVO ftpFileVO = null;
        try {
            XSSFWorkbook xssfWorkbook = (XSSFWorkbook)WorkbookFactory.create(inputStream);
            if (null != xssfWorkbook){
                ftpFileVO = getExcelInfo(ftpFilePO, xssfWorkbook, isWithStyle);
            }
        }finally {
            if (null != inputStream){
                inputStream.close();
            }
        }
        return ftpFileVO;
    }

    /**
     * 将PPT文件转换成image
     * @param ftpFilePO  PPT文件路径 如：d:/demo/demo1.ppt
     * @param imageFormatName 图片转化的格式字符串 ，如："jpg"、"jpeg"、"bmp" "png" "gif" "tiff"
     * @return OnlineDocumentInfoPO
     *  注：获取“imgNames”图片名称集合时，请先判断“converReturnResult” 是否为true；如果有一张转换失败则为false
     */
    private FtpFileVO parserPPT(FtpFilePO ftpFilePO, InputStream inputStream, String imageFormatName)throws Exception{
        String tempFile = getTemplateFilePath();
        //PPT转成图片后所有名称集合
        FileOutputStream outputStream = null;
        SlideShow slideShow = null;
        //ppt文件转化html结构资源
        StringBuffer pptSource = new StringBuffer();
        //ppt的内容
        StringBuffer pptContent = new StringBuffer();
        StringBuffer imghtml = new StringBuffer();
        StringBuffer imgName = new StringBuffer();
        StringBuffer imgs = new StringBuffer();
        try{
            slideShow = new SlideShow(inputStream);
            //获取PPT每页的大小（宽和高度）
            Dimension onePPTPageSize = slideShow.getPageSize();
            //获取PPT文件中的所有的PPT页面（获得每一张幻灯片）,并转为一张张的播放片
            Slide[] slides = slideShow.getSlides();
            Map<String,String> imageMap = new HashMap<>();
            Map<String,String> renameImageMap = new HashMap<>();
            if (null != slides && slides.length > 0){
                for (int i = 0; i < slides.length; i++){
                    //这几个循环是设置字体为宋体，防止中文乱码，
                    TextRun[] textRuns = slides[i].getTextRuns();
                    for (int j = 0; j < textRuns.length; j++){
                        RichTextRun[] richTextRuns = textRuns[j].getRichTextRuns();
                        String contentValue = textRuns[j].getText();
                        pptContent.append(contentValue);
                        for (int k = 0; k < richTextRuns.length; k++){
                            richTextRuns[k].setFontIndex(1);
                            richTextRuns[k].setFontName("宋体");
                        }
                    }
                    //创建BufferedImage对象，图像的尺寸为原来的每页的尺寸
                    BufferedImage oneBufferedImage = new BufferedImage(onePPTPageSize.width, onePPTPageSize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D oneGraphics2D = oneBufferedImage.createGraphics();
                    //设置转换后的图片背景色为白色
                    oneGraphics2D.setPaint(java.awt.Color.white);
                    oneGraphics2D.fill(new Rectangle2D.Float(0, 0, onePPTPageSize.width, onePPTPageSize.height));
                    try{
                        slides[i].draw(oneGraphics2D);
                    }catch (Exception e){
                        logger.error(e.getMessage());
                    }
                    //设置图片的存放路径和图片格式，注意生成的图片路径为绝对路径，最终获得各个图像文件所对应的输出流对象
                    imgName.setLength(0);
                    imgName.append(i + 1).append(underline).append(UUID.randomUUID().toString()).append(spot).append(imageFormatName);
                    //将图片名称添加的集合中
                    outputStream = new FileOutputStream(tempFile + imgName);
                    //转换后的图片文件保存的指定的目录中
                    ImageIO.write(oneBufferedImage, imageFormatName, outputStream);
                    imageMap.put(imgName.toString(),tempFile);
                    renameImageMap.put(imgName.toString(), imgName.toString());
                    //图片在html加载路径
                    imgs.setLength(0);
                    imgs.append(imgHead).append(pictureUrl).append(imgName.toString()).append(style);
                    imghtml.append(imgs);
                }
            }
            //上传本地图片到ftp服务器
            uploadImageToFtp(imageMap, renameImageMap);

            pptSource.append(pptHtmlHead).append(imghtml.toString()).append(pptHtmlEnd);
            List<String> stringList = getStringList(pptContent.toString(), 17500);
            return setFtpFileInfoVO(ftpFilePO, pptContent.toString());
        }finally{
            if(null != outputStream){
                outputStream.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
        }
    }

    /**
     * 将PPTX 文件转换成image
     * @param ftpFilePO  PPTX文件路径 如：d:/demo/demo1.pptx
     * @param imageFormatName 图片转化的格式字符串 ，如："jpg"、"jpeg"、"bmp" "png" "gif" "tiff"
     * @return OnlineDocumentInfoPO
     *  注：获取“imgNames”图片名称集合时，请先判断“converReturnResult” 是否为true；如果有一张转换失败则为false
     */
    private FtpFileVO parserPPTX(FtpFilePO ftpFilePO, InputStream inputStream,String imageFormatName) throws Exception{
        String tempFile = getTemplateFilePath();
        FileOutputStream outputStream = null;
        XMLSlideShow oneSlideShow = null;
        StringBuffer imgName = new StringBuffer();
        StringBuffer xmlFontFormat = new StringBuffer();
        StringBuffer imghtml = new StringBuffer();
        StringBuffer pptSource = new StringBuffer();
        StringBuffer imgs = new StringBuffer();
        XSLFPowerPointExtractor xslfPowerPointExtractor = null;
        try {
            oneSlideShow = new XMLSlideShow(inputStream);
            //获取PPT每页的尺寸大小（宽和高度）
            Dimension onePPTPageSize = oneSlideShow.getPageSize();
            //获取PPT文件中的所有PPT页面，并转换为一张张播放片
            List<XSLFSlide> XSLFSlideList = Arrays.asList(oneSlideShow.getSlides());
            xmlFontFormat.append("<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">").append("<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> ").append("<a:latin typeface=\"+mj-ea\"/> ").append("</a:rPr>").append("</xml-fragment>");
            Map<String,String> imageMap = new HashMap<>();
            Map<String,String> renameImageMap = new HashMap<>();
            for (int i = 0; i < XSLFSlideList.size(); i++) {
                //设置中文为宋体，解决中文乱码问题
                CTSlide ctSlide = XSLFSlideList.get(i).getXmlObject();
                CTGroupShape ctGroupShape = ctSlide.getCSld().getSpTree();
                List<CTShape> ctShapeList = ctGroupShape.getSpList();
                for (CTShape ctShape : ctShapeList) {
                    CTTextBody oneCTTextBody = ctShape.getTxBody();
                    if (null == oneCTTextBody) {
                        continue;
                    }
                    List<CTTextParagraph> ctTextParagraphList = oneCTTextBody.getPList();
                    CTTextFont oneCTTextFont = null;
                    oneCTTextFont = CTTextFont.Factory.parse(xmlFontFormat.toString());
                    for (CTTextParagraph ctTextParagraph : ctTextParagraphList) {
                        List<CTRegularTextRun> ctRegularTextRunList = ctTextParagraph.getRList();
                        for (CTRegularTextRun ctRegularTextRun : ctRegularTextRunList) {
                            CTTextCharacterProperties oneCTTextCharacterProperties = ctRegularTextRun.getRPr();
                            oneCTTextCharacterProperties.setLatin(oneCTTextFont);
                        }
                    }
                }
                //创建BufferedImage对象，图像尺寸为原来的PPT的每页尺寸
                BufferedImage oneBufferedImage = new BufferedImage(onePPTPageSize.width, onePPTPageSize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D oneGraphics2D = oneBufferedImage.createGraphics();
                try{
                    //将PPT文件中的每个页面中的相关内容画到转换后的图片中
                    XSLFSlideList.get(i).draw(oneGraphics2D);
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
                //设置图片的存放路径和图片格式，注意生成的文件路径为绝对路径，最终获得各个图像文件所对应的输出流的对象
                imgName.setLength(0);
                imgName.append(i + 1).append(underline).append(UUID.randomUUID().toString()).append(spot).append(imageFormatName);
                //将图片名称添加的集合中
                outputStream = new FileOutputStream(tempFile + imgName);
                //转换后的图片文件保存的指定的目录中
                ImageIO.write(oneBufferedImage, imageFormatName, outputStream);
                imageMap.put(imgName.toString(), tempFile);
                renameImageMap.put(imgName.toString(), imgName.toString());
                //图片在html加载路径
                imgs.setLength(0);
                imgs.append(imgHead).append(pictureUrl).append(imgName.toString()).append(style);
                imghtml.append(imgs);
            }
            //上传本地图片到ftp服务器
            uploadImageToFtp(imageMap, renameImageMap);
            pptSource.append(pptHtmlHead).append(imghtml.toString()).append(pptHtmlEnd);
            // 读取PPTX的内容
            xslfPowerPointExtractor = new XSLFPowerPointExtractor(oneSlideShow);
            String pptContent = xslfPowerPointExtractor.getText();
            List<String> stringList = getStringList(pptContent, 17500);
            return setFtpFileInfoVO(ftpFilePO, pptContent);
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != xslfPowerPointExtractor){
                xslfPowerPointExtractor.close();
            }
        }
    }
}
