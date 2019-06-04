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
    private String fileUuidName;
    private String fileType;
    private String fileSize;
    private String fileUnitSize;
    private String filePath;
    private Integer parentFileId;
    private Integer newParentFileId;
    private String filePreview;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private Integer creatorId;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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

    public String getFileUuidName() {
        return fileUuidName;
    }

    public void setFileUuidName(String fileUuidName) {
        this.fileUuidName = fileUuidName;
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

    public String getFileUnitSize() {
        return fileUnitSize;
    }

    public void setFileUnitSize(String fileUnitSize) {
        this.fileUnitSize = fileUnitSize;
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

    public Integer getNewParentFileId() {
        return newParentFileId;
    }

    public void setNewParentFileId(Integer newParentFileId) {
        this.newParentFileId = newParentFileId;
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
