/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import com.cosmos.acacia.entity.EntityService;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Form {

    FormContainer mainContainer();

    FormContainer[] formContainers() default {};

    Class<? extends EntityService> serviceClass() default EntityService.NULL.class;
}
