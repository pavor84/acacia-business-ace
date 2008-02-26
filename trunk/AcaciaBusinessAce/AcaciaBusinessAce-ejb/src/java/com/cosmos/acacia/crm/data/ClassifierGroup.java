/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "classifier_groups")
@NamedQueries({@NamedQuery(name = "ClassifierGroup.findByClassifierGroupId", query = "SELECT c FROM ClassifierGroup c WHERE c.classifierGroupId = :classifierGroupId"), @NamedQuery(name = "ClassifierGroup.findByParentId", query = "SELECT c FROM ClassifierGroup c WHERE c.parentId = :parentId"), @NamedQuery(name = "ClassifierGroup.findByIsSystemGroup", query = "SELECT c FROM ClassifierGroup c WHERE c.isSystemGroup = :isSystemGroup"), @NamedQuery(name = "ClassifierGroup.findByClassifierGroupCode", query = "SELECT c FROM ClassifierGroup c WHERE c.classifierGroupCode = :classifierGroupCode"), @NamedQuery(name = "ClassifierGroup.findByClassifierGroupName", query = "SELECT c FROM ClassifierGroup c WHERE c.classifierGroupName = :classifierGroupName"), @NamedQuery(name = "ClassifierGroup.findByDescription", query = "SELECT c FROM ClassifierGroup c WHERE c.description = :description")})
public class ClassifierGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "classifier_group_id", nullable = false)
    private BigInteger classifierGroupId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @Column(name = "is_system_group", nullable = false)
    private boolean isSystemGroup;

    @Column(name = "classifier_group_code", nullable = false)
    private String classifierGroupCode;

    @Column(name = "classifier_group_name", nullable = false)
    private String classifierGroupName;

    @Column(name = "description")
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

    public BigInteger getParentId() {
        return parentId;
    }

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

    public DataObject getDataObject() {
        return dataObject;
    }

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
        return "com.cosmos.acacia.crm.data.ClassifierGroup[classifierGroupId=" + classifierGroupId + "]";
    }

}
