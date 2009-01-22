package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

@Entity
@Table(name = "customer_discounts")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "CustomerDiscount.findForParent",
                query = "select p from CustomerDiscount p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = false"
            ),
        @NamedQuery
            (
                name = "CustomerDiscount.findById",
                query = "select p from CustomerDiscount p where p.dataObject.dataObjectId = :id"
            ),
        /**
         * Parameters:
         * - customer
         */
        @NamedQuery
            (
                name = "CustomerDiscount.getForCustomer",
                query = "select p from CustomerDiscount p where p.dataObject.deleted = false " +
                		"and p.customer = :customer"
            )
            
    })
/**
 * 
 */
public class CustomerDiscount extends DataObjectBean implements Serializable {
    @JoinColumn(name = "customer_id", referencedColumnName = "partner_id", nullable=false)
    @ManyToOne
    @Property(title="Customer",
              propertyValidator=@PropertyValidator(required=true), customDisplay="${customer.displayName}")
    private BusinessPartner customer;
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "discount_percent", precision=20, scale=4)
    private BigDecimal discountPercent;
    
    @Id
    @Column(name = "discount_id", nullable = false)
    private BigInteger discountId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "discount_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
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
        hash += (discountId != null ? discountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDiscount)) {
            return false;
        }
        CustomerDiscount other = (CustomerDiscount) object;
        if ((this.discountId == null && other.discountId != null) || (this.discountId != null && !this.discountId.equals(other.discountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.CustomerDiscount[discountId=" + discountId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return discountId;
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
        setDiscountId(id);
    }

    public BigInteger getDiscountId() {
        return discountId;
    }

    public void setDiscountId(BigInteger discountId) {
        this.discountId = discountId;
    }

    public BusinessPartner getCustomer() {
        return customer;
    }

    public void setCustomer(BusinessPartner customer) {
        this.customer = customer;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }
}
