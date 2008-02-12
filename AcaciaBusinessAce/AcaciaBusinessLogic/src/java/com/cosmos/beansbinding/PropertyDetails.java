/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.beansbinding;

import java.io.Serializable;

/**
 *
 * @author miro
 */
public class PropertyDetails
    implements Cloneable, Serializable
{

    private String propertyName;
    private String propertyTitle;
    private Class propertyClass;
    private Boolean readOnly;
    private Boolean editable;
    private Boolean visible;
    private Object sourceUnreadableValue;
    private String columnName;

    public PropertyDetails() {
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            Class propertyClass) {
        this(propertyName, propertyTitle, propertyClass, null);
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            Class propertyClass,
            Boolean editable) {
        this(propertyName, propertyTitle, propertyClass, editable, null);
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            Class propertyClass,
            Boolean editable,
            Boolean visible) {
        this(propertyName, propertyTitle, propertyClass, editable, visible, null);
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            Class propertyClass,
            Boolean editable,
            Boolean visible,
            Object sourceUnreadableValue) {
        this(propertyName, propertyTitle, propertyClass, editable, visible, sourceUnreadableValue, null);
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            Class propertyClass,
            Boolean editable,
            Boolean visible,
            Object sourceUnreadableValue,
            String columnName) {
        this.propertyName = propertyName;
        this.propertyTitle = propertyTitle;
        this.propertyClass = propertyClass;
        this.editable = editable;
        this.visible = visible;
        this.sourceUnreadableValue = sourceUnreadableValue;
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean isEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Class getPropertyClass() {
        return propertyClass;
    }

    public void setPropertyClass(Class propertyClass) {
        this.propertyClass = propertyClass;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public Object getSourceUnreadableValue() {
        return sourceUnreadableValue;
    }

    public void setSourceUnreadableValue(Object sourceUnreadableValue) {
        this.sourceUnreadableValue = sourceUnreadableValue;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property name='").append(propertyName);
        sb.append("', title='").append(propertyTitle).append('\'');
        sb.append(", class=").append(propertyClass);
        if(readOnly != null)
            sb.append(", readOnly=").append(readOnly);
        if(editable != null)
            sb.append(", editable=").append(editable);
        if(visible != null)
            sb.append(", visible=").append(visible);
        if(sourceUnreadableValue != null)
            sb.append(", sourceUnreadableValue=").append(sourceUnreadableValue);
        if(columnName != null)
            sb.append(", columnName=").append(columnName);
        sb.append(", super: ").append(super.toString());

        return sb.toString();
    }

    @Override
    public Object clone()
    {
        try
        {
            return (PropertyDetails)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            throw new RuntimeException(ex);
        }
    }

}
