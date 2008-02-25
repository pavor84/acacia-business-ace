/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation;

/**
 *
 * @author Bozhidar Bozhanov
 */
public interface ValidationType {
    public static final int NONE = 0;
    public static final int REQUIRED = 1;
    public static final int RANGE = 2;
    public static final int CUSTOM = 3;
}
