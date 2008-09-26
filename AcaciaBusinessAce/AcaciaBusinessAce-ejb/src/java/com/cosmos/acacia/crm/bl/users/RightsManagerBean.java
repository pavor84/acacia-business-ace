package com.cosmos.acacia.crm.bl.users;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.bl.impl.EntityManagerFacadeRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.enums.UserRightType;
import com.cosmos.acacia.crm.gui.LocalSession;

/**
 * The class supports both server-side and client-side invocations
 * This explains the some private wrapper methods, which distinguish
 * between the two types of invocations.
 *
 * @author Bozhidar Bozhanov
 *
 */
@Stateful
public class RightsManagerBean
    implements RightsManagerRemote, RightsManagerLocal {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserRightsLocal rightsHelper;

    @EJB
    AcaciaSessionLocal session;

    @EJB
    UsersLocal usersManager;

    private Set<UserRight> generalRights;
    private Set<UserRight> specialRights;

    // client-side managers

    private EntityManagerFacadeRemote emFacade;
    private AcaciaSessionRemote sessionRemote;
    private UsersRemote usersManagerRemote;
    private UserRightsRemote rightsHelperRemote;


    public RightsManagerBean() {

    }

    /**
     * Client-side instantiation method. Using the manager from the client
     * optimizes requests to the server, and thus - operational time
     *
     * @param emFacade an entity manager facade
     */
    public RightsManagerBean(EntityManagerFacadeRemote emFacade) {
        this.emFacade = emFacade;
        try {
            rightsHelperRemote = InitialContext.doLookup(UserRightsRemote.class.getName());
            usersManagerRemote = InitialContext.doLookup(UsersRemote.class.getName());
            sessionRemote = InitialContext.doLookup(AcaciaSessionRemote.class.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public boolean isAllowed(DataObject dataObject,
            UserRightType rightType) {

        return isAllowed(getUser(), dataObject, rightType);

    }

    @Override
    public boolean isAllowed(DataObject dataObject,
            SpecialPermission specialPermission) {

        return isAllowed(getUser(), dataObject, specialPermission);

    }

    @Override
    public boolean isAllowed(User user,
            DataObject dataObject,
            UserRightType rightType) {

        Set<UserRight> rights = fetchRights(user, dataObject, false);

        UserRight highestPriorityRegularRight = getHigherPriorityRight(rights, false);
        UserRight highestPriorityExclusionRight = getHigherPriorityRight(rights, true);

        if (highestPriorityRegularRight == null)
            return false;

        UserRight highestPriorityRight =
                compareRights(highestPriorityRegularRight, highestPriorityExclusionRight);

        // Setting excluded rights
        if (highestPriorityRight == highestPriorityExclusionRight) {
            highestPriorityRight.setCreate(
                    highestPriorityRegularRight.canCreate()
                    && !highestPriorityExclusionRight.canCreate());

            highestPriorityRight.setRead(
                    highestPriorityRegularRight.canRead()
                    && !highestPriorityExclusionRight.canRead());

            highestPriorityRight.setDelete(
                    highestPriorityRegularRight.canDelete()
                    && !highestPriorityExclusionRight.canDelete());

            highestPriorityRight.setModify(
                    highestPriorityRegularRight.canModify()
                    && !highestPriorityExclusionRight.canModify());
        }


//        if (highestPriorityRight.isExcluded())
//            return false;

        if (rightType == UserRightType.READ)
            return highestPriorityRight.canRead();

        if (rightType == UserRightType.CREATE)
            return highestPriorityRight.canCreate();

        if (rightType == UserRightType.MODIFY)
            return highestPriorityRight.canModify();

        if (rightType == UserRightType.DELETE)
            return highestPriorityRight.canDelete();

        return false;
    }

    @Override
    public boolean isAllowed(User user, DataObject dataObject,
            SpecialPermission specialPermission) {

        Set<UserRight> rights = fetchRights(user, dataObject, true);

        for (UserRight right : rights) {
            //TODO: this check might be incorrect
            if (right.getSpecialPermission().equals(specialPermission.getDbResource()))
                return true;
        }

        return false;
    }

    private UserRight getHigherPriorityRight(Set<UserRight> rights, boolean countExcluded) {

        UserRight highestPriorityRight = null;
        for (UserRight right : rights) {

            if (right.isExcluded() ^ countExcluded)
                continue;

            if (highestPriorityRight == null) {
                highestPriorityRight = right;
            } else {
                highestPriorityRight = compareRights(right, highestPriorityRight);
            }
        }

        return highestPriorityRight;
    }

    private UserRight compareRights(UserRight right1, UserRight right2) {
        UserRight higherPriorityRight = null;

        if (right1 == right2)
            return right1;

        if (right1 == null)
            return right2;

        if (right2 == null)
            return right1;


        if (right1.getUser() != null && right2.getUser() == null) {
            higherPriorityRight = right1;
        } else if (right2.getUser() != null && right1.getUser() == null) {
            higherPriorityRight = right2;
        } else if (right1.getDataObject() != null && right2.getDataObject() == null){
            higherPriorityRight = right1;
        } else {
            higherPriorityRight = right2;
        }

        return higherPriorityRight;
    }

    private Set<UserRight> fetchRights(User user, DataObject dataObject, boolean isSpecial) {

        Set<UserRight> rights = isSpecial ? specialRights : generalRights;

        if (rights == null) {
            rights = fetchRights(user, isSpecial);
            if (isSpecial)
                specialRights = new HashSet<UserRight>(rights);
            else
                generalRights = new HashSet<UserRight>(rights);
        }


        // Creating a set of applicable rights for the current data object
        // If the set of permissions is empty, do the operation for the parent
        // object, until the parent becomes null

        Set<UserRight> applicableRights = new HashSet<UserRight>();
        DataObject tmpDataObject = dataObject;

        while (applicableRights.size() == 0 && tmpDataObject != null) {
            for (UserRight right : rights) {
                if (right.getDataObjectType() == null) {
                    applicableRights.add(right);
                } else {
                    if (right.getDataObject() != null) {
                        if (right.getDataObject().equals(tmpDataObject))
                            applicableRights.add(right);
                    }
                    else if (right.getDataObjectType().equals(tmpDataObject.getDataObjectType()))
                        applicableRights.add(right);
                }
            }
            if (tmpDataObject.getParentDataObjectId() != null)
                if (em != null)
                    tmpDataObject = em.find(DataObject.class, tmpDataObject.getParentDataObjectId());
                else
                    tmpDataObject = emFacade.find(DataObject.class, tmpDataObject.getParentDataObjectId());
            else
                tmpDataObject = null;

        }

        return applicableRights;
    }


    @SuppressWarnings("null")
    private Set<UserRight> fetchRights(User user, boolean isSpecial) {
        // Getting the rights for this specific user, if any
        Set<UserRight> userSpecificRights = null;

        if (!isSpecial)
            userSpecificRights = getUserRights(user);
        else
            userSpecificRights = getSpecialPermissions(user);



        // Getting the directly assigned group for this user
        UserOrganization uo = null;
        if (usersManager != null)
            uo = usersManager.getUserOrganization(user, getOrganization());
        else
            uo = usersManagerRemote.getUserOrganization(user, getOrganization());

        UserGroup group = uo.getUserGroup();


        // if there is no directly assigned group, get the group
        // via the position type.

        if (group == null) {
            Address branch = uo.getBranch();
            if (branch != null) {
                ContactPerson contact = null;
                if (em != null) {
                    Query q = em.createNamedQuery("ContactPerson.findByPersonAndParentDataObject");
                    q.setParameter("person", user.getPerson());
                    q.setParameter("parentDataObjectId", branch.getAddressId());
                    try {
                        contact = (ContactPerson) q.getSingleResult();
                    } catch (Exception ex) {
                        // No match
                    }
                } else {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("person", user.getPerson());
                    params.put("parentDataObjectId", branch.getAddressId());
                    try {
                        contact = (ContactPerson) emFacade.getNamedQueryResult(
                            "ContactPerson.findByPersonAndParentDataObject",
                            params).get(0);
                    } catch (Exception ex){
                        // No match
                    }
                }

                try {
                    PositionType positionType = contact.getPositionType();
                    if (positionType != null)
                        group = positionType.getUserGroup();
                } catch (NullPointerException ex) {
                    // ignored
                }
            }
        }

        Set<UserRight> groupRights = new HashSet<UserRight>();
        if (group != null) {
            if (!isSpecial)
                groupRights = getUserRights(group);
            else
                groupRights = getSpecialPermissions(group);
        }

        // Creating a set of all rights for this user
        Set<UserRight> rights = new HashSet<UserRight>();

        rights.addAll(userSpecificRights);
        rights.addAll(groupRights);

        return rights;
    }

    @Override
    public void setGeneralRights(Set<UserRight> rights) {
        generalRights = rights;
    }

    @Override
    public void setSpecialRights(Set<UserRight> rights) {
        specialRights = rights;
    }

    private User getUser() {
        if (session != null)
            return session.getUser();

        return sessionRemote.getUser();
    }

    private Organization getOrganization() {
        if (session != null)
            return session.getOrganization();

        return (Organization) LocalSession.get(LocalSession.ORGANIZATION);
    }

    private Set<UserRight> getUserRights(User user) {
        if (rightsHelper != null)
            return rightsHelper.getUserRights(user);

        return rightsHelperRemote.getUserRights(user);
    }

    private Set<UserRight> getSpecialPermissions(User user) {
        if (rightsHelper != null)
            return rightsHelper.getSpecialPermissions(user);

        return rightsHelperRemote.getSpecialPermissions(user);
    }

    private Set<UserRight> getUserRights(UserGroup group) {
        if (rightsHelper != null)
            return rightsHelper.getUserRights(group);

        return rightsHelperRemote.getUserRights(group);
    }

    private Set<UserRight> getSpecialPermissions(UserGroup group) {
        if (rightsHelper != null)
            return rightsHelper.getSpecialPermissions(group);

        return rightsHelperRemote.getSpecialPermissions(group);
    }


}
