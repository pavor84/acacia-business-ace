/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;
import java.io.Serializable;
import java.math.BigInteger;

import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "classifier_groups")
@NamedQueries(
    {
        /**
         * All not deleted classifier groups.
         */
        @NamedQuery
            (
            name = "ClassifierGroup.getAllNotDeleted",
            query = "select cg from ClassifierGroup cg " +
                    " where cg.dataObject.deleted = false" +
                    "  and cg.dataObject.parentDataObjectId=:parentId"
            ),
        @NamedQuery
            (
            name = "ClassifierGroup.getByClassifierGroupCode",
            query = "select cg from ClassifierGroup cg " +
                    " where cg.dataObject.deleted = false" +
                    "  and cg.dataObject.parentDataObjectId=:parentId" +
                    "  and cg.classifierGroupCode = :classifierGroupCode"
            )
    }
)
public class ClassifierGroup extends DataObjectBean
        implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    public static final ClassifierGroup System =
            new ClassifierGroup();

    public static final Map<String, ClassifierGroup> ConstantsMap =
            new TreeMap<String, ClassifierGroup>();

    static {
        System.setClassifierGroupCode("System");
        System.setClassifierGroupName("System Group");
        System.setDescription("The System Classifier Group");
        System.setIsSystemGroup(true);

        setClassifierGroup(System);
    }

    private static final void setClassifierGroup(ClassifierGroup classifierGroup) {
        ConstantsMap.put(classifierGroup.getClassifierGroupCode(), classifierGroup);
    }



    @Id
    @Column(name = "classifier_group_id", nullable = false)
    @Property(title="Classifier Group Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger classifierGroupId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "classifier_group_code", nullable = false)
    @Property(title="Group Code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=32))
    private String classifierGroupCode;

    @Column(name = "classifier_group_name", nullable = false)
    @Property(title="Group Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=100))
    private String classifierGroupName;

    @Column(name = "is_system_group", nullable = false)
    @Property(title="System")
    private boolean isSystemGroup;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "classifier_group_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public ClassifierGroup() {
    }

    public ClassifierGroup(BigInteger classifierGroupId) {
        this.classifierGroupId = classifierGroupId;
    }

    public ClassifierGroup(BigInteger classifierGroupId, boolean isSystemGroup, String classifierGroupCode, String classifierGroupName) {
        this.classifierGroupId = classifierGroupId;
        this.isSystemGroup = isSystemGroup;
        this.classifierGroupCode = classifierGroupCode;
        this.classifierGroupName = classifierGroupName;
    }

    public BigInteger getClassifierGroupId() {
        return classifierGroupId;
    }

    public void setClassifierGroupId(BigInteger classifierGroupId) {
        this.classifierGroupId = classifierGroupId;
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public boolean getIsSystemGroup() {
        return isSystemGroup;
    }

    public void setIsSystemGroup(boolean isSystemGroup) {
        this.isSystemGroup = isSystemGroup;
    }

    public String getClassifierGroupCode() {
        return classifierGroupCode;
    }

    public void setClassifierGroupCode(String classifierGroupCode) {
        this.classifierGroupCode = classifierGroupCode;
    }

    public String getClassifierGroupName() {
        return classifierGroupName;
    }

    public void setClassifierGroupName(String classifierGroupName) {
        this.classifierGroupName = classifierGroupName;
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
        hash += (classifierGroupId != null ? classifierGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassifierGroup)) {
            return false;
        }
        ClassifierGroup other = (ClassifierGroup) object;
        if ((this.classifierGroupId == null && other.classifierGroupId != null) || (this.classifierGroupId != null && !this.classifierGroupId.equals(other.classifierGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ClassifierGroup[classifierGroupId=" + classifierGroupId +
                ", code=" + classifierGroupCode + "]";
    }

    @Override
    public BigInteger getId() {
        return classifierGroupId;
    }

    @Override
    public void setId(BigInteger id) {
        this.classifierGroupId = id;
    }

    @Override
    public String toShortText() {
        return null;
    }

    @Override
    public String toText() {
        return classifierGroupName + " (" +  classifierGroupCode + ")";
    }

    @Override
    public String getInfo() {
        return getClassifierGroupName();
    }
}
