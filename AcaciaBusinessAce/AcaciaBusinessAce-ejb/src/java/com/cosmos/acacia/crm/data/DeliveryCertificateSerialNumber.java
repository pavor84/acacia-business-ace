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

import com.cosmos.acacia.annotation.Property;
import com.cosmos.util.CloneableBean;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "delivery_certificate_serial_numbers")
@NamedQueries({ 
    @NamedQuery
    ( 
        /**
         * Get all delivery certificates for a given warehouse 
         * Parameters:
         * - warehouse - an warehouse
         */
        name = "DeliveryCertificateSerialNumber.findForCertificateItem",
        query = "select dcsn from DeliveryCertificateSerialNumber dcsn where dcsn.deliveryCertificateItem.certificateItemId=:parentId"
    )
})
public class DeliveryCertificateSerialNumber implements Serializable, CloneableBean<DeliveryCertificateSerialNumber> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected DeliveryCertificateSerialNumberPK deliveryCertificateSerialNumberPK;

    @JoinColumn(name = "certificate_item_id", referencedColumnName = "certificate_item_id", insertable = false, updatable = false)
    @ManyToOne
    private DeliveryCertificateItem deliveryCertificateItem;
    
    @Column(name = "serial_number", insertable = false, updatable = false)
    @Property(title = "Serial Number", editable = true)
    private String serialNumber;

    public DeliveryCertificateSerialNumber() {
    }

    public DeliveryCertificateSerialNumber(DeliveryCertificateSerialNumberPK deliveryCertificateSerialNumberPK) {
        this.deliveryCertificateSerialNumberPK = deliveryCertificateSerialNumberPK;
    }

    public DeliveryCertificateSerialNumber(BigInteger certificateItemId, String serialNumber) {
        this.deliveryCertificateSerialNumberPK = new DeliveryCertificateSerialNumberPK(certificateItemId, serialNumber);
    }

    public DeliveryCertificateSerialNumberPK getDeliveryCertificateSerialNumberPK() {
        return deliveryCertificateSerialNumberPK;
    }

    public void setDeliveryCertificateSerialNumberPK(DeliveryCertificateSerialNumberPK deliveryCertificateSerialNumberPK) {
        this.deliveryCertificateSerialNumberPK = deliveryCertificateSerialNumberPK;
    }

    public DeliveryCertificateItem getDeliveryCertificateItem() {
        return deliveryCertificateItem;
    }

    public void setDeliveryCertificateItem(DeliveryCertificateItem deliveryCertificateItem) {
        this.deliveryCertificateItem = deliveryCertificateItem;
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        if(deliveryCertificateSerialNumberPK != null){
        	deliveryCertificateSerialNumberPK.setSerialNumber(serialNumber);
        }
    	this.serialNumber = serialNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliveryCertificateSerialNumberPK != null ? deliveryCertificateSerialNumberPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryCertificateSerialNumber)) {
            return false;
        }
        DeliveryCertificateSerialNumber other = (DeliveryCertificateSerialNumber) object;
        if ((this.deliveryCertificateSerialNumberPK == null && other.deliveryCertificateSerialNumberPK != null) || (this.deliveryCertificateSerialNumberPK != null && !this.deliveryCertificateSerialNumberPK.equals(other.deliveryCertificateSerialNumberPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber[deliveryCertificateSerialNumberPK=" + deliveryCertificateSerialNumberPK + "]";
    }

    @Override
    public DeliveryCertificateSerialNumber clone() {
        try {
            return (DeliveryCertificateSerialNumber) super.clone();
        } catch(CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
