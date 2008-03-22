/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "order_confirmations")
public class OrderConfirmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "order_confirmation_id", nullable = false)
    private BigInteger orderConfirmationId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "purchase_order_id", referencedColumnName = "purchase_order_id")
    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "invoice_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @JoinColumn(name = "status_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource status;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private Person sender;

    @Column(name = "sender_name")
    private String senderName;

    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;

    @Column(name = "transportation_price")
    private BigDecimal transportationPrice;

    @Column(name = "invoice_sub_value", nullable = false)
    private BigDecimal invoiceSubValue;

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "invoice_value", nullable = false)
    private BigDecimal invoiceValue;

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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(BigDecimal invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public DbResource getStatus() {
        return status;
    }

    public void setStatus(DbResource status) {
        this.status = status;
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

}
