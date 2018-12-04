package com.bttc.HappyGraduation.doc.pojo.po;

/**
 * this is a bean
 *
 * @author DK
 * @create 2018-06-28 17:21
 */

public class SaveHtml {
    private String fileId;
    private String fileName;
    private String fileType;
    private String projectName;
    private String createTime;
    private String filePath;
    private String fileContent;

    public SaveHtml() {
    }

    public SaveHtml(String fileId, String fileName, String fileType, String projectName, String createTime, String filePath, String fileContent) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.projectName = projectName;
        this.createTime = createTime;
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return "SaveHtml{" +
                "fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", projectName='" + projectName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileContent='" + fileContent + '\'' +
                '}';
    }
}