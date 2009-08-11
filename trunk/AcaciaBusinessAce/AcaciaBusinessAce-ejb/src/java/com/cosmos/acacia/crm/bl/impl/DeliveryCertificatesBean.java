/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.invoice.InvoiceListLocal;
import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.product.ComplexProductItem;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumberPK;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.data.WarehouseProduct;
import com.cosmos.acacia.crm.data.predicates.InvoiceItemShippedPredicate;
import com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType;
import com.cosmos.acacia.crm.enums.DeliveryCertificateReason;
import com.cosmos.acacia.crm.enums.DeliveryCertificateStatus;
import com.cosmos.acacia.crm.enums.DeliveryStatus;
import com.cosmos.acacia.crm.validation.impl.DeliveryCertificateItemValidatorLocal;
import com.cosmos.acacia.crm.validation.impl.DeliveryCertificateSerialNumberValidatorLocal;
import com.cosmos.acacia.crm.validation.impl.DeliveryCertificateValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * @author daniel
 */
@Stateless
public class DeliveryCertificatesBean implements DeliveryCertificatesRemote, DeliveryCertificatesLocal {

    Logger log = Logger.getLogger(DeliveryCertificatesBean.class.getName());
    
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private AcaciaSessionLocal session;
    
    @EJB
    private InvoiceListLocal invoicesBean;
    
    @EJB
    private DeliveryCertificateValidatorLocal beanValidator;
    
    @EJB
    private DeliveryCertificateItemValidatorLocal deliveryCertificateItemValidator;
    
    @EJB
    private DeliveryCertificateSerialNumberValidatorLocal serialNumberValidator;
    
    @EJB
    private WarehouseListLocal warehouseListBean;
    
    private static final String EMPTY_SERIAL_NUMBER_PREFIX = "enter serial number ";
    
    public EntityProperties getDeliveryCertificateEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(DeliveryCertificate.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }
    
