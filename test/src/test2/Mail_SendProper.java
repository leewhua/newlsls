package test2;


import java.util.Properties;
/**
 * 发送邮件需要使用的基本信息
 */
public class Mail_SendProper {
    // 发送邮件的服务器的IP和端口
    private String Host;
    private String Port = "25";
    private String SendAddress; // 邮件发送者的地址
    private String ReceiveAddress; // 邮件接收者的地址

    private String subject; // 邮件主题
    private String content; // 邮件的文本内容
    private String[] attachFileNames; // 邮件附件的文件名
    /** 获得邮件会话属性 */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.Host);
        p.put("mail.smtp.port", this.Port);
        return p;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getHost() {
        return Host;
    }
    public void setHost(String host) {
        Host = host;
    }
    public String getPort() {
        return Port;
    }
    public void setPort(String port) {
        Port = port;
    }
    public String getReceiveAddress() {
        return ReceiveAddress;
    }
    public void setReceiveAddress(String receiveAddress) {
        ReceiveAddress = receiveAddress;
    }
    public String getSendAddress() {
        return SendAddress;
    }
    public void setSendAddress(String sendAddress) {
        SendAddress = sendAddress;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String[] getAttachFileNames() {
        return attachFileNames;
    }
    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }
}