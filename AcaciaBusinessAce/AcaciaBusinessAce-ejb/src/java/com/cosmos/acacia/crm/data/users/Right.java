package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.annotation.Property;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.cosmos.acacia.security.AccessRight;
import com.cosmos.util.CloneableBean;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;


@Entity
@Table(name = "user_rights")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="owner_type_id", discriminatorType=DiscriminatorType.STRING,length=1)
@NamedQueries({
    @NamedQuery(
        name = Right.NQ_FIND_ALL,
        query = "select t from Right t" +
                " where" +
                "  t.organizationId = :organizationId"
    )
})
public abstract class Right implements Serializable, Comparable<Right>, Comparator<Right>, CloneableBean<Right> {

    protected static final String USER_TYPE_ID = "U";
    protected static final String USER_GROUP_TYPE_ID = "G";
    //
    private static final String CLASS_NAME = "Right";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name="UserRightsSequenceGenerator", sequenceName="user_rights_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UserRightsSequenceGenerator")
    @Column(name = "user_right_id", nullable = false)
    protected Long userRightId;

    @Basic(optional = false)
    @Column(name = "access_rights", nullable = false)
    private int accessRightsValue;

    @Basic(optional = false)
    @Column(name = "excluded", nullable = false)
    @Property(title="Excluded")
    private boolean excluded;

    @Basic(optional = false)
    @Column(name = "organization_id", nullable = false)
    private BigInteger organizationId;

    @Basic(optional = false)
    @Column(name = "owner_type_id", nullable = false, insertable=false, updatable=false)
    @Property(title="Owner Type")
    protected char ownerTypeId;

    @Transient
    @Property(title="Owner Name")
    private String ownerName;

    @Basic(optional = false)
    @Column(name = "user_id", nullable = false, insertable=false, updatable=false)
    private BigInteger userId;

    @Basic(optional = false)
    @Column(name = "user_group_id", nullable = false, insertable=false, updatable=false)
    private BigInteger userGroupId;

    @ManyToOne
    @JoinColumn(name="data_object_type_id", referencedColumnName="data_object_type_id")
    @Property(title="Object Type", customDisplay="${dataObjectType.dataObjectType}")
    private DataObjectType dataObjectType;

    @ManyToOne
    @JoinColumn(name="data_object_id", referencedColumnName="data_object_id")
    @Property(title="Data Object", hidden=true)
    private DataObject dataObject;

    @Transient
    @Property(title="Object")
    private String objectInfo;

    @Transient
    @Property(title="Read")
    private boolean read;

    @Transient
    @Property(title="Create")
    private boolean create;

    @Transient
    @Property(title="Modify")
    private boolean modify;

    @Transient
    @Property(title="Delete")
    private boolean delete;

    @Transient
    @Property(title="Execute")
    private boolean execute;

    @Transient
    @Property(title="Access Rights")
    private String accessRightsInitials;

    @JoinColumn(name = "permission_category_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Permission Category")
    private DbResource permissionCategory;

    @ManyToOne
    @JoinColumn(name = "special_permission_id", referencedColumnName = "resource_id")
    @Property(title="Permission")
    private DbResource specialPermission;

    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="expires")
    @Property(title="Expires")
    private Date expires;

    public Right() {
        throw new UnsupportedOperationException("This constructor is not for use.");
    }

    protected Right(char ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }

    protected Right(char ownerTypeId, Long userRightId) {
        this(ownerTypeId);
        this.userRightId = userRightId;
    }

    public Long getUserRightId() {
        return userRightId;
    }

    public void setUserRightId(Long userRightId) {
        this.userRightId = userRightId;
    }

    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    public int getAccessRightsValue() {
        return accessRightsValue;
    }

    public void setAccessRightsValue(int accessRightsValue) {
        this.accessRightsValue = accessRightsValue;
    }

    public Set<AccessRight> getAccessRights() {
        return AccessRight.getAccessRights(accessRightsValue);
    }

    public void setAccessRights(Set<AccessRight> accessRights) {
        accessRightsValue = AccessRight.getAccessRight(accessRights);
    }

    public String getAccessRightsInitials() {
        return AccessRight.getAccessRightsInitials(accessRightsValue);
    }

    public boolean isAccessRight(AccessRight accessRight) {
        return AccessRight.isAccessRight(accessRightsValue, accessRight);
    }

    public void setAccessRight(AccessRight accessRight, boolean right) {
        accessRightsValue = AccessRight.setAccessRight(accessRightsValue, accessRight, right);
    }

    public char getOwnerTypeId() {
        return ownerTypeId;
    }

    public void setOwnerTypeId(char ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }

    public abstract String getOwnerName();

    public BigInteger getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(BigInteger userGroupId) {
        this.userGroupId = userGroupId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
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

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

    @Override
    public int hashCode() {
        if(userRightId != null) {
            return userRightId.hashCode();
        }

        return 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Right)) {
            return false;
        }
        Right other = (Right) object;
        if ((userRightId == null && other.userRightId != null) || (userRightId != null && !userRightId.equals(other.userRightId))) {
            return false;
        }
        return true;
    }

    public boolean isCreate() {
        return isAccessRight(AccessRight.Create);
    }

    public void setCreate(boolean right) {
        setAccessRight(AccessRight.Create, right);
    }

    public boolean isDelete() {
        return isAccessRight(AccessRight.Delete);
    }

    public void setDelete(boolean right) {
        setAccessRight(AccessRight.Delete, right);
    }

    public boolean isModify() {
        return isAccessRight(AccessRight.Modify);
    }

    public void setModify(boolean right) {
        setAccessRight(AccessRight.Modify, right);
    }

    public boolean isRead() {
        return isAccessRight(AccessRight.Read);
    }

    public void setRead(boolean right) {
        setAccessRight(AccessRight.Read, right);
    }

    public boolean isExecute() {
        return isAccessRight(AccessRight.Execute);
    }

    public void setExecute(boolean right) {
        setAccessRight(AccessRight.Execute, right);
    }

    public String getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(String objectInfo) {
        this.objectInfo = objectInfo;
    }

    public DbResource getPermissionCategory() {
        return permissionCategory;
    }

    public void setPermissionCategory(DbResource permissionCategory) {
        this.permissionCategory = permissionCategory;
    }

    public DbResource getSpecialPermission() {
        return specialPermission;
    }

    public void setSpecialPermission(DbResource specialPermission) {
        this.specialPermission = specialPermission;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("[userRightId=").append(userRightId);
        sb.append(", rights=").append(getAccessRights());
        sb.append(", excluded=").append(excluded);
        sb.append(", organizationId=").append(organizationId);
        sb.append(", ownerTypeId=").append(ownerTypeId);
        if(ownerTypeId == USER_TYPE_ID.charAt(0)) {
            sb.append(", user=").append(((UserRight)this).user);
        } else {
            sb.append(", userGroup=").append(((UserGroupRight)this).userGroup);
        }

        if(dataObjectType != null) {
            sb.append(", dataObjectType=").append(dataObjectType);
        }
        if(dataObject != null) {
            sb.append(", dataObject=").append(dataObject);
        }
        if(permissionCategory != null) {
            sb.append(", permissionCategory=").append(permissionCategory);
        }
        if(specialPermission != null) {
            sb.append(", specialPermission=").append(specialPermission);
        }
        if(expires != null) {
            sb.append(", expires=").append(expires);
        }

        sb.append("]");

        return sb.toString();
    }

    /**
     * Used for ranking rights by concreteness.
     * If a right is more concrete than the other, it should go
     * before it in a list/queue. Hence the -1 if 'this' is 'bigger'
     */
    @Override
    public int compareTo(Right other) {
        return compare(this, other);
    }

    /**
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
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Right o1, Right o2) {
        int result;
        if((result = o1.organizationId.compareTo(o2.organizationId)) != 0) {
            return result;
        }
        if((result = o1.ownerTypeId - o2.ownerTypeId) != 0) {
            return result;
        }
        if(o1.ownerTypeId == USER_TYPE_ID.charAt(0)) {
            if((result = ((UserRight)o1).user.getId().compareTo(((UserRight)o2).user.getId())) != 0) {
                return result;
            }
        } else {
            if((result = ((UserGroupRight)o1).userGroup.getId().compareTo(((UserGroupRight)o2).userGroup.getId())) != 0) {
                return result;
            }
        }
        if((result = o1.accessRightsValue - o2.accessRightsValue) != 0) {
            return result;
        }
        if(o1.excluded != o2.excluded) {
            if(o1.excluded) {
                return 1;
            } else {
                return -1;
            }
        }

        if((result = o1.dataObjectType.getDataObjectTypeId().compareTo(o2.dataObjectType.getDataObjectTypeId())) != 0) {
            return result;
        }
        if((result = o1.dataObject.getDataObjectId().compareTo(o2.dataObject.getDataObjectId())) != 0) {
            return result;
        }
        if((result = o1.permissionCategory.getResourceId().compareTo(o2.permissionCategory.getResourceId())) != 0) {
            return result;
        }
        return o1.specialPermission.getResourceId().compareTo(o2.specialPermission.getResourceId());
    }

    @Override
    public Right clone() {
        try {
            return (Right) super.clone();
        } catch(CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}