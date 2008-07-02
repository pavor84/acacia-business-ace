package com.cosmos.acacia.crm.bl.users;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageRetriever {
    //TODO optimize - lazy bundle loading, only once;
    
    public static String get(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("EmailMessages");
        return bundle.getString(key);
    }
    
    public static String get(String key, Locale locale) {
        try {
            if (locale == null)
                return get(key);

            ResourceBundle bundle = ResourceBundle.getBundle("EmailMessages", locale);
            return bundle.getString(key);
        } catch (Exception ex){
            return "";
        }
    }
    
    public static String get(String key, Locale locale, String[] values) {
        String message = get(key, locale);
        for (int i = 0; i < values.length; i ++) {
            message = message.replaceAll("{" + i + "}", values[i]);
        }
        return message;
    }
    
    public static String get(String key, Locale locale, String value) {
        return get(key, locale, new String[] {value});
    }
    
    public static String get(String key, String value) {
        return get(key, null, new String[] {value});
    }
    
    public static String get(String key, String[] values) {
        return get(key, null, values);
    }
}
