/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.mail;

import com.sun.mail.smtp.SMTPMessage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
Outgoing Server (SMTP)
Server Settings
Description:
Server Name: smtp.gmail.com
Port: 587
Security and Authentication
Use name and password []
Username:
Password:
Use secure connection:
No,
TLS, if available
 * TLS
SSL

Incoming Server
Server Settings
Server Type: POP3 Mail Server
Server Name: pop.gmail.com
Port: 995
Username:
Password:
Security Settings
Use secure connection:
No,
TLS, if available
TLS
 * SSL
Use secure authentication
 */
/**
 *
 * @author Miro
 */
public class MailUtils implements Serializable {

    public static String DEFAULT_CHARSET = "UTF-8";
    //
    private static InheritableThreadLocal<MailUtils> instance = new InheritableThreadLocal<MailUtils>();
    //
    private MailProperties mailProperties;
    //
    private Authenticator authenticator;
    private Session session;
    private Transport sendTransport;
    private Transport receiveTransport;
    private String charset = DEFAULT_CHARSET;

    public static MailUtils getInstance(MailProperties mailProperties) {
//        MailUtils utils;
//        if ((utils = instance.get()) == null) {
//            utils = new MailUtils(mailProperties);
//            instance.set(utils);
//        }
//
//        return utils;

        return new MailUtils(mailProperties);
    }

    public static MailUtils getInstance(MailServer outgoingServer, InternetAddress from) {
        return getInstance(new MailProperties(outgoingServer, from));
    }

    public static MailUtils getInstance(MailServer outgoingServer, MailServer incomingServer, InternetAddress from) {
        return getInstance(new MailProperties(outgoingServer, incomingServer, from));
    }

    public static void removeInstance() {
        instance.remove();
    }

