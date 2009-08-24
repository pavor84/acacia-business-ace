/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.mail;

/**
 *
 * @author Miro
 */
public enum MailProtocol {

    SMTP("smtp", MailOperation.Send),
    SMTPS("smtps", MailOperation.Send),
    IMAP("imap", MailOperation.Receive),
    IMAPS("imaps", MailOperation.Receive),
    POP3("pop3", MailOperation.Receive),
    POP3S("pop3s", MailOperation.Receive);

    private MailProtocol(String protocolName, MailOperation operation) {
        this.protocolName = protocolName;
        this.propertyName = "mail." + protocolName + ".host";
        this.operation = operation;
    }
    //
    private String protocolName;
    private String propertyName;
    private MailOperation operation;

    public String getProtocolName() {
        return protocolName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public MailOperation getOperation() {
        return operation;
    }
}
