/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.contactbook.AddressesListLocal;
import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListLocal;
import com.cosmos.acacia.crm.bl.contactbook.PersonsListLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType;
import com.cosmos.acacia.crm.enums.DeliveryCertificateReason;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignmentPK;
import com.cosmos.acacia.crm.data.Invoice;

/**
 *
 * @author daniel
 */
@Stateless
public class DeliveryCertificatesBean implements DeliveryCertificatesRemote, DeliveryCertificatesLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private DatabaseResourceLocal databaseResource;
    @EJB
    private AddressesListLocal addressesBean;
    @EJB
    private PersonsListLocal personsBean;
    @EJB
    private OrganizationsListLocal organizationsBean;
    @EJB
    private AcaciaSessionLocal session;
    
    @Deprecated
    public DeliveryCertificate createStubDeliveryCert() {
        
        /*
        
        DeliveryCertificate ds = new DeliveryCertificate();
        
        Query q1 = em.createNamedQuery("Warehouse.findById");
        q1.setParameter("id", new BigInteger("1213952129954"));
        Warehouse warehouse = (Warehouse)q1.getResultList().get(0);
        System.out.println("Warehouse: " + warehouse.getDescription());
        ds.setWarehouse(warehouse);
        ds.setWarehouseName(warehouse.getDescription());
        
        ds.setDeliveryCertificateNumber(3876423452385l);
        ds.setDeliveryCertificateDate(new Date());
        
        
        ds.setDeliveryCertificateMethodType(DeliveryCertificateMethodType.InPlace.getDbResource());
        ds.setDeliveryCertificateReason(DeliveryCertificateReason.Invoice.getDbResource());
        
        Query q = em.createNamedQuery("Person.findByName");
        q.setParameter("firstName", "Daniel");
        Person creator = (Person)q.getResultList().get(0);
        ds.setCreator(creator);
        ds.setCreatorName(creator.getLastName());
        
        //recepient - taken from the invoice
        //List<Organization> organizations = organizationsBean.getOrganizations(null);
        //BusinessPartner recipient = organizations.get(0);
        Query q2 = em.createNamedQuery("Organization.findByName");
        q2.setParameter("organizationName", "IBM");
        List<Organization> organizations = q2.getResultList();

        BusinessPartner recipient = organizations.get(0);
        if(recipient != null){
            ds.setRecipientName(((Organization)recipient).getOrganizationName());
            ds.setRecipient(recipient);
        }

        Query q3 = em.createNamedQuery("Person.findByName");
        q3.setParameter("firstName", "Miroslav");
        Person recipientContact = (Person)q3.getResultList().get(0);
        if(recipientContact != null){
            ds.setRecipientContact(recipientContact);
            ds.setRecipientContactName(recipientContact.getFirstName() + " " + recipientContact.getLastName());
        }
        
        ds.setCreationTime(new Date());
        
        esm.persist(em, ds);
        
        return ds;
         **/
        /**
        DeliveryCertificate ds = new DeliveryCertificate();
        
        Query q1 = em.createNamedQuery("Warehouse.findById");
        q1.setParameter("id", new BigInteger("1213952129954"));
        Warehouse warehouse = (Warehouse)q1.getResultList().get(0);
        System.out.println("Warehouse: " + warehouse.getDescription());
        ds.setWarehouse(warehouse);
        ds.setWarehouseName(warehouse.getDescription());
        
        ds.setDeliveryCertificateNumber(2111525321);
        ds.setDeliveryCertificateDate(new Date());
        
        ds.setDeliveryCertificateMethodType(DeliveryCertificateMethodType.Forwarder.getDbResource());
        ds.setDeliveryCertificateReason(DeliveryCertificateReason.Invoice.getDbResource());
        
        Query q7 = em.createNamedQuery("Organization.findByName");
        q7.setParameter("organizationName", "SmartMinds");
        List<Organization> forwarders = q7.getResultList();
        ds.setForwarder(forwarders.get(0));
        ds.setForwarderName(forwarders.get(0).getOrganizationName());
        
        Query q = em.createNamedQuery("Person.findByName");
        q.setParameter("firstName", "Daniel");
        Person creator = (Person)q.getResultList().get(0);
        ds.setCreator(creator);
        ds.setCreatorName(creator.getLastName());
        
        Query q2 = em.createNamedQuery("Organization.findByName");
        q2.setParameter("organizationName", "IBM");
        List<Organization> organizations = q2.getResultList();

        BusinessPartner recipient = organizations.get(0);
        if(recipient != null){
            ds.setRecipientName(((Organization)recipient).getOrganizationName());
            ds.setRecipient(recipient);
        }

        Query q3 = em.createNamedQuery("Person.findByName");
        q3.setParameter("firstName", "Radoslav");
        Person recipientContact = (Person)q3.getResultList().get(0);
        if(recipientContact != null){
            ds.setRecipientContact(recipientContact);
            ds.setRecipientContactName(recipientContact.getFirstName() + " " + recipientContact.getLastName());
        }
        
        ds.setCreationTime(new Date());
        
        esm.persist(em, ds);
        
        return ds;
         */
        return null;
    }

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
    public List<DeliveryCertificate> getDeliveryCertificates(String parentId) {
        Query q = em.createNamedQuery("DeliveryCertificate.findByWarehouse");
        List<DeliveryCertificate> result = q.getResultList();

        return result;
    }

    public DeliveryCertificate newDeliveryCertificate(Object parent) {
        DeliveryCertificate ds = new DeliveryCertificate();
        /*
        if(session.getOrganization() != null){
            String userOrganizationName = session.getOrganization().getOrganizationName();
            System.out.println("Organization name: " + userOrganizationName);
        }else{
            System.out.println("User Organization is NULL");
        }
        
        if(session.getPerson() != null){
            String userDisplayName = session.getPerson().getDisplayName();
            System.out.println("User display name: " + userDisplayName);
            ds.setCreator(session.getPerson());
            ds.setCreatorName(userDisplayName);
        }else{
            System.out.println("User is NULL");
        }
        */
        ds.setDeliveryCertificateMethodType(DeliveryCertificateMethodType.InPlace.getDbResource());
        
        //temp
        Query q2 = em.createNamedQuery("Organization.findByName");
        q2.setParameter("organizationName", "Opel");
        List<Organization> organizations = q2.getResultList();

        BusinessPartner recipient = organizations.get(0);
        if(recipient != null){
            ds.setRecipientName(((Organization)recipient).getOrganizationName());
            ds.setRecipient(recipient);
        }
        
        List branches = addressesBean.getAddresses(new BigInteger("1216474970646"));
        ds.setRecipientBranch((Address)branches.get(1));
        Query q0 = em.createNamedQuery("Person.findByName");
        q0.setParameter("firstName", "Charko");
        Person recipientBranchContactPerson = (Person)q0.getResultList().get(0);
        ds.setRecipientContact(recipientBranchContactPerson);
        ds.setRecipientContactName(recipientBranchContactPerson.getLastName());
        
        try{
            if(session != null && session.getPerson() != null){
                String userDisplayName = session.getPerson().getDisplayName();
                System.out.println("User display name: " + userDisplayName);
                ds.setCreator(session.getPerson());
                ds.setCreatorName(userDisplayName);
            }else{
                System.out.println("User is NULL");
            }
        }catch(Exception e){
            Query q = em.createNamedQuery("Person.findByName");
            q.setParameter("firstName", "Smarty");
            Person creator = (Person)q.getResultList().get(0);
            ds.setCreator(creator);
            ds.setCreatorName(creator.getLastName());
        }
        Query q1 = em.createNamedQuery("Warehouse.findById");
        q1.setParameter("id", new BigInteger("1213952129954"));
        Warehouse warehouse = (Warehouse)q1.getResultList().get(0);
        System.out.println("Warehouse: " + warehouse.getDescription());
        ds.setWarehouse(warehouse);
        ds.setWarehouseName(warehouse.getDescription());
        
        //ds.setDeliveryCertificateNumber(367856901);
        //ds.setDeliveryCertificateDate(new Date());
        //ds.setCreationTime(new Date());
        
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

    @Override
    public List<Organization> getForwarders() {
        return organizationsBean.getOrganizations(null);
    }

    @Override
    public List<DeliveryCertificateAssignment> getDocuments(DeliveryCertificateReason reason) {
        if(reason == DeliveryCertificateReason.Invoice){
            Query q1 = em.createNamedQuery("DeliveryCertificateAssignment.findAll");
            return q1.getResultList();
            //return invoicesBean.getInvoices(null);
        }else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Deprecated
    public void mapDeliveryCertificateToInvoice(BigInteger deliveryCertificateId, BigInteger documentId) {
        
        DeliveryCertificateAssignment assignment = new DeliveryCertificateAssignment();
        DeliveryCertificateAssignmentPK pk = new DeliveryCertificateAssignmentPK(deliveryCertificateId, documentId);
        assignment.setDeliveryCertificateAssignmentPK(pk);
        esm.persist(em, assignment);
    }

    public DeliveryCertificate saveDeliveryCertificate(DeliveryCertificate deliveryCertificate) {
        deliveryCertificate.setDeliveryCertificateDate(new Date());
        if(deliveryCertificate.getCreationTime() == null){
            deliveryCertificate.setCreationTime(new Date());
        }
        esm.persist(em, deliveryCertificate);
        return deliveryCertificate; 
    }

    @Override
    public int deleteDeliveryCertificate(DeliveryCertificate deliveryCertificate) {
        return esm.remove(em, deliveryCertificate);
    }
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
