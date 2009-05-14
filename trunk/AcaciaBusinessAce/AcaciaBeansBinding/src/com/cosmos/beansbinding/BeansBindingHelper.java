/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.beansbinding;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.Validator;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.beansbinding.validation.BaseValidator;
import com.cosmos.beansbinding.validation.DateRangeValidator;
import com.cosmos.beansbinding.validation.DateValidator;
import com.cosmos.beansbinding.validation.MaskFormatterValidator;
import com.cosmos.beansbinding.validation.NumericRangeValidator;
import com.cosmos.beansbinding.validation.NumericValidator;
import com.cosmos.beansbinding.validation.RegexValidator;
import com.cosmos.beansbinding.validation.TextLengthValidator;
import com.cosmos.util.ClassHelper;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author miro
 */
public class BeansBindingHelper {

    public static final String DEFAULT_FORMATTER_CLASS_NAME = DefaultFormatter.class.getName();
    private static final String EMPTY_STRING = "";
    protected static Logger log = Logger.getLogger(BeansBindingHelper.class);

    public static EntityProperties createEntityProperties(Class entityClass, boolean checkExportable) {
        EntityProperties entityProperties = new EntityProperties(entityClass);

        boolean isEntity = false;
        for (Annotation annotation : entityClass.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(Entity.class)) {
                isEntity = true;
            } else if (annotation.annotationType().equals(Table.class)) {
                Table table = (Table) annotation;
                entityProperties.setTableName(table.name());
            }
        }

// What if not an entity ?
//        if(!isEntity)
//            throw new IllegalArgumentException("The class " + entityClass.getName() + " is not entity because the annotation 'javax.persistence.Entity' is not present into the declared class annotations.");

        if (entityProperties.getTableName() == null) {
            entityProperties.setTableName(entityClass.getSimpleName());
        }

        int orderPosition = 0;
        Field[] fields = entityClass.getDeclaredFields();
        List<PropertyDetails> properties = new ArrayList<PropertyDetails>(fields.length);
        for (Field field : fields) {
            PropertyDetails detailsForField = createPropertyDetails(
                    field,
                    field.getName(),
                    ClassHelper.getClassName(field.getType()),
                    orderPosition += 10);

            if (detailsForField != null) {
                // add the property if no exportability check is required,
                // or the check is required, and the field is exportable
                if (!checkExportable || detailsForField.isExportable()) {
                    properties.add(detailsForField);
                }
            }
        }

        entityProperties.setBeanProperties(properties);

        for (Field field : fields) {
            Property property = field.getAnnotation(Property.class);
            if (property == null) {
                continue;
            }

            String[] propertyNames;
            if((propertyNames = property.depends()).length > 0) {
                PropertyDetails propertyDetails = entityProperties.getPropertyDetails(field.getName());
                propertyDetails.setPropertyDetailsDependencies(getPropertyDetails(propertyNames, entityProperties));
            }
        }

        return entityProperties;
    }

    private static List<PropertyDetails> getPropertyDetails(String[] propertyNames,
            EntityProperties entityProperties) {
        List<PropertyDetails> list = new ArrayList<PropertyDetails>(propertyNames.length);
        for (String propertyName : propertyNames) {
            PropertyDetails propertyDetails;
            if ((propertyDetails = entityProperties.getPropertyDetails(propertyName)) == null) {
                throw new RuntimeException("Missing property '" + propertyName + "' in " + entityProperties);
            }
            list.add(propertyDetails);
        }

        return list;
    }

    public static EntityProperties createEntityProperties(Class entityClass) {
        return createEntityProperties(entityClass, false);
    }

    /**
     * Create single property details for a given class and property name
     * @param entityClass - not null
     * @param propertyName - not null
     * @param orderPosition - may be null
     * @return - null if the field for such property is not found
     */
    @SuppressWarnings("unchecked")
    public static PropertyDetails createPropertyDetails(Class entityClass, String propertyName, Integer orderPosition) {
        try {
            Field field = null;
            //search for annotated field
            Field[] fields = entityClass.getDeclaredFields();
            for (Field f : fields) {
                if (f.getName().equals(propertyName)) {
                    field = f;
                    break;
                }
            }
            //if no field found - search for annotated getter
            if (field == null) {
                String firstLetter = "" + propertyName.charAt(0);
                firstLetter.toUpperCase();
                String propertyNameMod = firstLetter + propertyName.substring(1);
                String getterName = "get" + propertyNameMod;
                Method getter = entityClass.getMethod(getterName);

                return createPropertyDetails(getter, propertyName,
                        ClassHelper.getClassName(getter.getReturnType()), orderPosition);
                //otherwise use the field
            } else {
                return createPropertyDetails(field, field.getName(), ClassHelper.getClassName(field.getType()), orderPosition);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PropertyDetails createPropertyDetails(AccessibleObject accessibleObject, String propertyName, String propertyClassName, Integer orderPosition) {
        Property property = accessibleObject.getAnnotation(Property.class);
        if (property == null) {
            return null;
        }

        PropertyDetails pd = new PropertyDetails();
        if (orderPosition != null) {
            pd.setOrderPosition(orderPosition);
        }
        pd.setPropertyName(propertyName);
        pd.setPropertyClassName(propertyClassName);//
        pd.setPropertyTitle(property.title());
        pd.setReadOnly(property.readOnly());
        pd.setEditable(property.editable());
        pd.setVisible(property.visible());
        pd.setHiden(property.hidden());
        pd.setPercent(property.percent());
        pd.setShowOnly(property.showOnly());
        pd.setUpdateStrategy(property.updateStrategy());
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
        if (!DEFAULT_FORMATTER_CLASS_NAME.equals(formatterClass.getName())) {
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

        Annotation column = accessibleObject.getAnnotation(Column.class);
        if (column == null) {
            column = accessibleObject.getAnnotation(JoinColumn.class);
        }
        if (column == null) {
            column = accessibleObject.getAnnotation(PrimaryKeyJoinColumn.class);
        }
        if (column == null) {
            column = accessibleObject.getAnnotation(DiscriminatorColumn.class);
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

        Id id = accessibleObject.getAnnotation(Id.class);
        IdClass idClass = accessibleObject.getAnnotation(IdClass.class);
        EmbeddedId embeddedId = accessibleObject.getAnnotation(EmbeddedId.class);
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
            log.info("Setting date custom display for property " + pd.getPropertyName());

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
