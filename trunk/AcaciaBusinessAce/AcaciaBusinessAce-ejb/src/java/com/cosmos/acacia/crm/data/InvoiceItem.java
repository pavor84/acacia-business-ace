/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

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
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "InvoiceItem.findForParentAndDeleted",
                query = "select i from InvoiceItem i where i.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and i.dataObject.deleted = :deleted order by i.invoiceItemId"
            ),
        @NamedQuery
            (
                name = "InvoiceItem.findByParentDataObjectAndDeleted",
                query = "select i from InvoiceItem i where i.dataObject.parentDataObjectId = :parentDataObjectId and i.dataObject.deleted = :deleted order by i.invoiceItemId"
            ),
        @NamedQuery
            (
                name = "InvoiceItem.findByParentDataObjectIsNullAndDeleted",
                query = "select i from InvoiceItem i where i.dataObject.parentDataObjectId is null and i.dataObject.deleted = :deleted order by i.invoiceItemId"
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
    @Property(title="Product Name", customDisplay="${product.productName}", propertyValidator=@PropertyValidator(required=true))
    private Product product;

    @JoinColumn(name = "measure_unit_id", nullable=false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @Column(name = "ordered_quantity", nullable = false, precision=20, scale=4)
    @Property(title="Ordered quantiry")
    private BigDecimal orderedQuantity;

    @Column(name = "shipped_quantity", precision=20, scale=4)
    @Property(title="Shipped quantity", readOnly=true)
    private BigDecimal shippedQuantity;

    @Column(name = "returned_quantity", precision=20, scale=4)
    @Property(title="Returned quantity", readOnly=true)
    private BigDecimal returnedQuantity;
    
    @Column(name = "dueQuantity")
    private BigDecimal dueQuantity;

    @Column(name = "unit_price", nullable = false, precision=20, scale=4)
    @Property(title="Unit price", editable=false)
    private BigDecimal unitPrice;

    @Column(name = "extended_price", nullable = false, precision=20, scale=4)
    @Property(title="Extended price", readOnly=true)
    private BigDecimal extendedPrice;
    
    @Property(title="Discount", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "discount_amount", precision=20, scale=4)
    private BigDecimal discountAmount;
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "discount_percent", precision=20, scale=4)
    private BigDecimal discountPercent;

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

    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    @ManyToOne
    @Property(title="Warehouse", customDisplay="${warehouse.address.addressName}", propertyValidator=@PropertyValidator(required=true) )
    private Warehouse warehouse;
    
    @Column(name = "notes")
    @Property(title="Notes")
    private String notes;
    
    @Column(name = "product_description")
    @Property(title="Product Description")
    private String productDescription;
    
    @JoinColumn(name = "invoice_item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
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

    public Integer getShipWeek() {
        return shipWeek;
    }

    public void setShipWeek(Integer shipWeek) {
        this.shipWeek = shipWeek;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        firePropertyChange("dataObject", this.dataObject, dataObject);
        this.dataObject = dataObject;
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

    @Override
    public String getInfo() {
        return getInvoiceItemId().toString();
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

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getDueQuantity() {
        return dueQuantity;
    }

    public void setDueQuantity(BigDecimal dueQuantity) {
        this.dueQuantity = dueQuantity;
    }
}