    public EntityProperties getDeliveryCertificateListEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(DeliveryCertificate.class);
        entityProperties.removePropertyDetails("recipientBranch");
        entityProperties.removePropertyDetails("creationTime");
        entityProperties.removePropertyDetails("recipientContact");
        entityProperties.removePropertyDetails("creatorOrganization");
        entityProperties.removePropertyDetails("creatorBranch");
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }
    
    public EntityProperties getDeliveryCertificateItemsEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(DeliveryCertificateItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    public EntityProperties getDeliveryCertificateItemDetailsEntityProperties(){
    	EntityProperties entityProperties = esm.getEntityProperties(DeliveryCertificateItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }
    
    public EntityProperties getDeliveryCertificateSerialNumberListEntityProperties(){
    	EntityProperties entityProperties = esm.getEntityProperties(DeliveryCertificateSerialNumber.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }
    
    /**
     * 
     * @param parentId - warehouse id
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DeliveryCertificate> getDeliveryCertificates(BigInteger parentId) {
        Query q = em.createNamedQuery("DeliveryCertificate.findByWarehouse");
        q.setParameter("parentId", parentId);
        List<DeliveryCertificate> result = q.getResultList();
        
        //initialize the DelivetyCertificateAssignments
        for (DeliveryCertificate deliveryCertificate : result) {
            Query assignmentQuery = em.createNamedQuery("DeliveryCertificateAssignment.findByDeliveryCertificate");
            assignmentQuery.setParameter("deliveryCertificateId", deliveryCertificate.getDeliveryCertificateId());
            try{
                DeliveryCertificateAssignment assignment = (DeliveryCertificateAssignment)assignmentQuery.getSingleResult();
                deliveryCertificate.setDocumentAssignment(assignment);
            }catch(NoResultException nre){
                //something wrong? Maybe all delivery certificates must have assignments.
            }
        }
        
        
        return result;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public DeliveryCertificate getDeliveryCertificateById(BigInteger deliveryCertificateId) {
    	Query q = em.createNamedQuery("DeliveryCertificate.findByIdAndDeleted");
        q.setParameter("deliveryCertificateId", deliveryCertificateId);
        q.setParameter("deleted", false);
        List<DeliveryCertificate> result = q.getResultList();
        if(result.size() > 0){
        	return result.get(0);
        }else{
        	log.warning("The delivery certificate with ID=" + deliveryCertificateId + " ,was not found!");
        	return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DeliveryCertificateItem> getDeliveryCertificateItems(BigInteger parentId){
    	Query q1 = em.createNamedQuery("DeliveryCertificateItem.findForCertificate");
        q1.setParameter("parentId", parentId);
         
    	return (List<DeliveryCertificateItem>)q1.getResultList();
    }
    
    @Override
    public DeliveryCertificateItem getDeliveryCertificateItemById(BigInteger itemId){
    	Query q1 = em.createNamedQuery("DeliveryCertificateItem.findById");
        q1.setParameter("itemId", itemId);
         
    	return (DeliveryCertificateItem)q1.getSingleResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<DeliveryCertificateSerialNumber> getDeliveryCertificateItemSerialNumbers(BigInteger parentId){
    	Query q1 = em.createNamedQuery("DeliveryCertificateSerialNumber.findForCertificateItem");
        q1.setParameter("parentId", parentId);
        
        DeliveryCertificateItem item = this.getDeliveryCertificateItemById(parentId);
        List<DeliveryCertificateSerialNumber> serialNumbersList = (List<DeliveryCertificateSerialNumber>)q1.getResultList(); 
        int quantity = item.getQuantity().toBigInteger().intValue();
    	
        DeliveryCertificateSerialNumberPK serialNumberPK;
        
    	if(quantity > serialNumbersList.size()){
    		int savedSerialNumersCount = serialNumbersList.size();
    		for(int i = 0; i < quantity - savedSerialNumersCount; i++){
	    		//construct primary key
				serialNumberPK = new DeliveryCertificateSerialNumberPK();
				serialNumberPK.setCertificateItemId(parentId);
				
				//construct serial number
				DeliveryCertificateSerialNumber serialNumber = new DeliveryCertificateSerialNumber();
				serialNumber.setDeliveryCertificateSerialNumberPK(serialNumberPK);
				serialNumber.setDeliveryCertificateItem(item);
				serialNumber.setSerialNumber("enter serial number " + (savedSerialNumersCount + i + 1));
				serialNumbersList.add(serialNumber);
    		}
		}
        
    	return serialNumbersList;
    }
    
    @Override
    public DeliveryCertificate newDeliveryCertificate(BigInteger parentId) {
        DeliveryCertificate ds = new DeliveryCertificate();
        ds.setParentId(parentId);
        
        //default values
        ds.setStatus(DeliveryCertificateStatus.Draft.getDbResource());
        ds.setDeliveryCertificateReason(DeliveryCertificateReason.Invoice.getDbResource());
        ds.setDeliveryCertificateMethodType(DeliveryCertificateMethodType.InPlace.getDbResource());
        
        //automatically bounded values
        ds.setCreator(session.getPerson());
        ds.setCreatorOrganization(session.getOrganization());
        ds.setCreatorBranch(session.getBranch());
                
        Query q1 = em.createNamedQuery("Warehouse.findById");
        q1.setParameter("id", parentId);
        if(q1.getResultList() != null && q1.getResultList().size() > 0){
            Warehouse warehouse = (Warehouse)q1.getResultList().get(0);
            ds.setWarehouse(warehouse);
            ds.setWarehouseName(warehouse.getAddress().getAddressName());
        }else{
            log.severe("Cannot get the selected warehouse for setting it as parent of newly creating DeliveryCertificate");
        }
        
        DeliveryCertificateAssignment assignment = newDeliveryCertificateAssignment();
        ds.setDocumentAssignment(assignment);
        
        return ds;
    }

    public DeliveryCertificateSerialNumber newDeliveryCertificateSerialNumber(BigInteger parentId){
    	DeliveryCertificateSerialNumber dcsn = new DeliveryCertificateSerialNumber();
    	DeliveryCertificateSerialNumberPK pk = new DeliveryCertificateSerialNumberPK();
    	pk.setCertificateItemId(parentId);
    	dcsn.setDeliveryCertificateSerialNumberPK(pk);
    	return dcsn;
    }
    
    @Override
    public List<DbResource> getReasons() {
        return DeliveryCertificateReason.getDbResources();
    }

    @Override
    public List<DbResource> getDeliveryTypes() {
        return DeliveryCertificateMethodType.getDbResources();
    }
    
    @Override
    public DeliveryCertificate saveDeliveryCertificate(DeliveryCertificate deliveryCertificate, DeliveryCertificateAssignment assignment, List<DeliveryCertificateItem> items) {

        esm.persist(em, deliveryCertificate);
        assignment.setDeliveryCertificateId(deliveryCertificate.getId());
        esm.persist(em, assignment);
        
        for(DeliveryCertificateItem item : items){
        	item.setParentId(deliveryCertificate.getId());
        	esm.persist(em, item);
        }
        
        return deliveryCertificate; 
    }
    
    @Override
    public DeliveryCertificateItem saveDeliveryCertificateItem(DeliveryCertificateItem item) {
    	
    	deliveryCertificateItemValidator.validate(item);
    	esm.persist(em, item);
        return item; 
    }
    
    @SuppressWarnings("unchecked")
    public DeliveryCertificate deliverDeliveryCertificate(DeliveryCertificate deliveryCertificate, DeliveryCertificateAssignment assignment, List<DeliveryCertificateItem> items) {
    	
    	beanValidator.validate(deliveryCertificate, assignment, items);
    	
    	//set properties specific only for delivered protocols
    	Date now = new Date();
    	deliveryCertificate.setStatus(DeliveryCertificateStatus.Delivered.getDbResource());
    	deliveryCertificate.setDeliveryCertificateDate(now);
    	deliveryCertificate.setDeliveryCertificateNumber(BigInteger.valueOf(now.getTime()));
    	
    	BigInteger assignmentId = assignment.getDocumentId(); 
    	Invoice invoice = invoicesBean.getInvoiceById(assignmentId);
    	List<InvoiceItem> invoiceItems = invoicesBean.getInvoiceItems(invoice.getInvoiceId());
    	
    	//we have ensure enough quantities with validation
		for(DeliveryCertificateItem dcItem : items){
			InvoiceItem iItem = invoicesBean.getInvoiceItemById(dcItem.getReferenceItemId());
			this.updateQuantities(dcItem.getProduct(), dcItem.getQuantity(), iItem, invoice);
		}
		
		//check if we have shipped everything
		Collection<InvoiceItem> shippedItems = CollectionUtils.select(invoiceItems, new InvoiceItemShippedPredicate());
		if(shippedItems.size() == invoiceItems.size()){
			log.info("All qunatities are delivered for the invoice!");
			invoice.setDeliveryStatus(DeliveryStatus.Delivered.getDbResource());
		}else{
			log.info("Not all qunatities are delivered for the invoice!");
			invoice.setDeliveryStatus(DeliveryStatus.PartlyDelivered.getDbResource());
		}
		
		//we might have been updated the delivery status
		invoicesBean.saveInvoice(invoice);
		
		//persist the certificate
		deliveryCertificate = saveDeliveryCertificate(deliveryCertificate, assignment, items);
		
    	return deliveryCertificate; 
    }
  
    /** Recursive
     * This method updates all necessary quantities for a Product to be shipped  
     */
    private void updateQuantities(Product product, BigDecimal quantityToDeliver, InvoiceItem iItem, Invoice invoice){
    	
    	//updating the InvoiceItem 
    	BigDecimal shipedQuantity = (iItem.getShippedQuantity() != null) ? iItem.getShippedQuantity() : BigDecimal.ZERO;
    	iItem.setShippedQuantity(shipedQuantity.add(quantityToDeliver));
		invoicesBean.saveInvoiceItem(iItem);
		
    	if(product instanceof SimpleProduct){
	    	WarehouseProduct wp = invoicesBean.getWarehouseProduct(invoice.getBranch(), (SimpleProduct)product);
			
			//decrease warehouse quantities
			wp.setSoldQuantity(wp.getSoldQuantity().subtract(quantityToDeliver));
			wp.setQuantityInStock(wp.getQuantityInStock().subtract(quantityToDeliver));
			warehouseListBean.saveWarehouseProduct(wp);
		}
    	else{
    		List<ComplexProductItem> items = ((ComplexProduct)product).getComplexProductItems();
            if(items != null && items.size() > 0)
            {
                for(ComplexProductItem item : items)
                {
                	updateQuantities(item.getProduct(), quantityToDeliver, iItem, invoice);
                }
            }
    	}
    }
    
    @Override
    public int deleteDeliveryCertificate(DeliveryCertificate deliveryCertificate) {
    	DeliveryCertificateAssignment deliveryCertificateAssignment = deliveryCertificate.getDocumentAssignment();
    	if( deliveryCertificateAssignment != null ){
            esm.remove(em, deliveryCertificateAssignment);
        }
    	
    	List<DeliveryCertificateItem> items = getDeliveryCertificateItems(deliveryCertificate.getDeliveryCertificateId());
    	for(DeliveryCertificateItem item : items){
    		List<DeliveryCertificateSerialNumber> serialNumbers = getDeliveryCertificateItemSerialNumbers(item.getCertificateItemId());
    		for(DeliveryCertificateSerialNumber sn : serialNumbers){
    			esm.remove(em, sn);
    		}
    		esm.remove(em, item);
    	}
    	
        return esm.remove(em, deliveryCertificate);
    }

    @Override
    public DeliveryCertificateAssignment newDeliveryCertificateAssignment(DataObjectBean document) {
        if(document instanceof Invoice){
            Invoice invoice = (Invoice)document;
            DeliveryCertificateAssignment assignment = new DeliveryCertificateAssignment();
            assignment.setDocumentNumber(String.valueOf(invoice.getInvoiceNumber()));
            assignment.setDocumentId(invoice.getId());
            return assignment;
        }else{
            throw new IllegalArgumentException("DeliveryCertificate cannot be created from a document of type " + document.getDataObject().getDataObjectType().getDataObjectType());
        }
    }
    
    @Override
    public EntityProperties getDeliveryCertificateAssignmentEntityProperties(){
        
        EntityProperties entityProperties = esm.getEntityProperties(DeliveryCertificateAssignment.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }
    
    @Override
    public DeliveryCertificateAssignment newDeliveryCertificateAssignment(){
        DeliveryCertificateAssignment assignment = new DeliveryCertificateAssignment();
        return assignment;
    }
    
    @Override
    public DeliveryCertificateItem newDeliveryCertificateItem(DataObjectBean source){
    	
    	DeliveryCertificateItem dci = new DeliveryCertificateItem();
    	
    	if(source instanceof InvoiceItem){
    		InvoiceItem invoiceItem = ((InvoiceItem)source);
    		dci.setProduct(invoiceItem.getProduct());
    		BigDecimal shippedQuantity = invoiceItem.getShippedQuantity() != null ? invoiceItem.getShippedQuantity() : BigDecimal.ZERO;
    		dci.setQuantity(invoiceItem.getOrderedQuantity().subtract(shippedQuantity));
    		dci.setMeasureUnit(invoiceItem.getMeasureUnit());
    		dci.setReferenceItemId(invoiceItem.getId());
    	}else{
    		throw new IllegalArgumentException("Certificate Item cannot be created from " + source.getDataObject().getDataObjectType());
    	}
    	
    	return dci;
    }
    
    public DeliveryCertificateSerialNumber saveDeliveryCertificateItemSerialNumber(DeliveryCertificateSerialNumber entity){
		
    	//TODO: This check could be removed if we the panel with serial numbers contains only saved Serial Numbers and every new Serial Number to be added via footer row (created dynamically)
    	if(entity.getSerialNumber().startsWith(EMPTY_SERIAL_NUMBER_PREFIX)){
			//do not persists empty serial numbers
			return entity;
		}
		
		serialNumberValidator.validate(entity);
		
		esm.persist(em, entity);
		return entity;
    }
    
    public int deleteDeliveryCertificateItemSerialNumber(DeliveryCertificateSerialNumber serialNumber){
		return esm.remove(em, serialNumber);
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
