/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface ComponentProperty {

    public static final String JTabbedPane_TabPlacement_TOP = "1";
    public static final String JTabbedPane_TabPlacement_LEFT = "2";
    public static final String JTabbedPane_TabPlacement_BOTTOM = "3";
    public static final String JTabbedPane_TabPlacement_RIGHT = "4";

    String name();

    String value();
}
