package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.doc.pojo.vo.WordVO;
import com.bttc.HappyGraduation.doc.service.interfaces.IWordToHtml;
import com.bttc.HappyGraduation.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class BusinessController {

    @Autowired
    private IWordToHtml iWordToHtml;

    /**
     * 读取 word 2003 版本文件 .doc格式
     * 使用：tm-extractors-0.4.jar 文件，只可以读取 word2003 版的文件 如果你的word文档使用 wps程序
     * 保存过，会出现以下错误，只需要再用 word程序 打开保存一下就可以解决
     * org.textmining.text.extraction.FastSavedException: Fast-saved files are
     * unsupported at this time
     *
     * @param wordVO
     * @return
     */
//    @PostMapping(value = "/changeWord2003")
//    public ResultBean wordToHtml(@RequestBody WordVO wordVO) {
//        return ResultBean.ok(iWordToHtml.readWordFile2003(wordVO));
//    }

    /**
     * 2007版本word转换成html
     * 读取 word 2007 以上版本文件 .docx格式
     * 上面的方法只可读取 word2003版本的文件，无法读取word2007及以上版本的文档，要想读取word2007
     * word2010版本的文档必须使用apache的poi开源项目包，下载地址：www.baidu.com
     *
     * @param wordVO
     * @return
     */
//    @PostMapping(value = "/changeWord2007")
//    public ResultBean wordToHtml(@RequestBody WordVO wordVO) {
//        return ResultBean.ok(iWordToHtml.readWordFile2007(wordVO));
//    }
}
