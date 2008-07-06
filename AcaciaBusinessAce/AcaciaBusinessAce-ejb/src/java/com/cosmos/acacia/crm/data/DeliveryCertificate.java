/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
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
@Table(name = "delivery_certificates")
public class DeliveryCertificate extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "delivery_certificate_id", nullable = false)
    private BigInteger deliveryCertificateId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    @ManyToOne
    private Warehouse warehouse;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name = "delivery_certificate_number", nullable = false)
    private long deliveryCertificateNumber;

    @Column(name = "delivery_certificate_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date deliveryCertificateDate;

    /**
     * The Recipient can be both Person or Organization
     */
    @JoinColumn(name = "recipient_id", referencedColumnName = "partner_id")
    @ManyToOne
    @Property(title="Recipient")
    private BusinessPartner recipient;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @JoinColumn(name = "recipient_contact_id")
    @ManyToOne
    private Person recipientContact;

    @Column(name = "recipient_contact_name")
    private String recipientContactName;

    @JoinColumn(name = "delivery_cert_method_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource deliveryCertificateMethodType;

    @JoinColumn(name = "delivery_cert_reason_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource deliveryCertificateReason;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationTime;

    @JoinColumn(name = "creator_id")
    @ManyToOne
    private Person creator;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @JoinColumn(name = "forwarder_id", insertable = false, updatable = false)
    @ManyToOne
    private Organization forwarder;

    @JoinColumn(name = "forwarder_contact_id")
    @ManyToOne
    private Person forwarderContact;

    @Column(name = "forwarder_name")
    private String forwarderName;

    @Column(name = "forwarder_contact_name")
    private String forwarderContactName;

    @JoinColumn(name = "delivery_certificate_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public DeliveryCertificate() {
    }

    public DeliveryCertificate(BigInteger deliveryCertificateId) {
        this.deliveryCertificateId = deliveryCertificateId;
    }

    public BigInteger getDeliveryCertificateId() {
        return deliveryCertificateId;
    }

    public void setDeliveryCertificateId(BigInteger deliveryCertificateId) {
        this.deliveryCertificateId = deliveryCertificateId;
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

    public long getDeliveryCertificateNumber() {
        return deliveryCertificateNumber;
    }

    public void setDeliveryCertificateNumber(long deliveryCertificateNumber) {
        this.deliveryCertificateNumber = deliveryCertificateNumber;
    }

    public Date getDeliveryCertificateDate() {
        return deliveryCertificateDate;
    }

    public void setDeliveryCertificateDate(Date deliveryCertificateDate) {
        this.deliveryCertificateDate = deliveryCertificateDate;
    }

    public BusinessPartner getRecipient() {
        return recipient;
    }

    public void setRecipient(BusinessPartner recipient) {
        //firePropertyChange("recipient", this.recipient, recipient);
        this.recipient = recipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientContactName() {
        return recipientContactName;
    }

    public void setRecipientContactName(String recipientContactName) {
        this.recipientContactName = recipientContactName;
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

    public Person getRecipientContact() {
        return recipientContact;
    }

    public void setRecipientContact(Person recipientContact) {
        this.recipientContact = recipientContact;
    }

    public Person getForwarderContact() {
        return forwarderContact;
    }

    public void setForwarderContact(Person forwarderContact) {
        this.forwarderContact = forwarderContact;
    }

    public DbResource getDeliveryCertificateMethodType() {
        return deliveryCertificateMethodType;
    }

    public void setDeliveryCertificateMethodType(DbResource deliveryCertificateMethodType) {
        this.deliveryCertificateMethodType = deliveryCertificateMethodType;
    }

    public DbResource getDeliveryCertificateReason() {
        return deliveryCertificateReason;
    }

    public void setDeliveryCertificateReason(DbResource deliveryCertificateReason) {
        this.deliveryCertificateReason = deliveryCertificateReason;
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
        hash += (deliveryCertificateId != null ? deliveryCertificateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryCertificate)) {
            return false;
        }
        DeliveryCertificate other = (DeliveryCertificate) object;
        if ((this.deliveryCertificateId == null && other.deliveryCertificateId != null) || (this.deliveryCertificateId != null && !this.deliveryCertificateId.equals(other.deliveryCertificateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DeliveryCertificate[deliveryCertificateId=" + deliveryCertificateId + "]";
    }

    @Override
    public BigInteger getId() {
        return this.getDeliveryCertificateId();
    }

    @Override
    public void setId(BigInteger id) {
        this.setDeliveryCertificateId(id);
    }

}
