package com.bttc.HappyGraduation.session.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="user")
@DynamicUpdate
public class UserPO {
	
    public static final Integer ACTIVE = 1;
    public static final Integer DEACTIVE = 4;
    public static final Integer BLOCK = 2;
    public static final Integer STOP = 0;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition="int(10) COMMENT '用户编号'")
    private Integer userId;
    
    @Column(columnDefinition="varchar(255) COMMENT '账户名'", nullable=false)
    private String username;
    
    @Column(columnDefinition="varchar(255) COMMENT '用户中文名'", nullable=false)
    private String name;
    
    @Column(columnDefinition="varchar(255) COMMENT '密码'", nullable=false)
    private String password;
    
    @Column(columnDefinition="varchar(255) COMMENT '邮箱'", nullable=false)
    private String email;
    
    @Column(columnDefinition="int(1) COMMENT '帐号状态：1正常；0加锁；2停用；3历史'", nullable=false)
    private Integer state;

//    @Column(columnDefinition="varchar(255) COMMENT '手机号码'", nullable=false)
//    private String mobile;
    
    @Temporal(TemporalType.TIMESTAMP)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(columnDefinition = "datetime COMMENT '创建时间'", nullable=false)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(columnDefinition = "datetime COMMENT '操作时间'", nullable=false)
	private Date doneDate;

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

}
