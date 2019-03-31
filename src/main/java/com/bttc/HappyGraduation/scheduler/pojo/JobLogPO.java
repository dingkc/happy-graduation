package com.bttc.HappyGraduation.scheduler.pojo;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by jiajt on 2018/11/1.
 */
@Entity
@Table(name = "job_log")
public class JobLogPO {

    @Id
    private Long jobLogId;

    private Long jobDefineId;

    private String jobDefineName;

    private String jobDefineGroup;

    private String jobDefineType;

    private Integer excuteState;

    private String excuteMessage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private Long costTime;

    public Long getJobLogId() {
        return jobLogId;
    }

    public void setJobLogId(Long jobLogId) {
        this.jobLogId = jobLogId;
    }

    public Long getJobDefineId() {
        return jobDefineId;
    }

    public void setJobDefineId(Long jobDefineId) {
        this.jobDefineId = jobDefineId;
    }

    public String getJobDefineName() {
        return jobDefineName;
    }

    public void setJobDefineName(String jobDefineName) {
        this.jobDefineName = jobDefineName;
    }

    public String getJobDefineGroup() {
        return jobDefineGroup;
    }

    public void setJobDefineGroup(String jobDefineGroup) {
        this.jobDefineGroup = jobDefineGroup;
    }

    public String getJobDefineType() {
        return jobDefineType;
    }

    public void setJobDefineType(String jobDefineType) {
        this.jobDefineType = jobDefineType;
    }

    public Integer getExcuteState() {
        return excuteState;
    }

    public void setExcuteState(Integer excuteState) {
        this.excuteState = excuteState;
    }

    public String getExcuteMessage() {
        return excuteMessage;
    }

    public void setExcuteMessage(String excuteMessage) {
        this.excuteMessage = excuteMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }
}
