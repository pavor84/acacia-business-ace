package com.cosmos.acacia.crm.bl.users;

import java.util.Set;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.enums.UserRightType;

@Remote
public interface RightsManagerRemote {


    /**
     * Checks whether the current user has permission on the specified object
     *
     * @param dataObject
     * @param rightType
     * @return true if the user is allowed, false otherwise
     */
    boolean isAllowed(DataObject dataObject, UserRightType rightType);

    /**
     * Checks whether the current user has the specified special permission.
     * dataObject may be null, if the permission does not concern
     * any data object
     *
     * @param dataObject
     * @param specialPermission
     * @return
     */
    boolean isAllowed(DataObject dataObject,
            SpecialPermission specialPermission);


    /**
     * Checks whether the user has permission on the specified object
     *
     * @param user
     * @param dataObject
     * @param rightType
     * @return true if the user is allowed, false otherwise
     */
    boolean isAllowed(User user, DataObject dataObject, UserRightType rightType);

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
    void setGeneralRights(Set<UserRight> rights);

    /**
     * Setter for rights. Provided for unit testing
     *
     * @param rights
     */
    void setSpecialRights(Set<UserRight> rights);

}
