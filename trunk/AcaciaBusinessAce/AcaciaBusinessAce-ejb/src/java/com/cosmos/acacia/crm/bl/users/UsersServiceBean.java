/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.BusinessUnitAddress;
import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.TeamMember;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.crm.enums.AccountStatus;
import com.cosmos.acacia.crm.enums.BusinessUnitAddressType;
import com.cosmos.acacia.crm.enums.BusinessUnitType;
import com.cosmos.acacia.crm.enums.FunctionalHierarchy;
import com.cosmos.acacia.entity.AbstractEntityService;
import com.cosmos.acacia.entity.Operation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
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
    public List<BusinessUnit> getBusinessUnits() {
        Query q = em.createNamedQuery(BusinessUnit.NQ_FIND_ALL);
        q.setParameter(ORGANIZATION_KEY, session.getOrganization());

        return new ArrayList<BusinessUnit>(q.getResultList());
    }

    @Override
    public List<BusinessUnit> getBusinessUnits(BusinessUnit parentBusinessUnit) {
        Query q;
        if(parentBusinessUnit != null) {
            q = em.createNamedQuery(BusinessUnit.NQ_FIND_BY_PARENT_BUSINESS_UNIT);
            q.setParameter("parentBusinessUnit", parentBusinessUnit);
        } else {
            q = em.createNamedQuery(BusinessUnit.NQ_FIND_BY_NULL_PARENT_BUSINESS_UNIT);
            q.setParameter(ORGANIZATION_KEY, session.getOrganization());
        }

        return new ArrayList<BusinessUnit>(q.getResultList());
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
                    return (List<E>) getUserOrganizations((Organization) parameter);
                } else if(parameter instanceof User) {
                    return (List<E>) getUserOrganizations((User) parameter);
                }
            } else {
                return (List<E>) getUserOrganizations(session.getOrganization());
            }
        } else if (entityClass == Team.class) {
            if (extraParameters.length == 0) {
                return (List<E>) getTeams(session.getOrganization());
            }

            Object parameter;
            if ((parameter = extraParameters[0]) instanceof BusinessUnit) {
                return (List<E>) getTeams((DataObjectBean) parameter);
            }
        } else if (entityClass == BusinessUnit.class) {
            if(extraParameters.length == 0) {
                return (List<E>) getBusinessUnits();
            } else {
                return (List<E>) getBusinessUnits((BusinessUnit) extraParameters[0]);
            }
        }

        return super.getEntities(entityClass, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        if(!canDo(Operation.Read, entity, itemClass, extraParameters)) {
            return Collections.emptyList();
        }

        if(itemClass == TeamMember.class) {
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
        } else if(entity instanceof BusinessUnit) {
            if(BusinessUnitAddress.class == itemClass) {
                return (List<I>) getBusinessUnitAddresses((BusinessUnit) entity, extraParameters);
            } else if(JobTitle.class == itemClass) {
                return (List<I>) getJobTitles((BusinessUnit) entity, extraParameters);
            }
        } else if(entity instanceof UserOrganization) {
            if(UserSecurityRole.class == itemClass) {
                return (List<I>) getUserSecurityRoles((UserOrganization) entity);
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
        } else if (entity instanceof BusinessUnit) {
            BusinessUnit businessUnit = (BusinessUnit)entity;
            businessUnit.setOrganization(session.getOrganization());
            List<BusinessUnit> businessUnits;
            if((businessUnits = getBusinessUnits(null)).size() == 0) {
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
    protected <E, I> void initItem(E entity, I item) {
        if (entity instanceof BusinessUnit) {
            BusinessUnit businessUnit = (BusinessUnit)entity;
            if (item instanceof BusinessUnitAddress) {
                ((BusinessUnitAddress)item).setBusinessUnit(businessUnit);
            } else if (item instanceof JobTitle) {
                ((JobTitle)item).setBusinessUnit(businessUnit);
            }
        } else if(entity instanceof Team) {
            if(item instanceof TeamMember) {
                TeamMember member = (TeamMember) item;
                member.setStatus(AccountStatus.Enabled.getDbResource());
            }
        }
    }

    @Override
    protected <E> void preSave(E entity) {
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
        }
    }

    private void preSaveBusinessUnit(BusinessUnit businessUnit) {
        if(businessUnit.isRoot()) {
            List<BusinessUnit> businessUnits;
            if((businessUnits = getBusinessUnits(null)).size() > 0 && !businessUnits.get(0).equals(businessUnit)) {
                throw new RuntimeException("Only one root business unit is allowed.");
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
    protected <E> void postSave(E entity) {
        if (entity instanceof BusinessUnit) {
            BusinessUnit businessUnit = (BusinessUnit) entity;
            postSaveBusinessUnit(businessUnit);
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
    protected <E> void preDelete(E entity) {
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
