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
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling persons
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface AddressesListRemote {

    /* Handlign addresses */

    List<Country> getCountries();

    List<City> getCities();

    EntityProperties getAddressEntityProperties();

    Address newAddress();

    Address saveAddress(Address address);

    int deleteAddress(Address address);

    /* Handling contact persons */

    List<ContactPerson> getContactPersons(DataObject parent);

    EntityProperties getContactPersonEntityProperties();

    ContactPerson newContactPerson();

    ContactPerson saveContactPerson(ContactPerson contactPerson);

    int deleteContactPerson(ContactPerson contactPerson);


    /* Handling communication contacts */

    List<CommunicationContact> getCommunicationContacts(DataObject parent);

    List<CommunicationContact> getCommunicationContacts(ContactPerson contactPerson);

    EntityProperties getCommunicationContactEntityProperties();

    CommunicationContact newCommunicationContact();

    CommunicationContact saveCommunicationContact(CommunicationContact communicationContact);

    int deleteCommunicationContact(CommunicationContact communicationContact);
}