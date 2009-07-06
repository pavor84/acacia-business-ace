package com.cosmos.acacia.crm.bl.users;

import java.util.Set;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Right;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.security.AccessRight;

@Remote
public interface RightsManagerRemote {

    /**
     * Checks whether the current user has permission on the specified object
     *
     * @param dataObject
     * @param rightType
     * @return true if the user is allowed, false otherwise
     */
    boolean isAllowed(DataObject dataObject, AccessRight rightType);

    /**
     * Checks whether the current user has the specified special permission.
     *
     * @param dataObject
     * @param specialPermission
     * @return
     */
    boolean isAllowed(DataObject dataObject,
            SpecialPermission specialPermission);

    /**
     * Checks whether the current user has the specified special permission.
     * Use this method if the permission does not concern any dataObject
     *
     * @param dataObject
     * @param specialPermission
     * @return
     */
    boolean isAllowed(SpecialPermission specialPermission);

    boolean isAllowed(Set<SpecialPermission> specialPermissions);

    /**
     * Checks whether the user has permission on the specified object
     *
     * @param user
     * @param dataObject
     * @param rightType
     * @return true if the user is allowed, false otherwise
     */
    boolean isAllowed(User user, DataObject dataObject, AccessRight rightType);

    /**
     * Checks whether the user has the specified special permission.
     * dataObject may be null, if the permission does not concern
     * any data object
     *
     * @param user
     * @param dataObject
     * @param specialPermission
     * @return
     */
    boolean isAllowed(User user,
            DataObject dataObject,
            SpecialPermission specialPermission);

    /**
     * Setter for rights. Provided for unit testing
     *
     * @param rights
     */
    void setGeneralRights(Set<Right> rights);

    /**
     * Setter for rights. Provided for unit testing
     *
     * @param rights
     */
    void setSpecialRights(Set<Right> rights);

    /**
     * Clears the stored user rights
     */
    void clearCachedRights();

    Set<SpecialPermission> getPermissions(
            DataObject dataObject,
            AccessRight rightType);

    Set<SpecialPermission> getPermissions(
            DataObjectType dataObjectType,
            AccessRight rightType);

    Set<SpecialPermission> getPermissions(
            Class<? extends DataObjectBean> entityClass,
            AccessRight rightType);
}
