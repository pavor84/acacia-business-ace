/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Passport;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Implementation of handling persons (see interface for more information)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class AddressesListBean implements AddressesListRemote, AddressesListLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private LocationsListLocal locationsManager;

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

    public EntityProperties getPersonEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(Person.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public Address newAddress() {
        return new Address();
    }

    public Address saveAddress(Address address) {
        esm.persist(em, address);
        return address;
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

    public List<CommunicationContact> getCommunicationContacts(DataObject parent) {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("CommunicationContact.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObject", parent);
        }
        else
        {
            q = em.createNamedQuery("CommunicationContact.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);

        return new ArrayList<CommunicationContact>(q.getResultList());
    }

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

    public List<ContactPerson> getContactPersons(DataObject parent) {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObject", parent);
        }
        else
        {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);

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
            CommunicationContact communicationContact) {

        esm.persist(em, communicationContact);
        return communicationContact;
    }

    @Override
    public ContactPerson saveContactPerson(ContactPerson contactPerson) {
        esm.persist(em, contactPerson);
        return contactPerson;
    }
}
