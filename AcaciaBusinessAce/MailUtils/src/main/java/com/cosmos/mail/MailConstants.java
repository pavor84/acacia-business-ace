/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.mail;

/**
 *
 * @author Miro
 */
public interface MailConstants {

    public static final String PROTOCOL_SMTP = "smtp";
    public static final String PROTOCOL_SMTPS = "smtps";
    public static final String PROTOCOL_IMAP = "imap";
    public static final String PROTOCOL_IMAPS = "imaps";
    public static final String PROTOCOL_POP3 = "pop3";
    public static final String PROTOCOL_POP3S = "pop3s";

    //////////////////////////////////////////
    // Java Mail properties names below:
    //////////////////////////////////////////
    //javax.mail ==> Session properties
    public static final String SESSION_MAIL_DEBUG = "mail.debug"; //Default is false.
    public static final String SESSION_MAIL_FROM = "mail.from";
    public static final String SESSION_MAIL_MIME_ADDRESS_STRICT = "mail.mime.address.strict"; // The default is true.
    public static final String SESSION_MAIL_HOST = "mail.host";
    public static final String SESSION_MAIL_STORE_PROTOCOL = "mail.store.protocol";
    public static final String SESSION_MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String SESSION_MAIL_USER = "mail.user";
    //public static final String SESSION_MAIL_PROTOCOL_CLASS = "mail.protocol.class";
    //public static final String SESSION_MAIL_PROTOCOL_HOST = "mail.protocol.host";
    //public static final String SESSION_MAIL_PROTOCOL_PORT = "mail.protocol.port";
    //public static final String SESSION_MAIL_PROTOCOL_USER = "mail.protocol.user";

    //javax.mail.internet ==> System properties
    public static final String SYSTEM_MAIL_MIME_ADDRESS_STRICT = "mail.mime.address.strict";
    public static final String SYSTEM_MAIL_MIME_CHARSET = "mail.mime.charset";
    public static final String SYSTEM_MAIL_MIME_DECODETEXT_STRICT = "mail.mime.decodetext.strict"; // The default is true.
    public static final String SYSTEM_MAIL_MIME_ENCODEEOL_STRICT = "mail.mime.encodeeol.strict"; // The default is false.
    public static final String SYSTEM_MAIL_MIME_DECODEFILENAME = "mail.mime.decodefilename"; // The default is false
    public static final String SYSTEM_MAIL_MIME_ENCODEFILENAME = "mail.mime.encodefilename"; // The default is false.
    public static final String SYSTEM_MAIL_MIME_DECODEPARAMETERS = "mail.mime.decodeparameters"; // The default is false.
    public static final String SYSTEM_MAIL_MIME_ENCODEPARAMETERS = "mail.mime.encodeparameters"; // The default is false.
    public static final String SYSTEM_MAIL_MIME_MULTIPART_IGNOREMISSINGENDBOUNDARY = "mail.mime.multipart.ignoremissingendboundary"; // The default is true.
    public static final String SYSTEM_MAIL_MIME_MULTIPART_IGNOREMISSINGBOUNDARYPARAMETER = "mail.mime.multipart.ignoremissingboundaryparameter"; // The default is true

    //javax.mail.internet SUN specific ==> System properties
    public static final String SYSTEM_MAIL_MIME_BASE64_IGNOREERRORS = "mail.mime.base64.ignoreerrors"; // The default is false
    public static final String SYSTEM_MAIL_MIME_FOLDTEXT = "mail.mime.foldtext"; // The default is true
    public static final String SYSTEM_MAIL_MIME_SETCONTENTTYPEFILENAME = "mail.mime.setcontenttypefilename"; // The default is true
    public static final String SYSTEM_MAIL_MIME_SETDEFAULTTEXTCHARSET = "mail.mime.setdefaulttextcharset"; // The default is true
    public static final String SYSTEM_MAIL_MIME_PARAMETERS_STRICT = "mail.mime.parameters.strict"; // The default is true
    public static final String SYSTEM_MAIL_MIME_APPLEFILENAMES = "mail.mime.applefilenames"; // The default is false
    public static final String SYSTEM_MAIL_MIME_IGNOREUNKNOWNENCODING = "mail.mime.ignoreunknownencoding"; // The default is false
    public static final String SYSTEM_MAIL_MIME_UUDECODE_IGNOREERRORS = "mail.mime.uudecode.ignoreerrors"; // The default is false
    public static final String SYSTEM_MAIL_MIME_UUDECODE_IGNOREMISSINGBEGINEND = "mail.mime.uudecode.ignoremissingbeginend"; // The default is false
    public static final String SYSTEM_MAIL_MIME_MULTIPART_ALLOWEMPTY = "mail.mime.multipart.allowempty"; // The default is false
    public static final String SYSTEM_MAIL_MIME_MULTIPART_IGNOREEXISTINGBOUNDARYPARAMETER = "mail.mime.multipart.ignoreexistingboundaryparameter"; // The default is false
    public static final String SYSTEM_MAIL_ALTERNATES = "mail.alternates";
    public static final String SYSTEM_MAIL_REPLYALLCC = "mail.replyallcc"; // The default is false

