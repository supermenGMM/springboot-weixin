package com.mm.jmail;

import java.util.Properties;
/*
 * 邮件对象类
 */
public class JMailInfo {
    /**
     * private constructor
     */
    private JMailInfo() {
    }
    /**
     * 
     * @author Jim Yang
     *
     */
    private static class InstanceHandle {
        private static JMailInfo mailSenderInfo = new JMailInfo();
    }
    /**
     * this function is thread-safe
     * @return MailSenderInfo
     */
    public static JMailInfo getInstance(){
        return InstanceHandle.mailSenderInfo;
    }
    /**
     * @param serverHost the serverHost to set
     */
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }
    /**
     * @return the serverHost
     */
    public String getServerHost() {
        return serverHost;
    }
    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
    /**
     * @return the serverPort
     */
    public String getServerPort() {
        return serverPort;
    }
    /**
     * @param jmailSenderAddress the jmailSenderAddress to set
     */
    public void setJmailSenderAddress(String jmailSenderAddress) {
        this.jmailSenderAddress = jmailSenderAddress;
    }
    /**
     * @return the jmailSenderAddress
     */
    public String getJmailSenderAddress() {
        return jmailSenderAddress;
    }
    /**
     * multiple address please divided by ";",like this "222@163.com;22334@qq.com;XXXX@XX.com"
     * @param jmailAccepterAddress the jmailAccepterAddress to set
     */
    public void setJmailAccepterAddress(String jmailAccepterAddress) {
        this.jmailAccepterAddress = jmailAccepterAddress;
    }
    /**
     * 
     * @return the jmailAccepterAddress
     */
    public String getJmailAccepterAddress() {
        return jmailAccepterAddress;
    }
    /**
     * @param jmailSenderName the jmailSenderName to set
     */
    public void setJmailSenderName(String jmailSenderName) {
        this.jmailSenderName = jmailSenderName;
    }
    /**
     * @return the jmailSenderName
     */
    public String getJmailSenderName() {
        return jmailSenderName;
    }
    /**
     * @param jmailSenderPwd the jmailSenderPwd to set
     */
    public void setJmailSenderPwd(String jmailSenderPwd) {
        this.jmailSenderPwd = jmailSenderPwd;
    }
    /**
     * @return the jmailSenderPwd
     */
    public String getJmailSenderPwd() {
        return jmailSenderPwd;
    }
    /**
     * @param jmailSubject the jmailSubject to set
     */
    public void setJmailSubject(String jmailSubject) {
        this.jmailSubject = jmailSubject;
    }
    /**
     * @return the jmailSubject
     */
    public String getJmailSubject() {
        return jmailSubject;
    }
    /**
     * @param jmailBody the jmailBody to set
     */
    public void setJmailBody(String jmailBody) {
        this.jmailBody = jmailBody;
    }

    /**
     * @return the jmailBody
     */
    public String getJmailBody() {
        return jmailBody;
    }
    /**
     * @param isValidated the isValidated to set
     */
    public void setValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }
    /**
     * @return the isValidated
     */
    public boolean isValidated() {
        return isValidated;
    }
    /**
     * @param jmailAttachFile the jmailAttachFile to set
     */
    public void setJmailAttachFile(String jmailAttachFile) {
        this.jmailAttachFile = jmailAttachFile;
    }
    /**
     * @return the jmailAttachFile
     */
    public String getJmailAttachFile() {
        return jmailAttachFile;
    }
    /**
     * @return the jmailProperties
     */
    public Properties getJmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", this.getServerHost());
        prop.put("mail.smpt.port", this.getServerPort());
        prop.put("mail.smtp.auth", this.isValidated());
        return prop;
    }
    /**
     * multiple address please divided by ";",like this "222@163.com;22334@qq.com;XXXX@XX.com"
     * @param ccAddress the ccAddress to set
     */
    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }
    /**
     * @return the ccAddress
     */
    public String getCcAddress() {
        return ccAddress;
    }
    /**
     * multiple address please divided by ";",like this "222@163.com;22334@qq.com;XXXX@XX.com"
     * @param bccAddress the bccAddress to set
     */
    public void setBccAddress(String bccAddress) {
        this.bccAddress = bccAddress;
    }
    /**
     * @return the bccAddress
     */
    public String getBccAddress() {
        return bccAddress;
    }
    private String serverHost = null;
    private String serverPort = null;
    private String jmailSenderAddress = null;
    private String jmailAccepterAddress = null;
    private String jmailSenderName = null;
    private String jmailSenderPwd = null;
    private String jmailSubject = null;
    private String jmailBody = null;
    private boolean isValidated = true;
    private String jmailAttachFile;
    private String ccAddress = null;
    private String bccAddress = null;
    public static final String QQMAIL_HOST = "smtp.qq.com";
    public static final String QQMAIL_PORT = "465";
}