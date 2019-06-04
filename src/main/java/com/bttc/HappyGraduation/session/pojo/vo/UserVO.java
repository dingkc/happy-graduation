package com.bttc.HappyGraduation.session.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @Author: Dk
 * @Date: 2019/3/19 21:33
 **/
public class UserVO {
	
	private Integer userId;

	private String username;

	private String name;

	private String oldPassword;

	private String password;

	private String email;
	
	private Integer verifyCodeId;//验证码编号
	
	private String verifyCode;//验证码

	private Integer verifyCodeType;//验证码类型

//	private String mobile;

	private Integer systemRoleId;

	private String gitUserName;

	private String avatar;

	private String agileUserId;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}

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

	public Integer getSystemRoleId() {
		return systemRoleId;
	}

	public void setSystemRoleId(Integer systemRoleId) {
		this.systemRoleId = systemRoleId;
	}

	public String getGitUserName() {
		return gitUserName;
	}

	public void setGitUserName(String gitUserName) {
		this.gitUserName = gitUserName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAgileUserId() {
		return agileUserId;
	}

	public void setAgileUserId(String agileUserId) {
		this.agileUserId = agileUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
