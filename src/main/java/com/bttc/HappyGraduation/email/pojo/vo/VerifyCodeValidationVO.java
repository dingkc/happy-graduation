package com.bttc.HappyGraduation.email.pojo.vo;

/**
 * @Author KD
 * @Date 2018/12/20 17:21
 **/
public class VerifyCodeValidationVO {
    private Integer verifyCodeId;
    private String verifyCode;
    private String email;
    private Integer verifyCodeType;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVerifyCodeType() {
        return verifyCodeType;
    }

    public void setVerifyCodeType(Integer verifyCodeType) {
        this.verifyCodeType = verifyCodeType;
    }
}
