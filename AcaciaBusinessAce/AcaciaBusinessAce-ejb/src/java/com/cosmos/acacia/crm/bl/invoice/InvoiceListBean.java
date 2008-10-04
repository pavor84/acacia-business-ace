package com.cosmos.acacia.crm.bl.invoice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.contactbook.AddressesListLocal;
import com.cosmos.acacia.crm.bl.contactbook.LocationsListLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.impl.WarehouseListLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.data.WarehouseProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.DeliveryType;
import com.cosmos.acacia.crm.enums.DocumentDeliveryMethod;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.PaymentTerm;
import com.cosmos.acacia.crm.enums.PaymentType;
import com.cosmos.acacia.crm.enums.TransportationMethod;
import com.cosmos.acacia.crm.enums.VatCondition;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	10.09.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * Implement business logic behind the Invoices Use Cases
 */
@Stateless
public class InvoiceListBean implements InvoiceListLocal, InvoiceListRemote {
    @PersistenceContext
    private EntityManager em; 
 
    @EJB 
    private EntityStoreManagerLocal esm;
    
    @EJB
    private AcaciaSessionLocal acaciaSession;
    
    @EJB 
    private LocationsListLocal locationsList;
    
    @EJB 
    private AddressesListLocal addressesList;
    
    @EJB
    private WarehouseListLocal warehouseListLocal;
    
    public EntityProperties getListingEntityProperties() {
        
        EntityProperties entityProperties = esm.getEntityProperties(Invoice.class);
        
        //let's keep the columns in the table a reasonable count, so remove all not-crucial information
        entityProperties.removePropertyDetails("branchName");
        entityProperties.removePropertyDetails("branch");
        entityProperties.removePropertyDetails("recipientName");
        entityProperties.removePropertyDetails("recipientContactName");
        entityProperties.removePropertyDetails("invoiceType");
        entityProperties.removePropertyDetails("creatorName");
        entityProperties.removePropertyDetails("documentDeliveryMethod");
        entityProperties.removePropertyDetails("transportationMethod");
        entityProperties.removePropertyDetails("discountAmount");
        entityProperties.removePropertyDetails("exciseDutyValue");
        entityProperties.removePropertyDetails("paymentTerms");
        entityProperties.removePropertyDetails("singlePayAmount");
        entityProperties.removePropertyDetails("paymentsCount");
        entityProperties.removePropertyDetails("daysBetweenPayments");
        entityProperties.removePropertyDetails("deliveryType");
        entityProperties.removePropertyDetails("senderName");
        entityProperties.removePropertyDetails("sender");
        entityProperties.removePropertyDetails("shipDateFrom");
        entityProperties.removePropertyDetails("shipDateTo");
        entityProperties.removePropertyDetails("notes");
        entityProperties.removePropertyDetails("vatConditionNotes");
        
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
        
    }

