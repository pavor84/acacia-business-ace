/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "warehouse_products")
@NamedQueries({@NamedQuery(name = "WarehouseProduct.findByWarehouseId", query = "SELECT w FROM WarehouseProduct w WHERE w.warehouseProductPK.warehouseId = :warehouseId"), @NamedQuery(name = "WarehouseProduct.findByProductId", query = "SELECT w FROM WarehouseProduct w WHERE w.warehouseProductPK.productId = :productId"), @NamedQuery(name = "WarehouseProduct.findByQuantityInStock", query = "SELECT w FROM WarehouseProduct w WHERE w.quantityInStock = :quantityInStock"), @NamedQuery(name = "WarehouseProduct.findByOrderedQuantity", query = "SELECT w FROM WarehouseProduct w WHERE w.orderedQuantity = :orderedQuantity"), @NamedQuery(name = "WarehouseProduct.findByReservedQuantity", query = "SELECT w FROM WarehouseProduct w WHERE w.reservedQuantity = :reservedQuantity"), @NamedQuery(name = "WarehouseProduct.findBySoldQuantity", query = "SELECT w FROM WarehouseProduct w WHERE w.soldQuantity = :soldQuantity"), @NamedQuery(name = "WarehouseProduct.findByQuantityDue", query = "SELECT w FROM WarehouseProduct w WHERE w.quantityDue = :quantityDue"), @NamedQuery(name = "WarehouseProduct.findByNotes", query = "SELECT w FROM WarehouseProduct w WHERE w.notes = :notes")})
public class WarehouseProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WarehouseProductPK warehouseProductPK;
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
    @Column(name = "notes")
    private String notes;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    @ManyToOne
    private Product product;
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", insertable = false, updatable = false)
    @ManyToOne
    private Warehouse warehouse;

    public WarehouseProduct() {
    }

    public WarehouseProduct(WarehouseProductPK warehouseProductPK) {
        this.warehouseProductPK = warehouseProductPK;
    }

    public WarehouseProduct(long warehouseId, long productId) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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
