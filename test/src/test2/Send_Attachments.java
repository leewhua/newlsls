package test2;

import java.io.File;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
/**
 * 发送带附件的邮件
 */
public class Send_Attachments {
    public static boolean sendMail(Mail_SendProper mailSender) {    
        Session sendMailSession = Session.getInstance(
                mailSender.getProperties());// 根据邮件发送的属性和密码验证器构造一个发送邮件的session
        try {
            Message mailMessage = new MimeMessage(sendMailSession);// 根据session创建一个邮件消息
            Address from = new InternetAddress(mailSender.getSendAddress());// 创建邮件发送者地址
            mailMessage.setFrom(from); // 设置邮件消息的发送者地址
            Address to = new InternetAddress(mailSender.getReceiveAddress());// 创建邮件的接收者地址
            mailMessage.setRecipient(Message.RecipientType.TO, to);// 设置邮件消息的接收者地址
            mailMessage.setSubject(mailSender.getSubject()); // 设置邮件消息的主题
            mailMessage.setSentDate(new Date()); // 设置邮件消息发送的时间
            Multipart mainPart = new MimeMultipart();// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            BodyPart html = new MimeBodyPart(); // 创建一个包含HTML内容的MimeBodyPart
            html.setContent(mailSender.getContent(), "text/html; charset=utf-8"); // 设置HTML内容
            mainPart.addBodyPart(html);
            String[] attachFileNames = mailSender.getAttachFileNames();// 为邮件添加附件
            if (attachFileNames != null && attachFileNames.length > 0) {
                MimeBodyPart attachment = null;// 存放邮件附件的MimeBodyPart
                File file = null;
                for (int i = 0; i < attachFileNames.length; i++) {
                    attachment = new MimeBodyPart();
                    file = new File(attachFileNames[i]);// 根据附件文件创建文件数据源
                    FileDataSource fds = new FileDataSource(file);
                    attachment.setDataHandler(new DataHandler(fds));
                    attachment.setFileName(MimeUtility.encodeWord(
                            file.getName(), "utf-8", null)); // 为附件设置文件名
                    mainPart.addBodyPart(attachment);
                }
            }
            mailMessage.setContent(mainPart);// 将MiniMultipart对象设置为邮件内容
            Transport.send(mailMessage);// 发送邮件
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        // 创建邮件信息
//        mailSender.setSendAddress("xxx@163.com");
//        mailSender.setReceiveAddress("xxx@163.com");
//        mailSender.setSubject("带附件的邮件测试");
//        mailSender.setContent("我上传了附件，请注意查收测试成功!");
//        String[] fileNames = new String[1];
//        fileNames[0] = "E:/1.txt";
//        mailSender.setAttachFileNames(fileNames);
//        Send_Attachments.sendMail(mailSender);
    }
}