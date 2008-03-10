/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "receipt_certificates")
public class ReceiptCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "receipt_certificate_id", nullable = false)
    private BigInteger receiptCertificateId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    @ManyToOne
    private Warehouse warehouse;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name = "receipt_certificate_number", nullable = false)
    private long receiptCertificateNumber;

    @Column(name = "receipt_certificate_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date receiptCertificateDate;

    /**
     * The Deliverer can be both Person or Organization
     */
    @JoinColumn(name = "deliverer_id", referencedColumnName = "classified_object_id", insertable = false, updatable = false)
    @ManyToOne
    private DataObjectBean deliverer;

    @Column(name = "deliverer_name", nullable = false)
    private String delivererName;

    @JoinColumn(name = "deliverer_contact_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person delivererContact;

    @Column(name = "deliverer_contact_name")
    private String delivererContactName;

    @JoinColumn(name = "receipt_cert_method_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource receiptCertificateMethodType;

    @JoinColumn(name = "receipt_cert_reason_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource receiptCertificateReason;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationTime;

    @JoinColumn(name = "creator_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person creator;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @JoinColumn(name = "forwarder_id", referencedColumnName = "classified_object_id", insertable = false, updatable = false)
    @ManyToOne
    private Organization forwarder;

    @JoinColumn(name = "forwarder_contact_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person forwarderContact;

    @Column(name = "forwarder_name")
    private String forwarderName;

    @Column(name = "forwarder_contact_name")
    private String forwarderContactName;

    @JoinColumn(name = "receipt_certificate_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public ReceiptCertificate() {
    }

    public ReceiptCertificate(BigInteger receiptCertificateId) {
        this.receiptCertificateId = receiptCertificateId;
    }

    public BigInteger getReceiptCertificateId() {
        return receiptCertificateId;
    }

    public void setReceiptCertificateId(BigInteger receiptCertificateId) {
        this.receiptCertificateId = receiptCertificateId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public long getReceiptCertificateNumber() {
        return receiptCertificateNumber;
    }

    public void setReceiptCertificateNumber(long receiptCertificateNumber) {
        this.receiptCertificateNumber = receiptCertificateNumber;
    }

    public Date getReceiptCertificateDate() {
        return receiptCertificateDate;
    }

    public void setReceiptCertificateDate(Date receiptCertificateDate) {
        this.receiptCertificateDate = receiptCertificateDate;
    }

    public DataObjectBean getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(DataObjectBean deliverer) {
        this.deliverer = deliverer;
    }

    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    public String getDelivererContactName() {
        return delivererContactName;
    }

    public void setDelivererContactName(String delivererContactName) {
        this.delivererContactName = delivererContactName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Organization getForwarder() {
        return forwarder;
    }

    public void setForwarder(Organization forwarder) {
        this.forwarder = forwarder;
    }

    public String getForwarderName() {
        return forwarderName;
    }

    public void setForwarderName(String forwarderName) {
        this.forwarderName = forwarderName;
    }

    public String getForwarderContactName() {
        return forwarderContactName;
    }

    public void setForwarderContactName(String forwarderContactName) {
        this.forwarderContactName = forwarderContactName;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Person getDelivererContact() {
        return delivererContact;
    }

    public void setDelivererContact(Person delivererContact) {
        this.delivererContact = delivererContact;
    }

    public Person getForwarderContact() {
        return forwarderContact;
    }

    public void setForwarderContact(Person forwarderContact) {
        this.forwarderContact = forwarderContact;
    }

    public DbResource getReceiptCertificateMethodType() {
        return receiptCertificateMethodType;
    }

    public void setReceiptCertificateMethodType(DbResource receiptCertificateMethodType) {
        this.receiptCertificateMethodType = receiptCertificateMethodType;
    }

    public DbResource getReceiptCertificateReason() {
        return receiptCertificateReason;
    }

    public void setReceiptCertificateReason(DbResource receiptCertificateReason) {
        this.receiptCertificateReason = receiptCertificateReason;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiptCertificateId != null ? receiptCertificateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceiptCertificate)) {
            return false;
        }
        ReceiptCertificate other = (ReceiptCertificate) object;
        if ((this.receiptCertificateId == null && other.receiptCertificateId != null) || (this.receiptCertificateId != null && !this.receiptCertificateId.equals(other.receiptCertificateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.ReceiptCertificate[receiptCertificateId=" + receiptCertificateId + "]";
    }

}
