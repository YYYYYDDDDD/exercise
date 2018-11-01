package tools;

/**
 * 
 * 文件基本属性
 * 
 * @author ZhangZhiBin
 * @version 2018年9月6日
 * @see FileBean
 * @since
 */
public class FileBean {
    /**
     * 文件上传id 工具类中对该项重新赋值成用于访问文件地址的路径
     */
    private String fileId;

    /**
     * 文件上传id 用于文件编辑后向后台回传数据
     */
    private String fileResId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件时长
     */
    private String fileTime;
    
    /**
     * 是否主图
     */
    private String isDisplayToList;

    public String getIsDisplayToList() {
        return isDisplayToList;
    }

    public void setIsDisplayToList(String isDisplayToList) {
        this.isDisplayToList = isDisplayToList;
    }

    public String getFileResId() {
        return fileResId;
    }

    public void setFileResId(String fileResId) {
        this.fileResId = fileResId;
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

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

}
