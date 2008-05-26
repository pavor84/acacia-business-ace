/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.settings;


import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class GeneralSettings {

    public static Properties settings;
        
    static {
        try {
            settings = new Properties();        
            InputStream in = GeneralSettings.class.getResourceAsStream(
                    "settings.properties");
            settings.load(in);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        
    }
    
    public static String getSetting(String settingName)
    {
        return settings.getProperty(settingName);
    }
    
    public static DateFormat getDateFormat()
    {
        //return new SimpleDateFormat(getSetting("dateFormat"));
        return new SimpleDateFormat("dd.MM.yyyy");
    }
    
    public static boolean isDebug(){
        return settings.getProperty("debug").equals("true");
    }
}
