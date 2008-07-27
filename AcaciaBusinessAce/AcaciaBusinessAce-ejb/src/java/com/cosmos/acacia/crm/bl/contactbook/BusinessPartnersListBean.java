/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
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

    public void addPropertyDetails(int orderPosition, String propertyName, String columnName,
                          String customELDisplay, EntityProperties entityProperties) {
       PropertyDetails pd = new PropertyDetails(propertyName, columnName, null);
       pd.setCustomDisplay(customELDisplay);
       pd.setOrderPosition(orderPosition);
       entityProperties.addPropertyDetails(pd);
   }


    @SuppressWarnings("unchecked")
    @Override
    public List<BusinessPartner> getBusinessPartners(BigInteger parentDataObjectId) {
        Query q = em.createNamedQuery("BusinessPartner.findForParentAndDeletedById");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<BusinessPartner> result = q.getResultList();
        return result;
    }
}
