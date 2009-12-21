/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.warehouse;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

/**
 *
 * @author daniel
 */
@Embeddable
public class DeliveryCertificateAssignmentPK implements Serializable{
    
    @Column(name = "delivery_certificate_id", nullable = false)
    @Type(type="uuid")
    private UUID deliveryCertificateId;

    @Column(name = "document_id", nullable = false)
    @Type(type="uuid")
    private UUID documentId;

    public DeliveryCertificateAssignmentPK() {
    }

    public DeliveryCertificateAssignmentPK(UUID deliveryCertificateId, UUID documentId) {
        this.deliveryCertificateId = deliveryCertificateId;
        this.documentId = documentId;
    }

    public UUID getDeliveryCertificateId() {
        return deliveryCertificateId;
    }

    public void setDeliveryCertificateId(UUID deliveryCertificateId) {
        this.deliveryCertificateId = deliveryCertificateId;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += deliveryCertificateId.hashCode();
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