    @SuppressWarnings("unchecked")
    public List<Invoice> listInvoices(BigInteger parentDataObjectId, Address branch) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("Invoice.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        q.setParameter("branch", branch);
        
        List<Invoice> result = q.getResultList();
        return result;
    }

    
    public void deleteInvoice(Invoice invoice) {
        if ( invoice==null )
            throw new IllegalArgumentException("null: 'Invoice'");
        esm.remove(em, invoice);
    }

    
    public Invoice newInvoice(BigInteger parentDataObjectId) {
        
        Invoice c = new Invoice();
        
        Address branch = acaciaSession.getBranch();
        
        c.setParentId(parentDataObjectId);
        c.setBranch(branch);
        c.setBranchName(branch.getAddressName());
        c.setInvoiceType(InvoiceType.VatInvoice.getDbResource());
        c.setCurrency(Currency.Leva.getDbResource());
        c.setDiscountPercent(new BigDecimal(0));
        c.setDiscountAmount(new BigDecimal(0));
        c.setTransportationPrice(new BigDecimal(0));
        c.setInvoiceSubValue(new BigDecimal(0));
        c.setDocumentDeliveryMethod(DocumentDeliveryMethod.Courier.getDbResource());
        c.setStatus(InvoiceStatus.Open.getDbResource());
        c.setTransportationMethod(TransportationMethod.Courier.getDbResource());
        c.setDeliveryType(DeliveryType.DDP.getDbResource());
        c.setVat(new BigDecimal(0));
        c.setExciseDutyValue(new BigDecimal(0));
        c.setExciseDutyPercent(new BigDecimal(0));
        c.setVatCondition(VatCondition.VatPayable.getDbResource());
        c.setPaymentTerms(PaymentTerm.InAdvance.getDbResource());
        c.setPaymentType(PaymentType.Cash.getDbResource());
        c.setPaymentDueDate(new Date());
        c.setStatus(InvoiceStatus.Open.getDbResource());
        c.setInvoiceDate(new Date());
        
        return c;
    }

    
    public EntityProperties getDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Invoice.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    
    public List<ContactPerson> getSupplierContacts(BusinessPartner supplier) {
        if ( supplier == null )
            throw new IllegalArgumentException("not null supplier please!");
        
        Set<ContactPerson> result = new HashSet<ContactPerson>();
        
        List<Address> addrs = locationsList.getAddresses(supplier.getPartnerId());
        for (Address address : addrs) {
            List<ContactPerson> cPersons = addressesList.getContactPersons(address.getAddressId());
            result.addAll(cPersons);
        }
        
        return new ArrayList<ContactPerson>(result);
    }

    
    public Invoice saveInvoice(Invoice i) {
        if ( i.getCreationTime()==null ){
            i.setCreationTime(new Date());
            Person creator = acaciaSession.getPerson();
            i.setCreator(creator);
            i.setCreatorName(creator.getDisplayName());
        }
        
        esm.persist(em, i);
        
        return i;
    }
    
    public void deleteInvoiceItem(InvoiceItem item) {
        esm.remove(em, item);
    }
    
    public EntityProperties getItemsListEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(InvoiceItem.class);
        entityProperties.removePropertyDetails("productDescription");
        entityProperties.removePropertyDetails("notes");
        entityProperties.removePropertyDetails("discountAmount");
        entityProperties.removePropertyDetails("shipDateFrom");
        entityProperties.removePropertyDetails("shipDateTo");
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    
    public List<InvoiceItem> getInvoiceItems(BigInteger parentDataObjectId) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("InvoiceItem.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<InvoiceItem> result = q.getResultList();
        return result;
    }

    
    public InvoiceItem newInvoiceItem(BigInteger parentDataObjectId){
        InvoiceItem item = new InvoiceItem();
        item.setParentId(parentDataObjectId);
        item.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        return item;
    }
    
    public InvoiceItem saveInvoiceItem(InvoiceItem item) {
        
        esm.persist(em, item);
        
        return item;
    }

