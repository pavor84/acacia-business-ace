/**
 * 
 */
package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.data.product.SimpleProduct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

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
 * Created	:	28.12.2008
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "pricelist_items")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "PricelistItem.findForParentAndDeleted",
                query = "select p from PricelistItem p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = :deleted " +
                        "order by p.product.category.categoryName asc, p.product.productName asc, p.minQuantity asc"
            ),
        @NamedQuery
            (
                name = "PricelistItem.findById",
                query = "select p from PricelistItem p where p.dataObject.dataObjectId = :pricelistItemId"
            ),
        /**
         * Parameters:
         * - product - not null
         * - pricelistParentId - not null the parent of the price-lists to include (usually the organization)
         */
        @NamedQuery
            (
                name = "PricelistItem.getPricelistItemsForProduct",
                query = "select i from PricelistItem i where i.product = :product and " +
                		"i.dataObject.parentDataObjectId in " +
                		"(select p.id from Pricelist p where p.dataObject.parentDataObjectId = :pricelistParentId and p.active = true)"
            ) 
    })
public class PricelistItem extends DataObjectBean implements Serializable{
    @Property(title="Product", propertyValidator=@PropertyValidator(required=true), customDisplay="${product.productName}")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable=false)
    @ManyToOne
    private SimpleProduct product;
    
    @Column(name="apply_client_discount")
    @Property(title="Apply Client Discount")
    private boolean applyClientDiscount;
    
    @Property(title="Min. Quantity", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "min_quantity", precision=20, scale=4)
    private BigDecimal minQuantity;
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
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
        if (!(object instanceof PricelistItem)) {
            return false;
        }
        PricelistItem other = (PricelistItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.PricelistItem[itemId=" + itemId + "]";
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

    public BigInteger getPricelistItemId() {
        return itemId;
    }

    public void setPricelistItemId(BigInteger itemId) {
        this.itemId = itemId;
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

    public boolean isApplyClientDiscount() {
        return applyClientDiscount;
    }

    public void setApplyClientDiscount(boolean applyClientDiscount) {
        this.applyClientDiscount = applyClientDiscount;
    }

    public BigDecimal getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(BigDecimal minQuantity) {
        this.minQuantity = minQuantity;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }
    
    public BigDecimal getSourcePrice(){
        if ( product==null )
            return null;
        return product.getSalesPrice();
    }
    
    public BigDecimal getSalesPrice(){
        BigDecimal sourcePrice = getSourcePrice();
        if ( sourcePrice==null )
            return null;
        if ( getDiscountPercent()==null )
            return sourcePrice;
        BigDecimal percentDec = discountPercent.divide(new BigDecimal("100"), MathContext.DECIMAL64);
        return sourcePrice.subtract(sourcePrice.multiply(percentDec));
    }
}
