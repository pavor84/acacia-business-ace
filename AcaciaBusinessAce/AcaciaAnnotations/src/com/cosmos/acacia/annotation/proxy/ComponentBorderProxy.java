/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.ComponentBorder;
import com.cosmos.acacia.annotation.Property;
import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class ComponentBorderProxy extends AnnotationProxy<ComponentBorder, Property> implements Serializable {

    private ComponentProxy componentProxy;
    private ComponentBorder componentBorder;

    public ComponentBorderProxy(ComponentProxy componentProxy, ComponentBorder componentBorder) {
        super(ComponentBorder.class, componentProxy.getPropertyField(), componentProxy.getEntityFormConstants());
        this.componentProxy = componentProxy;
        this.componentBorder = componentBorder;

        init();
    }

    private void init() {
    }

    @Override
    protected ComponentBorder getConstantAnnotation() {
        return componentProxy.getConstantAnnotation().componentBorder();
    }

    @Override
    protected ComponentBorder getDefaultAnnotation() {
        return componentProxy.getDefaultAnnotation().componentBorder();
    }

    @Override
    protected ComponentBorder getAnnotation() {
        return componentBorder;
    }

    public static ComponentBorder newInstance(
            ComponentProxy componentProxy, ComponentBorder componentBorder) {
        return (ComponentBorder) Proxy.newProxyInstance(
                ComponentBorder.class.getClassLoader(),
                new Class[]{ComponentBorder.class},
                new ComponentBorderProxy(componentProxy, componentBorder));
    }
}
