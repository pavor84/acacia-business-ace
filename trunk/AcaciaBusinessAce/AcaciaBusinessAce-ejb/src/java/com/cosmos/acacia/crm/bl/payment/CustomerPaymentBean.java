package com.cosmos.acacia.crm.bl.payment;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.DocumentNumberLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.data.CustomerPaymentMatch;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.CustomerPaymentStatus;
import com.cosmos.acacia.crm.enums.CustomerPaymentType;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	06.03.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class CustomerPaymentBean implements CustomerPaymentLocal, CustomerPaymentRemote{
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private AcaciaSessionLocal acaciaSession;
    
    @EJB
    private InvoiceListRemote invoiceManager;
    
    @EJB
    private DocumentNumberLocal documentNumberLocal;
    
    public EntityProperties getListingEntityProperties() {

        EntityProperties entityProperties = esm.getEntityProperties(CustomerPayment.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        entityProperties.removePropertyDetails("branch");
        entityProperties.removePropertyDetails("customerContact");
        entityProperties.removePropertyDetails("status");
        entityProperties.removePropertyDetails("matchedAmount");
        entityProperties.removePropertyDetails("creator");
        entityProperties.removePropertyDetails("completor");
        entityProperties.removePropertyDetails("paymentReturn");
        entityProperties.removePropertyDetails("transactionFee");
        entityProperties.removePropertyDetails("description");
        
        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    public List<CustomerPayment> listCustomerPayments(UUID parentDataObjectId) {
        if (parentDataObjectId == null)
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("CustomerPayment.findForParent");
        q.setParameter("parentDataObjectId", parentDataObjectId);

        List<CustomerPayment> result = q.getResultList();
        return result;
    }

    public void deleteCustomerPayment(CustomerPayment customerPayment) {
        if (customerPayment == null)
            throw new IllegalArgumentException("null: 'CustomerPayment'");
        esm.remove(em, customerPayment);
    }

    public CustomerPayment newCustomerPayment(UUID parentDataObjectId) {
        CustomerPayment c = new CustomerPayment();
        c.setParentId(parentDataObjectId);
        c.setCurrency(Currency.BGN.getDbResource());
        c.setBranch(acaciaSession.getBranch());
        c.setStatus(CustomerPaymentStatus.Open.getDbResource());
        c.setPaymentReturn(Boolean.FALSE);
        c.setTransactionDate(new Date());
        c.setPaymentType(CustomerPaymentType.Bank.getDbResource());
        
        return c;
    }

    public EntityProperties getDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(CustomerPayment.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    public CustomerPayment saveCustomerPayment(CustomerPayment entity) {
        if( entity.getCreationTime()==null ){
            entity.setCreationTime(new Date());
            Person creator = acaciaSession.getPerson();
            entity.setCreator(creator);
        }
        
        if ( CustomerPaymentStatus.Completed.equals(entity.getStatus().getEnumValue()) && 
                entity.getCompletionTime()==null ){
            entity.setCompletionTime(new Date());
            Person completor = acaciaSession.getPerson();
            entity.setCompletor(completor);
        }
        
        if ( !CustomerPaymentType.Cash.equals(entity.getPaymentType().getEnumValue())){
            entity.setCashier(null);
        }
        if ( !CustomerPaymentType.Bank.equals(entity.getPaymentType().getEnumValue())){
            entity.setPaymentAccount(null);
        }
        
        esm.persist(em, entity);

        return entity;
    }

    @Override
    public CustomerPayment completeCustomerPayment(CustomerPayment entity) {
        entity.setStatus(CustomerPaymentStatus.Completed.getDbResource());
        documentNumberLocal.setDocumentNumber(entity);
        return saveCustomerPayment(entity);
    }

    @Override
    public List<CustomerPayment> getConfirmedDocuments() {
        Query q = em.createNamedQuery("CustomerPayment.getConfirmedPayments");
        q.setParameter("completed", CustomerPaymentStatus.Completed.getDbResource());
        q.setParameter("parentId", acaciaSession.getOrganization().getId());
        
        List<CustomerPayment> result = q.getResultList();
        return result;
    }

    @Override
    public List<CustomerPayment> getPendingPayments(Boolean includePartlyMatched,
                                                    Boolean includeUnmatched) {
        List<CustomerPayment> result = new ArrayList<CustomerPayment>();
        if ( includePartlyMatched ){
            List<CustomerPayment> partlyMatched = (List<CustomerPayment>)
                AcaciaUtils.getResultList(em, "CustomerPayment.getPartlyMatched", 
                "branch", acaciaSession.getBranch(), "completed", CustomerPaymentStatus.Completed.getDbResource());
            result.addAll(partlyMatched);
                
        }
        if ( includeUnmatched ){
            List<CustomerPayment> unmatched = (List<CustomerPayment>)
            AcaciaUtils.getResultList(em, "CustomerPayment.getUnmatched", 
                "branch", acaciaSession.getBranch(), "completed", CustomerPaymentStatus.Completed.getDbResource());
            result.addAll(unmatched);
        }
        return result;
    }

    @Override
    public CustomerPaymentMatch matchPayment(CustomerPayment payment, Invoice invoice,
                                             BigDecimal matchAmount) {
        BigDecimal maxAmount = invoice.getDueAmount();
        if ( maxAmount.compareTo(payment.getUnmatchedAmount())>0 ){
            maxAmount = payment.getUnmatchedAmount();
        }
        
        if (matchAmount.compareTo(maxAmount)>0)
            throw new IllegalArgumentException("Max amount to match is: "+maxAmount+", you supplied: "+matchAmount);
        else if ( matchAmount.compareTo(BigDecimal.ZERO)<0 )
            throw new IllegalArgumentException("Cannot use negative match amount!");
        
        //setup payment
        BigDecimal paymentMatchAmt = payment.getMatchedAmount();
        if ( paymentMatchAmt==null )
            paymentMatchAmt = BigDecimal.ZERO;
        paymentMatchAmt = paymentMatchAmt.add(matchAmount);
        payment.setMatchedAmount(paymentMatchAmt);
        
        //setup invoice
        BigDecimal invoicePaidAmt = invoice.getPaidAmount();
        if ( invoicePaidAmt==null )
            invoicePaidAmt = BigDecimal.ZERO;
        invoicePaidAmt = invoicePaidAmt.add(matchAmount);
        invoice.setPaidAmount(invoicePaidAmt);
        if ( invoice.getPaidAmount().compareTo(invoice.getTotalValue())==0 ){
            invoice.setCompletionDate(new Date());
            invoice.setStatus(InvoiceStatus.Paid.getDbResource());
        }
        
        //save payment and invoice
        payment = saveCustomerPayment(payment);
        invoice = invoiceManager.saveInvoice(invoice);
        
        //create payment match
        CustomerPaymentMatch match = new CustomerPaymentMatch();
        match.setCreationTime(new Date());
        match.setMatchNumber(getNextMatchNumber(invoice));
        match.setAmount(matchAmount);
        match.setCustomerPayment(payment);
        match.setInvoice(invoice);
        esm.persist(em, match);

        return match;
    }

    private Integer getNextMatchNumber(Invoice invoice) {
        Number maxNumber = (Number) 
            AcaciaUtils.getSingleResult(em, "CustomerPaymentMatch.maxNumber", "invoice", invoice);
        if ( maxNumber==null )
            return 1;
        else
            return maxNumber.intValue()+1;
    }

    @Override
    public List<CustomerPaymentMatch> getPaymentMatchList(Invoice invoice) {
        List<CustomerPaymentMatch> result = (List<CustomerPaymentMatch>) 
            AcaciaUtils.getResultList(em, "CustomerPaymentMatch.getForInvoice", "invoice", invoice);
        return result;
    }
}
