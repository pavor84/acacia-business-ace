/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

/**
 *
 * @author Miro
 */
public class StringUtils {

    public static String getLastName(String packageName) {
        int index;
        if((index = packageName.lastIndexOf('.')) >= 0 || (index = packageName.lastIndexOf(' ')) >= 0) {
            packageName = packageName.substring(index + 1);
        }

        return packageName;
    }
}
