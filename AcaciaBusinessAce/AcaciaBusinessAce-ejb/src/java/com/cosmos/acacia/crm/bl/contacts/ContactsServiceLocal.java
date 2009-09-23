/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contacts;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface ContactsServiceLocal extends ContactsServiceRemote {

    Person newPerson(BusinessPartner parentBusinessPartner);

    List<Person> getPersons(UUID parentBusinessPartnerId);

    List<Organization> getOrganizations(UUID parentBusinessPartnerId);

    Organization newOrganization();

    Organization saveOrganization(Organization organization);

    /*Address newAddress(BusinessPartner businessPartner);

    CommunicationContact newCommunicationContact(Address address);

    ContactPerson newContactPerson(Address address);

    BankDetail newBankDetail(Address address);

    Passport newPassport(Person person);*/
}
