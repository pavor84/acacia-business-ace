/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contacts;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.Person;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface ContactsServiceLocal extends ContactsServiceRemote {

    Person newPerson(BusinessPartner parentBusinessPartner);

    /*Organization newOrganization();

    Address newAddress(BusinessPartner businessPartner);

    CommunicationContact newCommunicationContact(Address address);

    ContactPerson newContactPerson(Address address);

    BankDetail newBankDetail(Address address);

    Passport newPassport(Person person);*/
}
