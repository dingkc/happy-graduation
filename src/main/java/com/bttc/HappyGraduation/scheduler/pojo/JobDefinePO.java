package com.bttc.HappyGraduation.scheduler.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jiajt on 2018/11/1.
 */
@Entity
@Table(name = "job_define")
public class JobDefinePO {

    @Id
    private Long jobDefineId;

    private String jobDefineName;

    private String jobDefineGroup;

    private String jobDefineType;

    private Integer excuteState;

    private Integer state;

    private String cronExpression;

    private String className;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date doneDate;

    private Integer operatorId;

    private String operatorName;

    private String remark;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
