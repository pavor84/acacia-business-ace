/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.invoice.InvoiceListLocal;
import com.cosmos.acacia.crm.bl.payment.CustomerPaymentLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;

/**
 * 
 * Created	:	26.07.2008
 * @author	Petar Milev
 *
 */
@Stateless
public class BusinessPartnersListBean implements BusinessPartnersListLocal, BusinessPartnersListRemote{

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private OrganizationsListLocal organizationsListLocal;
    
    @EJB
    private PersonsListLocal personsListLocal;

    @EJB
    private AcaciaSessionLocal acaciaSession;
    
    @EJB 
    private LocationsListLocal locationsList;
    
    @EJB 
    private AddressesListLocal addressesList;
    
    @EJB
    private InvoiceListLocal invoiceListLocal;

    @EJB
    private CustomerPaymentLocal customerPaymentMananger;

    @Override
    public void deleteBusinessPartner(BusinessPartner businessPartner) {
        if ( businessPartner instanceof Organization )
            organizationsListLocal.deleteOrganization((Organization) businessPartner);
        else if ( businessPartner instanceof Person )
            personsListLocal.deletePerson((Person) businessPartner);
        else
            throw new IllegalArgumentException("Not recognized type of BusinessPartner (or null): "+businessPartner);
    }

    @Override
    public EntityProperties getListingEntityProperties() {
        EntityProperties result = esm.getEntityProperties(BusinessPartner.class);
        
        PropertyDetails pd = null;
        
        //partner name
        addPropertyDetails(0, "displayName", "Partner Name", null, result);
        
        //partner type
        addPropertyDetails(10, "class", "Partner Type", "${class.simpleName}", result);
        
        //organization type
        pd = esm.getPropertyDetails(Organization.class, "organizationType", 20);
        result.addPropertyDetails(pd);
        
        //gender
        pd = esm.getPropertyDetails(Person.class, "gender", 30);
        result.addPropertyDetails(pd);
   
        //administration address
        pd = esm.getPropertyDetails(Organization.class, "administrationAddress", 40);
        result.addPropertyDetails(pd);
        
        //unique code
        addPropertyDetails(50, "uniqueCode", "Unique Code", null, result);
        
        //birth/registered
        addPropertyDetails(60, "birthOrRegistration", "Birth/Registration", null, result);
        
        //vat
        pd = esm.getPropertyDetails(Organization.class, "vatNumber", 70);
        result.addPropertyDetails(pd);    
        
        return result;
    }

    private void addPropertyDetails(int orderPosition, String propertyName, String columnName,
                          String customELDisplay, EntityProperties entityProperties) {
       PropertyDetails pd = new PropertyDetails(propertyName, columnName, null);
       pd.setCustomDisplay(customELDisplay);
       pd.setOrderPosition(orderPosition);
       entityProperties.addPropertyDetails(pd);
   }


    @Override
    public List<BusinessPartner> getBusinessPartners(UUID parentDataObjectId) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId is required!");
        Query q = em.createNamedQuery("BusinessPartner.findForParentAndDeletedById");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<BusinessPartner> result = q.getResultList();
        return new ArrayList<BusinessPartner>(result);
    }

    @Override
    public List<BusinessPartner> getBusinessPartners(Classifier classifier) {
        Query q;
        if(classifier != null) {
            q = em.createNamedQuery("BusinessPartner.findByClassifier");
            q.setParameter("classifierId", classifier.getId());
        } else {
            q = em.createNamedQuery("BusinessPartner.getAll");
        }
        q.setParameter("parentId", acaciaSession.getOrganization().getId());
        q.setParameter("deleted", false);
        
        List<BusinessPartner> result = q.getResultList();
        return new ArrayList<BusinessPartner>(result);
    }

    @Override
    public List<ContactPerson> getContactPersons(BusinessPartner businessPartner) {
        if ( businessPartner == null )
            throw new IllegalArgumentException("not null businessPartner parameter allowed!");
        
        Set<ContactPerson> result = new HashSet<ContactPerson>();
        
        List<Address> addrs = locationsList.getAddresses(businessPartner.getPartnerId());
        for (Address address : addrs) {
            List<ContactPerson> cPersons = addressesList.getContactPersons(address.getAddressId());
            result.addAll(cPersons);
        }
        
        return new ArrayList<ContactPerson>(result);
    }

    @Override
    public Map<BusinessPartner, BigDecimal> getLiabilityCustomers() {
        
        Map<BusinessPartner, BigDecimal> balances = getCustomersBalance();
        Map<BusinessPartner, BigDecimal> result = new HashMap<BusinessPartner, BigDecimal>();
        for (Map.Entry<BusinessPartner, BigDecimal> entry : balances.entrySet()) {
            if ( BigDecimal.ZERO.compareTo(entry.getValue())<0 ){
                result.put(entry.getKey(), entry.getValue());
            }
        }
        
        return result;
    }
    
    private Map<BusinessPartner, BigDecimal> getCustomersBalance(){
        Map<BusinessPartner, BigDecimal> result = new LinkedHashMap<BusinessPartner, BigDecimal>();
        
        //invoices
        List<Invoice> confirmedInvoices = invoiceListLocal.getConfirmedDocuments();
        for (Invoice invoice : confirmedInvoices) {
            BigDecimal current = invoice.getTotalValue();
            BigDecimal total = result.get(invoice.getRecipient());
            if ( total==null )
                total = BigDecimal.ZERO;
            if ( InvoiceType.CretidNoteInvoice.equals(invoice.getInvoiceType().getEnumValue()) )
                total = total.subtract(current);
            else
                total = total.add(current);
            result.put(invoice.getRecipient(), total);
        }
        
        //payments
        List<CustomerPayment> confirmedPayments = customerPaymentMananger.getConfirmedDocuments();
        for (CustomerPayment payment : confirmedPayments) {
            BigDecimal current = payment.getAmount();
            BigDecimal total = result.get(payment.getCustomer());
            if ( total==null )
                total = BigDecimal.ZERO;
            if ( payment.getPaymentReturn() )
                total = total.add(current);
            else
                total = total.subtract(current);
            result.put(payment.getCustomer(), total);
        }
        
        return result;
    }
    
    @Override
    public Map<BusinessPartner, BigDecimal> getPrepaidCustomers() {
        Map<BusinessPartner, BigDecimal> balances = getCustomersBalance();
        Map<BusinessPartner, BigDecimal> result = new HashMap<BusinessPartner, BigDecimal>();
        for (Map.Entry<BusinessPartner, BigDecimal> entry : balances.entrySet()) {
            if ( BigDecimal.ZERO.compareTo(entry.getValue())>0 ){
                result.put(entry.getKey(), entry.getValue());
            }
        }
        
        return result;
    }
}
