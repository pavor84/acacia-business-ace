/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.beansbinding.validation.DateRangeValidator;
import com.cosmos.beansbinding.validation.DateValidator;
import com.cosmos.beansbinding.validation.NoneValidator;
import com.cosmos.beansbinding.validation.NumericRangeValidator;
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
                Class validatorClass = propertyValidator.validator();
                if(!NoneValidator.class.equals(validatorClass))
                {
                    Validator validator;
                    try
                    {
                        validator = (Validator)validatorClass.newInstance();
                    }
                    catch(Exception ex)
                    {
                        throw new RuntimeException(ex);
                    }
                    if(validator instanceof DateValidator)
                    {
                        String strValue = propertyValidator.datePattern();
                        if(strValue != null && (strValue = strValue.trim()).length() > 0)
                            ((DateValidator)validator).setDatePattern(strValue);

                        if(validator instanceof DateRangeValidator)
                        {
                            DateRangeValidator rangeValidator = (DateRangeValidator)validator;
                            strValue = propertyValidator.fromDate();
                            if(strValue != null && (strValue = strValue.trim()).length() > 0)
                            {
                                Date date = now(strValue);
                                if(date != null)
                                    rangeValidator.setFromDate(date);
                                else
                                    rangeValidator.setFromDate(strValue);
                            }

                            strValue = propertyValidator.toDate();
                            if(strValue != null && (strValue = strValue.trim()).length() > 0)
                            {
                                Date date = now(strValue);
                                if(date != null)
                                    rangeValidator.setToDate(date);
                                else
                                    rangeValidator.setToDate(strValue);
                            }
                        }
                    }
                    else if(validator instanceof NumericRangeValidator)
                    {
                        NumericRangeValidator rangeValidator = (NumericRangeValidator)validator;
                        rangeValidator.setMaxValue(propertyValidator.maxValue());
                        rangeValidator.setMinValue(propertyValidator.minValue());
                    }
                    else if(validator instanceof TextLengthValidator)
                    {
                        TextLengthValidator rangeValidator = (TextLengthValidator)validator;
                        rangeValidator.setMaxLength(propertyValidator.maxLength());
                        rangeValidator.setMinLength(propertyValidator.minLength());
                    }
                    else if(validator instanceof RegexValidator)
                    {
                        String strValue = propertyValidator.regex();
                        if(strValue != null && (strValue = strValue.trim()).length() > 0)
                        {
                            ((RegexValidator)validator).setPattern(strValue);
                        }
                    }

                    String strValue = propertyValidator.tooltip();
                    if(strValue != null && (strValue = strValue.trim()).length() > 0)
                        ((RequiredValidator)validator).setTooltip(strValue);

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