    //com.sun.mail.imap ==> Session properties
    public static final String SESSION_MAIL_IMAP_USER = "mail.imap.user";
    public static final String SESSION_MAIL_IMAP_HOST = "mail.imap.host";
    public static final String SESSION_MAIL_IMAP_PORT = "mail.imap.port"; // Defaults to 143
    public static final String SESSION_MAIL_IMAP_PARTIALFETCH = "mail.imap.partialfetch"; // Defaults to true
    public static final String SESSION_MAIL_IMAP_FETCHSIZE = "mail.imap.fetchsize"; // Defaults to 16K
    public static final String SESSION_MAIL_IMAP_CONNECTIONTIMEOUT = "mail.imap.connectiontimeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_IMAP_TIMEOUT = "mail.imap.timeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_IMAP_STATUSCACHETIMEOUT = "mail.imap.statuscachetimeout"; // Default is 1000 (1 second)
    public static final String SESSION_MAIL_IMAP_APPENDBUFFERSIZE = "mail.imap.appendbuffersize";
    public static final String SESSION_MAIL_IMAP_CONNECTIONPOOLSIZE = "mail.imap.connectionpoolsize"; // Default is 1
    public static final String SESSION_MAIL_IMAP_CONNECTIONPOOLTIMEOUT = "mail.imap.connectionpooltimeout"; // Default is 45000 (45 seconds).
    public static final String SESSION_MAIL_IMAP_SEPARATESTORECONNECTION = "mail.imap.separatestoreconnection"; // Default is false
    public static final String SESSION_MAIL_IMAP_ALLOWREADONLYSELECT = "mail.imap.allowreadonlyselect"; // Default is false
    public static final String SESSION_MAIL_IMAP_AUTH_LOGIN_DISABLE = "mail.imap.auth.login.disable"; // Default is false
    public static final String SESSION_MAIL_IMAP_AUTH_PLAIN_DISABLE = "mail.imap.auth.plain.disable"; // Default is false
    public static final String SESSION_MAIL_IMAP_PROXYAUTH_USER = "mail.imap.proxyauth.user";
    public static final String SESSION_MAIL_IMAP_STARTTLS_ENABLE = "mail.imap.starttls.enable"; // Default is false
    public static final String SESSION_MAIL_IMAP_STARTTLS_REQUIRED = "mail.imap.starttls.required"; // Default is false
    public static final String SESSION_MAIL_IMAP_LOCALADDRESS = "mail.imap.localaddress";
    public static final String SESSION_MAIL_IMAP_LOCALPORT = "mail.imap.localport";
    public static final String SESSION_MAIL_IMAP_SASL_ENABLE = "mail.imap.sasl.enable"; // Default is false
    public static final String SESSION_MAIL_IMAP_SASL_MECHANISMS = "mail.imap.sasl.mechanisms";
    public static final String SESSION_MAIL_IMAP_SASL_AUTHORIZATIONID = "mail.imap.sasl.authorizationid";
    public static final String SESSION_MAIL_IMAP_SASL_REALM = "mail.imap.sasl.realm";
    public static final String SESSION_MAIL_IMAP_SASL_XGWTRUSTEDAPPHACK_ENABLE = "mail.imap.sasl.xgwtrustedapphack.enable"; // Defaults to true
    public static final String SESSION_MAIL_IMAP_SOCKETFACTORY = "mail.imap.socketFactory"; // object
    public static final String SESSION_MAIL_IMAP_SOCKETFACTORY_CLASS = "mail.imap.socketFactory.class";
    public static final String SESSION_MAIL_IMAP_SOCKETFACTORY_FALLBACK = "mail.imap.socketFactory.fallback"; // Defaults to true
    public static final String SESSION_MAIL_IMAP_SOCKETFACTORY_PORT = "mail.imap.socketFactory.port";
    public static final String SESSION_MAIL_IMAP_SSL_ENABLE = "mail.imap.ssl.enable"; // Defaults to false for the "imap" protocol and true for the "imaps" protocol.
    public static final String SESSION_MAIL_IMAP_SSL_CHECKSERVERIDENTITY = "mail.imap.ssl.checkserveridentity"; // Defaults to false
    public static final String SESSION_MAIL_IMAP_SSL_SOCKETFACTORY = "mail.imap.ssl.socketFactory"; // object
    public static final String SESSION_MAIL_IMAP_SSL_SOCKETFACTORY_CLASS = "mail.imap.ssl.socketFactory.class";
    public static final String SESSION_MAIL_IMAP_SSL_SOCKETFACTORY_PORT = "mail.imap.ssl.socketFactory.port";
    public static final String SESSION_MAIL_IMAP_SSL_PROTOCOLS = "mail.imap.ssl.protocols";
    public static final String SESSION_MAIL_IMAP_SSL_CIPHERSUITES = "mail.imap.ssl.ciphersuites";
    public static final String SESSION_MAIL_IMAP_MINIDLETIME = "mail.imap.minidletime"; // default is 10 milliseconds
    public static final String SESSION_MAIL_IMAP_ENABLEIMAPEVENTS = "mail.imap.enableimapevents"; // default is false
    //
    public static final String SESSION_MAIL_IMAPS_USER = "mail.imaps.user";
    public static final String SESSION_MAIL_IMAPS_HOST = "mail.imaps.host";
    public static final String SESSION_MAIL_IMAPS_PORT = "mail.imaps.port"; // Defaults to 143
    public static final String SESSION_MAIL_IMAPS_PARTIALFETCH = "mail.imaps.partialfetch"; // Defaults to true
    public static final String SESSION_MAIL_IMAPS_FETCHSIZE = "mail.imaps.fetchsize"; // Defaults to 16K
    public static final String SESSION_MAIL_IMAPS_CONNECTIONTIMEOUT = "mail.imaps.connectiontimeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_IMAPS_TIMEOUT = "mail.imaps.timeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_IMAPS_STATUSCACHETIMEOUT = "mail.imaps.statuscachetimeout"; // Default is 1000 (1 second)
    public static final String SESSION_MAIL_IMAPS_APPENDBUFFERSIZE = "mail.imaps.appendbuffersize";
    public static final String SESSION_MAIL_IMAPS_CONNECTIONPOOLSIZE = "mail.imaps.connectionpoolsize"; // Default is 1
    public static final String SESSION_MAIL_IMAPS_CONNECTIONPOOLTIMEOUT = "mail.imaps.connectionpooltimeout"; // Default is 45000 (45 seconds).
    public static final String SESSION_MAIL_IMAPS_SEPARATESTORECONNECTION = "mail.imaps.separatestoreconnection"; // Default is false
    public static final String SESSION_MAIL_IMAPS_ALLOWREADONLYSELECT = "mail.imaps.allowreadonlyselect"; // Default is false
    public static final String SESSION_MAIL_IMAPS_AUTH_LOGIN_DISABLE = "mail.imaps.auth.login.disable"; // Default is false
    public static final String SESSION_MAIL_IMAPS_AUTH_PLAIN_DISABLE = "mail.imaps.auth.plain.disable"; // Default is false
    public static final String SESSION_MAIL_IMAPS_PROXYAUTH_USER = "mail.imaps.proxyauth.user";
    public static final String SESSION_MAIL_IMAPS_STARTTLS_ENABLE = "mail.imaps.starttls.enable"; // Default is false
    public static final String SESSION_MAIL_IMAPS_STARTTLS_REQUIRED = "mail.imaps.starttls.required"; // Default is false
    public static final String SESSION_MAIL_IMAPS_LOCALADDRESS = "mail.imaps.localaddress";
    public static final String SESSION_MAIL_IMAPS_LOCALPORT = "mail.imaps.localport";
    public static final String SESSION_MAIL_IMAPS_SASL_ENABLE = "mail.imaps.sasl.enable"; // Default is false
    public static final String SESSION_MAIL_IMAPS_SASL_MECHANISMS = "mail.imaps.sasl.mechanisms";
    public static final String SESSION_MAIL_IMAPS_SASL_AUTHORIZATIONID = "mail.imaps.sasl.authorizationid";
    public static final String SESSION_MAIL_IMAPS_SASL_REALM = "mail.imaps.sasl.realm";
    public static final String SESSION_MAIL_IMAPS_SASL_XGWTRUSTEDAPPHACK_ENABLE = "mail.imaps.sasl.xgwtrustedapphack.enable"; // Defaults to true
    public static final String SESSION_MAIL_IMAPS_SOCKETFACTORY = "mail.imaps.socketFactory"; // object
    public static final String SESSION_MAIL_IMAPS_SOCKETFACTORY_CLASS = "mail.imaps.socketFactory.class";
    public static final String SESSION_MAIL_IMAPS_SOCKETFACTORY_FALLBACK = "mail.imaps.socketFactory.fallback"; // Defaults to true
    public static final String SESSION_MAIL_IMAPS_SOCKETFACTORY_PORT = "mail.imaps.socketFactory.port";
    //public static final String SESSION_MAIL_IMAPS_SSL_ENABLE = "mail.imaps.ssl.enable"; // Defaults to false for the "imap" protocol and true for the "imaps" protocol.
    //public static final String SESSION_MAIL_IMAPS_SSL_CHECKSERVERIDENTITY = "mail.imaps.ssl.checkserveridentity"; // Defaults to false
    //public static final String SESSION_MAIL_IMAPS_SSL_SOCKETFACTORY = "mail.imaps.ssl.socketFactory"; // object
    //public static final String SESSION_MAIL_IMAPS_SSL_SOCKETFACTORY_CLASS = "mail.imaps.ssl.socketFactory.class";
    //public static final String SESSION_MAIL_IMAPS_SSL_SOCKETFACTORY_PORT = "mail.imaps.ssl.socketFactory.port";
    //public static final String SESSION_MAIL_IMAPS_SSL_PROTOCOLS = "mail.imaps.ssl.protocols";
    //public static final String SESSION_MAIL_IMAPS_SSL_CIPHERSUITES = "mail.imaps.ssl.ciphersuites";
    public static final String SESSION_MAIL_IMAPS_MINIDLETIME = "mail.imaps.minidletime"; // default is 10 milliseconds
    public static final String SESSION_MAIL_IMAPS_ENABLEIMAPEVENTS = "mail.imaps.enableimapevents"; // default is false

