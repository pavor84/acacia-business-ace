/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Passport;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling persons
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface PersonsListRemote {

    List<Person> getPersons(DataObject parent);

    List<Country> getCountries();

    List<City> getCities();

    List<City> getCities(Country country);

    List<Address> getAddresses(DataObject parent);

    List<Passport> getPassports(DataObject parent);

    List<DbResource> getGenders();

    EntityProperties getPersonEntityProperties();

    EntityProperties getAddressEntityProperties();

    EntityProperties getPassportEntityProperties();

    Person newPerson();

    Person savePerson(Person person);

    int deletePerson(Person person);
}
