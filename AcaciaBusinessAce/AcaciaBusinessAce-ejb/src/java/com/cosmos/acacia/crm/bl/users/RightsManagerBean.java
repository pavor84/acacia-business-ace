package com.cosmos.acacia.crm.bl.users;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.app.AcaciaSessionLocal;
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

    private static final boolean DEFAULT_CREATE_RIGHT = true;
    private static final boolean DEFAULT_READ_RIGHT = true;
    private static final boolean DEFAULT_MODIFY_RIGHT = true;
    private static final boolean DEFAULT_DELETE_RIGHT = true;

    @PersistenceContext
    EntityManager em;

    @EJB
    UserRightsLocal rightsHelper;

    @EJB
    AcaciaSessionLocal session;

    @EJB
    UsersLocal usersManager;

    public RightsManagerBean() {

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
    public boolean isAllowed(SpecialPermission specialPermission) {
        return isAllowed(new DataObject(), specialPermission);
    }

    @SuppressWarnings("null")
    @Override
    public boolean isAllowed(User user,
            DataObject dataObject,
            UserRightType rightType) {

        Set<UserRight> rights = fetchRights(user, dataObject, false);

        Set<UserRight> generalSubset = getRightsSubset(rights, false);
        Set<UserRight> excludedSubset = getRightsSubset(rights, true);

        UserRight[] generalSubsetArray = toArray(generalSubset);
        UserRight[] excludedSubsetArray = toArray(excludedSubset);

        //Sorting arrays by priority
        Arrays.sort(generalSubsetArray);
        Arrays.sort(excludedSubsetArray);

        // default behavior : to allow or disallow access
        // when no settings are present
        if (generalSubsetArray.length == 0) {
            if (rightType == UserRightType.CREATE)
                return DEFAULT_CREATE_RIGHT;
            if (rightType == UserRightType.READ)
                return DEFAULT_READ_RIGHT;
            if (rightType == UserRightType.MODIFY)
                return DEFAULT_MODIFY_RIGHT;
            if (rightType == UserRightType.DELETE)
                return DEFAULT_DELETE_RIGHT;
        }

//        if (generalSubsetArray.length > 1) {
//            UserRight r = compareRights(generalSubsetArray[0], generalSubsetArray[1]);
//            if (generalSubsetArray[0] != r) {
//                System.out.println("FAKE");
//            }
//        }

        UserRight highestPriorityRegularRight = generalSubsetArray[0];
        UserRight highestPriorityExclusionRight = null;
        try {
            highestPriorityExclusionRight = excludedSubsetArray[0];
        } catch (Exception e) {
            //ignore
        }


        UserRight highestPriorityRight =
                compareRights(highestPriorityRegularRight, highestPriorityExclusionRight);

        // Excluded rights; here canXxx() means that Xxx is excluded
        if (highestPriorityRight.isExcluded()) {

            if (highestPriorityExclusionRight.canCreate())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setCreate(generalSubsetArray[1].canCreate());
                else
                    highestPriorityRight.setCreate(DEFAULT_CREATE_RIGHT);

            if (highestPriorityExclusionRight.canRead())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setCreate(generalSubsetArray[1].canRead());
                else
                    highestPriorityRight.setCreate(DEFAULT_READ_RIGHT);

            if (highestPriorityExclusionRight.canModify())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setCreate(generalSubsetArray[1].canModify());
                else
                    highestPriorityRight.setCreate(DEFAULT_MODIFY_RIGHT);

            if (highestPriorityExclusionRight.canDelete())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setCreate(generalSubsetArray[1].canDelete());
                else
                    highestPriorityRight.setCreate(DEFAULT_DELETE_RIGHT);
        }


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
            if (specialPermission.matches(right.getSpecialPermission()))
                return true;
        }

        return false;
    }

    private Set<UserRight> getRightsSubset(Set<UserRight> rights, boolean countExcluded) {

        Set<UserRight> subset = new HashSet<UserRight>();

        for (UserRight right : rights) {
            if (right.isExcluded() ^ countExcluded)
                continue;
            subset.add(right);
        }

        return subset;
    }

    /**
     * Compares two rights and returns the more concrete one.
     * If the two are equally concrete, the second one is returned.
     *
     * @param right1
     * @param right2
     * @return the more concrete right
     */
    public static UserRight compareRights(UserRight right1, UserRight right2) {

        UserRight higherPriorityRight = null;

        if (right1 == right2)
            return right2;

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
        } else if (right2.getDataObject() != null && right1.getDataObject() == null){
            higherPriorityRight = right2;
        } else {
            // checking parent-child relations
            if (right1.getDataObject().getDataObjectId()
                    .equals(right2.getDataObject().getParentDataObjectId())) {
                return right2;
            } else if (right2.getDataObject().getDataObjectId()
                    .equals(right1.getDataObject().getParentDataObjectId())) {
                return right1;
            } else {
                higherPriorityRight = right2;
            }
        }

        return higherPriorityRight;
    }

    private Set<UserRight> fetchRights(User user, DataObject dataObject, boolean isSpecial) {

        Set<UserRight> rights = isSpecial
            ? session.getSpecialPermissions() : session.getGeneralRights();

        if (rights == null) {
            rights = fetchRights(user, isSpecial);
            if (isSpecial)
                session.setSpecialPermissions(new HashSet<UserRight>(rights));
            else
                session.setGeneralRights(new HashSet<UserRight>(rights));
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
                tmpDataObject = em.find(DataObject.class, tmpDataObject.getParentDataObjectId());
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
        UserOrganization uo = usersManager.getUserOrganization(user, getOrganization());

        UserGroup group = uo.getUserGroup();


        // if there is no directly assigned group, get the group
        // via the position type.

        if (group == null)
            group = usersManager.getUserGroupByPositionType();

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
        session.setGeneralRights(rights);
    }

    @Override
    public void setSpecialRights(Set<UserRight> rights) {
        session.setSpecialPermissions(rights);
    }

    @Override
    public void clearCachedRights() {
        setSpecialRights(null);
        setGeneralRights(null);
    }
    
    private User getUser() {
        return session.getUser();
    }

    private Organization getOrganization() {
        return session.getOrganization();

    }

    private Set<UserRight> getUserRights(User user) {
        return rightsHelper.getUserRights(user);
    }

    private Set<UserRight> getSpecialPermissions(User user) {
        return rightsHelper.getSpecialPermissions(user);
    }

    private Set<UserRight> getUserRights(UserGroup group) {
        return rightsHelper.getUserRights(group);
    }

    private Set<UserRight> getSpecialPermissions(UserGroup group) {
        return rightsHelper.getSpecialPermissions(group);
    }

    private UserRight[] toArray(Set<UserRight> set) {
        UserRight[] array = new UserRight[set.size()];
        int i = 0;
        for (UserRight right : set) {
            array[i++] = right;
        }
        return array;
    }
}
