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
@Table(name = "classifiers")
@NamedQueries(
    {
        @NamedQuery
             (
                name = "Classifier.findByParentDataObjectAndDeleted",
                query = "select c from Classifier c where c.parentId = :groupId" +
                        " and c.dataObject.deleted = :deleted" +
                        " and (c.classifierGroup.dataObject.parentDataObjectId = :parentId" +
                        " or c.classifierGroup.isSystemGroup=true or c.classifierGroup.classifierGroupCode='system')"
             ),
        @NamedQuery
             (
                name = "Classifier.findAllAndDeleted",
                query = "select c from Classifier c where" +
                        " c.dataObject.deleted = :deleted" +
                        " and (c.classifierGroup.dataObject.parentDataObjectId = :parentId" +
                        " or c.classifierGroup.isSystemGroup=true or c.classifierGroup.classifierGroupCode='system')"
              ),
        @NamedQuery
            (
                name = "Classifier.findByCode",
                query = "select c from Classifier c where c.classifierCode = :code" +
                        " and c.dataObject.deleted = :deleted" +
                        " and (c.classifierGroup.dataObject.parentDataObjectId = :parentId" +
                        " or c.classifierGroup.isSystemGroup=true or c.classifierGroup.classifierGroupCode='system')"
            )
    }
)
public class Classifier extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "classifier_id", nullable = false)
    @Property(title="Address Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger classifierId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "classifier_code", nullable = false)
    @Property(title="Code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=32))
    private String classifierCode;

    @Column(name = "classifier_name", nullable = false)
    @Property(title="Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=128))
    private String classifierName;

    @JoinColumn(name = "parent_id", referencedColumnName = "classifier_group_id", insertable=false, updatable=false, nullable=false)
    @ManyToOne
    @Property(title="Group", customDisplay="${classifierGroup.classifierGroupName}")
    private ClassifierGroup classifierGroup;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

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

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
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

    public ClassifierGroup getClassifierGroup() {
        return classifierGroup;
    }

    public void setClassifierGroup(ClassifierGroup classifierGroup) {
        this.classifierGroup = classifierGroup;
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

    @Override
    public BigInteger getId() {
        return classifierId;
    }

    @Override
    public void setId(BigInteger id) {
        this.classifierId = id;
    }

    @Override
    public String getInfo() {
        return getClassifierName();
    }

    @Override
    public String toShortText() {
        return getClassifierName();
    }

    @Override
    public String toText() {
        return null;
    }
}
