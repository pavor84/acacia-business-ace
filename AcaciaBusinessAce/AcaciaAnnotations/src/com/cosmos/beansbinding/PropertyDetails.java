/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.beansbinding;

import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.util.ClassHelper;
import java.io.Serializable;
import java.util.List;
import javax.swing.text.DefaultFormatter;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author miro
 */
public class PropertyDetails implements Cloneable, Serializable {

    private String propertyName;
    private String propertyTitle;
    private Class propertyClass;
    private String propertyClassName;
    private boolean readOnly = false;
    private boolean editable = true;
    private boolean visible = true;
    private boolean hiden = false;
    private boolean required = false;
    private boolean percent = false;
    private DefaultFormatter formatter;
    private String formatPattern;
    private Object sourceUnreadableValue;
    private String columnName;
    private int orderPosition;
    private Validator validator;
    private ResourceDisplay resourceDisplayInTable;
    private boolean exportable;
    private byte reportColumnWidth;
    /**
     * @see #getCustomDislay()
     */
    private String customDisplay;
    private boolean showOnly;
    private AutoBinding.UpdateStrategy updateStrategy = AutoBinding.UpdateStrategy.READ_WRITE;
    private String selectableListDialogClassName;
    private List<PropertyDetail> selectableListDialogConstructorParameters;
    private List<PropertyDetails> propertyDetailsDependencies;

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
            int orderPosition) {
        this(propertyName, propertyTitle, propertyClassName, null, null, orderPosition);
    }
    
    public PropertyDetails(
                           String propertyName,
                           String propertyTitle,
                           String propertyClassName,
                           int orderPosition, boolean editable) {
        this(propertyName, propertyTitle, propertyClassName, null, null, orderPosition, editable);
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
        this(propertyName, propertyTitle, propertyClassName, sourceUnreadableValue, columnName, 0);
    }

    public PropertyDetails(
            String propertyName,
            String propertyTitle,
            String propertyClassName,
            Object sourceUnreadableValue,
            String columnName,
            int orderPosition) {
        this(propertyName, propertyTitle, propertyClassName, sourceUnreadableValue, columnName, orderPosition, true);
    }
    
    public PropertyDetails(
                           String propertyName,
                           String propertyTitle,
                           String propertyClassName,
                           Object sourceUnreadableValue,
                           String columnName,
                           int orderPosition, boolean editable) {
       this.propertyName = propertyName;
       this.propertyTitle = propertyTitle;
       this.propertyClassName = propertyClassName;
       this.sourceUnreadableValue = sourceUnreadableValue;
       this.columnName = columnName;
       this.orderPosition = orderPosition;
       this.editable = editable;
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

    public boolean isShowOnly() {
        return showOnly;
    }

    public void setShowOnly(boolean showOnly) {
        this.showOnly = showOnly;
    }

    public UpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    public void setUpdateStrategy(UpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public String getSelectableListDialogClassName() {
        return selectableListDialogClassName;
    }

    public void setSelectableListDialogClassName(String selectableListDialogClassName) {
        this.selectableListDialogClassName = selectableListDialogClassName;
    }

    public List<PropertyDetail> getSelectableListDialogConstructorParameters() {
        return selectableListDialogConstructorParameters;
    }

    public void setSelectableListDialogConstructorParameters(List<PropertyDetail> selectableListDialogConstructorParameters) {
        this.selectableListDialogConstructorParameters = selectableListDialogConstructorParameters;
    }

    public List<PropertyDetails> getPropertyDetailsDependencies() {
        return propertyDetailsDependencies;
    }

    public void setPropertyDetailsDependencies(List<PropertyDetails> propertyDetailsDependencies) {
        this.propertyDetailsDependencies = propertyDetailsDependencies;
    }

    public boolean isPercent() {
        return percent;
    }

    public void setPercent(boolean percent) {
        this.percent = percent;
    }

    public DefaultFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DefaultFormatter formatter) {
        this.formatter = formatter;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
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
        if (propertyClass == null && propertyClassName != null) {
            propertyClass = ClassHelper.getClass(propertyClassName);
        }

        return propertyClass;
    }

    public void setPropertyClass(Class propertyClass) {
        this.propertyClass = propertyClass;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
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
        sb.append(", percent=").append(percent);
        if (sourceUnreadableValue != null) {
            sb.append(", sourceUnreadableValue=").append(sourceUnreadableValue);
        }
        if (columnName != null) {
            sb.append(", columnName=").append(columnName);
        }
        sb.append(", super: ").append(super.toString());

        return sb.toString();
    }

    @Override
    public Object clone() {
        try {
            return (PropertyDetails) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Supply EL property string to use when displaying the given object.
     * If not provided, {@link #propertyName} will be used.
     * Getter for customDislay
     * @return String
     */
    public String getCustomDisplay() {
        return customDisplay;
    }

    /**
     * Setter for customDislay
     * @param customDisplay - String
     */
    public void setCustomDisplay(String customDisplay) {
        this.customDisplay = customDisplay;
    }

    /**
     * Gets the specific display option for a resource when it is going
     * to be displayed in a table
     *
     * @return the resource display
     */
    public ResourceDisplay getResourceDisplayInTable() {
        return resourceDisplayInTable;
    }

    /**
     * Sets the specific display option for a resource when it is going
     * to be displayed in a table
     * @param resourceDisplayInTable
     */
    public void setResourceDisplayInTable(ResourceDisplay resourceDisplayInTable) {
        this.resourceDisplayInTable = resourceDisplayInTable;
    }

    /**
     * Sets whether the property is exportable (in reports)
     *
     * @return exportable
     */
    public boolean isExportable() {
        return exportable;
    }

    /**
     * Gets whether the property is exportable (in reports)
     *
     * @param exportable
     */
    public void setExportable(boolean exportable) {
        this.exportable = exportable;
    }

    /**
     * Gets the width (in percents) of the column in reports
     * @return reportColumnWidth
     */
    public byte getReportColumnWidth() {
        return reportColumnWidth;
    }

    /**
     * Sets the width (in percents) of the column in reports
     * @param reportColumnWidth
     */
    public void setReportColumnWidth(byte reportColumnWidth) {
        this.reportColumnWidth = reportColumnWidth;
    }
}