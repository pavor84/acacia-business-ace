/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

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
import com.cosmos.acacia.crm.data.Invoice;
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

    
    
    @Override
    public List<DbResource> getReasons() {
        return DeliveryCertificateReason.getDbResources();
    }

    @Override
    public List<DbResource> getDeliveryTypes() {
        return DeliveryCertificateMethodType.getDbResources();
    }

    /*Test purposes*/
    @Deprecated
    private void mapDeliveryCertificateToInvoice(BigInteger deliveryCertificateId, BigInteger documentId) {
        
        //DeliveryCertificateAssignment assignment = new DeliveryCertificateAssignment();
        //DeliveryCertificateAssignmentPK pk = new DeliveryCertificateAssignmentPK(deliveryCertificateId, documentId);
        //assignment.setDeliveryCertificateAssignmentPK(pk);
        //esm.persist(em, assignment);
    }

    @Override
    public DeliveryCertificate saveDeliveryCertificate(DeliveryCertificate deliveryCertificate, DeliveryCertificateAssignment assignment) {

        esm.persist(em, deliveryCertificate);
        assignment.setDeliveryCertificateId(deliveryCertificate.getId());
        esm.persist(em, assignment);
        
        return deliveryCertificate; 
    }

    @Override
    public int deleteDeliveryCertificate(DeliveryCertificate deliveryCertificate) {
        if(deliveryCertificate.getDocumentAssignment() != null){
            esm.remove(em, deliveryCertificate.getDocumentAssignment());
        }
        return esm.remove(em, deliveryCertificate);
    }

    @Override
    public DeliveryCertificateAssignment newDeliveryCertificateAssignment(DeliveryCertificate ds, DataObjectBean document) {
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
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
