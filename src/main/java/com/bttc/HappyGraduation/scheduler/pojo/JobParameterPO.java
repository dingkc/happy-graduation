package com.bttc.HappyGraduation.scheduler.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jiajt on 2018/10/29.
 */
@Entity
@Table(name = "job_parameter")
public class JobParameterPO {
    @Id
    private Long jobParameterId;
    private Long jobDefineId;
    private String paramKey;
    private String paramValue;
    private Integer state;
    private String remark;

    public Long getJobParameterId() {
        return jobParameterId;
    }

    public void setJobParameterId(Long jobParameterId) {
        this.jobParameterId = jobParameterId;
    }

    public Long getJobDefineId() {
        return jobDefineId;
    }

    public void setJobDefineId(Long jobDefineId) {
        this.jobDefineId = jobDefineId;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
