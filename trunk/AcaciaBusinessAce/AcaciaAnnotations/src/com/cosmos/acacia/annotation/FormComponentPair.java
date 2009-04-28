/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.swing.JLabel;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface FormComponentPair {

    Component firstComponent();

    Component secondComponent();

    int componentIndex() default -1;

    String parentContainerName() default "";
}
