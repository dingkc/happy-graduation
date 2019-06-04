package com.bttc.HappyGraduation.business.note.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dk
 * @date 22:49 2019/3/25.
 */
@Entity
@Table(name = "notepad")
@DynamicUpdate
public class NotepadPO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition="int(10) COMMENT '记事本编号'")
    private Integer notepadId;

    @Column(columnDefinition="varchar(255) COMMENT '记事本名称'", nullable=false)
    private String notepadName;

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

    public Integer getNotepadId() {
        return notepadId;
    }

    public void setNotepadId(Integer notepadId) {
        this.notepadId = notepadId;
    }

    public String getNotepadName() {
        return notepadName;
    }

    public void setNotepadName(String notepadName) {
        this.notepadName = notepadName;
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
