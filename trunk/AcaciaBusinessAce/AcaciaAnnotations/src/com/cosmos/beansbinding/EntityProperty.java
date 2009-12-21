/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.entity.EntityAttributes;
import com.cosmos.beansbinding.validation.BaseValidator;
import com.cosmos.beansbinding.validation.DateRangeValidator;
import com.cosmos.beansbinding.validation.DateValidator;
import com.cosmos.beansbinding.validation.MaskFormatterValidator;
import com.cosmos.beansbinding.validation.NumericRangeValidator;
import com.cosmos.beansbinding.validation.NumericValidator;
import com.cosmos.beansbinding.validation.RegexValidator;
import com.cosmos.beansbinding.validation.TextLengthValidator;
import com.cosmos.util.ClassHelper;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.swing.text.DefaultFormatter;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class EntityProperty implements Cloneable, Serializable {

    private static final String EMPTY_STRING = "";
    private static final boolean DEFAULT_EDITABLE = true;
    //
    private String propertyName;
    private int orderPosition;
    //
    private String propertyTitle;
    private Class propertyClass;
    private String propertyClassName;
    private boolean readOnly = false;
    private boolean editable = DEFAULT_EDITABLE;
    private boolean visible = true;
    private boolean hiden = false;
    private boolean required = false;
    private boolean percent = false;
    private DefaultFormatter formatter;
    private String formatPattern;
    private Object sourceUnreadableValue;
    private String columnName;
    private Validator validator;
    private ResourceDisplay resourceDisplayInTable;
    private boolean exportable;
    private byte reportColumnWidth;
    private int maxTableColumnWidth;
    /**
     * @see #getCustomDislay()
     */
    private String customDisplay;
    private boolean showOnly;
    private AutoBinding.UpdateStrategy updateStrategy = AutoBinding.UpdateStrategy.READ_WRITE;
    private String selectableListDialogClassName;
    private List<PropertyDetail> selectableListDialogConstructorParameters = new ArrayList<PropertyDetail>();
    private List<EntityProperty> propertyDetailsDependencies = new ArrayList<EntityProperty>();

    protected EntityProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    protected EntityProperty(String propertyName, int orderPosition) {
        this.propertyName = propertyName;
        this.orderPosition = orderPosition;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public int getOrderPosition() {
        return orderPosition;
    }

    protected void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCustomDisplay() {
        return customDisplay;
    }

    public void setCustomDisplay(String customDisplay) {
        this.customDisplay = customDisplay;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isExportable() {
        return exportable;
    }

    public void setExportable(boolean exportable) {
        this.exportable = exportable;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public DefaultFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DefaultFormatter formatter) {
        this.formatter = formatter;
    }

    public boolean isHiden() {
        return hiden;
    }

    public void setHiden(boolean hiden) {
        this.hiden = hiden;
    }

    public int getMaxTableColumnWidth() {
        return maxTableColumnWidth;
    }

    public void setMaxTableColumnWidth(int maxTableColumnWidth) {
        this.maxTableColumnWidth = maxTableColumnWidth;
    }

    public boolean isPercent() {
        return percent;
    }

    public void setPercent(boolean percent) {
        this.percent = percent;
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

    public List<EntityProperty> getPropertyDetailsDependencies() {
        return propertyDetailsDependencies;
    }

    public void setPropertyDetailsDependencies(List<EntityProperty> propertyDetailsDependencies) {
        this.propertyDetailsDependencies = propertyDetailsDependencies;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public byte getReportColumnWidth() {
        return reportColumnWidth;
    }

    public void setReportColumnWidth(byte reportColumnWidth) {
        this.reportColumnWidth = reportColumnWidth;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ResourceDisplay getResourceDisplayInTable() {
        return resourceDisplayInTable;
    }

    public void setResourceDisplayInTable(ResourceDisplay resourceDisplayInTable) {
        this.resourceDisplayInTable = resourceDisplayInTable;
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

    public boolean isShowOnly() {
        return showOnly;
    }

    public void setShowOnly(boolean showOnly) {
        this.showOnly = showOnly;
    }

    public Object getSourceUnreadableValue() {
        return sourceUnreadableValue;
    }

    public void setSourceUnreadableValue(Object sourceUnreadableValue) {
        this.sourceUnreadableValue = sourceUnreadableValue;
    }

    public UpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    public void setUpdateStrategy(UpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void merge(EntityProperty master) {
        if(master.propertyTitle != null) {
            propertyTitle = master.propertyTitle;
        }
        if(master.propertyClass != null) {
            propertyClass = master.propertyClass;
        }
        if(master.propertyClassName != null) {
            propertyClassName = master.propertyClassName;
        }
        readOnly = master.readOnly;
        editable = master.editable;
        visible = master.visible;
        hiden = master.hiden;
        required = master.required;
        percent = master.percent;
        if(master.formatter != null) {
            formatter = master.formatter;
        }
        if(master.formatPattern != null) {
            formatPattern = master.formatPattern;
        }
        if(master.sourceUnreadableValue != null) {
            sourceUnreadableValue = master.sourceUnreadableValue;
        }
        if(master.columnName != null) {
            columnName = master.columnName;
        }
        if(master.orderPosition > 0) {
            orderPosition = master.orderPosition;
        }
        if(master.validator != null) {
            validator = master.validator;
        }
        if(master.resourceDisplayInTable != null) {
            resourceDisplayInTable = master.resourceDisplayInTable;
        }
        exportable = master.exportable;
        reportColumnWidth = master.reportColumnWidth;
        maxTableColumnWidth = master.maxTableColumnWidth;
        if(master.customDisplay != null) {
            customDisplay = master.customDisplay;
        }
        showOnly = master.showOnly;
        if(master.updateStrategy != null) {
            updateStrategy = master.updateStrategy;
        }
        if(master.selectableListDialogClassName != null) {
            selectableListDialogClassName = master.selectableListDialogClassName;
        }
        if(master.selectableListDialogConstructorParameters != null) {
            selectableListDialogConstructorParameters = master.selectableListDialogConstructorParameters;
        }
        if(master.propertyDetailsDependencies != null) {
            propertyDetailsDependencies = master.propertyDetailsDependencies;
        }
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
        sb.append(", orderPosition=").append(orderPosition);
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityProperty other = (EntityProperty) obj;
        if ((this.propertyName == null) ? (other.propertyName != null) : !this.propertyName.equals(other.propertyName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if(propertyName != null) {
            return propertyName.hashCode();
        }

        return 0;
    }

    @Override
    public Object clone() {
        try {
            return (EntityProperty) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static EntityProperty createEntityProperty(
            String propertyName,
            String propertyTitle,
            String propertyClassName,
            int orderPosition,
            boolean editable,
            Map<String, EntityAttributes<Property>> entityFormConstantsMap) {
        EntityProperty entityProperty = new EntityProperty(propertyName, orderPosition);
        entityProperty.setPropertyTitle(propertyTitle);
        entityProperty.setPropertyClassName(propertyClassName);
        entityProperty.setEditable(editable);

        return entityProperty;
    }

    public static EntityProperty createEntityProperty(
            String propertyName,
            String propertyTitle,
            String propertyClassName,
            int orderPosition,
            Map<String, EntityAttributes<Property>> entityFormConstantsMap) {
        return createEntityProperty(propertyName, propertyTitle, propertyClassName, orderPosition, DEFAULT_EDITABLE, entityFormConstantsMap);
    }

    public static EntityProperty createEntityProperty(
            String propertyName,
            String propertyTitle,
            String propertyClassName,
            Map<String, EntityAttributes<Property>> entityFormConstantsMap) {
        return createEntityProperty(propertyName, propertyTitle, propertyClassName, 0, entityFormConstantsMap);
    }

    /**
     * Create single property details for a given class and property name
     * @param entityClass - not null
     * @param propertyName - not null
     * @param orderPosition - may be null
     * @return - null if the field for such property is not found
     */
    public static EntityProperty createEntityProperty(
            Class entityClass,
            String propertyName,
            Integer orderPosition,
            Map<String, EntityAttributes<Property>> entityFormConstantsMap) {
        try {
            AccessibleObject propertyField = ClassHelper.getAccessibleObject(entityClass, propertyName);
            return createEntityProperty(
                    propertyField,
                    propertyName,
                    ClassHelper.getClassName(propertyField),
                    orderPosition,
                    entityFormConstantsMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static EntityProperty createEntityProperty(
            AccessibleObject propertyField,
            String propertyName,
            String propertyClassName,
            Integer orderPosition,
            Map<String, EntityAttributes<Property>> entityFormConstantsMap) {
        Property property = ClassHelper.getProperty(propertyField, entityFormConstantsMap);
        if (property == null) {
            return null;
        }

        EntityProperty pd;
        if(property.index() > 0) {
            orderPosition = property.index();
        }
        if (orderPosition != null) {
            pd = new EntityProperty(propertyName, orderPosition);
        } else {
            pd = new EntityProperty(propertyName);
        }

        pd.setPropertyClassName(propertyClassName);//
        pd.setPropertyTitle(property.title());
        pd.setReadOnly(property.readOnly());
        pd.setEditable(property.editable());
        pd.setVisible(property.visible());
        pd.setHiden(property.hidden());
        pd.setPercent(property.percent());
        pd.setShowOnly(property.showOnly());
        pd.setUpdateStrategy(property.updateStrategy());
        pd.setMaxTableColumnWidth(property.maxTableColumnWidth());
        SelectableList selectableList = property.selectableList();
        String value;
        if (!EMPTY_STRING.equals(value = selectableList.className())) {
            pd.setSelectableListDialogClassName(value);
            int size;
            if ((size = selectableList.constructorParameters().length) > 0) {
                List<PropertyDetail> list = new ArrayList<PropertyDetail>(size);
                for(PropertyName pn : selectableList.constructorParameters()) {
                    if (!EMPTY_STRING.equals(value = pn.setter())) {
                        list.add(new PropertyDetail(pn.getter(), value));
                    } else {
                        list.add(new PropertyDetail(pn.getter()));
                    }
                }
                pd.setSelectableListDialogConstructorParameters(list);
            }
        }
        Class<DefaultFormatter> formatterClass = property.formatter();
        if (!DefaultFormatter.class.equals(formatterClass)) {
            try {
                pd.setFormatter(formatterClass.newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (!EMPTY_STRING.equals(value = property.formatPattern())) {
            pd.setFormatPattern(value);
        }
        pd.setResourceDisplayInTable(property.resourceDisplayInTable());
        pd.setExportable(property.exportable());
        pd.setReportColumnWidth(property.reportColumnWidth());

        if (!EMPTY_STRING.equals(property.customDisplay())) {
            pd.setCustomDisplay(property.customDisplay());
        }

        /* Setting validation-related values */
        PropertyValidator propertyValidator = property.propertyValidator();

        if (propertyValidator.validationType() != ValidationType.NONE || propertyValidator.required()) {
            BaseValidator validator = new BaseValidator();

            validator.setRequired(propertyValidator.required());

            if (propertyValidator.validator() != com.cosmos.beansbinding.validation.NoneValidator.class) {
                try {
                    validator.addValidator((Validator) propertyValidator.validator().newInstance());
                } catch (Exception ex) {
                    // Log it!
                    ex.printStackTrace();
                }
            }

            if (propertyValidator.validationType() == ValidationType.DATE) {
                String strValue = propertyValidator.datePattern();
                if (strValue != null && (strValue = strValue.trim()).length() > 0) {
                    DateValidator dateValidator = new DateValidator();

                    dateValidator.setDatePattern(strValue);
                    validator.addValidator(dateValidator);
                }
            }

            if (propertyValidator.validationType() == ValidationType.DATE_RANGE) {
                DateRangeValidator dateRangeValidator = new DateRangeValidator();
                String strValue = propertyValidator.fromDate();
                if (strValue != null && (strValue = strValue.trim()).length() > 0) {
                    Date date = now(strValue);
                    if (date != null) {
                        dateRangeValidator.setFromDate(date);
                    } else {
                        dateRangeValidator.setFromDate(strValue);
                    }
                }

                strValue = propertyValidator.toDate();
                if (strValue != null && (strValue = strValue.trim()).length() > 0) {
                    Date date = now(strValue);
                    if (date != null) {
                        dateRangeValidator.setToDate(date);
                    } else {
                        dateRangeValidator.setToDate(strValue);
                    }
                }

                validator.addValidator(dateRangeValidator);
            }

            if (propertyValidator.validationType() == ValidationType.NUMBER) {
                NumericValidator numericValidator = new NumericValidator();
                validator.addValidator(numericValidator);
                numericValidator.setFloating(propertyValidator.floating());
            }

            if (propertyValidator.validationType() == ValidationType.NUMBER_RANGE) {
                NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
                numericRangeValidator.setMaxValue(propertyValidator.maxValue());
                numericRangeValidator.setMinValue(propertyValidator.minValue());
                validator.addValidator(numericRangeValidator);
                numericRangeValidator.setFloating(propertyValidator.floating());
            }

            if (propertyValidator.validationType() == ValidationType.LENGTH) {
                TextLengthValidator rangeValidator = new TextLengthValidator();
                rangeValidator.setMaxLength(propertyValidator.maxLength());
                rangeValidator.setMinLength(propertyValidator.minLength());
                validator.addValidator(rangeValidator);
            }

            if (propertyValidator.validationType() == ValidationType.REGEX || propertyValidator.regex() != null) {
                String strValue = propertyValidator.regex();
                if (strValue != null && (strValue = strValue.trim()).length() > 0) {
                    RegexValidator regexValidator = new RegexValidator();
                    regexValidator.setPattern(strValue);
                    validator.addValidator(regexValidator);
                }
            }

            if (propertyValidator.validationType() == ValidationType.MASK_FORMATTER) {
                MaskFormatterValidator v = new MaskFormatterValidator();
                v.setMaxLength(propertyValidator.maxLength());
                v.setMinLength(propertyValidator.minLength());
                validator.addValidator(v);
            }

            String strValue = propertyValidator.tooltip();
            if (strValue != null && (strValue = strValue.trim()).length() > 0) {
                validator.setTooltip(strValue);
            }

            pd.setValidator(validator);
        }

        value = property.sourceUnreadableValue();
        if (!EMPTY_STRING.equals(value)) {
            pd.setSourceUnreadableValue(value);
        }

        String columnName = null;
        boolean nullable = true;

        Annotation column = propertyField.getAnnotation(Column.class);
        if (column == null) {
            column = propertyField.getAnnotation(JoinColumn.class);
        }
        if (column == null) {
            column = propertyField.getAnnotation(PrimaryKeyJoinColumn.class);
        }
        if (column == null) {
            column = propertyField.getAnnotation(DiscriminatorColumn.class);
        }

        if (column != null) {
            try {
                columnName = (String) column.getClass().getDeclaredMethod("name").invoke(column);
                nullable = (Boolean) column.getClass().getDeclaredMethod("nullable").invoke(column);
            } catch (NoSuchMethodException ex) {
                nullable = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (columnName != null) {
            pd.setColumnName(columnName);
        } else {
            pd.setColumnName(pd.getPropertyName());
        }

        Id id = propertyField.getAnnotation(Id.class);
        IdClass idClass = propertyField.getAnnotation(IdClass.class);
        EmbeddedId embeddedId = propertyField.getAnnotation(EmbeddedId.class);
        if (id != null || idClass != null || embeddedId != null) {
            pd.setReadOnly(true);
            pd.setEditable(false);
            pd.setVisible(false);
            pd.setHiden(true);
            pd.setRequired(true);
        }

        if (!pd.isRequired() && column != null && !nullable) {
            System.out.println("Auto-required: " + columnName);
            pd.setRequired(true);
        }

        if (pd.isRequired()) {
            BaseValidator validator = (BaseValidator) pd.getValidator();
            if (validator == null) {
                validator = new BaseValidator();
            }

            if (!validator.isRequired()) {
                validator.setRequired(true);
                pd.setValidator(validator);
            }
        }

        // Setting custom display for Dates
        if (pd.getPropertyClass() == Date.class && EMPTY_STRING.equals(pd.getCustomDisplay())) {
            System.out.println("Setting date custom display for property " + pd.getPropertyName());

            String name = pd.getPropertyName();
            pd.setCustomDisplay("${" + name + ".date}.${" + name + ".month}.${" + name + ".year}");
        }

        return pd;
    }

    private static Date now(String now) {
        if ("now".equalsIgnoreCase(now) || "now()".equalsIgnoreCase(now)) {
            return new Date();
        }

        return null;
    }
}
