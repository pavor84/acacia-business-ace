/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.validation.OrganizationValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.enums.OrganizationType;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Implementation of handling organizations (see interface for more information)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class OrganizationsListBean implements OrganizationsListRemote, OrganizationsListLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private LocationsListLocal locationsManager;

    @EJB
    private BankDetailsListLocal bankDetailsManager;

    @EJB
    private OrganizationValidatorLocal organizationValidator;

    @SuppressWarnings("unchecked")
    public List<Organization> getOrganizations(DataObject parent)
    {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("Organization.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("Organization.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<Organization>(q.getResultList());
    }

    public List<DbResource> getCurrencies()
    {
        return bankDetailsManager.getCurrencies();
    }

    public EntityProperties getOrganizationEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(Organization.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public Organization newOrganization() {
        return new Organization();
    }

    public Organization saveOrganization(Organization organization) {
        organizationValidator.validate(organization);

        esm.persist(em, organization);
        return organization;
    }

    public int deleteOrganization(Organization organization) {
        return esm.remove(em, organization);
    }

    public List<Address> getAddresses(DataObject parent) {
       return locationsManager.getAddresses(parent);
    }

    public List<BankDetail> getBankDetails(DataObject parent) {
        // TODO implement
        return new ArrayList<BankDetail>();
    }

    public EntityProperties getAddressEntityProperties() {
       return locationsManager.getAddressEntityProperties();
    }

    public EntityProperties getBankDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(BankDetail.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public List<DbResource> getOrganizationTypes() {
        return OrganizationType.getDbResources();
    }
}
