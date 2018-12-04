package com.bttc.HappyGraduation.user.pojo.vo;

/**
 * Created by yuqh3 on 2018/7/5.
 */
public class VerifyCodeRecordVO {

    private String mailAccount;

    private Integer verifyCodeType;//验证码类型：1注册用户2修改密码

    public Integer getVerifyCodeType() {
        return verifyCodeType;
    }

    public void setVerifyCodeType(Integer verifyCodeType) {
        this.verifyCodeType = verifyCodeType;
    }

    public String getMailAccount() {
        return mailAccount;
    }

    public void setMailAccount(String mailAccount) {
        this.mailAccount = mailAccount;
    }
}
