/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.jdesktop.beansbinding.ELProperty;

/**
 *
 * @author Miro
 */
public class BooleanUtils {

    public static final String[] TRUE_ARRAY = {"true", "yes"};
    public static final String[] FALSE_ARRAY = {"false", "no"};

    public static Set<String> TRUE_SET;
    public static Set<String> FALSE_SET;
    public static Set<String> BOOLEAN_SET;

    public static Set<String> getTrueSet() {
        if(TRUE_SET == null) {
            TRUE_SET = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            TRUE_SET.addAll(Arrays.asList(TRUE_ARRAY));
        }

        return TRUE_SET;
    }

    public static Set<String> getFalseSet() {
        if(FALSE_SET == null) {
            FALSE_SET = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            FALSE_SET.addAll(Arrays.asList(FALSE_ARRAY));
        }

        return FALSE_SET;
    }

    public static Set<String> getBooleanSet() {
        if(FALSE_SET == null) {
            BOOLEAN_SET = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            BOOLEAN_SET.addAll(getTrueSet());
            BOOLEAN_SET.addAll(getFalseSet());
        }

        return FALSE_SET;
    }

    public static boolean parseBoolean(String value) {
        if(value != null && getTrueSet().contains(value)) {
            return true;
        }

        return false;
    }

    public static boolean evaluateExpression(Object thisObject, String expression) {
        if(getBooleanSet().contains(expression)) {
            return parseBoolean(expression);
        }

        Object result;
        if((result = ELProperty.create(expression).getValue(thisObject)) == null) {
            return false;
        }

        if(result instanceof Boolean) {
            return (Boolean)result;
        }

        return parseBoolean(String.valueOf(result));
    }

}
