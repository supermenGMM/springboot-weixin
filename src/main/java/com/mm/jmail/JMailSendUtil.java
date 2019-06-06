package com.mm.jmail;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
/**
 * @author JimYang
 */
public class JMailSendUtil {
   private JMailSendUtil() {};
   /**
    * 发送纯文本格式邮件，可以发给多人，可以添加附件，抄送，暗抄送。
    * @param jmailInfo
    * @throws MessagingException
    * @throws IOException
    */
   public static void SendSmtpTextMail(JMailInfo jmailInfo) 
     throws MessagingException, IOException{
    Properties prop = jmailInfo.getJmailProperties();
    prop.put("mail.transport.protocol","smtp");
    Authenticator authenticator = getAuth(jmailInfo.getJmailSenderName(), jmailInfo.getJmailSenderPwd());
    Session jmailSession = Session.getDefaultInstance(prop, authenticator);
    Address senderAddress = new InternetAddress(jmailInfo.getJmailSenderAddress());
    Message mmsg = new MimeMessage(jmailSession);
    mmsg.setFrom(senderAddress);
    if (!checkNULL(jmailInfo.getJmailAccepterAddress())) {
       mmsg.addRecipients(RecipientType.TO, addressBuilder(jmailInfo.getJmailAccepterAddress()));
    }
    if (!checkNULL(jmailInfo.getCcAddress())) {
         mmsg.addRecipients(RecipientType.CC, addressBuilder(jmailInfo.getCcAddress()));
     }
    if (!checkNULL(jmailInfo.getBccAddress())) {
         mmsg.addRecipients(RecipientType.BCC, addressBuilder(jmailInfo.getBccAddress()));
     }
    mmsg.setSubject(jmailInfo.getJmailSubject());
    mmsg.setSentDate(new Date());
    Multipart multipart = new MimeMultipart();
    BodyPart bodyPart = new MimeBodyPart();
    bodyPart.setContent(jmailInfo.getJmailBody(), "text/plain;charset=UTF-8");
    multipart.addBodyPart(bodyPart);
    if (!checkNULL(jmailInfo.getJmailAttachFile())) {
        buildAttachFile(multipart, jmailInfo.getJmailAttachFile());
        mmsg.setContent(multipart);
    }
    mmsg.setContent(multipart);
    mmsg.saveChanges();
    Transport.send(mmsg);
    }
   /**
    * 发送HTML格式邮件，可CC，bcc,发给多人，多个附件
    * @param jmailInfo
    * @throws MessagingException
    * @throws IOException
    */
public static void SendSmtpHtmlMail(JMailInfo jmailInfo) throws MessagingException, IOException {
     Properties prop = jmailInfo.getJmailProperties();
        prop.put("mail.transport.protocol","smtp");
        Authenticator authenticator = getAuth(jmailInfo.getJmailSenderName(), jmailInfo.getJmailSenderPwd());
        Session jmailSession = Session.getDefaultInstance(prop, authenticator);
        Address senderAddress = new InternetAddress(jmailInfo.getJmailSenderAddress());
        Message mmsg = new MimeMessage(jmailSession);
        mmsg.setFrom(senderAddress);
        if (!checkNULL(jmailInfo.getJmailAccepterAddress())) {
           mmsg.addRecipients(RecipientType.TO, addressBuilder(jmailInfo.getJmailAccepterAddress()));
        }
        if (!checkNULL(jmailInfo.getCcAddress())) {
             mmsg.addRecipients(RecipientType.CC, addressBuilder(jmailInfo.getCcAddress()));
         }
        if (!checkNULL(jmailInfo.getBccAddress())) {
             mmsg.addRecipients(RecipientType.BCC, addressBuilder(jmailInfo.getBccAddress()));
         }
        mmsg.setSubject(jmailInfo.getJmailSubject());
        mmsg.setSentDate(new Date());
        Multipart multipart = new  MimeMultipart();
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(jmailInfo.getJmailBody(), "text/html;charset=UTF-8");
        multipart.addBodyPart(bodyPart);
        if (!checkNULL(jmailInfo.getJmailAttachFile())) {
            BodyPart[] bodyParts = BuildAttachmentFile(jmailInfo.getJmailAttachFile());
            for (BodyPart body:bodyParts) {
                multipart.addBodyPart(body);
            }
        }
        mmsg.setContent(multipart);
        mmsg.saveChanges();
        Transport.send(mmsg);
}
/**
 * 
 * @param mailInfo 邮件主体
 * @param delay 延迟时间
 * @param timeUnit 时间单位(时，分，秒，天，毫秒...)
 * @param htmlFlag 是否为HTML邮件
 */
public static void SendTimeScheduleSmtpMail(final JMailInfo mailInfo,long delay,TimeUnit timeUnit,final boolean htmlFlag) {
    final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
 scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
    scheduledThreadPoolExecutor.schedule(new Runnable() {
        @Override
        public void run() {
                try {
                    if (!htmlFlag) {
                        SendSmtpTextMail(mailInfo);
                    } else {
                    SendSmtpHtmlMail(mailInfo);}
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
        }
    },delay, timeUnit);
    scheduledThreadPoolExecutor.shutdown();
}
/**
 * 定时重发邮件，支持纯文本和HTML，后者可以直接加入HTML标签来布局邮件显示效果。<br>
 * 邮件重发次数为java <code>int</code>类型，注意取值范围
 * @param mailInfo 邮件信息
 * @param delayTime 延迟时间
 * @param repeatStep 重发间隔时间
 * @param repeatTimes 重发次数
 * @param timeUnit 时间单位
 * @param htmlFlag 是否发送HTML邮件
 */
public static void SendTimeScheduleRepeatSmtpMail(final JMailInfo mailInfo,long delayTime,long repeatStep,final int repeatTimes,TimeUnit timeUnit,final boolean htmlFlag) {
    final ScheduledThreadPoolExecutor schThPoolExce = new ScheduledThreadPoolExecutor(2);
 schThPoolExce.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
schThPoolExce.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
  schThPoolExce.scheduleAtFixedRate(new Runnable() {
    //定时重复执行自定义任务
        int i=0;
        @Override
        public void run() {
            ++i;
            int j = repeatTimes;
            if (i <= j) {
            try {
                //System.out.println("第 "+i+"份邮件开始发送。。");
                if (htmlFlag) {
                    SendSmtpHtmlMail(mailInfo);
                } else {
                    SendSmtpTextMail(mailInfo);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            //System.out.println("第 "+i+"份邮件发送完成。。。");
            }
        } else {
         //System.out.println("定时重复任务完成，共重复发送 "+(i>1?(i-1):i)+"份邮件。");
            schThPoolExce.shutdownNow();
        }
    }},delayTime, repeatStep, timeUnit);
    schThPoolExce.shutdown();
}
/**
    * 邮箱登陆权限验证获取
    * @param userName 邮箱登陆用户名
    * @param userPwd 登陆密码
    * @return
    */
   private static Authenticator getAuth(String userName,String userPwd) {
      Object lock = new Object();
      final String name = userName;
      final String pwd = userPwd;
      Authenticator authenticator = null;
      synchronized (lock) {
           authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(name,pwd);
            }
        };
      }
    return authenticator;
   }
   /**
    * 解析邮件地多个地址用英文分号隔开，返回邮件地址数组
    * @param addressStr
    * @return
    */
   private static Address [] addressBuilder(String addressStr) {
       String [] temp = null;
       Address[] tempAddressGroup = null;
       try {
        if (addressStr.indexOf(";") != -1) {
               temp = addressStr.split(";");
               tempAddressGroup = new InternetAddress[temp.length];
               for (int i=0;i<temp.length;i++) {
                   tempAddressGroup[i] = new InternetAddress(temp[i]);
               }
           } else {
              tempAddressGroup = new InternetAddress[]{new InternetAddress(addressStr)};
           }
    } catch (AddressException e) {
        e.printStackTrace();
    }
    return tempAddressGroup;
   }
   private static boolean checkNULL(String addressStr){
       if (addressStr == null) {
           return true;
       } else if (addressStr.trim().equals("")) {
        return true;
       }else {
           return false;
       }
   }
   /**
    * 解析附件内容，返回一个或者多个附件
    * @param filePaths 附件地址，多个地址用英文的分号(;)号隔开
    * @return
    * @throws MessagingException
    * @throws IOException
    */
   private static BodyPart[] BuildAttachmentFile(String filePaths) throws MessagingException, IOException{
       BodyPart[] parts = null;
       if (filePaths.indexOf(";") != -1) {
           String [] paths = filePaths.split(";");
           parts = new MimeBodyPart[paths.length];
           for (int i = 0;i<paths.length;i++) {
                   FileDataSource fileDataSource = new FileDataSource(paths[i]);
                   MimeBodyPart body = new MimeBodyPart();
                   body.setFileName(MimeUtility.encodeWord(fileDataSource.getName()));
                   body.setDataHandler(new DataHandler(fileDataSource));
                   parts[i] = body;
           }
       } else {
           MimeBodyPart body = new MimeBodyPart();
           FileDataSource fds = new FileDataSource(filePaths);
           body.setDataHandler(new DataHandler(fds));
           body.setFileName(MimeUtility.encodeWord(fds.getName()));
           parts = new BodyPart[]{body};
           
       }
    return parts;
   }
   /**
    * 解析附件路径，并且将解析成功后的附件加入到附件对象的主体，作为邮件附件发送。
    * @param multipart 附件主体
    * @param filePaths 附件路径
    */
   private static void buildAttachFile(Multipart multipart,String filePaths) {
       try {
        if (filePaths.indexOf(";") != -1) {
               String [] paths = filePaths.split(";");
               for (int i = 0;i<paths.length;i++) {
                       FileDataSource fileDataSource = new FileDataSource(paths[i]);
                       MimeBodyPart body = new MimeBodyPart();
                       body.setFileName(MimeUtility.encodeWord(fileDataSource.getName()));
            body.setDataHandler(new DataHandler(fileDataSource));
            multipart.addBodyPart(body);
               }
           } else {
               MimeBodyPart body = new MimeBodyPart();
               FileDataSource fds = new FileDataSource(filePaths);
               body.setDataHandler(new DataHandler(fds));
      //对标题编码，不然附件无法显示出来         body.setFileName(MimeUtility.encodeWord(fds.getName()));
               multipart.addBodyPart(body);
           }
    } catch (MessagingException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
   }
}

