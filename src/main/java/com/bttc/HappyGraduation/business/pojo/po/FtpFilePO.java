package com.bttc.HappyGraduation.business.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dk
 * @date 22:30 2019/3/25.
 */
@Entity
@Table(name = "ftp_file")
public class FtpFilePO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition="int(10) COMMENT '用户编号'")
    private Integer ftpFileId;

    @Column(columnDefinition="varchar(255) COMMENT '文件名称'", nullable=false)
    private String fileName;

    @Column(columnDefinition="varchar(255) COMMENT '文件类型'", nullable=false)
    private String fileType;

    @Column(columnDefinition="varchar(255) COMMENT '文件大小'", nullable=false)
    private String fileSize;

    @Column(columnDefinition="varchar(255) COMMENT '文件路径'", nullable=false)
    private String filePath;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '创建时间'", nullable=false)
    private Date createDate;

    @Column(columnDefinition="int(10) COMMENT '创建人id'")
    private Integer creatorId;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '操作时间'", nullable=false)
    private Date doneDate;

    @Column(columnDefinition="int(10) COMMENT '操作人id'")
    private Integer operatorId;

    @Column(columnDefinition="int(1) COMMENT '数据状态0失效1生效'", nullable=false)
    private Integer state;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}