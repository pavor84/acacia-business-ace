/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

import java.awt.Event;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Validation {
    int type() default ValidationType.NONE;
    int event() default Event.KEY_ACTION;
    int rangeStart() default 0;
    int rangeEnd() default 1000;
    String customMethod() default "";
}