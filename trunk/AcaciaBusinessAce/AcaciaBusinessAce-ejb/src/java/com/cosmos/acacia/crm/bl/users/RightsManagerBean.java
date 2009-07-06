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
import com.cosmos.acacia.crm.data.Right;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.permission.DataObjectPermission;
import com.cosmos.acacia.crm.data.permission.DataObjectTypePermission;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.security.AccessRight;
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
            AccessRight rightType) {

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
            AccessRight rightType) {

        Set<Right> rights = fetchRights(user, dataObject, false);

        Set<Right> generalSubset = getRightsSubset(rights, false);
        Set<Right> excludedSubset = getRightsSubset(rights, true);

        Right[] generalSubsetArray = toArray(generalSubset);
        Right[] excludedSubsetArray = toArray(excludedSubset);

        //Sorting arrays by priority
        Arrays.sort(generalSubsetArray);
        Arrays.sort(excludedSubsetArray);

        // default behavior : to allow or disallow access
        // when no settings are present
        if (generalSubsetArray.length == 0) {
            if (rightType == AccessRight.Create)
                return DEFAULT_CREATE_RIGHT;
            if (rightType == AccessRight.Read)
                return DEFAULT_READ_RIGHT;
            if (rightType == AccessRight.Modify)
                return DEFAULT_MODIFY_RIGHT;
            if (rightType == AccessRight.Delete)
                return DEFAULT_DELETE_RIGHT;
            if (rightType == AccessRight.Execute)
                return DEFAULT_EXECUTE_RIGHT;
        }

//        if (generalSubsetArray.length > 1) {
//            UserRight r = compareRights(generalSubsetArray[0], generalSubsetArray[1]);
//            if (generalSubsetArray[0] != r) {
//                System.out.println("FAKE");
//            }
//        }

        Right highestPriorityRegularRight = generalSubsetArray[0];
        Right highestPriorityExclusionRight = null;
        try {
            highestPriorityExclusionRight = excludedSubsetArray[0];
        } catch (Exception e) {
            //ignore
        }

        Right highestPriorityRight =
                compareRights(highestPriorityRegularRight, highestPriorityExclusionRight);

        // Excluded rights; here canXxx() means that Xxx is excluded
        if (highestPriorityRight.isExcluded()) {

            if (highestPriorityExclusionRight.isCreate())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setCreate(generalSubsetArray[1].isCreate());
                else
                    highestPriorityRight.setCreate(DEFAULT_CREATE_RIGHT);

            if (highestPriorityExclusionRight.isRead())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setRead(generalSubsetArray[1].isRead());
                else
                    highestPriorityRight.setRead(DEFAULT_READ_RIGHT);

            if (highestPriorityExclusionRight.isModify())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setModify(generalSubsetArray[1].isModify());
                else
                    highestPriorityRight.setModify(DEFAULT_MODIFY_RIGHT);

            if (highestPriorityExclusionRight.isDelete())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setDelete(generalSubsetArray[1].isDelete());
                else
                    highestPriorityRight.setDelete(DEFAULT_DELETE_RIGHT);

            if (highestPriorityExclusionRight.isExecute())
                if (generalSubsetArray.length > 1)
                    highestPriorityRight.setExecute(generalSubsetArray[1].isDelete());
                else
                    highestPriorityRight.setExecute(DEFAULT_DELETE_RIGHT);
        }


        if (rightType == AccessRight.Read)
            return highestPriorityRight.isRead();

        if (rightType == AccessRight.Create)
            return highestPriorityRight.isCreate();

        if (rightType == AccessRight.Modify)
            return highestPriorityRight.isModify();

        if (rightType == AccessRight.Delete)
            return highestPriorityRight.isDelete();

        if (rightType == AccessRight.Execute)
            return highestPriorityRight.isExecute();

        return false;
    }

    @Override
    public boolean isAllowed(
            User user,
            DataObject dataObject,
            SpecialPermission specialPermission) {

        Set<Right> rights = fetchRights(user, dataObject, true);

        for (Right right : rights) {
            if (specialPermission.matches(right.getSpecialPermission()))
                return true;
        }

        return false;
    }

    private Set<Right> getRightsSubset(Set<Right> rights, boolean countExcluded) {

        Set<Right> subset = new HashSet<Right>();

        for (Right right : rights) {
            if (right.isExcluded() ^ countExcluded)
                continue;
            subset.add(right);
        }

        return subset;
    }

    /**
     * Compares two rights and returns the more concrete one.
     * If the two are equally concrete, the second one is returned.
     * CREATE INDEX uix_user_rights
     *  ON user_rights
     *  USING btree(
     *   organization_id,
     *   owner_type_id,
     *   owner_id,
     *   data_object_type_id,
     *   data_object_id,
     *   permission_category_id,
     *   special_permission_id);
     *
     * @param right1
     * @param right2
     * @return the more concrete right
     */
    public static Right compareRights(Right right1, Right right2) {
        Right higherPriorityRight = null;
        if (right1 == right2)
            return right2;

        if (right1 == null)
            return right2;

        if (right2 == null)
            return right1;

        if(right1.compareTo(right2) > 0) {
            return right1;
        } else {
            return right2;
        }
    }

    private Set<Right> fetchRights(User user, final DataObject dataObject, boolean isSpecial) {
        Set<Right> rights;
        if(isSpecial) {
            rights = session.getSpecialPermissions();
        } else {
            rights = session.getGeneralRights();
        }

        if (rights == null) {
            rights = fetchRights(user, isSpecial);
            if (isSpecial)
                session.setSpecialPermissions(new HashSet<Right>(rights));
            else
                session.setGeneralRights(new HashSet<Right>(rights));
        }


        // Creating a set of applicable rights for the current data object
        // If the set of permissions is empty, do the operation for the parent
        // object, until the parent becomes null

        Set<Right> applicableRights = new HashSet<Right>();
        DataObject tmpDataObject = dataObject;

        while (applicableRights.size() == 0 && tmpDataObject != null) {
            for (Right right : rights) {
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
    private Set<Right> fetchRights(User user, boolean isSpecial) {
        // Getting the rights for this specific user, if any
        Set<Right> userSpecificRights = null;

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

        Set<Right> groupRights = new HashSet<Right>();
        if (group != null) {
            if (!isSpecial)
                groupRights = getUserRights(group);
            else
                groupRights = getSpecialPermissions(group);
        }

        // Creating a set of all rights for this user
        Set<Right> rights = new HashSet<Right>();

        rights.addAll(userSpecificRights);
        rights.addAll(groupRights);

        return rights;
    }

    @Override
    public void setGeneralRights(Set<Right> rights) {
        session.setGeneralRights(rights);
    }

    @Override
    public void setSpecialRights(Set<Right> rights) {
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

    private Set<Right> getUserRights(User user) {
        return rightsService.getRights(user);
    }

    private Set<Right> getSpecialPermissions(User user) {
        return rightsService.getSpecialPermissions(user);
    }

    private Set<Right> getUserRights(UserGroup group) {
        return rightsService.getRights(group);
    }

    private Set<Right> getSpecialPermissions(UserGroup group) {
        return rightsService.getSpecialPermissions(group);
    }

    private Right[] toArray(Set<Right> set) {
        return set.toArray(new Right[set.size()]);
    }

    @Override
    public Set<SpecialPermission> getPermissions(DataObject dataObject, AccessRight rightType) {
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
    public Set<SpecialPermission> getPermissions(DataObjectType dataObjectType, AccessRight rightType) {
        if(dataObjectType == null) {
            return Collections.emptySet();
        }

        return getPermissions(permissionService.getDataObjectTypePermissions(dataObjectType, rightType));
    }

    @Override
    public Set<SpecialPermission> getPermissions(Class<? extends DataObjectBean> entityClass, AccessRight rightType) {
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
