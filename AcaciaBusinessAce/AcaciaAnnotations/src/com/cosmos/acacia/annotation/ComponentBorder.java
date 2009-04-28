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
public @interface ComponentBorder {

    /**
     * javax.swing.BorderFactory method name
     */
    BorderType borderType() default BorderType.NONE;

    int type() default -1;

    String highlightColor() default "";

    String shadowColor() default "";

    String highlightOuterColor() default "";

    String highlightInnerColor() default "";

    String shadowOuterColor() default "";

    String shadowInnerColor() default "";

    String color() default "";

    int thickness() default -1;

    int top() default -1;

    int left() default -1;

    int bottom() default -1;

    int right() default -1;

    String tileIconURI() default "";

    String title() default "";

    int titleJustification() default -1;

    int titlePosition() default -1;

    String titleFont() default "";

    String titleColor() default "";
//    ComponentBorder outsideBorder() default ...;
//    ComponentBorder insideBorder() default ...;
//    ComponentBorder border() default ...;
}
