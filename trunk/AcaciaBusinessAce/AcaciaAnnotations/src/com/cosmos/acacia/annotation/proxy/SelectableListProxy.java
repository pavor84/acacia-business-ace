/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.SelectableList;
import java.io.Serializable;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class SelectableListProxy extends AnnotationProxy<SelectableList, Property> implements Serializable {

    private PropertyProxy propertyProxy;
    private SelectableList selectableList;

    public SelectableListProxy(PropertyProxy propertyProxy, SelectableList selectableList) {
        super(SelectableList.class, propertyProxy.getPropertyField(), propertyProxy.getEntityFormConstants());
        this.propertyProxy = propertyProxy;
        this.selectableList = selectableList;

        init();
    }

    private void init() {
        Object result;
        try {
            result = invoke(getAnnotation(), "constructorParameters");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        int size;
        if(result instanceof IncompleteAnnotationException || (size = ((Object[])result).length) == 0) {
            putAnnotationProxy("constructorParameters", result);
        } else {
            PropertyName[] propetyNames = new PropertyName[size];
            for(int i = 0; i < size; i++) {
                propetyNames[i] = PropertyNameProxy.newInstance(this, (PropertyName) ((Object[])result)[i], i);
            }
            putAnnotationProxy("constructorParameters", propetyNames);
        }
    }

    protected PropertyProxy getPropertyProxy() {
        return propertyProxy;
    }

    @Override
    protected SelectableList getAnnotation() {
        return selectableList;
    }

    @Override
    protected SelectableList getConstantAnnotation() {
        return propertyProxy.getConstantAnnotation().selectableList();
    }

    @Override
    protected SelectableList getDefaultAnnotation() {
        return propertyProxy.getDefaultAnnotation().selectableList();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = super.invoke(proxy, method, args);
        if("className".equals(method.getName()) && (result == null || ((String) result).length() == 0)) {
            String listFormClassName;
            if((listFormClassName = getEntityFormConstants().getListFormClassName()) != null) {
                return listFormClassName;
            }
        }

        return result;
    }

    public static SelectableList newInstance(PropertyProxy propertyProxy, SelectableList selectableList) {
        return (SelectableList) Proxy.newProxyInstance(
                SelectableList.class.getClassLoader(),
                new Class[]{SelectableList.class},
                new SelectableListProxy(propertyProxy, selectableList));
    }
}
