package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.validation.PersonValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Passport;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.enums.Gender;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import org.apache.log4j.Logger;

/**
 * Implementation of handling persons (see interface for more information)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class PersonsListBean implements PersonsListRemote, PersonsListLocal {

    protected Logger log = Logger.getLogger(PersonsListBean.class);
    
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private LocationsListLocal locationsManager;

    @EJB
    private PersonValidatorLocal personValidator;

    @SuppressWarnings("unchecked")
    public List<Person> getPersons(DataObject parent)
    {

        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("Person.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObject", parent);
        }
        else
        {
            q = em.createNamedQuery("Person.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<Person>(q.getResultList());
    }

    public List<Country> getCountries()
    {
        return locationsManager.getCountries();
    }

    public List<City> getCities()
    {
        return locationsManager.getCities();
    }

    public EntityProperties getPersonEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(Person.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public Person newPerson() {
        return new Person();
    }

    public Person savePerson(Person person) {
        personValidator.validate(person);
        esm.persist(em, person);
        return person;
    }

    public Person saveIfUnique(Person person) {
    
        // Not using a NamedQuery, because it will complicate things unnecessarily
        
        String query = "select p from Person p where " +
                "p.firstName = :firstName and " +
                "p.lastName = :lastName and ";
        
        if (person.getSecondName() != null)
            query += "p.secondName = :secondName and ";
        else
            query += "p.secondName is null and ";
        
        
        if (person.getExtraName() != null)
            query += "p.extraName = :extraName and ";
        else
            query += "p.extraName is null and ";
        
        
        query += "p.dataObject.deleted = false";
            
        Query q = em.createQuery(query);
        
        q.setParameter("firstName", person.getFirstName());
        q.setParameter("lastName", person.getLastName());
        if (person.getSecondName() != null)
            q.setParameter("secondName", person.getSecondName());
        
        if (person.getExtraName() != null)
            q.setParameter("extraName", person.getExtraName());
        
        List results = q.getResultList();
        log.info("Existing persons: " + results.size());
        
        if (results != null && results.size() > 0)
            return null;
        
        return savePerson(person);
    }
    
    public int deletePerson(Person person) {
        try {
            return esm.remove(em, person);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidationException(ex);
        }
    }

    public List<Address> getAddresses(DataObject parent) {
       return locationsManager.getAddresses(parent);
    }

    public List<Passport> getPassports(DataObject parent) {
        // TODO implement
        return new ArrayList<Passport>();
    }

    public EntityProperties getAddressEntityProperties() {
       return locationsManager.getAddressEntityProperties();
    }

    public EntityProperties getPassportEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Passport.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public List<DbResource> getGenders() {
        return Gender.getDbResources();
    }

    @Override
    public List<City> getCities(Country country) {
        return locationsManager.getCities(country);
    }
}
