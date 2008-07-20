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

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "delivery_certificate_assignments")
@NamedQueries( {
    @NamedQuery (
            name = "DeliveryCertificateAssignment.findAll",
            query = "select a from DeliveryCertificateAssignment a"
    )
})
public class DeliveryCertificateAssignment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected DeliveryCertificateAssignmentPK deliveryCertificateAssignmentPK;

    @JoinColumn(name = "delivery_certificate_id", referencedColumnName = "delivery_certificate_id", insertable=false, updatable=false)
    @ManyToOne
    private DeliveryCertificate deliveryCertificate;

    @Column(name = "document_id", insertable=false, updatable=false)
    private BigInteger documentId;
    
    public DeliveryCertificateAssignment(){
        
    }
    
    public DeliveryCertificateAssignment(DeliveryCertificateAssignmentPK deliveryCertificateAssignmentPK){
        this.deliveryCertificateAssignmentPK = deliveryCertificateAssignmentPK;
    }

    public DeliveryCertificateAssignment(BigInteger deliveryCertificateId, BigInteger documentId){
        this.deliveryCertificateAssignmentPK = new DeliveryCertificateAssignmentPK(deliveryCertificateId, documentId);
    }
    
    public DeliveryCertificateAssignmentPK getDeliveryCertificateAssignmentPK() {
        return deliveryCertificateAssignmentPK;
    }

    public void setDeliveryCertificateAssignmentPK(DeliveryCertificateAssignmentPK deliveryCertificateAssignmentPK) {
        this.deliveryCertificateAssignmentPK = deliveryCertificateAssignmentPK;
    }
    
    public DeliveryCertificate getDeliveryCertificate() {
        return deliveryCertificate;
    }

    public void setDeliveryCertificate(DeliveryCertificate deliveryCertificate) {
        this.deliveryCertificate = deliveryCertificate;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliveryCertificateAssignmentPK != null ? deliveryCertificateAssignmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryCertificateAssignment)) {
            return false;
        }
        DeliveryCertificateAssignment other = (DeliveryCertificateAssignment) object;
        if ((this.deliveryCertificateAssignmentPK == null && other.deliveryCertificateAssignmentPK != null) || (this.deliveryCertificateAssignmentPK != null && !this.deliveryCertificateAssignmentPK.equals(other.deliveryCertificateAssignmentPK))){ 
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber[deliveryCertificateAssignmentPK=" + deliveryCertificateAssignmentPK + "]";
    }

}
