/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DbResource;
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
     * @param parentId
     * @return a list of persons
     */
    List<Person> getPersons(BigInteger parentId);

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
     * @param parentId
     * @return list of addresses
     */
    List<Address> getAddresses(BigInteger parentId);

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
     * @param parentId the parent
     * @return the newly created Person
     */
    Person newPerson(BigInteger parentId);

    /**
     * Saves a Person
     *
     * @param person
     * @return the saved person
     */
    Person savePerson(Person person);

     /**
     * Saves a Person only if the combination of his/her three names is unique
     *
     * @param person
     * @return the saved person or null, of the person is not unique
     */
    Person saveIfUnique(Person person);

    /**
     * Deletes a Person
     *
     * @param person
     * @return the version of the deleted Person
     */
    int deletePerson(Person person);

    /**
     * Lists all the persons registered with the current organization
     * @param parentDataObjecetId
     * @return list of staff persons
     */
    List<Person> getStaff();

    /**
     * List all persons that are Classified as {@link Classifier#Cashier} and are from the current branch
     * @return
     */
    List<Person> getCashiers();
}
