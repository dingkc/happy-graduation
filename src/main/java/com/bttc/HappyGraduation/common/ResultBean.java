package com.bttc.HappyGraduation.common;


public class ResultBean {
	
	
	private String status;//操作结果状态码
	private String statusDecs;//操作结果状态码描述
	private String errCode;//操作结果错误码
	private String errMessage;//操作结果失败返回信息
	private Object data;//操作结果返回对象

    public static ResultBean newBean(){
        return new ResultBean();
    }
    
    public ResultBean() {
		super();
	}

	public ResultBean(Object data) {
		super();
		this.data = data;
	}
    
	public ResultBean(String status, String statusDecs, String errCode, String errMessage, Object data) {
		super();
		this.status = status;
		this.statusDecs = statusDecs;
		this.errCode = errCode;
		this.errMessage = errMessage;
		this.data = data;
	}

	/**
	* <p>Title: ok</p>
	* <p>Description: 请求操作成功返回，返回码为0，返回对象为data</p>
	*/
	public static ResultBean ok(Object data) {
        return new ResultBean("0", "success", null, null, data);
    }
	
	
	/**
	* <p>Title: internalServerError</p>
	* <p>Description: 服务端错误异常时，操作失败返回，返回码为500，错误描述为message</p>
	*/
	public static ResultBean internalServerError(String errMessage) {
		return new ResultBean("500", "failed", null, errMessage, null);
	}
	
	/**
	* <p>Title: internalServerError</p>
	* <p>Description: 服务端错误异常时，操作失败返回，返回码为500，错误描述为message</p>
	*/
	public static ResultBean internalServerError(String errCode, String errMessage) {
		return new ResultBean("500", "failed", errCode, errMessage, null);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDecs() {
		return statusDecs;
	}

	public void setStatusDecs(String statusDecs) {
		this.statusDecs = statusDecs;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
