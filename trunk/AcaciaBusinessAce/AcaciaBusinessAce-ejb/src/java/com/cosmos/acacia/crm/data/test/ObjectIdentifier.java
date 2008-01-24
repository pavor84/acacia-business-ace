/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.test;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "object_identifiers")
@NamedQueries({@NamedQuery(name = "ObjectIdentifier.findByObjectId", query =
                                                                     "SELECT o FROM ObjectIdentifier o WHERE o.objectId = :objectId"),
               @NamedQuery(name = "ObjectIdentifier.findByDeleted", query =
                                                                    "SELECT o FROM ObjectIdentifier o WHERE o.deleted = :deleted")})
public class ObjectIdentifier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "object_id", nullable = false)
    private BigDecimal objectId;
    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public ObjectIdentifier() {
    }

    public ObjectIdentifier(BigDecimal objectId) {
        this.objectId = objectId;
    }

    public ObjectIdentifier(BigDecimal objectId, boolean deleted) {
        this.objectId = objectId;
        this.deleted = deleted;
    }

    public BigDecimal getObjectId() {
        return objectId;
    }

    public void setObjectId(BigDecimal objectId) {
        this.objectId = objectId;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objectId != null ? objectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjectIdentifier)) {
            return false;
        }
        ObjectIdentifier other = (ObjectIdentifier) object;
        if ((this.objectId == null && other.objectId != null) ||
            (this.objectId != null && !this.objectId.equals(other.objectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.ObjectIdentifier[objectId=" +
               objectId + "]";
    }

}
