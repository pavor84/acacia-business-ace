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
import com.cosmos.acacia.annotation.ValidationType;

/**
 * 
 */
@Entity
@Table(name = "customer_discount_items")
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
public class CustomerDiscountItem extends DataObjectBean implements Serializable{
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
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(required=true, validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "discount_percent", precision=20, scale=4)
    private BigDecimal discountPercent;
    
    @Id
    @Column(name = "item_id", nullable = false)
    private BigInteger itemId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
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
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDiscountItem)) {
            return false;
        }
        CustomerDiscountItem other = (CustomerDiscountItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.CustomerDiscountItem[itemId=" + itemId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return itemId;
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
        setItemId(id);
    }

    public BigInteger getItemId() {
        return itemId;
    }

    public void setItemId(BigInteger itemId) {
        this.itemId = itemId;
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
}
