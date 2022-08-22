package jqq.email.bo;

import java.util.List;

/**
 * <br>==========================
 * <br> 公司：阿里互动娱乐
 * <br> 系统：用户平台
 * <br> 开发：qq.jiang(jqq105718@alibaba-inc.com)
 * <br> 创建时间：2022/8/3 09:38
 * <br>==========================
 */
public class Email {

    // 发件人邮箱
    private String fromEmail;
    // 发件人姓名
    private String fromName;
    // 收件人邮箱
    private String toEmail;
    // 收件人姓名
    private String toName;
    // 主题
    private String object;
    // 内容
    private String context;
    //附近
    private List<EmailFile> fileList;

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<EmailFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<EmailFile> fileList) {
        this.fileList = fileList;
    }
}
