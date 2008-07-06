/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

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
        if (parentDataObjectId != null)
            address.setParentId(parentDataObjectId);

        addressValidator.validate(address);

        return locationsManager.saveAddress(address);
    }

    public int deleteAddress(Address address) {
        return esm.remove(em, address);
    }

    public List<Address> getAddresses(BigInteger parentId) {
       return locationsManager.getAddresses(parentId);
    }

    public EntityProperties getAddressEntityProperties() {
       return locationsManager.getAddressEntityProperties();
    }

    @SuppressWarnings("unchecked")
    public List<CommunicationContact> getCommunicationContacts(BigInteger parentId) {
        Query q;
        if(parentId != null)
        {
            q = em.createNamedQuery("CommunicationContact.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentId);
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
    public List<ContactPerson> getContactPersons(BigInteger parentId) {
        Query q;
        if(parentId != null)
        {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentId);
        }
        else
        {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);

        log.info("Parent: " + parentId);

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
            BigInteger parentDataObjectId,
            ContactPerson contactPerson)
    {

        communicationContact.setContactPerson(contactPerson);
        if (parentDataObjectId != null)
        {
            communicationContact.setParentId(parentDataObjectId);
        }

        communicationContactValidator.validate(communicationContact);

        esm.persist(em, communicationContact);
        return communicationContact;
    }

    @Override
    public ContactPerson saveContactPerson(ContactPerson contactPerson, BigInteger parentDataObjectId) {

        //if (parentDataObjectId != contactPerson.getParentId())
            contactPerson.setParentId(parentDataObjectId);

        contactPersonValidator.validate(contactPerson);

        esm.persist(em, contactPerson);

        return contactPerson;
    }

    public List<PositionType> getPositionTypes(DataObject parent, BigInteger parentId) {
        try {
            return getPositionTypes(
                    Class.forName(parent.getDataObjectType().getDataObjectType()), parentId);
        } catch (Exception ex) {
            return new LinkedList<PositionType>();
        }
    }

   @SuppressWarnings("unchecked")
   public List<PositionType> getPositionTypes(Class ownerClass, BigInteger parentId) {
       try {
            return positionTypesManager.getPositionTypes(ownerClass, parentId);
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
