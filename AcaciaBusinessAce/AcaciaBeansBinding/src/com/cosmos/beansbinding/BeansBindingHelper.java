/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.beansbinding.validation.BaseValidator;
import com.cosmos.beansbinding.validation.DateRangeValidator;
import com.cosmos.beansbinding.validation.DateValidator;
import com.cosmos.beansbinding.validation.NumericRangeValidator;
import com.cosmos.beansbinding.validation.NumericValidator;
import com.cosmos.beansbinding.validation.RegexValidator;
import com.cosmos.beansbinding.validation.RequiredValidator;
import com.cosmos.beansbinding.validation.TextLengthValidator;
import com.cosmos.util.ClassHelper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.jdesktop.beansbinding.Validator;


/**
 *
 * @author miro
 */
public class BeansBindingHelper {

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
        if(!isEntity)
            throw new IllegalArgumentException("The class " + entityClass.getName() + " is not entity because the annotation 'javax.persistence.Entity' is not present into the declared class annotations.");

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
                pd.setOrderPosition(orderPosition++);
                pd.setPropertyName(field.getName());
                pd.setPropertyClassName(ClassHelper.getClassName(field.getType()));
                pd.setPropertyTitle(property.title());
                pd.setReadOnly(property.readOnly());
                pd.setEditable(property.editable());
                pd.setVisible(property.visible());
                pd.setHiden(property.hidden());

                /* Setting validation-related values */
                PropertyValidator propertyValidator = property.propertyValidator();
                
                if(propertyValidator.validationType() != ValidationType.NONE)
                {
                    BaseValidator validator = new BaseValidator();
                    
                    if (propertyValidator.required())
                        validator.addValidator(new RequiredValidator());
                    
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
                    else if(propertyValidator.validationType() == ValidationType.NUMBER)
                    {
                        NumericValidator numericValidator = new NumericValidator();
                        validator.addValidator(numericValidator);
                        
                    }
                    else if(propertyValidator.validationType() == ValidationType.NUMBER_RANGE)
                    {
                        NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
                        numericRangeValidator.setMaxValue(propertyValidator.maxValue());
                        numericRangeValidator.setMinValue(propertyValidator.minValue());
                        validator.addValidator(numericRangeValidator);
                    }
                    else if(propertyValidator.validationType() == ValidationType.LENGTH)
                    {
                        TextLengthValidator rangeValidator = new TextLengthValidator();
                        rangeValidator.setMaxLength(propertyValidator.maxLength());
                        rangeValidator.setMinLength(propertyValidator.minLength());
                        validator.addValidator(rangeValidator);
                    }
                    else if(propertyValidator.validationType() == ValidationType.REGEX)
                    {
                        String strValue = propertyValidator.regex();
                        if(strValue != null && (strValue = strValue.trim()).length() > 0)
                        {
                            RegexValidator regexValidator = new RegexValidator();
                            regexValidator.setPattern(strValue);
                            validator.addValidator(regexValidator);
                        }
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
                Column column = field.getAnnotation(Column.class);
                if(column != null)
                {
                    columnName = column.name();
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

                if(!pd.isRequired() && column != null && !column.nullable())
                {
                    pd.setRequired(true);
                }

                if(pd.getValidator() == null && pd.isRequired())
                    pd.setValidator(new RequiredValidator());

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
