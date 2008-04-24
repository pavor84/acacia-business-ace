/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import com.cosmos.acacia.crm.bl.impl.*;
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
import com.cosmos.acacia.crm.data.DbResource;
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

    @EJB
    private BankDetailsListLocal bankDetailsManager;
        
    public List<Country> getCountries()
    {

        Query q = em.createNamedQuery("Country.fetchAll");

        return new ArrayList<Country>(q.getResultList());
    }

    public EntityProperties getCountryEntityProperties()
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
    	Query q = em.createNamedQuery("City.fetchAll");

        return new ArrayList<City>(q.getResultList());
    }

    public EntityProperties getCityEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(City.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public City newCity() {
    	return new City();
    }

    public City saveCity(City city) {
        esm.persist(em, city);
        return city;
    }

    public int deleteCity(City city) {
        return esm.remove(em, city);
    }

    public List<Address> getAddresses(DataObject parent) {
        if (parent != null) {
            Query query = em.createNamedQuery("Address.findByParentDataObjectAndDeleted");
            query.setParameter("parentDataObject", parent);
            query.setParameter("deleted", false);
            
            return new ArrayList<Address>(query.getResultList());
        } else {
            return new ArrayList<Address>();
        }
               
    }

    public EntityProperties getAddressEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Address.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public Address newAddress() {
        return new Address();
    }

    public Address saveAddress(Address address) {
        esm.persist(em, address);
        return address;
    }

    public int deleteAddress(Address address) {
        return esm.remove(em, address);
    }
    
    public List<DbResource> getCurrencies() {
        return bankDetailsManager.getCurrencies();
    }
}
