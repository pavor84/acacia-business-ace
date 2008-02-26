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
 * @author Miro
 */
@Embeddable
public class ClassifiedObjectPK implements Serializable {

    @Column(name = "classifier_id", nullable = false)
    private BigInteger classifierId;

    @Column(name = "classified_object_id", nullable = false)
    private BigInteger classifiedObjectId;

    public ClassifiedObjectPK() {
    }

    public ClassifiedObjectPK(BigInteger classifierId, BigInteger classifiedObjectId) {
        this.classifierId = classifierId;
        this.classifiedObjectId = classifiedObjectId;
    }

    public BigInteger getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(BigInteger classifierId) {
        this.classifierId = classifierId;
    }

    public BigInteger getClassifiedObjectId() {
        return classifiedObjectId;
    }

    public void setClassifiedObjectId(BigInteger classifiedObjectId) {
        this.classifiedObjectId = classifiedObjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += classifierId.intValue();
        hash += classifiedObjectId.intValue();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassifiedObjectPK)) {
            return false;
        }
        ClassifiedObjectPK other = (ClassifiedObjectPK) object;
        if (this.classifierId != other.classifierId) {
            return false;
        }
        if (this.classifiedObjectId != other.classifiedObjectId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ClassifiedObjectPK[classifierId=" + classifierId + ", classifiedObjectId=" + classifiedObjectId + "]";
    }

}
