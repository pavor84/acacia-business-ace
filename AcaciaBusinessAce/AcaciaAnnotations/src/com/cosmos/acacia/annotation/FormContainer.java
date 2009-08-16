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
public @interface FormContainer {

    public static final String DEPENDS_ENTITY_FORM = "<entityForm>";

    String name();

    String title() default "";

    Layout layout() default @Layout();

    Component container();

    int componentIndex() default -1;

    String parentContainerName() default "";

    RelationshipType relationshipType() default RelationshipType.None;

    String listPanelClassName() default "";

    Class entityClass() default void.class;

    /**
     * The property name(s) from which this property depends.
     * If the property name is FormContainer.DEPENDS_ENTITY_FORM, then the whole
     * form validity is the condition.
     * List<PropertyDetails> getPropertyDetailsDependencies() from PropertyDetails
     */
    String[] depends() default {};
}