    //com.sun.mail.pop3 ==> Session properties
    public static final String SESSION_MAIL_POP3_USER = "mail.pop3.user";
    public static final String SESSION_MAIL_POP3_HOST = "mail.pop3.host";
    public static final String SESSION_MAIL_POP3_PORT = "mail.pop3.port"; // Defaults to 110
    public static final String SESSION_MAIL_POP3_CONNECTIONTIMEOUT = "mail.pop3.connectiontimeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_POP3_TIMEOUT = "mail.pop3.timeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_POP3_RSETBEFOREQUIT = "mail.pop3.rsetbeforequit"; // Default is false
    public static final String SESSION_MAIL_POP3_MESSAGE_CLASS = "mail.pop3.message.class";
    public static final String SESSION_MAIL_POP3_LOCALADDRESS = "mail.pop3.localaddress";
    public static final String SESSION_MAIL_POP3_LOCALPORT = "mail.pop3.localport";
    public static final String SESSION_MAIL_POP3_APOP_ENABLE = "mail.pop3.apop.enable"; // Defaults to false
    public static final String SESSION_MAIL_POP3_SOCKETFACTORY = "mail.pop3.socketFactory"; // object
    public static final String SESSION_MAIL_POP3_SOCKETFACTORY_CLASS = "mail.pop3.socketFactory.class";
    public static final String SESSION_MAIL_POP3_SOCKETFACTORY_FALLBACK = "mail.pop3.socketFactory.fallback"; // Defaults to true
    public static final String SESSION_MAIL_POP3_SOCKETFACTORY_PORT = "mail.pop3.socketFactory.port";
    public static final String SESSION_MAIL_POP3_SSL_ENABLE = "mail.pop3.ssl.enable"; // Defaults to false for the "pop3" protocol and true for the "pop3s" protocol
    public static final String SESSION_MAIL_POP3_SSL_CHECKSERVERIDENTITY = "mail.pop3.ssl.checkserveridentity"; // Defaults to false
    public static final String SESSION_MAIL_POP3_SSL_SOCKETFACTORY = "mail.pop3.ssl.socketFactory"; // object
    public static final String SESSION_MAIL_POP3_SSL_SOCKETFACTORY_CLASS = "mail.pop3.ssl.socketFactory.class";
    public static final String SESSION_MAIL_POP3_SSL_SOCKETFACTORY_PORT = "mail.pop3.ssl.socketFactory.port";
    public static final String SESSION_MAIL_POP3_SSL_PROTOCOLS = "mail.pop3.ssl.protocols";
    public static final String SESSION_MAIL_POP3_SSL_CIPHERSUITES = "mail.pop3.ssl.ciphersuites";
    public static final String SESSION_MAIL_POP3_DISABLETOP = "mail.pop3.disabletop"; // Defaults to false
    public static final String SESSION_MAIL_POP3_FORGETTOPHEADERS = "mail.pop3.forgettopheaders"; // Defaults to false
    //
    public static final String SESSION_MAIL_POP3S_USER = "mail.pop3s.user";
    public static final String SESSION_MAIL_POP3S_HOST = "mail.pop3s.host";
    public static final String SESSION_MAIL_POP3S_PORT = "mail.pop3s.port"; // Defaults to 110
    public static final String SESSION_MAIL_POP3S_CONNECTIONTIMEOUT = "mail.pop3s.connectiontimeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_POP3S_TIMEOUT = "mail.pop3s.timeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_POP3S_RSETBEFOREQUIT = "mail.pop3s.rsetbeforequit"; // Default is false
    public static final String SESSION_MAIL_POP3S_MESSAGE_CLASS = "mail.pop3s.message.class";
    public static final String SESSION_MAIL_POP3S_LOCALADDRESS = "mail.pop3s.localaddress";
    public static final String SESSION_MAIL_POP3S_LOCALPORT = "mail.pop3s.localport";
    public static final String SESSION_MAIL_POP3S_APOP_ENABLE = "mail.pop3s.apop.enable"; // Defaults to false
    public static final String SESSION_MAIL_POP3S_SOCKETFACTORY = "mail.pop3s.socketFactory"; // object
    public static final String SESSION_MAIL_POP3S_SOCKETFACTORY_CLASS = "mail.pop3s.socketFactory.class";
    public static final String SESSION_MAIL_POP3S_SOCKETFACTORY_FALLBACK = "mail.pop3s.socketFactory.fallback"; // Defaults to true
    public static final String SESSION_MAIL_POP3S_SOCKETFACTORY_PORT = "mail.pop3s.socketFactory.port";
    //public static final String SESSION_MAIL_POP3S_SSL_ENABLE = "mail.pop3s.ssl.enable"; // Defaults to false for the "pop3" protocol and true for the "pop3s" protocol
    //public static final String SESSION_MAIL_POP3S_SSL_CHECKSERVERIDENTITY = "mail.pop3s.ssl.checkserveridentity"; // Defaults to false
    //public static final String SESSION_MAIL_POP3S_SSL_SOCKETFACTORY = "mail.pop3s.ssl.socketFactory"; // object
    //public static final String SESSION_MAIL_POP3S_SSL_SOCKETFACTORY_CLASS = "mail.pop3s.ssl.socketFactory.class";
    //public static final String SESSION_MAIL_POP3S_SSL_SOCKETFACTORY_PORT = "mail.pop3s.ssl.socketFactory.port";
    //public static final String SESSION_MAIL_POP3S_SSL_PROTOCOLS = "mail.pop3s.ssl.protocols";
    //public static final String SESSION_MAIL_POP3S_SSL_CIPHERSUITES = "mail.pop3s.ssl.ciphersuites";
    public static final String SESSION_MAIL_POP3S_DISABLETOP = "mail.pop3s.disabletop"; // Defaults to false
    public static final String SESSION_MAIL_POP3S_FORGETTOPHEADERS = "mail.pop3s.forgettopheaders"; // Defaults to false

