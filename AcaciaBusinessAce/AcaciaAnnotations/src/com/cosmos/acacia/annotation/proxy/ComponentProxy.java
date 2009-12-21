/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.ComponentBorder;
import com.cosmos.acacia.annotation.ComponentProperty;
import com.cosmos.acacia.annotation.Property;
import java.io.Serializable;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class ComponentProxy extends AnnotationProxy<Component, Property> implements Serializable {

    private FormComponentPairProxy formComponentPairProxy;
    private Component component;
    private boolean firstComponent;

    public ComponentProxy(FormComponentPairProxy formComponentPairProxy, Component component, boolean firstComponent) {
        super(Component.class, formComponentPairProxy.getPropertyField(), formComponentPairProxy.getEntityFormConstants());
        this.formComponentPairProxy = formComponentPairProxy;
        this.component = component;
        this.firstComponent = firstComponent;

        init();
    }

    private void init() {
        Object result;
        try {
            result = invoke(getAnnotation(), "componentProperties");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        int size;
        if(result instanceof IncompleteAnnotationException || (size = ((Object[])result).length) == 0) {
            putAnnotationProxy("componentProperties", result);
        } else {
            ComponentProperty[] componentProperties = new ComponentProperty[size];
            for(int i = 0; i < size; i++) {
                componentProperties[i] = ComponentPropertyProxy.newInstance(this, (ComponentProperty) ((Object[])result)[i], i);
            }
            putAnnotationProxy("componentProperties", componentProperties);
        }

        try {
            result = invoke(getAnnotation(), "componentBorder");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if(result instanceof IncompleteAnnotationException) {
            putAnnotationProxy("componentBorder", result);
        } else {
            ComponentBorder componentBorder = ComponentBorderProxy.newInstance(this, (ComponentBorder) result);
            putAnnotationProxy("componentBorder", componentBorder);
        }
    }

    @Override
    protected Component getConstantAnnotation() {
        if(firstComponent) {
            return formComponentPairProxy.getConstantAnnotation().firstComponent();
        } else {
            return formComponentPairProxy.getConstantAnnotation().secondComponent();
        }
    }

    @Override
    protected Component getDefaultAnnotation() {
        if(firstComponent) {
            return formComponentPairProxy.getDefaultAnnotation().firstComponent();
        } else {
            return formComponentPairProxy.getDefaultAnnotation().secondComponent();
        }
    }

    @Override
    protected Component getAnnotation() {
        return component;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = super.invoke(proxy, method, args);
        if("text".equals(method.getName()) && (result == null || ((String) result).length() == 0)) {
            String text;
            if((text = (String) formComponentPairProxy.getPropertyProxy().invoke("title")) != null && text.length() > 0) {
                return text + ":";
            }
        }

        return result;
    }

    public static Component newInstance(
            FormComponentPairProxy formComponentPairProxy, Component component, boolean firstComponent) {
        return (Component) Proxy.newProxyInstance(
                Component.class.getClassLoader(),
                new Class[]{Component.class},
                new ComponentProxy(formComponentPairProxy, component, firstComponent));
    }
}