    protected MailUtils(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    public MailProperties getMailProperties() {
        return mailProperties;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    protected MailServer getOutgoingServer() {
        return getMailProperties().getOutgoingServer();
    }

    protected MailServer getIncomingServer() {
        return getMailProperties().getIncomingServer();
    }

    protected Authenticator getAuthenticator(MailServer mailServer) {
        return authenticator;
    }

    protected MimeMessage createMessage(MailServer mailServer, MessageParameters messageParameters)
            throws NoSuchProviderException, MessagingException, IOException {
        MimeMessage message;
        MailProtocol protocol = mailServer.getProtocol();
        switch (protocol) {
            case SMTP:
            case SMTPS:
                message = new SMTPMessage(getSession(mailServer));
                break;

            case IMAP:
            case IMAPS:
            case POP3:
            case POP3S:
            default:
                throw new UnsupportedOperationException("The protocol " + protocol + " is not implemented.");
        }

        InternetAddress from = getFrom(messageParameters);
        message.setFrom(from);

        InternetAddress replyTo = getReplyTo(messageParameters);
        if (replyTo != null) {
            message.setReplyTo(new Address[]{replyTo});
        }

        Properties messageHeaders;
        if ((messageHeaders = getMailProperties().getMessageHeaders()) != null && messageHeaders.size() > 0) {
            for (String key : messageHeaders.stringPropertyNames()) {
                String value = messageHeaders.getProperty(key);
                message.setHeader(key, value);
            }
        }

        String fromString = from.toString();
        // Reading confirmation
        Boolean booleanValue;
        if ((booleanValue = isConfirmReadingTo(messageParameters)) != null) {
            if (booleanValue) {
                message.setHeader(MailConstants.MESSAGE_HEADER_X_CONFIRM_READING_TO, fromString);
            } else {
                message.removeHeader(MailConstants.MESSAGE_HEADER_X_CONFIRM_READING_TO);
            }
        }
        if ((booleanValue = isDispositionNotificationTo(messageParameters)) != null) {
            if (booleanValue) {
                message.setHeader(MailConstants.MESSAGE_HEADER_DISPOSITION_NOTIFICATION_TO, fromString);
            } else {
                message.removeHeader(MailConstants.MESSAGE_HEADER_DISPOSITION_NOTIFICATION_TO);
            }
        }

        // Confirm receipt
        if ((booleanValue = isReturnReceiptTo(messageParameters)) != null) {
            if (booleanValue) {
                message.setHeader(MailConstants.MESSAGE_HEADER_RETURN_RECEIPT_TO, fromString);
            } else {
                message.removeHeader(MailConstants.MESSAGE_HEADER_RETURN_RECEIPT_TO);
            }
        }

        Collection<Address> addresses = messageParameters.getTo();
        message.setRecipients(Message.RecipientType.TO, addresses.toArray(new Address[addresses.size()]));

        if ((addresses = messageParameters.getCc()) != null && addresses.size() > 0) {
            message.setRecipients(Message.RecipientType.CC, addresses.toArray(new Address[addresses.size()]));
        }

        if ((addresses = messageParameters.getBcc()) != null && addresses.size() > 0) {
            message.setRecipients(Message.RecipientType.BCC, addresses.toArray(new Address[addresses.size()]));
        }

        String subject;
        if ((subject = messageParameters.getSubject()) != null) {
            message.setSubject(subject, DEFAULT_CHARSET);
        }

        Collection files = messageParameters.getFiles();
        String messageText = messageParameters.getMessageText();
        if (files != null && files.size() > 0) {
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageText, getCharset());
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

            message.setContent(mp);
        } else {
            message.setText(messageText, DEFAULT_CHARSET);
        }

        message.setSentDate(new Date());

        UUID uuid;
        if ((uuid = messageParameters.getUuid()) != null) {
            message.setHeader(MailConstants.MESSAGE_HEADER_UUID, uuid.toString());
        }

        return message;
    }

    protected Session getSession(MailServer mailServer) {
        if (session == null) {
            session = Session.getInstance(getMailProperties(), getAuthenticator(mailServer));
//            if(isDebug()) {
//                session.setDebug(true);
//            }
        }
        return session;
    }

    protected Transport getSendTransport() throws NoSuchProviderException, MessagingException {
        if (sendTransport == null) {
            sendTransport = getTransport(getOutgoingServer());
        }

        return sendTransport;
    }

    protected Transport getReceiveTransport() throws NoSuchProviderException, MessagingException {
        if (receiveTransport == null) {
            receiveTransport = getTransport(getIncomingServer());
        }

        return receiveTransport;
    }

    protected Transport getTransport(MailServer mailServer) throws NoSuchProviderException, MessagingException {
        Transport transport = getSession(mailServer).getTransport(mailServer.getProtocol().getProtocolName());

        if (!transport.isConnected()) {
            Boolean booleanValue;
            MailAuthentication authentication = mailServer.getAuthentication();
            if ((booleanValue = authentication.isNameAndPasswordAuthentication()) != null && booleanValue) {
                String host = mailServer.getServerName();
                int port = mailServer.getServerPort();
                String username = authentication.getUsername();
                String password = authentication.getPassword();
                transport.connect(host, port, username, password);
            } else {
                transport.connect();
            }
        }

        return transport;
    }

    protected InternetAddress getFrom(MessageParameters messageParameters) {
        return getMailProperties().getFrom();
    }

    protected InternetAddress getReplyTo(MessageParameters messageParameters) {
        InternetAddress replyTo;
        if ((replyTo = messageParameters.getReplyTo()) != null) {
            return replyTo;
        }

        return getMailProperties().getReplyTo();
    }

    protected Boolean isConfirmReadingTo(MessageParameters messageParameters) {
        return messageParameters.isConfirmReadingTo();
    }

    protected Boolean isDispositionNotificationTo(MessageParameters messageParameters) {
        return messageParameters.isDispositionNotificationTo();
    }

    protected Boolean isReturnReceiptTo(MessageParameters messageParameters) {
        return messageParameters.isReturnReceiptTo();
    }

    public void sendMessage(MessageParameters messageParameters)
            throws NoSuchProviderException, MessagingException, IOException {
        MimeMessage message = createMessage(getOutgoingServer(), messageParameters);

        Transport transport = getSendTransport();

        transport.sendMessage(message, message.getAllRecipients());

//        if (sender.isStoreToIMAP()) {
//            Folder folder = getStore().getFolder(sender.getImapFolder());
//            if (folder == null) {
//                System.err.println("Can't get record folder where to store the sent message.");
//                return;
//            }
//            if (!folder.exists()) {
//                folder.create(Folder.HOLDS_MESSAGES);
//            }
//
//            Message[] msgs = new Message[1];
//            msgs[0] = msg;
//            folder.appendMessages(msgs);
//        }
    }

    public void setDebug(boolean debug) {
        getMailProperties().setDebug(debug);
    }

    public boolean getDebug() {
        Boolean value;
        if ((value = getMailProperties().getDebug()) != null && value) {
            return value;
        }

        return false;
    }

    private static final String PK_OUTGOING = "outgoing.";
    private static final String PK_PROPERTIES = "properties.";
    private static final String PK_AUTHENTICATION = "authentication.";
    private static final String PK_SECURITY = "security.";
    private static final String PK_PROTOCOL = "protocol";
    private static final String PK_SERVER_NAME = "serverName";
    private static final String PK_SERVER_PORT = "serverPort";
    //
    private static final String PK_USERNAME = "username";
    private static final String PK_PASSWORD = "password";
    private static final String PK_NAME_AND_PASS_AUTH = "nameAndPasswordAuthentication";
    //
    private static final String PK_SECURE_CONNECTION = "secureConnection";
    private static final String PK_SECURE_AUTHENTICATION = "secureAuthentication";

    //
    private static final String PK_FROM_ADDRESS = "fromAddress.";
    private static final String PK_ADDRESS = "address";
    private static final String PK_PERSONAL = "personal";

    public static MailUtils load(Properties properties) {
        MailServer outgoingServer = loadMailServer(getPropertiesWithPrefix(properties, PK_OUTGOING));
        InternetAddress fromAddress = loadInternetAddress(getPropertiesWithPrefix(properties, PK_FROM_ADDRESS));

        return getInstance(outgoingServer, fromAddress);
    }

//# MailServer
//outgoing.protocol=SMTP
//outgoing.serverName=smtp.gmail.com
//outgoing.serverPort=587
//# Properties
//outgoing.properties.mail.smtp.allow8bitmime=true
//outgoing.properties.mail.smtp.connectiontimeout=60000
//outgoing.properties.mail.smtp.timeout=true
    private static MailServer loadMailServer(Properties properties) {
        MailAuthentication authentication = loadMailAuthentication(getPropertiesWithPrefix(properties, PK_AUTHENTICATION));
        MailSecurity security = loadMailSecurity(getPropertiesWithPrefix(properties, PK_SECURITY));

        MailProtocol protocol = MailProtocol.valueOf(properties.getProperty(PK_PROTOCOL));
        String serverName = properties.getProperty(PK_SERVER_NAME);
        int serverPort = Integer.parseInt(properties.getProperty(PK_SERVER_PORT));

        MailServer server = new MailServer(protocol, serverName, serverPort, authentication, security);

        Properties props = getPropertiesWithPrefix(properties, PK_PROPERTIES);
        for(String key : props.stringPropertyNames()) {
            server.setProperty(key, props.getProperty(key));
        }

        return server;
    }

//# MailAuthentication
//outgoing.authentication.password=Acac1aBusiness@ce
//outgoing.authentication.username=acacia-mail@space-comm.com
//outgoing.authentication.nameAndPasswordAuthentication=true
    private static MailAuthentication loadMailAuthentication(Properties properties) {
        MailAuthentication authentication = new MailAuthentication(
                properties.getProperty(PK_USERNAME),
                properties.getProperty(PK_PASSWORD));
        String value;
        if((value = properties.getProperty(PK_NAME_AND_PASS_AUTH)) != null) {
            authentication.setNameAndPasswordAuthentication(Boolean.valueOf(value));
        }

        return authentication;
    }

//# MailSecurity
//outgoing.security.secureConnection=Tls
//#outgoing.security.secureAuthentication=false
    private static MailSecurity loadMailSecurity(Properties properties) {
        MailSecurity security = new MailSecurity(SecureConnection.valueOf(properties.getProperty(PK_SECURE_CONNECTION)));
        String value;
        if((value = properties.getProperty(PK_SECURE_AUTHENTICATION)) != null) {
            security.setSecureAuthentication(Boolean.valueOf(value));
        }

        return security;
    }

//# InternetAddress
//fromAddress.address=acacia-mail@space-comm.com
//fromAddress.personal=Acacia Business Ace
    private static InternetAddress loadInternetAddress(Properties properties) {
        String address = properties.getProperty(PK_ADDRESS);
        String personal;
        try {
            if((personal = properties.getProperty(PK_PERSONAL)) != null) {
                return new InternetAddress(address, personal);
            } else {
                return new InternetAddress(address);
            }
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Properties getPropertiesWithPrefix(Properties source, String prefix) {
        int beginIndex = prefix.length();
        Properties properties = new Properties();
        for(String key : source.stringPropertyNames()) {
            if(key.startsWith(prefix)) {
                String newKey = key.substring(beginIndex);
                properties.setProperty(newKey, source.getProperty(key));
            }
        }

        return properties;
    }
}
