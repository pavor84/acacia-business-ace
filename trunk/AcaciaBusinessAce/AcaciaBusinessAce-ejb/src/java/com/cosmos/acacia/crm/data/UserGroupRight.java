/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Miro
 */
@Entity
@DiscriminatorValue(value = Right.USER_GROUP_TYPE_ID)
@NamedQueries({
    @NamedQuery(
        name = UserGroupRight.NQ_FIND_BY_USER_GROUP_AND_PERMISSION_NULL,
        query = "select t from UserGroupRight t" +
                " where" +
                "  t.organizationId = :organizationId" +
                "  and t.userGroup = :userGroup" +
                "  and t.specialPermission is null"
    ),
    @NamedQuery(
        name = UserGroupRight.NQ_FIND_BY_USER_GROUP_AND_PERMISSION_NOT_NULL,
        query = "select t from UserGroupRight t" +
                " where" +
                "  t.organizationId = :organizationId" +
                "  and t.userGroup = :userGroup" +
                "  and t.specialPermission is not null"
    )
})
public class UserGroupRight extends Right {

    private static final String CLASS_NAME = "UserGroupRight";
    public static final String NQ_FIND_BY_USER_GROUP_AND_PERMISSION_NULL =
            CLASS_NAME + ".findByUserGroupAndPermissionNull";
    public static final String NQ_FIND_BY_USER_GROUP_AND_PERMISSION_NOT_NULL =
            CLASS_NAME + ".findByUserGroupAndPermissionNotNull";

    @Basic(optional = false)
    @JoinColumn(name = "user_group_id", referencedColumnName = "user_group_id", nullable = false)
    @ManyToOne
    @Property(title="User Group", customDisplay="${userGroup.name}", hidden=true)
    protected UserGroup userGroup;

    public UserGroupRight() {
        super(USER_GROUP_TYPE_ID.charAt(0));
    }

    public UserGroupRight(Long userRightId) {
        super(USER_GROUP_TYPE_ID.charAt(0), userRightId);
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String getOwnerName() {
        if(userGroup != null) {
            return userGroup.getName();
        }

        return null;
    }
}
