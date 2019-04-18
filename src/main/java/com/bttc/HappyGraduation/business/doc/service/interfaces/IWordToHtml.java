package com.bttc.HappyGraduation.business.doc.service.interfaces;

/**
 * @author DK
 * @create 2018-06-22 9:04
 */

public interface IWordToHtml {
    /**
     * 读取 word 2003 版本文件 .doc格式
     * <p>
     * 使用：tm-extractors-0.4.jar 文件，只可以读取 word2003 版的文件 如果你的word文档使用 wps程序
     * 保存过，会出现以下错误，只需要再用 word程序 打开保存一下就可以解决
     * org.textmining.text.extraction.FastSavedException: Fast-saved files are
     * unsupported at this time
     *
     * @param filePath
     * @return
     */
     String readWordFile2003(String filePath) throws Exception;

    /**
     * 读取 word 2007 以上版本文件 .docx格式
     * <p>
     * 上面的方法只可读取 word2003版本的文件，无法读取word2007及以上版本的文档， 要想读取word2007
     * word2010版本的文档必须使用apache的poi开源项目包，下载地址：
     * http://www.apache.org/dyn/closer.cgi/poi/release/bin/poi-bin-3.9-20121203.tar.gz
     * <p>
     * 使用到的 jar 包
     * poi-3.9-20121203.jar poi-ooxml-3.9-20121203.jar
     * poi-ooxml-schemas-3.9-20121203.jar poi-scratchpad-3.9-20121203.jar
     * xmlbeans-2.3.0.jar dom4j-1.6.1.jar
     *
     * @param filePath
     * @return
     */
    public String readWordFile2007(String filePath) throws Exception;
}