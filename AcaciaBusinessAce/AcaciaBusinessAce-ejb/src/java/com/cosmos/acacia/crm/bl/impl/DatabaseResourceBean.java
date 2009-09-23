/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.bl.cash.CurrencyNominalLocal;
import com.cosmos.acacia.crm.bl.contactbook.LocationsListLocal;
import com.cosmos.acacia.crm.bl.contacts.ContactsServiceLocal;
import com.cosmos.acacia.crm.bl.users.UsersServiceLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.EnumClass;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.City;
import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.contacts.Country;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.PersonalCommunicationContact;
import com.cosmos.acacia.crm.data.security.EntityTypePrivilege;
import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.PrivilegeCategory;
import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.crm.enums.AccountStatus;
import com.cosmos.acacia.crm.enums.BusinessActivity;
import com.cosmos.acacia.crm.enums.BusinessUnitAddressType;
import com.cosmos.acacia.crm.enums.BusinessUnitType;
import com.cosmos.acacia.crm.enums.CommunicationType;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.CustomerPaymentStatus;
import com.cosmos.acacia.crm.enums.CustomerPaymentType;
import com.cosmos.acacia.crm.enums.DataType;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType;
import com.cosmos.acacia.crm.enums.DeliveryCertificateReason;
import com.cosmos.acacia.crm.enums.DeliveryCertificateStatus;
import com.cosmos.acacia.crm.enums.DeliveryStatus;
import com.cosmos.acacia.crm.enums.DeliveryType;
import com.cosmos.acacia.crm.enums.DocumentDeliveryMethod;
import com.cosmos.acacia.crm.enums.DocumentStatus;
import com.cosmos.acacia.crm.enums.DocumentType;
import com.cosmos.acacia.crm.enums.FunctionalHierarchy;
import com.cosmos.acacia.crm.enums.Gender;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.OrderConfirmationType;
import com.cosmos.acacia.crm.enums.OrganizationType;
import com.cosmos.acacia.crm.enums.PassportType;
import com.cosmos.acacia.crm.enums.PaymentTerm;
import com.cosmos.acacia.crm.enums.PaymentType;
import com.cosmos.acacia.crm.enums.PermissionCategory;
import com.cosmos.acacia.crm.enums.ProductColor;
import com.cosmos.acacia.crm.enums.PurchaseOrderStatus;
import com.cosmos.acacia.crm.enums.TransportationMethod;
import com.cosmos.acacia.crm.enums.VatCondition;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.security.AccessLevel;
import com.cosmos.acacia.security.AccessRight;
import com.cosmos.acacia.security.PrivilegeCategoryType;
import com.cosmos.acacia.security.PrivilegeType;
import com.cosmos.util.ClassHelper;
import com.cosmos.util.SecurityUtils;
import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 *
 * @author miro
 */
@Stateless
public class DatabaseResourceBean implements DatabaseResourceLocal {

    private static boolean initialized = false;
    private static Map<String, EnumClass> enumClassMap;
    private static Map<Enum, Map<String, DbResource>> dbResourceMap;
    private static Map<String, Set<Class>> entityClassesMap;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private CurrencyNominalLocal currencyNominalManager;

    @EJB
    private LocationsListLocal locationsService;

    @EJB
    private ContactsServiceLocal contactsService;

    @EJB
    private UsersServiceLocal usersService;

    @EJB
    private AcaciaSessionLocal session;

    @EJB
    private DataObjectTypeLocal dotService;

    @Override
    public synchronized void initDatabaseResource() {
        if (!initialized) {
            initDbResources();
            initCountries();
            initCities();
            initContacts();
            currencyNominalManager.initCurrencyNominals();
            initialized = true;
        }
    }

