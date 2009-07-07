/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

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
@DiscriminatorValue(value = Right.USER_TYPE_ID)
@NamedQueries({
    @NamedQuery(
        name = UserRight.NQ_FIND_BY_USER_AND_PERMISSION_NULL,
        query = "select t from UserRight t" +
                " where" +
                "  t.organizationId = :organizationId" +
                "  and t.user = :user" +
                "  and t.specialPermission is null"
    ),
    @NamedQuery(
        name = UserRight.NQ_FIND_BY_USER_AND_PERMISSION_NOT_NULL,
        query = "select t from UserRight t" +
                " where" +
                "  t.organizationId = :organizationId" +
                "  and t.user = :user" +
                "  and t.specialPermission is not null"
    )
})
public class UserRight extends Right {

    private static final String CLASS_NAME = "UserRight";
    public static final String NQ_FIND_BY_USER_AND_PERMISSION_NULL =
            CLASS_NAME + ".findByUserAndPermissionNull";
    public static final String NQ_FIND_BY_USER_AND_PERMISSION_NOT_NULL =
            CLASS_NAME + ".findByUserAndPermissionNotNull";

    @Basic(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne
    @Property(title="User", customDisplay="${user.userName}", hidden=true)
    protected User user;

    public UserRight() {
        super(USER_TYPE_ID.charAt(0));
    }

    public UserRight(Long userRightId) {
        super(USER_TYPE_ID.charAt(0), userRightId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getOwnerName() {
        if(user != null) {
            return user.getUserName();
        }

        return null;
    }
}