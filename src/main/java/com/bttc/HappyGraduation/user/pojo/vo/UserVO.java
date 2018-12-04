package com.bttc.HappyGraduation.user.pojo.vo;

/**
* <p>Title: UserVO</p>
* <p>Description: </p> 
* @author liuxf6
* @date 2018年7月3日
*/
public class UserVO {
	
	private Integer userId;

	private String accountName;

	private String userName;

	private String oldPassword;

	private String password;

	private String email;
	
	private Integer verifyCodeId;//验证码编号
	
	private String verifyCode;//验证码

	private Integer verifyCodeType;//验证码类型

	private String mobile;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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

    public Integer getVerifyCodeType() {
        return verifyCodeType;
    }

    public void setVerifyCodeType(Integer verifyCodeType) {
        this.verifyCodeType = verifyCodeType;
    }
}
