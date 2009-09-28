/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.util.ClassHelper;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Miro
 */
public class EntityProperties implements Cloneable, Serializable {

    private final ReentrantLock lock = new ReentrantLock();
    //
    private Class entityClass;
    private TreeMap<String, EntityProperty> propertyNameMap = new TreeMap<String, EntityProperty>();
    private TreeMap<Integer, EntityProperty> propertyIndexMap = new TreeMap<Integer, EntityProperty>();
    //
    private String tableName;
    private EntityProperties superEntityProperties;
    private AutoBinding.UpdateStrategy updateStrategy;
    private Class entityFormClass;
    private Class entityListFormClass;

    public EntityProperties(Class entityClass) {
        this(entityClass, false);
    }

    public EntityProperties(Class entityClass, boolean checkExportable) {
        this.entityClass = entityClass;
        List<Class> classes = ClassHelper.getSuperclasses(entityClass, true);
        createEntityProperties(classes, checkExportable);
    }

    public void setOrderPosition(String propertyName, int orderPosition) {
        if(orderPosition <= 0) {
            throw new IllegalArgumentException("OrderPosition parameter must be greater than 0. orderPosition=" + orderPosition);
        }
        EntityProperty entityProperty;
        if((entityProperty = getEntityProperty(orderPosition)) != null && !propertyName.equals(entityProperty.getPropertyName())) {
            throw new IllegalStateException(getErrorMessage(propertyName, orderPosition));
        }

        if((entityProperty = getEntityProperty(propertyName)) == null) {
            throw new IllegalStateException("Missing entity property with name '" + propertyName + "'.");
        }

        int oldOrderPosition;
        if(orderPosition == (oldOrderPosition = entityProperty.getOrderPosition())) {
            return;
        }

        lock.lock();
        try {
            entityProperty.setOrderPosition(orderPosition);
            propertyIndexMap.remove(oldOrderPosition);
            propertyIndexMap.put(orderPosition, entityProperty);
        } finally {
            lock.unlock();
        }
    }

    public void addEntityProperties(EntityProperties entityProperties) {
        addEntityProperties(entityProperties.getValues());
    }

    public void addEntityProperties(Collection<EntityProperty> entityProperties) {
        for(EntityProperty entityProperty : entityProperties) {
            addEntityProperty(entityProperty);
        }
    }

    private String getErrorMessage(String propertyName, int orderPosition) {
        StringBuilder sb = new StringBuilder();
        sb.append("Property already exists with order position ").append(orderPosition);
        sb.append(". The new propertyName is '").append(propertyName);
        sb.append("'. The existing propertyName is '");
        sb.append(propertyIndexMap.get(orderPosition).getPropertyName()).append("'.");
        return sb.toString();
    }

    public void addEntityProperty(EntityProperty entityProperty) {
        lock.lock();
        try {
            String propertyName = entityProperty.getPropertyName();
            int orderPosition = entityProperty.getOrderPosition();
            if(propertyNameMap.containsKey(propertyName)) {
                remove(propertyName);
            }
            if(orderPosition > 0 && propertyIndexMap.containsKey(orderPosition)) {
                throw new IllegalStateException(getErrorMessage(propertyName, orderPosition));
            }
            if(orderPosition <= 0) {
                orderPosition = getLastIndex() + Property.STEP_VALUE;
                entityProperty.setOrderPosition(orderPosition);
            }

            propertyNameMap.put(propertyName, entityProperty);
            propertyIndexMap.put(orderPosition, entityProperty);
        } finally {
            lock.unlock();
        }
    }

    private int getLastIndex() {
        int lastIndex = Property.INITIAL_INDEX_VALUE;
        for(int index : getIndexes()) {
            if(index >= Property.INITIAL_INDEX_VALUE && index < Property.CUSTOM_INDEX_VALUE) {
                lastIndex = Math.max(lastIndex, index);
            }
        }

        return lastIndex;
    }

    public EntityProperty getEntityProperty(String propertyName) {
        return propertyNameMap.get(propertyName);
    }

    public EntityProperty getEntityProperty(int index) {
        return propertyIndexMap.get(index);
    }

    public EntityProperty removeEntityProperty(EntityProperty entityProperty) {
        return removeEntityProperty(entityProperty.getPropertyName());
    }

    public EntityProperty removeEntityProperty(String propertyName) {
        lock.lock();
        try {
            return remove(propertyName);
        } finally {
            lock.unlock();
        }
    }

