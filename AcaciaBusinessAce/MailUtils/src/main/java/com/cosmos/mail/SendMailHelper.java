package com.cosmos.mail;

import java.io.IOException;
import java.util.Date;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Address;
import java.util.Arrays;
import java.util.Collection;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.UUID;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SendMailHelper {

    public static String DEFAULT_CHARSET = "UTF-8";
    //private boolean debug;
    private SenderDetails sender;
    private Session session;
    private SMTPTransport smtpTransport;
    private Store store;

    // props.put("mail.debug", "true");
    // session.setDebug(true) or -Dmail.debug=true
    // java -Djava.security.debug=help MyClass
    // System.getProperties()
    // -Djavax.net.debug=help
    // java -Djavax.net.debug=help MyApp
    public SendMailHelper(SenderDetails sender) {
        this.sender = sender;
        //debug = sender.isDebugEnabled();
    }

    public void close()
            throws MessagingException {
        if (smtpTransport != null && smtpTransport.isConnected()) {
            smtpTransport.close();
        }
        if (store != null && store.isConnected()) {
            store.close();
        }
    }

    public void sendMessage(Address to, String message, String subject)
            throws NoSuchProviderException,
            MessagingException,
            IOException {
        sendMessage(to, message, subject, null);
    }

    public void sendMessage(Address to, String message, String subject, UUID uuid)
            throws NoSuchProviderException,
            MessagingException,
            IOException {
        sendMessage(Arrays.<Address>asList(to), message, subject, uuid);
    }

    public void sendMessage(
            Collection<Address> to,
            String message,
            String subject,
            UUID uuid)
            throws NoSuchProviderException,
            MessagingException,
            IOException {
        sendMessage(to, message, subject, null, uuid);
    }

    public void sendMessage(
            Address to,
            String message,
            String subject,
            Collection files,
            UUID uuid)
            throws NoSuchProviderException,
            MessagingException,
            IOException {
        sendMessage(Arrays.<Address>asList(to), message, subject, files, uuid);
    }

    public void sendMessage(Collection<Address> to,
            String message,
            String subject,
            Collection files,
            UUID uuid)
            throws NoSuchProviderException,
            MessagingException,
            IOException {
        sendMessage(to, message, subject, files, null, uuid);
    }

    public void sendMessage(Collection<Address> to,
            String message,
            String subject,
            Collection files,
            Collection<Address> cc,
            UUID uuid)
            throws NoSuchProviderException,
            MessagingException,
            IOException {
        sendMessage(to, message, subject, files, cc, null, uuid);
    }

    public void sendMessage(
            Collection<Address> to,
            String message,
            String subject,
            Collection files,
            Collection<Address> cc,
            Collection<Address> bcc,
            UUID uuid)
            throws NoSuchProviderException,
            MessagingException,
            IOException {
        SMTPMessage msg = new SMTPMessage(getSession());

        InternetAddress from = sender.getFrom();

        msg.setFrom(from);
        InternetAddress replyTo = sender.getReplyTo();
        if (replyTo != null) {
            msg.setReplyTo(new Address[]{replyTo});
        }

        String fromString = from.toString();

        // Reading confirmation
        msg.setHeader("X-Confirm-Reading-To", fromString);
        msg.setHeader("Disposition-Notification-To", fromString);

        // Confirm receipt
        msg.setHeader("Return-Receipt-To", fromString);

        msg.setRecipients(Message.RecipientType.TO, to.toArray(new Address[to.size()]));
        if (cc != null && cc.size() > 0) {
            msg.setRecipients(Message.RecipientType.CC, cc.toArray(new Address[cc.size()]));
        }
        if (bcc != null && bcc.size() > 0) {
            msg.setRecipients(Message.RecipientType.BCC, bcc.toArray(new Address[bcc.size()]));
        }

        if (subject != null) {
            msg.setSubject(subject, DEFAULT_CHARSET);
        }

        if (files != null && files.size() > 0) {
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message, DEFAULT_CHARSET);
            MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart(messageBodyPart);
            for (Object fileObject : files) {
                File file;
                if (fileObject instanceof File) {
                    file = (File) fileObject;
                } else {
                    file = new File((String) fileObject);
                }
                MimeBodyPart fileBodyPart = new MimeBodyPart();
                fileBodyPart.attachFile(file);
                mp.addBodyPart(fileBodyPart);
            }

            msg.setContent(mp);
        } else {
            msg.setText(message, DEFAULT_CHARSET);
        }

        msg.setHeader("X-Mailer", "Cosmopolitan Mailer - COSMOS Software Enterprises, Ltd.");
        msg.setSentDate(new Date());

        if (uuid != null) {
            msg.setHeader(MailConstants.MESSAGE_HEADER_UUID, uuid.toString());
        }

        SMTPTransport transport = getSMTPTransport();

        transport.sendMessage(msg, msg.getAllRecipients());

        if (sender.isStoreToIMAP()) {
            Folder folder = getStore().getFolder(sender.getImapFolder());
            if (folder == null) {
                System.err.println("Can't get record folder where to store the sent message.");
                return;
            }
            if (!folder.exists()) {
                folder.create(Folder.HOLDS_MESSAGES);
            }

            Message[] msgs = new Message[1];
            msgs[0] = msg;
            folder.appendMessages(msgs);
        }
    }

    private Session getSession() {
        if (session == null) {
            session = Session.getInstance(sender.getMailProperties(), null);
            /*if(debug)
            {
            session.setDebug(true);
            }*/
        }
        return session;
    }

    private SMTPTransport getSMTPTransport()
            throws NoSuchProviderException,
            MessagingException {
        if (smtpTransport == null) {
            smtpTransport = (SMTPTransport) getSession().getTransport(sender.getSmtpProtocol());
        }

        if (!smtpTransport.isConnected()) {
            if (sender.isSmtpAuthentication()) {
                String host = sender.getSmtpHost();
                String user = sender.getSmtpUsername();
                String password = sender.getSmtpPassword();
                smtpTransport.connect(host, user, password);
            } else {
                smtpTransport.connect();
            }
        }

        return smtpTransport;
    }

    private Store getStore()
            throws NoSuchProviderException,
            MessagingException {
        if (store == null) {
            String protocol = sender.getImapProtocol();
            if (protocol != null) {
                store = getSession().getStore(protocol);
            } else {
                store = getSession().getStore();
            }
        }

        if (!store.isConnected()) {
            String host = sender.getImapHost();
            String user = sender.getImapUsername();
            String password = sender.getImapPassword();
            if (host != null || user != null || password != null) {
                store.connect(host, user, password);
            } else {
                store.connect();
            }
        }

        return store;
    }

    public static SenderDetails getSenderDetails()
            throws UnsupportedEncodingException,
            AddressException {
        Properties properties = new Properties();
        String address = properties.getProperty("from.address");
        String personal = properties.getProperty("from.personal");
        InternetAddress from;
        if (personal != null) {
            from = new InternetAddress(address, personal);
        } else {
            from = new InternetAddress(address);
        }

        SenderDetails sender = new SenderDetails(
                properties.getProperty("smtp.host"),
                properties.getProperty("smtp.username"),
                properties.getProperty("smtp.password"),
                from, properties);

        sender.setStoreToIMAP(Boolean.parseBoolean(properties.getProperty("storeToIMAP")));
        sender.setImapHost(properties.getProperty("imap.host"));
        sender.setImapProtocol(properties.getProperty("imap.protocol"));
        sender.setImapUsername(properties.getProperty("imap.username"));
        sender.setImapPassword(properties.getProperty("imap.password"));
        sender.setImapFolder(properties.getProperty("imapFolder"));

        address = properties.getProperty("reply.to.address");
        personal = properties.getProperty("reply.to.personal");
        if (address != null) {
            if (personal != null) {
                from = new InternetAddress(address, personal);
            } else {
                from = new InternetAddress(address);
            }
            sender.setReplyTo(from);
        }

        return sender;
    }
}