    @SuppressWarnings("unchecked")
    public WarehouseProduct getWarehouseProduct(SimpleProduct product) {
        Address branch = acaciaSession.getBranch();
        if ( branch==null )
            throw new IllegalArgumentException("Branch of logged user required!");
        if ( product==null )
            throw new IllegalArgumentException("Product parameter missing!");
        
        Query q = em.createNamedQuery("WarehouseProduct.findByProductAndBranch");
        q.setParameter("branch", branch);
        q.setParameter("product", product);
        
        List result = q.getResultList();
        if ( result.isEmpty() )
            return null;
        else
            return (WarehouseProduct) q.getResultList().get(0);
    }

    
    public EntityProperties getItemDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(InvoiceItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<DbResource> getDocumentTypes() {
        return InvoiceType.getDbResources();
    }

    @Override
    public List<DbResource> getCurrencies() {
        return Currency.getDbResources();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> listInvoices(BigInteger parentDataObjectId) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("Invoice.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<Invoice> result = q.getResultList();
        return result;
    }

    @Override
    public List<ContactPerson> getRecipientContacts(BusinessPartner recipient) {
        if ( recipient == null )
            throw new IllegalArgumentException("not null recipient please!");
        
        Set<ContactPerson> result = new HashSet<ContactPerson>();
        
        List<Address> addrs = locationsList.getAddresses(recipient.getPartnerId());
        for (Address address : addrs) {
            List<ContactPerson> cPersons = addressesList.getContactPersons(address.getAddressId());
            result.addAll(cPersons);
        }
        
        return new ArrayList<ContactPerson>(result);
    }

    @Override
    public List<DbResource> getDeliveryMethods() {
        return DocumentDeliveryMethod.getDbResources();
    }

    @Override
    public List<DbResource> getTransportationMethods() {
        return TransportationMethod.getDbResources();
    }

    @Override
    public List<DbResource> getDeliveryTypes() {
        return DeliveryType.getDbResources();
    }
    
    @Override
    public List<DbResource> getVatConditions() {
        return VatCondition.getDbResources();
    }

    @Override
    public List<DbResource> getPaymentTerms() {
        return PaymentTerm.getDbResources();
    }

    @Override
    public List<DbResource> getPaymentTypes() {
        return PaymentType.getDbResources();
    }

    @Override
    public List<DbResource> getInvoiceStatuses() {
        return InvoiceStatus.getDbResources();
    }

    @Override
    public Invoice sendInvoice(Invoice entity) {
        if ( InvoiceStatus.Open.equals(entity.getStatus().getEnumValue()) ){
            setInvoiceNumber(entity);
            entity.setStatus(InvoiceStatus.Sent.getDbResource());
        }else if ( InvoiceStatus.Published.equals(entity.getStatus().getEnumValue()) ){
            entity.setStatus(InvoiceStatus.Sent.getDbResource());
        }
        
        Person person = acaciaSession.getPerson();
        entity.setSender(person);
        entity.setSenderName(person.getDisplayName());
        entity.setSentTime(new Date());
        
        return saveInvoice(entity);
    }

    private void setInvoiceNumber(Invoice entity) {
        Query q = em.createNamedQuery("Invoice.maxInvoiceNumberForBranch");
        if ( entity.getBranch()==null )
            throw new IllegalArgumentException("Invoice branch is null!");
        q.setParameter("branch", entity.getBranch());
        
        BigInteger result = (BigInteger) q.getSingleResult();
        //no orders for this warehouse
        if ( result==null ){
            Warehouse warehouse = warehouseListLocal.getWarehouseForAddress(entity.getBranch());
            if ( warehouse==null ){
                throw new IllegalStateException("No warehouse found for address: "+entity.getBranch()==null?"null":entity.getBranch().getAddressName());
            }
            
            if ( warehouse.getIndex()==null || warehouse.getIndex().equals(new Long(0))){
                throw new IllegalStateException("No warehouse index set for warehouse: "
                    +warehouse.getAddress().getAddressName()+". This is needed to generate document numbers!");
            }
            
            result = new BigInteger(""+warehouse.getIndex());
            result = result.multiply(WarehouseListLocal.DOCUMENT_INDEX_MULTIPLICATOR);
        }else{
            result = result.add(new BigInteger("1"));
        }
        
        entity.setInvoiceNumber(result);
    }

    @Override
    public Invoice publishInvoice(Invoice entity) {
        if ( !InvoiceStatus.Open.equals(entity.getStatus().getEnumValue()) )
            throw new IllegalArgumentException("The invoice should be OPEN in order to be PUBLISHED!");
        
        setInvoiceNumber(entity);
        entity.setStatus(InvoiceStatus.Published.getDbResource());
        return saveInvoice(entity);
    }

    @Override
    public void saveInvoiceItems(List<InvoiceItem> newItems) {
        if ( newItems==null )
            throw new IllegalArgumentException("newItems can't be null!");
        for (InvoiceItem item : newItems) {
            //InvoiceItem.validate(item);//TODO validate
            esm.persist(em, item);
        }
        em.flush();
    }
}
