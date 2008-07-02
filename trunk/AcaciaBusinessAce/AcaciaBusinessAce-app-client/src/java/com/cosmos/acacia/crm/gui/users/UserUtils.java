package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.app.*;
import java.util.Locale;

import com.cosmos.acacia.crm.bl.users.UsersRemote;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class UserUtils {

    protected static Logger log = Logger.getLogger(UserUtils.class);
    protected static Locale locale;
    
    public static void updateUserLocale(UsersRemote bean) {
        bean.setLocale((Locale) locale);
    }
    
    public static void setLocale(Locale locale) {
        UserUtils.locale = locale;
    }
    
    public static String getHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            return new String(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("Hashing algorithm not found");
            return password;
        }
    }
    
    public static String getHexString(byte[] array) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
		hexString.append(Integer.toHexString(0xFF & array[i]));
	}
        return hexString.toString();
    }
}
