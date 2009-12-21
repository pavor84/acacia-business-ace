/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.mail;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.enums.MailType;
import com.cosmos.mail.MailUtils;
import com.cosmos.mail.MessageParameters;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Miro
 */
@MessageDriven(mappedName = "jms/emailQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailBean implements MessageListener {

    @EJB
    private AcaciaSessionLocal session;

    public MailBean() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage) message;
            MailUtils mailUtils;
            MailType mailType = MailType.valueOf(mapMessage.getString("MailType"));
            switch(mailType) {
                case System:
                    mailUtils = session.getSystemMailUtils();
                    break;
                case Organization:
                    mailUtils = session.getOrganizationMailUtils();
                    break;
                case User:
                    mailUtils = session.getUserMailUtils();
                    break;
                default:
                    mailUtils = null;
            }
            if(mailUtils == null) {
                return;
            }

            MessageParameters messageParameters = readMessageParameters(mapMessage.getBytes("MessageParameters"));
            mailUtils.sendMessage(messageParameters);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private MessageParameters readMessageParameters(byte[] byteArray) {
        try {
            ByteArrayInputStream inStream = new ByteArrayInputStream(byteArray);
            ObjectInputStream ois = new ObjectInputStream(inStream);
            return (MessageParameters) ois.readObject();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
