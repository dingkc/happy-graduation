package com.bttc.HappyGraduation.business.friend.pojo.vo;

import java.util.Date;

/**
 * @author Dk
 * @date 17:16 2019/4/22.
 */
public class RequestApproveHistoryVO {

    private Integer requestApproveHistoryId;
    private Integer targetId;
    private Integer sourceId;
    private String sourceName;
    private String message;
    private Date createDate;
    private Integer creatorId;
    private Date doneDate;
    private Integer operatorId;
    private Integer status;
    private Integer state;

    public Integer getRequestApproveHistoryId() {
        return requestApproveHistoryId;
    }

    public void setRequestApproveHistoryId(Integer requestApproveHistoryId) {
        this.requestApproveHistoryId = requestApproveHistoryId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
