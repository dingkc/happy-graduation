package com.bttc.HappyGraduation.business.friend.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dk
 * @date 16:37 2019/4/22.
 */
@Entity
@Table(name = "friend_record")
@DynamicUpdate
public class FriendRecordPO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition="int(10) COMMENT '主键'", nullable=false)
    private Integer friendRecordId;

    @Column(columnDefinition="int(10) COMMENT '拥有者id'", nullable=false)
    private Integer ownerId;

    @Column(columnDefinition="int(10) COMMENT '好友id'", nullable=false)
    private Integer friendId;

    @Column(columnDefinition="varchar(500) COMMENT '备注'")
    private String remark;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '创建时间'", nullable=false)
    private Date createDate;

    @Column(columnDefinition="int(10) COMMENT '创建人id'", nullable=false)
    private Integer creatorId;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '操作时间'", nullable=false)
    private Date doneDate;

    @Column(columnDefinition="int(10) COMMENT '操作人id'", nullable=false)
    private Integer operatorId;

    @Column(columnDefinition="int(1) COMMENT '数据状态0失效1生效'", nullable=false)
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

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
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