    private void initDbResources() {
        getDbResources(Gender.class);
        getDbResources(MeasurementUnit.class);
        getDbResources(OrganizationType.class);
        getDbResources(ProductColor.class);
        getDbResources(CommunicationType.class);
        getDbResources(Currency.class);
        getDbResources(PassportType.class);
        getDbResources(Algorithm.Type.class);
        getDbResources(DataType.class);
        getDbResources(DeliveryCertificateMethodType.class);
        getDbResources(DeliveryCertificateReason.class);
        getDbResources(DeliveryCertificateStatus.class);
        getDbResources(InvoiceType.class);
        getDbResources(DocumentDeliveryMethod.class);
        getDbResources(TransportationMethod.class);
        getDbResources(PaymentType.class);
        getDbResources(PaymentTerm.class);
        getDbResources(DeliveryType.class);
        getDbResources(VatCondition.class);
        getDbResources(InvoiceStatus.class);
        getDbResources(DeliveryStatus.class);
        getDbResources(PurchaseOrderStatus.class);
        getDbResources(OrderConfirmationType.class);
        getDbResources(SpecialPermission.class);
        getDbResources(CustomerPaymentStatus.class);
        getDbResources(CustomerPaymentType.class);
        getDbResources(DocumentType.class);
        getDbResources(DocumentStatus.class);
        getDbResources(AccessRight.class);
        getDbResources(PermissionCategory.class);
        getDbResources(AccessRight.class);
        getDbResources(AccessLevel.class);
        getDbResources(PrivilegeType.class);
        getDbResources(PrivilegeCategoryType.class);
        getDbResources(BusinessActivity.class);
        getDbResources(BusinessUnitType.class);
        getDbResources(BusinessUnitAddressType.class);
        getDbResources(AccountStatus.class);
        getDbResources(FunctionalHierarchy.class);
    }

    private void initCountries() {
        Long count;
        if((count = locationsService.getCountriesCount()) != null && count > 0) {
            return;
        }

        Map<String, Country> countryMap = new TreeMap<String, Country>();

        for(Locale locale : Locale.getAvailableLocales()) {
            try {
                java.util.Currency utilCurrency = java.util.Currency.getInstance(locale);
                String countryName = locale.getDisplayCountry(Locale.ENGLISH);
                if(countryMap.containsKey(countryName)) {
                    continue;
                }
                Country country = new Country();
                country.setCountryName(countryName);
                country.setCountryCodeA2(locale.getCountry());
                country.setCountryCodeA3(locale.getISO3Country());
                country.setCurrency(Currency.valueOf(utilCurrency.getCurrencyCode()).getDbResource());
                countryMap.put(countryName, country);
            } catch(IllegalArgumentException ex) {
            } catch(NullPointerException ex) {
            }
        }

        for(Country country : countryMap.values()) {
            esm.persist(em, country);
        }
    }

    private void initCities() {
        Country bulgaria = locationsService.getCountryByCodeA2(Country.CODE_A2_BULGARIA);
        Long count;
        if((count = locationsService.getCountriesCount()) != null && count > 0) {
            return;
        }

        City city;
        if((city = locationsService.getCityByCode(bulgaria, City.CODE_SOFIA)) == null) {
            city = locationsService.newCity(bulgaria);
            city.setCityName("Sofia");
            city.setCityCode(City.CODE_SOFIA);
            city.setCityPhoneCode("2");
            city.setPostalCode("1000");
            esm.persist(em, city);
        }

        if((city = locationsService.getCityByCode(bulgaria, City.CODE_STARA_ZAGORA)) == null) {
            city = locationsService.newCity(bulgaria);
            city.setCityName("Stara Zagora");
            city.setCityCode(City.CODE_STARA_ZAGORA);
            city.setCityPhoneCode("42");
            city.setPostalCode("6000");
            esm.persist(em, city);
        }
    }

