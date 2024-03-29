package com.cosmos.acacia.crm.data.accounting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.document.BusinessDocument;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Person;

/**
 * 
 * Created	:	23.04.2009
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "cash_reconcile", catalog = "acacia", schema = "public")
@DiscriminatorValue(value = BusinessDocument.CASH_RECONCILE)
@PrimaryKeyJoinColumn(name="cash_reconcile_id", referencedColumnName="document_id")
@NamedQueries({
    @NamedQuery(
        name = CashReconcile.NQ_FIND_ALL,
        query = "SELECT t FROM CashReconcile t" +
                " where" +
                "  t.publisherBranch = :publisherBranch " +
                " order by t.documentNumber"
    ),
    @NamedQuery(
        name = CashReconcile.NQ_FIND_PREVIOUS,
        query = "SELECT t FROM CashReconcile t" +
                " where" +
                "  t.publisherBranch = :publisherBranch " +
                "  and t.documentNumber < :documentNumber" +
                " order by t.documentNumber desc"
    ),
    @NamedQuery(
        name = CashReconcile.NQ_MAX_NUMBER,
        query = "select max(t.documentNumber) from CashReconcile t" +
                " where" +
                "  t.publisherBranch = :publisherBranch"
    )
})
public class CashReconcile extends BusinessDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "CashReconcile";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_PREVIOUS = CLASS_NAME + ".findPrevious";
    public static final String NQ_MAX_NUMBER = "NQ_MAX_NUMBER";

    @JoinColumn(name = "cashier_id", referencedColumnName = "person_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Cashier", propertyValidator=@PropertyValidator(required=true), customDisplay="${cashier.displayName}")
    private Person cashier;
    
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    private DbResource currency;

    @Column(name = "initial_cash_balance", precision = 19, scale = 4)
    @Property(title="Initial cash balance", editable=false)
    private BigDecimal initialCashBalance;
    
    @Column(name = "initial_bank_balance", precision = 19, scale = 4)
    @Property(title="Initial bank balance", editable=false)
    private BigDecimal initialBankBalance;
    
    @Column(name = "period_cash_revenue", precision = 19, scale = 4)
    @Property(title="Period Cash Revenue", editable=false)
    private BigDecimal periodCashRevenue;
    
    @Column(name = "period_bank_revenue", precision = 19, scale = 4)
    @Property(title="Period Bank Revenue", editable=false)
    private BigDecimal periodBankRevenue;
    
    @Column(name = "period_cash_expenses", precision = 19, scale = 4)
    @Property(title="Period Cash Expenses", editable=false)
    private BigDecimal periodCashExpenses;
    
    @Column(name = "period_bank_expenses", precision = 19, scale = 4)
    @Property(title="Period Bank Expenses", editable=false)
    private BigDecimal periodBankExpenses;
    
    
    public CashReconcile() {
        super(CASH_RECONCILE);
    }

    public CashReconcile(UUID invoiceId) {
        super(CASH_RECONCILE, invoiceId);
    }

    public Person getCashier() {
        return cashier;
    }

    public void setCashier(Person cashier) {
        this.cashier = cashier;
    }

    public BigDecimal getInitialCashBalance() {
        return initialCashBalance;
    }

    public void setInitialCashBalance(BigDecimal initialCashBalance) {
        this.initialCashBalance = initialCashBalance;
    }

    public BigDecimal getInitialBankBalance() {
        return initialBankBalance;
    }

    public void setInitialBankBalance(BigDecimal initialBankBalance) {
        this.initialBankBalance = initialBankBalance;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }
    
    public BigDecimal getInitialBalance(){
        BigDecimal result = BigDecimal.ZERO;
        if ( getInitialBankBalance()!=null )
            result = result.add(getInitialBankBalance());
        if ( getInitialCashBalance()!=null )
            result = result.add(getInitialCashBalance());
        return result;
    }

    public BigDecimal getEndBalance(){
        BigDecimal result = BigDecimal.ZERO;
        if ( getEndBankBalance()!=null )
            result = result.add(getEndBankBalance());
        if ( getEndCashBalance()!=null )
            result = result.add(getEndCashBalance());
        return result;
    }
    
    public BigDecimal getPeriodRevenue(){
        BigDecimal result = BigDecimal.ZERO;
        if ( getPeriodCashRevenue()!=null )
            result = result.add(getPeriodCashRevenue());
        if ( getPeriodBankRevenue()!=null )
            result = result.add(getPeriodBankRevenue());
        return result;
    }
    
    public BigDecimal getPeriodExpenses(){
        BigDecimal result = BigDecimal.ZERO;
        if ( getPeriodCashExpenses()!=null )
            result = result.add(getPeriodCashExpenses());
        if ( getPeriodBankExpenses()!=null )
            result = result.add(getPeriodBankExpenses());
        return result;
    }

    public BigDecimal getEndCashBalance() {
        BigDecimal result = getInitialCashBalance();
        if ( getPeriodCashExpenses()!=null )
            result = result.subtract(getPeriodCashExpenses());
        if ( getPeriodCashRevenue()!=null )
            result = result.add(getPeriodCashRevenue());
        return result;
    }

    public BigDecimal getEndBankBalance() {
        BigDecimal result = getInitialBankBalance();
        if ( getPeriodBankExpenses()!=null )
            result = result.subtract(getPeriodBankExpenses());
        if ( getPeriodBankRevenue()!=null )
            result = result.add(getPeriodBankRevenue());
        return result;
    }

    public BigDecimal getPeriodCashExpenses() {
        return periodCashExpenses;
    }

    public void setPeriodCashExpenses(BigDecimal periodCashExpenses) {
        this.periodCashExpenses = periodCashExpenses;
    }

    public BigDecimal getPeriodBankExpenses() {
        return periodBankExpenses;
    }

    public void setPeriodBankExpenses(BigDecimal periodBankExpenses) {
        this.periodBankExpenses = periodBankExpenses;
    }

    public BigDecimal getPeriodBankRevenue() {
        return periodBankRevenue;
    }

    public void setPeriodBankRevenue(BigDecimal periodBankRevenue) {
        this.periodBankRevenue = periodBankRevenue;
    }

    public BigDecimal getPeriodCashRevenue() {
        return periodCashRevenue;
    }

    public void setPeriodCashRevenue(BigDecimal periodCashRevenue) {
        this.periodCashRevenue = periodCashRevenue;
    }
    
    public List<CashReconcilePaymentSummary> getExpensesPayments(){
        return getPaymentSummaries(false);
    }
    
    public List<CashReconcilePaymentSummary> getRevenuePayments(){
        return getPaymentSummaries(true);
    }
    
    private List<CashReconcilePaymentSummary> getPaymentSummaries(boolean revenue) {
        throw new UnsupportedOperationException("ToDo");
        /*Set<CashReconcilePaymentSummary> payments = new HashSet<CashReconcilePaymentSummary>(getPayments());
        if ( payments==null )
            return new ArrayList<CashReconcilePaymentSummary>();
        for (Iterator iterator = payments.iterator(); iterator.hasNext();) {
            CashReconcilePaymentSummary payment = (CashReconcilePaymentSummary) iterator.next();
            BigDecimal paymentAmount = payment.getAmount();
            int compare = paymentAmount.compareTo(BigDecimal.ZERO);
            boolean positive = compare>0;
            if ( revenue && !positive )
                iterator.remove();
            else if ( !revenue && positive )
                iterator.remove();
        }
        ArrayList<CashReconcilePaymentSummary> result = new ArrayList<CashReconcilePaymentSummary>(payments);
        Collections.sort(result, new Comparator<CashReconcilePaymentSummary>() {
            @Override
            public int compare(CashReconcilePaymentSummary o1, CashReconcilePaymentSummary o2) {
                int result = o1.getPaymentType().getEnumName().compareTo(o2.getPaymentType().getEnumName());
                if ( result==0 ){
                    o1.getCurrency().getEnumName().compareTo(o2.getCurrency().getEnumName());
                }
                return result;
            }
        });
        return result;*/
    }
}
