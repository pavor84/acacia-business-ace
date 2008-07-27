package com.cosmos.acacia.crm.data;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Table(name = "user_groups")
@NamedQueries({
        @NamedQuery(
                 name = "UserGroup.findByParentDataObjectAndDeleted",
                 query = "select ug from UserGroup ug where ug.dataObject.parentDataObjectId = :parentDataObjectId and ug.dataObject.deleted = :deleted"
             ),
        @NamedQuery
            (
            name = "UserGroup.findByParentDataObjectIsNullAndDeleted",
            query = "select ug from UserGroup ug where ug.dataObject.parentDataObjectId is null and ug.dataObject.deleted = :deleted"
            )
})
public class UserGroup extends DataObjectBean implements Serializable {

    @Id
    @Column(name = "user_group_id", nullable = false)
    @Property(title="User Group ID", visible=false, hidden=true)
    private BigInteger userGroupId;

    @Column(name = "name", nullable = false)
    @Property(title="Group name", propertyValidator=@PropertyValidator(
        validationType=ValidationType.LENGTH, minLength=3, maxLength=50))
    private String name;

    @JoinColumn(name = "user_group_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "description")
    @Property(title="Description")
    private String description;
            
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

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return getUserGroupId();
    }

    @Override
    public String getInfo() {
        return getName();
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;

    }

    @Override
    public void setId(BigInteger id) {
        setUserGroupId(id);
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((userGroupId == null) ? 0 : userGroupId.hashCode());
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
        final UserGroup other = (UserGroup) obj;
        if (userGroupId == null) {
            if (other.userGroupId != null)
                return false;
        } else if (!userGroupId.equals(other.userGroupId))
            return false;
        return true;
    }
}
