/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.mail;

import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author Miro
 */
public class MailServer implements Serializable {

    public static final Integer DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final Integer DEFAULT_TIMEOUT = 60000;

    //
    private static final String PREFIX_MAIL = "mail.";
    private static final String SUFFIX_HOST = ".host";
    private static final String SUFFIX_PORT = ".port";
    private static final String SUFFIX_STARTTLS_ENABLE = ".starttls.enable";
    private static final String SUFFIX_STARTTLS_REQUIRED = ".starttls.required";
    private static final String SUFFIX_SSL_ENABLE = ".ssl.enable";
    private static final String SUFFIX_AUTH = ".auth";
    private static final String SUFFIX_USER = ".user";
    private static final String SUFFIX_PASSWORD = ".password";
    //
    private static final String SUFFIX_CONNECTION_TIMEOUT = ".connectiontimeout";
    private static final String SUFFIX_TIMEOUT = ".timeout";
    private static final String SUFFIX_ALLOW8BITMIME = ".allow8bitmime";
    //
    private MailProtocol protocol;
    private String serverName;
    private Integer serverPort;
    private MailAuthentication authentication;
    private MailSecurity security;
    private String description;
    private Properties properties = new Properties();
    private Properties messageHeaders;

    public MailServer() {
    }

    public MailServer(
            MailProtocol protocol,
            String serverName,
            Integer serverPort,
            MailAuthentication authentication,
            MailSecurity security,
            String description) {
        this.protocol = protocol;
        this.description = description;

        setServerName(serverName);
        setServerPort(serverPort);
        setAuthentication(authentication);
        setSecurity(security);

        setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
        setTimeout(DEFAULT_TIMEOUT);
        setPropertySuffix(SUFFIX_ALLOW8BITMIME, Boolean.TRUE.toString());
    }

    public MailServer(
            MailProtocol protocol,
            String serverName,
            Integer serverPort,
            MailAuthentication authentication,
            MailSecurity security) {
        this(protocol, serverName, serverPort, authentication, security, null);
    }

    public MailAuthentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(MailAuthentication authentication) {
        this.authentication = authentication;
        switch(getProtocol()) {
            case SMTP: case SMTPS:
                Boolean booleanValue;
                if((booleanValue = authentication.isNameAndPasswordAuthentication()) != null) {
                    setPropertySuffix(SUFFIX_AUTH, booleanValue.toString());
                } else {
                    removePropertySuffix(SUFFIX_AUTH);
                }
                break;
        }

        String value;
        if((value = authentication.getUsername()) != null) {
            setPropertySuffix(SUFFIX_USER, value);
        } else {
            removePropertySuffix(SUFFIX_USER);
        }
        if((value = authentication.getPassword()) != null) {
            setPropertySuffix(SUFFIX_PASSWORD, value);
        } else {
            removePropertySuffix(SUFFIX_PASSWORD);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MailProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(MailProtocol protocol) {
        this.protocol = protocol;
    }

    public MailSecurity getSecurity() {
        return security;
    }

    public void setSecurity(MailSecurity security) {
        this.security = security;
        if(messageHeaders != null) {
            removePropertySuffix(SUFFIX_STARTTLS_REQUIRED);
            removePropertySuffix(SUFFIX_STARTTLS_ENABLE);
            removePropertySuffix(SUFFIX_SSL_ENABLE);
        }
        switch(security.getSecureConnection()) {
            case Tls:
                setPropertySuffix(SUFFIX_STARTTLS_REQUIRED, Boolean.TRUE.toString());
            case TlsIfAvailable:
                setPropertySuffix(SUFFIX_STARTTLS_ENABLE, Boolean.TRUE.toString());
                break;
            case Ssl:
                setPropertySuffix(SUFFIX_SSL_ENABLE, Boolean.TRUE.toString());
                break;
        }

        switch(getProtocol()) {
            case IMAP: case IMAPS:
            case POP3: case POP3S:
                Boolean booleanValue;
                if((booleanValue = security.isSecureAuthentication()) != null) {
                    // ToDo
                    //setPropertySuffix(SUFFIX_???, booleanValue.toString());
                }
                break;
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
        setPropertySuffix(SUFFIX_HOST, serverName);
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
        if(serverPort != null) {
            setPropertySuffix(SUFFIX_PORT, serverPort.toString());
        } else {
            removePropertySuffix(SUFFIX_PORT);
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    protected Object setProperty(String key, String value) {
        return properties.setProperty(key, value);
    }

    protected String getProperty(String key) {
        return properties.getProperty(key);
    }

    protected Object removeProperty(String key) {
        return properties.remove(key);
    }

    protected Object setPropertySuffix(String suffix, String value) {
        return setProperty(PREFIX_MAIL + getProtocol().getProtocolName() + suffix, value);
    }

    protected String getPropertySuffix(String suffix) {
        return getProperty(PREFIX_MAIL + getProtocol().getProtocolName() + suffix);
    }

    public Object removePropertySuffix(String suffix) {
        return removeProperty(PREFIX_MAIL + getProtocol().getProtocolName() + suffix);
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

    public Object setConnectionTimeout(Integer timeout) {
        if(timeout != null) {
            return setPropertySuffix(SUFFIX_CONNECTION_TIMEOUT, Integer.toString(timeout));
        } else {
            return removePropertySuffix(SUFFIX_CONNECTION_TIMEOUT);
        }
    }

    public Object setTimeout(Integer timeout) {
        if(timeout != null) {
            return setPropertySuffix(SUFFIX_TIMEOUT, Integer.toString(timeout));
        } else {
            return removePropertySuffix(SUFFIX_TIMEOUT);
        }
    }
}
