package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.cosmos.acacia.annotation.Property;


@Entity
@Table(name = "user_rights")
@NamedQueries({
        @NamedQuery(
                name = "UserRight.findByUser",
                query = "select ur from UserRight ur where ur.user=:user and ur.specialPermission is null order by dataObjectType,excluded"
        ),
        @NamedQuery(
                name = "UserRight.findByUserGroup",
                query = "select ur from UserRight ur where ur.userGroup=:userGroup and ur.specialPermission is null order by dataObjectType,excluded"
        ),
        @NamedQuery(
                name = "UserRight.findSpecialByUser",
                query = "select ur from UserRight ur where ur.user=:user and ur.specialPermission is not null order by dataObjectType,excluded"
        ),
        @NamedQuery(
                name = "UserRight.findSpecialByUserGroup",
                query = "select ur from UserRight ur where ur.userGroup=:userGroup and ur.specialPermission is not null order by dataObjectType,excluded"
        )
})
public class UserRight implements Serializable {

    @Id
    @SequenceGenerator(name="UserRightsSequenceGenerator", sequenceName="user_rights_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UserRightsSequenceGenerator")
    @Column(name="user_right_id")
    protected Long userRightId;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id")
    @Property(title="User", customDisplay="${user.userName}")
    private User user;

    @ManyToOne
    @JoinColumn(name="user_group_id", referencedColumnName="user_group_id")
    @Property(title="User Group", customDisplay="${userGroup.name}")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name="data_object_type_id", referencedColumnName="data_object_type_id")
    @Property(title="Object Type", customDisplay="${dataObjectType.dataObjectType}")
    private DataObjectType dataObjectType;

    @Transient
    @Property(title="Object")
    private String objectInfo;

    @ManyToOne
    @JoinColumn(name="data_object_id", referencedColumnName="data_object_id")
    @Property(title="Data Object", hidden=true)
    private DataObject dataObject;

    @Column(name="can_read")
    @Property(title="Read")
    private boolean read;

    @Column(name="can_create")
    @Property(title="Create")
    private boolean create;

    @Column(name="can_modify")
    @Property(title="Modify")
    private boolean modify;

    @Column(name="can_delete")
    @Property(title="Delete")
    private boolean delete;

    @ManyToOne
    @JoinColumn(name = "special_permission_id", referencedColumnName = "resource_id")
    @Property(title="Special permission")
    private DbResource specialPermission;

    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="expires")
    @Property(title="Expires")
    private Date expires;

    @Column(name="excluded")
    @Property(title="Excluded")
    private boolean excluded;

    public Long getUserRightId() {
        return userRightId;
    }

    public void setUserRightId(Long userRightId) {
        this.userRightId = userRightId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DataObjectType getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(DataObjectType dataObjectType) {
        this.dataObjectType = dataObjectType;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public boolean canRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean canCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean canModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean canDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((dataObject == null) ? 0 : dataObject.hashCode());
        result = prime * result
                + ((dataObjectType == null) ? 0 : dataObjectType.hashCode());
        result = prime * result + (excluded ? 1231 : 1237);
        result = prime
                * result
                + ((specialPermission == null) ? 0 : specialPermission
                        .hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result
                + ((userGroup == null) ? 0 : userGroup.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserRight other = (UserRight) obj;
        if (dataObject == null) {
            if (other.dataObject != null)
                return false;
        } else if (!dataObject.equals(other.dataObject))
            return false;
        if (dataObjectType == null) {
            if (other.dataObjectType != null)
                return false;
        } else if (!dataObjectType.equals(other.dataObjectType))
            return false;
        if (excluded != other.excluded)
            return false;
        if (specialPermission == null) {
            if (other.specialPermission != null)
                return false;
        } else if (!specialPermission.equals(other.specialPermission))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (userGroup == null) {
            if (other.userGroup != null)
                return false;
        } else if (!userGroup.equals(other.userGroup))
            return false;
        return true;
    }

    // Additional getters for BeansBinding to work
    public boolean isCreate() {
        return create;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isModify() {
        return modify;
    }

    public boolean isRead() {
        return read;
    }

    public String getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(String objectInfo) {
        this.objectInfo = objectInfo;
    }

    public DbResource getSpecialPermission() {
        return specialPermission;
    }

    public void setSpecialPermission(DbResource specialPermission) {
        this.specialPermission = specialPermission;
    }

    @Override
    public String toString() {
        return super.toString() + ";" +
            "User: " + user + "; " +
            "UserGroup: " + userGroup + "; " +
            "DataObject: " + dataObject + "; " +
            "DataObjectType: " + dataObjectType + "; ";
    }
}
