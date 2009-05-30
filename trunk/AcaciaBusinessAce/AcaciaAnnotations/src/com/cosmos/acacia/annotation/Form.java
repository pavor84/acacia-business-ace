/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import com.cosmos.acacia.entity.EntityService;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static com.cosmos.acacia.annotation.Component.NullJComponent;
import static com.cosmos.acacia.entity.EntityService.NullEntityService;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Form {

    FormContainer mainContainer() default @FormContainer(
            name="",
            container=@Component(componentClass=NullJComponent.class));

    FormContainer[] formContainers() default {};

    Class<? extends EntityService> serviceClass() default NullEntityService.class;

    Logic logic() default @Logic();
}
