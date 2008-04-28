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

    /**
     * Gets the persons for a specified parent.
     * If parent is null, all persons are returned.
     *
     * @param parent
     * @return a list of persons
     */
    List<Person> getPersons(DataObject parent);

    /**
     * Gets a list of all countries
     *
     * @return list of countries
     */
    List<Country> getCountries();

    /**
     * Gets a list of all cities
     *
     * @return list of cities
     */
    List<City> getCities();

    /**
     * Gets a list of all cities for a specified country
     *
     * @param country
     * @return list of cities
     */
    List<City> getCities(Country country);

    /**
     * Lists all the addresses for a parent (Person data object)
     *
     * @param parent
     * @return list of addresses
     */
    List<Address> getAddresses(DataObject parent);

    /**
     * Lists all the passports for a parent (Person data object)
     *
     * @param parent
     * @return list of passports
     */
    List<Passport> getPassports(DataObject parent);

    /**
     * Gets all the (two) possible genders
     *
     * @return the genders
     */
    List<DbResource> getGenders();

    /**
     * Gets the EntityProperties of Person
     *
     * @return the entity properties
     */
    EntityProperties getPersonEntityProperties();

    /**
     * Gets the EntityProperties of Address
     *
     * @return the entity properties
     */
    EntityProperties getAddressEntityProperties();

    /**
     * Gets the EntityProperties of Passport
     *
     * @return the entity properties
     */
    EntityProperties getPassportEntityProperties();

    /**
     * Creates a new, empty Person instance
     *
     * @return the newly created Person
     */
    Person newPerson();

    /**
     * Saves a Person
     *
     * @param person
     * @return the saved person
     */
    Person savePerson(Person person);

    /**
     * Deletes a Person
     *
     * @param person
     * @return the version of the deleted Person
     */
    int deletePerson(Person person);
}
