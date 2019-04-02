package com.bttc.HappyGraduation.business.ftp.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dk
 * @date 22:30 2019/3/25.
 */
public class FtpFileVO {

    private Integer ftpFileId;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String filePath;
    private Integer parentFileId;
    private String filePreview;
    private Date createDate;
    private Integer creatorId;
    private Date doneDate;
    private Integer operatorId;
    private MultipartFile file;

    public Integer getFtpFileId() {
        return ftpFileId;
    }

    public void setFtpFileId(Integer ftpFileId) {
        this.ftpFileId = ftpFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getParentFileId() {
        return parentFileId;
    }

    public void setParentFileId(Integer parentFileId) {
        this.parentFileId = parentFileId;
    }

    public String getFilePreview() {
        return filePreview;
    }

    public void setFilePreview(String filePreview) {
        this.filePreview = filePreview;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
