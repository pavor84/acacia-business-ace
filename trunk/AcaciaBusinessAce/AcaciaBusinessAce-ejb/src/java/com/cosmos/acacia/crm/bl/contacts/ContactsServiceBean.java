/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contacts;

import com.cosmos.acacia.crm.bl.users.UsersServiceLocal;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.enums.BusinessUnitType;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.entity.AbstractEntityService;
import com.cosmos.beansbinding.EntityProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class ContactsServiceBean extends AbstractEntityService implements ContactsServiceRemote, ContactsServiceLocal {

    @EJB
    private UsersServiceLocal usersService;

    @Override
    public Organization saveOrganization(Organization organization) {
        esm.persist(em, organization);
        postSaveOrganization(organization);
        return organization;
    }

    private void postSaveOrganization(Organization organization) {
        if(!session.getSystemOrganization().getBusinessPartnerId().equals(organization.getParentBusinessPartnerId())) {
            return;
        }

        if(usersService.getRootBusinessUnit(organization) == null) {
            BusinessUnit rootBusinessUnit = new BusinessUnit(organization);
            rootBusinessUnit.setBusinessUnitName(BusinessUnit.ROOT_BUSINESS_UNIT_NAME);
            rootBusinessUnit.setBusinessUnitType(BusinessUnitType.Administrative.getDbResource());
            rootBusinessUnit.setRoot(true);
            rootBusinessUnit.setDisabled(false);
            usersService.save(rootBusinessUnit);

            session.sendSystemMail(organization.toString(), "New user Organization: " + organization.getInfo());
        }
    }

    @Override
    public Organization newOrganization() {
        Organization sessionOrganization = session.getOrganization();
        Organization organization = new Organization();
        organization.setParentId(sessionOrganization.getBusinessPartnerId());
        organization.setDefaultCurrency(Currency.BGN.getDbResource());
        return organization;
    }

    @Override
    public List<Person> getPersons(UUID parentBusinessPartnerId) {
        if(parentBusinessPartnerId == null) {
            throw new NullPointerException("parentBusinessPartnerId can not be null.");
        }
        Query q = em.createNamedQuery(Person.NQ_FIND_ALL_PERSONS);
        q.setParameter("parentBusinessPartnerId", parentBusinessPartnerId);
        q.setParameter("deleted", false);

        return new ArrayList<Person>(q.getResultList());
    }

    @Override
    public List<Organization> getOrganizations(UUID parentBusinessPartnerId) {
        if(parentBusinessPartnerId == null) {
            throw new NullPointerException("parentBusinessPartnerId can not be null.");
        }

        Query q = em.createNamedQuery(Organization.NQ_FIND_ALL_ORGANIZATIONS);
        q.setParameter("parentBusinessPartnerId", parentBusinessPartnerId);
        q.setParameter("deleted", false);
        return new ArrayList<Organization>(q.getResultList());
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        if(Organization.class == entityClass) {
            if(extraParameters.length == 0) {
                return (List<E>) getOrganizations(session.getOrganization().getBusinessPartnerId());
            }
        }

        return super.getEntities(entityClass, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        return super.getEntityItems(entity, itemClass, extraParameters);
    }

    @Override
    public Person newPerson(BusinessPartner parentBusinessPartner) {
        Person person = new Person();
        person.setDefaultCurrency(Currency.EUR.getDbResource());
        person.setParentId(parentBusinessPartner.getBusinessPartnerId());
        return person;
    }
}
