/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.data.contacts.BasicOrganization;
import java.util.UUID;
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
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BankDetail;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.enums.Currency;
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


    @Override
    public List<Organization> getOrganizations(UUID parentId)
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

    @Override
    public List<DbResource> getCurrencies()
    {
        return bankDetailsManager.getCurrencies();
    }

    @Override
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

    @Override
    public Organization newOrganization(UUID parentId) {
        Organization org = new Organization();
        org.setParentId(parentId);
        org.setDefaultCurrency(Currency.BGN.getDbResource());
        return org;
    }

    @Override
    public BasicOrganization newBasicOrganization() {
        BasicOrganization basicOrganization = new BasicOrganization();
        basicOrganization.setDefaultCurrency(Currency.BGN.getDbResource());
        return basicOrganization;
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
        organization.setDefaultCurrency(basicOrganization.getDefaultCurrency());
        organization = saveOrganization(organization);

        Person person = personsManager.newPerson(acaciaSession.getOrganization());
        person.setFirstName(basicOrganization.getFirstName());
        person.setSecondName(basicOrganization.getSecondName());
        person.setLastName(basicOrganization.getLastName());
        person.setExtraName(basicOrganization.getExtraName());
        person = personsManager.savePerson(person);

        Address address = addressesManager.newAddress(organization);
        address.setAddressName("Registration");
        address.setCity(basicOrganization.getCity());
        address.setPostalCode(basicOrganization.getPostalCode());
        address.setPostalAddress(basicOrganization.getPostalAddress());
        address = addressesManager.saveAddress(address);

        ContactPerson contactPerson = addressesManager.newContactPerson();
        contactPerson.setPerson(person);
        contactPerson = addressesManager.saveContactPerson(contactPerson, address.getId());

        organization.setRegistrationAddress(address);
        organization = saveOrganization(organization);

        if(basicOrganization.isCustomer()) {
            Classifier classifier = classifiersManager.getClassifier(
                    Classifier.Customer.getClassifierCode());
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

    @Override
    public int deleteOrganization(Organization organization) {
        return esm.remove(em, organization);
    }

    @Override
    public List<Address> getAddresses(UUID parentId) {
       return locationsManager.getAddresses(parentId);
    }

    @Override
    public EntityProperties getAddressEntityProperties() {
       return locationsManager.getAddressEntityProperties();
    }

    @Override
    public EntityProperties getBankDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(BankDetail.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public List<DbResource> getOrganizationTypes() {
        return OrganizationType.getDbResources();
    }
}
