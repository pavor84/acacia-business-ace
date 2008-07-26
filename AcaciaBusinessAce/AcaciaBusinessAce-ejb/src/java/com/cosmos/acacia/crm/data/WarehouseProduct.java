/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "warehouse_products")
@NamedQueries({
    @NamedQuery
    ( 
        /**
         * Get all warehouse products which are not deleted (i.e. their warehouse is not deleted)
         */
        name = "WarehouseProduct.findAll",
        query = "select w from WarehouseProduct w where w.warehouse.dataObject.deleted = false"
    ),
    @NamedQuery
    ( 
        /**
         * Get all warehouse products which are not deleted and are in a given warehouse and
         * associated with a given product
         * Parameters:
         *  - product - not null
         *  - warehouse - not null
         */
        name = "WarehouseProduct.findByProductAndWarehouse",
        query = "select w from WarehouseProduct w where w.warehouse.dataObject.deleted = false " +
        		"and w.warehouse = :warehouse and w.product = :product"
    ),
    @NamedQuery
    ( 
        /**
         * Get all warehouse products associated with a given warehouse
         * Parameters:
         *  - warehouse - not null
         */
        name = "WarehouseProduct.findForWarehouse",
        query = "select wp from WarehouseProduct wp where wp.warehouse = :warehouse "
    ),
    @NamedQuery
    ( 
        /**
         * Get the warehouse product for a given branch and product
         * Parameters:
         *  - product - not null
         *  - branch - not null
         */
        name = "WarehouseProduct.findByProductAndBranch",
        query = "select w from WarehouseProduct w where w.warehouse.address = :branch " +
                "and w.product = :product"
    )
})
public class WarehouseProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "warehouse_product_id", nullable = false)
    @SequenceGenerator(name="WarehouseProductSequenceGenerator", sequenceName="warehouse_product_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WarehouseProductSequenceGenerator")
    private Integer id;

    @Property(title="Warehouse", propertyValidator=@PropertyValidator(required=true), customDisplay="${warehouse.address.addressName}")
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", nullable=false)
    @ManyToOne
    private Warehouse warehouse;

    @Property(title="Product", propertyValidator=@PropertyValidator(required=true), customDisplay="${product.productName}")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable=false)
    @ManyToOne
    private SimpleProduct product;

    @Property(title="In Stock", editable=false, propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "quantity_in_stock")
    private BigDecimal quantityInStock;

    @Property(title="Ordered", editable=false, propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "ordered_quantity")
    private BigDecimal orderedQuantity;

    @Property(title="Reserved", editable=false, propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "reserved_quantity")
    private BigDecimal reservedQuantity;

    @Property(title="Sold", editable=false, propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "sold_quantity")
    private BigDecimal soldQuantity;

    @Property(title="Due", editable=false, propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "quantity_due")
    private BigDecimal quantityDue;

    @Property(title="Min. Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "minimum_quantity")
    private BigDecimal minimumQuantity;

    @Property(title="Max. Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "maximum_quantity")
    private BigDecimal maximumQuantity;

    @Property(title="Default Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "default_quantity")
    private BigDecimal defaultQuantity;

    @Property(title="Purchase Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    
    @Property(title="Sales Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Property(title="Delivery Time", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "delivery_time")
    private Integer deliveryTime;

    @Column(name = "notes")
    @Property(title="Notes")
    private String notes;

    public WarehouseProduct() {
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
    
    /**
     * Synthetic getter - decides whether to get the value from warehouse product or
     * from the associated simple product
     * @return BigDecimal
     */
    public BigDecimal getPrefferedMinQuantity(){
        if ( minimumQuantity!=null )
            return minimumQuantity;
        else if ( product!=null )
            return product.getMinimumQuantity();
        else
            return null;
    }
    
    /**
     * Synthetic getter - decides whether to get the value from warehouse product or
     * from the associated simple product
     * @return BigDecimal
     */
    public BigDecimal getPrefferedMaxQuantity(){
        if ( maximumQuantity!=null )
            return maximumQuantity;
        else if ( product!=null )
            return product.getMaximumQuantity();
        else
            return null;
    }
    
    /**
     * Synthetic getter - decides whether to get the value from warehouse product or
     * from the associated simple product
     * @return BigDecimal
     */
    public BigDecimal getPrefferedDefaultQuantity(){
        if ( defaultQuantity!=null )
            return defaultQuantity;
        else if ( product!=null )
            return product.getDefaultQuantity();
        else
            return null;
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

    public void setDefaultQuantity(BigDecimal defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setMaximumQuantity(BigDecimal maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public void setMinimumQuantity(BigDecimal minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WarehouseProduct)) {
            return false;
        }
        WarehouseProduct other = (WarehouseProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.WarehouseProduct[id=" + id + "]";
    }

    /**
     * Getter for id
     * @return Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id - Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMinimumQuantity() {
        return minimumQuantity;
    }

    public BigDecimal getMaximumQuantity() {
        return maximumQuantity;
    }

    public BigDecimal getDefaultQuantity() {
        return defaultQuantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }
}
