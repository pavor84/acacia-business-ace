/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.Country;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.beansbinding.EntityProperties;

/**
 * The implementation of handling locations (see interface for more info)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class LocationsListBean implements LocationsListRemote, LocationsListLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    public List<Country> getCountries()
    {

        Query q = em.createNamedQuery("Country.findAll");

        return new ArrayList<Country>(q.getResultList());
    }

    public EntityProperties getCountriesEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(Country.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public Country newCountry() {
        return new Country();
    }

    public Country saveCountry(Country country) {
        esm.persist(em, country);
        return country;
    }

    public int deleteCountry(Country country) {
        return esm.remove(em, country);
    }

    public List<City> getCities() {
    	Query q = em.createNamedQuery("Country.findAll");

        return new ArrayList<City>(q.getResultList());
    }

    public EntityProperties getCitiesEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(City.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public City newCity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public City saveCity(City city) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int deleteCity(City city) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Address> getAddresses(DataObject parent) {
        // TODO : implement
        return new ArrayList<Address>();
    }

    public EntityProperties getAddressesEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Address.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public Address newAddress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Address saveAddress(Address address) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int deleteAddress(Address address) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
