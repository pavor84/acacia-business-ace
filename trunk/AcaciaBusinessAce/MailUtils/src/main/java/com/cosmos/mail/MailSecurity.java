/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.mail;

/**
 *
 * @author Miro
 */
public class MailSecurity {

    private SecureConnection secureConnection;
    private Boolean secureAuthentication;

    public MailSecurity() {
    }

    public MailSecurity(SecureConnection secureConnection) {
        this(secureConnection, false);
    }

    public MailSecurity(SecureConnection secureConnection, boolean secureAuthentication) {
        this.secureConnection = secureConnection;
        this.secureAuthentication = secureAuthentication;
    }

    public Boolean isSecureAuthentication() {
        return secureAuthentication;
    }

    public void setSecureAuthentication(Boolean secureAuthentication) {
        this.secureAuthentication = secureAuthentication;
    }

    public SecureConnection getSecureConnection() {
        return secureConnection;
    }

    public void setSecureConnection(SecureConnection secureConnection) {
        this.secureConnection = secureConnection;
    }
}
