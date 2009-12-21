/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.sales;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.Address;
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
import java.math.BigInteger;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "sales_invoices")
@NamedQueries(
    {
        /**
         * Get all unpaid invoices for the given recipient
         * Parameters: 
         * - recipient - BusinessPartner instance (assuming 'customer' classified), required
         * - waitingForPayment - status, supply not null InvoiceStatus.WaitForPayment.getDbResource
         */
        @NamedQuery
            (
                name = "Invoice.getDueInvoicesForRecipient",
                query = "select i from SalesInvoice i where i.recipient = :recipient and i.status = :waitingForPayment"
            ),
        /**
         * Get all unpaid invoices for the given recipient
         * Parameters: 
         * - parentId - BusinessPartner instance (assuming 'customer' classified), required
         * - waitingForPayment - status, supply not null InvoiceStatus.WaitForPayment.getDbResource
         */
        @NamedQuery
            (
                name = "Invoice.getDueInvoices",
                query = "select i from SalesInvoice i where i.status = :waitingForPayment and i.dataObject.parentDataObjectId = :parentId"
            ),
        /**
         * If a given InvoiceItem is copied from an other document, then this query returns all
         * other InvoiceItems of this document. Otherwise returns emtpy list.
         * Parameters: 
         * - item - the item instance, required
         */
        @NamedQuery
            (
                name = "Invoice.getCopiedItemsFromTheSameDocument",
                query = "select link.invoiceItem from SalesInvoiceItemLink link where link.templateDocumentId in "+
                        "(select templateDocumentId from SalesInvoiceItemLink where invoiceItem = :item)"
            ),
        /**
         * Parameters: 
         * - proformaInvoice - required. Supply Boolean.TRUE as value
         * - recipient - required - the recipient
         * - paid - required. Value of InvoiceStatus.PAID.getDbResource()
         */
        @NamedQuery
            (
                name = "Invoice.getTemplatesForInvoice",
                query = "select i from SalesInvoice i where i.proformaInvoice = :proformaInvoice " +
                		"and i.recipient = :recipient " +
                		"and i.status = :paid " +
                		//at last, make sure that the selected invoice is not already used as template
                		"and not exists (from SalesInvoiceItemLink itemLink where itemLink.templateDocumentId = i.invoiceId)"
            ),
        /**
         * Get all cancel-able invoices for a given recipient. 
         * Parameters: 
         * - proformaInvoice - required. Supply Boolean.TRUE as value
         * - recipient - required - the recipient
         * - simpleInvoice - required. Value of InvoiceType.SimpleInvoice.getDbResource()
         * - vatInvoice - required. Value of InvoiceType.VatInvoice.getDbResource()
         * - waitingForPayment - required. Value of InvoiceStatus.WaitForPayment.getDbResource()
         */
        @NamedQuery
            (
                name = "Invoice.getInvoiceToCancel",
                query = "select i from SalesInvoice i where i.proformaInvoice = :proformaInvoice " +
                        "and i.recipient = :recipient " +
                        "and i.status = :waitingForPayment " +
                        "and (i.invoiceType = :simpleInvoice or i.invoiceType = :vatInvoice) "
            ),
        /**
         * Parameters: 
         *  - branch - not null
         *  - proformaInvoice - true or false, not null
         */
        @NamedQuery
            (
                name = "Invoice.maxInvoiceNumberForBranch",
                query = "select max(i.invoiceNumber) from SalesInvoice i where i.branch = :branch " +
                		"and i.proformaInvoice = :proformaInvoice "
            ),
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         *  - proformaInvoice - not null, true or false
         */
        @NamedQuery
            (
                name = "Invoice.findForParentAndDeleted",
                query = "select i from SalesInvoice i where i.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and i.dataObject.deleted = :deleted and i.proformaInvoice = :proformaInvoice"
            ),
        @NamedQuery
            (
                name = "Invoice.findByParentDataObjectAndDeleted",
                query = "select i from SalesInvoice i where i.dataObject.parentDataObjectId = :parentDataObjectId and i.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "Invoice.findByParentDataObjectIsNullAndDeleted",
                query = "select i from SalesInvoice i where i.dataObject.parentDataObjectId is null and i.dataObject.deleted = :deleted"
            ),
        @NamedQuery
        (
            name = "Invoice.findById",
            query = "select i from SalesInvoice i where i.dataObject.dataObjectId = :invoiceId"
        ),
        /**
         * Parameters:
         * - recipient - not null
         * - waitingForPayment - InvoiceStatus.WaitForPayment.getDbResource()
         * - paid - InvoiceStatus.Paid.getDbResource()
         * - proformaInvoice - not null (true or false)
         */
        @NamedQuery
        (
            name = "Invoice.getConfirmedInvoicesForRecipient",
            query = "select i from SalesInvoice i where i.recipient = :recipient and " +
            		"(i.status = :waitingForPayment or i.status = :paid) and " +
            		"i.proformaInvoice = :proformaInvoice order by i.invoiceDate desc"
        ),
        /**
         * Parameters:
         * - parentId - not null
         * - waitingForPayment - InvoiceStatus.WaitForPayment.getDbResource()
         * - paid - InvoiceStatus.Paid.getDbResource()
         */
        @NamedQuery
        (
            name = "Invoice.getConfirmedInvoices",
            query = "select i from SalesInvoice i where " +
            		"(i.status = :waitingForPayment or i.status = :paid)" +
            		" and i.dataObject.parentDataObjectId = :parentId and i.dataObject.deleted = false"
        ),
        /**
         * Parameters:
         * - waitForPayment - InvoiceStatus.WaitForPayment.getDbResource()
         * - branch
         * - recipient
         * - creditNote - InvoiceType.CretidNoteInvoice.getDbResource()
         */
        @NamedQuery
        (
            name = "Invoice.getPartlyMatched",
            query = "select i from SalesInvoice i where " +
                    "(i.status = :waitForPayment) and i.recipient=:recipient " +
                    " and i.branch= :branch and i.dataObject.deleted = false " +
                    " and i.paidAmount is not null and i.paidAmount>0 and i.invoiceType<>:creditNote " +
                    " order by i.id"
        ),
        /**
         * Parameters:
         * - waitingForPayment - InvoiceStatus.WaitForPayment.getDbResource()
         * - branch
         * - recipient
         * - creditNote - InvoiceType.CretidNoteInvoice.getDbResource()
         */
        @NamedQuery
        (
            name = "Invoice.getUnmatched",
            query = "select i from SalesInvoice i where " +
                    "(i.status = :waitForPayment) and i.recipient=:recipient " +
                    " and i.branch= :branch and i.dataObject.deleted = false " +
                    " and (i.paidAmount is null or i.paidAmount=0) and i.invoiceType<>:creditNote " +
                    " order by i.id"
        )
    })
