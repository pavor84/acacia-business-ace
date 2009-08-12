/**
 * 
 */
package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.data.sales.Invoice;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class CustomerPaymentMatch implements Serializable{
    @Id
    @SequenceGenerator(name="CustomerPaymentMatchSeqGen", sequenceName="customer_payment_match_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CustomerPaymentMatchSeqGen")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @JoinColumn(name = "customer_payment", nullable=false)
    @ManyToOne
    private CustomerPayment customerPayment;
    
    @JoinColumn(name = "invoice", nullable=false)
    @ManyToOne
    private Invoice invoice;
    
    @Column(name = "amount", nullable = false, precision=20, scale=4)
    private BigDecimal amount;
    
    @Column(name = "matchNumber")
    private Integer matchNumber;
    
    @Column(name = "creation_time")
    @Temporal(TemporalType.DATE)
    private Date creationTime;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CustomerPaymentMatch other = (CustomerPaymentMatch) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
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
    
}
