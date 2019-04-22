package com.bttc.HappyGraduation.business.friend.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Dk
 * @date 16:54 2019/4/22.
 */
public class FriendRecordVO {

    private Integer friendRecordId;
    private Integer ownerId;
    private Integer owenrName;
    private Integer friendId;
    private Integer friendName;
    private String remark;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private Integer creatorId;
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date doneDate;
    private Integer opteratorId;
    private Integer state;

    public Integer getFriendRecordId() {
        return friendRecordId;
    }

    public void setFriendRecordId(Integer friendRecordId) {
        this.friendRecordId = friendRecordId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOwenrName() {
        return owenrName;
    }

    public void setOwenrName(Integer owenrName) {
        this.owenrName = owenrName;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getFriendName() {
        return friendName;
    }

    public void setFriendName(Integer friendName) {
        this.friendName = friendName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getOpteratorId() {
        return opteratorId;
    }

    public void setOpteratorId(Integer opteratorId) {
        this.opteratorId = opteratorId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
