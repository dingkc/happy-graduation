package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.doc.service.interfaces.IWordToHtml;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.FtpFileVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IFtpFileSV;
import com.bttc.HappyGraduation.common.ResultBean;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
public class FileController {

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

    @Autowired
    private IFtpFileSV iFtpFileSV;

    @PostMapping(value = "${apiVersion1}/ftpFiles/uploads")
    public ResultBean uploadFile(@RequestParam MultipartFile file, @RequestParam(required = false) Integer parentFileId) throws Exception {
        iFtpFileSV.uploadFile(file, parentFileId);
        return ResultBean.ok(null);
    }

    @GetMapping(value = "${apiVersion1}/ftpFiles/downloads")
    public ResultBean downloadFile(@RequestParam String fileUuidName, @RequestParam String fileName, HttpServletResponse response) throws Exception {
        iFtpFileSV.downloadFile(fileUuidName, fileName, response);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title: previewFile</p>
     * <p>Description: 文件预览接口</p>
     * @Author: Dk
     * @param ftpFileId : 文件编号
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/2 20:58
     **/
    @PostMapping(value = "${apiVersion1}/ftpFiles/{ftpFileId}/previews")
    public ResultBean previewFile(@PathVariable Integer ftpFileId) {
        return ResultBean.ok(iFtpFileSV.previewFile(ftpFileId));
    }

    /**
     * <p>Title: deleteFile</p>
     * <p>Description: 删除文件接口</p>
     * @Author: Dk
     * @param ftpFileId : 文件编号
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/5 20:50
     **/
    @DeleteMapping(value = "${apiVersion1}/ftpFiles/{ftpFileId}")
    public ResultBean deleteFile(@PathVariable Integer ftpFileId) throws BusinessException {
        iFtpFileSV.deleteFile(ftpFileId);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title: fileDetails</p>
     * <p>Description: 查询文件详情</p>
     * @Author: Dk
     * @param ftpFileId : 文件编号
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/5 20:52
     **/
    @GetMapping(value = "${apiVersion1}/ftpFiles/{ftpFileId}")
    public ResultBean fileDetails(@PathVariable Integer ftpFileId) {
        return ResultBean.ok(iFtpFileSV.queryFileByFileId(ftpFileId));
    }

    /**
     * <p>Title: queryFileByConditions</p>
     * <p>Description: 多条件查询文件</p>
     * @Author: Dk
     * @param parentFileId : 父文件编号
     * @param fileType : 文件类型
     * @param pageNumber : 分页页码
     * @param pageSize : 分页大小
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/5 21:01
     **/
    @GetMapping(value = "${apiVersion1}/ftpFiles")
    public ResultBean queryFileByConditions(@RequestParam(required = false) Integer parentFileId, @RequestParam(required = false) String fileType,
                                            @RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) String fileName) throws BusinessException {
        return ResultBean.ok(iFtpFileSV.queryFileByConditions(parentFileId, fileType, pageNumber, pageSize, fileName));
    }

    /**
     * <p>Title: addDir</p>
     * <p>Description: 新建文件夹</p>
     * @Author: Dk
     * @param ftpFileVO : 文件信息
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/23 9:15
     **/
    @PostMapping(value = "${apiVersion1}/ftpFiles/dirs")
    public ResultBean addDir(@RequestBody FtpFileVO ftpFileVO) throws BusinessException {
        iFtpFileSV.addDir(ftpFileVO);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title: updateFtpFiles</p>
     * <p>Description: 移动文件或重命名文件</p>
     * @Author: Dk
     * @param ftpFileId : 文件编号
     * @param ftpFileVO : 文件信息
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/23 9:52
     **/
    @PutMapping(value = "${apiVersion1}/ftpFiles/{ftpFileId}")
    public ResultBean updateFtpFiles(@PathVariable Integer ftpFileId, @RequestBody FtpFileVO ftpFileVO) throws BusinessException {
        iFtpFileSV.updateFtpFile(ftpFileId, ftpFileVO);
        return ResultBean.ok(null);
    }

}
