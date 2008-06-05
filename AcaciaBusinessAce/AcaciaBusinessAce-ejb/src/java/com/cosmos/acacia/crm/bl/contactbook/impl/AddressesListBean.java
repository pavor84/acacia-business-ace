/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.validation.AddressValidatorLocal;
import com.cosmos.acacia.crm.bl.contactbook.validation.CommunicationContactValidatorLocal;
import com.cosmos.acacia.crm.bl.contactbook.validation.ContactPersonValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.enums.CommunicationType;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigInteger;

/**
 * Implementation of handling persons (see interface for more information)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class AddressesListBean implements AddressesListRemote, AddressesListLocal {

    protected static Logger log = Logger.getLogger(AddressesListBean.class);

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private LocationsListLocal locationsManager;

    @EJB
    private PersonsListLocal personManager;

    @EJB
    private AddressValidatorLocal addressValidator;

    @EJB
    private ContactPersonValidatorLocal contactPersonValidator;

    @EJB
    private CommunicationContactValidatorLocal communicationContactValidator;

    @EJB
    private PositionTypesListLocal positionTypesManager;

    /**
     * A method for listing all existing countries
     *
     * @param parent
     * @return the country-list
     */
    public List<Country> getCountries()
    {
        return locationsManager.getCountries();
    }

    /**
     * A method for listing all existing cities
     *
     * @param parent
     * @return the city-list
     */
    public List<City> getCities()
    {
        return locationsManager.getCities();
    }

    public Address newAddress() {
        return locationsManager.newAddress();
    }

    public Address saveAddress(Address address, BigInteger parentDataObjectId)
    {
        address.setParentId(parentDataObjectId);

        if (address.getDataObject() == null){
            DataObject dataObject = new DataObject();
            dataObject.setParentDataObjectId(parentDataObjectId);
            address.setDataObject(dataObject);
        }

        addressValidator.validate(address);

        return locationsManager.saveAddress(address);
    }

    public int deleteAddress(Address address) {
        return esm.remove(em, address);
    }

    public List<Address> getAddresses(DataObject parent) {
       return locationsManager.getAddresses(parent);
    }

    public EntityProperties getAddressEntityProperties() {
       return locationsManager.getAddressEntityProperties();
    }

    @SuppressWarnings("unchecked")
    public List<CommunicationContact> getCommunicationContacts(DataObject parent) {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("CommunicationContact.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("CommunicationContact.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);

        return new ArrayList<CommunicationContact>(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    public List<CommunicationContact> getCommunicationContacts(ContactPerson contactPerson) {
        Query q;
        if(contactPerson != null)
        {
            q = em.createNamedQuery("CommunicationContact.findByContactPerson");
            q.setParameter("contactPerson", contactPerson);
        }
        else
        {
            q = em.createNamedQuery("CommunicationContact.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);

        return new ArrayList<CommunicationContact>(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    public List<ContactPerson> getContactPersons(DataObject parent) {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);

        log.info("Parent: " + parent);

        return new ArrayList<ContactPerson>(q.getResultList());
    }

    @Override
    public int deleteCommunicationContact(
            CommunicationContact communicationContact) {

        return esm.remove(em, communicationContact);
    }

    @Override
    public int deleteContactPerson(ContactPerson contactPerson) {

        return esm.remove(em, contactPerson);
    }

    @Override
    public EntityProperties getCommunicationContactEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(CommunicationContact.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public EntityProperties getContactPersonEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(ContactPerson.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public CommunicationContact newCommunicationContact() {
        return new CommunicationContact();
    }

    @Override
    public ContactPerson newContactPerson() {
       return new ContactPerson();
    }

    @Override
    public CommunicationContact saveCommunicationContact(
            CommunicationContact communicationContact,
            DataObject parentDataObject,
            ContactPerson contactPerson)
    {

        communicationContact.setContactPerson(contactPerson);
        if (parentDataObject != null)
        {
            communicationContact.setParentId(parentDataObject.getDataObjectId());
            if (communicationContact.getDataObject() == null){
                DataObject dataObject = new DataObject();
                dataObject.setParentDataObjectId(parentDataObject.getDataObjectId());
                communicationContact.setDataObject(dataObject);
            }
        }

        communicationContactValidator.validate(communicationContact);

        esm.persist(em, communicationContact);
        return communicationContact;
    }

    @Override
    public ContactPerson saveContactPerson(ContactPerson contactPerson, DataObject parentDataObject) {

        contactPerson.setParentId(parentDataObject.getDataObjectId());
        if (contactPerson.getDataObject() == null){
            DataObject dataObject = new DataObject();
            if(parentDataObject != null)
                dataObject.setParentDataObjectId(parentDataObject.getDataObjectId());
            contactPerson.setDataObject(dataObject);
        }

        contactPersonValidator.validate(contactPerson);

        esm.persist(em, contactPerson);

        return contactPerson;
    }

    public List<PositionType> getPositionTypes(DataObject parent) {
        try {
            return getPositionTypes(
                    Class.forName(parent.getDataObjectType().getDataObjectType()));
        } catch (Exception ex) {
            return new LinkedList<PositionType>();
        }
    }

   @SuppressWarnings("unchecked")
   public List<PositionType> getPositionTypes(Class ownerClass) {
       try {
            return positionTypesManager.getPositionTypes(ownerClass);
       } catch (Exception ex) {
           ex.printStackTrace();
           return new LinkedList<PositionType>();
       }
    }


    public List<Person> getPersons() {
        return personManager.getPersons(null);
    }

    public List<DbResource> getCommunicationTypes() {
        return CommunicationType.getDbResources();
    }

    public List<City> getCities(Country country)
    {
        return locationsManager.getCities(country);
    }

}