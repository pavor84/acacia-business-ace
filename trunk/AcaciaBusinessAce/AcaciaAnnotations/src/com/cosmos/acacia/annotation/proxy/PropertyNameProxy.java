/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.SelectableList;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Proxy;

/**
 *
 * @author Miro
 */
public class PropertyNameProxy extends AnnotationProxy<PropertyName, Property> implements Serializable {

    @Property(title="",
        selectableList=@SelectableList(constructorParameters={@PropertyName()})
    )
    private static PropertyName defaultAnnotation;
    //
    private SelectableListProxy selectableListProxy;
    private PropertyName propertyName;
    private int itemId;

    public PropertyNameProxy(SelectableListProxy selectableListProxy, PropertyName propertyName, int itemId) {
        super(PropertyName.class, selectableListProxy.getPropertyField(), selectableListProxy.getEntityFormConstants());
        this.selectableListProxy = selectableListProxy;
        this.propertyName = propertyName;
        this.itemId = itemId;
    }

    @Override
    protected PropertyName getAnnotation() {
        return propertyName;
    }

    @Override
    protected PropertyName getConstantAnnotation() {
        PropertyName[] propertyNames = selectableListProxy.getConstantAnnotation().constructorParameters();
        if(itemId < propertyNames.length) {
            return propertyNames[itemId];
        }

        return null;
    }

    @Override
    protected PropertyName getDefaultAnnotation() {
        if (defaultAnnotation == null) {
            try {
                AccessibleObject field = getAccessibleObject("defaultAnnotation");
                Property property = field.getAnnotation(Property.class);
                defaultAnnotation = property.selectableList().constructorParameters()[0];
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return defaultAnnotation;
    }

    public SelectableListProxy getSelectableListProxy() {
        return selectableListProxy;
    }

    public static PropertyName newInstance(SelectableListProxy selectableListProxy, PropertyName propertyName, int itemId) {
        return (PropertyName) Proxy.newProxyInstance(
                PropertyName.class.getClassLoader(),
                new Class[]{PropertyName.class},
                new PropertyNameProxy(selectableListProxy, propertyName, itemId));
    }
}
