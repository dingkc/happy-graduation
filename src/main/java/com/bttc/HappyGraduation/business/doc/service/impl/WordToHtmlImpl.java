package com.bttc.HappyGraduation.business.doc.service.impl;

import com.bttc.HappyGraduation.business.doc.service.interfaces.IWordToHtml;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


/**
 * this a main class
 *
 * @author DK
 * @create 2018-06-04 18:57
 */


@Service
public class WordToHtmlImpl implements IWordToHtml {


    /**
     * 2007版本word转换成html
     * 读取 word 2007 以上版本文件 .docx格式
     * 上面的方法只可读取 word2003版本的文件，无法读取word2007及以上版本的文档，要想读取word2007
     * word2010版本的文档必须使用apache的poi开源项目包，下载地址：
     * www.baidu.com
     * @throws IOException
     */
//    @Test
    @Override
    public String readWordFile2007(String path) throws Exception {
        String content = "";
        String filepath = "C:/Users/DK/Desktop/Result/";
        String imagepath = "C:/Users/DK/Desktop/Result/image/";
        File file1 = new File(path);
        String fileName = file1.getName();
        String[] split = fileName.split("[.]");
        String htmlName = split[0].concat(".html");
//        String fileName = "测试B.docx";
//        String htmlName = "测试B.html";
        final String file = filepath + fileName;
        File f = new File(file);
        if (!f.exists()) {
            System.out.println("Sorry File does not Exists!");
        } else {
            if (f.getName().toLowerCase().endsWith(".docx")) {
                // 1) 加载word文档生成 XWPFDocument对象
                InputStream in = new FileInputStream(f);
                XWPFDocument document = new XWPFDocument(in);
                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
                File imageFolderFile = new File(imagepath);
                XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
                options.setExtractor(new FileImageExtractor(imageFolderFile));
                options.setIgnoreStylesIfUnused(false);
                options.setFragment(true);
                // 3) 将 XWPFDocument转换成XHTML
                OutputStream out = new FileOutputStream(new File(filepath + htmlName));
                XHTMLConverter.getInstance().convert(document, out, options);
                //也可以使用字符数组流获取解析的内容
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                XHTMLConverter.getInstance().convert(document, baos, options);
                content = baos.toString();
                System.out.println(content);
                baos.close();
            } else {
                System.out.println("Enter only MS Office 2007+ files");
            }
        }
        return content;
    }

    /**
     * 读取 word 2003 版本文件 .doc格式
     * <p>
     * 使用：tm-extractors-0.4.jar 文件，只可以读取 word2003 版的文件 如果你的word文档使用 wps程序
     * 保存过，会出现以下错误，只需要再用 word程序 打开保存一下就可以解决
     * org.textmining.text.extraction.FastSavedException: Fast-saved files are
     * unsupported at this time
     *
     * @throws IOException
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
//    @Test
    @Override
    public String readWordFile2003(String path) throws IOException, TransformerException, ParserConfigurationException {
        String filepath = "C:/Users/DK/Desktop/Result/";
        final String imagepath = "C:/Users/DK/Desktop/Result/image/";
        File file1 = new File(path);
        String fileName = file1.getName();
        String[] split = fileName.split("[.]");
        String htmlName = split[0].concat(".html");
//        String fileName = "测试A.business";
//        String htmlName = "测试A.html";
        final String fileAP = filepath + fileName;
        InputStream input = new FileInputStream(new File(fileAP));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                File imgPath = new File(imagepath);
                if (!imgPath.exists()) {
                    //图片目录不存在则创建
                    imgPath.mkdir();
                }
                File file = new File(imagepath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imagepath + suggestedName;
            }
        });

        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
//
//        File htmlFile = new File(filepath + htmlName);
//        OutputStream outStream = new FileOutputStream(htmlFile);

        //也可以使用字符数组流获取解析的内容
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                OutputStream outStream = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");

        serializer.transform(domSource, streamResult);

        //也可以使用字符数组流获取解析的内容
                String content = baos.toString();
                System.out.println(content);
                baos.close();
        outStream.close();
        return content;
    }

    /**
     * 读取txt文件
     */


    /**
     * 读取rtf文件
     */

}