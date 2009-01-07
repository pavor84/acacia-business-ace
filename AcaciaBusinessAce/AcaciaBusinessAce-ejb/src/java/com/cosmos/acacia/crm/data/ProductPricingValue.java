/**
 * 
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

/**
 * Created	:	04.01.2009
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "product_pricing_value")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         *  - type - type
         */
        @NamedQuery
            (
                name = "ProductPricingValue.findForParentAndDeleted",
                query = "select p from ProductPricingValue p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = :deleted and p.type = :type"
            ),
        /**
         * Parameters:
         *  - parentDataObjectId - not null
         *  - deleted - should be Boolean.FALSE
         *  - type - not null
         *  - name - not null
         *  
         */
        @NamedQuery
            (
                name = "ProductPricingValue.findByNameTypeNotDeleted",
                query = "select p from ProductPricingValue p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                "and p.dataObject.deleted = :deleted and p.name like :name and p.type = :type"
            )  
    })
public class ProductPricingValue extends DataObjectBean implements Serializable{
    
    public enum Type{
        DISCOUNT,
        DUTY,
        TRANSPORT,
        PROFIT
    }
    
    @Id
    @Column(name = "value_id", nullable = false)
    private BigInteger valueId;
    
    @Column(name = "name", nullable = false)
    @Property(title="Value Name", propertyValidator=@PropertyValidator(required=true, validationType=ValidationType.LENGTH, minLength=2, maxLength=50))
    private String name;

    @Property(title="Percent Value", propertyValidator=@PropertyValidator(required=true,
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000d))
    @Column(name = "value", precision=20, scale=4)
    private BigDecimal value;
    
    @Column(name = "type")
    private Type type;
    
    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "value_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    
    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valueId != null ? valueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductPricingValue)) {
            return false;
        }
        ProductPricingValue other = (ProductPricingValue) object;
        if ((this.valueId == null && other.valueId != null) || (this.valueId != null && !this.valueId.equals(other.valueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ProductPricingValue[valueId=" + valueId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return valueId;
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public void setId(BigInteger id) {
        setValueId(id);
    }

    public BigInteger getValueId() {
        return valueId;
    }

    public void setValueId(BigInteger valueId) {
        this.valueId = valueId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
