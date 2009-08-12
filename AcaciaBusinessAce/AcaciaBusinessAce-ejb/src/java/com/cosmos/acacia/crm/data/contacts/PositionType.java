package com.cosmos.acacia.crm.data.contacts;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.crm.data.users.*;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "position_types")
@NamedQueries(
    {
        @NamedQuery
             (
                 name = "PositionType.findPersonPositionTypes",
                 query = "select pt from PositionType pt where pt.ownerType='P' and pt.dataObject.parentDataObjectId=:parentDataObjectId and pt.dataObject.deleted = :deleted and pt.isInternal=false"
             ),
                @NamedQuery
             (
                 name = "PositionType.findOrganizationPositionTypes",
                 query = "select pt from PositionType pt where pt.ownerType='O' and pt.dataObject.parentDataObjectId=:parentDataObjectId and pt.dataObject.deleted = :deleted and pt.isInternal=false"
             ),
             @NamedQuery
             (
                name = "PositionType.findByName",
                query = "select pt from PositionType pt where pt.positionTypeName=:positionTypeName and pt.isInternal=false"
             ),
             @NamedQuery
             (
                 name = "PositionType.findInternalOrganizationPositionTypes",
                 query = "select pt from PositionType pt where pt.ownerType='O' and pt.dataObject.parentDataObjectId=:parentDataObjectId and pt.dataObject.deleted = :deleted and pt.isInternal=true"
             )
        }
)
public class PositionType extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "position_type_id", nullable = false)
    @Property(title="Position Type Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger positionTypeId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "position_type_name", nullable = false)
    @Property(title="Position Type Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=32))
    private String positionTypeName;

    @Column(name = "owner_type", nullable = false)
    private char ownerType;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "user_group_id", referencedColumnName = "user_group_id")
    @OneToOne
    @Property(title="User Group", customDisplay="${userGroup.name}")
    private UserGroup userGroup;

    @JoinColumn(name = "position_type_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    @ManyToOne
    @JoinColumn(name="parent_position_type_id")
    @Property(title="Higher Position Type", customDisplay="${parentPositionType.positionTypeName}")
    private PositionType parentPositionType;

    @Column(name="is_internal")
    private boolean isInternal;

    public PositionType() {
    }

    public PositionType(BigInteger positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public BigInteger getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(BigInteger positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public char getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(char ownerType) {
        this.ownerType = ownerType;
    }

    public String getPositionTypeName() {
        return positionTypeName;
    }

    public void setPositionTypeName(String positionTypeName) {
        this.positionTypeName = positionTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public int hashCode() {
        int hash = 0;
        hash += (positionTypeId != null ? positionTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PositionType)) {
            return false;
        }
        PositionType other = (PositionType) object;
        if ((this.positionTypeId == null && other.positionTypeId != null) || (this.positionTypeId != null && !this.positionTypeId.equals(other.positionTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.PositionType[positionTypeId=" + positionTypeId + "]";
    }

    @Override
    public BigInteger getId() {
        return getPositionTypeId();
    }

    @Override
    public void setId(BigInteger id) {
        positionTypeId = id;
    }

    public String toShortText() {
        return null;
    }

    public String toText() {
        return getPositionTypeName();
    }

    @Override
    public String getInfo() {
        return getPositionTypeName();
    }

    public PositionType getParentPositionType() {
        return parentPositionType;
    }

    public void setParentPositionType(PositionType parentPositionType) {
        this.parentPositionType = parentPositionType;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
