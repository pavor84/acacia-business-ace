package com.cosmos.acacia.annotation;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Miro
 */
@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.SOURCE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property
{
    public static final String NULL = "<!--[Property.NULL]-->";

    String title();
    boolean readOnly() default false;
    boolean editable() default true;
    boolean visible() default true;

    /**
     * hiden means is existing as ColumnBinding. If true, then that property
     * will not exist as ColumnBinding. By default all @Id properties are hiden.
     * @return
     */
    boolean hiden() default false;

    String sourceUnreadableValue() default NULL;
}