    public List<DbResource> getDbResources(Class<? extends DatabaseResource> dbResourceClass) {
        DatabaseResource[] dbResourcesArray = dbResourceClass.getEnumConstants();
        int size;
        if (dbResourcesArray == null || (size = dbResourcesArray.length) == 0) {
            return Collections.<DbResource>emptyList();
        }

        List<DbResource> dbResources = new ArrayList<DbResource>(size);

        for (DatabaseResource dbr : dbResourcesArray) {
            Enum enumValue = (Enum) dbr;
            DbResource resource = getDbResource(enumValue);
            dbr.setDbResource(resource);
            dbResources.add(resource);
        }

        return dbResources;
    }

    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
    private EnumClass getEnumClass(Enum dbEnum) {
        if (enumClassMap == null) {
            enumClassMap = new HashMap<String, EnumClass>();
        }

        String className = dbEnum.getClass().getName();
        EnumClass enumClass = enumClassMap.get(className);
        if (enumClass == null) {
            Query q = em.createNamedQuery("EnumClass.findByEnumClassName");
            q.setParameter("enumClassName", className);
            try {
                enumClass = (EnumClass) q.getSingleResult();
            } catch (NoResultException ex) {
                enumClass = new EnumClass();
                enumClass.setEnumClassName(className);
                em.persist(enumClass);
            }

            enumClassMap.put(className, enumClass);
        }

        return enumClass;
    }

    private DbResource getDbResource(Enum dbEnum) {
        if (dbResourceMap == null) {
            dbResourceMap = new HashMap<Enum, Map<String, DbResource>>();
        }

        Map<String, DbResource> enumMap = dbResourceMap.get(dbEnum);
        if (enumMap == null) {
            enumMap = new HashMap<String, DbResource>(dbEnum.getClass().getEnumConstants().length);
        }

        String enumName = dbEnum.name();
        DbResource dbResource = enumMap.get(enumName);
        if (dbResource == null) {
            EnumClass enumClass = getEnumClass(dbEnum);
            Query q = em.createNamedQuery("DbResource.findByEnumClassAndName");
            q.setParameter("enumClass", enumClass);
            q.setParameter("enumName", enumName);
            try {
                dbResource = (DbResource) q.getSingleResult();
            } catch (NoResultException ex) {
                dbResource = new DbResource();
                dbResource.setEnumClass(enumClass);
                dbResource.setEnumName(enumName);
                em.persist(dbResource);
            }

            enumMap.put(enumName, dbResource);
        }

        return dbResource;
    }

/*
delete from registration_codes;
delete from assembling_messages;
delete from expressions;
delete from privilege_roles;
delete from privileges;
delete from privilege_categories;
delete from user_security_roles;
delete from security_roles;
delete from user_organizations;
delete from business_unit_addresses;
delete from business_units;
delete from user_organizations;
delete from personal_communication_contacts;
delete from contact_persons;
delete from communication_contacts;
delete from addresses;
delete from users;
delete from organizations;
*/
    private void initContacts() {
        Query q = em.createQuery("select count(t) from Organization t");
        Long count;
        if((count = (Long) q.getSingleResult()) != null && count > 0) {
            return;
        }

        DataObject dataObject = esm.saveDataObject(em, Organization.class);
        UUID organizationId = dataObject.getDataObjectId();
        dataObject.setParentDataObjectId(organizationId);
        dataObject.setDataObjectVersion(1);
        dataObject.setOrderPosition(1);
        em.persist(dataObject);

        Organization organization = new Organization(organizationId, "COSMOS Software Enterprises, Ltd.");
        organization.setDataObject(dataObject);
        organization.setParentId(organizationId);
        organization.setDefaultCurrency(Currency.EUR.getDbResource());
        organization.setActive(true);
        organization.setNickname("system");
        em.persist(organization);

        Address address = new Address();
        Country bulgaria = locationsService.getCountryByCodeA2(Country.CODE_A2_BULGARIA);
        City city = locationsService.getCityByCode(bulgaria, City.CODE_SOFIA);
        address.setCountry(bulgaria);
        address.setCity(city);
        address.setAddressName(Address.NAME_HEADQUARTER);
        address.setBusinessPartner(organization);
        address.setPostalCode("1612");
        address.setPostalAddress("ap. 15, entr. B, fl. 1, 5 Vorino str., kv. Krasno selo");
        esm.persist(em, address);

        organization.setRegistrationAddress(address);
        em.persist(organization);

        city = locationsService.getCityByCode(bulgaria, City.CODE_STARA_ZAGORA);
        Person person = contactsService.newPerson(organization);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1966, Calendar.NOVEMBER, 17, 4, 0);
        person.setBirthDate(calendar.getTime());
        person.setBirthPlaceCountry(bulgaria);
        person.setBirthPlaceCity(city);
        person.setFirstName("Miroslav");
        person.setLastName("Miroslav");
        person.setGender(Gender.Male.getDbResource());
        esm.persist(em, person);

