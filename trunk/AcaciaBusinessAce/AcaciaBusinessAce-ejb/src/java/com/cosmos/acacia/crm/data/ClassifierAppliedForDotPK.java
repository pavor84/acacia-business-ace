/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Embeddable
public class ClassifierAppliedForDotPK implements Serializable {

    @Column(name = "classifier_id", nullable = false)
    @Type(type="uuid")
    private UUID classifierId;

    @Column(name = "data_object_type_id", nullable = false)
    private Integer dataObjectTypeId;

    public ClassifierAppliedForDotPK() {
    }

    public ClassifierAppliedForDotPK(UUID classifierId, Integer dataObjectTypeId) {
        this.classifierId = classifierId;
        this.dataObjectTypeId = dataObjectTypeId;
    }

    public UUID getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(UUID classifierId) {
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
        hash += classifierId.hashCode();
        hash += dataObjectTypeId.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassifierAppliedForDotPK)) {
            return false;
        }
        ClassifierAppliedForDotPK other = (ClassifierAppliedForDotPK) object;
        if (!this.classifierId.equals(other.classifierId)) {
            return false;
        }
        if (!this.dataObjectTypeId.equals(other.dataObjectTypeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ClassifiedObjectPK[classifierId=" + classifierId + ", dataObjectTypeId=" + dataObjectTypeId + "]";
    }

}
