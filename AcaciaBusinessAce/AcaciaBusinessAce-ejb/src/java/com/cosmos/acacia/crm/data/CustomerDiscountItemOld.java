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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import java.math.MathContext;
import javax.persistence.Basic;
import javax.persistence.Transient;

/**
 * 
 */
@Entity
@Table(name = "customer_discount_items_old")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "CustomerDiscountItem.findForParent",
                query = "select p from CustomerDiscountItem p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = false "
            ),
        @NamedQuery
            (
                name = "CustomerDiscountItem.findById",
                query = "select p from CustomerDiscountItem p where p.dataObject.dataObjectId = :id"
            ),
        /**
         * Pars:
         * - category
         * - parentDataObjectId
         */
        @NamedQuery
            (
                name = "CustomerDiscountItem.findByCategory",
                query = "select p from CustomerDiscountItem p where p.dataObject.parentDataObjectId = :parentDataObjectId and p.category = :category and p.dataObject.deleted = false"
            ) ,
        /**
         * Pars:
         * - product
         * - parentDataObjectId
         */
        @NamedQuery
            (
                name = "CustomerDiscountItem.findByProduct",
                query = "select p from CustomerDiscountItem p where p.dataObject.parentDataObjectId = :parentDataObjectId and p.product = :product and p.dataObject.deleted = false"
            )
    })
public class CustomerDiscountItemOld extends DataObjectBean implements Serializable {

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;

    @Property(title="Product", propertyValidator=@PropertyValidator(required=true), customDisplay="${product.productName}")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private SimpleProduct product;
    
    @JoinColumn(name = "category_id", referencedColumnName = "product_category_id")
    @ManyToOne
    @Property(title="Category", propertyValidator=@PropertyValidator(required=true), customDisplay="${category.categoryName}")
    private ProductCategory category;
    
    @Column(name="include_heirs")
    @Property(title="Include Heirs")
    private boolean includeHeirs;
    
    @JoinColumn(name = "customer_discount_id", referencedColumnName = "customer_discount_id", nullable=false)
    @ManyToOne(optional=false)
    private CustomerDiscountOld customerDiscount;

    @Property(title="Discount %", propertyValidator=@PropertyValidator(required=true))
    @Column(name = "discount_percent", precision = 7, scale = 6)
    private BigDecimal discountPercent;

    @Transient
    @Property(title="Cutomer Price", editable=false, readOnly=true)
    private BigDecimal cutomerPrice;

    @Transient
    private BigDecimal discount;

    @Id
    @Basic(optional = false)
    @Column(name = "customer_discount_item_id", nullable = false, precision = 7, scale = 4, columnDefinition="numeric")
    private BigInteger customerDiscountItemId;

    @JoinColumn(name = "customer_discount_item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public CustomerDiscountItemOld() {
    }

    public CustomerDiscountItemOld(BigInteger customerDiscountItemId) {
        this.customerDiscountItemId = customerDiscountItemId;
    }

    public CustomerDiscountOld getCustomerDiscount() {
        return customerDiscount;
    }

    public void setCustomerDiscount(CustomerDiscountOld customerDiscount) {
        this.customerDiscount = customerDiscount;
        if(customerDiscount != null) {
            setParentId(customerDiscount.getId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public BigInteger getParentId() {
        System.out.println("getParentId().customerDiscount: " + customerDiscount + ", dataObject: " + dataObject);
        if(customerDiscount != null)
            customerDiscount.getId();

        if(dataObject != null)
            dataObject.getParentDataObjectId();

        return null;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        System.out.println("setParentId(" + parentId + ").customerDiscount: " + customerDiscount + ", dataObject: " + dataObject);
        if(dataObject == null) {
            dataObject = new DataObject();
        }

        dataObject.setParentDataObjectId(parentId);
    }

    @Override
    public int hashCode() {
        return customerDiscountItemId != null ? customerDiscountItemId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDiscountItemOld)) {
            return false;
        }
        CustomerDiscountItemOld other = (CustomerDiscountItemOld) object;
        if ((customerDiscountItemId == null && other.customerDiscountItemId != null) ||
                (customerDiscountItemId != null && !customerDiscountItemId.equals(other.customerDiscountItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CustomerDiscountItem[itemId=" + customerDiscountItemId + "]";
    }

    @Override
    public DataObject getDataObject() {
        System.out.println("getDataObject(): " + dataObject);
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return customerDiscountItemId;
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        System.out.println("setDataObject(" + dataObject + ")");
        this.dataObject = dataObject;
    }

    @Override
    public void setId(BigInteger id) {
        setCustomerDiscountItemId(id);
    }

    public BigInteger getCustomerDiscountItemId() {
        return customerDiscountItemId;
    }

    public void setCustomerDiscountItemId(BigInteger customerDiscountItemId) {
        this.customerDiscountItemId = customerDiscountItemId;
    }

    public SimpleProduct getProduct() {
        return product;
    }

    public void setProduct(SimpleProduct product) {
        this.product = product;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isIncludeHeirs() {
        return includeHeirs;
    }

    public void setIncludeHeirs(boolean includeHeirs) {
        this.includeHeirs = includeHeirs;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BigDecimal getDiscount() {
        BigDecimal value;
        if ((value = getDiscountPercent()) != null) {
            return value;
        }

        return getCustomerDiscount().getDiscountPercent();
    }

    public void setDiscount(BigDecimal discount) {
    }

    public BigDecimal getCutomerPrice() {
        BigDecimal salesPrice;
        if ((salesPrice = getProduct().getSalePrice()) == null) {
            return null;
        }

        BigDecimal percent;
        if ((percent = getDiscount()) != null) {
            return salesPrice.subtract(salesPrice.multiply(percent, MATH_CONTEXT), MATH_CONTEXT);
        }

        return salesPrice;
    }

    public void setCutomerPrice(BigDecimal cutomerPrice) {
    }
}
