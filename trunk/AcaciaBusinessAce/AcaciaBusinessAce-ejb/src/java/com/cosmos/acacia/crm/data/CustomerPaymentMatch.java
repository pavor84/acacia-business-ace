/**
 * 
 */
package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.data.sales.SalesInvoice;
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
import org.hibernate.annotations.Type;

/**
 * Created	:	19.03.2009
 * @author	Petar Milev
 * 
 * Associate payment with invoice with a given match amount.
 */
@Entity
@Table(name = "customer_payment_match")
@NamedQueries(
    {
        /**
         * Parameters:
         * -invoice 
         */
        @NamedQuery(
            name = "CustomerPaymentMatch.maxNumber",
            query = "select max(e.matchNumber) from CustomerPaymentMatch e where e.invoice=:invoice"),
        /**
         * Parameters:
         * -invoice 
         */
        @NamedQuery(
            name = "CustomerPaymentMatch.getForInvoice",
            query = "select e from CustomerPaymentMatch e where e.invoice=:invoice order by e.matchNumber")
    })
public class CustomerPaymentMatch extends DataObjectBean implements Serializable{
    @Id
//    @SequenceGenerator(name="CustomerPaymentMatchSeqGen", sequenceName="customer_payment_match_seq", allocationSize=1)
//    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CustomerPaymentMatchSeqGen")
    @Column(name = "customer_payment_match_id", nullable = false)
    @Type(type="uuid")
    private UUID matchId;
    
    @JoinColumn(name = "customer_payment_id", nullable=false)
    @ManyToOne
    private CustomerPayment customerPayment;
    
    @JoinColumn(name = "invoice_id", nullable=false)
    @ManyToOne
    private SalesInvoice invoice;
    
    @Column(name = "amount", nullable = false, precision=20, scale=4)
    private BigDecimal amount;
    
    @Column(name = "matchNumber")
    private Integer matchNumber;
    
    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    
    @JoinColumn(name = "user_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID id) {
        this.matchId = id;
    }

    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public SalesInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(SalesInvoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(Integer matchNumber) {
        this.matchNumber = matchNumber;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public UUID getParentId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UUID getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setId(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
