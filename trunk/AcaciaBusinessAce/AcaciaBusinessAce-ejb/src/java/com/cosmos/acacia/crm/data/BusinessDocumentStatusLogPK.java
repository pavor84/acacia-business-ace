/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Embeddable
public class BusinessDocumentStatusLogPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "document_id", nullable = false)
    private BigInteger documentId;

    @Basic(optional = false)
    @Column(name = "document_status_id", nullable = false)
    private int documentStatusId;

    @Basic(optional = false)
    @Column(name = "action_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionTime;

    public BusinessDocumentStatusLogPK() {
    }

    public BusinessDocumentStatusLogPK(BigInteger documentId, int documentStatusId, Date actionTime) {
        this.documentId = documentId;
        this.documentStatusId = documentStatusId;
        this.actionTime = actionTime;
    }

    public BigInteger getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigInteger documentId) {
        this.documentId = documentId;
    }

    public int getDocumentStatusId() {
        return documentStatusId;
    }

    public void setDocumentStatusId(int documentStatusId) {
        this.documentStatusId = documentStatusId;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    @Override
    public int hashCode() {
        int hash = documentId != null ? documentId.hashCode() : 0;
        hash += (int) documentStatusId;
        hash += (actionTime != null ? actionTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessDocumentStatusLogPK)) {
            return false;
        }
        BusinessDocumentStatusLogPK other = (BusinessDocumentStatusLogPK) object;
        if ((documentId == null && other.documentId != null) ||
                (documentId != null && !documentId.equals(other.documentId))) {
            return false;
        }
        if (documentStatusId != other.documentStatusId) {
            return false;
        }
        if ((actionTime == null && other.actionTime != null) ||
                (actionTime != null && !actionTime.equals(other.actionTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessDocumentStatusLogPK[documentId=" + documentId + ", documentStatusId=" + documentStatusId + ", actionTime=" + actionTime + "]";
    }
}
