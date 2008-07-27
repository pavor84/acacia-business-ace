package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import java.math.BigInteger;
import java.util.Set;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.beansbinding.EntityProperties;
import java.util.List;

@Remote
public interface UserRightsRemote {


    /**
     * Gets the list of user groups for the specified parentId
     * 
     * @param parentId
     * @return the list
     */
    List<UserGroup> getUserGroups(BigInteger parentId);
    
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
    UserGroup newUserGroup(BigInteger parentId);

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
    void assingGroupToUser(UserGroup group, User user);

    /**
     * Assigns a set of general rights to a user
     *
     * @param rights
     * @param user
     */
    void assignRightsToUser(Set<UserRight> rights, User user);

    /**
     * Assigns a set of general rights to a group
     *
     * @param rights
     * @param group
     */
    void assignRightsToGroup(Set<UserRight> rights, UserGroup group);

    /**
     * Assigns a set of specific rights to a user
     *
     * @param permissions
     * @param user
     */
    void assignSpecialPermissionsToUser(
            Set<SpecialPermission> permissions,
            User user);

    /**
     * Assigns a set of specific rights to a group
     *
     * @param permissions
     * @param group
     */
    void assignSpecialPermissionsToGroup(
            Set<SpecialPermission> permissions,
            UserGroup group);


    /**
     * Removes a set of general rights from a user
     *
     * @param rights
     * @param user
     */
    void removeRightsFromUser(Set<UserRight> rights, User user);

    /**
     * Removes a set of general rights from a group
     *
     * @param rights
     * @param group
     */
    void removeRightsFromGroup(Set<UserRight> rights, UserGroup group);

    /**
     * Removes a set of specific rights from a user
     *
     * @param permissions
     * @param user
     */
    void removeSpecialPermissionsFromUser(
            Set<SpecialPermission> permissions,
            User user);

    /**
     * Removes a set of specific rights from a group
     *
     * @param permissions
     * @param group
     */
    void removeSpecialPermissionsFromGroup(
            Set<SpecialPermission> permissions,
            UserGroup group);

    /**
     * Lists the general rights for a user
     *
     * @param user
     * @return the set of rights
     */
    Set<UserRight> getUserRights(User user);

    /**
     * Lists the general rights for a user group
     *
     * @param userGroup
     * @return the set of rights
     */
    Set<UserRight> getUserRights(UserGroup userGroup);

    /**
     * Lists the special permissions (specific rights) for a user
     *
     * @param user
     * @return the set of rights
     */
    Set<SpecialPermission> getSpecialPermissions(User user);

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
}