    private EntityProperty remove(String propertyName) {
        EntityProperty entityProperty;
        if((entityProperty = propertyNameMap.remove(propertyName)) != null) {
            propertyIndexMap.remove(entityProperty.getOrderPosition());
        }

        return entityProperty;
    }

    public Collection<EntityProperty> getValues() {
        return new ArrayList(propertyIndexMap.values());
    }

    public Set<String> getKeys() {
        return propertyNameMap.keySet();
    }

    public Set<Integer> getIndexes() {
        return propertyIndexMap.keySet();
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public Class getEntityFormClass() {
        return entityFormClass;
    }

    public void setEntityFormClass(Class entityFormClass) {
        this.entityFormClass = entityFormClass;
    }

    public Class getEntityListFormClass() {
        return entityListFormClass;
    }

    public void setEntityListFormClass(Class entityListFormClass) {
        this.entityListFormClass = entityListFormClass;
    }

    public TreeMap<Integer, EntityProperty> getPropertyIndexMap() {
        return propertyIndexMap;
    }

    public void setPropertyIndexMap(TreeMap<Integer, EntityProperty> propertyIndexMap) {
        this.propertyIndexMap = propertyIndexMap;
    }

    public TreeMap<String, EntityProperty> getPropertyNameMap() {
        return propertyNameMap;
    }

    public void setPropertyNameMap(TreeMap<String, EntityProperty> propertyNameMap) {
        this.propertyNameMap = propertyNameMap;
    }

    public EntityProperties getSuperEntityProperties() {
        return superEntityProperties;
    }

    public void setSuperEntityProperties(EntityProperties superEntityProperties) {
        this.superEntityProperties = superEntityProperties;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public UpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    public void setUpdateStrategy(UpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    @Override
    public Object clone() {
        try {
            EntityProperties entityProps = (EntityProperties) super.clone();
            entityProps.propertyNameMap = new TreeMap<String, EntityProperty>();
            entityProps.propertyIndexMap = new TreeMap<Integer, EntityProperty>();
            for(String propertyName : propertyNameMap.keySet()) {
                EntityProperty entityProperty = (EntityProperty) propertyNameMap.get(propertyName).clone();
                int orderPosition = entityProperty.getOrderPosition();
                entityProps.propertyNameMap.put(propertyName, entityProperty);
                entityProps.propertyIndexMap.put(orderPosition, entityProperty);
            }

            return entityProps;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void createEntityProperties(List<Class> entityClasses, boolean checkExportable) {
        for(Class cls : entityClasses) {
            createEntityProperties(cls, checkExportable);
        }
    }

    protected void createEntityProperties(Class cls, boolean checkExportable) {
        for (Annotation annotation : cls.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(Entity.class)) {
            } else if (annotation.annotationType().equals(Table.class)) {
                if(entityClass == cls) {
                    Table table = (Table) annotation;
                    setTableName(table.name());
                }
            } else if (annotation.annotationType().equals(Form.class)) {
                Form form = (Form) annotation;
                String value;
                if((value = form.entityFormClassName()) != null && value.length() > 0) {
                    try {
                        setEntityFormClass(Class.forName(value));
                    } catch(Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if((value = form.entityListFormClassName()) != null && value.length() > 0) {
                    try {
                        setEntityListFormClass(Class.forName(value));
                    } catch(Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        if (getTableName() == null) {
            setTableName(entityClass.getSimpleName());
        }

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            EntityProperty entityProperty = EntityProperty.createEntityProperty(
                    field,
                    field.getName(),
                    ClassHelper.getClassName(field.getType()),
                    null);

            if (entityProperty != null) {
                // add the property if no exportability check is required,
                // or the check is required, and the field is exportable
                if (!checkExportable || entityProperty.isExportable()) {
                    addEntityProperty(entityProperty);
                }
            }
        }

        for (Field field : fields) {
            Property property = field.getAnnotation(Property.class);
            if (property == null) {
                continue;
            }

            String[] propertyNames;
            if((propertyNames = property.depends()).length > 0) {
                EntityProperty entityProperty = getEntityProperty(field.getName());
                entityProperty.setPropertyDetailsDependencies(getEntityPropertyList(propertyNames));
            }
        }
    }

    private List<EntityProperty> getEntityPropertyList(String[] propertyNames) {
        List<EntityProperty> list = new ArrayList<EntityProperty>(propertyNames.length);
        for (String propertyName : propertyNames) {
            EntityProperty entityProperty;
            if ((entityProperty = getEntityProperty(propertyName)) == null) {
                throw new RuntimeException("Missing property '" + propertyName + "' in " + toString());
            }
            list.add(entityProperty);
        }

        return list;
    }
}
