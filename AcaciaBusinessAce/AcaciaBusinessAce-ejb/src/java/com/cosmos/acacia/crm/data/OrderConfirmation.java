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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "order_confirmations")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         *  - branch - may be null
         */
        @NamedQuery
            (
                name = "OrderConfirmation.findForParentAndDeleted",
                query = "select o from OrderConfirmation o where o.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and o.dataObject.deleted = :deleted " +
                        "and (o.branch = :branch or :branch is null)"
            )
    })
public class OrderConfirmation extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "order_confirmation_id", nullable = false)
    private BigInteger orderConfirmationId;

    @Column(name = "parent_id")
    private BigInteger parentId;
    
    @Property(title="Branch", customDisplay="${branch.addressName}", editable=false)
    @JoinColumn(name = "branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address branch;

    @Property(title="Document Number", propertyValidator=@PropertyValidator(maxLength=255, required=true))
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Property(title="Document Date", propertyValidator=@PropertyValidator(required=true))
    @Column(name = "documentDate_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date documentDate;
    
    @Property(title="Type", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "document_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource documentType;
    
    /**
     * The Supplier can be both Person or Organization
     */
    @Property(title="Supplier", propertyValidator=@PropertyValidator(required=true), customDisplay="${supplier.displayName}")
    @ManyToOne
    private BusinessPartner supplier;
    
    @Property(title="Supplier Name", editable=false)
    @Column(name = "supplier_name", nullable = false)
    private String supplierName;
    
    @Property(title="Supplier Contact", propertyValidator=@PropertyValidator(required=true), customDisplay="${supplierContact.contact.displayName}")
    @JoinColumn(name = "supplier_contact_id")
    @ManyToOne
    private ContactPerson supplierContact;

    @Property(title="Contact Name")
    @Column(name = "supplier_contact_name")
    private String supplierContactName;

    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;
    
    @Property(title="Invoice Sub Value", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "invoice_sub_value", nullable = false)
    private BigDecimal invoiceSubValue;
    
    @Property(title="Vat %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "vat", nullable = false)
    private BigDecimal vat;
    
    @Property(title="Discount", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "discount_percent")
    private BigDecimal discountPercent;
    
    @Property(title="Transport Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "transport_price")
    private BigDecimal transportationPrice;

    @Property(title="Total Value", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d, required=true))
    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;
    
    @Column(name = "notes")
    @Property(title="Notes")
    private String notes;
    
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

    @JoinColumn(name = "order_confirmation_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public OrderConfirmation() {
    }

    public OrderConfirmation(BigInteger orderConfirmationId) {
        this.orderConfirmationId = orderConfirmationId;
    }

    public BigInteger getOrderConfirmationId() {
        return orderConfirmationId;
    }

    public void setOrderConfirmationId(BigInteger orderConfirmationId) {
        this.orderConfirmationId = orderConfirmationId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigDecimal getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(BigDecimal transportationPrice) {
        this.transportationPrice = transportationPrice;
    }

    public BigDecimal getInvoiceSubValue() {
        return invoiceSubValue;
    }

    public void setInvoiceSubValue(BigDecimal invoiceSubValue) {
        this.invoiceSubValue = invoiceSubValue;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderConfirmationId != null ? orderConfirmationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderConfirmation)) {
            return false;
        }
        OrderConfirmation other = (OrderConfirmation) object;
        if ((this.orderConfirmationId == null && other.orderConfirmationId != null) || (this.orderConfirmationId != null && !this.orderConfirmationId.equals(other.orderConfirmationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.OrderConfirmation[orderConfirmationId=" + orderConfirmationId + "]";
    }

    @Override
    public BigInteger getId() {
        return getOrderConfirmationId();
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public void setId(BigInteger id) {
        setOrderConfirmationId(id);
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        this.branch = branch;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public DbResource getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DbResource documentType) {
        this.documentType = documentType;
    }

    public BusinessPartner getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessPartner supplier) {
        this.supplier = supplier;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public ContactPerson getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(ContactPerson supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierContactName() {
        return supplierContactName;
    }

    public void setSupplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getShipWeek() {
        return shipWeek;
    }

    public void setShipWeek(Integer shipWeek) {
        this.shipWeek = shipWeek;
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

}
