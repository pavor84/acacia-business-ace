package com.cosmos.acacia.crm.bl.invoice;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.assembling.AssemblingLocal;
import com.cosmos.acacia.crm.bl.contactbook.AddressesListLocal;
import com.cosmos.acacia.crm.bl.contactbook.LocationsListLocal;
import com.cosmos.acacia.crm.bl.impl.DocumentNumberLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.impl.WarehouseListLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.product.ComplexProductItem;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.sales.InvoiceItem;
import com.cosmos.acacia.crm.data.sales.InvoiceItemLink;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.DeliveryStatus;
import com.cosmos.acacia.crm.enums.DeliveryType;
import com.cosmos.acacia.crm.enums.DocumentDeliveryMethod;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.PaymentTerm;
import com.cosmos.acacia.crm.enums.PaymentType;
import com.cosmos.acacia.crm.enums.TransportationMethod;
import com.cosmos.acacia.crm.enums.VatCondition;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;

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
    
    @EJB
    private AssemblingLocal assemblingLocal;
    
    @EJB
    private DocumentNumberLocal documentNumberLocal;
    
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
        entityProperties.removePropertyDetails("discountPercent");
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
        entityProperties.removePropertyDetails("completionDate");
        entityProperties.removePropertyDetails("deliveryStatus");
        entityProperties.removePropertyDetails("validTo");
        entityProperties.removePropertyDetails("attendee");
        entityProperties.removePropertyDetails("additionalTerms");
        
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
        
    }

    @SuppressWarnings("unchecked")
    public List<Invoice> listInvoices(UUID parentDataObjectId, Address branch) {
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

    
    public Invoice newInvoice(UUID parentDataObjectId) {
        Invoice c = new Invoice();
        
        Address branch = acaciaSession.getBranch();
        
        c.setParentId(parentDataObjectId);
        c.setBranch(branch);
        c.setBranchName(branch.getAddressName());
        c.setInvoiceType(InvoiceType.VatInvoice.getDbResource());
        c.setCurrency(Currency.BGN.getDbResource());
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
        c.setProformaInvoice(Boolean.FALSE);
        c.setDeliveryStatus(DeliveryStatus.NotDelivered.getDbResource());
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, 1);
        c.setValidTo(now.getTime());
        c.setPaidAmount(BigDecimal.ZERO);
        
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
    
    @Override
    public List<InvoiceItem> deleteInvoiceItem(InvoiceItem item) {
        List<InvoiceItem> copiedItems = getCopiedItemsFromTheSameDocument(item);
        //handle the case where this item is copied
        if ( copiedItems!=null && !copiedItems.isEmpty() ){
            List<InvoiceItemLink> links = getInvoiceItemLinks(copiedItems);
            //make sure we remove every link just once
            Set<InvoiceItemLink> setOfLinks = new HashSet<InvoiceItemLink>(links);
            for (InvoiceItemLink invoiceItemLink : setOfLinks) {
                em.remove(invoiceItemLink);
            }
            for (InvoiceItem copiedItem : copiedItems )
                esm.remove(em, copiedItem);
            return copiedItems;
        }
        //if this is a normal simple item
        else{
            List<InvoiceItemLink> invoiceItemLinks = getInvoiceItemLinks(item);
            for (InvoiceItemLink invoiceItemLink : invoiceItemLinks) {
                em.remove(invoiceItemLink);
            }
            esm.remove(em, item);
            List<InvoiceItem> result = new ArrayList<InvoiceItem>();
            result.add(item);
            return result;
        }
    }
    
    /**
     * Returns all links for the given items. 
     * @param copiedItems
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<InvoiceItemLink> getInvoiceItemLinks(List<InvoiceItem> items) {
        Query q = em.createNamedQuery("InvoiceItemLink.getInvoicesItemLinksForItems");
        q.setParameter("items",items);
        List<InvoiceItemLink> links = q.getResultList();
        return links;
    }

    @SuppressWarnings("unchecked")
    private List<InvoiceItem> getCopiedItemsFromTheSameDocument(InvoiceItem item) {
        Query q = em.createNamedQuery("Invoice.getCopiedItemsFromTheSameDocument");
        q.setParameter("item",item);
        
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<InvoiceItemLink> getInvoiceItemLinks(InvoiceItem item) {
        Query q = null;
        
        q = em.createNamedQuery("InvoiceItemLink.getInvoicesItemLinks");
        q.setParameter("invoiceItem",item);
        
        return q.getResultList();
    }

    public EntityProperties getItemsListEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(InvoiceItem.class);
        entityProperties.removePropertyDetails("productDescription");
        entityProperties.removePropertyDetails("notes");
        entityProperties.removePropertyDetails("discountAmount");
        entityProperties.removePropertyDetails("discountPercent");
        entityProperties.removePropertyDetails("shipDateFrom");
        entityProperties.removePropertyDetails("shipDateTo");
        
        PropertyDetails productCode = new PropertyDetails("product.codeFormatted", "Code", SimpleProduct.class.getName());
        productCode.setCustomDisplay("${product.codeFormatted}");
        productCode.setOrderPosition(25);
        productCode.setVisible(true);
        entityProperties.addPropertyDetails(productCode);
        
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    public List<InvoiceItem> getInvoiceItems(UUID parentDataObjectId) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("InvoiceItem.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<InvoiceItem> result = q.getResultList();
        return result;
    }

    
    public InvoiceItem newInvoiceItem(UUID parentDataObjectId){
        InvoiceItem item = new InvoiceItem();
        item.setParentId(parentDataObjectId);
        Invoice invoice = em.find(Invoice.class, parentDataObjectId);
        Warehouse warehouse = warehouseListLocal.getWarehouseForAddress(invoice.getBranch());
        item.setWarehouse(warehouse);
        return item;
    }
    
    public Warehouse getInvoiceWarehouse(UUID invoiceId){
        Invoice invoice = em.find(Invoice.class, invoiceId);
        return warehouseListLocal.getWarehouseForAddress(invoice.getBranch());
    }
    
    public InvoiceItem saveInvoiceItem(InvoiceItem item) {
        
        esm.persist(em, item);
        
        return item;
    }

    @SuppressWarnings("unchecked")
    public WarehouseProduct getWarehouseProduct(SimpleProduct product) {
        Address branch = acaciaSession.getBranch();
//        if ( branch==null )
//            throw new IllegalArgumentException("Branch of logged user required!");
//        if ( product==null )
//            throw new IllegalArgumentException("Product parameter missing!");
//        
//        Query q = em.createNamedQuery("WarehouseProduct.findByProductAndBranch");
//        q.setParameter("branch", branch);
//        q.setParameter("product", product);
//        
//        List result = q.getResultList();
//        if ( result.isEmpty() )
//            return null;
//        else
//            return (WarehouseProduct) q.getResultList().get(0);
        return getWarehouseProduct(branch, product);
    }

    @SuppressWarnings("unchecked")
    public WarehouseProduct getWarehouseProduct(Address branch, SimpleProduct product) {
        if ( branch ==null )
            throw new IllegalArgumentException("Branch required!");
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
    public List<Invoice> listInvoices(UUID parentDataObjectId, Boolean proform) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("Invoice.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        if ( Boolean.TRUE.equals(proform) )
            q.setParameter("proformaInvoice", Boolean.TRUE);
        else
            q.setParameter("proformaInvoice", Boolean.FALSE);
        
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
        
        Person person = acaciaSession.getPerson();
        entity.setSender(person);
        entity.setSenderName(person.getDisplayName());
        entity.setSentTime(new Date());
        
        return saveInvoice(entity);
    }

    @Override
    public void saveInvoiceItems(List<InvoiceItem> newItems) {
        if ( newItems==null )
            throw new IllegalArgumentException("newItems can't be null!");
        for (InvoiceItem item : newItems) {
            esm.persist(em, item);
        }
        em.flush();
    }

    @Override
    public void addInvoiceItems(List<InvoiceItemLink> itemLinks) {
        for (InvoiceItemLink invoiceItemLink : itemLinks) {
            saveInvoiceItem(invoiceItemLink.getInvoiceItem());
            saveInvoiceItemLink(invoiceItemLink);
        }
    }

    private void saveInvoiceItemLink(InvoiceItemLink invoiceItemLink) {
        esm.persist(em, invoiceItemLink);
    }

    @Override
    public List<?> getDocumentItems(DataObjectBean document) {
        if ( document instanceof Invoice ){
            return getInvoiceItems(document.getId());
        }
        throw new IllegalArgumentException("Cannot get items! Document not supported: "+document);
    }

    @Override
    public Invoice cancel(Invoice entity) {
        boolean proforma = Boolean.TRUE.equals(entity.getProformaInvoice());

        if (proforma){
            if ( InvoiceStatus.WaitForPayment.equals(entity.getStatus().getEnumValue()) ){}//ok
            else
                throw new IllegalArgumentException("The proforma invoice should be in WAIT_FOR_PAYMENT state in order to be cancelled!");
        }else{
            if ( InvoiceStatus.Paid.equals(entity.getStatus().getEnumValue()) ){}//ok
            else
                throw new IllegalArgumentException("The invoice should be COMPLETED in order to cancelled!");
        }
        
        entity.setStatus(InvoiceStatus.Cancelled.getDbResource());
        return saveInvoice(entity);
    }

    @Override
    public Invoice confirm(Invoice entity) {
        boolean proforma = Boolean.TRUE.equals(entity.getProformaInvoice());

        if (proforma){
            if ( InvoiceStatus.Open.equals(entity.getStatus().getEnumValue()) ||
                InvoiceStatus.Reopen.equals(entity.getStatus().getEnumValue())){}//ok
            else
                throw new IllegalArgumentException("The proforma invoice should be OPEN or REOPEN in order to confirmed!");
        }else{
            if ( InvoiceStatus.Open.equals(entity.getStatus().getEnumValue()) ){}//ok
            else
                throw new IllegalArgumentException("The invoice should be OPEN in order to be confirmed!");
            
            boolean reserve = true;
            //if credit note - we will restore the quantities instead of reserving them
            if ( InvoiceType.CretidNoteInvoice.equals(entity.getInvoiceType().getEnumValue()))
                reserve = false;
            
            updateWarehouseQuantities(entity, reserve);
        }
        
        //the document date is the date that it was confirmed as valid document
        entity.setInvoiceDate(new Date());
        //the number of the document is generated at confirmation
        documentNumberLocal.setDocumentNumber(entity);
        entity.setStatus(InvoiceStatus.WaitForPayment.getDbResource());
        
        //update template documents
        //updateTemplateDocuments(entity);
        
        return saveInvoice(entity);
    }

    /**
     * If this is a proforma-invoice, updates the respective invoice for the canceled amount 
     * @param entity
     */
//    private void updateTemplateDocuments(Invoice entity) {
//        //update the canceled amount for the template invoice if this is proforma
//        if ( entity.getInvoiceType()!=null &&
//                InvoiceType.CretidNoteInvoice.equals(entity.getInvoiceType().getEnumValue()) ){
//            
//            Set<Invoice> templateDocuments = new HashSet<Invoice>();
//            List<InvoiceItemLink> proformaItemLinks = getInvoiceItemLinks(entity);
//            
//            for (InvoiceItemLink invoiceItemLink : proformaItemLinks) {
//                Invoice template = em.getReference(Invoice.class, invoiceItemLink.getTemplateDocumentId());
//                templateDocuments.add(template);
//                
//                BigDecimal canceledAmount = template.getCanceledAmount();
//                canceledAmount = canceledAmount.add(invoiceItemLink.getInvoiceItem().getExtendedPrice());
//                template.setCanceledAmount(canceledAmount);
//            }
//            
//            for (Invoice templateInvoices : templateDocuments) {
//                esm.persist(em, templateInvoices);
//            }
//        }
//    }

    private List<InvoiceItemLink> getInvoiceItemLinks(Invoice entity) {
        return (List<InvoiceItemLink>) 
        AcaciaUtils.getResultList(em, "InvoiceItemLink.getForInvoice", "invoiceId", entity.getId());
    }

    /**
     * The method updates the {@link WarehouseProduct} quantities for the given confirmed invoice.
     * @param entity
     * @param reserve - reserve or restored the quantities
     */
    private void updateWarehouseQuantities(Invoice entity, boolean reserve) {
        List<InvoiceItem> items = getInvoiceItems(entity.getInvoiceId());
        Address userBranch = acaciaSession.getBranch();
        Warehouse userWarehouse = warehouseListLocal.getWarehouseForAddress(userBranch);
        for (InvoiceItem invoiceItem : items) {
            Product product = invoiceItem.getProduct();
            Warehouse warehouse = invoiceItem.getWarehouse();
            if ( warehouse==null )
                warehouse = userWarehouse;
            BigDecimal itemQuantity = transformQuantity(
                invoiceItem.getOrderedQuantity(), invoiceItem.getMeasureUnit(), product.getMeasureUnit());
            reserveProductQuantity(product, warehouse, itemQuantity, null, reserve, invoiceItem);
        }
    }

    /**
     * Get the quantity in the target unit.
     * For example for source = 2000 gram, and target unit kg, the result is 2 (kg).
     * @param sourceQuantity
     * @param sourceMeasureUnit
     * @param targetMeasureUnit
     * @return
     */
    private BigDecimal transformQuantity(BigDecimal sourceQuantity, DbResource sourceMeasureUnit,
                                         DbResource targetMeasureUnit) {
        if ( targetMeasureUnit==null )
            return sourceQuantity;
        
        MeasurementUnit sourceUnit = (MeasurementUnit) sourceMeasureUnit.getEnumValue();
        MeasurementUnit targetUnit = (MeasurementUnit) targetMeasureUnit.getEnumValue();

        BigDecimal targetUnitValue = targetUnit.getCgsUnitValue();
        
        BigDecimal sourceUnitValue = sourceUnit.getCgsUnitValue();
        
        BigDecimal multiplier = sourceUnitValue.divide(targetUnitValue);
        
        BigDecimal result = sourceQuantity.multiply(multiplier);
        return result;
    }

    /**
     * Recursively reserves the quantity for every simple product.
     * @param warehouse 
     * @param reserve 
     * @param complexProduct
     */
    private void reserveProductQuantity(Product product, Warehouse warehouse, BigDecimal itemQuantity, BigDecimal quantity, boolean reserve, Object item) {
        if ( product instanceof ComplexProduct ){
            ComplexProduct complexProduct = (ComplexProduct) product;
            
            List<ComplexProductItem> cItems = complexProduct.getComplexProductItems();
            if ( cItems==null ){
                cItems = assemblingLocal.getComplexProductItems(complexProduct);
            }
            if ( cItems!=null ){
                for (ComplexProductItem cItem : cItems) {
                    reserveProductQuantity(cItem.getProduct(), warehouse, itemQuantity, cItem.getQuantity(), reserve, cItem);
                }
            }
        }else if ( product instanceof SimpleProduct ){
            if ( reserve )
                reserveSimpleProductQuantity((SimpleProduct)product, warehouse, itemQuantity, quantity, item);
            else
                restoreSimpleProductQuantity((SimpleProduct)product, warehouse, itemQuantity, quantity, item);
        }
    }

    /**
     * Restores previously reserved quantities.
     * The method does the opposite of {@link #reserveSimpleProductQuantity(SimpleProduct, Warehouse, BigDecimal, BigDecimal)}
     * @param product
     * @param warehouse
     * @param itemQuantity
     * @param pQuantity
     */
    private void restoreSimpleProductQuantity(SimpleProduct product, Warehouse warehouse,
                                              BigDecimal itemQuantity, BigDecimal pQuantity, Object item) {
        WarehouseProduct wp = getWarehouseProduct(product);
        if ( wp==null ){
            wp = createWarehouseProduct(product, warehouse);
        }
        
        BigDecimal quantity = itemQuantity;
        if ( pQuantity!=null )
            quantity = pQuantity.multiply(itemQuantity);
        
        BigDecimal soldQuantityToUse = quantity;
        wp.setSoldQuantity(wp.getSoldQuantity().subtract(soldQuantityToUse));
        
        BigDecimal dueQuantityToUse = null;
        if ( item instanceof InvoiceItem ){
            InvoiceItem invoiceItem = (InvoiceItem) item;
            if ( invoiceItem.getDueQuantity()!=null ){
                dueQuantityToUse = invoiceItem.getDueQuantity();
            }
        }else if ( item instanceof ComplexProductItem ){
            ComplexProductItem complexProductItem = (ComplexProductItem) item;
            if ( complexProductItem.getDueQuantity()!=null )
                dueQuantityToUse = complexProductItem.getDueQuantity();
        }
        
        if ( dueQuantityToUse!=null ){
            wp.setQuantityDue(wp.getQuantityDue().subtract(dueQuantityToUse));
        }
        
        warehouseListLocal.saveWarehouseProduct(wp);
    }

    /**
     * Actually reserves the quantity for the given product at the given warehouse.
     * @param product
     * @param warehouse
     * @param itemQuantity - since this product is actually related to a document item, this is
     * the quantity specified for the whole item (which may consist of multiple simple products)
     * @param pQuantity - this is the actual quantity for this specific simple product
     */
    private void reserveSimpleProductQuantity(SimpleProduct product, Warehouse warehouse, BigDecimal itemQuantity, BigDecimal pQuantity, Object item) {
        WarehouseProduct wp = getWarehouseProduct(product);
        if ( wp==null ){
            wp = createWarehouseProduct(product, warehouse);
        }
        
        BigDecimal quantity = itemQuantity;
        if ( pQuantity!=null )
            quantity = pQuantity.multiply(itemQuantity);
        
        BigDecimal soldQuantityToUse = quantity;
        BigDecimal dueQuantityToUse = null;
        
        if ( wp.getFreeQuantity().compareTo(quantity)<0 ){
            //if negative free quantity - set all as 'due'
            if ( wp.getFreeQuantity().compareTo(BigDecimal.ZERO)<0 )
                dueQuantityToUse = quantity;
            //otherwise, get the available free quantity
            else
                dueQuantityToUse = quantity.subtract(wp.getFreeQuantity());
        }
        
        BigDecimal updatedSoldQty = wp.getSoldQuantity().add(soldQuantityToUse);
        wp.setSoldQuantity(updatedSoldQty);
        
        if ( dueQuantityToUse!=null ){
            BigDecimal updatedDueQuantity = wp.getQuantityDue().add(dueQuantityToUse);
            wp.setQuantityDue(updatedDueQuantity);
            //update the item
            if ( item instanceof InvoiceItem ){
                InvoiceItem invoiceItem = (InvoiceItem) item;
                if ( invoiceItem.getDueQuantity()==null )
                    invoiceItem.setDueQuantity(BigDecimal.ZERO);
                invoiceItem.setDueQuantity(invoiceItem.getDueQuantity().add(dueQuantityToUse));
                saveInvoiceItem(invoiceItem);
            }else if ( item instanceof ComplexProductItem ){
                ComplexProductItem complexProductItem = (ComplexProductItem) item;
                if ( complexProductItem.getDueQuantity()==null )
                    complexProductItem.setDueQuantity(BigDecimal.ZERO);
                complexProductItem.setDueQuantity(complexProductItem.getDueQuantity().add(dueQuantityToUse));
                saveComplexProductItem(complexProductItem);
            }
        }
        
        warehouseListLocal.saveWarehouseProduct(wp);
    }

    private void saveComplexProductItem(ComplexProductItem complexProductItem) {
        esm.persist(em, complexProductItem);
    }

    /**
     * Creates new warehouse product instance.
     * @param product
     * @param warehouse
     * @return
     */
    private WarehouseProduct createWarehouseProduct(SimpleProduct product, Warehouse warehouse) {
        WarehouseProduct wp = warehouseListLocal.newWarehouseProduct(warehouse.getParentId(), warehouse);
        wp.setProduct(product);
        warehouseListLocal.saveWarehouseProduct(wp);
        return wp;
    }

    @Override
    public Invoice reopen(Invoice entity) {
        boolean proforma = Boolean.TRUE.equals(entity.getProformaInvoice());

        if (proforma){
            if ( InvoiceStatus.WaitForPayment.equals(entity.getStatus().getEnumValue()) ||
                InvoiceStatus.Cancelled.equals(entity.getStatus().getEnumValue())){}//ok
            else
                throw new IllegalArgumentException("The proforma invoice should be CANCELLED or WAIT_FOR_PAYMENT in order to be reopen!");
        }else{
            throw new IllegalArgumentException("Invoices can not be reopen!");
        }
        
        entity.setStatus(InvoiceStatus.Reopen.getDbResource());
        
        return saveInvoice(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<?> getTemplateDocuments(Invoice forDocument, Boolean includeInvoices, Boolean includeProformas) {
        boolean proform = Boolean.TRUE.equals(forDocument.getProformaInvoice());
        
        if ( proform ){
            //TODO when Sales Offers are ready - supply the implementation here.
            return new ArrayList<Object>();
        }else{
            //TEMPORARY - fix when Sales Offers are implemented TODO
            if ( !includeProformas )
                return new ArrayList<Object>();
            Query q = em.createNamedQuery("Invoice.getTemplatesForInvoice");
            q.setParameter("proformaInvoice", Boolean.TRUE);
            q.setParameter("recipient", forDocument.getRecipient());
            q.setParameter("paid", InvoiceStatus.Paid.getDbResource());
            List<?> templates = q.getResultList();
            return templates;
        }
    }

    @Override
    public Boolean isTemplateItem(InvoiceItem item) {
        List<InvoiceItemLink> links = getInvoiceItemLinks(item);
        return !links.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> getDueDocuments(BusinessPartner recipient) {
        Query q = em.createNamedQuery("Invoice.getDueInvoicesForRecipient");
        q.setParameter("recipient", recipient);
        q.setParameter("waitingForPayment", InvoiceStatus.WaitForPayment.getDbResource());
        
        List<Invoice> result = q.getResultList();
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Invoice> getDueDocuments() { 
        Query q = em.createNamedQuery("Invoice.getDueInvoices");
        q.setParameter("waitingForPayment", InvoiceStatus.WaitForPayment.getDbResource());
        q.setParameter("parentId", acaciaSession.getOrganization().getId());
        
        List<Invoice> result = q.getResultList();
        return result;
    }
    
    private List<Invoice> getConfirmedInvoices(BusinessPartner recipient, Date startDate){
        Query q = em.createNamedQuery("Invoice.getConfirmedInvoicesForRecipient");
        q.setParameter("recipient", recipient);
        q.setParameter("waitingForPayment", InvoiceStatus.WaitForPayment.getDbResource());
        q.setParameter("paid", InvoiceStatus.Paid.getDbResource());
        q.setParameter("proformaInvoice", Boolean.FALSE);
        
        List<Invoice> invoices = q.getResultList();
        List<Invoice> result = new ArrayList<Invoice>();
        for (Invoice invoice : invoices) {
            if ( invoice.getInvoiceDate()!=null && !invoice.getInvoiceDate().before(startDate)){
                result.add(invoice);
            }else{
                break;
            }
        }
        return result;
    }
    
    /**
     * Get the turnover for a given recipient after a given date (which may be null)
     * @param recipient
     * @param startDate
     * @return
     */
    public BigDecimal getRecipientTurnover(BusinessPartner recipient, Date startDate){
        List<Invoice> invoices = getConfirmedInvoices(recipient, startDate);
        
        BigDecimal turnover = new BigDecimal(0);
        for (Invoice invoice : invoices) {
            //if the current document is CREDIT note, decrease the due
            if ( InvoiceType.CretidNoteInvoice.equals(invoice.getInvoiceType().getEnumValue()) )
                turnover = turnover.subtract(invoice.getTotalValue());
            //otherwise increase the due amount
            else
                turnover = turnover.add(invoice.getTotalValue());
        }
        
        return turnover;
    }

    @Override
    public List<?> getInvoicesToCancel(Invoice invoice) {
        if ( InvoiceType.CretidNoteInvoice.equals(invoice.getInvoiceType().getEnumValue())){
            //ok
        }else{
            throw new IllegalArgumentException("Only credits notes can cancel invoices!");
        }
        
        Query q = em.createNamedQuery("Invoice.getInvoiceToCancel");
        q.setParameter("recipient", invoice.getRecipient());
        q.setParameter("waitingForPayment", InvoiceStatus.WaitForPayment.getDbResource());
        q.setParameter("proformaInvoice", Boolean.FALSE);
        q.setParameter("simpleInvoice", InvoiceType.SimpleInvoice.getDbResource());
        q.setParameter("vatInvoice", InvoiceType.VatInvoice.getDbResource());
        
        return q.getResultList();
    }
    
    public Invoice getInvoiceById(UUID invoiceId){
    	Query q = em.createNamedQuery("Invoice.findById");
    	q.setParameter("invoiceId", invoiceId);
    	List<Invoice> result = q.getResultList();
    	if(result.isEmpty()){
    		return null;
    	}else{
    		return result.get(0);
    	}
    }
    
    public InvoiceItem getInvoiceItemById(UUID invoiceItemId){
    	
    	Query q = em.createNamedQuery("InvoiceItem.findByIdAndDeleted");
    	q.setParameter("invoiceItemId", invoiceItemId);
    	q.setParameter("deleted", false);
    	List<InvoiceItem> result = q.getResultList();
    	if(result.isEmpty()){
    		return null;
    	}else{
    		return result.get(0);
    	}
    }

    @Override
    public List<Invoice> getConfirmedDocuments() {
        Query q = em.createNamedQuery("Invoice.getConfirmedInvoices");
        q.setParameter("waitingForPayment", InvoiceStatus.WaitForPayment.getDbResource());
        q.setParameter("paid", InvoiceStatus.Paid.getDbResource());
        q.setParameter("parentId", acaciaSession.getOrganization().getId());
        
        List<Invoice> result = q.getResultList();
        return result;
    }

    @Override
    public List<Invoice> getPendingInvoices(BusinessPartner recipient, Boolean includedPartlyPaid,
                                            Boolean includeUnpaid) {
        List<Invoice> result = new ArrayList<Invoice>();
        if ( includedPartlyPaid ){
            List<Invoice> partlyMatched = (List<Invoice>)
                AcaciaUtils.getResultList(em, "Invoice.getPartlyMatched", 
                "branch", acaciaSession.getBranch(),
                "recipient", recipient,
                "waitForPayment", InvoiceStatus.WaitForPayment.getDbResource(),
                "creditNote", InvoiceType.CretidNoteInvoice.getDbResource());
            result.addAll(partlyMatched);
        }
        if ( includeUnpaid ){
            List<Invoice> unmatched = (List<Invoice>)
            AcaciaUtils.getResultList(em, "Invoice.getUnmatched",
                "branch", acaciaSession.getBranch(),
                "recipient", recipient,
                "waitForPayment", InvoiceStatus.WaitForPayment.getDbResource(),
                "creditNote", InvoiceType.CretidNoteInvoice.getDbResource());
            result.addAll(unmatched);
        }
        
        //remove the invoices that have no due
        for (Iterator iterator = result.iterator(); iterator.hasNext();) {
            Invoice invoice = (Invoice) iterator.next();
            if (  BigDecimal.ZERO.compareTo(invoice.getDueAmount())>=0 ){
                iterator.remove();
            }
        }
        
        return result;
    }
}
