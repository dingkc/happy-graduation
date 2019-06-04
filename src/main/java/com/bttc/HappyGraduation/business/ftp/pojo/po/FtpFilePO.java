package com.bttc.HappyGraduation.business.ftp.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dk
 * @date 22:30 2019/3/25.
 */
@Entity
@Table(name = "ftp_file")
@DynamicUpdate
public class FtpFilePO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition="int(10) COMMENT '用户编号'")
    private Integer ftpFileId;

    @Column(columnDefinition="varchar(255) COMMENT '文件名称'", nullable=false)
    private String fileName;

    @Column(columnDefinition="varchar(255) COMMENT '文件随机名称'", nullable=false)
    private String fileUuidName;

    @Column(columnDefinition="varchar(255) COMMENT '文件类型'", nullable=false)
    private String fileType;

    @Column(columnDefinition="bigint(10) COMMENT '文件大小'", nullable=false)
    private Long fileSize;

    @Column(columnDefinition="varchar(255) COMMENT '文件单位大小'", nullable=false)
    private String fileUnitSize;

    @Column(columnDefinition="varchar(255) COMMENT '文件路径'", nullable=false)
    private String filePath;

    @Column(columnDefinition="int(10) COMMENT '父文件编号'")
    private Integer parentFileId;

    @Column(columnDefinition="LONGTEXT COMMENT '文件预览/xml'")
    private String filePreview;

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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getFileUnitSize() {
        return fileUnitSize;
    }

    public void setFileUnitSize(String fileUnitSize) {
        this.fileUnitSize = fileUnitSize;
    }
}
