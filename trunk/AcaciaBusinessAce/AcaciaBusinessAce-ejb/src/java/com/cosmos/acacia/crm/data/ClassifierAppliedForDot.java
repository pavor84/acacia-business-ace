/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Entity
@Table(name = "classifier_applied_for_dot")
@NamedQueries(
        {
            @NamedQuery(
                    name = "ClassifierAppliedForDot.clearTypesForClassifier",
                    query = "delete from ClassifierAppliedForDot cafd where cafd.classifier=:classifier"
                ),
            @NamedQuery(
                    name = "ClassifierAppliedForDot.findByDataObjectType",
                    query = "select cafd from ClassifierAppliedForDot cafd where cafd.dataObjectType=:dataObjectType"
            ),
            @NamedQuery(
                    name = "ClassifierAppliedForDot.findByClassifier",
                    query = "select cafd from ClassifierAppliedForDot cafd where cafd.classifier=:classifier"
            ),
            @NamedQuery(
                    name = "ClassifierAppliedForDot.findByDataObjectTypeAndParent",
                    query = "select cafd from ClassifierAppliedForDot cafd where cafd.dataObjectType=:dataObjectType and cafd.classifier.dataObject.parentDataObjectId = :parentId"
            ),
            @NamedQuery(
                    name = "ClassifierAppliedForDot.findAll",
                    query = "select cafd from ClassifierAppliedForDot cafd"
            )
        }
)
public class ClassifierAppliedForDot implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ClassifierAppliedForDotPK classifierAppliedForDotPK;

    @JoinColumn(name = "classifier_id", referencedColumnName = "classifier_id", insertable=false, updatable=false)
    @ManyToOne
    protected Classifier classifier;
    
    @JoinColumn(name = "data_object_type_id", referencedColumnName = "data_object_type_id", insertable=false, updatable=false)
    @ManyToOne
    protected DataObjectType dataObjectType;

    public ClassifierAppliedForDotPK getClassifierAppliedForDotPK() {
        return classifierAppliedForDotPK;
    }

    public void setClassifierAppliedForDotPK(
            ClassifierAppliedForDotPK classifierAppliedForDotPK) {
        this.classifierAppliedForDotPK = classifierAppliedForDotPK;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public DataObjectType getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(DataObjectType dataObjectType) {
        this.dataObjectType = dataObjectType;
    }

    public ClassifierAppliedForDot() {
    }

    public ClassifierAppliedForDot(ClassifierAppliedForDotPK classifierAppliedForDotPK) {
        this.classifierAppliedForDotPK = classifierAppliedForDotPK;
    }

    public ClassifierAppliedForDot(BigInteger classifierId, Integer dataObjectTypeId) {
        this.classifierAppliedForDotPK =
            new ClassifierAppliedForDotPK(classifierId, dataObjectTypeId);
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classifierAppliedForDotPK != null ? classifierAppliedForDotPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassifierAppliedForDot)) {
            return false;
        }
        ClassifierAppliedForDot other = (ClassifierAppliedForDot) object;
        if ((this.classifierAppliedForDotPK == null && other.classifierAppliedForDotPK != null) || (this.classifierAppliedForDotPK != null && !this.classifierAppliedForDotPK.equals(other.classifierAppliedForDotPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ClassifierAppliedForDot[classifierAppliedForDotPK=" + classifierAppliedForDotPK + "]";
    }

}
