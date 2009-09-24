/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.contactbook;

import java.util.UUID;
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

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.location.City;
import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.PositionType;
import com.cosmos.acacia.crm.enums.CommunicationType;
import com.cosmos.beansbinding.EntityProperties;
import javax.persistence.NoResultException;

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
    private PositionTypesListLocal positionTypesManager;

//    @EJB
//    private UserRightsLocal groupsManager;
//    @EJB
//    private UsersLocal usersManager;
    /**
     * A method for listing all existing countries
     *
     * @param parent
     * @return the country-list
     */
    public List<Country> getCountries() {
        return locationsManager.getCountries();
    }

    /**
     * A method for listing all existing cities
     *
     * @param parent
     * @return the city-list
     */
    @Override
    public List<City> getCities(Country country) {
        return locationsManager.getCities(country);
    }

    @Override
    public Address newAddress(BusinessPartner businessPartner) {
        return locationsManager.newAddress(businessPartner);
    }

    @Override
    public Address saveAddress(Address address) {
        return locationsManager.saveAddress(address);
    }

    public int deleteAddress(Address address) {
//        // TODO: Transaction required! (in case automatic transactions are off
//        if (address.getUserGroup() != null)
//            esm.remove(em, address.getUserGroup());

        return esm.remove(em, address);
    }

    public List<Address> getAddresses(BusinessPartner businessPartner) {
        return locationsManager.getAddresses(businessPartner);
    }

    public EntityProperties getAddressEntityProperties() {
        return locationsManager.getAddressEntityProperties();
    }

    @Override
    public List<CommunicationContact> getCommunicationContacts(Address address) {
        return getCommunicationContacts(address, null);
    }

    @Override
    public List<CommunicationContact> getCommunicationContacts(Address address, CommunicationType communicationType) {
        if (address == null || address.getAddressId() == null) {
            return new ArrayList<CommunicationContact>();
        }

        Query q;
        if (communicationType == null) {
            q = em.createNamedQuery(CommunicationContact.NQ_FIND_ALL);
        } else {
            q = em.createNamedQuery(CommunicationContact.NQ_FIND_BY_COMMUNICATION_TYPE);
            q.setParameter("communicationType", communicationType.getDbResource());
        }
        q.setParameter("parentId", address.getAddressId());
        q.setParameter("deleted", false);

        return new ArrayList<CommunicationContact>(q.getResultList());
    }

    @Override
    public List<CommunicationContact> getCommunicationContacts(ContactPerson contactPerson) {
        if (contactPerson == null || contactPerson.getContactPersonId() == null) {
            return new ArrayList<CommunicationContact>();
        }

        Query q = em.createNamedQuery(CommunicationContact.NQ_FIND_BY_CONTACT_PERSON);
        q.setParameter("contactPerson", contactPerson);
        q.setParameter("deleted", false);

        return new ArrayList<CommunicationContact>(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    public List<ContactPerson> getContactPersons(UUID parentId) {
        Query q;
        if (parentId != null) {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentId);
        } else {
            q = em.createNamedQuery("ContactPerson.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);

        log.info("Parent: " + parentId);

        return new ArrayList<ContactPerson>(q.getResultList());
    }

    @Override
    public ContactPerson getContactPerson(Address address, Person person) {
        if (address == null || address.getId() == null || person == null || person.getId() == null) {
            return null;
        }

        Query q = em.createNamedQuery("ContactPerson.findByAddressIdAndPerson");
        q.setParameter("addressId", address.getAddressId());
        q.setParameter("person", person);
        try {
            return (ContactPerson) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public int deleteCommunicationContact(
            CommunicationContact communicationContact) {

        return esm.remove(em, communicationContact);
    }

    @Override
    public int deleteContactPerson(ContactPerson contactPerson) {
//        UserOrganization uo =
//            usersManager.getUserOrganization(contactPerson.getPerson());
//
//        // clean group only if the group is underscored (a branch group)
//        if (uo.getUserGroup().getName().indexOf("_") > -1)
//                uo.setUserGroup(null);

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
    public CommunicationContact newCommunicationContact(
            Address address,
            CommunicationType communicationType,
            ContactPerson contactPerson) {
        CommunicationContact communicationContact = new CommunicationContact();
        communicationContact.setParentId(address.getAddressId());
        if(communicationType != null) {
            communicationContact.setCommunicationType(communicationType.getDbResource());
        }
        //communicationContact.setContactPerson(contactPerson);
        return communicationContact;
    }

    @Override
    public ContactPerson newContactPerson(Address address) {
        ContactPerson contactPerson = new ContactPerson(address);
        return contactPerson;
    }

    @Override
    public CommunicationContact saveCommunicationContact(
            CommunicationContact communicationContact,
            UUID parentDataObjectId,
            ContactPerson contactPerson) {

        // communicationContact.setContactPerson(contactPerson);
        if (parentDataObjectId != null) {
            communicationContact.setParentId(parentDataObjectId);
        }

        esm.persist(em, communicationContact);
        return communicationContact;
    }

    @Override
    public ContactPerson saveContactPerson(ContactPerson contactPerson) {
        esm.persist(em, contactPerson);

        return contactPerson;
    }

    public List<PositionType> getPositionTypes(DataObject parent, UUID parentId) {
        try {
            return getPositionTypes(
                    Class.forName(parent.getDataObjectType().getDataObjectType()), parentId);
        } catch (Exception ex) {
            return new LinkedList<PositionType>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<PositionType> getPositionTypes(Class ownerClass, UUID parentId) {
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
}
