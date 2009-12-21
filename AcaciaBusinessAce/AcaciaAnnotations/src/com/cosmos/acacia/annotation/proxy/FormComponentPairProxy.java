/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import java.io.Serializable;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class FormComponentPairProxy extends AnnotationProxy<FormComponentPair, Property> implements Serializable {

    private PropertyProxy propertyProxy;
    private FormComponentPair formComponentPair;

    public FormComponentPairProxy(PropertyProxy propertyProxy, FormComponentPair formComponentPair) {
        super(FormComponentPair.class, propertyProxy.getPropertyField(), propertyProxy.getEntityFormConstants());
        this.propertyProxy = propertyProxy;
        this.formComponentPair = formComponentPair;

        init();
    }

    protected PropertyProxy getPropertyProxy() {
        return propertyProxy;
    }

    private void init() {
        Object result;
        try {
            result = invoke(getAnnotation(), "firstComponent");
        } catch (Exception ex) {
            if(ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        }
        if(result instanceof IncompleteAnnotationException) {
            putAnnotationProxy("firstComponent", result);
        } else {
            Component component = ComponentProxy.newInstance(this, (Component) result, true);
            putAnnotationProxy("firstComponent", component);
        }

        try {
            result = invoke(getAnnotation(), "secondComponent");
        } catch (Exception ex) {
            if(ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        }
        if(result instanceof IncompleteAnnotationException) {
            putAnnotationProxy("secondComponent", result);
        } else {
            Component component = ComponentProxy.newInstance(this, (Component) result, false);
            putAnnotationProxy("secondComponent", component);
        }
    }

    @Override
    protected FormComponentPair getConstantAnnotation() {
        return propertyProxy.getConstantAnnotation().formComponentPair();
    }

    @Override
    protected FormComponentPair getDefaultAnnotation() {
        return propertyProxy.getDefaultAnnotation().formComponentPair();
    }

    @Override
    protected FormComponentPair getAnnotation() {
        return formComponentPair;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = super.invoke(proxy, method, args);
        if("parentContainerName".equals(method.getName()) && (result == null || ((String) result).length() == 0)) {
            String parentContainerName;
            if((parentContainerName = (String) propertyProxy.invoke("parentContainerName")) != null && parentContainerName.length() > 0) {
                return parentContainerName;
            }
        }

        return result;
    }

    public static FormComponentPair newInstance(PropertyProxy propertyProxy, FormComponentPair formComponentPair) {
        return (FormComponentPair) Proxy.newProxyInstance(
                FormComponentPair.class.getClassLoader(),
                new Class[]{FormComponentPair.class},
                new FormComponentPairProxy(propertyProxy, formComponentPair));
    }
}
