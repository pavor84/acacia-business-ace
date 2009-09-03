package com.cosmos.test.bl.contactbook;

import java.util.List;

import javax.ejb.EJB;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.bl.contactbook.BankDetailsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.LocationsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.PassportsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.PersonsListRemote;
import com.cosmos.acacia.crm.bl.impl.ProductsListBean;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.City;
import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.contacts.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.enums.CommunicationType;
import com.cosmos.acacia.gui.AcaciaPanel;
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

    @EJB
    private PassportsListRemote passportFormSession;

    @EJB
    private LocationsListRemote locationFormSession;

    @Before
    public void setUp() {
        if (addressFormSession == null){
            addressFormSession = AcaciaPanel.getBean(AddressesListRemote.class, false);
        }

        if (organizationFormSession == null){
            organizationFormSession = AcaciaPanel.getBean(OrganizationsListRemote.class, false);
        }

        if (personFormSession == null){
            personFormSession = AcaciaPanel.getBean(PersonsListRemote.class, false);
        }

        if (bankDetailsFormSession == null){
            bankDetailsFormSession = AcaciaPanel.getBean(BankDetailsListRemote.class, false);
        }

        if (passportFormSession == null){
            passportFormSession = AcaciaPanel.getBean(PassportsListRemote.class, false);
        }

        if (locationFormSession == null){
            locationFormSession = AcaciaPanel.getBean(LocationsListRemote.class, false);
        }
    }

    @Test
    public void fetchTest(){
        List<Address> possibleAddresses = addressFormSession.getAddresses(null);
        Assert.assertNotNull(possibleAddresses);

        List<DbResource> ct = addressFormSession.getCommunicationTypes();
        Assert.assertNotNull(ct);

        List<City> cities = addressFormSession.getCities(null);
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

        Organization organization = organizationFormSession.newOrganization(null);
        organization.setOrganizationName(TestUtils.getRandomString(15));

        organization = organizationFormSession.saveOrganization(organization);

        DataObject organizationDataObject = organization.getDataObject();
        Address address = addressFormSession.newAddress();
        address.setAddressName(TestUtils.getRandomString(15));

        System.out.println("DOID" + organizationDataObject.getDataObjectId());
        address = addressFormSession.saveAddress(address, organizationDataObject.getDataObjectId());

        DataObject addressDataObject = address.getDataObject();

        ContactPerson contactPerson = addressFormSession.newContactPerson();
        Person person = personFormSession.newPerson(null);
        person.setFirstName(TestUtils.getRandomString(5));
        person.setSecondName(TestUtils.getRandomString(5));
        person.setLastName(TestUtils.getRandomString(5));
        person.setExtraName(TestUtils.getRandomString(5));
        person = personFormSession.savePerson(person);

        contactPerson.setPerson(person);
        contactPerson.setDataObject(addressDataObject);

        contactPerson = addressFormSession.saveContactPerson(contactPerson, addressDataObject.getDataObjectId());
        CommunicationContact communicationContact = addressFormSession.newCommunicationContact(
                address, CommunicationType.Phone, contactPerson);
        communicationContact.setCommunicationValue(TestUtils.getRandomString(5));
        communicationContact = addressFormSession.saveCommunicationContact(communicationContact, addressDataObject.getDataObjectId(), contactPerson);

        // Modifying the entries

        organization.setOrganizationName(TestUtils.getRandomString(10));
        organization = organizationFormSession.saveOrganization(organization);

        person.setFirstName(TestUtils.getRandomString(6));
        person = personFormSession.savePerson(person);

        //person.setDescription(TestUtils.getRandomString(6));
        person = personFormSession.savePerson(person);

        contactPerson.setPerson(person);
        contactPerson = addressFormSession.saveContactPerson(contactPerson, address.getDataObject().getDataObjectId());

        communicationContact.setCommunicationValue(TestUtils.getRandomString(5));
        communicationContact = addressFormSession.saveCommunicationContact(communicationContact, address.getDataObject().getDataObjectId(), contactPerson);


        // Deleting the entries

        addressFormSession.deleteCommunicationContact(communicationContact);
        addressFormSession.deleteContactPerson(contactPerson);
        personFormSession.deletePerson(person);
        addressFormSession.deleteAddress(address);
        organizationFormSession.deleteOrganization(organization);

    }

    @Test
    public void locationsTest() {

        // adding

        Country country = locationFormSession.newCountry();
        country.setCountryName(TestUtils.getRandomString(10));
        country = locationFormSession.saveCountry(country);

        country.setCountryCodeA2(TestUtils.getRandomString(1));
//        try {
            locationFormSession.saveCountry(country);
//            Assert.fail("Should throw validation exception");
//        } catch (Exception ex) {
//            ValidationException vex = TestUtils.extractValidationException(ex);
//            Assert.assertNotNull(vex);
//            Assert.assertTrue(vex.getMessages().size() > 0);
//        }

        City city = locationFormSession.newCity(country);
        city.setCountry(country);
        city.setCityName(TestUtils.getRandomString(10));

        city = locationFormSession.saveCity(city);

        city.setCityCode(TestUtils.getRandomString(1));
        locationFormSession.saveCity(city);

        // deleting

        locationFormSession.deleteCountry(country);
    }
}
