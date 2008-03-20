/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Miro
 */
@Target(value=ElementType.ANNOTATION_TYPE) 
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValidator {

    Class validator() default com.cosmos.beansbinding.validation.NoneValidator.class;
    
    ValidationType validationType() default ValidationType.NONE;
    
    boolean required() default false;
    
    boolean floating() default true;
    
    double minValue() default Double.MIN_VALUE;
    double maxValue() default Double.MAX_VALUE;

    int minLength() default 0;
    int maxLength() default Integer.MAX_VALUE;

    String regex() default "";
    String tooltip() default "";

    String fromDate() default "";
    String toDate() default "";
    String datePattern() default "";
}
