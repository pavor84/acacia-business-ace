/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "classifiers")
@NamedQueries({@NamedQuery(name = "Classifier.findByClassifierId", query = "SELECT c FROM Classifier c WHERE c.classifierId = :classifierId"), @NamedQuery(name = "Classifier.findByParentId", query = "SELECT c FROM Classifier c WHERE c.parentId = :parentId"), @NamedQuery(name = "Classifier.findByClassifierCode", query = "SELECT c FROM Classifier c WHERE c.classifierCode = :classifierCode"), @NamedQuery(name = "Classifier.findByClassifierName", query = "SELECT c FROM Classifier c WHERE c.classifierName = :classifierName"), @NamedQuery(name = "Classifier.findByDescription", query = "SELECT c FROM Classifier c WHERE c.description = :description")})
public class Classifier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "classifier_id", nullable = false)
    private BigInteger classifierId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @Column(name = "classifier_code", nullable = false)
    private String classifierCode;

    @Column(name = "classifier_name", nullable = false)
    private String classifierName;

    @Column(name = "description")
    private String description;

    @JoinTable(name = "classifier_applied_for_dot", joinColumns = {@JoinColumn(name = "classifier_id", referencedColumnName = "classifier_id")}, inverseJoinColumns = {@JoinColumn(name = "data_object_type_id", referencedColumnName = "data_object_type_id")})
    @ManyToMany
    private Collection<DataObjectType> appliedForDataObjectTypes;

    @JoinColumn(name = "classifier_group_id", referencedColumnName = "classifier_group_id")
    @ManyToOne
    private ClassifierGroup classifierGroup;

    @JoinColumn(name = "classifier_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public Classifier() {
    }

    public Classifier(BigInteger classifierId) {
        this.classifierId = classifierId;
    }

    public Classifier(BigInteger classifierId, String classifierCode, String classifierName) {
        this.classifierId = classifierId;
        this.classifierCode = classifierCode;
        this.classifierName = classifierName;
    }

    public BigInteger getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(BigInteger classifierId) {
        this.classifierId = classifierId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getClassifierCode() {
        return classifierCode;
    }

    public void setClassifierCode(String classifierCode) {
        this.classifierCode = classifierCode;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public void setClassifierName(String classifierName) {
        this.classifierName = classifierName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<DataObjectType> getAppliedForDataObjectTypes() {
        return appliedForDataObjectTypes;
    }

    public void setAppliedForDataObjectTypes(Collection<DataObjectType> appliedForDataObjectTypes) {
        this.appliedForDataObjectTypes = appliedForDataObjectTypes;
    }

    public ClassifierGroup getClassifierGroup() {
        return classifierGroup;
    }

    public void setClassifierGroup(ClassifierGroup classifierGroup) {
        this.classifierGroup = classifierGroup;
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
        hash += (classifierId != null ? classifierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classifier)) {
            return false;
        }
        Classifier other = (Classifier) object;
        if ((this.classifierId == null && other.classifierId != null) || (this.classifierId != null && !this.classifierId.equals(other.classifierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Classifier[classifierId=" + classifierId + "]";
    }

}
