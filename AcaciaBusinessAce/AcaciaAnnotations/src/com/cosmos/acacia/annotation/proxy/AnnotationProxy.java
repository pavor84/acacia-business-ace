/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.entity.EntityAttributes;
import com.cosmos.util.ClassHelper;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Miro
 */
public abstract class AnnotationProxy<T extends Annotation, P extends Annotation> implements InvocationHandler, Serializable {

    private Class<T> annotationClass;
    private AccessibleObject propertyField;
    private EntityAttributes<P> entityFormConstants;
    //
    private Map<String, Object> annotationMethodsMap = new HashMap<String, Object>();

    protected AnnotationProxy(Class<T> annotationClass, AccessibleObject propertyField, EntityAttributes<P> entityFormConstants) {
        this.annotationClass = annotationClass;
        this.propertyField = propertyField;
        this.entityFormConstants = entityFormConstants;
    }

    protected abstract T getConstantAnnotation();

    protected abstract T getDefaultAnnotation();

    protected abstract T getAnnotation();

    protected Object getAnnotationProxy(String methodName) {
        Object result;
        if((result = annotationMethodsMap.get(methodName)) instanceof IncompleteAnnotationException) {
            throw (IncompleteAnnotationException) result;
        }

        return result;
    }

    protected void putAnnotationProxy(String methodName, Object methodResult) {
        annotationMethodsMap.put(methodName, methodResult);
    }

    protected Class<T> getAnnotationClass() {
        return annotationClass;
    }

    protected EntityAttributes<P> getEntityFormConstants() {
        return entityFormConstants;
    }

    protected AccessibleObject getPropertyField() {
        return propertyField;
    }

    protected String getPropertyName() {
        return ClassHelper.getAccessibleObjectName(propertyField);
    }

    protected Object invoke(String methodName) throws Throwable {
        Method method = annotationClass.getMethod(methodName);
        return invoke(null, method, null);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation annotation = getAnnotation();
        Class returnType;
        if((returnType = method.getReturnType()).isArray()) {
            Object[] defaultArray = (Object[]) method.invoke(getDefaultAnnotation(), args);
            Object[] array = (Object[]) method.invoke(annotation, args);
            int size;
            if(defaultArray.length != (size = array.length)) {
                if(size == 0 || !(array[0] instanceof Annotation)) {
                    return array;
                }

                Object result;
                if((result = getAnnotationProxy(method.getName())) != null) {
                    return result;
                }

                throw new NullPointerException("Missing annotation proxy instance for " + returnType);
            }
        } else if(returnType.isAnnotation()) {
            Object result;
            if((result = getAnnotationProxy(method.getName())) != null) {
                return result;
            }

            throw new NullPointerException("Missing annotation proxy instance for " + returnType);
        }

        if(!ClassHelper.equals(getDefaultAnnotation(), annotation, method)) {
            try {
                Object result = ClassHelper.invoke(method, annotation);
                return result;
            } catch(IncompleteAnnotationException ex) {
            }
        }

        Annotation constantAnnotation;
        if((constantAnnotation = getConstantAnnotation()) != null) {
            Object result = ClassHelper.invoke(method, constantAnnotation);
            return result;
        }

        return null;
    }

    protected AccessibleObject getAccessibleObject(String fieldName) throws Exception {
        return ClassHelper.getAccessibleObject(getClass(), fieldName);
    }

    protected Object invoke(Annotation annotation, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(annotation, annotation.getClass().getMethod(methodName));
    }

    protected Object invoke(Annotation annotation, Method method) throws InvocationTargetException, IllegalAccessException {
        try {
            return ClassHelper.invoke(method, annotation);
        } catch(IncompleteAnnotationException ex) {
            return ex;
        }
    }
}
