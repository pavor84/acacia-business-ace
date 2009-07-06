package com.cosmos.acacia.crm.bl.users;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.data.Right;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.beansbinding.EntityProperties;

@Remote
public interface UserRightsRemote {

    /**
     * Gets the list of user groups for the specified parentId
     *
     * @param parentId
     * @return the list
     */
    List<UserGroup> getUserGroups();

    /**
     * Persists the specified user group
     *
     * @param group
     * @return the newly saved group
     */
    UserGroup saveUserGroup(UserGroup group);

    /**
     * Creates a new user group with the specified parent
     *
     * @param parentId
     * @return the new user group
     */
    UserGroup newUserGroup();

    /**
     * Deletes a user group
     *
     * @param group
     * @return the version
     */
    int deleteUserGroup(UserGroup group);

    /**
     * Gets the entity properties of UserGroup
     *
     * @return the entity properties
     */
    EntityProperties getUserGroupEntityProperties();

    /**
     * Gets the entity properties of UserRight
     *
     * @return the entity properties
     */
    EntityProperties getUserRightEntityProperties();

    EntityProperties getUserGroupRightEntityProperties();

    /**
     * Assigns a specified user group to a position type
     *
     * @param group
     * @param position
     */
    void assignGroupToPosition(UserGroup group, PositionType position);

    /**
     * Assigns a group to a user
     *
     * @param group
     * @param user
     */
    void assignGroupToUser(UserGroup group, User user);

    /**
     * Assigns a set of general rights to a user
     *
     * @param rights
     * @param user
     */
    void assignRightsToUser(Set<Right> rights, User user);

    /**
     * Assigns a set of general rights to a group
     *
     * @param rights
     * @param group
     */
    void assignRightsToGroup(Set<Right> rights, UserGroup group);

    /**
     * Assigns a set of specific rights to a user
     *
     * @param permissions
     * @param user
     */
    void assignSpecialPermissionsToUser(Set<Right> permissions, User user);

    /**
     * Assigns a set of specific rights to a group
     *
     * @param permissions
     * @param group
     */
    void assignSpecialPermissionsToGroup(Set<Right> permissions, UserGroup group);

    /**
     * Removes a set of rights.
     *
     * @param rights
     * @param user
     */
    void removeRights(Set<Right> rights);

    Right newRight(User user);

    Right newRight(UserGroup userGroup);

    /**
     * Lists the general rights for a user
     *
     * @param user
     * @return the set of rights
     */
    Set<Right> getRights(User user);

    /**
     * Lists the general rights for a user group
     *
     * @param userGroup
     * @return the set of rights
     */
    Set<Right> getRights(UserGroup userGroup);

    /**
     * Lists the special permissions (specific rights) for a user
     *
     * @param user
     * @return the set of rights
     */
    Set<Right> getSpecialPermissions(User user);

    /**
     * Lists the special permissions (specific rights) for a user group
     *
     * @param userGroup
     * @return the set of rights
     */
    Set<Right> getSpecialPermissions(UserGroup userGroup);

    /**
     * Lists data object types
     *
     * @return the list
     */
    List<DataObjectType> getDataObjectTypes();

    /**
     * Returns a list of all entities (DataObjectBean) for a given type
     *
     * @param dataObjectType
     * @return list of data object beans
     */
    List<DataObjectBean> getDataObjectBeans(DataObjectType dataObjectType);

    /**
     * Gets the DataObjectBean corresponding to a given data object
     *
     * @param dataObject
     * @return data object bean
     */
    DataObjectBean getDataObjectBean(DataObject dataObject);

    /**
     * Lists all special permissions avialable in the system
     *
     * @return a list of DbResource
     */
    List<DbResource> getSpecialPermissions();
}
