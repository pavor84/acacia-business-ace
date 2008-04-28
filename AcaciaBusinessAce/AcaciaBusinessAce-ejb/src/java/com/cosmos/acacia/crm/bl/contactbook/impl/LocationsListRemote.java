package com.cosmos.acacia.crm.bl.contactbook.impl;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.Country;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling location-related entities,
 * namely Countries, Cities and Addresses
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface LocationsListRemote {

    /**
     * Listing all currencies
     *
     * @return list of currencies
     */
    List<DbResource> getCurrencies();

    // Methods for handling countries

    /**
     * Listing all countries
     *
     * @return list of countries
     */
    List<Country> getCountries();

    /**
     * Gets the EntityProperties for Country
     *
     * @return the entity properties
     */
    EntityProperties getCountryEntityProperties();

    /**
     * Creates a new country
     *
     * @return the newly created country
     */
    Country newCountry();

    /**
     * Saves a country
     *
     * @param country
     * @return the saved country
     */
    Country saveCountry(Country country);

    /**
     * Deletes a country
     *
     * @param country
     * @return the version of the deleted country
     */
    int deleteCountry(Country country);
    // End of methods for handling countries

    // Methods for handling cities

    /**
     * Lists all cities
     *
     * @return list of cities
     */
    List<City> getCities();

    /**
     * Lists all cities belonging to a specified country
     *
     * @param country
     * @return list of cities
     */
    List<City> getCities(Country country);

    /**
     * Gets the EntityProperties for City
     *
     * @return the entity properties
     */
    EntityProperties getCityEntityProperties();

    /**
     * Creates a new City
     *
     * @return the newly created city
     */
    City newCity();

    /**
     * Saves a City
     *
     * @param city
     * @return the saved city
     */
    City saveCity(City city);

    /**
     * Deletes a City
     *
     * @param city
     * @return the version of the deleted city
     */
    int deleteCity(City city);
    // End of methods for handlign cities

    // Methods for handling addresses/branches

    /**
     * Lists all addresses belonging to a specified parent object
     * (Organization or Person)
     *
     * @param parent
     * @return list of addresses
     */
    List<Address> getAddresses(DataObject parent);

    /**
     * Gets the EntityProperties of Address
     *
     * @return the entity properties
     */
    EntityProperties getAddressEntityProperties();

    /**
     * Creates a new Address
     *
     * @return the newly created address
     */
    Address newAddress();

    /**
     * Saves an Address
     *
     * @param address
     * @return the saved address
     */
    Address saveAddress(Address address);


    /**
     * Deletes an Address
     *
     * @param address
     * @return the version of the deleted address
     */
    int deleteAddress(Address address);

    // End of methods for handling addresses/branches
}
