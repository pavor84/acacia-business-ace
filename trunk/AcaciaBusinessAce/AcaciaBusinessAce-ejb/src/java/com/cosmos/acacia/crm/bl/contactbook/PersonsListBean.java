package com.cosmos.acacia.crm.bl.contactbook;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.validation.PersonValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.ClassifiersLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.users.UsersLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.City;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.Country;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Passport;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.enums.Gender;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;

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

    @EJB
    private AcaciaSessionLocal session;
    
    @EJB
    private ClassifiersLocal classifersManager;
    
    @EJB
    private UsersLocal usersManager;
    
    @SuppressWarnings("unchecked")
    public List<Person> getPersons(UUID parentId)
    {

        Query q;
        if(parentId != null)
        {
            q = em.createNamedQuery("Person.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentId);
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

    public Person newPerson(UUID parentId) {
        Person p = new Person();
        p.setParentId(parentId);
        return p;
    }

    public Person savePerson(Person person) {
        personValidator.validate(person);
        esm.persist(em, person);
        return person;
    }

    @SuppressWarnings("unchecked")
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

        if (results != null && results.size() > 0)
            return null;

        return savePerson(person);
    }

    public int deletePerson(Person person) {
        return esm.remove(em, person);
    }

    public List<Address> getAddresses(UUID parentId) {
       return locationsManager.getAddresses(parentId);
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

    @Override
    public List<Person> getStaff() {
        List<User> users = usersManager.getUsers(session.getOrganization());
        List<Person> result = new ArrayList<Person>(users.size());
        for (User user : users) {
            Person person = user.getPerson();
            if (person != null)
                result.add(person);
        }
        return result;
    }

    @Override
    public List<Person> getCashiers() {
        Classifier cashierClassifer = classifersManager.getClassifier(Classifier.Cashier.getClassifierCode()); 
        
        List cashiers = (List)
            AcaciaUtils.getResultList(em, "Person.getCassifiedFromBranch", 
            "branchId", session.getBranch().getAddressId(),
            "classifierId", cashierClassifer.getClassifierId()
            );
        
        //double check if the result only contains persons, otherwise it will blow ugly
        for (Iterator iterator = cashiers.iterator(); iterator.hasNext();) {
            if ( !(iterator.next() instanceof Person) )
                iterator.remove();
            
        }
        return cashiers;
    }
}
