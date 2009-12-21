/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contacts;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BankDetail;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Passport;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.PositionType;
import java.util.List;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface ContactsServiceLocal extends ContactsServiceRemote {

    List<BankDetail> getBankDetails(Address address);

    List<CommunicationContact> getCommunicationContacts(Address address);

    List<ContactPerson> getContactPersons(Address address);

    List<Passport> getPassports(Person person);

    List<PositionType> getPositionTypes(BusinessPartner businessPartner);

    List<Address> getAddresses(BusinessPartner businessPartner);

    List<Address> getAddresses(BusinessPartner businessPartner, String namePrefix);

    Person newPerson();

    List<Person> getPersons(UUID parentBusinessPartnerId);

    List<Organization> getOrganizations(UUID parentBusinessPartnerId, List<Classifier> classifiers);

    Organization newOrganization();

    Organization saveOrganization(Organization organization);

    /*Address newAddress(BusinessPartner businessPartner);

    CommunicationContact newCommunicationContact(Address address);

    ContactPerson newContactPerson(Address address);

    BankDetail newBankDetail(Address address);

    Passport newPassport(Person person);*/
}
