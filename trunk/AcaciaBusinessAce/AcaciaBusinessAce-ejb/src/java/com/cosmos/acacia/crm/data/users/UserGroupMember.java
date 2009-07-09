/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObject;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "user_group_members", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_group_id", "user_id"})}
)
@NamedQueries({
    @NamedQuery(
        name = UserGroupMember.NQ_FIND_ALL,
        query = "SELECT u FROM UserGroupMember u" +
                " WHERE" +
                "  u.userGroup.organizationId = :organizationId"
    ),
    @NamedQuery(
        name = UserGroupMember.NQ_FIND_BY_USER,
        query = "SELECT u FROM UserGroupMember u" +
                " WHERE" +
                "  u.userGroup.organizationId = :organizationId" +
                "  and u.user = :user"
    ),
    @NamedQuery(
        name = UserGroupMember.NQ_FIND_BY_USER_GROUP,
        query = "SELECT u FROM UserGroupMember u" +
                " WHERE" +
                "  u.userGroup = :userGroup"
    ),
    @NamedQuery(
        name = UserGroupMember.NQ_IS_USER_MEMBER_OF_USER_GROUP,
        query = "SELECT u.userGroupMemberId FROM UserGroupMember u" +
                " WHERE" +
                "  u.userGroup = :userGroup" +
                "  and u.user = :user"
    ),
    @NamedQuery(
        name = UserGroupMember.NQ_FIND_BY_USER_AND_USER_GROUP,
        query = "SELECT u FROM UserGroupMember u" +
                " WHERE" +
                "  u.userGroup = :userGroup" +
                "  and u.user = :user"
    )
})
public class UserGroupMember extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "UserGroupMember";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_USER = CLASS_NAME + ".findByUser";
    public static final String NQ_FIND_BY_USER_GROUP = CLASS_NAME + ".findByUserGroup";
    public static final String NQ_IS_USER_MEMBER_OF_USER_GROUP = CLASS_NAME + ".isUserMemberOfUserGroup";
    public static final String NQ_FIND_BY_USER_AND_USER_GROUP = CLASS_NAME + ".findByUserAndUserGroup";

    @Id
    @Basic(optional = false)
    @Column(name = "user_group_member_id", nullable = false, precision = 19, scale = 0)
    private BigInteger userGroupMemberId;

    @JoinColumn(name = "user_group_id", referencedColumnName = "user_group_id", nullable = false)
    @ManyToOne(optional = false)
    private UserGroup userGroup;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "user_group_member_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public UserGroupMember() {
    }

    public UserGroupMember(BigInteger userGroupMemberId) {
        this.userGroupMemberId = userGroupMemberId;
    }

    public BigInteger getUserGroupMemberId() {
        return userGroupMemberId;
    }

    public void setUserGroupMemberId(BigInteger userGroupMemberId) {
        this.userGroupMemberId = userGroupMemberId;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        if(userGroupMemberId != null) {
            return userGroupMemberId.hashCode();
        }

        return 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroupMember)) {
            return false;
        }
        UserGroupMember other = (UserGroupMember) object;
        if ((userGroupMemberId == null && other.userGroupMemberId != null) || (userGroupMemberId != null && !userGroupMemberId.equals(other.userGroupMemberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserGroupMember[userGroupMemberId=" + userGroupMemberId + "]";
    }

    @Override
    public BigInteger getId() {
        return getUserGroupMemberId();
    }

    @Override
    public void setId(BigInteger id) {
        setUserGroupMemberId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(dataObject != null) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(userGroup != null) {
            sb.append(userGroup.getName());
        } else {
            sb.append("null");
        }
        sb.append(":");
        if(user != null) {
            sb.append(user.getUserName());
        } else {
            sb.append("null");
        }
        sb.append("]");

        return sb.toString();
    }
}
