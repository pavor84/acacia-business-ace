package com.cosmos.acacia.crm.bl.users;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageRetriever {

    
    public static String get(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("EmailMessages");
        return bundle.getString(key);
    }
    
    public static String get(String key, Locale locale) {
        if (locale == null)
            return get(key);
        
        ResourceBundle bundle = ResourceBundle.getBundle("EmailMessages", locale);
        return bundle.getString(key);
    }
}
