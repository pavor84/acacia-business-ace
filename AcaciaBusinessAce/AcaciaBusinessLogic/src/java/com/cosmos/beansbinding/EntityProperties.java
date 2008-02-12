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
import org.jdesktop.beansbinding.AutoBinding;

/**
 *
 * @author miro
 */
public class EntityProperties
    implements Cloneable, Serializable
{

    private Class entityClass;
    private String tableName;
    private EntityProperties superEntityProperties;
    private Map<String, PropertyDetails> beanProperties;
    private AutoBinding.UpdateStrategy updateStrategy;

    public EntityProperties(Object entityBean) {
        this(entityBean.getClass());
    }

    public EntityProperties(Class entityClass) {
        this.entityClass = entityClass;
        BeansBindingHelper.initEntityProperties(this, entityClass);
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
        if(beanProperties != null)
            return beanProperties.values();

        return Collections.EMPTY_LIST;
    }

    public Set<String> getKeys()
    {
        if(beanProperties != null)
            return beanProperties.keySet();

        return Collections.EMPTY_SET;
    }

    public void setBeanProperties(Collection<PropertyDetails> beanProps) {
        int size;
        if(beanProps == null || (size = beanProps.size()) == 0)
            beanProperties = Collections.EMPTY_MAP;
        else
        {
            beanProperties = new HashMap<String, PropertyDetails>(size);
            
            for(PropertyDetails bp : beanProps)
            {
                beanProperties.put(bp.getPropertyName(), bp);
            }
        }
    }

    public void setBeanProperties(Map<String, PropertyDetails> beanProperties) {
        this.beanProperties = beanProperties;
    }

    public void addPropertyDetails(PropertyDetails propertyDetails)
    {
        if(beanProperties == null)
            beanProperties = new HashMap<String, PropertyDetails>();

        beanProperties.put(propertyDetails.getPropertyName(), propertyDetails);
    }

    public PropertyDetails getPropertyDetails(String propertyName)
    {
        if(beanProperties != null)
            return beanProperties.get(propertyName);

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

    /*
    private Map<String, PropertyDetails> beanProperties;
    private AutoBinding.UpdateStrategy updateStrategy;*/

        return sb.toString();
    }

    @Override
    public Object clone()
    {
        try
        {
            System.out.println("clone.source: " + this);
            EntityProperties entityProps = (EntityProperties)super.clone();
            Collection<PropertyDetails> props = getValues();
            List<PropertyDetails> newProps = new ArrayList<PropertyDetails>(props.size());
            Iterator<PropertyDetails> iter = props.iterator();
            while(iter.hasNext())
            {
                newProps.add((PropertyDetails)iter.next().clone());
            }
            entityProps.setBeanProperties(newProps);

            System.out.println("clone.copy: " + entityProps);

            return entityProps;
        }
        catch(CloneNotSupportedException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}

/*
cls: class com.cosmos.acacia.crm.data.Product

cls.getAnnotations():
[
@javax.persistence.Table(schema=, uniqueConstraints=[], catalog=, name=products),
@javax.persistence.NamedQueries(value=[@javax.persistence.NamedQuery(hints=[], name=Product.findByParentDataObjectAndDeleted, query=select p from Product p where p.dataObject.parentDataObject = :parentDataObject and p.dataObject.deleted = :deleted)]),
@javax.persistence.Entity(name=)
]

cls.getClasses(): []

cls.getDeclaredAnnotations():
[
@javax.persistence.Entity(name=),
@javax.persistence.Table(schema=, uniqueConstraints=[], catalog=, name=products),
@javax.persistence.NamedQueries(value=[@javax.persistence.NamedQuery(hints=[], name=Product.findByParentDataObjectAndDeleted, query=select p from Product p where p.dataObject.parentDataObject = :parentDataObject and p.dataObject.deleted = :deleted), @javax.persistence.NamedQuery(hints=[], name=Product.findByProductId, query=SELECT p FROM Product p WHERE p.productId = :productId), @javax.persistence.NamedQuery(hints=[], name=Product.findByParentId, query=SELECT p FROM Product p WHERE p.parentId = :parentId), @javax.persistence.NamedQuery(hints=[], name=Product.findByCategoryId, query=SELECT p FROM Product p WHERE p.categoryId = :categoryId), @javax.persistence.NamedQuery(hints=[], name=Product.findByProductName, query=SELECT p FROM Product p WHERE p.productName = :productName), @javax.persistence.NamedQuery(hints=[], name=Product.findByProductCode, query=SELECT p FROM Product p WHERE p.productCode = :productCode), @javax.persistence.NamedQuery(hints=[], name=Product.findByMeasureUnitId, query=SELECT p FROM Product p WHERE p.measureUnitId = :measureUnitId), @javax.persistence.NamedQuery(hints=[], name=Product.findByIsComplex, query=SELECT p FROM Product p WHERE p.isComplex = :isComplex), @javax.persistence.NamedQuery(hints=[], name=Product.findByIsPurchased, query=SELECT p FROM Product p WHERE p.isPurchased = :isPurchased), @javax.persistence.NamedQuery(hints=[], name=Product.findByIsSalable, query=SELECT p FROM Product p WHERE p.isSalable = :isSalable), @javax.persistence.NamedQuery(hints=[], name=Product.findByIsObsolete, query=SELECT p FROM Product p WHERE p.isObsolete = :isObsolete), @javax.persistence.NamedQuery(hints=[], name=Product.findByPatternFormatId, query=SELECT p FROM Product p WHERE p.patternFormatId = :patternFormatId), @javax.persistence.NamedQuery(hints=[], name=Product.findByProductColor, query=SELECT p FROM Product p WHERE p.productColor = :productColor), @javax.persistence.NamedQuery(hints=[], name=Product.findByMinimumQuantity, query=SELECT p FROM Product p WHERE p.minimumQuantity = :minimumQuantity), @javax.persistence.NamedQuery(hints=[], name=Product.findByMaximumQuantity, query=SELECT p FROM Product p WHERE p.maximumQuantity = :maximumQuantity), @javax.persistence.NamedQuery(hints=[], name=Product.findByDefaultQuantity, query=SELECT p FROM Product p WHERE p.defaultQuantity = :defaultQuantity), @javax.persistence.NamedQuery(hints=[], name=Product.findByPurchasePrice, query=SELECT p FROM Product p WHERE p.purchasePrice = :purchasePrice), @javax.persistence.NamedQuery(hints=[], name=Product.findBySalePrice, query=SELECT p FROM Product p WHERE p.salePrice = :salePrice), @javax.persistence.NamedQuery(hints=[], name=Product.findByListPrice, query=SELECT p FROM Product p WHERE p.listPrice = :listPrice), @javax.persistence.NamedQuery(hints=[], name=Product.findByQuantityPerPackage, query=SELECT p FROM Product p WHERE p.quantityPerPackage = :quantityPerPackage), @javax.persistence.NamedQuery(hints=[], name=Product.findByDimensionUnitId, query=SELECT p FROM Product p WHERE p.dimensionUnitId = :dimensionUnitId), @javax.persistence.NamedQuery(hints=[], name=Product.findByDimensionWidth, query=SELECT p FROM Product p WHERE p.dimensionWidth = :dimensionWidth), @javax.persistence.NamedQuery(hints=[], name=Product.findByDimensionHeight, query=SELECT p FROM Product p WHERE p.dimensionHeight = :dimensionHeight), @javax.persistence.NamedQuery(hints=[], name=Product.findByWeightUnitId, query=SELECT p FROM Product p WHERE p.weightUnitId = :weightUnitId), @javax.persistence.NamedQuery(hints=[], name=Product.findByWeight, query=SELECT p FROM Product p WHERE p.weight = :weight), @javax.persistence.NamedQuery(hints=[], name=Product.findByDeliveryTime, query=SELECT p FROM Product p WHERE p.deliveryTime = :deliveryTime), @javax.persistence.NamedQuery(hints=[], name=Product.findByDescription, query=SELECT p FROM Product p WHERE p.description = :description), @javax.persistence.NamedQuery(hints=[], name=Product.findByProducerId, query=SELECT p FROM Product p WHERE p.producerId = :producerId)])
]

cls.getDeclaredFields():
[
private static final long com.cosmos.acacia.crm.data.Product.serialVersionUID,
private java.math.BigInteger com.cosmos.acacia.crm.data.Product.productId,
private java.math.BigInteger com.cosmos.acacia.crm.data.Product.parentId,
private java.math.BigInteger com.cosmos.acacia.crm.data.Product.categoryId,
private java.lang.String com.cosmos.acacia.crm.data.Product.productName, private java.lang.String com.cosmos.acacia.crm.data.Product.productCode, private int com.cosmos.acacia.crm.data.Product.measureUnitId, private boolean com.cosmos.acacia.crm.data.Product.isComplex, private boolean com.cosmos.acacia.crm.data.Product.isPurchased, private boolean com.cosmos.acacia.crm.data.Product.isSalable, private boolean com.cosmos.acacia.crm.data.Product.isObsolete, private java.lang.Integer com.cosmos.acacia.crm.data.Product.patternFormatId, private java.lang.String com.cosmos.acacia.crm.data.Product.productColor, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.minimumQuantity, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.maximumQuantity, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.defaultQuantity, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.purchasePrice, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.salePrice, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.listPrice, private int com.cosmos.acacia.crm.data.Product.quantityPerPackage, private java.lang.Integer com.cosmos.acacia.crm.data.Product.dimensionUnitId, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.dimensionWidth, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.dimensionHeight, private java.lang.Integer com.cosmos.acacia.crm.data.Product.weightUnitId, private java.math.BigDecimal com.cosmos.acacia.crm.data.Product.weight, private java.lang.Integer com.cosmos.acacia.crm.data.Product.deliveryTime, private java.lang.String com.cosmos.acacia.crm.data.Product.description, private java.math.BigInteger com.cosmos.acacia.crm.data.Product.producerId, private com.cosmos.acacia.crm.data.DataObject com.cosmos.acacia.crm.data.Product.dataObject
]

cls.getDeclaredMethods():
[
public int com.cosmos.acacia.crm.data.Product.hashCode(),
public boolean com.cosmos.acacia.crm.data.Product.equals(java.lang.Object),
public java.lang.String com.cosmos.acacia.crm.data.Product.toString(),
public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getId(), public java.lang.String com.cosmos.acacia.crm.data.Product.getDescription(), public void com.cosmos.acacia.crm.data.Product.setDescription(java.lang.String), public void com.cosmos.acacia.crm.data.Product.setId(java.math.BigInteger), public com.cosmos.acacia.crm.data.DataObject com.cosmos.acacia.crm.data.Product.getDataObject(), public void com.cosmos.acacia.crm.data.Product.setDataObject(com.cosmos.acacia.crm.data.DataObject), public static com.cosmos.acacia.crm.data.Product com.cosmos.acacia.crm.data.Product.newTestProduct(java.lang.String,java.lang.String), public java.lang.String com.cosmos.acacia.crm.data.Product.getProductName(), public void com.cosmos.acacia.crm.data.Product.setProductName(java.lang.String), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getWeight(), public void com.cosmos.acacia.crm.data.Product.setWeight(java.math.BigDecimal), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getProductId(), public void com.cosmos.acacia.crm.data.Product.setProductId(java.math.BigInteger), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getParentId(), public void com.cosmos.acacia.crm.data.Product.setParentId(java.math.BigInteger), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getCategoryId(), public void com.cosmos.acacia.crm.data.Product.setCategoryId(java.math.BigInteger), public java.lang.String com.cosmos.acacia.crm.data.Product.getProductCode(), public void com.cosmos.acacia.crm.data.Product.setProductCode(java.lang.String), public int com.cosmos.acacia.crm.data.Product.getMeasureUnitId(), public void com.cosmos.acacia.crm.data.Product.setMeasureUnitId(int), public boolean com.cosmos.acacia.crm.data.Product.getIsComplex(), public void com.cosmos.acacia.crm.data.Product.setIsComplex(boolean), public boolean com.cosmos.acacia.crm.data.Product.getIsPurchased(), public void com.cosmos.acacia.crm.data.Product.setIsPurchased(boolean), public boolean com.cosmos.acacia.crm.data.Product.getIsSalable(), public void com.cosmos.acacia.crm.data.Product.setIsSalable(boolean), public boolean com.cosmos.acacia.crm.data.Product.getIsObsolete(), public void com.cosmos.acacia.crm.data.Product.setIsObsolete(boolean), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getPatternFormatId(), public void com.cosmos.acacia.crm.data.Product.setPatternFormatId(java.lang.Integer), public java.lang.String com.cosmos.acacia.crm.data.Product.getProductColor(), public void com.cosmos.acacia.crm.data.Product.setProductColor(java.lang.String), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getMinimumQuantity(), public void com.cosmos.acacia.crm.data.Product.setMinimumQuantity(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getMaximumQuantity(), public void com.cosmos.acacia.crm.data.Product.setMaximumQuantity(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getDefaultQuantity(), public void com.cosmos.acacia.crm.data.Product.setDefaultQuantity(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getPurchasePrice(), public void com.cosmos.acacia.crm.data.Product.setPurchasePrice(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getSalePrice(), public void com.cosmos.acacia.crm.data.Product.setSalePrice(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getListPrice(), public void com.cosmos.acacia.crm.data.Product.setListPrice(java.math.BigDecimal), public int com.cosmos.acacia.crm.data.Product.getQuantityPerPackage(), public void com.cosmos.acacia.crm.data.Product.setQuantityPerPackage(int), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getDimensionUnitId(), public void com.cosmos.acacia.crm.data.Product.setDimensionUnitId(java.lang.Integer), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getDimensionWidth(), public void com.cosmos.acacia.crm.data.Product.setDimensionWidth(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getDimensionHeight(), public void com.cosmos.acacia.crm.data.Product.setDimensionHeight(java.math.BigDecimal), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getWeightUnitId(), public void com.cosmos.acacia.crm.data.Product.setWeightUnitId(java.lang.Integer), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getDeliveryTime(), public void com.cosmos.acacia.crm.data.Product.setDeliveryTime(java.lang.Integer), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getProducerId(),
public void com.cosmos.acacia.crm.data.Product.setProducerId(java.math.BigInteger)
]

cls.getFields(): []

cls.getGenericInterfaces(): [interface java.io.Serializable]

cls.getGenericSuperclass(): class com.cosmos.acacia.crm.data.DataObjectBean

cls.getInterfaces(): [interface java.io.Serializable]

cls.getMethods(): [public int com.cosmos.acacia.crm.data.Product.hashCode(), public boolean com.cosmos.acacia.crm.data.Product.equals(java.lang.Object), public java.lang.String com.cosmos.acacia.crm.data.Product.toString(), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getId(), public java.lang.String com.cosmos.acacia.crm.data.Product.getDescription(), public void com.cosmos.acacia.crm.data.Product.setDescription(java.lang.String), public void com.cosmos.acacia.crm.data.Product.setId(java.math.BigInteger), public com.cosmos.acacia.crm.data.DataObject com.cosmos.acacia.crm.data.Product.getDataObject(), public void com.cosmos.acacia.crm.data.Product.setDataObject(com.cosmos.acacia.crm.data.DataObject), public static com.cosmos.acacia.crm.data.Product com.cosmos.acacia.crm.data.Product.newTestProduct(java.lang.String,java.lang.String), public java.lang.String com.cosmos.acacia.crm.data.Product.getProductName(), public void com.cosmos.acacia.crm.data.Product.setProductName(java.lang.String), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getWeight(), public void com.cosmos.acacia.crm.data.Product.setWeight(java.math.BigDecimal), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getProductId(), public void com.cosmos.acacia.crm.data.Product.setProductId(java.math.BigInteger), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getParentId(), public void com.cosmos.acacia.crm.data.Product.setParentId(java.math.BigInteger), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getCategoryId(), public void com.cosmos.acacia.crm.data.Product.setCategoryId(java.math.BigInteger), public java.lang.String com.cosmos.acacia.crm.data.Product.getProductCode(), public void com.cosmos.acacia.crm.data.Product.setProductCode(java.lang.String), public int com.cosmos.acacia.crm.data.Product.getMeasureUnitId(), public void com.cosmos.acacia.crm.data.Product.setMeasureUnitId(int), public boolean com.cosmos.acacia.crm.data.Product.getIsComplex(), public void com.cosmos.acacia.crm.data.Product.setIsComplex(boolean), public boolean com.cosmos.acacia.crm.data.Product.getIsPurchased(), public void com.cosmos.acacia.crm.data.Product.setIsPurchased(boolean), public boolean com.cosmos.acacia.crm.data.Product.getIsSalable(), public void com.cosmos.acacia.crm.data.Product.setIsSalable(boolean), public boolean com.cosmos.acacia.crm.data.Product.getIsObsolete(), public void com.cosmos.acacia.crm.data.Product.setIsObsolete(boolean), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getPatternFormatId(), public void com.cosmos.acacia.crm.data.Product.setPatternFormatId(java.lang.Integer), public java.lang.String com.cosmos.acacia.crm.data.Product.getProductColor(), public void com.cosmos.acacia.crm.data.Product.setProductColor(java.lang.String), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getMinimumQuantity(), public void com.cosmos.acacia.crm.data.Product.setMinimumQuantity(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getMaximumQuantity(), public void com.cosmos.acacia.crm.data.Product.setMaximumQuantity(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getDefaultQuantity(), public void com.cosmos.acacia.crm.data.Product.setDefaultQuantity(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getPurchasePrice(), public void com.cosmos.acacia.crm.data.Product.setPurchasePrice(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getSalePrice(), public void com.cosmos.acacia.crm.data.Product.setSalePrice(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getListPrice(), public void com.cosmos.acacia.crm.data.Product.setListPrice(java.math.BigDecimal), public int com.cosmos.acacia.crm.data.Product.getQuantityPerPackage(), public void com.cosmos.acacia.crm.data.Product.setQuantityPerPackage(int), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getDimensionUnitId(), public void com.cosmos.acacia.crm.data.Product.setDimensionUnitId(java.lang.Integer), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getDimensionWidth(), public void com.cosmos.acacia.crm.data.Product.setDimensionWidth(java.math.BigDecimal), public java.math.BigDecimal com.cosmos.acacia.crm.data.Product.getDimensionHeight(), public void com.cosmos.acacia.crm.data.Product.setDimensionHeight(java.math.BigDecimal), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getWeightUnitId(), public void com.cosmos.acacia.crm.data.Product.setWeightUnitId(java.lang.Integer), public java.lang.Integer com.cosmos.acacia.crm.data.Product.getDeliveryTime(), public void com.cosmos.acacia.crm.data.Product.setDeliveryTime(java.lang.Integer), public java.math.BigInteger com.cosmos.acacia.crm.data.Product.getProducerId(), public void com.cosmos.acacia.crm.data.Product.setProducerId(java.math.BigInteger), public void com.cosmos.acacia.crm.data.DataObjectBean.addPropertyChangeListener(java.beans.PropertyChangeListener), public void com.cosmos.acacia.crm.data.DataObjectBean.removePropertyChangeListener(java.beans.PropertyChangeListener), public final void java.lang.Object.wait() throws java.lang.InterruptedException, public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException, public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException, public final native java.lang.Class java.lang.Object.getClass(), public final native void java.lang.Object.notify(), public final native void java.lang.Object.notifyAll()]

cls.getSuperclass(): class com.cosmos.acacia.crm.data.DataObjectBean

cls.getTypeParameters(): []

*/