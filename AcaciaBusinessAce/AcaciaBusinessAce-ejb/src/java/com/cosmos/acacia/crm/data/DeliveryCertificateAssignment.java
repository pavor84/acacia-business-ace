/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import org.hibernate.annotations.Type;

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
    ),
    @NamedQuery (
        name = "DeliveryCertificateAssignment.findByDeliveryCertificate",
        query= "select a from DeliveryCertificateAssignment a where a.deliveryCertificateId=:deliveryCertificateId"
    )
})
public class DeliveryCertificateAssignment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "delivery_certificate_id", nullable=false)
    @Type(type="uuid")
    private UUID deliveryCertificateId;

    @Column(name = "document_id", nullable=false)
    @Type(type="uuid")
    private UUID documentId;

    @Column(name = "document_number", nullable=false)
    @Property(title="Document Number")
    private String documentNumber;
    
    public UUID getDeliveryCertificateId() {
        return deliveryCertificateId;
    }

    public void setDeliveryCertificateId(UUID deliveryCertificateId) {
        this.deliveryCertificateId = deliveryCertificateId;
    }
    
    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }
    
    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DeliveryCertificateAssignment[deliveryCertificate=" + deliveryCertificateId + "]";
    }
    
}