    //com.sun.mail.smtp ==> Session properties
    public static final String SESSION_MAIL_SMTP_USER = "mail.smtp.user";
    public static final String SESSION_MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String SESSION_MAIL_SMTP_PORT = "mail.smtp.port"; // Defaults to 25
    public static final String SESSION_MAIL_SMTP_CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_SMTP_TIMEOUT = "mail.smtp.timeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_SMTP_FROM = "mail.smtp.from";
    public static final String SESSION_MAIL_SMTP_LOCALHOST = "mail.smtp.localhost";
    public static final String SESSION_MAIL_SMTP_LOCALADDRESS = "mail.smtp.localaddress";
    public static final String SESSION_MAIL_SMTP_LOCALPORT = "mail.smtp.localport";
    public static final String SESSION_MAIL_SMTP_EHLO = "mail.smtp.ehlo"; // Defaults to true
    public static final String SESSION_MAIL_SMTP_AUTH = "mail.smtp.auth"; // Defaults to false
    public static final String SESSION_MAIL_SMTP_AUTH_MECHANISMS = "mail.smtp.auth.mechanisms";
    public static final String SESSION_MAIL_SMTP_SUBMITTER = "mail.smtp.submitter";
    public static final String SESSION_MAIL_SMTP_DSN_NOTIFY = "mail.smtp.dsn.notify";
    public static final String SESSION_MAIL_SMTP_DSN_RET = "mail.smtp.dsn.ret";
    public static final String SESSION_MAIL_SMTP_ALLOW8BITMIME = "mail.smtp.allow8bitmime"; // default ???
    public static final String SESSION_MAIL_SMTP_SENDPARTIAL = "mail.smtp.sendpartial"; // Defaults to false
    public static final String SESSION_MAIL_SMTP_SASL_REALM = "mail.smtp.sasl.realm";
    public static final String SESSION_MAIL_SMTP_QUITWAIT = "mail.smtp.quitwait"; // Defaults to true
    public static final String SESSION_MAIL_SMTP_REPORTSUCCESS = "mail.smtp.reportsuccess"; // Defaults to false
    public static final String SESSION_MAIL_SMTP_SOCKETFACTORY = "mail.smtp.socketFactory"; // object
    public static final String SESSION_MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    public static final String SESSION_MAIL_SMTP_SOCKETFACTORY_FALLBACK = "mail.smtp.socketFactory.fallback"; // Defaults to true
    public static final String SESSION_MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    public static final String SESSION_MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable"; // Defaults to false for the "smtp" protocol and true for the "smtps" protocol
    public static final String SESSION_MAIL_SMTP_SSL_CHECKSERVERIDENTITY = "mail.smtp.ssl.checkserveridentity"; // Defaults to false
    public static final String SESSION_MAIL_SMTP_SSL_SOCKETFACTORY = "mail.smtp.ssl.socketFactory"; // object
    public static final String SESSION_MAIL_SMTP_SSL_SOCKETFACTORY_CLASS = "mail.smtp.ssl.socketFactory.class";
    public static final String SESSION_MAIL_SMTP_SSL_SOCKETFACTORY_PORT = "mail.smtp.ssl.socketFactory.port";
    public static final String SESSION_MAIL_SMTP_SSL_PROTOCOLS = "mail.smtp.ssl.protocols";
    public static final String SESSION_MAIL_SMTP_SSL_CIPHERSUITES = "mail.smtp.ssl.ciphersuites";
    public static final String SESSION_MAIL_SMTP_MAILEXTENSION = "mail.smtp.mailextension";
    public static final String SESSION_MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable"; // Defaults to false
    public static final String SESSION_MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required"; // Defaults to false
    public static final String SESSION_MAIL_SMTP_USERSET = "mail.smtp.userset"; // Defaults to false
    //
    public static final String SESSION_MAIL_SMTPS_USER = "mail.smtps.user";
    public static final String SESSION_MAIL_SMTPS_HOST = "mail.smtps.host";
    public static final String SESSION_MAIL_SMTPS_PORT = "mail.smtps.port"; // Defaults to 25
    public static final String SESSION_MAIL_SMTPS_CONNECTIONTIMEOUT = "mail.smtps.connectiontimeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_SMTPS_TIMEOUT = "mail.smtps.timeout"; // Default is infinite timeout
    public static final String SESSION_MAIL_SMTPS_FROM = "mail.smtps.from";
    public static final String SESSION_MAIL_SMTPS_LOCALHOST = "mail.smtps.localhost";
    public static final String SESSION_MAIL_SMTPS_LOCALADDRESS = "mail.smtps.localaddress";
    public static final String SESSION_MAIL_SMTPS_LOCALPORT = "mail.smtps.localport";
    public static final String SESSION_MAIL_SMTPS_EHLO = "mail.smtps.ehlo"; // Defaults to true
    public static final String SESSION_MAIL_SMTPS_AUTH = "mail.smtps.auth"; // Defaults to false
    public static final String SESSION_MAIL_SMTPS_AUTH_MECHANISMS = "mail.smtps.auth.mechanisms";
    public static final String SESSION_MAIL_SMTPS_SUBMITTER = "mail.smtps.submitter";
    public static final String SESSION_MAIL_SMTPS_DSN_NOTIFY = "mail.smtps.dsn.notify";
    public static final String SESSION_MAIL_SMTPS_DSN_RET = "mail.smtps.dsn.ret";
    public static final String SESSION_MAIL_SMTPS_ALLOW8BITMIME = "mail.smtps.allow8bitmime"; // default ???
    public static final String SESSION_MAIL_SMTPS_SENDPARTIAL = "mail.smtps.sendpartial"; // Defaults to false
    public static final String SESSION_MAIL_SMTPS_SASL_REALM = "mail.smtps.sasl.realm";
    public static final String SESSION_MAIL_SMTPS_QUITWAIT = "mail.smtps.quitwait"; // Defaults to true
    public static final String SESSION_MAIL_SMTPS_REPORTSUCCESS = "mail.smtps.reportsuccess"; // Defaults to false
    public static final String SESSION_MAIL_SMTPS_SOCKETFACTORY = "mail.smtps.socketFactory"; // object
    public static final String SESSION_MAIL_SMTPS_SOCKETFACTORY_CLASS = "mail.smtps.socketFactory.class";
    public static final String SESSION_MAIL_SMTPS_SOCKETFACTORY_FALLBACK = "mail.smtps.socketFactory.fallback"; // Defaults to true
    public static final String SESSION_MAIL_SMTPS_SOCKETFACTORY_PORT = "mail.smtps.socketFactory.port";
    //public static final String SESSION_MAIL_SMTPS_SSL_ENABLE = "mail.smtps.ssl.enable"; // Defaults to false for the "smtp" protocol and true for the "smtps" protocol
    //public static final String SESSION_MAIL_SMTPS_SSL_CHECKSERVERIDENTITY = "mail.smtps.ssl.checkserveridentity"; // Defaults to false
    //public static final String SESSION_MAIL_SMTPS_SSL_SOCKETFACTORY = "mail.smtps.ssl.socketFactory"; // object
    //public static final String SESSION_MAIL_SMTPS_SSL_SOCKETFACTORY_CLASS = "mail.smtps.ssl.socketFactory.class";
    //public static final String SESSION_MAIL_SMTPS_SSL_SOCKETFACTORY_PORT = "mail.smtps.ssl.socketFactory.port";
    //public static final String SESSION_MAIL_SMTPS_SSL_PROTOCOLS = "mail.smtps.ssl.protocols";
    //public static final String SESSION_MAIL_SMTPS_SSL_CIPHERSUITES = "mail.smtps.ssl.ciphersuites";
    public static final String SESSION_MAIL_SMTPS_MAILEXTENSION = "mail.smtps.mailextension";
    public static final String SESSION_MAIL_SMTPS_STARTTLS_ENABLE = "mail.smtps.starttls.enable"; // Defaults to false
    public static final String SESSION_MAIL_SMTPS_STARTTLS_REQUIRED = "mail.smtps.starttls.required"; // Defaults to false
    public static final String SESSION_MAIL_SMTPS_USERSET = "mail.smtps.userset"; // Defaults to false

