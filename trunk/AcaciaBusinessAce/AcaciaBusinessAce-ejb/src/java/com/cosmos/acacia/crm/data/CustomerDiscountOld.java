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
import javax.persistence.Basic;

@Entity
@Table(name = "customer_discounts_old")
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
public class CustomerDiscountOld extends DataObjectBean implements Serializable {

    @JoinColumn(name = "customer_id", referencedColumnName = "partner_id", nullable=false)
    @ManyToOne
    @Property(title="Customer",
              propertyValidator=@PropertyValidator(required=true), customDisplay="${customer.displayName}")
    private BusinessPartner customer;
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "discount_percent", precision=7, scale=6)
    private BigDecimal discountPercent;
    
    @Id
    @Basic(optional = false)
    @Column(name = "customer_discount_id", nullable = false)
    private BigInteger customerDiscountId;

    @Column(name = "organization_id")
    private BigInteger organizationId;

    @JoinColumn(name = "customer_discount_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    
    @Override
    public BigInteger getParentId() {
        return getOrganizationId();
    }

    @Override
    public void setParentId(BigInteger parentId) {
        setOrganizationId(parentId);
    }

    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public int hashCode() {
        return (customerDiscountId != null ? customerDiscountId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDiscountOld)) {
            return false;
        }
        CustomerDiscountOld other = (CustomerDiscountOld) object;
        if ((customerDiscountId == null && other.customerDiscountId != null) ||
                (customerDiscountId != null && !customerDiscountId.equals(other.customerDiscountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CustomerDiscount[discountId=" + customerDiscountId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return customerDiscountId;
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
        setCustomerDiscountId(id);
    }

    public BigInteger getCustomerDiscountId() {
        return customerDiscountId;
    }

    public void setCustomerDiscountId(BigInteger customerDiscountId) {
        this.customerDiscountId = customerDiscountId;
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
