package com.cosmos.acacia.crm.bl.contactbook;

import java.util.List;

import javax.ejb.Remote;

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
import java.util.UUID;

/**
 * An EJB for handling persons
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface AddressesListRemote {

    /**
     * Fetches all countries
     *
     * @return list of countries
     */
    List<Country> getCountries();

    /**
     * Fetches all the cities in the specified country
     *
     * @param country
     * @return list of cities
     */
    List<City> getCities(Country country);

    /* Handling addresses */

    /**
     * Gets the EntityProperties of the Address entity
     *
     * @return the entity properties
     */
    EntityProperties getAddressEntityProperties();

    /**
     * Creates a new, empty Address
     *
     * @return the newly created Address
     */
    Address newAddress(BusinessPartner businessPartner);

    /**
     * Saves an address for the specified parent (person or organization)
     * @param address
     * @param parentDataObjectId
     *
     * @return the saved Address
     */
    Address saveAddress(Address address);


    /**
     * Deleted the specified Address
     * @param address
     *
     * @return the version of the deleted Address
     */
    int deleteAddress(Address address);

    /**
     * Lists all addresses for a specified parent (Person or Organization)
     *
     * @param parentDataObjectId
     * @return list of addresses
     */
    List<Address> getAddresses(BusinessPartner businessPartner);

    /* Handling contact persons */

    /**
     * Lists all contact persons for a specified parent (Address data object)
     *
     * @param parentDataObjectId
     * @return list of contact persons
     */
    List<ContactPerson> getContactPersons(UUID parentDataObjectId);

    /**
     * 
     * @param address
     * @param person
     * @return
     */
    ContactPerson getContactPerson(Address address, Person person);

    /**
     * Gets the EntityProperties for a ContactPerson entity
     *
     * @return entity properties
     */
    EntityProperties getContactPersonEntityProperties();


    /**
     * Creates a new, empty ContactPerson
     *
     * @return the newly created ContactPerson
     */
    ContactPerson newContactPerson(Address address);

    /**
     * Saves a ContactPerson for a specified parent (an Address data object)
     *
     * @param contactPerson
     * @param parentDataObjectId
     * @return the saved ContactPerson
     */
    ContactPerson saveContactPerson(ContactPerson contactPerson);

    /**
     * Deletes a ContactPerson
     *
     * @param contactPerson
     * @return the version of the deleted ContactPerson
     */
    int deleteContactPerson(ContactPerson contactPerson);

    /**
     * Gets a list of all position types for a specified
     * parent object (Organization or Person)
     *
     * @param parentDataObject
     * @param parentId the id of the current logged organization
     * @return the list of PositionType's
     */
    List<PositionType> getPositionTypes(DataObject parentDataObject, UUID parentId);

    /**
     * Gets a list of all position types for a specified
     * class (Organization or Parent)
     *
     * @param ownerClass
     * @param parentId
     * @return the list of PositionType's
     */
    @SuppressWarnings("unchecked")
    List<PositionType> getPositionTypes(Class ownerClass, UUID parentDataObjectId);

    /**
     * Lists all persons
     *
     * @return a list of persons
     */
    List<Person> getPersons();

    /* Handling communication contacts */

    /**
     * Lists all communication contacts belonging to
     * a specified parent (Address data object)
     *
     * @param parentDataObjectId
     * @return a list of communication contacts
     */
    List<CommunicationContact> getCommunicationContacts(Address address);

    List<CommunicationContact> getCommunicationContacts(Address address, CommunicationType communicationType);

    /**
     * Lists all communication contacts belonging to
     * a specified ContactPerson
     *
     * @param contactPerson
     * @return
     */
    List<CommunicationContact> getCommunicationContacts(ContactPerson contactPerson);


    /**
     * Gets the EntityProperties of CommunicationContact
     *
     * @return the entity properties
     */
    EntityProperties getCommunicationContactEntityProperties();

    /**
     * Creates a new, empty CommunicationContact
     *
     * @return the newly created CommunicationContact
     */
    CommunicationContact newCommunicationContact(
            Address address,
            CommunicationType communicationType,
            ContactPerson contactPerson);

    /**
     * Saves a CommunicationContact for specified parent (Address data object)
     * and a ContactPerson. The latter may be null, indicating the the
     * CommunicationContact belongs to the address, rather than to a specific
     * Person at that Address
     *
     * @param communicationContact
     * @param parentDataObjectId
     * @param contactPerson
     * @return the saved CommunicationContact
     */
    CommunicationContact saveCommunicationContact(
            CommunicationContact communicationContact,
            UUID parentDataObjectId,
            ContactPerson contactPerson);

    /**
     * Deletes a CommunicationContact
     *
     * @param communicationContact
     * @return the version of the deleted CommunicationContact
     */
    int deleteCommunicationContact(CommunicationContact communicationContact);

    /**
     * Lists all CommunicationType's
     *
     * @return list of communication types
     */
    List<DbResource> getCommunicationTypes();
}