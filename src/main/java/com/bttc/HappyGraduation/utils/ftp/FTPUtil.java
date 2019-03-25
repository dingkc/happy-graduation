package com.bttc.HappyGraduation.utils.ftp;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FTPUtil {

	protected static Logger log = LoggerFactory.getLogger(FTPUtil.class);

	private FTPClient client = null;
	private FTPConfig config = null;

	/**
	 * 初始化ftp配置
	 * 
	 * @param ftpPathCode
	 * @throws Exception
	 */
	private void initFtpConfig(String ftpPathCode) throws Exception {
		this.config = new FTPConfig();
		Properties activeProperties = getProperties();
//		if (activeProperties == null) {
//			throw new Exception("根据当前ftpPathCode：" + ftpPathCode + "找不到对应的ftp配置文件！");
//		}

		String hostIp = activeProperties.getProperty(ftpPathCode + FTPConstant.PROPERTIES_KEY_IP);
		if (StringUtils.isNotEmpty(hostIp)) {
			config.setHostIp(hostIp);
		}

		String port = activeProperties.getProperty(ftpPathCode + FTPConstant.PROPERTIES_KEY_PORT);
		if (StringUtils.isNotEmpty(port)) {
			config.setPort(Integer.valueOf(port));
		}

		String userName = activeProperties.getProperty(ftpPathCode + FTPConstant.PROPERTIES_KEY_USERNAME);
		if (StringUtils.isNotEmpty(userName)) {
			config.setUserName(userName);
		}

		String password = activeProperties.getProperty(ftpPathCode + FTPConstant.PROPERTIES_KEY_PASSWORD);
		if (StringUtils.isNotEmpty(password)) {
			config.setPassword(password);
		}

		String localPath = activeProperties.getProperty(ftpPathCode + FTPConstant.PROPERTIES_KEY_LOCALPATH);
		if (StringUtils.isNotEmpty(localPath)) {
			config.setLocalPath(localPath);
		}

		String remotePath = activeProperties.getProperty(ftpPathCode + FTPConstant.PROPERTIES_KEY_REMOTEPATH);
		if (StringUtils.isNotEmpty(remotePath)) {
			config.setRemotePath(remotePath);
		}

	}

	private Properties getProperties() throws Exception {
		Properties pps = new Properties();
		pps.load(new FileInputStream(new File(FTPConstant.MAIN_PROPERTIES)));

		return pps;
	}

	private void initFtpConfig(FTPConfig ftpConfig) throws Exception {
		this.config = ftpConfig;
	}

	public FTPUtil(FTPConfig ftpConfig) throws Exception {
		// 初始化配置信息
		initFtpConfig(ftpConfig);
		client = new FTPClient();
		client.setDefaultTimeout(
				(config.getTimeoutSeconds() == null ? FTPConstant.DEFAULT_TIMEOUT_SECONDS : config.getTimeoutSeconds())
						* 1000);

		// 创建FTP连接
		// open();
	}

	public FTPUtil(String ftpPathCode) throws Exception {
		// 初始化配置信息
		initFtpConfig(ftpPathCode);
		client = new FTPClient();
		client.setDefaultTimeout(
				(config.getTimeoutSeconds() == null ? FTPConstant.DEFAULT_TIMEOUT_SECONDS : config.getTimeoutSeconds())
						* 1000);

		// 创建FTP连接
		// open();
	}

	/**
	 * 设置FTP字符集
	 * 
	 * @param encoding
	 *            字符集
	 * @return
	 */
	public void setEncoding(String encoding) {
		client.setControlEncoding(encoding);
	}

	/**
	 * 创建FTP连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public void open() throws Exception {
		client.connect(config.getHostIp(), config.getPort());
		int reply = client.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			client.disconnect();
			log.error("FTP服务器连接被拒绝！");
			throw new Exception("FTP服务器连接被拒绝！");
		}
		if (!client.login(config.getUserName(), config.getPassword())) {
			client.disconnect();
			log.error("FTP服务器登陆失败！");
			throw new Exception("FTP服务器登陆失败！");
		}
		if (!client.changeWorkingDirectory(wrapper(config.getRemotePath()))) {
			client.disconnect();
			log.error("FTP服务器设置工作目录异常！");
			throw new Exception("FTP服务器设置工作目录异常！");
		}
		if (FTPReply.isPositiveCompletion(client.sendCommand("OPTS UTF8", "ON"))) {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
			setEncoding("UTF-8");
		} else {
			setEncoding("GBK");
		}
	}

	/**
	 * 关闭FTP连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public void close() throws Exception {
		if (client.isConnected()) {
			client.logout();
			client.disconnect();
		}
	}

	/**
	 * 设置FTP工作模式，工作模式分为：主动模式、被动模式 @param mode 工作模式 0:主动模式 1:被动模式
	 * 默认为主动模式 @return @throws
	 */
	public void setMode(int mode) {
		switch (mode) {
		case FTPConstant.ACTIVE_MODE:
			client.enterLocalActiveMode();
			break;
		case FTPConstant.PASSIVE_MODE:
			client.enterLocalPassiveMode();
			break;
		default:
			break;
		}
	}

	public void setFileType(int mode) throws Exception {
		switch (mode) {
		case FTPConstant.BIN:
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			break;
		case FTPConstant.ASC:
			client.setFileType(FTPClient.ASCII_FILE_TYPE);
			break;
		default:
			throw new Exception(String.format("不支持的文件模式[%d]！", mode));
		}
	}

	/**
	 * 按指定的模式匹配获取文件列表
	 * 
	 * @param pathName
	 *            文件模式
	 * @return String[] 文件列表
	 * @throws Exception
	 */
	public String[] list(String pathName) throws Exception {
		return client.listNames(wrapper(pathName));
	}

	public String[] list(String pathName, int mode) throws Exception {
		setMode(mode);
		return client.listNames(wrapper(pathName));
	}

	public String getCharset() {
		return client.getCharsetName();
	}

	/**
	 * 获取当前工作目录下文件列表
	 * 
	 * @return String[] 文件列表
	 * @throws Exception
	 */
	public String[] list() throws Exception {
		return client.listNames();
	}

	public String[] list(int mode) throws Exception {
		setMode(mode);
		return client.listNames();
	}

	/**
	 * 按指定的模式匹配获取文件信息列表
	 * 
	 * @param pathName
	 *            文件模式
	 * @return FTPFile[] 文件信息列表
	 * @throws Exception
	 */
	public FTPFile[] listFiles(String pathName) throws Exception {
		return client.listFiles(wrapper(pathName));
	}

	public FTPFile[] listFiles(String pathName, int mode) throws Exception {
		setMode(mode);
		return client.listFiles(wrapper(pathName));
	}

	/**
	 * 获取当前工作目录下文件信息列表
	 * 
	 * @return FTPFile[] 文件信息列表
	 * @throws Exception
	 */
	public FTPFile[] listFiles() throws Exception {
		return client.listFiles();
	}

	public FTPFile[] listFiles(int mode) throws Exception {
		setMode(mode);
		return client.listFiles();
	}

	/**
	 * 获取先前命令执行的返回码
	 * 
	 * @return int 返回码
	 * @throws Exception
	 */
	public int getReplay() throws Exception {
		return client.getReply();
	}

	/**
	 * 变更FTP工作目录
	 * 
	 * @param path
	 *            工作目录
	 * @return
	 * @throws Exception
	 */
	public boolean changeDirectory(String path) throws Exception {
		return client.changeWorkingDirectory(wrapper(path));
	}

	/**
	 * 获取当前FTP工作目录
	 * 
	 * @return String 当前工作目录
	 * @throws Exception
	 */
	public String getCurrentDirectory() throws Exception {
		return client.printWorkingDirectory();
	}

	/**
	 * 创建FTP目录
	 * 
	 * @param path
	 *            待创建的目录
	 * @return
	 * @throws Exception
	 */
	public boolean mkdir(String path) throws Exception {

		if (StringUtils.isEmpty(path)) {
			return false;
		}

		return client.makeDirectory(wrapper(path));
	}

	/**
	 * 递归创建路径中所有不存在的目录
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public boolean mkdirs(String path) throws Exception {

		if (StringUtils.isEmpty(path)) {
			return false;
		}

		StringBuilder sb = new StringBuilder();
		try {
			String[] filepath = StringUtils.split(path, FTPConstant.FTP_FILE_SERPERATOR);

			for (int i = 0; i < filepath.length; i++) {
				sb.append(FTPConstant.FTP_FILE_SERPERATOR).append(filepath[i]);
				if (!isRemoteDirExits(sb.toString())) {
					mkdir(sb.toString());
				}
			}
		} catch (Exception e) {
			throw new Exception(String.format("目录[%s]创建失败。", sb.toString()));
		}
		return true;
	}

	/**
	 * 通过输入流上传文件
	 * 
	 * @param remoteFileName 远程文件名
	 * @param input
	 *            输入流对象
	 * @return
	 * @throws Exception
	 */
	public void upload(String remoteFileName, InputStream input) throws Exception {
		if (!client.storeFile(wrapper(remoteFileName), input)) {
			throw new Exception(String.format("文件[%s]上传失败", remoteFileName));
		}
	}

	/**
	 * 通过输入流按指定文件类型上传文件
	 * 
	 * @param remoteFileName 远程文件名
	 * @param input
	 *            输入流对象
	 * @param mode
	 *            文件传输类型：BIN 二进制文件；ASC ASCII码文件
	 * @return
	 * @throws Exception
	 */
	public void upload(String remoteFileName, InputStream input, int mode) throws Exception {
		setFileType(mode);
		upload(remoteFileName, input);
	}

	/**
	 * 将本地文件上传至FTP服务器
	 * 
	 * @param remoteFileName 远程文件名
	 * @param localFileName
	 *            本地文件名
	 * @return
	 * @throws Exception
	 */
	public void upload(String remoteFileName, String localFileName) throws Exception {

		InputStream is = null;
		String localFilePathName = "";
		String remoteFilePathName = FilenameUtils.getFullPath(remoteFileName);
		if (!exitsFilePathWithFileName(localFileName)) {
			localFilePathName = config.getLocalPath() + "/" + localFileName;
		} else {
			localFilePathName = localFileName;
		}

		if (!isLocalFileExits(localFilePathName)) {
			throw new Exception(String.format("本地文件[%s]不存在，上传失败", localFileName));
		}

		if (exitsFilePathWithFileName(remoteFileName) && !isRemoteFileExits(remoteFilePathName)) {
			log.warn(String.format("[%s]路径不存在,系统已经自动创建。", remoteFilePathName));
			mkdirs(remoteFilePathName);
		}
		try{
			is = new FileInputStream(localFilePathName);
			if (!client.storeFile(wrapper(remoteFileName), is)) {

				throw new Exception(String.format("文件[%s]上传失败", remoteFileName));
			}
		}finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 按指定文件类型将本地文件上传至FTP服务器
	 * 
	 * @param remoteFileName 远程文件名
	 * @param localFileName
	 *            本地文件名
	 * @param mode
	 *            文件传输类型：BIN 二进制文件；ASC ASCII码文件
	 * @return
	 * @throws Exception
	 */
	public void upload(String remoteFileName, String localFileName, int mode) throws Exception {
		setMode(mode);
		upload(remoteFileName, localFileName);
	}

	/**
	 * 从FTP服务器上下载指定的文件，并返回输出流对象
	 * 
	 * @param remoteFileName 远程文件名
	 * @return output 输出流对象
	 * @throws Exception
	 */
	public void download(String remoteFileName, OutputStream output) throws Exception {
		if (!client.retrieveFile(wrapper(remoteFileName), output)) {
			throw new Exception(String.format("文件[%s]下载失败", remoteFileName));
		}
	}

	/**
	 * 按指定的传输类型从FTP服务器上下载指定的文件，并返回输出流对象
	 * 
	 * @param remoteFileName 远程文件名
	 * @param mode
	 *            文件传输类型：BIN 二进制文件；ASC ASCII码文件
	 * @return output 输出流对象
	 * @throws Exception
	 */
	public void download(String remoteFileName, OutputStream output, int mode) throws Exception {
		setFileType(mode);
		download(remoteFileName, output);
	}

	/**
	 * 按指定的传输类型从FTP服务器上下载指定的文件，并返回输出流对象
	 *
	 * @param remoteFileName 远程文件名
	 *            文件传输类型：BIN 二进制文件；ASC ASCII码文件
	 * @return output 输出流对象
	 * @throws Exception
	 */
	public InputStream download(String remoteFileName) throws Exception {
		InputStream inputStream = client.retrieveFileStream(wrapper(remoteFileName));
		if(inputStream == null){
			throw new Exception(String.format("文件[%s]下载失败", remoteFileName));
		}
		return inputStream;
	}

	/**
	 * 从FTP服务器上下载指定的文件，并保存为本地文件
	 * 
	 * @param remoteFileName 远程文件名
	 * @param localFileName
	 *            本地文件名
	 * @return
	 * @throws Exception
	 */
	public void download(String remoteFileName, String localFileName) throws Exception {

		if (!isRemoteFileExits(remoteFileName)) {
			throw new Exception("ftp主机上文件[%d]不存在,无法下载！"+remoteFileName);
		}

		OutputStream os = null;

		String localFilePathName = null;
		if (!exitsFilePathWithFileName(localFileName)) {
			localFilePathName = config.getLocalPath() + "/" + localFileName;
		} else {
			localFilePathName = localFileName;
		}
		try {
			os = new FileOutputStream(localFilePathName);
			if (!client.retrieveFile(wrapper(remoteFileName), os)) {
				throw new Exception(String.format("文件[%s]下载失败", remoteFileName));
			}
		} catch (Exception e) {
			throw new Exception("文件下载失败", e);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * 按指定的文件传输类型从FTP服务器上下载指定的文件，并保存为本地文件
	 * 
	 * @param remoteFileName 远程文件名
	 * @param localFileName
	 *            本地文件名
	 * @return
	 * @throws Exception
	 */
	public void download(String remoteFileName, String localFileName, int mode) throws Exception {
		setFileType(mode);
		download(remoteFileName, localFileName);
	}

	/**
	 * 读取文件内容
	 * 
	 * @param remoteFileName
	 * @return
	 * @throws Exception
	 */
	public String readFileToString(String remoteFileName) throws Exception {

		return readFileToString(remoteFileName, Charset.defaultCharset());
	}

	/**
	 * 读取文件内容
	 * 
	 * @param remoteFileName
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public String readFileToString(String remoteFileName, String encoding) throws Exception {

		return readFileToString(remoteFileName, Charsets.toCharset(encoding));
	}

	/**
	 * 读取文件内容
	 * 
	 * @param remoteFileName
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public String readFileToString(String remoteFileName, Charset encoding) throws Exception {
		if (!isRemoteFileExits(remoteFileName)) {
			throw new Exception("ftp主机上文件[%d]不存在,无法下载！"+ remoteFileName);
		}
		try (InputStream in = client.retrieveFileStream(wrapper(remoteFileName));) {
			return IOUtils.toString(in, encoding);
		} catch (IOException e) {
			throw new IOException(String.format("文件[%s]读取失败", remoteFileName));
		}
	}

	/**
	 * 将FTP服务器上指定的文件名变更为新的文件名
	 * 
	 * @param oldRemoteFileName
	 *            原文件名
	 * @param newRemoteFileName
	 *            新文件名
	 * @return
	 * @throws Exception
	 */
	public void rename(String oldRemoteFileName, String newRemoteFileName) throws Exception {
		String newRemoteFilePathName = FilenameUtils.getFullPath(newRemoteFileName);
		if (!isRemoteFileExits(oldRemoteFileName)) {
			throw new Exception("文件[%d]不存在！"+oldRemoteFileName);
		}
		if (!isRemoteFileExits(newRemoteFilePathName)) {
			log.warn("[%s]路径不存在,系统已经自动创建。"+ newRemoteFilePathName);
			mkdirs(newRemoteFilePathName);
		}
		if (!client.rename(wrapper(oldRemoteFileName), wrapper(newRemoteFileName))) {
			throw new Exception("文件[%d]改名失败！"+ oldRemoteFileName);
		}
	}

	public void rename(String oldRemoteFileName, String newRemoteFileName, int mode) throws Exception {
		setMode(mode);
		rename(oldRemoteFileName, newRemoteFileName);
	}

	/**
	 * 从FTP服务器上删除指定的文件
	 * 
	 * @param remoteFileName 远程文件名
	 * @return
	 * @throws Exception
	 */
	public void delete(String remoteFileName) throws Exception {
		if (!isRemoteFileExits(remoteFileName)) {
			log.warn(remoteFileName + "不存在！");
			return;
		}
		if (!client.deleteFile(wrapper(remoteFileName))) {
			throw new Exception("文件[%d]删除失败！"+remoteFileName);
		}
	}

	private String wrapper(String str) throws Exception {
		if(FTPReply.isPositiveCompletion(client.sendCommand("OPTS UTF8", "ON"))){
			return new String(str.getBytes("GBK"), "ISO-8859-1");
		}else {
			return new String(str.getBytes("GBK"), "ISO-8859-1");
		}
	}

	

	public void compressToZip(String remoteFileName, ZipOutputStream zipOut) throws Exception {
		FTPFile[] files = listFiles(remoteFileName);
		for (FTPFile file : files) {
			if (file.isFile()) {
				InputStream in = client.retrieveFileStream(file.getName());
				doCompress(in, file.getName(), zipOut);
			} else {
				compressToZip(file.getName(), zipOut);
			}
		}
	}

	public void doCompress(InputStream fin, String fileName, ZipOutputStream out) throws Exception {
		byte[] buffer = new byte[1024];
		out.putNextEntry(new ZipEntry(fileName));
		int len = 0;
		while ((len = fin.read(buffer)) > 0) {
			out.write(buffer, 0, len);
			out.flush();
		}
		out.closeEntry();
		fin.close();
	}

	/**
	 * 判断链接是否被关闭
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isConnected() throws Exception {
		return client.isConnected();
	}

	public boolean isAvailable() throws Exception {
		return client.isAvailable();
	}

	/**
	 * 判断文件(远端)是否存在
	 * 
	 * @param fileName
	 *            文件或者目录名称
	 * @return
	 * @throws Exception
	 */
	public boolean isRemoteFileExits(String fileName) throws Exception {

		if (StringUtils.isEmpty(fileName)) {
			throw new NullPointerException("fileName must not be null");
		}
		String files[] = list(fileName);
		if (files != null && files.length > 0) {
			return true;
		}

		return false;
	}

	public boolean isRemoteFileExits(String fileName, int mode) throws Exception {

		if (StringUtils.isEmpty(fileName)) {
			throw new NullPointerException("fileName must not be null");
		}
		String files[] = list(fileName, mode);
		if (StringUtils.isNoneEmpty(files)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断远端目录是否存在
	 * 
	 * @param dirName
	 * @return
	 * @throws Exception
	 */
	public boolean isRemoteDirExits(String dirName) throws Exception {

		if (StringUtils.isEmpty(dirName)) {
			throw new NullPointerException("fileName must not be null");
		}

		return changeDirectory(dirName);
	}

	/**
	 * 判断文件(本地)是否存在
	 * 
	 * @param fileName
	 *            文件或者目录名称
	 * @return
	 * @throws Exception
	 */
	public boolean isLocalFileExits(String fileName) throws Exception {

		File file = new File(fileName);
		if (file.exists()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断文件名称中是否包含路径
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean exitsFilePathWithFileName(String fileName) throws Exception {

		String filepath = FilenameUtils.getFullPath(fileName);
		if (StringUtils.isNotEmpty(filepath)) {
			return true;
		}

		return false;
	}

	/**
	 * 获取本地路径
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private String getLocalPath(String fileName) throws Exception {
		if (!exitsFilePathWithFileName(fileName)) {
			return config.getLocalPath();
		}
		return FilenameUtils.getFullPath(fileName);
	}

	/**
	 * 获取远端路径
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private String getRemotePath(String fileName) throws Exception {
		if (!exitsFilePathWithFileName(fileName)) {
			return config.getRemotePath();
		}
		return FilenameUtils.getFullPath(fileName);
	}
}
