/**
 * 
 */
package com.cosmos.acacia.crm.data.cash;

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
import com.cosmos.acacia.crm.data.DbResource;

/**
 * Created	:	26.04.2009
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "cash_recon_pay_summary")
@NamedQueries({
    @NamedQuery(
        name = CashReconcilePaymentSummary.NQ_FIND_FOR_RECONCILE,
        query = "SELECT e FROM CashReconcilePaymentSummary e" +
            " where" +
            "  e.cashReconcile.id = :cashReconcile ")
})
public class CashReconcilePaymentSummary implements Serializable{
    public static final String NQ_FIND_FOR_RECONCILE =
            "CashReconcilePaymentSummary.findForReconcile";

    @Id
    @SequenceGenerator(name="CashReconcilePaymentSummaryGen", sequenceName="cash_recon_pay_summary_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CashReconcilePaymentSummaryGen")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @JoinColumn(name = "paymentType", nullable = false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Payment Type")
    private DbResource paymentType;
    
    @JoinColumn(name = "currency", nullable = false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Currency")
    private DbResource currency;
    
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    @Property(title="Amount")
    private BigDecimal amount;
    
    @ManyToOne
    @JoinColumn (name="cash_reconcile_id", updatable = false, insertable = false)
    private CashReconcile cashReconcile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DbResource getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(DbResource paymentType) {
        this.paymentType = paymentType;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CashReconcile getCashReconcile() {
        return cashReconcile;
    }

    public void setCashReconcile(CashReconcile cashReconcile) {
        this.cashReconcile = cashReconcile;
    }

    public CashReconcilePaymentSummary(DbResource paymentType, DbResource currency,
            CashReconcile cashReconcile, BigDecimal amount) {
        super();
        this.paymentType = paymentType;
        this.currency = currency;
        this.cashReconcile = cashReconcile;
        this.amount = amount;
    }

    public CashReconcilePaymentSummary() {
        super();
    }

}
