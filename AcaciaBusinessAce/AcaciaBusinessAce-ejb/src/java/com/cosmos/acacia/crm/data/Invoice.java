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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "invoice_id", nullable = false)
    private BigInteger invoiceId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address branch;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "invoice_number", nullable = false)
    private long invoiceNumber;

    @Column(name = "invoice_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @JoinColumn(name = "recipient_id", referencedColumnName = "classified_object_id", insertable = false, updatable = false)
    @ManyToOne
    private ClassifiedObject recipientClassifier;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @JoinColumn(name = "recipient_contact_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person recipientContactId;

    @Column(name = "recipient_contact_name", nullable = false)
    private String recipientContactName;

    @JoinColumn(name = "invoice_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource invoiceType;

    @JoinColumn(name = "status_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource statusId;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationTime;

    @JoinColumn(name = "creator_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person creator;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @JoinColumn(name = "doc_delivery_method_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource documentDeliveryMethod;

    @JoinColumn(name = "transportation_method_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource transportationMethod;

    @JoinColumn(name = "shipping_agent_id", referencedColumnName = "classified_object_id", insertable = false, updatable = false)
    @ManyToOne
    private ClassifiedObject shippingAgentClassifier;

    @Column(name = "transportation_price")
    private BigDecimal transportationPrice;

    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;

    @Column(name = "invoice_sub_value", nullable = false)
    private BigDecimal invoiceSubValue;

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "invoice_value", nullable = false)
    private BigDecimal invoiceValue;

    @Column(name = "excise_duty_percent")
    private BigDecimal exciseDutyPercent;

    @Column(name = "excise_duty_value")
    private BigDecimal exciseDutyValue;

    @JoinColumn(name = "vat_condition_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource vatCondition;

    @Column(name = "vat_percent", nullable = false)
    private BigDecimal vatPercent;

    @Column(name = "vat_value", nullable = false)
    private BigDecimal vatValue;

    @Column(name = "total_invoice_value", nullable = false)
    private BigDecimal totalInvoiceValue;

    @JoinColumn(name = "payment_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource paymentType;

    @JoinColumn(name = "payment_terms_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource paymentTerms;

    @Column(name = "payment_due_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDueDate;

    @JoinColumn(name = "delivery_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource deliveryType;

    @Column(name = "sent_time")
    @Temporal(TemporalType.DATE)
    private Date sentTime;

    @JoinColumn(name = "sender_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person sender;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "first_ship_date")
    @Temporal(TemporalType.DATE)
    private Date firstShipDate;

    @Column(name = "last_ship_date")
    @Temporal(TemporalType.DATE)
    private Date lastShipDate;

    @Column(name = "finalizing_date")
    @Temporal(TemporalType.DATE)
    private Date finalizingDate;

    @JoinColumn(name = "invoice_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;



    public Invoice() {
    }

    public Invoice(BigInteger invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigInteger getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(BigInteger invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public ClassifiedObject getRecipientClassifier() {
        return recipientClassifier;
    }

    public void setSupplierClassifier(ClassifiedObject recipientClassifier) {
        this.recipientClassifier = recipientClassifier;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientContactName() {
        return recipientContactName;
    }

    public void setRecipientContactName(String recipientContactName) {
        this.recipientContactName = recipientContactName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public ClassifiedObject getShippingAgentClassifier() {
        return shippingAgentClassifier;
    }

    public void setShippingAgentClassifier(ClassifiedObject shippingAgentClassifier) {
        this.shippingAgentClassifier = shippingAgentClassifier;
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

    public BigDecimal getExciseDutyPercent() {
        return exciseDutyPercent;
    }

    public void setExciseDutyPercent(BigDecimal exciseDutyPercent) {
        this.exciseDutyPercent = exciseDutyPercent;
    }

    public BigDecimal getExciseDutyValue() {
        return exciseDutyValue;
    }

    public void setExciseDutyValue(BigDecimal exciseDutyValue) {
        this.exciseDutyValue = exciseDutyValue;
    }

    public BigDecimal getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(BigDecimal vatPercent) {
        this.vatPercent = vatPercent;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }

    public BigDecimal getTotalInvoiceValue() {
        return totalInvoiceValue;
    }

    public void setTotalInvoiceValue(BigDecimal totalInvoiceValue) {
        this.totalInvoiceValue = totalInvoiceValue;
    }

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getFirstShipDate() {
        return firstShipDate;
    }

    public void setFirstShipDate(Date firstShipDate) {
        this.firstShipDate = firstShipDate;
    }

    public Date getLastShipDate() {
        return lastShipDate;
    }

    public void setLastShipDate(Date lastShipDate) {
        this.lastShipDate = lastShipDate;
    }

    public Date getFinalizingDate() {
        return finalizingDate;
    }

    public void setFinalizingDate(Date finalizingDate) {
        this.finalizingDate = finalizingDate;
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        this.branch = branch;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Person getRecipientContactId() {
        return recipientContactId;
    }

    public void setRecipientContactId(Person recipientContactId) {
        this.recipientContactId = recipientContactId;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public DbResource getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(DbResource paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public DbResource getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(DbResource paymentType) {
        this.paymentType = paymentType;
    }

    public DbResource getStatusId() {
        return statusId;
    }

    public void setStatusId(DbResource statusId) {
        this.statusId = statusId;
    }

    public DbResource getTransportationMethod() {
        return transportationMethod;
    }

    public void setTransportationMethod(DbResource transportationMethod) {
        this.transportationMethod = transportationMethod;
    }

    public DbResource getVatCondition() {
        return vatCondition;
    }

    public void setVatCondition(DbResource vatCondition) {
        this.vatCondition = vatCondition;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public DbResource getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DbResource deliveryType) {
        this.deliveryType = deliveryType;
    }

    public DbResource getDocumentDeliveryMethod() {
        return documentDeliveryMethod;
    }

    public void setDocumentDeliveryMethod(DbResource documentDeliveryMethod) {
        this.documentDeliveryMethod = documentDeliveryMethod;
    }

    public DbResource getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(DbResource invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceId != null ? invoiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invoiceId == null && other.invoiceId != null) || (this.invoiceId != null && !this.invoiceId.equals(other.invoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Invoice[invoiceId=" + invoiceId + "]";
    }

}
