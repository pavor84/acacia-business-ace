/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.users;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.apache.log4j.Logger;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class SecurityManager implements Serializable {

    protected static Logger log = Logger.getLogger(SecurityManager.class);
    
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
   
    public static PublicKey getPublicKey() {
        return getKeys().getPublic();
    }
    
    public static PrivateKey getPrivateKey() {
        return getKeys().getPrivate();
    }
}
