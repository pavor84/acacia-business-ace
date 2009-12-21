package com.cosmos.acacia.crm.bl.cash;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.text.DateFormatter;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.accounting.BanknoteQuantity;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.accounting.CashReconcile;
import com.cosmos.acacia.crm.data.accounting.CashReconcilePaymentSummary;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.CustomerPaymentStatus;
import com.cosmos.acacia.crm.enums.CustomerPaymentType;
import com.cosmos.acacia.crm.enums.DocumentStatus;
import com.cosmos.acacia.crm.enums.DocumentType;
import com.cosmos.acacia.entity.AcaciaEntityAttributes;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.EntityProperty;

/**
 * 
 * Created	:	28.04.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class CashReconcileBean implements CashReconcileLocal, CashReconcileRemote{
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private BanknoteQuantityLocal banknoteQuantityManager;
    
    @EJB
    private AcaciaSessionLocal session;
    
    public EntityProperties getListingEntityProperties() {

        EntityProperties ep = esm.getEntityProperties(CashReconcile.class);
        ep.removeEntityProperty("initialCashBalance");
        ep.removeEntityProperty("initialBankBalance");
        ep.removeEntityProperty("periodBankRevenue");
        ep.removeEntityProperty("periodCashExpenses");
        ep.removeEntityProperty("periodBankExpenses");
        ep.removeEntityProperty("periodCashRevenue");
        ep.removeEntityProperty("documentStatus");
        ep.removeEntityProperty("publisher");
        ep.removeEntityProperty("publisherBranch");
        //re-order the columns and add missing property details
        ep.addEntityProperty(EntityProperty.createEntityProperty("documentNumber", "Document Number", Long.class.getName(), 10, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("documentDate", "Document Date", Date.class.getName(), 20, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.setOrderPosition("cashier", 30);
        EntityProperty officerInCharge = EntityProperty.createEntityProperty("publisherOfficer", "Officer in Charge", Person.class.getName(), 40, AcaciaEntityAttributes.getEntityAttributesMap());
        officerInCharge.setCustomDisplay("${publisherOfficer.displayName}");
        ep.addEntityProperty(officerInCharge);
        ep.setOrderPosition("currency", 50);
        ep.addEntityProperty(EntityProperty.createEntityProperty("initialBalance", "Initial Balance", BigDecimal.class.getName(), 60, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("periodExpenses", "Period Expenses", BigDecimal.class.getName(), 70, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("periodRevenue", "Period Revenue", BigDecimal.class.getName(), 80, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("endBalance", "End Balance", BigDecimal.class.getName(), 90, AcaciaEntityAttributes.getEntityAttributesMap()));

        ep.getEntityProperty("documentDate").setFormatter(new DateFormatter(AcaciaUtils.getShortTimeFormat()));

        return ep;
    }

    @SuppressWarnings("unchecked")
    public List<CashReconcile> listCashReconciles(UUID parentId) {
        if (parentId == null)
            throw new IllegalArgumentException("parentId can't be null");
        
        Query q = em.createNamedQuery(CashReconcile.NQ_FIND_ALL);
        UUID branchId = session.getBranch().getId();
        q.setParameter("branchId", branchId);

        List<CashReconcile> result = q.getResultList();
        return result;
    }

    public void deleteCashReconcile(CashReconcile cashReconcile) {
        if (cashReconcile == null)
            throw new IllegalArgumentException("null: 'CashReconcile'");
        esm.remove(em, cashReconcile);
    }

    public CashReconcile newCashReconcile(UUID parentDataObjectId) {
        CashReconcile c = esm.newBusinessDocument(DocumentType.CashReconcile);
        c.setParentId(parentDataObjectId);
        c.setCurrency(session.getOrganization().getDefaultCurrency());
        setInitialAmounts(c);
        return c;
    }

    private void setInitialAmounts(CashReconcile c) {
        CashReconcile previous = getPreivousCashReconcile(c);
        
        //initial balances
        BigDecimal initialCash = BigDecimal.ZERO;
        BigDecimal initialBank = BigDecimal.ZERO;
        if ( previous!=null ){
            initialCash = previous.getEndCashBalance();
            initialBank = previous.getEndBankBalance();
        }
        c.setInitialCashBalance(initialCash);
        c.setInitialBankBalance(initialBank);
        
        //period balances
        List<CashReconcilePaymentSummary> paymentSummaries = createPaymentSummaries(previous, c);
        //c.setPayments(new HashSet<CashReconcilePaymentSummary>(paymentSummaries));
        setPeriodAmounts(c, paymentSummaries);
    }

    private void setPeriodAmounts(CashReconcile c,
                                  List<CashReconcilePaymentSummary> paymentSummaries) {
        BigDecimal expensesCash = BigDecimal.ZERO;
        BigDecimal expensesBank = BigDecimal.ZERO;
        BigDecimal revenueCash = BigDecimal.ZERO;
        BigDecimal revenueBank = BigDecimal.ZERO;
        
        for (CashReconcilePaymentSummary paymentSummary : paymentSummaries) {
            CustomerPaymentType type = (CustomerPaymentType) paymentSummary.getPaymentType().getEnumValue();
            
            BigDecimal convertedAmount = convertAmount(Currency.BGN, (Currency) paymentSummary.getCurrency().getEnumValue(), 
                paymentSummary.getAmount());
            if ( CustomerPaymentType.Cash.equals(type) )
                revenueCash = revenueCash.add(convertedAmount);
            else
                revenueBank = revenueBank.add(convertedAmount);
        }
        
        c.setPeriodBankExpenses(expensesBank);
        c.setPeriodCashExpenses(expensesCash);
        c.setPeriodBankRevenue(revenueBank);
        c.setPeriodCashRevenue(revenueCash);
    }

    private BigDecimal convertAmount(Currency targetCurrency, Currency sourceCurrency, BigDecimal amount) {
        return Currency.convertAmount(targetCurrency, sourceCurrency, amount);
    }

    private List<CashReconcilePaymentSummary> createPaymentSummaries(CashReconcile previous,
                                                                  CashReconcile current) {
        Date start = new Date(0);
        if ( previous!=null )
            start = previous.getDocumentDate();
        List<CustomerPayment> customerPayments = 
            (List<CustomerPayment>) AcaciaUtils.getResultList(em, "CustomerPayment.confirmedForPeriod",
            "branch", current.getPublisherBranch(), "completed", CustomerPaymentStatus.Completed.getDbResource(),
            "start", start, "end", current.getCreationTime());
        
        Map<Currency, CashReconcilePaymentSummary> bank = new HashMap<Currency, CashReconcilePaymentSummary>();
        Map<Currency, CashReconcilePaymentSummary> cash = new HashMap<Currency, CashReconcilePaymentSummary>();
        for (CustomerPayment customerPayment : customerPayments) {
            Currency curr = (Currency) customerPayment.getCurrency().getEnumValue();
            CustomerPaymentType type = (CustomerPaymentType) customerPayment.getPaymentType().getEnumValue();
            
            Map<Currency, CashReconcilePaymentSummary> sumMap = cash;
            if ( CustomerPaymentType.Bank.equals(type) )
                sumMap = bank;
            
            CashReconcilePaymentSummary summary = sumMap.get(curr);
            if (summary == null ){
                summary = new CashReconcilePaymentSummary();
                summary.setPaymentType(type.getDbResource());
                summary.setCurrency(curr.getDbResource());
                summary.setCashReconcile(current);
                summary.setAmount(BigDecimal.ZERO);
                sumMap.put(curr, summary);
            }
            
            summary.setAmount(summary.getAmount().add(customerPayment.getAmount()));
        }
        
        List<CashReconcilePaymentSummary> result = new ArrayList<CashReconcilePaymentSummary>();
        result.addAll(bank.values());
        result.addAll(cash.values());
        return result;
    }

    private CashReconcile getPreivousCashReconcile(CashReconcile c) {
        Query q = em.createNamedQuery(CashReconcile.NQ_FIND_PREVIOUS);
        q.setParameter("publisherBranch", c.getPublisherBranch());
        q.setParameter("documentNumber", c.getDocumentNumber());
        List<CashReconcile> previous;
        if((previous = q.getResultList()) == null || previous.size() == 0) {
            return null;
        }

        return previous.get(0);
    }

    @Override
    public EntityProperties getDetailEntityProperties() {
        EntityProperties ep = esm.getEntityProperties(CashReconcile.class);
        ep.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        
        ep.addEntityProperty(EntityProperty.createEntityProperty("documentNumber", "Document Number", Long.class.getName(), 10, false, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("documentDate", "Document Date", Date.class.getName(), 20, false, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("publisherOfficer", "Officer in Charge", Person.class.getName(), 40, false, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("initialBalance", "Initial Balance", BigDecimal.class.getName(), 60, false, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("periodExpenses", "Period Expenses", BigDecimal.class.getName(), 70, false, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("periodRevenue", "Period Revenue", BigDecimal.class.getName(), 80, false, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("endBalance", "End Balance", BigDecimal.class.getName(), 90, false, AcaciaEntityAttributes.getEntityAttributesMap()));
        ep.addEntityProperty(EntityProperty.createEntityProperty("notes", "Notes", String.class.getName(), AcaciaEntityAttributes.getEntityAttributesMap()));
        
        return ep;
    }

    public CashReconcile saveCashReconcile(CashReconcile entity) {
//        for (CashReconcilePaymentSummary summary : entity.getPayments()) {
//            esm.persist(em, summary);
//        }
        
        esm.persist(em, entity);

        return entity;
    }

    @Override
    public CashReconcile completeCashReconcile(CashReconcile entity) {
        entity.setDocumentStatus(DocumentStatus.Completed.getDbResource());
        entity.setDocumentDate(new Date());
        //esm.setDocumentNumber(em, entity);
        //documentNumberLocal.setDocumentNumber(entity);
        esm.setDocumentNumber(em, entity);
        return saveCashReconcile(entity);
    }

    @Override
    public List<EndBalance> getEndBalances(CashReconcile entity) {
        List<BanknoteQuantity> banknoteQuantities = banknoteQuantityManager.listBanknoteQuantitys(entity.getId());
        
        Currency defaultCurrency = (Currency) entity.getCurrency().getEnumValue();
        TreeMap<Currency, EndBalance> balances = new TreeMap<Currency, EndBalance>();
        for (BanknoteQuantity banknoteQuantity : banknoteQuantities) {
            Currency key = (Currency) banknoteQuantity.getCurrencyNominal().getCurrency().getEnumValue();
            EndBalance balance = balances.get(key);
            if ( balance==null ){
                balance = new EndBalance(banknoteQuantity.getCurrencyNominal().getCurrency(), BigDecimal.ZERO, BigDecimal.ZERO);
                balances.put(key, balance);
            }
            
            balance.setAmount(balance.getAmount().add(banknoteQuantity.getAmount()));
            balance.setAmountDefCurrency(balance.getAmountDefCurrency().add(banknoteQuantity.getAmount(defaultCurrency)));
        }
        
        return new ArrayList<EndBalance>(balances.values());
    }

    @Override
    public EntityProperties getPaymentSummaryListDetails() {
        EntityProperties p = esm.getEntityProperties(CashReconcilePaymentSummary.class);
        return p;
    }

    @Override
    public Set<CashReconcilePaymentSummary> getPaymentSummaries(CashReconcile entity) {
        throw new UnsupportedOperationException("ToDo");
//        List<CashReconcilePaymentSummary> result = (List<CashReconcilePaymentSummary>)
//            AcaciaUtils.getResultList(em, CashReconcilePaymentSummary.NQ_FIND_FOR_RECONCILE,
//                "cashReconcile", entity);
//        return new HashSet<CashReconcilePaymentSummary>(result);
    }
}
