package com.mm.jmail;
import javax.mail.MessagingException;
import java.io.IOException;

public class Test2 {
    /**
     * @param args
     */
    public static void main(String[] args) throws IOException, MessagingException {
       JMailInfo mailInfo = JMailInfo.getInstance();
       mailInfo.setServerHost("smtp.ruifucredit.com");
       mailInfo.setServerPort("25");

       mailInfo.setJmailSenderAddress("zhaomeng@ruifucredit.com");
       mailInfo.setJmailAccepterAddress("miaolihong@ruifucredit.com;zhaomeng@ruifucredit.com");
       //mailInfo.setCcAddress("*332@qq.com");
       //mailInfo.setBccAddress("*5911@qq.com");
       mailInfo.setJmailSubject("提醒！");

        //QQ邮箱

        mailInfo.setJmailSenderName("zhaomeng@ruifucredit.com");

        //QQ邮箱密码 

       mailInfo.setJmailSenderPwd(
           ",,561359mm");
      // mailInfo.setJmailAttachFile("附件路径，多个附件用;分开");
       long start = System.currentTimeMillis();
       mailInfo.setJmailBody("有异常发生");
        JMailSendUtil.SendSmtpTextMail(mailInfo);

       System.out.println("发送成功！用时："+Long.toString(System.currentTimeMillis()-start)+"毫秒");
    }    
}