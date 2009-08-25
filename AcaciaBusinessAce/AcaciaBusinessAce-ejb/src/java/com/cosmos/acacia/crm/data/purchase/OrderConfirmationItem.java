/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.purchase;

import com.cosmos.acacia.crm.data.*;
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

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "order_confirmation_items")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "OrderConfirmationItem.findForParentAndDeleted",
                query = "select i from OrderConfirmationItem i where i.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and i.dataObject.deleted = :deleted"
            ),
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - product - not null
         */
        @NamedQuery
            (
                name = "OrderConfirmationItem.findForOrderAndProduct",
                query = "select i from OrderConfirmationItem i where i.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and i.product = :product"
            )
    })
public class OrderConfirmationItem extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "confirmation_item_id", nullable = false)
    @Type(type="uuid")
    private UUID confirmationItemId;

    @Column(name = "parent_id")
    @Type(type="uuid")
    private UUID parentId;

    @Property(title="Product", propertyValidator=@PropertyValidator(required=true), customDisplay="${product.productName}")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private SimpleProduct product;

    @Property(title="Measure Unit", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "measure_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource measureUnit;

    @Property(title="Confirmed Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d, required=true))
    @Column(name = "confirmed_quantity", nullable = false)
    private BigDecimal confirmedQuantity;

    @Property(title="Unit Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Property(title="Extended Price", editable=false)
    @Column(name = "extended_price", nullable = false)
    private BigDecimal extendedPrice;
    
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;

    @Property(title="Ship Week", propertyValidator=@PropertyValidator(validationType=ValidationType.NUMBER_RANGE, minValue=0, maxValue=53))
    @Column(name = "ship_week")
    private Integer shipWeek;
    
    @Property(title="Ship Date From")
    @Column(name = "ship_date_from")
    @Temporal(TemporalType.DATE)
    private Date shipDateFrom;
    
    @Property(title="Ship Date To")
    @Column(name = "ship_date_to")
    @Temporal(TemporalType.DATE)
    private Date shipDateTo;

    @JoinColumn(name = "confirmation_item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    /**
     * This is the quantity that is matched (reserved) and assigned to one or many {@link PurchaseOrderItem}s
     * @return
     */
    @Column(name = "matched_quantity")
    private BigDecimal matchedQuantity;

    public OrderConfirmationItem() {
    }

    public OrderConfirmationItem(UUID confirmationItemId) {
        this.confirmationItemId = confirmationItemId;
    }

    public UUID getConfirmationItemId() {
        return confirmationItemId;
    }

    public void setConfirmationItemId(UUID confirmationItemId) {
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

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public SimpleProduct getProduct() {
        return product;
    }

    public void setProduct(SimpleProduct product) {
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

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public UUID getId() {
        return confirmationItemId;
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
    public void setId(UUID id) {
        setConfirmationItemId(id);
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public Date getShipDateFrom() {
        return shipDateFrom;
    }

    public void setShipDateFrom(Date shipDateFrom) {
        this.shipDateFrom = shipDateFrom;
    }

    public Date getShipDateTo() {
        return shipDateTo;
    }

    public void setShipDateTo(Date shipDateTo) {
        this.shipDateTo = shipDateTo;
    }

    /**
     * This is a synthetic method. Calculates and returns the quantity that is not yet matched against an purchase order item.
     * This value is exactly: (confirmedQuantity-matchedQuantity)
     * @return
     */
    public BigDecimal getPendingQuantity() {
        BigDecimal matched = getMatchedQuantity();
        if ( matched==null )
            matched = new BigDecimal(0);
        return getConfirmedQuantity().subtract(matched);
    }

    /**
     * This is the quantity that is matched (reserved) and assigned to one or many {@link PurchaseOrderItem}s
     * @return
     */
    public BigDecimal getMatchedQuantity() {
        return matchedQuantity;
    }

    public void setMatchedQuantity(BigDecimal matchedQuantity) {
        this.matchedQuantity = matchedQuantity;
    }

    public Integer getShipWeek() {
        return shipWeek;
    }

    public void setShipWeek(Integer shipWeek) {
        this.shipWeek = shipWeek;
    }
}
