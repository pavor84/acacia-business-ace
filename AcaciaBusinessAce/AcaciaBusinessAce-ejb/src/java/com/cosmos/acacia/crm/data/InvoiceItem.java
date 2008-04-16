/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "invoice_items")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "InvoiceItem.findByParentDataObjectAndDeleted",
                query = "select i from InvoiceItem i where i.dataObject.parentDataObject = :parentDataObject and i.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "InvoiceItem.findByParentDataObjectIsNullAndDeleted",
                query = "select i from InvoiceItem i where i.dataObject.parentDataObject is null and i.dataObject.deleted = :deleted"
            )
    })
public class InvoiceItem extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "invoice_item_id", nullable = false)
    @Property(title="Invoice Item Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger invoiceItemId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    @Property(title="Product")
    private Product product;

    @JoinColumn(name = "measure_unit_id", nullable=false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @Column(name = "ordered_quantity", nullable = false)
    @Property(title="Ordered quantiry")
    private BigDecimal orderedQuantity;

    @Column(name = "shipped_quantity")
    @Property(title="Shipped quantir\ty")
    private BigDecimal shippedQuantity;

    @Column(name = "returned_quantity")
    @Property(title="Returned quantiry")
    private BigDecimal returnedQuantity;

    @Column(name = "unit_price", nullable = false)
    @Property(title="Unit price")
    private BigDecimal unitPrice;

    @Column(name = "extended_price", nullable = false)
    @Property(title="Extended price")
    private BigDecimal extendedPrice;

    @Column(name = "ship_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Ship date")
    private Date shipDate;

    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    @ManyToOne
    @Property(title="Warehouse")
    private Warehouse warehouse;

    @JoinColumn(name = "invoice_item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    @Property(title="Date object")
    private DataObject dataObject;


    public InvoiceItem() {
    }

    public InvoiceItem(BigInteger invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public BigInteger getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(BigInteger invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigDecimal getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(BigDecimal orderedQuantity) {
        firePropertyChange("orderedQuantity", this.orderedQuantity, orderedQuantity);
        this.orderedQuantity = orderedQuantity;
    }

    public BigDecimal getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(BigDecimal shippedQuantity) {
        firePropertyChange("shippedQuantity", this.shippedQuantity, shippedQuantity);
        this.shippedQuantity = shippedQuantity;
    }

    public BigDecimal getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(BigDecimal returnedQuantity) {
        firePropertyChange("returnedQuantity", this.returnedQuantity, returnedQuantity);
        this.returnedQuantity = returnedQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        firePropertyChange("unitPrice", this.unitPrice, unitPrice);
        this.unitPrice = unitPrice;
    }

    public BigDecimal getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(BigDecimal extendedPrice) {
        firePropertyChange("extendedPrice", this.extendedPrice, extendedPrice);
        this.extendedPrice = extendedPrice;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        firePropertyChange("shipDate", this.shipDate, shipDate);
        this.shipDate = shipDate;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        firePropertyChange("dataObject", this.dataObject, dataObject);
        this.dataObject = dataObject;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        firePropertyChange("product", this.product, product);
        this.product = product;
    }

    public DbResource getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(DbResource measureUnit) {
        firePropertyChange("measureUnit", this.measureUnit, measureUnit);
        this.measureUnit = measureUnit;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        firePropertyChange("warehouse", this.warehouse, warehouse);
        this.warehouse = warehouse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceItemId != null ? invoiceItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoiceItem)) {
            return false;
        }
        InvoiceItem other = (InvoiceItem) object;
        if ((this.invoiceItemId == null && other.invoiceItemId != null) || (this.invoiceItemId != null && !this.invoiceItemId.equals(other.invoiceItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.InvoiceItem[invoiceItemId=" + invoiceItemId + "]";
    }

    @Override
    public BigInteger getId() {
        return getInvoiceItemId();
    }

    @Override
    public void setId(BigInteger id) {
        setInvoiceItemId(id);
    }

}