        ContactPerson contactPerson = new ContactPerson(address);
        contactPerson.setPerson(person);
        esm.persist(em, contactPerson);

        CommunicationContact communicationContact = new CommunicationContact(address);
        communicationContact.setCommunicationType(CommunicationType.Email.getDbResource());
        communicationContact.setCommunicationValue("mnachev@gmail.com");
        esm.persist(em, communicationContact);
        PersonalCommunicationContact personalCommunicationContact = new PersonalCommunicationContact();
        personalCommunicationContact.setContactPerson(contactPerson);
        personalCommunicationContact.setCommunicationContact(communicationContact);
        esm.persist(em, personalCommunicationContact);

        communicationContact = new CommunicationContact(address);
        communicationContact.setCommunicationType(CommunicationType.Mobile.getDbResource());
        communicationContact.setCommunicationValue("(+359-88) 897-31-95");
        esm.persist(em, communicationContact);
        personalCommunicationContact = new PersonalCommunicationContact();
        personalCommunicationContact.setContactPerson(contactPerson);
        personalCommunicationContact.setCommunicationContact(communicationContact);
        esm.persist(em, personalCommunicationContact);

        communicationContact = new CommunicationContact(address);
        communicationContact.setCommunicationType(CommunicationType.Skype.getDbResource());
        communicationContact.setCommunicationValue("mnachev66");
        esm.persist(em, communicationContact);
        personalCommunicationContact = new PersonalCommunicationContact();
        personalCommunicationContact.setContactPerson(contactPerson);
        personalCommunicationContact.setCommunicationContact(communicationContact);
        esm.persist(em, personalCommunicationContact);

        User supervisor = usersService.newUser();
        supervisor.setUserName(User.SUPERVISOR_USER_NAME);
        supervisor.setEmailAddress("mnachev@gmail.com");
        supervisor.setPerson(person);
        supervisor.setUserPassword(SecurityUtils.getHash(User.SUPERVISOR_USER_PASSWORD));
        supervisor.setCreationTime(new Date());
        supervisor.setIsNew(false);
        esm.persist(em, supervisor);

        BusinessUnit rootBusinessUnit = new BusinessUnit(organization);
        rootBusinessUnit.setBusinessUnitName(BusinessUnit.ROOT_BUSINESS_UNIT_NAME);
        rootBusinessUnit.setBusinessUnitType(BusinessUnitType.Administrative.getDbResource());
        rootBusinessUnit.setRoot(true);
        rootBusinessUnit.setDisabled(false);
        esm.persist(em, rootBusinessUnit);

        BusinessUnit usersBusinessUnit = new BusinessUnit(rootBusinessUnit);
        usersBusinessUnit.setBusinessUnitName(BusinessUnit.USERS_BUSINESS_UNIT_NAME);
        usersBusinessUnit.setBusinessUnitType(BusinessUnitType.Administrative.getDbResource());
        usersBusinessUnit.setDisabled(false);
        esm.persist(em, usersBusinessUnit);

        UserOrganization userOrganization = new UserOrganization(supervisor, organization);
        userOrganization.setUserActive(true);
        userOrganization.setBranch(address);
        userOrganization.setBusinessUnit(rootBusinessUnit);
        esm.persist(em, userOrganization);

        SecurityRole securityRole = new SecurityRole(organization, rootBusinessUnit, User.SUPERVISOR_USER_NAME);
        esm.persist(em, securityRole);

        UserSecurityRole userSecurityRole = new UserSecurityRole(userOrganization, securityRole);
        esm.persist(em, userSecurityRole);

        initPrivilegeCategory(organization);

        Map<AccessRight, AccessLevel> roleAccessMap = new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
        roleAccessMap.put(AccessRight.Read, AccessLevel.System);
        roleAccessMap.put(AccessRight.Create, AccessLevel.System);
        roleAccessMap.put(AccessRight.Delete, AccessLevel.System);
        roleAccessMap.put(AccessRight.Modify, AccessLevel.System);
        roleAccessMap.put(AccessRight.Execute, AccessLevel.System);

