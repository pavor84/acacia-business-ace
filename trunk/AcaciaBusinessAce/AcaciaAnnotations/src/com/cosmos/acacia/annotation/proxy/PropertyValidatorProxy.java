/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class PropertyValidatorProxy extends AnnotationProxy<PropertyValidator, Property> implements Serializable {

    private PropertyProxy propertyProxy;
    private PropertyValidator propertyValidator;

    public PropertyValidatorProxy(PropertyProxy propertyProxy, PropertyValidator propertyValidator) {
        super(PropertyValidator.class, propertyProxy.getPropertyField(), propertyProxy.getEntityFormConstants());
        this.propertyProxy = propertyProxy;
        this.propertyValidator = propertyValidator;

        init();
    }

    private void init() {
    }

    @Override
    protected PropertyValidator getConstantAnnotation() {
        return propertyProxy.getConstantAnnotation().propertyValidator();
    }

    @Override
    protected PropertyValidator getDefaultAnnotation() {
        return propertyProxy.getDefaultAnnotation().propertyValidator();
    }

    @Override
    protected PropertyValidator getAnnotation() {
        return propertyValidator;
    }

    public PropertyProxy getPropertyProxy() {
        return propertyProxy;
    }

    public static PropertyValidator newInstance(PropertyProxy propertyProxy, PropertyValidator propertyValidator) {
        return (PropertyValidator) Proxy.newProxyInstance(
                PropertyValidator.class.getClassLoader(),
                new Class[]{PropertyValidator.class},
                new PropertyValidatorProxy(propertyProxy, propertyValidator));
    }
}
