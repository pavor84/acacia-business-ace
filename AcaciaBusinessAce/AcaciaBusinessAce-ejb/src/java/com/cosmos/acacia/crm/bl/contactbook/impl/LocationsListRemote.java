/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

    List<DbResource> getCurrencies();

    // Methods for handling countries
    List<Country> getCountries();

    EntityProperties getCountryEntityProperties();

    Country newCountry();

    Country saveCountry(Country country);

    int deleteCountry(Country country);
    // End of methods for handling countries

    // Methods for handling cities
    List<City> getCities();

    List<City> getCities(Country country);

    EntityProperties getCityEntityProperties();

    City newCity();

    City saveCity(City city);

    int deleteCity(City city);
    // End of methods for handlign cities

    // Methods for handling addresses/branches
    List<Address> getAddresses(DataObject parent);

    EntityProperties getAddressEntityProperties();

    Address newAddress();

    Address saveAddress(Address address);

    int deleteAddress(Address address);
    // End of methods for handlign addresses/branches
}
