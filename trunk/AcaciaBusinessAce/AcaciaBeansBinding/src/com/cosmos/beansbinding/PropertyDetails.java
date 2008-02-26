/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.beansbinding;

import com.cosmos.util.ClassHelper;
import java.io.Serializable;
import java.util.Formatter;

/**
 *
 * @author miro
 */
public class PropertyDetails
    implements Cloneable, Serializable
{

    private String propertyName;
    private String propertyTitle;
    private String propertyClassName;
    private boolean readOnly = false;
    private boolean editable = true;
    private boolean visible = true;
    private boolean hiden = false;
    private boolean required = false;
    private Object sourceUnreadableValue;
    private String columnName;
    private int orderPosition;

    /* Validation fields */
    private int validationType;
    private int validationRangeStart;
    private int validationEvent;
    private int validationRangeEnd;
    private String validationCustomMethod;
    private String validationRegex;
    private String validationTooltip;
    
    public PropertyDetails() {
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            String propertyClassName) {
        this(propertyName, propertyTitle, propertyClassName, null);
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            String propertyClassName,
            Object sourceUnreadableValue) {
        this(propertyName, propertyTitle, propertyClassName, sourceUnreadableValue, null);
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            String propertyClassName,
            Object sourceUnreadableValue,
            String columnName) {
        this.propertyName = propertyName;
        this.propertyTitle = propertyTitle;
        this.propertyClassName = propertyClassName;
        this.sourceUnreadableValue = sourceUnreadableValue;
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Class getPropertyClass() {
        if(propertyClassName != null)
        {
            return ClassHelper.getClass(propertyClassName);
        }

        return null;
    }

    public String getPropertyClassName() {
        return propertyClassName;
    }

    public void setPropertyClassName(String propertyClassName) {
        this.propertyClassName = propertyClassName;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isHiden() {
        return hiden;
    }

    public void setHiden(boolean hiden) {
        this.hiden = hiden;
    }

    public int getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getValidationCustomMethod() {
        return validationCustomMethod;
    }

    public void setValidationCustomMethod(String validationCustomMethod) {
        this.validationCustomMethod = validationCustomMethod;
    }

    public String getValidationRegex() {
        return validationRegex;
    }

    public void setValidationRegex(String validationRegex) {
        this.validationRegex = validationRegex;
    }
    
    public int getValidationEvent() {
        return validationEvent;
    }

    public void setValidationEvent(int validationEvent) {
        this.validationEvent = validationEvent;
    }

    public int getValidationRangeEnd() {
        return validationRangeEnd;
    }

    public void setValidationRangeEnd(int validationRangeEnd) {
        this.validationRangeEnd = validationRangeEnd;
    }

    public int getValidationRangeStart() {
        return validationRangeStart;
    }

    public void setValidationRangeStart(int validationRangeStart) {
        this.validationRangeStart = validationRangeStart;
    }

    public int getValidationType() {
        return validationType;
    }

    public void setValidationType(int validationType) {
        this.validationType = validationType;
    }

    public String getValidationTooltip() {
        return validationTooltip;
    }

    public void setValidationTooltip(String validationTooltip) {
        this.validationTooltip = validationTooltip;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property name='").append(propertyName);
        sb.append("', title='").append(propertyTitle).append('\'');
        sb.append(", class=").append(getPropertyClass());
        sb.append(", readOnly=").append(readOnly);
        sb.append(", editable=").append(editable);
        sb.append(", visible=").append(visible);
        sb.append(", hiden=").append(hiden);
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
