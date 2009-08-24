/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.mail;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Miro
 */
public class MessageParameters implements Serializable {

    private Collection<Address> to;
    private String messageText;
    private String subject;
    //
    private InternetAddress replyTo;
    /**
     * Files can File, String or URI instance.
     */
    private Collection files;
    private Collection<Address> cc;
    private Collection<Address> bcc;
    private UUID uuid;
    //
    private Boolean confirmReadingTo;
    private Boolean dispositionNotificationTo;
    private Boolean returnReceiptTo;

    public MessageParameters(Collection<Address> to, String messageText, String subject) {
        this(to, messageText, subject, null);
    }

    public MessageParameters(Collection<Address> to, String messageText, String subject, InternetAddress replyTo) {
        this(to, messageText, subject, replyTo, null, null, null, null);
    }

    public MessageParameters(
            Collection<Address> to,
            String messageText,
            String subject,
            InternetAddress replyTo,
            Collection files,
            Collection<Address> cc,
            Collection<Address> bcc,
            UUID uuid) {
        this.to = to;
        this.messageText = messageText;
        this.subject = subject;
        this.replyTo = replyTo;
        this.files = files;
        this.cc = cc;
        this.bcc = bcc;
        this.uuid = uuid;
    }

    public InternetAddress getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(InternetAddress replyTo) {
        this.replyTo = replyTo;
    }

    public Collection<Address> getBcc() {
        return bcc;
    }

    public void setBcc(Collection<Address> bcc) {
        this.bcc = bcc;
    }

    public Collection<Address> getCc() {
        return cc;
    }

    public void setCc(Collection<Address> cc) {
        this.cc = cc;
    }

    public Collection getFiles() {
        return files;
    }

    public void setFiles(Collection files) {
        this.files = files;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Collection<Address> getTo() {
        return to;
    }

    public void setTo(Collection<Address> to) {
        this.to = to;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean isConfirmReadingTo() {
        return confirmReadingTo;
    }

    public void setConfirmReadingTo(Boolean confirmReadingTo) {
        this.confirmReadingTo = confirmReadingTo;
    }

    public Boolean isDispositionNotificationTo() {
        return dispositionNotificationTo;
    }

    public void setDispositionNotificationTo(Boolean dispositionNotificationTo) {
        this.dispositionNotificationTo = dispositionNotificationTo;
    }

    public Boolean isReturnReceiptTo() {
        return returnReceiptTo;
    }

    public void setReturnReceiptTo(Boolean returnReceiptTo) {
        this.returnReceiptTo = returnReceiptTo;
    }
}
