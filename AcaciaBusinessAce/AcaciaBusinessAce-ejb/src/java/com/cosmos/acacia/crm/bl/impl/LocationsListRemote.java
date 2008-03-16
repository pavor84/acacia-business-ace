/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.Country;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling location-related entities,
 * namely Countries, Cities and Addresses
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface LocationsListRemote {

    // Methods for handling countries
    List<Country> getCountries();

    EntityProperties getCountriesEntityProperties();

    Country newCountry();

    Country saveCountry(Country country);

    int deleteCountry(Country country);
    // End of methods for handling countries

    // Methods for handling cities
    List<City> getCities();

    EntityProperties getCitiesEntityProperties();

    City newCity();

    City saveCity(City city);

    int deleteCity(City city);
    // End of methods for handlign cities

    // Methods for handling addresses/branches
    List<Address> getAddresses(DataObject parent);

    EntityProperties getAddressesEntityProperties();

    Address newAddress();

    Address saveAddress(Address address);

    int deleteAddress(Address address);
    // End of methods for handlign addresses/branches
}
