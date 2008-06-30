/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class SecurityManager implements Serializable {

    protected static Logger log = Logger.getLogger(SecurityManager.class);
    
    private static Certificate certificate;
    
    private static KeyPair keys;
    
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
    
    private static Certificate getCertificate() {
        if (certificate == null) {
            try {
                FileInputStream inStream = new FileInputStream(new File("."));
                certificate = CertificateFactory.getInstance("X.509").generateCertificate(inStream);
            } catch (Exception ex){
                log.error("", ex);
            }
        }
        return certificate;
    }
   
    public static PublicKey getPublicKey() {
        return getKeys().getPublic();
    }
    
    public static PrivateKey getPrivateKey() {
        return getKeys().getPrivate();
    }
}
