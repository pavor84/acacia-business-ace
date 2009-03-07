/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.customer;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "customer_discounts"
//    , catalog = "acacia"
//    , schema = "public"
    , uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_id", "customer_id"})}
)
@NamedQueries({
    @NamedQuery(
        name="CustomerDiscount.findByCustomer",
        query="select t1" +
            " from CustomerDiscount t1" +
            " where" +
            "  customer = :customer" +
            "  and dataObject.deleted = false")
})
public class CustomerDiscount extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "customer_discount_id", nullable = false)
    private BigInteger customerDiscountId;

    @JoinColumn(name = "customer_id", referencedColumnName = "partner_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Customer",
              propertyValidator=@PropertyValidator(required=true), customDisplay="${customer.displayName}")
    private BusinessPartner customer;

    @Column(name = "discount_percent", precision = 7, scale = 6)
    @Property(title="Discount %")
    private BigDecimal discountPercent;

    @JoinColumn(name = "customer_discount_id", referencedColumnName = "data_object_id",
        nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    @Column(name = "organization_id", nullable=false, precision=18, scale=0)
    private BigInteger organizationId;

    public CustomerDiscount() {
    }

    public CustomerDiscount(BigInteger customerDiscountId) {
        this.customerDiscountId = customerDiscountId;
    }

    public BigInteger getCustomerDiscountId() {
        return customerDiscountId;
    }

    public void setCustomerDiscountId(BigInteger customerDiscountId) {
        this.customerDiscountId = customerDiscountId;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BusinessPartner getCustomer() {
        return customer;
    }

    public void setCustomer(BusinessPartner customer) {
        this.customer = customer;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
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
        if (!(object instanceof CustomerDiscount)) {
            return false;
        }
        CustomerDiscount other = (CustomerDiscount) object;
        if ((customerDiscountId == null && other.customerDiscountId != null) || (customerDiscountId != null && !customerDiscountId.equals(other.customerDiscountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CustomerDiscount[customerDiscountId=" + customerDiscountId + "]";
    }

    @Override
    public BigInteger getId() {
        return getCustomerDiscountId();
    }

    @Override
    public void setId(BigInteger id) {
        setCustomerDiscountId(id);
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
    public String getInfo() {
        return toString();
    }
}
