package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObject;
import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import javax.persistence.Basic;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_groups", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_id", "user_group_name"})
})
@NamedQueries({
    @NamedQuery(
        name = UserGroup.NQ_FIND_ALL,
        query = "select ug from UserGroup ug" +
                " where" +
                "  ug.organizationId = :organizationId" +
                "  and ug.dataObject.deleted = :deleted"
    )
})
public class UserGroup extends DataObjectBean implements Serializable {

    protected static final String CLASS_NAME = "UserGroup";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";

    @Id
    @Basic(optional = false)
    @Column(name = "user_group_id", nullable = false)
    @Property(title="User Group ID", visible=false, hidden=true)
    private BigInteger userGroupId;

    @Basic(optional = false)
    @Column(name = "user_group_name", nullable = false, length = 50)
    @Property(title="Group name", propertyValidator=@PropertyValidator(
        validationType=ValidationType.LENGTH, minLength=3, maxLength=50))
    private String name;

    @Column(name = "description", length = 2147483647)
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "user_group_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    @Basic(optional = false)
    @Column(name = "organization_id", nullable = false)
    @Property(title="Organization Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger organizationId;

    public UserGroup() {
    }

    public UserGroup(BigInteger userGroupId) {
        this.userGroupId = userGroupId;
    }

    public BigInteger getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(BigInteger userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
        super.setParentId(organizationId);
    }

    @Override
    public BigInteger getParentId() {
        return getOrganizationId();
    }

    @Override
    public void setParentId(BigInteger parentId) {
        setOrganizationId(parentId);
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public String getInfo() {
        return getName();
    }

    @Override
    public void setId(BigInteger id) {
        setUserGroupId(id);
    }

    @Override
    public BigInteger getId() {
        return getUserGroupId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        if(userGroupId != null) {
            return userGroupId.hashCode();
        }

        return 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((userGroupId == null && other.userGroupId != null)
                || (userGroupId != null && !userGroupId.equals(other.userGroupId))) {
            return false;
        }
        return true;
    }
}
