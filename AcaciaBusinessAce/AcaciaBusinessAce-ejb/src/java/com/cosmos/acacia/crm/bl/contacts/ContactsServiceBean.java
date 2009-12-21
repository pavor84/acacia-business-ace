/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contacts;

import com.cosmos.acacia.crm.bl.users.UsersServiceLocal;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BankDetail;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Passport;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.PositionType;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.enums.BusinessUnitType;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.entity.AbstractEntityService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<BankDetail> getBankDetails(Address address) {
        Query q = em.createNamedQuery(BankDetail.NQ_FIND_ALL);
        q.setParameter("address", address);

        return new ArrayList<BankDetail>(q.getResultList());
    }

    @Override
    public List<CommunicationContact> getCommunicationContacts(Address address) {
        Query q = em.createNamedQuery(CommunicationContact.NQ_FIND_ALL);
        q.setParameter("address", address);

        return new ArrayList<CommunicationContact>(q.getResultList());
    }

    @Override
    public List<ContactPerson> getContactPersons(Address address) {
        Query q = em.createNamedQuery(ContactPerson.NQ_FIND_ALL);
        q.setParameter("address", address);

        return new ArrayList<ContactPerson>(q.getResultList());
    }

    @Override
    public List<Passport> getPassports(Person person) {
        Query q = em.createNamedQuery(Passport.NQ_FIND_ALL);
        q.setParameter("person", person);

        return new ArrayList<Passport>(q.getResultList());
    }

    @Override
    public List<PositionType> getPositionTypes(BusinessPartner businessPartner) {
        Query q = em.createNamedQuery(PositionType.NQ_FIND_ALL);
        q.setParameter("businessPartner", businessPartner);

        return new ArrayList<PositionType>(q.getResultList());
    }

    @Override
    public List<Address> getAddresses(BusinessPartner businessPartner) {
        Query q = em.createNamedQuery(Address.NQ_FIND_ALL);
        q.setParameter("businessPartner", businessPartner);

        return new ArrayList<Address>(q.getResultList());
    }

    @Override
    public List<Address> getAddresses(BusinessPartner businessPartner, String namePrefix) {
        Query q = em.createNamedQuery(Address.NQ_FIND_ALL_BY_NAME_PREFIX);
        q.setParameter("businessPartner", businessPartner);
        q.setParameter("namePrefix", namePrefix + "%");

        return new ArrayList<Address>(q.getResultList());
    }

    @Override
    public Organization saveOrganization(Organization organization) {
        return save(organization);
    }

    @Override
    protected <E> void postSave(E entity, Map<String, Object> parameters) {
        super.postSave(entity, parameters);

        if(entity instanceof Organization) {
            postSaveOrganization((Organization) entity, parameters);
        }
    }

    private void postSaveOrganization(Organization organization, Map<String, Object> parameters) {
        if(!session.getSupervisorOrganization().getBusinessPartnerId().equals(organization.getParentBusinessPartnerId())) {
            return;
        }

        if(usersService.getRootBusinessUnit(organization) == null) {
            BusinessUnit rootBusinessUnit = usersService.newItem(organization, BusinessUnit.class);
            rootBusinessUnit.setBusinessUnitName(BusinessUnit.ROOT_BUSINESS_UNIT_NAME);
            rootBusinessUnit.setBusinessUnitType(BusinessUnitType.Administrative.getDbResource());
            rootBusinessUnit.setRoot(true);
            rootBusinessUnit.setDisabled(false);
            usersService.save(rootBusinessUnit);

            session.sendSystemMail(organization.toString(), "New user Organization: " + organization.getInfo());
        }
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
    public List<Organization> getOrganizations(UUID parentBusinessPartnerId, List<Classifier> classifiers) {
        if(parentBusinessPartnerId == null) {
            throw new NullPointerException("parentBusinessPartnerId can not be null.");
        }

        Query q;
        if(classifiers != null && classifiers.size() > 0) {
            q = em.createNamedQuery(Organization.NQ_FIND_ALL_ORGANIZATIONS_BY_CLASSIFIERS);
            q.setParameter("classifiers", classifiers);
        } else {
            q = em.createNamedQuery(Organization.NQ_FIND_ALL_ORGANIZATIONS);
        }
        q.setParameter("parentBusinessPartnerId", parentBusinessPartnerId);
        q.setParameter("deleted", false);
        return new ArrayList<Organization>(q.getResultList());
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, List classifiers, Object... extraParameters) {
        if(Person.class == entityClass) {
            return (List<E>) getPersons(session.getOrganization().getBusinessPartnerId());
        } else if(Organization.class == entityClass) {
            return (List<E>) getOrganizations(session.getOrganization().getBusinessPartnerId(), classifiers);
        }
        return super.getEntities(entityClass, classifiers, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, List classifiers, Object... extraParameters) {
        if(Address.class == itemClass) {
            if(entity instanceof BusinessPartner) {
                return (List<I>) getAddresses((BusinessPartner) entity);
            }
        } else if(BankDetail.class == itemClass) {
            if(entity instanceof Address) {
                return (List<I>) getBankDetails((Address) entity);
            }
        } else if(CommunicationContact.class == itemClass) {
            if(entity instanceof Address) {
                return (List<I>) getCommunicationContacts((Address) entity);
            }
        } else if(ContactPerson.class == itemClass) {
            if(entity instanceof Address) {
                return (List<I>) getContactPersons((Address) entity);
            }
        } else if(Passport.class == itemClass) {
            if(entity instanceof Person) {
                return (List<I>) getPassports((Person) entity);
            }
        } else if(PositionType.class == itemClass) {
            if(entity instanceof BusinessPartner) {
                return (List<I>) getPositionTypes((BusinessPartner) entity);
            }
        }

        return super.getEntityItems(entity, itemClass, classifiers, extraParameters);
    }

    @Override
    public Organization newOrganization() {
        return newEntity(Organization.class);
    }

    @Override
    public Person newPerson() {
        return newEntity(Person.class);
    }

    @Override
    protected <E> void initEntity(E entity) {
        super.initEntity(entity);

        if(entity instanceof Person) {
            Person person = (Person) entity;
            person.setDefaultCurrency(Currency.EUR.getDbResource());
            person.setParentId(session.getOrganization().getBusinessPartnerId());
        } else if(entity instanceof Organization) {
            Organization organization = (Organization) entity;
            organization.setParentId(session.getOrganization().getBusinessPartnerId());
            organization.setDefaultCurrency(Currency.BGN.getDbResource());
        }
    }

    @Override
    protected <E, I> void initItem(E entity, I item) {
        super.initItem(entity, item);

        if(item instanceof Address) {
            Address address = (Address) item;
            address.setBusinessPartner((BusinessPartner) entity);
            setAddressName(address);
        }
    }

    private void setAddressName(Address address) {
        BusinessPartner businessPartner = address.getBusinessPartner();
        String prefix;
        String suffix = null;
        if(businessPartner instanceof Organization) {
            List<Address> addresses = getAddresses(businessPartner);
            if(addresses.size() == 0) {
                prefix = Address.NAME_REGISTRATION_ADDRESS;
                suffix = "";
            } else {
                prefix = Address.NAME_OFFICE_ADDRESS;
            }
        } else {
            prefix = Address.NAME_HOME_ADDRESS;
        }

        if(suffix == null) {
            List<Address> addresses = getAddresses(businessPartner, prefix);
            suffix = " " + Integer.toString(addresses.size() + 1);
        }

        address.setAddressName(prefix + suffix);
    }
}
