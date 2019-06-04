package com.bttc.HappyGraduation.email.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Dk
 * @Date: 2019/3/25 22:39
 **/
@Entity
@Table(name="verify_code_record")
@DynamicUpdate
public class VerifyCodeRecordPO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition="int(10) COMMENT '验证码编号'")
    private Integer verifyCodeId;

    @Column(columnDefinition="varchar(255) COMMENT '验证码'", nullable=false)
    private String verifyCode;

    @Column(columnDefinition="varchar(255) COMMENT '邮箱账号'", nullable=false)
    private String mailAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '创建时间'", nullable=false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime COMMENT '失效时间'", nullable=false)
    private Date expireDate;


    public Integer getVerifyCodeId() {
        return verifyCodeId;
    }

    public void setVerifyCodeId(Integer verifyCodeId) {
        this.verifyCodeId = verifyCodeId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getMailAccount() {
        return mailAccount;
    }

    public void setMailAccount(String mailAccount) {
        this.mailAccount = mailAccount;
    }
}
