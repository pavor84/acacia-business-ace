/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import java.lang.reflect.Field;

/**
 *
 * @author miro
 */
public class ClassHelper {

    public static Class getClass(String className) {
        if ("boolean".equals(className)) {
            return boolean.class;
        } else if ("char".equals(className)) {
            return char.class;
        } else if ("byte".equals(className)) {
            return byte.class;
        } else if ("short".equals(className)) {
            return short.class;
        } else if ("int".equals(className)) {
            return int.class;
        } else if ("long".equals(className)) {
            return long.class;
        } else if ("float".equals(className)) {
            return float.class;
        } else if ("double".equals(className)) {
            return double.class;
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getClassName(Class cls) {
        if (boolean.class.equals(cls)) {
            return Boolean.class.getName();
        } else if (char.class.equals(cls)) {
            return Character.class.getName();
        } else if (byte.class.equals(cls)) {
            return Byte.class.getName();
        } else if (short.class.equals(cls)) {
            return Short.class.getName();
        } else if (int.class.equals(cls)) {
            return Integer.class.getName();
        } else if (long.class.equals(cls)) {
            return Long.class.getName();
        } else if (float.class.equals(cls)) {
            return Float.class.getName();
        } else if (double.class.equals(cls)) {
            return Double.class.getName();
        }

        return cls.getName();
    }

    public static String objectToString(Object object) {
        String result = object.getClass().getName() + " values {\n";
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                result += field.getName() + "=" + field.get(object) + "\n";
            } catch (Exception ex) {
            }
        }
        result += "\n}";

        return result;
    }
}
