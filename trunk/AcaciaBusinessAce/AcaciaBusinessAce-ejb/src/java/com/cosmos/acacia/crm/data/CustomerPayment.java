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

@Entity
@Table(name = "customer_payments")
@NamedQueries(
    {
        /**
         * Parameters:
         * - parentId - not null
         * - completed - CustomerPaymentStatus.Completed.getDbResource()
         */
        @NamedQuery
            (
                name = "CustomerPayment.getConfirmedPayments",
                query = "select p from CustomerPayment p where p.dataObject.parentDataObjectId = :parentId and p.status=:completed and p.dataObject.deleted = false "
            ),
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         */
        @NamedQuery
            (
                name = "CustomerPayment.findForParent",
                query = "select p from CustomerPayment p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = false"
            ),
        /**
         * Parameters:
         * - customer
         */
        @NamedQuery
            (
                name = "CustomerPayment.getForCustomer",
                query = "select p from CustomerPayment p where p.dataObject.deleted = false " +
                		"and p.customer = :customer"
            ),
        /**
         * Parameters:
         * - branch
         */
        @NamedQuery
            (
                name = "CustomerPayment.maxDocumentNumberForBranch",
                query = "select max(e.documentNumber) from CustomerPayment e where e.branch = :branch "
            ),
        /**
         * Parameters:
         * - branch
         * - completed - CustomerPaymentStatus.Completed.getDbResource()
         */
        @NamedQuery
            (
                name = "CustomerPayment.getPartlyMatched",
                query = "select e from CustomerPayment e where e.branch = :branch and e.dataObject.deleted = false and e.status = :completed " +
                        "and e.matchedAmount is not null and e.matchedAmount > 0 and e.matchedAmount <> e.amount order by e.id"
            ),
        /**
         * Parameters:
         * - branch
         * - completed - CustomerPaymentStatus.Completed.getDbResource()
         */
        @NamedQuery
            (
                name = "CustomerPayment.getUnmatched",
                query = "select e from CustomerPayment e where e.branch = :branch and e.dataObject.deleted = false and e.status = :completed " +
                        "and (e.matchedAmount = 0 or e.matchedAmount is null) order by e.id"
            )
        
    })
public class CustomerPayment extends DataObjectBean implements Serializable {
    @Id
    @Column(name = "payment_id", nullable = false)
    private BigInteger paymentId;
    
    @JoinColumn(name = "customer_id", referencedColumnName = "partner_id", nullable=false)
    @ManyToOne
    @Property(title="Customer",
              propertyValidator=@PropertyValidator(required=true), customDisplay="${customer.displayName}")
    private BusinessPartner customer;

    @Property(title="Amount", propertyValidator=@PropertyValidator(required=true,
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "amount", nullable = false, precision=20, scale=4)
    private BigDecimal amount;
    
    @Property(title="Matched Amount")
    @Column(name = "matchedAmount", precision=20, scale=4)
    private BigDecimal matchedAmount;
    
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    @Property(title="Created At", editable=false)
    private Date creationTime;
    
    @Column(name = "paymentAccount")
    @Property(title="Payment Account", customDisplay="${paymentAccount.bankAccount}(${paymentAccount.bank.displayName})", propertyValidator=@PropertyValidator(required=true))
    private BankDetail paymentAccount;
    
    @Property(title="Type", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "payment_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource paymentType;
    
    @Column(name = "reference_no", nullable = false)
    @Property(title="Transaction/Cash Receipt No",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=50, required=true))
    private String referenceNo;
    
    @Property(title="Cashier", customDisplay="${cashier.displayName}", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "cashier_id")
    @ManyToOne
    private Person cashier;
    
    @Column(name = "transaction_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Transaction Date", propertyValidator=@PropertyValidator(required=true))
    private Date transactionDate;
    
    @Column(name = "parent_id")
    private BigInteger parentId;
    
    @JoinColumn(name = "branch_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Branch")
    private Address branch;
    
    @Property(title="Doc. Number", editable=false)
    @Column(name = "document_number")
    private BigInteger documentNumber;
    
    @Property(title="Customer Contact", customDisplay="${customerContact.contact.displayName}")
    @JoinColumn(name = "customer_contact_id")
    @ManyToOne
    private ContactPerson customerContact;
    
    @Property(title="Status", readOnly=true, propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "status_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource status;
    
    @Property(title="Created By", customDisplay="${creator.displayName}", editable=false, readOnly=true)
    @JoinColumn(name = "creator_id")
    @ManyToOne
    private Person creator;
    
    @Column(name = "completed_at")
    @Temporal(TemporalType.DATE)
    @Property(title="Completed At", editable=false)
    private Date completionTime;

    @Property(title="Completed By", customDisplay="${completor.displayName}", editable=false, readOnly=true)
    @JoinColumn(name = "completor_id")
    @ManyToOne
    private Person completor;
    
    @Property(title="Payment Return")
    @Column(name = "paymentReturn", nullable=false)
    private Boolean paymentReturn;
    
    @Property(title="Transaction Fee",propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d) )
    @Column(name = "transaction_fee", precision=20, scale=4)
    private BigDecimal transactionFee;
    
    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "payment_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    
    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentId != null ? paymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerPayment)) {
            return false;
        }
        CustomerPayment other = (CustomerPayment) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.CustomerPayment[paymentId=" + paymentId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return paymentId;
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
    public void setId(BigInteger id) {
        setPaymentId(id);
    }

    public BigInteger getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(BigInteger paymentId) {
        this.paymentId = paymentId;
    }

    public BusinessPartner getCustomer() {
        return customer;
    }

    public void setCustomer(BusinessPartner customer) {
        this.customer = customer;
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        this.branch = branch;
    }

    public BigInteger getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(BigInteger documentNumber) {
        this.documentNumber = documentNumber;
    }

    public ContactPerson getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(ContactPerson customerContact) {
        this.customerContact = customerContact;
    }

    public DbResource getStatus() {
        return status;
    }

    public void setStatus(DbResource status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public Person getCompletor() {
        return completor;
    }

    public void setCompletor(Person completor) {
        this.completor = completor;
    }

    public DbResource getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(DbResource paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public Person getCashier() {
        return cashier;
    }

    public void setCashier(Person cashier) {
        this.cashier = cashier;
    }

    public Boolean getPaymentReturn() {
        return paymentReturn;
    }

    public void setPaymentReturn(Boolean paymentReturn) {
        this.paymentReturn = paymentReturn;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankDetail getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(BankDetail paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public BigDecimal getMatchedAmount() {
        return matchedAmount;
    }
    
    public BigDecimal getUnmatchedAmount(){
        if ( getMatchedAmount()==null )
            return getAmount();
        else{
            return getAmount().subtract(getMatchedAmount());
        }
    }

    public void setMatchedAmount(BigDecimal matchedAmount) {
        this.matchedAmount = matchedAmount;
    }
}
