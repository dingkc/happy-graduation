package com.bttc.HappyGraduation.business.doc.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.utils.FTPUtil;

import java.io.IOException;
import java.io.InputStream;

public interface IOnlineDocumentSV {

    void parserDocument(Integer ftpFileId) throws Exception;

    InputStream downloadDocumentFromFtp(FTPUtil ftpUtil, String fullPathNoName, String fileName);

    void ftpUpload(FTPUtil ftpUtil,InputStream inputStream, String fullPathNoName,String fileName) throws IOException;
}
