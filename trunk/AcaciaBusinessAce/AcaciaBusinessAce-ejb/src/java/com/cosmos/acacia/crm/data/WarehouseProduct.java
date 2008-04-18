/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "warehouse_products")
public class WarehouseProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected WarehouseProductPK warehouseProductPK;

    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", insertable = false, updatable = false)
    @ManyToOne
    private Warehouse warehouse;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    @ManyToOne
    private SimpleProduct product;

    @Column(name = "quantity_in_stock")
    private BigDecimal quantityInStock;

    @Column(name = "ordered_quantity")
    private BigDecimal orderedQuantity;

    @Column(name = "reserved_quantity")
    private BigDecimal reservedQuantity;

    @Column(name = "sold_quantity")
    private BigDecimal soldQuantity;

    @Column(name = "quantity_due")
    private BigDecimal quantityDue;

    @Column(name = "minimum_quantity")
    private BigDecimal minimumQuantity;

    @Column(name = "maximum_quantity")
    private BigDecimal maximumQuantity;

    @Column(name = "default_quantity")
    private BigDecimal defaultQuantity;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "delivery_time")
    private Integer deliveryTime;

    @Column(name = "notes")
    private String notes;


    public WarehouseProduct() {
    }

    public WarehouseProduct(WarehouseProductPK warehouseProductPK) {
        this.warehouseProductPK = warehouseProductPK;
    }

    public WarehouseProduct(BigInteger warehouseId, BigInteger productId) {
        this.warehouseProductPK = new WarehouseProductPK(warehouseId, productId);
    }

    public WarehouseProductPK getWarehouseProductPK() {
        return warehouseProductPK;
    }

    public void setWarehouseProductPK(WarehouseProductPK warehouseProductPK) {
        this.warehouseProductPK = warehouseProductPK;
    }

    public BigDecimal getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(BigDecimal quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public BigDecimal getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(BigDecimal orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public BigDecimal getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(BigDecimal reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public BigDecimal getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(BigDecimal soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public BigDecimal getQuantityDue() {
        return quantityDue;
    }

    public void setQuantityDue(BigDecimal quantityDue) {
        this.quantityDue = quantityDue;
    }

    public BigDecimal getFreeQuantity()
    {
        BigDecimal freeQuantity;
        if(quantityInStock != null)
            freeQuantity = quantityInStock;
        else
            freeQuantity = BigDecimal.ZERO;

        if(reservedQuantity != null)
            freeQuantity = freeQuantity.subtract(reservedQuantity);

        if(soldQuantity != null)
            freeQuantity = freeQuantity.subtract(soldQuantity);

        return freeQuantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SimpleProduct getProduct() {
        return product;
    }

    public void setProduct(SimpleProduct product) {
        this.product = product;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getDefaultQuantity() {
        if(defaultQuantity != null)
            return defaultQuantity;

        if(product != null)
            return product.getDefaultQuantity();

        return BigDecimal.ZERO;
    }

    public void setDefaultQuantity(BigDecimal defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public Integer getDeliveryTime() {
        if(deliveryTime != null)
            return deliveryTime;

        if(product != null)
            return product.getDeliveryTime();

        return 0;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public BigDecimal getMaximumQuantity() {
        if(maximumQuantity != null)
            return maximumQuantity;

        if(product != null)
            return product.getMaximumQuantity();

        return BigDecimal.ZERO;
    }

    public void setMaximumQuantity(BigDecimal maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public BigDecimal getMinimumQuantity() {
        if(minimumQuantity != null)
            return minimumQuantity;

        if(product != null)
            return product.getMinimumQuantity();

        return BigDecimal.ZERO;
    }

    public void setMinimumQuantity(BigDecimal minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public BigDecimal getPurchasePrice() {
        if(purchasePrice != null)
            return purchasePrice;

        if(product != null)
            return product.getPurchasePrice();

        return BigDecimal.ZERO;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        if(salePrice != null)
            return salePrice;

        if(product != null)
            return product.getSalePrice();

        return BigDecimal.ZERO;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (warehouseProductPK != null ? warehouseProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WarehouseProduct)) {
            return false;
        }
        WarehouseProduct other = (WarehouseProduct) object;
        if ((this.warehouseProductPK == null && other.warehouseProductPK != null) || (this.warehouseProductPK != null && !this.warehouseProductPK.equals(other.warehouseProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.WarehouseProduct[warehouseProductPK=" + warehouseProductPK + "]";
    }

}
