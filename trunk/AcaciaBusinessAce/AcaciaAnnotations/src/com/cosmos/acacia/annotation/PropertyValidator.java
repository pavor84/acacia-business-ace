/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation;

import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public @interface PropertyValidator {

    //Class<? extends Validator> validatorClass() default Validator.class;
    int minValue() default Integer.MIN_VALUE;
    int maxValue() default Integer.MAX_VALUE;
}
