/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.warehouse;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "receipt_certificate_serial_numbers")
public class ReceiptCertificateSerialNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ReceiptCertificateSerialNumberPK receiptCertificateSerialNumberPK;

    @JoinColumn(name = "certificate_item_id", referencedColumnName = "certificate_item_id", insertable = false, updatable = false)
    @ManyToOne
    private ReceiptCertificateItem receiptCertificateItem;

    public ReceiptCertificateSerialNumber() {
    }

    public ReceiptCertificateSerialNumber(ReceiptCertificateSerialNumberPK receiptCertificateSerialNumberPK) {
        this.receiptCertificateSerialNumberPK = receiptCertificateSerialNumberPK;
    }

    public ReceiptCertificateSerialNumber(UUID certificateItemId, String serialNumber) {
        this.receiptCertificateSerialNumberPK = new ReceiptCertificateSerialNumberPK(certificateItemId, serialNumber);
    }

    public ReceiptCertificateSerialNumberPK getReceiptCertificateSerialNumberPK() {
        return receiptCertificateSerialNumberPK;
    }

    public void setReceiptCertificateSerialNumberPK(ReceiptCertificateSerialNumberPK receiptCertificateSerialNumberPK) {
        this.receiptCertificateSerialNumberPK = receiptCertificateSerialNumberPK;
    }

    public ReceiptCertificateItem getReceiptCertificateItem() {
        return receiptCertificateItem;
    }

    public void setReceiptCertificateItem(ReceiptCertificateItem receiptCertificateItem) {
        this.receiptCertificateItem = receiptCertificateItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiptCertificateSerialNumberPK != null ? receiptCertificateSerialNumberPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceiptCertificateSerialNumber)) {
            return false;
        }
        ReceiptCertificateSerialNumber other = (ReceiptCertificateSerialNumber) object;
        if ((this.receiptCertificateSerialNumberPK == null && other.receiptCertificateSerialNumberPK != null) || (this.receiptCertificateSerialNumberPK != null && !this.receiptCertificateSerialNumberPK.equals(other.receiptCertificateSerialNumberPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.ReceiptCertificateSerialNumber[receiptCertificateSerialNumberPK=" + receiptCertificateSerialNumberPK + "]";
    }

}
