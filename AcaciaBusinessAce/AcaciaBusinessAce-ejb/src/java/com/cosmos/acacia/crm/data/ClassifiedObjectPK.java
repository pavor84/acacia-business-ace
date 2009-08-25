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
 * @author Miro
 */
@Embeddable
public class ClassifiedObjectPK implements Serializable {

    @Column(name = "classifier_id", nullable = false)
    @Type(type="uuid")
    private UUID classifierId;

    @Column(name = "classified_object_id", nullable = false)
    @Type(type="uuid")
    private UUID classifiedObjectId;

    public ClassifiedObjectPK() {
    }

    public ClassifiedObjectPK(UUID classifierId, UUID classifiedObjectId) {
        this.classifierId = classifierId;
        this.classifiedObjectId = classifiedObjectId;
    }

    public UUID getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(UUID classifierId) {
        this.classifierId = classifierId;
    }

    public UUID getClassifiedObjectId() {
        return classifiedObjectId;
    }

    public void setClassifiedObjectId(UUID classifiedObjectId) {
        this.classifiedObjectId = classifiedObjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += classifierId.hashCode();
        hash += classifiedObjectId.hashCode();
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