    ///////////////////////////////////////
    // Email BC constants below:
    ///////////////////////////////////////

    public static String PREAMBLE = "This is the preamble area of a multipart message. " +
    "Mail readers that understand multipart format should ignore this preamble.\r\n" +
    "If you are reading this text, you might want to consider changing to " +
    "a mail reader that understands how to properly display multipart messages.";

    public static final String ACK_MODE_AUTOMATIC = "automatic";
    public static final String ACK_MODE_MANUAL = "manual";

    public static final String ACK_OP_MARK_AS_READ = "markAsRead";
    public static final String ACK_OP_DELETE = "delete";

    public static final String SMTP_USE_TYPE_LITERAL = "literal";
    public static final String SMTP_USE_TYPE_ENCODED = "encoded";

    public static final String SMTP_SEND_OPTION_TEXT_ONLY = "Text Only";
    public static final String SMTP_SEND_OPTION_HTML_ONLY = "HTML Only";
    public static final String SMTP_SEND_OPTION_XML_ONLY = "XML Only";
    public static final String SMTP_SEND_OPTION_BOTH_TEXT_AND_HTML = "Both Text and HTML";
    public static final String SMTP_SEND_OPTION_BOTH_TEXT_AND_XML = "Both Text and XML";

    // SMTP Properties
    public static final String MAIL_SMTP_SERVER_NAME = "mail.smtp.servername";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_USERNAME = "mail.smtp.username";
    public static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";
    public static final String MAIL_SMTP_USESSL = "mail.smtp.usessl";
    public static final String MAIL_SMTP_LOCATION = "mail.smtp.location";
    public static final String MAIL_SMTP_CHARSET = "mail.smtp.charset";
    public static final String MAIL_SMTP_USE = "mail.smtp.use";
    public static final String MAIL_SMTP_ENCODING_STYLE = "mail.smtp.encodingstyle";
    public static final String MAIL_SMTP_SEND_OPTION = "mail.smtp.sendoption";
    public static final String MAIL_SMTP_EMBED_IMAGES_IN_HTML = "mail.smtp.embedimagesinhtml";
    public static final String MAIL_SMTP_HANDLE_NM_ATTACHMENTS = "mail.smtp.handlenmattachments";
    //
    // part names:
    // generally they are not necessary because they are already accessible via message,
    // but they could provide an alternative access which may be useful for some cases which cannot (don't) handle multiple parts message, e.g. fuji
    // binding input ==> to overwrite the part values instead of part names
    public static final String MAIL_SMTP_SUBJECT = "mail.smtp.subject";
    public static final String MAIL_SMTP_FROM = "mail.smtp.from";
    public static final String MAIL_SMTP_TO = "mail.smtp.to";
    public static final String MAIL_SMTP_CC = "mail.smtp.cc";
    public static final String MAIL_SMTP_BCC = "mail.smtp.bcc";
    public static final String MAIL_SMTP_NEWSGROUP = "mail.smtp.newgroup";
    public static final String MAIL_SMTP_BODY = "mail.smtp.body";
    //

