/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.location;

import com.cosmos.acacia.crm.data.location.City;
import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.entity.AbstractEntityService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class LocationsServiceBean extends AbstractEntityService
        implements LocationsServiceRemote, LocationsServiceLocal {

    @Override
    public List<Country> getCountries() {
        Query q = em.createNamedQuery(Country.NQ_FIND_ALL);
        return new ArrayList<Country>(q.getResultList());
    }

    @Override
    public Long getCountriesCount() {
        Query q = em.createNamedQuery(Country.NQ_COUNT_COUNTRIES);
        return (Long) q.getSingleResult();
    }

    @Override
    public List<City> getCities(Country country) {
        Query q = em.createNamedQuery(City.NQ_FIND_ALL);
        q.setParameter("country", country);

        return new ArrayList<City>(q.getResultList());
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, List classifiers, Object... extraParameters) {
        if(Country.class == entityClass) {
            return (List<E>) getCountries();
        }

        return super.getEntities(entityClass, classifiers, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, List classifiers, Object... extraParameters) {
        if(City.class == itemClass) {
            return (List<I>) getCities((Country) entity);
        }

        return super.getEntityItems(entity, itemClass, classifiers, extraParameters);
    }
}
