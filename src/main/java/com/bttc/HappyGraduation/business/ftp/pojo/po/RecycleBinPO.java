package com.bttc.HappyGraduation.business.ftp.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dk
 * @date 22:44 2019/3/25.
 */
@Entity
@Table(name = "recycle_bin")
@DynamicUpdate
public class RecycleBinPO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition="int(10) COMMENT '回收站文件编号'")
    private Integer recycleBinId;

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
    @Column(columnDefinition = "datetime COMMENT '删除时间'", nullable=false)
    private Date doneDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '到期时间'", nullable=false)
    private Date expireDate;

    @Column(columnDefinition="int(10) COMMENT '删除人id'")
    private Integer operatorId;

    @Column(columnDefinition="int(1) COMMENT '数据状态0失效1生效'", nullable=false)
    private Integer state;

    public Integer getRecycleBinId() {
        return recycleBinId;
    }

    public void setRecycleBinId(Integer recycleBinId) {
        this.recycleBinId = recycleBinId;
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
