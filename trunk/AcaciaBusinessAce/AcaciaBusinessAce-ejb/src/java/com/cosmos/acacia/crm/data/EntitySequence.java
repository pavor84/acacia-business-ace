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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Miro
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "entity_sequences", catalog = "acacia", schema = "public")
@NamedQueries({
    @NamedQuery(name = "EntitySequence.findByEntityId",
        query = "SELECT e FROM EntitySequence e WHERE" +
            " e.entitySequencePK.entityId = :entityId"),
    @NamedQuery(name = "EntitySequence.findByEntityIdAndDataObjectTypeId",
        query = "SELECT e FROM EntitySequence e WHERE" +
            " e.entitySequencePK.entityId = :entityId" +
            " and e.entitySequencePK.dataObjectTypeId = :dataObjectTypeId")
})
public class EntitySequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected EntitySequencePK entitySequencePK;

    @Column(name = "initial_value")
    private Long initialValue;

    @Column(name = "sequence_value")
    private Long sequenceValue;

//    @JoinColumn(name = "data_object_type_id", referencedColumnName = "data_object_type_id", nullable = false, insertable = false, updatable = false)
//    @ManyToOne(optional = false)
//    private DataObjectType dataObjectType;
//
//    @JoinColumn(name = "entity_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
//    @ManyToOne(optional = false)
//    private DataObject dataObject;

    public EntitySequence() {
    }

    public EntitySequence(EntitySequencePK entitySequencePK) {
        this.entitySequencePK = entitySequencePK;
    }

    public EntitySequence(BigInteger entityId, int dataObjectTypeId) {
        this.entitySequencePK = new EntitySequencePK(entityId, dataObjectTypeId);
    }

    public EntitySequencePK getEntitySequencePK() {
        return entitySequencePK;
    }

    public void setEntitySequencePK(EntitySequencePK entitySequencePK) {
        this.entitySequencePK = entitySequencePK;
    }

    public Long getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Long initialValue) {
        this.initialValue = initialValue;
    }

    public Long getSequenceValue() {
        return sequenceValue;
    }

    public void setSequenceValue(Long sequenceValue) {
        this.sequenceValue = sequenceValue;
    }

    public Integer getDataObjectTypeId() {
        if(entitySequencePK != null)
            return entitySequencePK.getDataObjectTypeId();

        return null;
    }

    public BigInteger getDataObjectId() {
        if(entitySequencePK != null)
            return entitySequencePK.getEntityId();

        return null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entitySequencePK != null ? entitySequencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntitySequence)) {
            return false;
        }
        EntitySequence other = (EntitySequence) object;
        if ((this.entitySequencePK == null && other.entitySequencePK != null) || (this.entitySequencePK != null && !this.entitySequencePK.equals(other.entitySequencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntitySequence[entitySequencePK=" + entitySequencePK + "]";
    }
}
