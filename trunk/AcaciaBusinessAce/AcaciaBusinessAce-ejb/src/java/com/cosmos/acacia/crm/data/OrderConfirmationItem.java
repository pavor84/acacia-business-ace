/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "order_confirmation_items")
public class OrderConfirmationItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "confirmation_item_id", nullable = false)
    private BigInteger confirmationItemId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private Product product;

    @JoinColumn(name = "measure_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource measureUnit;

    @Column(name = "confirmed_quantity", nullable = false)
    private BigDecimal confirmedQuantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "extended_price", nullable = false)
    private BigDecimal extendedPrice;

    @Column(name = "ship_date")
    @Temporal(TemporalType.DATE)
    private Date shipDate;


    public OrderConfirmationItem() {
    }

    public OrderConfirmationItem(BigInteger confirmationItemId) {
        this.confirmationItemId = confirmationItemId;
    }

    public BigInteger getConfirmationItemId() {
        return confirmationItemId;
    }

    public void setConfirmationItemId(BigInteger confirmationItemId) {
        this.confirmationItemId = confirmationItemId;
    }

    public BigDecimal getConfirmedQuantity() {
        return confirmedQuantity;
    }

    public void setConfirmedQuantity(BigDecimal confirmedQuantity) {
        this.confirmedQuantity = confirmedQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(BigDecimal extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        hash += (confirmationItemId != null ? confirmationItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderConfirmationItem)) {
            return false;
        }
        OrderConfirmationItem other = (OrderConfirmationItem) object;
        if ((this.confirmationItemId == null && other.confirmationItemId != null) || (this.confirmationItemId != null && !this.confirmationItemId.equals(other.confirmationItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.OrderConfirmationItem[confirmationItemId=" + confirmationItemId + "]";
    }

}
