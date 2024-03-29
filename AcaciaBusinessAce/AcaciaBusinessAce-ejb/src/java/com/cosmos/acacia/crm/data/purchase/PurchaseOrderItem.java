/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.purchase;

import com.cosmos.acacia.crm.data.product.SimpleProduct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "purchase_order_items")
@NamedQueries(
    {
        /**
         * Parameters:
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "PurchaseOrderItem.findForParentAndDeleted",
                query = "select i from PurchaseOrderItem i where i.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and i.dataObject.deleted = :deleted order by i.orderItemId "
            ),
        /**
         * Parameters:
         *  - parentDataObjectId - not null, the parent object id
         *  - product - not null
         */
        @NamedQuery
            (
                name = "PurchaseOrderItem.findForOrderAndProduct",
                query = "select i from PurchaseOrderItem i where i.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and i.product = :product"
            )
    })
public class PurchaseOrderItem extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "order_item_id", nullable = false)
    @Type(type="uuid")
    private UUID orderItemId;

    @Column(name = "parent_id")
    @Type(type="uuid")
    private UUID parentId;

    @Property(title="Product", propertyValidator=@PropertyValidator(required=true),
            customDisplay="${product.productName}", exportable=true, reportColumnWidth=35)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private SimpleProduct product;

    @Property(title="Measure Unit", propertyValidator=@PropertyValidator(required=true),
            exportable=true, reportColumnWidth=5)
    @JoinColumn(name = "measure_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource measureUnit;

    @Property(title="Ordered Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d, required=true),
        exportable=true, reportColumnWidth=8)
    @Column(name = "ordered_quantity", nullable = false)
    private BigDecimal orderedQuantity;

    @Property(title="Confirmed Qty", editable=false, exportable=true, reportColumnWidth=8)
    @Column(name = "confirmed_quantity")
    private BigDecimal confirmedQuantity;

    @Property(title="Delivered Qty", editable=false, exportable=true, reportColumnWidth=8)
    @Column(name = "delivered_quantity")
    private BigDecimal deliveredQuantity;

    @Property(title="Purchase Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d),
        exportable=true, reportColumnWidth=15)
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true), editable=false)
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;

    @Property(title="Ship Date From", readOnly=true)
    @Column(name = "ship_date_from")
    @Temporal(TemporalType.DATE)
    private Date shipDateFrom;

    @Property(title="Ship Date To", readOnly=true)
    @Column(name = "ship_date_to")
    @Temporal(TemporalType.DATE)
    private Date shipDateTo;

    @Transient
    @Property(title="Total", exportable=true, reportColumnWidth=16)
    private BigDecimal total;

    @JoinColumn(name = "order_item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public PurchaseOrderItem() {
    }

    public PurchaseOrderItem(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public UUID getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public BigDecimal getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(BigDecimal orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public BigDecimal getConfirmedQuantity() {
        return confirmedQuantity;
    }

    public void setConfirmedQuantity(BigDecimal confirmedQuantity) {
        this.confirmedQuantity = confirmedQuantity;
    }

    public BigDecimal getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(BigDecimal deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public SimpleProduct getProduct() {
        return product;
    }

    public void setProduct(SimpleProduct product) {
        this.product = product;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
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
        hash += (orderItemId != null ? orderItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseOrderItem)) {
            return false;
        }
        PurchaseOrderItem other = (PurchaseOrderItem) object;
        if ((this.orderItemId == null && other.orderItemId != null) || (this.orderItemId != null && !this.orderItemId.equals(other.orderItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.PurchaseOrderItem[orderItemId=" + orderItemId + "]";
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public Date getShipDateTo() {
        return shipDateTo;
    }

    public void setShipDateTo(Date shipDateTo) {
        this.shipDateTo = shipDateTo;
    }

    public Date getShipDateFrom() {
        return shipDateFrom;
    }

    public void setShipDateFrom(Date shipDateFrom) {
        this.shipDateFrom = shipDateFrom;
    }

    @Override
    public UUID getId() {
        return orderItemId;
    }

    @Override
    public String getInfo() {
        return "";
    }

    @Override
    public void setId(UUID id) {
        orderItemId = id;
    }

    /**
     * This is a synthetic method. Calculates and returns the quantity that is not yet matched against confirmation items.
     * This value is exactly: (orderedQuantity-confirmedQuantity)
     * @return
     */
    public BigDecimal getPendingQuantity() {
        BigDecimal confirmed = getConfirmedQuantity();
        if ( confirmed==null )
            confirmed = new BigDecimal(0);
        return getOrderedQuantity().subtract(confirmed);
    }

    /**
     * This is a synthetic method. Calculates and returns the total price
     * The value is exactly: orderedQuantity * purchasePrice
     * @return
     */
    public BigDecimal getTotal() {
        return orderedQuantity.multiply(purchasePrice);
    }
}