    // POP3 NM Properties
    // generally they are not necessary because the values are in wsdl already,
    // but they could be useful for the cases where ApplicationVariable and/or ApplicationConfiguration applies
    public static final String MAIL_POP3_SERVER_NAME = "mail.pop3.servername";
    public static final String MAIL_POP3_PORT = "mail.pop3.port";
    public static final String MAIL_POP3_USERNAME = "mail.pop3.username";
    public static final String MAIL_POP3_PASSWORD = "mail.pop3.password";
    public static final String MAIL_POP3_USESSL = "mail.pop3.usessl";
    public static final String MAIL_POP3_MAIL_FOLDER = "mail.pop3.mailfolder";
    public static final String MAIL_POP3_MAX_MESSAGE_COUNT = "mail.pop3.maxmessagecount";
    public static final String MAIL_POP3_MESSAGE_ACK_MODE = "mail.pop3.ackmode";
    public static final String MAIL_POP3_POLLING_INTERVAL = "mail.pop3.pollinginterval";
    public static final String MAIL_POP3_HANDLE_NM_ATTACHMENTS = "mail.pop3.handlenmattachments";
    public static final String MAIL_POP3_SAVE_ATTACHMENTS_TO_DIR = "mail.pop3.saveattachmentstodir";
    //
    // part names:
    // generally they are not necessary because they are already accessible via message,
    // but they could provide an alternative access which may be useful for some cases which cannot (don't) handle multiple parts message, e.g. fuji
    // binding input ==> to overwrite the part values instead of part names
    public static final String MAIL_POP3_SUBJECT = "mail.pop3.subject";
    public static final String MAIL_POP3_FROM = "mail.pop3.from";
    public static final String MAIL_POP3_TO = "mail.pop3.to";
    public static final String MAIL_POP3_CC = "mail.pop3.cc";
    public static final String MAIL_POP3_BCC = "mail.pop3.bcc";
    public static final String MAIL_POP3_NEWSGROUP = "mail.pop3.newgroup";
    public static final String MAIL_POP3_BODY = "mail.pop3.body";
    //

