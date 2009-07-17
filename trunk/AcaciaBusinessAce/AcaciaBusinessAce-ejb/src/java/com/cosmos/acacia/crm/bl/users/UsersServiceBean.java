/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.TeamMember;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.enums.BusinessUnitType;
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
public class UsersServiceBean extends AbstractEntityService implements UsersServiceRemote, UsersServiceLocal {

    public static final String ORGANIZATION_KEY = "organization";
    public static final String BUSINESS_UNIT_KEY = "businessUnit";
    public static final String TEAM_KEY = "team";
    public static final String USER_KEY = "user";

    public List<User> getUsers(Organization organization) {
        if (organization == null || organization.getId() == null) {
            Query q = em.createNamedQuery("User.findAll");
            return new ArrayList<User>(q.getResultList());
        }

        Query q = em.createNamedQuery("UserOrganization.findByOrganization");
        q.setParameter(ORGANIZATION_KEY, organization);
        List<UserOrganization> uoList = q.getResultList();
        List<User> users = new ArrayList<User>(uoList.size());
        for (UserOrganization userOrganization : uoList) {
            User user = userOrganization.getUser();
            if (userOrganization.getBranch() != null) {
                user.setBranchName(userOrganization.getBranch().getAddressName());
            }

            user.setActive(userOrganization.isUserActive());
            users.add(user);
        }

        return users;
    }

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

    public List<BusinessUnit> getBusinessUnits() {
        Query q = em.createNamedQuery(BusinessUnit.NQ_FIND_ALL);
        q.setParameter("organization", session.getOrganization());

        return new ArrayList<BusinessUnit>(q.getResultList());
    }

    public List<BusinessUnit> getBusinessUnits(BusinessUnit parentBusinessUnit) {
        Query q;
        if(parentBusinessUnit != null) {
            q = em.createNamedQuery(BusinessUnit.NQ_FIND_BY_PARENT_BUSINESS_UNIT);
            q.setParameter("parentBusinessUnit", parentBusinessUnit);
        } else {
            q = em.createNamedQuery(BusinessUnit.NQ_FIND_BY_NULL_PARENT_BUSINESS_UNIT);
            q.setParameter("organization", session.getOrganization());
        }

        return new ArrayList<BusinessUnit>(q.getResultList());
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        if (entityClass == User.class) {
            return (List<E>) getUsers(session.getOrganization());
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

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        if(itemClass == TeamMember.class) {
            Query q;
            if(entity instanceof Team) {
                q = em.createNamedQuery(TeamMember.NQ_FIND_BY_TEAM);
                q.setParameter(TEAM_KEY, entity);
            } else if(entity instanceof User) {
                q = em.createNamedQuery(TeamMember.NQ_FIND_BY_USER);
                q.setParameter(USER_KEY, entity);
            } else {
                throw new UnsupportedOperationException("Not supported parent entity: " + entity);
            }

            return new ArrayList<I>(q.getResultList());
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected <E> void initEntity(E entity) {
        if (entity instanceof Team) {
            ((Team)entity).setOrganization(session.getOrganization());
        } else if (entity instanceof BusinessUnit) {
            BusinessUnit businessUnit = (BusinessUnit)entity;
            businessUnit.setOrganization(session.getOrganization());
            if(getBusinessUnits(null).size() == 0) {
                businessUnit.setRoot(true);
                businessUnit.setBusinessUnitType(BusinessUnitType.Administrative.getDbResource());
                businessUnit.setBusinessUnitName(session.getOrganization().getOrganizationName());
                businessUnit.setDivisionName(BusinessUnitType.Administrative.name());
            }
        }
    }

    @Override
    protected <E> void preSave(E entity) {
        if (entity instanceof User) {
            User user = (User) entity;
        }
        if (entity instanceof Team) {
            Team team = (Team) entity;
            Organization organization;
            if ((organization = team.getOrganization()) == null || !organization.equals(session.getOrganization())) {
                team.setOrganization(session.getOrganization());
            }
        }
        if (entity instanceof TeamMember) {
            TeamMember member = (TeamMember) entity;
        }
    }
}
