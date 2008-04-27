package com.cosmos.test.bl.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.impl.AddressesListRemote;
import com.cosmos.acacia.crm.bl.contactbook.impl.BankDetailsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.impl.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.impl.PersonsListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.enums.CommunicationType;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.test.bl.TestUtils;

/**
 *
 * Created	:	06.04.2008
 * @author	Bozhidar Bozhanov
 * @version $Id: $
 *
 * Business logic test for
 * {@link ProductsListBean}
 *
 */
public class ContactBookTest {

    @EJB
    private AddressesListRemote addressFormSession;

    @EJB
    private OrganizationsListRemote organizationFormSession;

    @EJB
    private PersonsListRemote personFormSession;

    @EJB
    private BankDetailsListRemote bankDetailsFormSession;


    @Before
    public void setUp() {
        if (addressFormSession == null){
            try {
                addressFormSession = InitialContext.doLookup(AddressesListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }

        if (organizationFormSession == null){
            try {
                organizationFormSession = InitialContext.doLookup(OrganizationsListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }

        if (personFormSession == null){
            try {
                personFormSession = InitialContext.doLookup(PersonsListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }

        if (bankDetailsFormSession == null){
            try {
                bankDetailsFormSession = InitialContext.doLookup(BankDetailsListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
    }

    @Test
    public void fetchTest(){
        List<Address> possibleAddresses = addressFormSession.getAddresses(null);
        Assert.assertNotNull(possibleAddresses);

        List<DbResource> ct = addressFormSession.getCommunicationTypes();
        Assert.assertNotNull(ct);

        List<City> cities = addressFormSession.getCities();
        Assert.assertNotNull(cities);

        List<Organization> organizations = organizationFormSession.getOrganizations(null);
        Assert.assertNotNull(organizations);

        List<Person> persons = personFormSession.getPersons(null);
        Assert.assertNotNull(persons);

        List<DbResource> communicationTypes = addressFormSession.getCommunicationTypes();
        Assert.assertNotNull(communicationTypes);

        EntityProperties entityProperties =
            addressFormSession.getAddressEntityProperties();
        Assert.assertNotNull(entityProperties);
    }

    @Test
    public void functionalTest() {

        // Creating the entities

        Organization organization = organizationFormSession.newOrganization();
        organization.setOrganizationName(TestUtils.getRandomString(15));

        organization = organizationFormSession.saveOrganization(organization);

        DataObject organizationDataObject = organization.getDataObject();
        Address address = addressFormSession.newAddress();
        address.setAddressName(TestUtils.getRandomString(15));

        address = addressFormSession.saveAddress(address, organizationDataObject);

        DataObject addressDataObject = address.getDataObject();

        ContactPerson contactPerson = addressFormSession.newContactPerson();
        Person person = personFormSession.newPerson();
        person.setFirstName(TestUtils.getRandomString(5));
        person.setSecondName(TestUtils.getRandomString(5));
        person.setLastName(TestUtils.getRandomString(5));
        person.setExtraName(TestUtils.getRandomString(5));
        person = personFormSession.savePerson(person);

        contactPerson.setContact(person);
        contactPerson.setDataObject(addressDataObject);

        contactPerson = addressFormSession.saveContactPerson(contactPerson, addressDataObject);
        CommunicationContact communicationContact = addressFormSession.newCommunicationContact();
        communicationContact.setCommunicationType(addressFormSession.getCommunicationTypes().get(0));
        communicationContact.setCommunicationValue(TestUtils.getRandomString(5));

        communicationContact = addressFormSession.saveCommunicationContact(communicationContact, addressDataObject, contactPerson);

        // Modifying the entries

        organization.setOrganizationName(TestUtils.getRandomString(10));
        organization = organizationFormSession.saveOrganization(organization);

        person.setFirstName(TestUtils.getRandomString(6));
        person = personFormSession.savePerson(person);

        contactPerson.setContact(person);
        contactPerson = addressFormSession.saveContactPerson(contactPerson, address.getDataObject());

        communicationContact.setCommunicationValue(TestUtils.getRandomString(5));
        communicationContact = addressFormSession.saveCommunicationContact(communicationContact, address.getDataObject(), contactPerson);


        // Deleting the entries

        addressFormSession.deleteCommunicationContact(communicationContact);
        addressFormSession.deleteContactPerson(contactPerson);
        personFormSession.deletePerson(person);
        addressFormSession.deleteAddress(address);
        organizationFormSession.deleteOrganization(organization);

    }
}
