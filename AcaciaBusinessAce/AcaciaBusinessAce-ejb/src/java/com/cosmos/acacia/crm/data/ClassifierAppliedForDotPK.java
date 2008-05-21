/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Embeddable
public class ClassifierAppliedForDotPK implements Serializable {

    @Column(name = "classifier_id", nullable = false)
    private BigInteger classifierId;

    @Column(name = "data_object_type_id", nullable = false)
    private Integer dataObjectTypeId;

    public ClassifierAppliedForDotPK() {
    }

    public ClassifierAppliedForDotPK(BigInteger classifierId, Integer dataObjectTypeId) {
        this.classifierId = classifierId;
        this.dataObjectTypeId = dataObjectTypeId;
    }

    public BigInteger getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(BigInteger classifierId) {
        this.classifierId = classifierId;
    }

    public Integer getDataObjectTypeIdId() {
        return dataObjectTypeId;
    }

    public void setDataObjectTypeIdId(Integer dataObjectTypeId) {
        this.dataObjectTypeId = dataObjectTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += classifierId.intValue();
        hash += dataObjectTypeId.intValue();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassifierAppliedForDotPK)) {
            return false;
        }
        ClassifierAppliedForDotPK other = (ClassifierAppliedForDotPK) object;
        if (this.classifierId != other.classifierId) {
            return false;
        }
        if (this.dataObjectTypeId != other.dataObjectTypeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ClassifiedObjectPK[classifierId=" + classifierId + ", dataObjectTypeId=" + dataObjectTypeId + "]";
    }

}
