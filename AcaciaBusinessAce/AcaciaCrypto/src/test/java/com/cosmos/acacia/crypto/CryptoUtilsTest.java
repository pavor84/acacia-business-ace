/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miro
 */
public class CryptoUtilsTest {

    private static CryptoUtils cryptoUtils;

    public CryptoUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        cryptoUtils = CryptoUtils.getInstance();
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

    @Test
    public void testGetCertificate() {
        assertNotNull(cryptoUtils.getCertificate());
    }

    @Test
    public void testGetPublicKey() {
        assertNotNull(cryptoUtils.getPublicKey());
    }

    @Test
    public void testEncryptDecrypt() {
        byte[] source = "Hello World.".getBytes();
        byte[] target = cryptoUtils.decrypt(cryptoUtils.encrypt(source));
        assertTrue(Arrays.equals(source, target));
    }

    @Test
    public void testBase64EncryptDecrypt() {
        String sourceString = "Hello World.";
        String encryptedBase64 = cryptoUtils.encryptBase64(sourceString);
        byte[] target = cryptoUtils.decryptBase64(encryptedBase64);
        String targetString = new String(target);
        assertEquals(sourceString, targetString);
    }

    @Test
    public void testStoreLoadProperties() {
        Properties sourceProperties = new Properties();
        sourceProperties.setProperty("key1", "value1");
        sourceProperties.setProperty("key2", "value2");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        cryptoUtils.storeProperties(sourceProperties, outStream, "Some comment");
        ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
        Properties targetProperties = cryptoUtils.loadProperties(inStream);
        assertTrue(sourceProperties.equals(targetProperties));
    }
}