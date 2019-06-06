package com.mm.jmail;
import javax.mail.MessagingException;
import java.io.IOException;

public class Test {
    /**
     * @param args
     */
    public static void main(String[] args) throws IOException, MessagingException {
       JMailInfo mailInfo = JMailInfo.getInstance();
       mailInfo.setServerHost(JMailInfo.QQMAIL_HOST);
       mailInfo.setServerPort(JMailInfo.QQMAIL_PORT);

       mailInfo.setJmailSenderAddress("852458550@qq.com");
       mailInfo.setJmailAccepterAddress("zhaomeng@ruifucredit.com;");
       //mailInfo.setCcAddress("*332@qq.com");
       //mailInfo.setBccAddress("*5911@qq.com");
       mailInfo.setJmailSubject("JMAIL邮件发送！");

        //QQ邮箱

        mailInfo.setJmailSenderName("852458550@qq.com");

        //QQ邮箱密码 

       mailInfo.setJmailSenderPwd("jhdpznlfobcubdgj");
      // mailInfo.setJmailAttachFile("附件路径，多个附件用;分开");
       long start = System.currentTimeMillis();
       mailInfo.setJmailBody("body");
        JMailSendUtil.SendSmtpTextMail(mailInfo);

       System.out.println("发送成功！用时："+Long.toString(System.currentTimeMillis()-start)+"毫秒");
    }    
}