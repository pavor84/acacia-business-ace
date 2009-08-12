/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.validation.CityValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.EntitySequenceServiceLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.validation.GenericUniqueValidatorLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.City;
import com.cosmos.acacia.crm.data.contacts.Country;
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
    private EntitySequenceServiceLocal entitySequenceService;
    @EJB
    private BankDetailsListLocal bankDetailsManager;
    @EJB
    private GenericUniqueValidatorLocal<Country> validator;

    @EJB
    private CityValidatorLocal cityValidator;

    @SuppressWarnings("unchecked")
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

        validator.validate(country, "countryName");

        esm.persist(em, country);
        return country;
    }

    public int deleteCountry(Country country) {
        return esm.remove(em, country);
    }

    @SuppressWarnings("unchecked")
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
        cityValidator.validate(city);

        esm.persist(em, city);
        return city;
    }

    public int deleteCity(City city) {
        return esm.remove(em, city);
    }

    @SuppressWarnings("unchecked")
    public List<Address> getAddresses(BigInteger parentId) {
        if (parentId == null) {
            return new ArrayList<Address>();
        }

        Query query = em.createNamedQuery("Address.findByParentDataObjectAndDeleted");
        query.setParameter("parentDataObjectId", parentId);
        query.setParameter("deleted", false);

        return new ArrayList<Address>(query.getResultList());
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

    @SuppressWarnings("unchecked")
    @Override
    public List<City> getCities(Country country) {
        Query q = em.createNamedQuery("City.findByCountry");
        q.setParameter("country", country);

        return new ArrayList<City>(q.getResultList());
    }
}
