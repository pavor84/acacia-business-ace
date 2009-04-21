/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class EntitySequencePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "entity_id", nullable = false)
    private BigInteger entityId;

    @Basic(optional = false)
    @Column(name = "data_object_type_id", nullable = false)
    private int dataObjectTypeId;

    public EntitySequencePK() {
    }

    public EntitySequencePK(BigInteger entityId, int dataObjectTypeId) {
        this.entityId = entityId;
        this.dataObjectTypeId = dataObjectTypeId;
    }

    public BigInteger getEntityId() {
        return entityId;
    }

    public void setEntityId(BigInteger entityId) {
        this.entityId = entityId;
    }

    public int getDataObjectTypeId() {
        return dataObjectTypeId;
    }

    public void setDataObjectTypeId(int dataObjectTypeId) {
        this.dataObjectTypeId = dataObjectTypeId;
    }

    @Override
    public int hashCode() {
        int hash;
        if(entityId != null)
            hash = entityId.hashCode();
        else
            hash = 0;
        hash += (int) dataObjectTypeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntitySequencePK)) {
            return false;
        }
        EntitySequencePK other = (EntitySequencePK) object;
        if (entityId == null || other.entityId == null || entityId.equals(other.entityId)) {
            return false;
        }
        if (dataObjectTypeId != other.dataObjectTypeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntitySequencePK[entityId=" + entityId + ", dataObjectTypeId=" + dataObjectTypeId + "]";
    }
}
