package com.bttc.HappyGraduation.business.ftp.utils;

/**
 * ftp静态常量值
 * 
 * @author LXX
 *
 */
public class FTPConstant {

	// 文件传输类型：BIN 二进制文件；ASC ASCII码文件
	public static final short BIN = 0;
	public static final short ASC = 1;

	public static final short ACTIVE_MODE = 0;
	public static final short PASSIVE_MODE = 1;

	public static int DEFAULT_TIMEOUT_SECONDS = 120;

	public static final String PROPERTIES_SUFFIX = ".properties";

	public static final String MAIN_PROPERTIES = "classpath:ftpConfig.properties";

	// ftp配置文件key值
	public static final String PROPERTIES_KEY_IP = ".ftp.host.ip";
	public static final String PROPERTIES_KEY_PORT = ".ftp.host.port";
	public static final String PROPERTIES_KEY_USERNAME = ".ftp.host.username";
	public static final String PROPERTIES_KEY_PASSWORD = ".ftp.host.password";
	public static final String PROPERTIES_KEY_LOCALPATH = ".ftp.local.path";
	public static final String PROPERTIES_KEY_REMOTEPATH = ".ftp.remote.path";
	public static final String PROPERTIES_KEY_TIMEOUT_SECOND = ".ftp.timeout.second";
	
	
	public static final char FTP_FILE_SERPERATOR = '/';

}
