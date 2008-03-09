/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "classified_objects")
public class ClassifiedObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ClassifiedObjectPK classifiedObjectPK;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "classifier_id", referencedColumnName = "classifier_id", insertable = false, updatable = false)
    @ManyToOne
    private Classifier classifier;

    @JoinColumn(name = "classified_object_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @ManyToOne
    private DataObject dataObject;

    public ClassifiedObject() {
    }

    public ClassifiedObject(ClassifiedObjectPK classifiedObjectPK) {
        this.classifiedObjectPK = classifiedObjectPK;
    }

    public ClassifiedObject(BigInteger classifierId, BigInteger classifiedObjectId) {
        this.classifiedObjectPK = new ClassifiedObjectPK(classifierId, classifiedObjectId);
    }

    public ClassifiedObjectPK getClassifiedObjectPK() {
        return classifiedObjectPK;
    }

    public void setClassifiedObjectPK(ClassifiedObjectPK classifiedObjectPK) {
        this.classifiedObjectPK = classifiedObjectPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
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
        hash += (classifiedObjectPK != null ? classifiedObjectPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassifiedObject)) {
            return false;
        }
        ClassifiedObject other = (ClassifiedObject) object;
        if ((this.classifiedObjectPK == null && other.classifiedObjectPK != null) || (this.classifiedObjectPK != null && !this.classifiedObjectPK.equals(other.classifiedObjectPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ClassifiedObject[classifiedObjectPK=" + classifiedObjectPK + "]";
    }

}
