package com.bttc.HappyGraduation.business.ftp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class FTPConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6505066806416215056L;
	@Value("${local.ftp.ip}")
	private String hostIp;
	@Value("${local.ftp.port}")
	private int port;
	@Value("${local.ftp.username}")
	private String userName;
	@Value("${local.ftp.password}")
	private String password;
	@Value("${local.ftp.localPath}")
	private String localPath ;
	@Value("${local.ftp.remotePath}")
	private String remotePath ;
	@Value("${local.ftp.timeoutSeconds}")
	private Integer timeoutSeconds ;

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public Integer getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public void setTimeoutSeconds(Integer timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}

	@Override
	public String toString() {
		return "FTPConfig [hostIp=" + hostIp + ", port=" + port + ", userName=" + userName + ", password=" + password
				+ ", localPath=" + localPath + ", remotePath=" + remotePath + ", timeoutSeconds=" + timeoutSeconds
				+ "]";
	}

	

}
