/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.data.BasicOrganization;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.validation.OrganizationValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.ClassifiersLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
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

    @EJB
    private AddressesListLocal addressesManager;

    @EJB
    private PersonsListLocal personsManager;

    @EJB
    private AcaciaSessionLocal acaciaSession;

    @EJB
    private ClassifiersLocal classifiersManager;


    @SuppressWarnings("unchecked")
    public List<Organization> getOrganizations(BigInteger parentId)
    {
        Query q;
        if(parentId != null)
        {
            q = em.createNamedQuery("Organization.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentId);
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

    @Override
    public EntityProperties getBasicOrganizationEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(BasicOrganization.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public Organization newOrganization(BigInteger parentId) {
        Organization org = new Organization();
        org.setParentId(parentId);
        return org;
    }

    public Organization saveOrganization(Organization organization) {
        organizationValidator.validate(organization);

        esm.persist(em, organization);
        return organization;
    }

    @Override
    public Organization saveBasicOrganization(BasicOrganization basicOrganization) {
        Organization organization = newOrganization(acaciaSession.getOrganization().getId());
        organization.setOrganizationName(basicOrganization.getOrganizationName());
        organization.setNickname(basicOrganization.getNickname());
        organization.setVatNumber(basicOrganization.getVatNumber());
        organization.setVatNumber(basicOrganization.getVatNumber());
        organization.setUniqueIdentifierCode(basicOrganization.getUniqueIdentifierCode());
        organization = saveOrganization(organization);

        Person person = personsManager.newPerson(acaciaSession.getOrganization().getId());
        person.setFirstName(basicOrganization.getFirstName());
        person.setSecondName(basicOrganization.getSecondName());
        person.setLastName(basicOrganization.getLastName());
        person.setExtraName(basicOrganization.getExtraName());
        person = personsManager.savePerson(person);

        Address address = addressesManager.newAddress();
        address.setAddressName("Registration");
        address.setCity(basicOrganization.getCity());
        address.setPostalCode(basicOrganization.getPostalCode());
        address.setPostalAddress(basicOrganization.getPostalAddress());
        address = addressesManager.saveAddress(address, organization.getId());

        ContactPerson contactPerson = addressesManager.newContactPerson();
        contactPerson.setContact(person);
        contactPerson = addressesManager.saveContactPerson(contactPerson, address.getId());

        organization.setRegistrationAddress(address);
        organization = saveOrganization(organization);

        System.out.println("classifiersManager: " + classifiersManager);
        if(basicOrganization.isCustomer()) {
            Classifier classifier = classifiersManager.getClassifier(
                    Classifier.Customer.getClassifierCode());
            System.out.println("classifier: " + classifier);
            System.out.println("organization: " + organization);
            System.out.println("organization.getDataObject(): " + organization.getDataObject());
            classifiersManager.classifyDataObject(organization.getDataObject(), classifier);
        }

        if(basicOrganization.isSupplier()) {
            Classifier classifier = classifiersManager.getClassifier(
                    Classifier.Supplier.getClassifierCode());
            classifiersManager.classifyDataObject(organization.getDataObject(), classifier);
        }

        if(basicOrganization.isProducer()) {
            Classifier classifier = classifiersManager.getClassifier(
                    Classifier.Producer.getClassifierCode());
            classifiersManager.classifyDataObject(organization.getDataObject(), classifier);
        }

        if(basicOrganization.isShippingAgent()) {
            Classifier classifier = classifiersManager.getClassifier(
                    Classifier.ShippingAgent.getClassifierCode());
            classifiersManager.classifyDataObject(organization.getDataObject(), classifier);
        }

        if(basicOrganization.isCourier()) {
            Classifier classifier = classifiersManager.getClassifier(
                    Classifier.Courier.getClassifierCode());
            classifiersManager.classifyDataObject(organization.getDataObject(), classifier);
        }

        if(basicOrganization.isBank()) {
            Classifier classifier = classifiersManager.getClassifier(
                    Classifier.Bank.getClassifierCode());
            classifiersManager.classifyDataObject(organization.getDataObject(), classifier);
        }

        return organization;
    }

    public int deleteOrganization(Organization organization) {
        return esm.remove(em, organization);
    }

    public List<Address> getAddresses(BigInteger parentId) {
       return locationsManager.getAddresses(parentId);
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