        setSecurityRoleAccess(organization, securityRole, User.class.getPackage(), roleAccessMap);
    }

    private void setSecurityRoleAccess(
            Organization organization,
            SecurityRole securityRole,
            Package pkg,
            Map<AccessRight, AccessLevel> roleAccessMap) {
        String packageName = pkg.getName();
        PrivilegeCategory privilegeCategory = getPrivilegeCategory(organization, getLastName(packageName));
        for(Class entityClass : getEntityClasses(packageName)) {
            String entityClassName = entityClass.getName();
            DataObjectType entityDataObjectType = dotService.getDataObjectType(entityClassName);
            EntityTypePrivilege privilege = new EntityTypePrivilege(securityRole, entityDataObjectType);
            privilege.setPrivilegeCategory(privilegeCategory);
            privilege.setPrivilegeName(getLastName(entityClassName));
            esm.persist(em, privilege);
            setPrivilegeRoleAccess(privilege, roleAccessMap);
        }
    }

    private void setPrivilegeRoleAccess(Privilege privilege, Map<AccessRight, AccessLevel> roleAccessMap) {
        for(AccessRight right : AccessRight.values()) {
            AccessLevel level;
            if((level = roleAccessMap.get(right)) == null) {
                level = AccessLevel.None;
            }
            PrivilegeRole privilegeRole = new PrivilegeRole(privilege, right.getDbResource(), level.getDbResource());
            esm.persist(em, privilegeRole);
        }
    }

    @Override
    public void initPrivilegeCategory(Organization organization) {
        Query q = em.createNamedQuery(PrivilegeCategory.NQ_COUNT_BY_ORGANIZATION);
        q.setParameter("organization", organization);
        Long count;
        if((count = (Long) q.getSingleResult()) != null && count > 0) {
            return;
        }

        DbResource categoryType = PrivilegeCategoryType.System.getDbResource();
        Map<String, Set<Class>> classesMap = getEntityClassesMap();
        for(String packageName : classesMap.keySet()) {
            packageName = getLastName(packageName);
            PrivilegeCategory category = new PrivilegeCategory(organization, categoryType, packageName);
            esm.persist(em, category);
        }
    }

    private String getLastName(String packageName) {
        int index;
        if((index = packageName.lastIndexOf('.')) >= 0 || (index = packageName.lastIndexOf(' ')) >= 0) {
            packageName = packageName.substring(index + 1);
        }

        return packageName;
    }

    private PrivilegeCategory getPrivilegeCategory(Organization organization, Class entityClass) {
        return getPrivilegeCategory(organization, getLastName(entityClass.getPackage().getName()));
    }

    private PrivilegeCategory getPrivilegeCategory(Organization organization, String categoryName) {
        Query q = em.createNamedQuery(PrivilegeCategory.NQ_FIND_BY_NAME);
        q.setParameter("organization", organization);
        q.setParameter("categoryName", categoryName);
        try {
            return (PrivilegeCategory) q.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    private Set<Class> getEntityClasses(String packageName) {
        return getEntityClassesMap().get(packageName);
    }

    private Map<String, Set<Class>> getEntityClassesMap() {
        if(entityClassesMap != null) {
            return entityClassesMap;
        }

        entityClassesMap = new TreeMap<String, Set<Class>>();
        try {
            Set<Class<?>> acaciaClasses = ClassHelper.getClasses("com.cosmos", true, DataObjectBean.class);
            for(Class cls : acaciaClasses) {
                if(Modifier.isAbstract(cls.getModifiers())) {
                    continue;
                }

                Package pkg = cls.getPackage();
                String packageName = pkg.getName();
                Set<Class> classes;
                if((classes = entityClassesMap.get(packageName)) == null) {
                    classes = new HashSet<Class>();
                    entityClassesMap.put(packageName, classes);
                }
                classes.add(cls);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return entityClassesMap;
    }
}
