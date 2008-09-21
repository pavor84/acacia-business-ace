package com.cosmos.acacia.crm.gui;

import java.util.HashMap;
import java.util.Map;

public class LocalSession {

    //Keys
    public static final String BRANCH = "SESSION_BRANCH";
    public static final String VIEW_DATA_FROM_ALL_BRANCHES
        = "VIEW_DATA_FROM_ALL_BRANCHES";

    private static Map<String, Object> sessionCache = new HashMap<String, Object>();

    public static void put(String key, Object value) {
        sessionCache.put(key, value);
    }

    public static Object get(String key) {
        return sessionCache.get(key);
    }
}