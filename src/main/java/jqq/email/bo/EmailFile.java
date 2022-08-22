package jqq.email.bo;

import java.io.InputStream;

/**
 * <br>==========================
 * <br> 公司：阿里互动娱乐
 * <br> 系统：用户平台
 * <br> 开发：qq.jiang(jqq105718@alibaba-inc.com)
 * <br> 创建时间：2022/8/3 10:44
 * <br>==========================
 */
public class EmailFile {

    private String fileName;
    private InputStream inputStream;

    public EmailFile(String fileName, InputStream inputStream) {
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
