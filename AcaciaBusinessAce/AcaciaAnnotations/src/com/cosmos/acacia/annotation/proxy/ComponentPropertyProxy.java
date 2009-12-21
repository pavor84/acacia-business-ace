/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.ComponentProperty;
import com.cosmos.acacia.annotation.Property;
import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class ComponentPropertyProxy extends AnnotationProxy<ComponentProperty, Property> implements Serializable {

    private ComponentProxy componentProxy;
    private ComponentProperty componentProperty;
    private int itemId;

    public ComponentPropertyProxy(ComponentProxy componentProxy, ComponentProperty componentProperty, int itemId) {
        super(ComponentProperty.class, componentProxy.getPropertyField(), componentProxy.getEntityFormConstants());
        this.componentProxy = componentProxy;
        this.componentProperty = componentProperty;
        this.itemId = itemId;

        init();
    }

    private void init() {
    }

    @Override
    protected ComponentProperty getConstantAnnotation() {
        ComponentProperty[] componentProperties;
        if(itemId < (componentProperties = componentProxy.getConstantAnnotation().componentProperties()).length) {
            return componentProperties[itemId];
        }

        return null;
    }

    @Override
    protected ComponentProperty getDefaultAnnotation() {
        ComponentProperty[] componentProperties;
        if(itemId < (componentProperties = componentProxy.getDefaultAnnotation().componentProperties()).length) {
            return componentProperties[itemId];
        }

        return null;
    }

    @Override
    protected ComponentProperty getAnnotation() {
        return componentProperty;
    }

    public static ComponentProperty newInstance(
            ComponentProxy componentProxy, ComponentProperty componentProperty, int itemId) {
        return (ComponentProperty) Proxy.newProxyInstance(
                ComponentProperty.class.getClassLoader(),
                new Class[]{ComponentProperty.class},
                new ComponentPropertyProxy(componentProxy, componentProperty, itemId));
    }
}
