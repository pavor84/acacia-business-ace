/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.swing.JComponent;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface Component {

    public static final class NullJComponent extends JComponent {

        public NullJComponent() {
            throw new UnsupportedOperationException("The NULL class can not be used.");
        }
    }

    String resourceKey() default "";

    String text() default "";

    String toolTipText() default "";

    int horizontalAlignment() default -1;

    int verticalAlignment() default -1;

    String iconURI() default "";

    Class<? extends JComponent> componentClass();

    ComponentProperty[] componentProperties() default {};

    String componentConstraints() default "";

    ComponentBorder componentBorder() default @ComponentBorder();

    boolean scrollable() default false;
}
