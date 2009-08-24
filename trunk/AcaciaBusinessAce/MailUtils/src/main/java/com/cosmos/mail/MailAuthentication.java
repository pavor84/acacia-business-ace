/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.mail;

/**
 *
 * @author Miro
 */
public class MailAuthentication {

    private Boolean nameAndPasswordAuthentication;
    private String username;
    private String password;

    public MailAuthentication() {
    }

    public MailAuthentication(Boolean nameAndPasswordAuthentication, String username, String password) {
        this.nameAndPasswordAuthentication = nameAndPasswordAuthentication;
        this.username = username;
        this.password = password;
    }

    public MailAuthentication(String username, String password) {
        this(Boolean.TRUE, username, password);
    }

    public Boolean isNameAndPasswordAuthentication() {
        return nameAndPasswordAuthentication;
    }

    public void setNameAndPasswordAuthentication(Boolean nameAndPasswordAuthentication) {
        this.nameAndPasswordAuthentication = nameAndPasswordAuthentication;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
