/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListLocal;
import com.cosmos.acacia.crm.bl.contactbook.PersonsListLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BusinessPartner;
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
        return ds;
    }

    @Override
    public List<DbResource> getReasons() {
        return DeliveryCertificateReason.getDbResources();
    }
    
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
