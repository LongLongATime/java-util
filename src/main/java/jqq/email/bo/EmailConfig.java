package jqq.email.bo;

/**
 * <br>==========================
 * <br> 公司：阿里互动娱乐
 * <br> 系统：用户平台
 * <br> 开发：qq.jiang(jqq105718@alibaba-inc.com)
 * <br> 创建时间：2022/8/3 09:41
 * <br>==========================
 */
public class EmailConfig {

    //设置发件人的SMTP服务器地址
    private String smtpHost ;
    //设置发件人的SMTP服务器地址
    private String smtpPort ;
    //设置传输协议
    private String transportProtocol ;

    /**
     * 以下是安全配置，由于SMTP服务器容易受到攻击，所以建议配置和开启
     */
    //设置用户的认证方式
    private String smtpAuth ;
    //开启ssl传输
    private Boolean smtpSsl;
    //身份认证密码
    private String authPassword;
    //身份认证账号
    private String authUserName;

    //设置调试信息在控制台打印出来
    private Boolean debug;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public Boolean getSmtpSsl() {
        return smtpSsl;
    }

    public void setSmtpSsl(Boolean smtpSsl) {
        this.smtpSsl = smtpSsl;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public String getAuthUserName() {
        return authUserName;
    }

    public void setAuthUserName(String authUserName) {
        this.authUserName = authUserName;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }
}
