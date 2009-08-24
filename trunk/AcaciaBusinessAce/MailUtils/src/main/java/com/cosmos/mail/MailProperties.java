/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.mail;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Miro
 */
public class MailProperties extends Properties implements Serializable {

    public static final String MH_MAIL_DEBUG = "mail.debug";
    private static final String MAILER_NAME = "Cosmopolitan Mailer - COSMOS Software Enterprises, Ltd.";
    //
    private MailServer outgoingServer;
    private MailServer incomingServer;
    private InternetAddress from;
    private InternetAddress replyTo;
    private Properties messageHeaders;

    public MailProperties(MailServer outgoingServer, InternetAddress from) {
        this(outgoingServer, null, from);
    }

    public MailProperties(MailServer outgoingServer, MailServer incomingServer, InternetAddress from) {
        setOutgoingServer(outgoingServer);
        setIncomingServer(incomingServer);
        this.from = from;
        setMessageHeader(MailConstants.MESSAGE_HEADER_X_MAILER, MAILER_NAME);
    }


    protected void setProperties(MailServer mailServer) {
        if(mailServer == null) {
            return;
        }

        Properties props;
        if((props = mailServer.getMessageHeaders()) != null && props.size() > 0) {
            for(String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                setMessageHeader(key, value);
            }
        }

        props = mailServer.getProperties();
        for(String key : props.stringPropertyNames()) {
            String value = props.getProperty(key);
            setProperty(key, value);
        }
    }

    public void setIncomingServer(MailServer incomingServer) {
        this.incomingServer = incomingServer;
        if(incomingServer != null) {
            setProperties(incomingServer);
        }
    }

    public MailServer getIncomingServer() {
        return incomingServer;
    }

    public void setOutgoingServer(MailServer outgoingServer) {
        this.outgoingServer = outgoingServer;
        setProperties(outgoingServer);
    }

    public MailServer getOutgoingServer() {
        return outgoingServer;
    }

    public InternetAddress getFrom() {
        return from;
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    public InternetAddress getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(InternetAddress replyTo) {
        this.replyTo = replyTo;
    }

    public Object setDebug(Boolean debug) {
        if(debug != null) {
            return setProperty(MH_MAIL_DEBUG, debug.toString());
        } else {
            return remove(MH_MAIL_DEBUG);
        }
    }

    public Boolean getDebug() {
        String value;
        if((value = getMessageHeader(MH_MAIL_DEBUG)) == null) {
            return null;
        }

        return Boolean.valueOf(value);
    }

    public Properties getMessageHeaders() {
        return messageHeaders;
    }

    public void setMessageHeaders(Properties messageHeaders) {
        this.messageHeaders = messageHeaders;
    }

    public Object setMessageHeader(String key, String value) {
        if(messageHeaders == null) {
            messageHeaders = new Properties();
        }

        if(value != null) {
            return messageHeaders.setProperty(key, value);
        } else {
            return messageHeaders.remove(key);
        }
    }

    public String getMessageHeader(String key) {
        if(messageHeaders == null) {
            return null;
        }

        return messageHeaders.getProperty(key);
    }

    public Object removeMessageHeader(String key) {
        if(messageHeaders == null) {
            return null;
        }

        return messageHeaders.remove(key);
    }
}
