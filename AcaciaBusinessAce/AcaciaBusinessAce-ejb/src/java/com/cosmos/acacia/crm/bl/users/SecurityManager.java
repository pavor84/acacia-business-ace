/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import org.apache.log4j.Logger;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class SecurityManager implements Serializable {

    protected static Logger log = Logger.getLogger(SecurityManager.class);
    
    private static String ALIAS = "acacia";
    private static String PASSWORD = "acacia";
    
    private static Certificate certificate;
    
    private static KeyStore keyStore;
    private static KeyPair keys;
    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    
    @Deprecated
    private static KeyPair getKeys() {
        if (keys == null) {
            try {
                keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            } catch (NoSuchAlgorithmException ex) {
                log.error(ex);
            }
        }
        return keys;
    }
    
    @Deprecated
    private static Certificate getCertificate() {
        if (certificate == null) {
            try {
                FileInputStream inStream = new FileInputStream(new File("acacia.p12"));
                certificate = CertificateFactory.getInstance("X.509").generateCertificate(inStream);
                inStream.close();
            } catch (Exception ex){
                log.error("", ex);
            }
        }
        return certificate;
    }
    
    private static KeyStore getKeyStore() throws Exception{
        if (keyStore == null) {
            InputStream inStream = SecurityManager.class.getResourceAsStream("acacia.p12");
            keyStore = KeyStore.getInstance("pkcs12");
            keyStore.load(inStream, SecurityManager.PASSWORD.toCharArray());
            inStream.close();
        }
        
        return keyStore;
    }
   
    public static PublicKey getPublicKey() {
        if (publicKey == null) {
            try {
                KeyStore keyStore = getKeyStore();
                publicKey = keyStore.getCertificate(ALIAS).getPublicKey();
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        return publicKey;
    }
    
    public static PrivateKey getPrivateKey() {
       if (privateKey == null) {
            try {
                KeyStore keyStore = getKeyStore();
                privateKey = (PrivateKey) keyStore.getKey(SecurityManager.ALIAS, SecurityManager.PASSWORD.toCharArray());
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
        return privateKey;
    }
}
