/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.bl.contacts.ContactsServiceLocal;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.BusinessUnitAddress;
import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.TeamMember;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.users.UserRegistration;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.crm.enums.AccountStatus;
import com.cosmos.acacia.crm.enums.BusinessUnitAddressType;
import com.cosmos.acacia.crm.enums.BusinessUnitType;
import com.cosmos.acacia.crm.enums.FunctionalHierarchy;
import com.cosmos.acacia.entity.AbstractEntityService;
import com.cosmos.acacia.entity.Operation;
import com.cosmos.util.SecurityUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class UsersServiceBean extends AbstractEntityService implements UsersServiceRemote, UsersServiceLocal {

    public static final String ORGANIZATION_KEY = "organization";
    public static final String BUSINESS_UNIT_KEY = "businessUnit";
    public static final String TEAM_KEY = "team";
    public static final String USER_KEY = "user";
    public static final String PK_USER_ORGANIZATION = "userOrganization";
    public static final String ADDRESS_TYPE_KEY = "addressType";
    public static final String PK_BUSINESS_UNITS = "businessUnits";
    public static final String PK_FUNCTIONAL_HIERARCHIES = "functionalHierarchies";
    //
    @EJB
    private ContactsServiceLocal contactsService;

    @Override
    public User newUser() {
        User user = new User(session.getSystemOrganization());
        return user;
    }

    @Override
    public List<User> getUsers(Organization organization) {
        Query q = em.createNamedQuery(User.NQ_FIND_ALL);
        q.setParameter("userName", User.SUPERVISOR_USER_NAME);
        return new ArrayList<User>(q.getResultList());
    }

    @Override
    public List<UserOrganization> getUserOrganizations(Organization organization) {
        Query q = em.createNamedQuery(UserOrganization.NQ_FIND_BY_ORGANIZATION);
        q.setParameter(ORGANIZATION_KEY, organization);
        return new ArrayList<UserOrganization>(q.getResultList());
    }

    @Override
    public List<UserOrganization> getUserOrganizations(User user) {
        Query q = em.createNamedQuery(UserOrganization.NQ_FIND_BY_USER);
        q.setParameter(USER_KEY, user);
        return new ArrayList<UserOrganization>(q.getResultList());
    }

    @Override
    public UserOrganization getUserOrganization(User user, Organization organization) {
        Query q = em.createNamedQuery(UserOrganization.NQ_FIND_BY_USER_AND_ORGANIZATION);
        q.setParameter(USER_KEY, user);
        q.setParameter(ORGANIZATION_KEY, organization);
        return (UserOrganization) q.getSingleResult();
    }

    @Override
    public List<UserOrganization> getPossibleManagers(UserOrganization userOrganization) {
        BusinessUnit businessUnit;
        if((businessUnit = userOrganization.getBusinessUnit()) == null) {
            return Collections.emptyList();
        }
        List<BusinessUnit> parentalBusinessUnits = getParentalBusinessUnits(businessUnit);

        JobTitle jobTitle;
        DbResource dbResource;
        if((jobTitle = userOrganization.getJobTitle()) == null || (dbResource = jobTitle.getFunctionalHierarchy()) == null) {
            return Collections.emptyList();
        }
        List<DbResource> parentalHierarchies = getParentalHierarchy(dbResource);
        Query q = em.createNamedQuery(UserOrganization.NQ_FIND_BY_BUSINESS_UNITS_AND_FUNCTIONAL_HIERARCHY);
        q.setParameter(ORGANIZATION_KEY, session.getOrganization());
        q.setParameter(PK_BUSINESS_UNITS, parentalBusinessUnits);
        q.setParameter(PK_FUNCTIONAL_HIERARCHIES, parentalHierarchies);
        List<UserOrganization> userOrganizations = new ArrayList<UserOrganization>(q.getResultList());
        if(userOrganizations.contains(userOrganization)) {
            userOrganizations.remove(userOrganization);
        }
        return userOrganizations;
    }

    @Override
    public List<Organization> getActiveOrganizations(User user) {
        if (user == null)
            user = session.getUser();

        Query q = em.createNamedQuery(UserOrganization.NQ_FIND_BY_ACTIVE_USER);
        q.setParameter("user", user);
        return new ArrayList<Organization>(q.getResultList());
    }

    @Override
    public List<Organization> getOrganizations(User user) {
        if(user == null) {
            throw new NullPointerException("The user parameter can not be null.");
        }

        Query q = em.createNamedQuery(UserOrganization.NQ_FIND_ORGANIZATIONS_BY_USER);
        q.setParameter("user", user);
        return new ArrayList<Organization>(q.getResultList());
    }

    @Override
    public BusinessUnit getRootBusinessUnit(Organization organization) {
        Query q = em.createNamedQuery(BusinessUnit.NQ_FIND_ROOT_BUSINESS_UNIT);
        q.setParameter(ORGANIZATION_KEY, organization);
        try {
            return (BusinessUnit) q.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @Override
    public BusinessUnit getBusinessUnit(Organization organization, String businessUnitName) {
        Query q = em.createNamedQuery(BusinessUnit.NQ_FIND_BY_BUSINESS_UNIT_NAME);
        q.setParameter(ORGANIZATION_KEY, organization);
        q.setParameter("businessUnitName", businessUnitName);
        try {
            return (BusinessUnit) q.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    private List<BusinessUnit> getParentalBusinessUnits(BusinessUnit businessUnit) {
        List<BusinessUnit> businessUnits = new ArrayList<BusinessUnit>();
        while(businessUnit != null) {
            businessUnits.add(businessUnit);
            businessUnit = businessUnit.getParentBusinessUnit();
        }

        return businessUnits;
    }

    private List<DbResource> getParentalHierarchy(DbResource functionalHierarchy) {
        List<FunctionalHierarchy> parentalHierarchy = ((FunctionalHierarchy) functionalHierarchy.getEnumValue()).getParentalHierarchy();
        List<DbResource> hierarchy = new ArrayList<DbResource>(parentalHierarchy.size());
        for(FunctionalHierarchy fh : parentalHierarchy) {
            hierarchy.add(fh.getDbResource());
        }

        return hierarchy;
    }

    @Override
    public List<Team> getTeams(DataObjectBean parameter) {
        Query q;
        if (parameter instanceof Organization) {
            q = em.createNamedQuery(Team.NQ_FIND_ALL);
            q.setParameter(ORGANIZATION_KEY, parameter);
        } else if (parameter instanceof BusinessUnit) {
            q = em.createNamedQuery(Team.NQ_FIND_BY_BUSINESS_UNIT);
            q.setParameter(BUSINESS_UNIT_KEY, parameter);
        } else {
            throw new UnsupportedOperationException("Unknown parameter type: " + parameter);
        }

        return new ArrayList<Team>(q.getResultList());
    }

    @Override
    public List<UserSecurityRole> getUserSecurityRoles(UserOrganization userOrganization) {
        Query q = em.createNamedQuery(UserSecurityRole.NQ_FIND_ALL);
        q.setParameter(PK_USER_ORGANIZATION, userOrganization);

        return new ArrayList<UserSecurityRole>(q.getResultList());
    }

    @Override
    public List<BusinessUnit> getBusinessUnits(Organization organization) {
        Query q = em.createNamedQuery(BusinessUnit.NQ_FIND_ALL);
        q.setParameter(ORGANIZATION_KEY, organization);

        return new ArrayList<BusinessUnit>(q.getResultList());
    }

    @Override
    public List<BusinessUnit> getChildrenBusinessUnits(BusinessUnit parentBusinessUnit) {
        Query q;
        if(parentBusinessUnit != null) {
            q = em.createNamedQuery(BusinessUnit.NQ_FIND_BY_PARENT_BUSINESS_UNIT);
            q.setParameter("parentBusinessUnit", parentBusinessUnit);
        } else {
            q = em.createNamedQuery(BusinessUnit.NQ_FIND_ROOT_BUSINESS_UNIT);
            q.setParameter(ORGANIZATION_KEY, session.getOrganization());
        }

        return new ArrayList<BusinessUnit>(q.getResultList());
    }

    @Override
    public Set<BusinessUnit> getParentChildBusinessUnits(BusinessUnit currentBusinessUnit) {
        HashSet<BusinessUnit> businessUnits = new HashSet<BusinessUnit>();
        businessUnits.add(currentBusinessUnit);

        BusinessUnit parentBusinessUnit;
        if((parentBusinessUnit = currentBusinessUnit.getParentBusinessUnit()) == null) {
            return businessUnits;
        }
        businessUnits.add(parentBusinessUnit);
        businessUnits.addAll(getChildrenBusinessUnits(parentBusinessUnit));

        return businessUnits;
    }

    @Override
    public List<BusinessUnitAddress> getBusinessUnitAddresses(BusinessUnit businessUnit, Object... extraParameters) {
        Query q;
        if(extraParameters.length == 0) {
            q = em.createNamedQuery(BusinessUnitAddress.NQ_FIND_ALL);
        } else {
            Object parameter;
            if((parameter = extraParameters[0]) instanceof DbResource) {
                q = em.createNamedQuery(BusinessUnitAddress.NQ_FIND_BY_ADDRESS_TYPE);
                q.setParameter(ADDRESS_TYPE_KEY, parameter);
            } else {
                throw new RuntimeException("Unsuported extra parameter(s): " + parameter);
            }
        }
        q.setParameter(BUSINESS_UNIT_KEY, businessUnit);
        return new ArrayList<BusinessUnitAddress>(q.getResultList());
    }

    @Override
    public List<JobTitle> getJobTitles(BusinessUnit businessUnit, Object... extraParameters) {
        Query q = em.createNamedQuery(JobTitle.NQ_FIND_ALL);
        q.setParameter(BUSINESS_UNIT_KEY, businessUnit);
        return new ArrayList<JobTitle>(q.getResultList());
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        if(!canDo(Operation.Read, null, entityClass, extraParameters)) {
            return Collections.emptyList();
        }

        if (entityClass == User.class) {
            Object parameter;
            if(extraParameters.length > 0) {
                if((parameter = extraParameters[0]) instanceof UserOrganization) {
                    return (List<E>) getPossibleManagers((UserOrganization) parameter);
                } else if(parameter instanceof Organization) {
                    //return (List<E>) getUserOrganizations((Organization) parameter);
                    return (List<E>) getUsers((Organization) parameter);
                } else if(parameter instanceof User) {
                    return (List<E>) getUserOrganizations((User) parameter);
                }
            } else {
                //return (List<E>) getUserOrganizations(session.getOrganization());
                return (List<E>) getUsers(session.getOrganization());
            }
        } else if (entityClass == Team.class) {
            if (extraParameters.length == 0) {
                return (List<E>) getTeams(session.getOrganization());
            }

            Object parameter;
            if ((parameter = extraParameters[0]) instanceof BusinessUnit) {
                return (List<E>) getTeams((DataObjectBean) parameter);
            }
        } else if (entityClass == UserRegistration.class) {
            return new ArrayList<E>(2);
        }

        return super.getEntities(entityClass, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        if(!canDo(Operation.Read, entity, itemClass, extraParameters)) {
            return Collections.emptyList();
        }

        if(TeamMember.class == itemClass) {
            Query q;
            if(entity instanceof Team) {
                q = em.createNamedQuery(TeamMember.NQ_FIND_BY_TEAM);
                q.setParameter(TEAM_KEY, entity);
            } else if(entity instanceof UserOrganization) {
                q = em.createNamedQuery(TeamMember.NQ_FIND_BY_USER_ORGANIZATION);
                q.setParameter(USER_KEY, entity);
            } else {
                throw new UnsupportedOperationException("Not supported parent entity: " + entity);
            }
            return new ArrayList<I>(q.getResultList());
        } else if (BusinessUnit.class == itemClass) {
            if(entity instanceof Organization) {
                return (List<I>) getBusinessUnits((Organization) entity);
            } else if (entity instanceof BusinessUnit) {
                return (List<I>) getChildrenBusinessUnits((BusinessUnit) entity);
            }
        } else if(UserOrganization.class == itemClass) {
            if(entity instanceof User) {
                return (List<I>) getUserOrganizations((User) entity);
            } else if(entity instanceof Organization) {
                return (List<I>) getUserOrganizations((Organization) entity);
            }
        } else if(UserSecurityRole.class == itemClass) {
            if(entity instanceof UserOrganization) {
                return (List<I>) getUserSecurityRoles((UserOrganization) entity);
            }
        } else if(BusinessUnitAddress.class == itemClass) {
            if(entity instanceof BusinessUnit) {
                return (List<I>) getBusinessUnitAddresses((BusinessUnit) entity, extraParameters);
            }
        } else if(JobTitle.class == itemClass) {
            if(entity instanceof BusinessUnit) {
                return (List<I>) getJobTitles((BusinessUnit) entity, extraParameters);
            }
        }

        return super.getEntityItems(entity, itemClass, extraParameters);
    }

    @Override
    protected <E> void initEntity(E entity) {
        if (entity instanceof Team) {
            Team team = (Team) entity;
            team.setOrganization(session.getOrganization());
            team.setStatus(AccountStatus.Enabled.getDbResource());
        }
    }

    @Override
    protected <E, I> void initItem(E entity, I item) {
        if (item instanceof BusinessUnitAddress) {
            if (entity instanceof BusinessUnit) {
                BusinessUnit businessUnit = (BusinessUnit)entity;
                ((BusinessUnitAddress)item).setBusinessUnit(businessUnit);
            }
        } else if (item instanceof JobTitle) {
            if (entity instanceof BusinessUnit) {
                BusinessUnit businessUnit = (BusinessUnit)entity;
                ((JobTitle)item).setBusinessUnit(businessUnit);
            }
        } else if(item instanceof TeamMember) {
            if(entity instanceof Team) {
                TeamMember member = (TeamMember) item;
                member.setStatus(AccountStatus.Enabled.getDbResource());
            }
        } else if (item instanceof BusinessUnit) {
            BusinessUnit businessUnit = (BusinessUnit)item;
            businessUnit.setOrganization(session.getOrganization());
            List<BusinessUnit> businessUnits;
            if((businessUnits = getChildrenBusinessUnits(null)).size() == 0) {
                businessUnit.setRoot(true);
                businessUnit.setBusinessUnitType(BusinessUnitType.Administrative.getDbResource());
                businessUnit.setBusinessUnitName(session.getOrganization().getOrganizationName());
                businessUnit.setDivisionName(BusinessUnitType.Administrative.name());
            } else {
                businessUnit.setParentBusinessUnit(businessUnits.get(0));
            }
        }
    }

    @Override
    public <E> E save(E entity) {
        if(entity instanceof UserRegistration) {
            return (E) saveUserRegistration((UserRegistration) entity);
        }

        return super.save(entity);
    }

    private UserRegistration saveUserRegistration(UserRegistration userRegistration) {
        Organization systemOrganization = session.getSystemOrganization();

        Person person = new Person(); //contactsService.new
        person.setDefaultCurrency(userRegistration.getDefaultCurrency());
        person.setGender(userRegistration.getGender());
        person.setPersonalUniqueId(userRegistration.getPersonalUniqueId());
        person.setFirstName(userRegistration.getFirstName());
        person.setSecondName(userRegistration.getSecondName());
        person.setLastName(userRegistration.getLastName());
        person.setExtraName(userRegistration.getExtraName());
        person.setBirthDate(userRegistration.getBirthDate());
        person.setBirthPlaceCity(userRegistration.getBirthPlaceCity());
        person.setBirthPlaceCountry(userRegistration.getBirthPlaceCountry());
        person.setParentBusinessPartnerId(systemOrganization.getBusinessPartnerId());
        person = super.save(person);

        Address address = new Address();

        User user = newUser();
        user.setEmailAddress(userRegistration.getEmailAddress());
        user.setUserName(userRegistration.getUsername());
        user.setUserPassword(SecurityUtils.getHash(userRegistration.getPassword()));
        user.setPerson(person);
        user.setCreationTime(new Date());

        user = super.save(user);

        BusinessUnit usersBusinessUnit = getBusinessUnit(systemOrganization, BusinessUnit.USERS_BUSINESS_UNIT_NAME);
        UserOrganization userOrganization = new UserOrganization(user, systemOrganization);
        userOrganization.setBranch(systemOrganization.getRegistrationAddress());
        userOrganization.setBusinessUnit(usersBusinessUnit);
        userOrganization.setUserActive(true);
        super.save(userOrganization);

        session.sendSystemMail("New User Registration: " + user.toString(), user.getInfo());

        userRegistration.setUserId(user.getUserId());
        return userRegistration;
    }

    @Override
    protected <E> void preSave(E entity, Map<String, Object> parameters) {
        if (entity instanceof User) {
            User user = (User) entity;
        } else if (entity instanceof Team) {
            Team team = (Team) entity;
            Organization organization;
            if ((organization = team.getOrganization()) == null || !organization.equals(session.getOrganization())) {
                team.setOrganization(session.getOrganization());
            }
        } else if (entity instanceof TeamMember) {
            TeamMember member = (TeamMember) entity;
        } else if (entity instanceof BusinessUnit) {
            BusinessUnit businessUnit = (BusinessUnit) entity;
            preSaveBusinessUnit(businessUnit);
        } else if(entity instanceof UserOrganization) {
            preSaveUserOrganization((UserOrganization) entity, parameters);
        }
    }

    private void preSaveBusinessUnit(BusinessUnit businessUnit) {
        if(businessUnit.isRoot()) {
            if(getRootBusinessUnit(businessUnit.getOrganization()) != null) {
                throw new RuntimeException("The root business unit already exists for this organization. " + businessUnit +
                        ", " + businessUnit.getOrganization());
            }
            businessUnit.setParentBusinessUnit(null);
        } else {
            BusinessUnit parentBusinessUnit;
            if((parentBusinessUnit = businessUnit.getParentBusinessUnit()) == null) {
                throw new RuntimeException("The parent business unit can not be null.");
            } else if(parentBusinessUnit.equals(businessUnit)) {
                throw new RuntimeException("The parent business unit must be different than the business unit.");
            }
        }
    }

    @Override
    protected <E> void postSave(E entity, Map<String, Object> parameters) {
        if (entity instanceof BusinessUnit) {
            BusinessUnit businessUnit = (BusinessUnit) entity;
            postSaveBusinessUnit(businessUnit);
        } else if (entity instanceof UserOrganization) {
            postSaveUserOrganization((UserOrganization) entity, parameters);
        }
    }

    private void preSaveUserOrganization(UserOrganization userOrganization, Map<String, Object> parameters) {
        if(userOrganization.getUserOrganizationId() == null) {
            setNewEntity(parameters);
        }
    }

    private void postSaveUserOrganization(UserOrganization userOrganization, Map<String, Object> parameters) {
        if(isNewEntity(parameters)) {
            session.sendSystemMail(userOrganization.toString(), "New UserOrganization: " + userOrganization.getInfo());
        }
    }

    private void postSaveBusinessUnit(BusinessUnit businessUnit) {
        List<BusinessUnitAddress> businessUnitAddresses;
        DbResource billToType = BusinessUnitAddressType.BillTo.getDbResource();
        if((businessUnitAddresses = getBusinessUnitAddresses(businessUnit, billToType)) != null &&
                businessUnitAddresses.size() > 0) {
            return;
        }

        BusinessUnitAddress businessUnitAddress = newItem(businessUnit, BusinessUnitAddress.class);
        businessUnitAddress.setAddressType(billToType);
        businessUnitAddress.setAddress(session.getBranch());
        esm.persist(em, businessUnitAddress);
    }

    @Override
    protected <E> void preDelete(E entity, Map<String, Object> parameters) {
        if (entity instanceof BusinessUnit) {
            preDeleteBusinessUnit((BusinessUnit) entity);
        }
    }

    private void preDeleteBusinessUnit(BusinessUnit businessUnit) {
        if(businessUnit.isRoot()) {
            throw new RuntimeException("The root business unit can not be deleted.");
        } else {
            Query q = em.createNamedQuery(BusinessUnitAddress.NQ_DELETE_ALL);
            q.setParameter(BUSINESS_UNIT_KEY, businessUnit);
            q.executeUpdate();

            q = em.createNamedQuery(JobTitle.NQ_DELETE_ALL);
            q.setParameter(BUSINESS_UNIT_KEY, businessUnit);
            q.executeUpdate();
        }
    }
}
