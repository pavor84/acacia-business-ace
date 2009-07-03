package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.data.DataObjectBean;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.permission.PermissionLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.data.permission.DataObjectPermission;
import com.cosmos.acacia.crm.data.permission.DataObjectTypePermission;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.enums.UserRightType;
import java.math.BigInteger;
import java.util.Collections;
import java.util.EnumSet;
import java.util.TreeSet;

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
    private static final boolean DEFAULT_EXECUTE_RIGHT = true;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserRightsLocal rightsService;

    @EJB
    private AcaciaSessionLocal session;

    @EJB
    private UsersLocal usersManager;

    @EJB
    private PermissionLocal permissionService;

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

    @Override
    public boolean isAllowed(Set<SpecialPermission> specialPermissions) {
        DataObject dataObject = new DataObject();
        for(SpecialPermission permission : specialPermissions) {
            if(isAllowed(dataObject, permission))
                return true;
        }

        return false;
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
            if (rightType == UserRightType.EXECUTE)
                return DEFAULT_EXECUTE_RIGHT;
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
                    highestPriorityRight.setRead(generalSubsetArray[1].canRead());
                else
                    highestPriorityRight.setRead(DEFAULT_READ_RIGHT);

            if (highestPriorityExclusionRight.canModify())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setModify(generalSubsetArray[1].canModify());
                else
                    highestPriorityRight.setModify(DEFAULT_MODIFY_RIGHT);

            if (highestPriorityExclusionRight.canDelete())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setDelete(generalSubsetArray[1].canDelete());
                else
                    highestPriorityRight.setDelete(DEFAULT_DELETE_RIGHT);

            if (highestPriorityExclusionRight.canExecute())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setExecute(generalSubsetArray[1].canDelete());
                else
                    highestPriorityRight.setExecute(DEFAULT_DELETE_RIGHT);
        }


        if (rightType == UserRightType.READ)
            return highestPriorityRight.canRead();

        if (rightType == UserRightType.CREATE)
            return highestPriorityRight.canCreate();

        if (rightType == UserRightType.MODIFY)
            return highestPriorityRight.canModify();

        if (rightType == UserRightType.DELETE)
            return highestPriorityRight.canDelete();

        if (rightType == UserRightType.EXECUTE)
            return highestPriorityRight.canExecute();

        return false;
    }

    @Override
    public boolean isAllowed(
            User user,
            DataObject dataObject,
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

    private Set<UserRight> fetchRights(User user, final DataObject dataObject, boolean isSpecial) {
        Set<UserRight> rights;
        if(isSpecial) {
            rights = session.getSpecialPermissions();
        } else {
            rights = session.getGeneralRights();
        }

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
                DataObjectType dot;
                if ((dot = right.getDataObjectType()) == null) {
                    // If neighter type nor object is specified, the right
                    // is valid for all objects
                    applicableRights.add(right);
                } else {
                    if (right.getDataObject() != null) {
                        if (right.getDataObject().equals(tmpDataObject))
                            applicableRights.add(right);
                    }
                    else if (dot.equals(tmpDataObject.getDataObjectType()))
                        applicableRights.add(right);
                }
            }

            BigInteger parentDataObjectId;
            if ((parentDataObjectId = tmpDataObject.getParentDataObjectId()) != null)
                tmpDataObject = em.find(DataObject.class, parentDataObjectId);
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
    	if (session != null)
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
        return rightsService.getUserRights(user);
    }

    private Set<UserRight> getSpecialPermissions(User user) {
        return rightsService.getSpecialPermissions(user);
    }

    private Set<UserRight> getUserRights(UserGroup group) {
        return rightsService.getUserRights(group);
    }

    private Set<UserRight> getSpecialPermissions(UserGroup group) {
        return rightsService.getSpecialPermissions(group);
    }

    private UserRight[] toArray(Set<UserRight> set) {
        UserRight[] array = new UserRight[set.size()];
        int i = 0;
        for (UserRight right : set) {
            array[i++] = right;
        }
        return array;
    }

    @Override
    public Set<SpecialPermission> getPermissions(DataObject dataObject, UserRightType rightType) {
        if(dataObject == null) {
            return Collections.emptySet();
        }

        Set<DataObjectPermission> doPermissions =
                permissionService.getDataObjectPermissions(dataObject, rightType);

        TreeSet<SpecialPermission> permissions = new TreeSet<SpecialPermission>();
        if(doPermissions != null && doPermissions.size() > 0) {
            for(DataObjectPermission doPermission : doPermissions) {
                permissions.add((SpecialPermission)doPermission.getUserRightType().getEnumValue());
            }
        }

        Set<SpecialPermission> typePermissions;
        if((typePermissions = getPermissions(dataObject.getDataObjectType(), rightType)) != null
                && typePermissions.size() > 0) {
            for(SpecialPermission permission : typePermissions) {
                if(!permissions.contains(permission)) {
                    permissions.add(permission);
                }
            }
        }

        if(permissions.size() == 0) {
            return Collections.emptySet();
        }

        return EnumSet.copyOf(permissions);
    }

    @Override
    public Set<SpecialPermission> getPermissions(DataObjectType dataObjectType, UserRightType rightType) {
        if(dataObjectType == null) {
            return Collections.emptySet();
        }

        return getPermissions(permissionService.getDataObjectTypePermissions(dataObjectType, rightType));
    }

    @Override
    public Set<SpecialPermission> getPermissions(Class<? extends DataObjectBean> entityClass, UserRightType rightType) {
        if(entityClass == null) {
            return Collections.emptySet();
        }

        return getPermissions(permissionService.getDataObjectTypePermissions(entityClass, rightType));
    }

    private Set<SpecialPermission> getPermissions(Set<DataObjectTypePermission> dotPermissions) {
        if(dotPermissions == null || dotPermissions.size() == 0) {
            return Collections.emptySet();
        }

        TreeSet<SpecialPermission> set = new TreeSet<SpecialPermission>();
        for(DataObjectTypePermission dotPermission : dotPermissions) {
            set.add((SpecialPermission)dotPermission.getUserRightType().getEnumValue());
        }

        if(set.size() == 0) {
            return Collections.emptySet();
        }

        return EnumSet.copyOf(set);
    }
}