    // IMAP NM Properties
    // generally they are not necessary because the values are in wsdl already,
    // but they could be useful for the cases where ApplicationVariable and/or ApplicationConfiguration applies
    public static final String MAIL_IMAP_SERVER_NAME = "mail.imap.servername";
    public static final String MAIL_IMAP_PORT = "mail.imap.port";
    public static final String MAIL_IMAP_USERNAME = "mail.imap.username";
    public static final String MAIL_IMAP_PASSWORD = "mail.imap.password";
    public static final String MAIL_IMAP_USESSL = "mail.imap.usessl";
    public static final String MAIL_IMAP_MAIL_FOLDER = "mail.imap.mailfolder";
    public static final String MAIL_IMAP_MAX_MESSAGE_COUNT = "mail.imap.maxmessagecount";
    public static final String MAIL_IMAP_MESSAGE_ACK_MODE = "mail.imap.ackmode";
    public static final String MAIL_IMAP_POLLING_INTERVAL = "mail.imap.pollinginterval";
    public static final String MAIL_IMAP_HANDLE_NM_ATTACHMENTS = "mail.imap.handlenmattachments";
    public static final String MAIL_IMAP_SAVE_ATTACHMENTS_TO_DIR = "mail.imap.saveattachmentstodir";
    //
    // part names:
    // generally they are not necessary because they are already accessible via message,
    // but they could provide an alternative access which may be useful for some cases which cannot (don't) handle multiple parts message, e.g. fuji
    // binding input ==> to overwrite the part values instead of part names
    public static final String MAIL_IMAP_SUBJECT = "mail.imap.subject";
    public static final String MAIL_IMAP_FROM = "mail.imap.from";
    public static final String MAIL_IMAP_TO = "mail.imap.to";
    public static final String MAIL_IMAP_CC = "mail.imap.cc";
    public static final String MAIL_IMAP_BCC = "mail.imap.bcc";
    public static final String MAIL_IMAP_NEWSGROUP = "mail.imap.newgroup";
    public static final String MAIL_IMAP_BODY = "mail.imap.body";

    //
    public static final String MESSAGE_HEADER_UUID = "X-UUID";
    //
    public static final String MESSAGE_HEADER_X_CONFIRM_READING_TO =
            "X-Confirm-Reading-To";
    public static final String MESSAGE_HEADER_DISPOSITION_NOTIFICATION_TO =
            "Disposition-Notification-To";
    public static final String MESSAGE_HEADER_RETURN_RECEIPT_TO =
            "Return-Receipt-To";
    public static final String MESSAGE_HEADER_X_MAILER =
            "X-Mailer";
}
