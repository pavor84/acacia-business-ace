/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.beansbinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    private Map<String, PropertyDetails> beanProperties;
    private AutoBinding.UpdateStrategy updateStrategy;

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
        if (beanProperties != null) {
            Collection<PropertyDetails> values = beanProperties.values();
            TreeMap<Integer, PropertyDetails> map = new TreeMap<Integer, PropertyDetails>();
            for (PropertyDetails property : values) {
                map.put(property.getOrderPosition(), property);
            }
            return map.values();
        }

        return Collections.EMPTY_LIST;
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
        if (beanProperties == null) {
            beanProperties = new HashMap<String, PropertyDetails>();
        }

        for (PropertyDetails propDetails : beanProps) {
            String propName = propDetails.getPropertyName();
            if (!beanProperties.containsKey(propName)) {
                beanProperties.put(propName, propDetails);
            }
        }
    }

    public void setBeanProperties(Collection<PropertyDetails> beanProps) {
        int size;
        if (beanProps == null || (size = beanProps.size()) == 0) {
            beanProperties = Collections.EMPTY_MAP;
        } else {
            beanProperties = new HashMap<String, PropertyDetails>(size);

            for (PropertyDetails bp : beanProps) {
                beanProperties.put(bp.getPropertyName(), bp);
            }
        }
    }

    public void setBeanProperties(Map<String, PropertyDetails> beanProperties) {
        this.beanProperties = beanProperties;
    }

    public void addPropertyDetails(PropertyDetails propertyDetails) {
        if (beanProperties == null) {
            beanProperties = new HashMap<String, PropertyDetails>();
        }

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
        Collection<PropertyDetails> properties = getValues();
        sb.append(", properties: ").append(properties);
        sb.append(", properties.hashCode: ").append(properties.hashCode());
        sb.append(", 1st property: ").append(properties.iterator().next());
        sb.append(", super: ").append(super.toString());

        //private Map<String, PropertyDetails> beanProperties;
        //private AutoBinding.UpdateStrategy updateStrategy;

        return sb.toString();
    }

    @Override
    public Object clone() {
        try {
            EntityProperties entityProps = (EntityProperties) super.clone();
            Collection<PropertyDetails> props = getValues();
            List<PropertyDetails> newProps = new ArrayList<PropertyDetails>(props.size());
            Iterator<PropertyDetails> iter = props.iterator();
            while (iter.hasNext()) {
                newProps.add((PropertyDetails) iter.next().clone());
            }
            entityProps.setBeanProperties(newProps);

            return entityProps;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}

