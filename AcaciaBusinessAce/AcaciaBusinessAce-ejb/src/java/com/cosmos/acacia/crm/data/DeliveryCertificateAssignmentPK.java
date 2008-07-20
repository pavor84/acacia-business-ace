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
 * @author daniel
 */
@Embeddable
public class DeliveryCertificateAssignmentPK implements Serializable{
    
    @Column(name = "delivery_certificate_id", nullable = false)
    private BigInteger deliveryCertificateId;

    @Column(name = "document_id", nullable = false)
    private BigInteger documentId;

    public DeliveryCertificateAssignmentPK() {
    }

    public DeliveryCertificateAssignmentPK(BigInteger deliveryCertificateId, BigInteger documentId) {
        this.deliveryCertificateId = deliveryCertificateId;
        this.documentId = documentId;
    }

    public BigInteger getDeliveryCertificateId() {
        return deliveryCertificateId;
    }

    public void setDeliveryCertificateId(BigInteger deliveryCertificateId) {
        this.deliveryCertificateId = deliveryCertificateId;
    }

    public BigInteger getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigInteger documentId) {
        this.documentId = documentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += deliveryCertificateId.intValue();
        hash += (documentId != null ? documentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryCertificateAssignmentPK)) {
            return false;
        }
        DeliveryCertificateAssignmentPK other = (DeliveryCertificateAssignmentPK) object;
        if (this.deliveryCertificateId != other.deliveryCertificateId) {
            return false;
        }
        if ((this.documentId == null && other.documentId != null) || (this.documentId != null && !this.documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DeliveryCertificateAssignmentPK[deliveryCertificateId=" + deliveryCertificateId + ", documentId=" + documentId + "]";
    }
}
