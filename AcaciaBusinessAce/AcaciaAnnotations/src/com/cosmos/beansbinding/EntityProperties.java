/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.beansbinding;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jdesktop.beansbinding.AutoBinding;

/**
 *
 * @author miro
 */
public class EntityProperties implements Cloneable, Serializable {

    private Class entityClass;
    private String tableName;
    private EntityProperties superEntityProperties;
    private Map<String, PropertyDetails> beanProperties = new TreeMap<String, PropertyDetails>();
    private AutoBinding.UpdateStrategy updateStrategy;
    private Class entityFormClass;
    private Class entityListFormClass;

    public EntityProperties(Class entityClass) {
        this.entityClass = entityClass;
    }

    public EntityProperties(Collection<PropertyDetails> beanProps) {
        setBeanProperties(beanProps);
    }

    /*
    private String columnName;*/
    public Class getEntityClass() {
        return entityClass;
    }

    public EntityProperties getSuperEntityProperties() {
        return superEntityProperties;
    }

    public Class getEntityFormClass() {
        return entityFormClass;
    }

    void setEntityFormClass(Class entityFormClass) {
        this.entityFormClass = entityFormClass;
    }

    public Class getEntityListFormClass() {
        return entityListFormClass;
    }

    void setEntityListFormClass(Class entityListFormClass) {
        this.entityListFormClass = entityListFormClass;
    }

    void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public AutoBinding.UpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    public void setUpdateStrategy(AutoBinding.UpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public Collection<PropertyDetails> getValues() {
        return getPropertyDetailsMap().values();
    }

    protected Map<Integer, PropertyDetails> getPropertyDetailsMap() {
        if (beanProperties != null) {
            Collection<PropertyDetails> values = beanProperties.values();
            TreeMap<Integer, PropertyDetails> map = new TreeMap<Integer, PropertyDetails>();
            for (PropertyDetails property : values) {
                map.put(property.getOrderPosition(), property);
            }
            return map;
        }

        return Collections.emptyMap();
    }

    public Set<String> getKeys() {
        if (beanProperties != null) {
            return beanProperties.keySet();
        }

        return Collections.EMPTY_SET;
    }

    public void addEntityProperties(EntityProperties entityProperties) {
        addBeanProperties(entityProperties.getValues());
    }

    public void addBeanProperties(Collection<PropertyDetails> beanProps) {
        int orderPosition;
        Map<Integer, PropertyDetails> map = getPropertyDetailsMap();
        if((map = getPropertyDetailsMap()).size() == 0) {
            orderPosition = 0;
        } else {
            orderPosition = ((TreeMap<Integer, PropertyDetails>)map).lastKey();
        }

        for (PropertyDetails propDetails : beanProps) {
            propDetails = (PropertyDetails)propDetails.clone();
            propDetails.setOrderPosition(++orderPosition);
            String propName = propDetails.getPropertyName();
            if (!beanProperties.containsKey(propName)) {
                beanProperties.put(propName, propDetails);
            } else {
                PropertyDetails superPD = beanProperties.get(propName);
                superPD.merge(propDetails);
            }
        }
    }

    public void setBeanProperties(Collection<PropertyDetails> beanProps) {
        beanProperties.clear();
        if (beanProps != null && beanProps.size() > 0) {
            for (PropertyDetails bp : beanProps) {
                beanProperties.put(bp.getPropertyName(), bp);
            }
        }
    }

    public void setBeanProperties(Map<String, PropertyDetails> beanProperties) {
        this.beanProperties = beanProperties;
    }

    public void addPropertyDetails(PropertyDetails propertyDetails) {
        beanProperties.put(propertyDetails.getPropertyName(), propertyDetails);
    }

    public PropertyDetails removePropertyDetails(PropertyDetails propertyDetails) {
        return removePropertyDetails(propertyDetails.getPropertyName());
    }

    public PropertyDetails removePropertyDetails(String propertyName) {
        if (beanProperties != null) {
            return beanProperties.remove(propertyName);
        }

        return null;
    }

    public PropertyDetails getPropertyDetails(String propertyName) {
        if (beanProperties != null) {
            return beanProperties.get(propertyName);
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entity class: ").append(entityClass);
        sb.append(", tableName: ").append(tableName);
        sb.append(", keys: ").append(beanProperties.keySet());
        sb.append(", values: ").append(beanProperties.values());
        Collection<PropertyDetails> properties = getValues();
        sb.append(", properties: ").append(properties);
        sb.append(", properties.hashCode: ").append(properties.hashCode());
        sb.append(", super: ").append(super.toString());

        //private Map<String, PropertyDetails> beanProperties;
        //private AutoBinding.UpdateStrategy updateStrategy;

        return sb.toString();
    }

    @Override
    public Object clone() {
        try {
            EntityProperties entityProps = (EntityProperties) super.clone();
            entityProps.beanProperties = new TreeMap<String, PropertyDetails>();
            for(String key : beanProperties.keySet()) {
                entityProps.beanProperties.put(key, (PropertyDetails) beanProperties.get(key).clone());
            }

            return entityProps;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}

