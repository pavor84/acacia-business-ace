/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

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

/**
 * An EJB for handling persons
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface AddressesListRemote {

    List<Country> getCountries();

    List<City> getCities();
    
    List<City> getCities(Country country);

    /* Handling addresses */

    EntityProperties getAddressEntityProperties();

    Address newAddress();

    Address saveAddress(Address address, DataObject parentDataObject);

    int deleteAddress(Address address);

    List<Address> getAddresses(DataObject parentDataObject);

    /* Handling contact persons */

    List<ContactPerson> getContactPersons(DataObject parent);

    EntityProperties getContactPersonEntityProperties();

    ContactPerson newContactPerson();

    ContactPerson saveContactPerson(ContactPerson contactPerson, DataObject parentDataObject);

    int deleteContactPerson(ContactPerson contactPerson);

    List<PositionType> getPositionTypes(DataObject parentDataObject);

    @SuppressWarnings("unchecked")
    List<PositionType> getPositionTypes(Class ownerClass);

    List<Person> getPersons();

    /* Handling communication contacts */

    List<CommunicationContact> getCommunicationContacts(DataObject parent);

    List<CommunicationContact> getCommunicationContacts(ContactPerson contactPerson);

    EntityProperties getCommunicationContactEntityProperties();

    CommunicationContact newCommunicationContact();

    CommunicationContact saveCommunicationContact(
            CommunicationContact communicationContact,
            DataObject parent,
            ContactPerson contactPerson);

    int deleteCommunicationContact(CommunicationContact communicationContact);

    List<DbResource> getCommunicationTypes();
}