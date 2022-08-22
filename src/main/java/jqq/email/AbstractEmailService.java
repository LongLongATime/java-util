package jqq.email;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import jqq.email.bo.Email;
import jqq.email.bo.EmailConfig;
import jqq.email.bo.EmailFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送邮件模版类
 * <br>==========================
 * <br> 公司：阿里互动娱乐
 * <br> 系统：用户平台
 * <br> 开发：qq.jiang(jqq105718@alibaba-inc.com)
 * <br> 创建时间：2022/8/3 09:38
 * <br>==========================
 */
public abstract class AbstractEmailService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 发送一个简单的邮件
    public boolean sendSimpleEmail(Email email) throws Exception {
        EmailConfig emailConfig = getEmailConfig();
        if(emailConfig==null){
            logger.error("没有邮箱配置");
            return false;
        }

        javax.mail.Session session = getMailSession(emailConfig);

        // 3. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email.getFromEmail(), email.getFromName(), "UTF-8"));                                                                     // From: 发件人
        message.setRecipient(
            MimeMessage.RecipientType.TO, new InternetAddress(email.getToEmail(), email.getToName(), "UTF-8"));      // To: 收件人（可以增加多个收件人、抄送、密送）
        message.setSubject(email.getObject(), "UTF-8");// Subject: 邮件主题
        MimeMultipart mimeMultipart = createMimeMultipart(email);
        message.setContent(mimeMultipart);                                                                                // Content: 邮件正文（可以使用html标签）
        message.setSentDate(new java.util.Date());                                                                                                        // 设置发件时间
        message.saveChanges();                                                                                                                            // 保存设置

        // 4. 根据 Session 获取邮件传输对象
        Transport.send(message);
        return true;
    }

    private Session getMailSession(final EmailConfig emailConfig) {
        // 1. 创建参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", emailConfig.getTransportProtocol());       // 使用的协议（JavaMail规范要求）
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", emailConfig.getSmtpAuth());
        props.put("mail.smtp.host", emailConfig.getSmtpHost());
        props.put("mail.smtp.connectiontimeout", "5000"); // 5秒连接
        props.put("mail.smtp.timeout", "5000");
        if (emailConfig.getSmtpSsl()) {
            // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", emailConfig.getSmtpPort());
            props.put("mail.smtp.port", emailConfig.getSmtpPort());
        } else {
            props.put("mail.smtp.port", emailConfig.getSmtpPort());
        }
        // 发件人的账号
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", emailConfig.getAuthPassword());

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(emailConfig.getAuthUserName(), emailConfig.getAuthPassword());
            }
        };

        return javax.mail.Session.getInstance(props,authenticator);
    }

    private MimeMultipart createMimeMultipart(Email email) throws MessagingException, IOException {
        MimeMultipart mimeMultipart = new MimeMultipart();
        // 设置邮件正文(邮件文本消息)
        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setContent(email.getContext(), "text/html;charset=gbk");
        mimeMultipart.addBodyPart(textBody);
        if (email.getFileList() != null && email.getFileList().size()> 0){
            List<EmailFile> fileList = email.getFileList();
            for (EmailFile mailFile : fileList){
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                /**
                 * 此处附件是在minio文件服务器上存储,此处拿到的是文件的输入流inpu* tStream,需特殊处理后才能放在DataHandler中InputStreamDataSource
                 * 如果是直接在本地获取文件可以直接使用FileDataSource
                 * DataHandler handler = new DataHandler(new FileDataSource("文件存储路径,例如: D:\\file\\111.doc");
                 */
                DataHandler handler = new DataHandler(new ByteArrayDataSource(mailFile.getInputStream(),  "application/octet-stream"));
                mimeBodyPart.setDataHandler(handler);
                //对文件名进行编码，防止出现乱码
                String fileName = MimeUtility.encodeText(mailFile.getFileName(), "utf-8", "B");
                mimeBodyPart.setFileName(fileName);
                mimeMultipart.addBodyPart(mimeBodyPart);
            }
        }
        return mimeMultipart;
    }


    protected abstract EmailConfig getEmailConfig();

}