public class SalesInvoice extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "invoice_id", nullable = false)
    @Type(type="uuid")
    @Property(title="Invoice Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID invoiceId;
    
    @Column(name = "proforma", nullable=false)
    private Boolean proformaInvoice;
    
    @Column(name = "parent_id")
    @Type(type="uuid")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID parentId;

    @JoinColumn(name = "branch_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Branch")
    private Address branch;

    @Column(name = "branch_name", nullable = false)
    @Property(title="Branch Name")
    private String branchName;

    @Property(title="Doc. Number", editable=false)
    @Column(name = "invoice_number")
    private BigInteger invoiceNumber;

    @Column(name = "invoice_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Date", editable=false)
    private Date invoiceDate;

    @JoinColumn(name = "recipient_id", referencedColumnName = "business_partner_id", nullable=false)
    @ManyToOne
    @Property(title="Recipient",
              propertyValidator=@PropertyValidator(required=true), customDisplay="${recipient.displayName}")
    private BusinessPartner recipient;

    @Column(name = "recipient_name", nullable = false)
    @Property(title="Recipient name",
              propertyValidator=@PropertyValidator(required=true))
    private String recipientName;

    @Property(title="Recipient Contact", propertyValidator=@PropertyValidator(required=true), customDisplay="${recipientContact.contact.displayName}")
    @JoinColumn(name = "supplier_contact_id")
    @ManyToOne
    private ContactPerson recipientContact;

    @Column(name = "recipient_contact_name", nullable = false)
    @Property(title="Recipient contact name",
              propertyValidator=@PropertyValidator(required=true))
    private String recipientContactName;

    @JoinColumn(name = "invoice_type_id", nullable = false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Type",
              propertyValidator=@PropertyValidator(required=true))
    private DbResource invoiceType;

    @Property(title="Status", readOnly=true, propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "status_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource status;
    
    @Property(title="Delivery Status", readOnly=true)
    @ManyToOne
    private DbResource deliveryStatus;

    @Column(name = "creation_time")
    @Temporal(TemporalType.DATE)
    @Property(title="Creation date", editable=false)
    private Date creationTime;

    @Property(title="Creator", customDisplay="${creator.displayName}")
    @JoinColumn(name = "creator_id")
    @ManyToOne
    private Person creator;
    
    @Property(title="Creator Name", editable=false)
    @Column(name = "creator_name")
    private String creatorName;

    @Property(title="Delivery Method", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "doc_delivery_method_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource documentDeliveryMethod;

    @JoinColumn(name = "transportation_method_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Transportation method", 
              propertyValidator=@PropertyValidator(required=true))
    private DbResource transportationMethod;
    
    @Property(title="Shipping Agent", customDisplay="${shippingAgent.displayName}")
    @ManyToOne
    private BusinessPartner shippingAgent;

    @Property(title="Transport Price")
    @Column(name = "transport_price", precision=20, scale=4)
    private BigDecimal transportationPrice;

    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;

    @Property(title="Invoice Sub Value", propertyValidator=@PropertyValidator(required=true))
    @Column(name = "invoice_sub_value", nullable = false, precision=20, scale=4)
    private BigDecimal invoiceSubValue;

    @Property(title="Vat %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "vat", nullable = false, precision=20, scale=4)
    private BigDecimal vat;
    
    @Property(title="Discount", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "discount_amount", precision=20, scale=4)
    private BigDecimal discountAmount;
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "discount_percent", precision=20, scale=4)
    private BigDecimal discountPercent;
    
    @Property(title="Total Value", propertyValidator=@PropertyValidator(required=true))
    @Column(name = "total_value", nullable = false, precision=20, scale=4)
    private BigDecimal totalValue;

    @Property(title="Excise Duty %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000d))
    @Column(name = "excise_duty_percent", precision=20, scale=4)
    private BigDecimal exciseDutyPercent;

    @Property(title="Excise Duty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "excise_duty_amount", precision=20, scale=4)
    private BigDecimal exciseDutyValue;

    @JoinColumn(name = "vat_condition_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Vat Condition", propertyValidator=@PropertyValidator(required=true))
    private DbResource vatCondition;

    @JoinColumn(name = "payment_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Payment Method",
              propertyValidator=@PropertyValidator(required=true))
    private DbResource paymentType;

    @JoinColumn(name = "payment_terms_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Payment Terms",
              propertyValidator=@PropertyValidator(required=true))
    private DbResource paymentTerms;

    @Column(name = "payment_due_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Payment due date")
    private Date paymentDueDate;
    
    @Property(title="Single Pay Amount", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "single_pay", precision=20, scale=4)
    private BigDecimal singlePayAmount;
    
    @Property(title="Payments Count", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0, maxValue=1000000))
    @Column(name = "payments_count")
    private Integer paymentsCount;
    
    @Property(title="Days Between Payments", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0, maxValue=1000000))
    @Column(name = "days_between_payments")
    private Integer daysBetweenPayments;

    @JoinColumn(name = "delivery_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Delivery Type", propertyValidator=@PropertyValidator( required=true ))
    private DbResource deliveryType;

    @Column(name = "sent_time")
    @Temporal(TemporalType.DATE)
    @Property(title="Sent Time", editable=false)
    private Date sentTime;

    @Property(title="Sender")
    @JoinColumn(name = "sender_id")
    @ManyToOne
    private Person sender;
    
    @Property(title="Sender Name", editable=false)
    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "completion_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Completion Date", editable=false)
    private Date completionDate;
    
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
    
    @Column(name = "vat_condition_notes")
    @Property(title="Vat Condition Notes")
    private String vatConditionNotes;
    
    @Column(name = "validTo")
    @Temporal(TemporalType.DATE)
    @Property(title="Valid To")
    private Date validTo;
    
    @Property(title="Atendee", customDisplay="${attendee.contact.displayName}")
    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private ContactPerson attendee;
    
    @Column(name = "additionalTerms")
    @Property(title="Additional Terms")
    private String additionalTerms;
    
    @Column(name = "paid_amount", precision=20, scale=4)
    private BigDecimal paidAmount;
    
    @JoinColumn(name = "invoice_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    @Property(title="Invoice ID", editable=false, readOnly=true, visible=false)
    private DataObject dataObject;

    public SalesInvoice() {
    }

    public SalesInvoice(UUID invoiceId) {
        this.invoiceId = invoiceId;
    }

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(UUID invoiceId) {
        this.invoiceId = invoiceId;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        firePropertyChange("invoiceDate", this.invoiceDate, invoiceDate);
        this.invoiceDate = invoiceDate;
    }

    public BusinessPartner getRecipient() {
        return recipient;
    }

    public void setRecipient(BusinessPartner recipient) {
        firePropertyChange("recipientLink", this.recipient, recipient);
        this.recipient = recipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        firePropertyChange("recipientName", this.recipientName, recipientName);
        this.recipientName = recipientName;
    }

    public String getRecipientContactName() {
        return recipientContactName;
    }

    public void setRecipientContactName(String recipientContactName) {
        firePropertyChange("recipientContactName", this.recipientContactName, recipientContactName);
        this.recipientContactName = recipientContactName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        firePropertyChange("creationTime", this.creationTime, creationTime);
        this.creationTime = creationTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        firePropertyChange("creatorName", this.creatorName, creatorName);
        this.creatorName = creatorName;
    }

    public BigDecimal getTransportationPrice() {
        return transportationPrice;
    }

    public void setTransportationPrice(BigDecimal transportationPrice) {
        firePropertyChange("transportationPrice", this.transportationPrice, transportationPrice);
        this.transportationPrice = transportationPrice;
    }

    public BigDecimal getInvoiceSubValue() {
        return invoiceSubValue;
    }

    public void setInvoiceSubValue(BigDecimal invoiceSubValue) {
        firePropertyChange("invoiceSubValue", this.invoiceSubValue, invoiceSubValue);
        this.invoiceSubValue = invoiceSubValue;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        firePropertyChange("discountPercent", this.discountPercent, discountPercent);
        this.discountPercent = discountPercent;
    }

    public BigDecimal getExciseDutyPercent() {
        return exciseDutyPercent;
    }

    public void setExciseDutyPercent(BigDecimal exciseDutyPercent) {
        firePropertyChange("exciseDutyPercent", this.exciseDutyPercent, exciseDutyPercent);
        this.exciseDutyPercent = exciseDutyPercent;
    }

    public BigDecimal getExciseDutyValue() {
        return exciseDutyValue;
    }

    public void setExciseDutyValue(BigDecimal exciseDutyValue) {
        firePropertyChange("exciseDutyValue", this.exciseDutyValue, exciseDutyValue);
        this.exciseDutyValue = exciseDutyValue;
    }

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        firePropertyChange("paymentDueDate", this.paymentDueDate, paymentDueDate);
        this.paymentDueDate = paymentDueDate;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        firePropertyChange("sentTime", this.sentTime, sentTime);
        this.sentTime = sentTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        firePropertyChange("senderName", this.senderName, senderName);
        this.senderName = senderName;
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        firePropertyChange("branch", this.branch, branch);
        this.branch = branch;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        firePropertyChange("dataObject", this.dataObject, dataObject);
        this.dataObject = dataObject;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        firePropertyChange("sender", this.sender, sender);
        this.sender = sender;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        firePropertyChange("creator", this.creator, creator);
        this.creator = creator;
    }

    public DbResource getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(DbResource paymentTerms) {
        firePropertyChange("paymentTerms", this.paymentTerms, paymentTerms);
        this.paymentTerms = paymentTerms;
    }

    public DbResource getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(DbResource paymentType) {
        firePropertyChange("paymentType", this.paymentType, paymentType);
        this.paymentType = paymentType;
    }

    public DbResource getTransportationMethod() {
        return transportationMethod;
    }

    public void setTransportationMethod(DbResource transportationMethod) {
        firePropertyChange("transportationMethod", this.transportationMethod, transportationMethod);
        this.transportationMethod = transportationMethod;
    }

    public DbResource getVatCondition() {
        return vatCondition;
    }

    public void setVatCondition(DbResource vatCondition) {
        firePropertyChange("vatCondition", this.vatCondition, vatCondition);
        this.vatCondition = vatCondition;
    }

    public DbResource getCurrency() {
        return currency;
    }
    
    public void setCurrency(DbResource currency) {
        firePropertyChange("currency", this.currency, currency);
        this.currency = currency;
    }

    public DbResource getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DbResource deliveryType) {
        firePropertyChange("deliveryType", this.deliveryType, deliveryType);
        this.deliveryType = deliveryType;
    }

    public DbResource getDocumentDeliveryMethod() {
        return documentDeliveryMethod;
    }

    public void setDocumentDeliveryMethod(DbResource documentDeliveryMethod) {
        firePropertyChange("documentDeliveryMethod", this.documentDeliveryMethod, documentDeliveryMethod);
        this.documentDeliveryMethod = documentDeliveryMethod;
    }

    public DbResource getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(DbResource invoiceType) {
        firePropertyChange("invoiceType", this.invoiceType, invoiceType);
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
        if (!(object instanceof SalesInvoice)) {
            return false;
        }
        SalesInvoice other = (SalesInvoice) object;
        if ((this.invoiceId == null && other.invoiceId != null) || (this.invoiceId != null && !this.invoiceId.equals(other.invoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Invoice[invoiceId=" + invoiceId + "]";
    }

    @Override
    public UUID getId() {
        return getInvoiceId();
    }

    @Override
    public void setId(UUID id) {
        setInvoiceId(id);
    }

    @Override
    public String getInfo() {
        return "" + getInvoiceNumber();
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

    public ContactPerson getRecipientContact() {
        return recipientContact;
    }

    public void setRecipientContact(ContactPerson recipientContact) {
        this.recipientContact = recipientContact;
    }

    public DbResource getStatus() {
        return status;
    }

    public void setStatus(DbResource status) {
        this.status = status;
    }

    public BusinessPartner getShippingAgent() {
        return shippingAgent;
    }

    public void setShippingAgent(BusinessPartner shippingAgent) {
        this.shippingAgent = shippingAgent;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getVatConditionNotes() {
        return vatConditionNotes;
    }

    public void setVatConditionNotes(String vatConditionNotes) {
        this.vatConditionNotes = vatConditionNotes;
    }

    public BigDecimal getSinglePayAmount() {
        return singlePayAmount;
    }

    public void setSinglePayAmount(BigDecimal singlePayAmount) {
        this.singlePayAmount = singlePayAmount;
    }

    public Integer getPaymentsCount() {
        return paymentsCount;
    }

    public void setPaymentsCount(Integer paymentsCount) {
        this.paymentsCount = paymentsCount;
    }

    public Integer getDaysBetweenPayments() {
        return daysBetweenPayments;
    }

    public void setDaysBetweenPayments(Integer daysBetweenPayments) {
        this.daysBetweenPayments = daysBetweenPayments;
    }

    public BigInteger getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(BigInteger invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Boolean getProformaInvoice() {
        return proformaInvoice;
    }

    public void setProformaInvoice(Boolean proformaInvoice) {
        this.proformaInvoice = proformaInvoice;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public DbResource getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DbResource deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public ContactPerson getAttendee() {
        return attendee;
    }

    public void setAttendee(ContactPerson attendee) {
        this.attendee = attendee;
    }

    public String getAdditionalTerms() {
        return additionalTerms;
    }

    public void setAdditionalTerms(String additionalTerms) {
        this.additionalTerms = additionalTerms;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
    
    /**
     * Synthetic getter. Substracts the {@link #getPaidAmount()} from {@link #getTotalValue()}.
     * Always returns not null result.
     */
    public BigDecimal getDueAmount(){
        BigDecimal totalValue = getTotalValue();
        if ( totalValue==null )
            totalValue = BigDecimal.ZERO;
        BigDecimal paidAmount = getPaidAmount();
        if ( paidAmount==null )
            paidAmount = BigDecimal.ZERO;
        totalValue = totalValue.subtract(paidAmount);
        return totalValue;
    }
}
