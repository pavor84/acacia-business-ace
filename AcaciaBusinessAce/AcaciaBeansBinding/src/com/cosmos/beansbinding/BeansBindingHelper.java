/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
import com.cosmos.acacia.annotation.PropertyValidator;
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
import javax.persistence.Transient;


/**
 *
 * @author miro
 */
public class BeansBindingHelper {

    protected static Logger log = Logger.getLogger(BeansBindingHelper.class);

    public static EntityProperties createEntityProperties(Class entityClass) {
        EntityProperties entityProperties = new EntityProperties(entityClass);

        boolean isEntity = false;
        for(Annotation annotation : entityClass.getDeclaredAnnotations())
        {
            if(annotation.annotationType().equals(Entity.class))
                isEntity = true;
            else if(annotation.annotationType().equals(Table.class))
            {
                Table table = (Table)annotation;
                entityProperties.setTableName(table.name());
            }
        }
        
// What if not an entity ?
//        if(!isEntity)
//            throw new IllegalArgumentException("The class " + entityClass.getName() + " is not entity because the annotation 'javax.persistence.Entity' is not present into the declared class annotations.");

        if(entityProperties.getTableName() == null)
             entityProperties.setTableName(entityClass.getSimpleName());

        int orderPosition = 0;
        Field[] fields = entityClass.getDeclaredFields();
        List<PropertyDetails> properties = new ArrayList<PropertyDetails>(fields.length);
        for(Field field : fields)
        {
            Property property = field.getAnnotation(Property.class);
            if(property != null)
            {
                PropertyDetails pd = new PropertyDetails();
                pd.setOrderPosition(orderPosition+=10);//increase by 10 - let have it possible to put columns in between
                pd.setPropertyName(field.getName());
                pd.setPropertyClassName(ClassHelper.getClassName(field.getType()));
                pd.setPropertyTitle(property.title());
                pd.setReadOnly(property.readOnly());
                pd.setEditable(property.editable());
                pd.setVisible(property.visible());
                pd.setHiden(property.hidden());
                pd.setResourceDisplayInTable(property.resourceDisplayInTable());

                if ( !Property.NULL.equals(property.customDisplay()) )
                    pd.setCustomDisplay(property.customDisplay());

                /* Setting validation-related values */
                PropertyValidator propertyValidator = property.propertyValidator();

                if(propertyValidator.validationType() != ValidationType.NONE || propertyValidator.required())
                {
                    BaseValidator validator = new BaseValidator();

                    validator.setRequired(propertyValidator.required());

                    if (propertyValidator.validator() != com.cosmos.beansbinding.validation.NoneValidator.class) {
                        try {
                            validator.addValidator((Validator) propertyValidator.validator().newInstance());
                        } catch (Exception ex){
                            // Log it!
                            ex.printStackTrace();
                        }
                    }

                    if(propertyValidator.validationType() == ValidationType.DATE)
                    {
                        String strValue = propertyValidator.datePattern();
                        if(strValue != null && (strValue = strValue.trim()).length() > 0){
                            DateValidator dateValidator = new DateValidator();

                            dateValidator.setDatePattern(strValue);
                            validator.addValidator(dateValidator);
                        }
                    }

                    if (propertyValidator.validationType() == ValidationType.DATE_RANGE)
                    {
                        DateRangeValidator dateRangeValidator = new DateRangeValidator();
                        String strValue = propertyValidator.fromDate();
                        if(strValue != null && (strValue = strValue.trim()).length() > 0)
                        {
                            Date date = now(strValue);
                            if(date != null)
                                dateRangeValidator.setFromDate(date);
                            else
                                dateRangeValidator.setFromDate(strValue);
                        }

                        strValue = propertyValidator.toDate();
                        if(strValue != null && (strValue = strValue.trim()).length() > 0)
                        {
                            Date date = now(strValue);
                            if(date != null)
                                dateRangeValidator.setToDate(date);
                            else
                                dateRangeValidator.setToDate(strValue);
                        }

                        validator.addValidator(dateRangeValidator);
                    }

                    if(propertyValidator.validationType() == ValidationType.NUMBER)
                    {
                        NumericValidator numericValidator = new NumericValidator();
                        validator.addValidator(numericValidator);
                        numericValidator.setFloating(propertyValidator.floating());
                    }

                    if(propertyValidator.validationType() == ValidationType.NUMBER_RANGE)
                    {
                        NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
                        numericRangeValidator.setMaxValue(propertyValidator.maxValue());
                        numericRangeValidator.setMinValue(propertyValidator.minValue());
                        validator.addValidator(numericRangeValidator);
                        numericRangeValidator.setFloating(propertyValidator.floating());
                    }

                    if(propertyValidator.validationType() == ValidationType.LENGTH)
                    {
                        TextLengthValidator rangeValidator = new TextLengthValidator();
                        rangeValidator.setMaxLength(propertyValidator.maxLength());
                        rangeValidator.setMinLength(propertyValidator.minLength());
                        validator.addValidator(rangeValidator);
                    }

                    if(propertyValidator.validationType() == ValidationType.REGEX
                            || propertyValidator.regex() != null)
                    {
                        String strValue = propertyValidator.regex();
                        if(strValue != null && (strValue = strValue.trim()).length() > 0)
                        {
                            RegexValidator regexValidator = new RegexValidator();
                            regexValidator.setPattern(strValue);
                            validator.addValidator(regexValidator);
                        }
                    }

                    if(propertyValidator.validationType() == ValidationType.MASK_FORMATTER)
                    {
                        MaskFormatterValidator v = new MaskFormatterValidator();
                        v.setMaxLength(propertyValidator.maxLength());
                        v.setMinLength(propertyValidator.minLength());
                        validator.addValidator(v);
                    }

                    String strValue = propertyValidator.tooltip();
                    if(strValue != null && (strValue = strValue.trim()).length() > 0)
                        validator.setTooltip(strValue);

                    pd.setValidator(validator);
                }

                Object value = property.sourceUnreadableValue();
                if(!Property.NULL.equals(value))
                    pd.setSourceUnreadableValue(value);

                String columnName = null;
                boolean nullable = true;

                Annotation column = field.getAnnotation(Column.class);
                if (column == null) column = field.getAnnotation(JoinColumn.class);
                if (column == null) column = field.getAnnotation(PrimaryKeyJoinColumn.class);
                if (column == null) column = field.getAnnotation(DiscriminatorColumn.class);

                if(column != null)
                {
                    try {
                        columnName = (String) column.getClass().getDeclaredMethod("name").invoke(column);
                        nullable = (Boolean) column.getClass().getDeclaredMethod("nullable").invoke(column);
                    } catch (NoSuchMethodException ex){
                        nullable = false;
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

                if(columnName != null)
                    pd.setColumnName(columnName);
                else
                    pd.setColumnName(pd.getPropertyName());

                Id id = field.getAnnotation(Id.class);
                IdClass idClass = field.getAnnotation(IdClass.class);
                EmbeddedId embeddedId = field.getAnnotation(EmbeddedId.class);
                if(id != null || idClass != null || embeddedId != null)
                {
                    pd.setReadOnly(true);
                    pd.setEditable(false);
                    pd.setVisible(false);
                    pd.setHiden(true);
                    pd.setRequired(true);
                }

                if(!pd.isRequired() && column != null && !nullable)
                {
                    System.out.println("Auto-required: " + columnName);
                    pd.setRequired(true);
                }

                if(pd.isRequired()){
                    BaseValidator validator = (BaseValidator) pd.getValidator();
                    if (validator == null)
                        validator = new BaseValidator();

                    if (!validator.isRequired())
                    {
                        validator.setRequired(true);
                        pd.setValidator(validator);
                    }
                }

                // Setting custom display for Dates
                if (pd.getPropertyClass() == Date.class && pd.getCustomDisplay() == Property.NULL)
                {
                    log.info("Setting date custom display for property " + pd.getPropertyName());

                    String name = pd.getPropertyName();
                    pd.setCustomDisplay("${" + name + ".date}.${" + name + ".month}.${" + name + ".year}");
                }
                properties.add(pd);
            }
        }

        entityProperties.setBeanProperties(properties);

        return entityProperties;
    }

    private static Date now(String now)
    {
        if("now".equalsIgnoreCase(now) || "now()".equalsIgnoreCase(now))
            return new Date();

        return null;
    }
}
