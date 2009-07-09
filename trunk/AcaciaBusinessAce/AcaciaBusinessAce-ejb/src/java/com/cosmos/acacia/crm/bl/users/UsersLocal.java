/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserGroup;
import com.cosmos.acacia.crm.data.users.UserGroupMember;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Local
public interface UsersLocal extends UsersRemote {

    /**
     * UserGroupMember
     */
    UserGroupMember getUserGroupMember(UserGroup userGroup, User user);

    List<UserGroupMember> getUserGroupMembers();

    List<UserGroupMember> getUserGroupMembers(User user);

    List<UserGroupMember> getUserGroupMembers(UserGroup userGroup);

    UserGroupMember newUserGroupMember();

    UserGroupMember newUserGroupMember(User user);

    UserGroupMember saveUserGroupMember(UserGroupMember userGroupMember);

    void deleteUserGroupMember(UserGroupMember userGroupMember);
}
