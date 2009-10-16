/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.converter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface DataColumn {

    boolean relativeColumnPosition() default true;

    int columnPosition() default 0;

    int length();

    String maskFormat() default "";

    String columnSeparator() default " ";

    Class dataType() default Object.class;
}
