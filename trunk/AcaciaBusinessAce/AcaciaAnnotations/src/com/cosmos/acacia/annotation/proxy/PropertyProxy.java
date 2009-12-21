/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.entity.EntityAttributes;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class PropertyProxy extends AnnotationProxy<Property, Property> implements Serializable {

    @Property(title="")
    private static Property defaultAnnotation;

    public PropertyProxy(AccessibleObject propertyField, EntityAttributes<Property> entityFormConstants) {
        super(Property.class, propertyField, entityFormConstants);
        init();
    }

    private void init() {
        Annotation annotation = getAnnotation();
        Object result;
        try {
            result = invoke(annotation, "selectableList");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if(result instanceof IncompleteAnnotationException) {
            putAnnotationProxy("selectableList", result);
        } else {
            putAnnotationProxy("selectableList", SelectableListProxy.newInstance(this, (SelectableList) result));
        }

        try {
            result = invoke(annotation, "propertyValidator");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if(result instanceof IncompleteAnnotationException) {
            putAnnotationProxy("propertyValidator", result);
        } else {
            putAnnotationProxy("propertyValidator", PropertyValidatorProxy.newInstance(this, (PropertyValidator) result));
        }

        try {
            result = invoke(annotation, "formComponentPair");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if(result instanceof IncompleteAnnotationException) {
            putAnnotationProxy("formComponentPair", result);
        } else {
            putAnnotationProxy("formComponentPair", FormComponentPairProxy.newInstance(this, (FormComponentPair) result));
        }
        
    }

    @Override
    protected Property getAnnotation() {
        return getPropertyField().getAnnotation(Property.class);
    }

    @Override
    protected Property getConstantAnnotation() {
        return getEntityFormConstants().getAnnotation();
    }

    @Override
    protected Property getDefaultAnnotation() {
        if (defaultAnnotation == null) {
            try {
                AccessibleObject field = getAccessibleObject("defaultAnnotation");
                defaultAnnotation = field.getAnnotation(Property.class);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return defaultAnnotation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = super.invoke(proxy, method, args);
        if("customDisplay".equals(method.getName()) && (result == null || ((String) result).length() == 0)) {
            String customDisplaySuffix;
            if((customDisplaySuffix = getEntityFormConstants().getCustomDisplaySuffix()) != null) {
                return "${" + getPropertyName() + "." + customDisplaySuffix + "}";
            }
        }

        return result;
    }

    public static Property newInstance(AccessibleObject propertyField, EntityAttributes<Property> entityFormConstants) {
        return (Property) Proxy.newProxyInstance(
                Property.class.getClassLoader(),
                new Class[]{Property.class},
                new PropertyProxy(propertyField, entityFormConstants));
    }
}
