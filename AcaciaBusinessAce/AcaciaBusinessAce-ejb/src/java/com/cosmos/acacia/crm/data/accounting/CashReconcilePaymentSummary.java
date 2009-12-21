/**
 * 
 */
package com.cosmos.acacia.crm.data.accounting;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

/**
 * Created	:	26.04.2009
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "cash_reconcile_payment_summary", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"cash_reconcile_id", "payment_type_id", "currency_id"})
})
@NamedQueries({
    @NamedQuery(
        name = CashReconcilePaymentSummary.NQ_FIND_ALL,
        query = "SELECT t FROM CashReconcilePaymentSummary t" +
                " where" +
                "  t.cashReconcile = :cashReconcile" +
                " order by t.paymentType, t.currency"
    )
})
public class CashReconcilePaymentSummary extends DataObjectBean implements Serializable{

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "CashReconcilePaymentSummary";
    public static final String NQ_FIND_ALL =
            CLASS_NAME + ".findAll";

    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "payment_summary_id", nullable = false)
    private UUID paymentSummaryId;
    
    @JoinColumn(name = "payment_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Payment Type")
    private DbResource paymentType;
    
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Currency")
    private DbResource currency;
    
    @Basic(optional = false)
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    @Property(title="Amount")
    private BigDecimal amount;
    
    @JoinColumn(name = "cash_reconcile_id", referencedColumnName = "cash_reconcile_id", nullable = false)
    @ManyToOne(optional = false)
    private CashReconcile cashReconcile;

    @JoinColumn(name = "payment_summary_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public CashReconcilePaymentSummary() {
    }

    public CashReconcilePaymentSummary(UUID paymentSummaryId) {
        this.paymentSummaryId = paymentSummaryId;
    }

    public UUID getPaymentSummaryId() {
        return paymentSummaryId;
    }

    public void setPaymentSummaryId(UUID paymentSummaryId) {
        this.paymentSummaryId = paymentSummaryId;
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

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public UUID getId() {
        return getPaymentSummaryId();
    }

    @Override
    public void setId(UUID id) {
        setPaymentSummaryId(id);
    }

    @Override
    public UUID getParentId() {
        if(cashReconcile != null) {
            return cashReconcile.getDocumentId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(cashReconcile);
        sb.append(':').append(paymentType);
        sb.append(':').append(currency);

        return sb.toString();
    }
}
