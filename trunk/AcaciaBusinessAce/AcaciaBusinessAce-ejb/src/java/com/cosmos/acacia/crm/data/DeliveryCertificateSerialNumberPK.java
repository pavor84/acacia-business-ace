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
public class DeliveryCertificateSerialNumberPK implements Serializable {

    @Column(name = "certificate_item_id", nullable = false)
    @Type(type="uuid")
    private UUID certificateItemId;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    public DeliveryCertificateSerialNumberPK() {
    }

    public DeliveryCertificateSerialNumberPK(UUID certificateItemId, String serialNumber) {
        this.certificateItemId = certificateItemId;
        this.serialNumber = serialNumber;
    }

    public UUID getCertificateItemId() {
        return certificateItemId;
    }

    public void setCertificateItemId(UUID certificateItemId) {
        this.certificateItemId = certificateItemId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += certificateItemId.hashCode();
        hash += (serialNumber != null ? serialNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryCertificateSerialNumberPK)) {
            return false;
        }
        DeliveryCertificateSerialNumberPK other = (DeliveryCertificateSerialNumberPK) object;
        if (this.certificateItemId != other.certificateItemId) {
            return false;
        }
        if ((this.serialNumber == null && other.serialNumber != null) || (this.serialNumber != null && !this.serialNumber.equals(other.serialNumber))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumberPK[certificateItemId=" + certificateItemId + ", serialNumber=" + serialNumber + "]";
    }

}
