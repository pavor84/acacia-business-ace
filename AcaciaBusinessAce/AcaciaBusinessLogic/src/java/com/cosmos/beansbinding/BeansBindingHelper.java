/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import com.cosmos.acacia.annotation.Property;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 *
 * @author miro
 */
class BeansBindingHelper {

    static void initEntityProperties(EntityProperties entityProperties, Class entityClass) {
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

        Field[] fields = entityClass.getDeclaredFields();
        List<PropertyDetails> properties = new ArrayList<PropertyDetails>(fields.length);
        for(Field field : fields)
        {
            Property property = field.getAnnotation(Property.class);
            if(property != null)
            {
                PropertyDetails pd = new PropertyDetails();
                pd.setPropertyName(field.getName());
                pd.setPropertyClass(field.getType());
                pd.setPropertyTitle(property.title());
                pd.setReadOnly(property.readOnly());
                pd.setEditable(property.editable());
                pd.setVisible(property.visible());
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
                    pd.setReadOnly(Boolean.TRUE);
                    pd.setEditable(Boolean.FALSE);
                    pd.setVisible(Boolean.FALSE);
                }

                properties.add(pd);
            }
        }

        entityProperties.setBeanProperties(properties);
    }

}
