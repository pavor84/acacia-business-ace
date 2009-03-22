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
@Table(name = "product_percent_values")
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
                name = "ProductPercentValue.findForParentAndDeleted",
                query = "select p from ProductPercentValue p" +
                    " where" +
                    "  p.dataObject.parentDataObjectId = :parentDataObjectId" +
                    "  and p.dataObject.deleted = :deleted" +
                    "  and p.type = :type"
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
                name = "ProductPercentValue.findByNameTypeNotDeleted",
                query = "select p from ProductPercentValue p" +
                    " where" +
                    "  p.dataObject.parentDataObjectId = :parentDataObjectId " +
                    "  and p.dataObject.deleted = :deleted" +
                    "  and p.name like :name" +
                    "  and p.type = :type"
            )  
    })
public class ProductPercentValue extends DataObjectBean implements Serializable{
    
    public enum Type {
        DISCOUNT,
        CUSTOMS_DUTY,
        TRANSPORT,
        PROFIT,
        EXCISE_DUTY,
    }
    
    @Id
    @Column(name = "percent_value_id", nullable = false)
    private BigInteger valueId;
    
    @Column(name = "value_name", nullable = false)
    @Property(title="Value Name",
        propertyValidator=@PropertyValidator(required=true,
            validationType=ValidationType.LENGTH, minLength=2, maxLength=128))
    private String name;

    @Property(title="Percent Value", percent=true,
        propertyValidator=@PropertyValidator(required=true))
    @Column(name = "percent_value", precision=8, scale=6)
    private BigDecimal percentValue;
    
    @Column(name = "value_type_id")
    private Type type;
    
    @Column(name = "organization_id")
    private BigInteger organizationId;

    @JoinColumn(name = "percent_value_id", referencedColumnName = "data_object_id",
        insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public BigInteger getParentId() {
        return getOrganizationId();
    }

    @Override
    public void setParentId(BigInteger parentId) {
        setOrganizationId(parentId);
    }

    @Override
    public int hashCode() {
        return valueId != null ? valueId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductPercentValue)) {
            return false;
        }
        ProductPercentValue other = (ProductPercentValue) object;
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

    public BigDecimal getPercentValue() {
        return percentValue;
    }

    public void setPercentValue(BigDecimal value) {
        this.percentValue = value;
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
