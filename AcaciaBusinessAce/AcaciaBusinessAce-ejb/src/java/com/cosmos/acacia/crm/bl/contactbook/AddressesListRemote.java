package com.cosmos.acacia.crm.bl.contactbook;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigInteger;

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
     * Fetches all cities
     *
     * @return list of cities
     */
    List<City> getCities();

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
    Address newAddress();

    /**
     * Saves an address for the specified parent (person or organization)
     * @param address
     * @param parentDataObjectId
     *
     * @return the saved Address
     */
    Address saveAddress(Address address, BigInteger parentDataObjectId);


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
    List<Address> getAddresses(BigInteger parentDataObjectId);

    /* Handling contact persons */

    /**
     * Lists all contact persons for a specified parent (Address data object)
     *
     * @param parentDataObjectId
     * @return list of contact persons
     */
    List<ContactPerson> getContactPersons(BigInteger parentDataObjectId);

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
    ContactPerson newContactPerson();

    /**
     * Saves a ContactPerson for a specified parent (an Address data object)
     *
     * @param contactPerson
     * @param parentDataObjectId
     * @return the saved ContactPerson
     */
    ContactPerson saveContactPerson(ContactPerson contactPerson,
            BigInteger parentDataObjectId);

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
     * @return the list of PositionType's
     */
    List<PositionType> getPositionTypes(DataObject parentDataObject);

    /**
     * Gets a list of all position types for a specified
     * class (Organization or Parent)
     *
     * @param ownerClass
     * @return the list of PositionType's
     */
    @SuppressWarnings("unchecked")
    List<PositionType> getPositionTypes(Class ownerClass);

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
    List<CommunicationContact>
        getCommunicationContacts(BigInteger parentDataObjectId);

    /**
     * Lists all communication contacts belonging to
     * a specified ContactPerson
     *
     * @param contactPerson
     * @return
     */
    List<CommunicationContact>
        getCommunicationContacts(ContactPerson contactPerson);


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
    CommunicationContact newCommunicationContact();

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
            BigInteger parentDataObjectId,
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