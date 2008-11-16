/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumberPK;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType;
import com.cosmos.acacia.crm.enums.DeliveryCertificateReason;
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
    	EntityProperties entityProperties = esm.getEntityProperties(DeliveryCertificateSerialNumberPK.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }
    
    /**
     * 
     * @param parentId - warehouse id
     * @return
     */
    @Override
    public List<DeliveryCertificate> getDeliveryCertificates(BigInteger parentId) {
        Query q = em.createNamedQuery("DeliveryCertificate.findByWarehouse");
        q.setParameter("parentId", parentId);
        List<DeliveryCertificate> result = q.getResultList();
        
        //initialate the DelivetyCertificateAssignments
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
    public List<DeliveryCertificateItem> getDeliveryCertificateItems(BigInteger parentId){
    	Query q1 = em.createNamedQuery("DeliveryCertificateItem.findForCertificate");
        q1.setParameter("parentId", parentId);
         
    	return (List<DeliveryCertificateItem>)q1.getResultList();
    }
    
    @Override
    public List<DeliveryCertificateSerialNumber> getDeliveryCertificateItemSerialNumbers(BigInteger parentId){
    	Query q1 = em.createNamedQuery("DeliveryCertificateSerialNumber.findForCertificateItem");
        q1.setParameter("parentId", parentId);
         
    	return (List<DeliveryCertificateSerialNumber>)q1.getResultList();
    }
    
    @Override
    public DeliveryCertificate newDeliveryCertificate(BigInteger parentId) {
        DeliveryCertificate ds = new DeliveryCertificate();
        ds.setParentId(parentId);
        
        //default values
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
    public int deleteDeliveryCertificate(DeliveryCertificate deliveryCertificate) {
    	DeliveryCertificateAssignment deliveryCertificateAssignment = deliveryCertificate.getDocumentAssignment();
    	if( deliveryCertificateAssignment != null ){
            esm.remove(em, deliveryCertificateAssignment);
        }
    	
    	List<DeliveryCertificateItem> items = getDeliveryCertificateItems(deliveryCertificate.getDeliveryCertificateId());
    	for(DeliveryCertificateItem item : items){
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
    		dci.setQuantity(invoiceItem.getOrderedQuantity());
    		dci.setMeasureUnit(invoiceItem.getMeasureUnit());
    	}else{
    		throw new IllegalArgumentException("Certificate Item cannot be created from " + source.getDataObject().getDataObjectType());
    	}
    	
    	return dci;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
