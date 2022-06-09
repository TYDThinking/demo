package com.akun.demo.utils.code;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;

import org.apache.commons.mail.HtmlEmail;

/**
 * @Package: com.akun.demo.utils
 * @ClassName: emailUtil
 * @Author: akun
 * @CreateTime: 2022/4/11 15:17
 * @Description:
 */
public class EmailUtil {

    /**
     * 发送验证码
     * @param email
     * @return
     */
    public String sendAuthCodeEmail(String email) {
        /*验证码*/
        String code = generateVerifyCodeAndLetter();
        try {
            HtmlEmail mail = new HtmlEmail();
            /*发送邮件的服务器 126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com*/
            mail.setHostName("smtp.qq.com");
            /*不设置发送的消息有可能是乱码*/
            mail.setCharset("UTF-8");
            /*IMAP/SMTP服务的密码    发送消息的邮箱如：2212312@qq.com*/
            mail.setAuthentication("1422132642@qq.com", "btzvrwtqeswqfici");
            /*发送邮件的邮箱和发件人*/
            mail.setFrom("1422132642@qq.com", "发件人");
            /*使用安全链接*/
            mail.setSSLOnConnect(true);
            /*接收的邮箱*/
            mail.addTo(email);
            /*验证码*/
//            String code = generateVerifyCode(6);
//            String code = generateVerifyCodeAndLetter();
            /*设置邮件的主题*/
            mail.setSubject("注册验证码");
            /*设置邮件的内容*/
            mail.setMsg("尊敬的用户:你好! 注册验证码为:" + code + "(有效期为一分钟)");
            String s = mail.send();//发送
            System.out.println("s = " + s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return code;
    }

//    //测试
//    public static void main(String[] args) throws Exception {
//        EmailUtil emailUtil = new EmailUtil();
//        String i = emailUtil.sendAuthCodeEmail("1714658349@qq.com");
//        System.out.println("i = " + i);
//    }
    public  void SendEmail() throws Exception{
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
//        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，POP3/SMTP协议授权码 163使用：smtp.163.com
        ts.connect("smtp.qq.com", "1422132642", "tyjdxdkafjsvgggc");
        //发送方的QQ号           POP3/SMTP协议授权码
        // 创建邮件
        Message message = createSimpleMail(session);
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
    /**
     * 生成随机验证码（数字和字母）
     * @return
     */
    public  String generateVerifyCodeAndLetter(){
        //  获取6为随机验证码
        String[] letters = new String[] {
                "q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",
                "A","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M",
                "0","1","2","3","4","5","6","7","8","9"};
        String stringBuilder ="";
        for (int i = 0; i < 6; i++) {
            stringBuilder = stringBuilder + letters[(int)Math.floor(Math.random()*letters.length)];
        }
        return stringBuilder;
    }

    /**
     * 生成随机验证码（只有数字）
     * @param number 几位数
     * @return
     */
    public  String generateVerifyCode(int number) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= number; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public  MimeMessage createSimpleMail(Session session) throws Exception {
        //  获取6为随机验证码
        String[] letters = new String[] {
                "q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",
                "A","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M",
                "0","1","2","3","4","5","6","7","8","9"};
        String stringBuilder ="";
        for (int i = 0; i < 6; i++) {
            stringBuilder = stringBuilder + letters[(int)Math.floor(Math.random()*letters.length)];
        }

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人   发送方QQ
        message.setFrom(new InternetAddress("1422132642@qq.com"));
        // 指明邮件的收件人，发件人和收件人如果是一样的，那就是自己给自己发   接受方QQ
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("1422132642@qq.com"));
        // 邮件的标题
        message.setSubject("JavaMail测试");
        // 邮件的文本内容
        message.setContent("欢迎您注册【OG高玩会】,账号注册验证码为(一分钟有效):"+stringBuilder+",请勿回复此邮箱", "text/html;charset=UTF-8");

        // 返回创建好的邮件对象
        return message;
    }
}
