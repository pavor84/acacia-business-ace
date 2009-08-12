/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.product;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.util.CloneableBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "product_suppliers", catalog = "acacia", schema = "public")
@NamedQueries({
    @NamedQuery(
        name = "ProductSupplier.findByProduct",
        query = "SELECT p FROM ProductSupplier p" +
                " where p.product = :product")
})
public class ProductSupplier implements Serializable, CloneableBean<ProductSupplier> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ProductSupplierPK productSupplierPK;

    @JoinColumn(name = "supplier_id", referencedColumnName = "partner_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @Property(title="Supplier", propertyValidator=@PropertyValidator(required=true), customDisplay="${supplier.displayName}")
    private BusinessPartner supplier;

    @Basic(optional = false)
    @Column(name = "product_code", nullable = false, length = 50)
    @Property(title="Product Code",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=50, required=true))
    private String productCode;

    @Basic(optional = false)
    @Column(name = "product_name", nullable = false, length = 100)
    @Property(title="Product Name",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=100))
    private String productName;

    @Basic(optional = false)
    @Column(name = "price_per_quantity", nullable = false)
    @Property(title="Price Per Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=1d))
    private int pricePerQuantity = 1;

    @Basic(optional = false)
    @Column(name = "order_price", nullable = false, precision = 19, scale = 4)
    @Property(title="Order Price")
    private BigDecimal orderPrice;

    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    private DbResource currency;

    @Column(name = "delivery_time")
    @Property(title="Delivery Time")
    private Integer deliveryTime;

    @JoinColumn(name = "measure_unit_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @Basic(optional = false)
    @Column(name = "min_order_quantity", nullable = false, precision = 19, scale = 4)
    @Property(title="Min. Order Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=1d))
    private BigDecimal minOrderQuantity = BigDecimal.ONE;

    @Column(name = "max_order_quantity", precision = 19, scale = 4)
    @Property(title="Max. Order Qty")
    private BigDecimal maxOrderQuantity;

    @Basic(optional = false)
    @Column(name = "is_deliverable", nullable = false)
    @Property(title="Deliverable")
    private boolean deliverable;

    @Basic(optional = false)
    @Column(name = "is_obsolete", nullable = false)
    @Property(title="Obsolete")
    private boolean obsolete;

    @Column(name = "description", length = 2147483647)
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @Property(title="Product", customDisplay="${product.productCode} (${product.productName})",
            reportColumnWidth=45,
            propertyValidator=@PropertyValidator(required=true))
    private SimpleProduct product;


    public ProductSupplier() {
    }

    public ProductSupplier(ProductSupplierPK productSupplierPK) {
        this.productSupplierPK = productSupplierPK;
    }

    public ProductSupplier(BigInteger productId, BigInteger supplierId) {
        this.productSupplierPK = new ProductSupplierPK(productId, supplierId);
    }

    public ProductSupplierPK getProductSupplierPK() {
        return productSupplierPK;
    }

    public void setProductSupplierPK(ProductSupplierPK productSupplierPK) {
        this.productSupplierPK = productSupplierPK;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public boolean isDeliverable() {
        return deliverable;
    }

    public void setDeliverable(boolean deliverable) {
        this.deliverable = deliverable;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    public BigDecimal getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(BigDecimal minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public BigDecimal getMaxOrderQuantity() {
        return maxOrderQuantity;
    }

    public void setMaxOrderQuantity(BigDecimal maxOrderQuantity) {
        this.maxOrderQuantity = maxOrderQuantity;
    }

    public int getPricePerQuantity() {
        return pricePerQuantity;
    }

    public void setPricePerQuantity(int pricePerQuantity) {
        this.pricePerQuantity = pricePerQuantity;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusinessPartner getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessPartner supplier) {
        this.supplier = supplier;
        if(productSupplierPK != null) {
            if(supplier != null)
                productSupplierPK.setSupplierId(supplier.getId());
            else
                productSupplierPK.setSupplierId(null);
        }
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public SimpleProduct getProduct() {
        return product;
    }

    public void setProduct(SimpleProduct product) {
        this.product = product;
        if(productSupplierPK != null) {
            if(product != null) {
                productSupplierPK.setProductId(product.getId());
            } else
                productSupplierPK.setProductId(null);
        }
    }

    public DbResource getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(DbResource measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productSupplierPK != null ? productSupplierPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductSupplier)) {
            return false;
        }
        ProductSupplier other = (ProductSupplier) object;
        if ((this.productSupplierPK == null && other.productSupplierPK != null) || (this.productSupplierPK != null && !this.productSupplierPK.equals(other.productSupplierPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductSupplier[" + productSupplierPK + "]";
    }

    @Override
    public ProductSupplier clone() {
        try {
            return (ProductSupplier) super.clone();
        } catch(CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
