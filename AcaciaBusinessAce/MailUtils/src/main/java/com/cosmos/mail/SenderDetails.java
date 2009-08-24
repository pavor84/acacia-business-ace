package com.cosmos.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Map;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: COSMOS Software Enterprises, Ltd.</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SenderDetails {

    private String smtpHost;
    private String smtpProtocol = "smtp"; // smtp, smtps
    private String smtpUsername;
    private String smtpPassword;
    private InternetAddress from;
    private InternetAddress replyTo;
    private boolean storeToIMAP = false;
    private String imapHost;
    private String imapProtocol = "imap"; // imap, imaps
    private String imapUsername;
    private String imapPassword;
    private String imapFolder = "INBOX";
    private Properties mailProperties;
    private boolean debugEnabled = false;

    public SenderDetails(String smtpHost,
            String smtpUsername,
            String smtpPassword,
            String from,
            Properties mailProperties)
            throws AddressException {
        this(smtpHost, smtpUsername, smtpPassword, new InternetAddress(from), mailProperties);
    }

    public SenderDetails(String smtpHost,
            String smtpUsername,
            String smtpPassword,
            InternetAddress from,
            Properties mailProperties) {
        this(smtpHost, "smtp", smtpUsername, smtpPassword, from, mailProperties);
    }

    public SenderDetails(String smtpHost,
            String smtpProtocol,
            String smtpUsername,
            String smtpPassword,
            InternetAddress from,
            Properties mailProperties) {
        this.smtpHost = smtpHost;
        this.smtpProtocol = smtpProtocol;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
        this.from = from;
        this.mailProperties = mailProperties;
    }

    public SenderDetails(Map properties)
            throws AddressException,
            UnsupportedEncodingException {
        mailProperties = new Properties();
        mailProperties.putAll(properties);
        smtpHost = mailProperties.getProperty("mail.smtp.host");
        smtpProtocol = mailProperties.getProperty("mail.smtp.protocol");
        smtpUsername = mailProperties.getProperty("mail.smtp.user");
        smtpPassword = mailProperties.getProperty("mail.smtp.password");

        from = getSenderEmailAddress(mailProperties);

        String fromStr = mailProperties.getProperty("mail.smtp.replyto");
        if (fromStr != null && (fromStr = fromStr.trim()).length() > 0) {
            replyTo = new InternetAddress(fromStr);
        }
    }

    public InternetAddress getFrom() {
        return from;
    }

    public String getImapFolder() {
        return imapFolder;
    }

    public String getImapHost() {
        return imapHost;
    }

    public String getImapPassword() {
        return imapPassword;
    }

    public String getImapProtocol() {
        return imapProtocol;
    }

    public String getImapUsername() {
        return imapUsername;
    }

    public Properties getMailProperties() {
        return mailProperties;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public String getSmtpProtocol() {
        return smtpProtocol;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public boolean isStoreToIMAP() {
        return storeToIMAP;
    }

    public InternetAddress getReplyTo() {
        return replyTo;
    }

    public boolean isSmtpAuthentication() {
        return Boolean.parseBoolean(mailProperties.getProperty("mail.smtp.auth", "false"));
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    public void setImapFolder(String imapFolder) {
        this.imapFolder = imapFolder;
    }

    public void setImapHost(String imapHost) {
        this.imapHost = imapHost;
    }

    public void setImapPassword(String imapPassword) {
        this.imapPassword = imapPassword;
    }

    public void setImapProtocol(String imapProtocol) {
        this.imapProtocol = imapProtocol;
    }

    public void setImapUsername(String imapUsername) {
        this.imapUsername = imapUsername;
    }

    public void setMailProperties(Properties mailProperties) {
        this.mailProperties = mailProperties;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public void setSmtpProtocol(String smtpProtocol) {
        this.smtpProtocol = smtpProtocol;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public void setStoreToIMAP(boolean storeToIMAP) {
        this.storeToIMAP = storeToIMAP;
    }

    public void setReplyTo(InternetAddress replyTo) {
        this.replyTo = replyTo;
    }

    public void setSmtpAuthentication(boolean smtpAuthentication) {
        mailProperties.setProperty("mail.smtp.auth", Boolean.toString(smtpAuthentication));
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public static InternetAddress getSenderEmailAddress(Properties appConfig)
            throws UnsupportedEncodingException,
            AddressException {
        String fromStr = appConfig.getProperty("mail.smtp.from");
        if (fromStr != null && (fromStr = fromStr.trim()).length() > 0) {
            String name = appConfig.getProperty("personal.name");
            String organization = appConfig.getProperty("organization");
            String personal = null;
            if (name != null) {
                if (organization != null) {
                    personal = name + " (" + organization + ")";
                } else {
                    personal = name;
                }
            }
            if (personal != null) {
                return new InternetAddress(fromStr, personal);
            } else {
                return new InternetAddress(fromStr);
            }
        }

        return null;
    }
}
