package com.bttc.HappyGraduation.business.ftp.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dk
 * @date 22:44 2019/3/25.
 */
public class RecycleBinVO {
    private Integer recycleBinId;
    private Integer ftpFileId;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String filePath;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date doneDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expireDate;
    private Integer operatorId;
    private Integer state;

    public Integer getRecycleBinId() {
        return recycleBinId;
    }

    public void setRecycleBinId(Integer recycleBinId) {
        this.recycleBinId = recycleBinId;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}