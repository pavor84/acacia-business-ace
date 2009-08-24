/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.mail;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Miro
 */
public class MailUtilsTest {

    private static MailUtils mailUtils;

    public MailUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/mail_config.xml");
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(inStream));
        MailServer outgoingServer = (MailServer) decoder.readObject();
        decoder.close();

        inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/from_address.xml");
        decoder = new XMLDecoder(new BufferedInputStream(inStream));
        InternetAddress fromAddress = (InternetAddress) decoder.readObject();
        decoder.close();
        
        mailUtils = new MailUtils(outgoingServer, fromAddress);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of sendMessage method, of class MailUtils.
     */
    @Test
    public void testSendMessage() throws Exception {
        InternetAddress address = new InternetAddress("miro@space-comm.com", "Miroslav Nachev");
        MessageParameters parameters = new MessageParameters(
                Arrays.<Address>asList(address), "Some message text.", "Email test");
        mailUtils.sendMessage(parameters);
    }
}