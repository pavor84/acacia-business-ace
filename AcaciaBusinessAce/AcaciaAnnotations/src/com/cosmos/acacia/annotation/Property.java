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
@Retention(RetentionPolicy.RUNTIME)
public @interface Property
{
    public static final String NULL = NullClass.NULL;

    String title();
    boolean readOnly() default false;
    boolean editable() default true;
    boolean visible() default true;
    boolean percent() default false;

    /**
     * hidden means is existing as ColumnBinding. If true, then that property
     * will not exist as ColumnBinding. By default all @Id properties are hidden.
     * @return
     */
    boolean hidden() default false;

    String sourceUnreadableValue() default NULL;

    PropertyValidator propertyValidator() default @PropertyValidator;

    /**
     * Supply EL property string to use when displaying the given element.
     * The EL expression will be applied over the current property value.
     * If not provided, {@link #propertyName} will be used instead.
     * @return String
     */
    String customDisplay() default NULL;

    /**
     * Specifies how is the resource displayed in a table
     */
     ResourceDisplay resourceDisplayInTable() default ResourceDisplay.ShortName;

     /**
      * Specified wheter the field is exportable (in reports)
      */
     boolean exportable() default false;

     /**
      * Specifies the width (in percentage) of the column in reports
      * Remember to drop 5% if a front id column is to be added
      */
     byte reportColumnWidth() default 0;
}
