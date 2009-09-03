/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.Address;
import java.io.Serializable;
import java.util.UUID;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType;
import java.math.BigInteger;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "delivery_certificates")
@NamedQueries({ 
    @NamedQuery
    ( 
        /**
         * Get all delivery certificates for a given warehouse 
         * Parameters:
         * - warehouse - an warehouse
         */
        name = "DeliveryCertificate.findByWarehouse",
        query = "select ds from DeliveryCertificate ds where ds.warehouse.warehouseId=:parentId and ds.dataObject.deleted = false"
    ),
    @NamedQuery
    (
    	name = "DeliveryCertificate.findByIdAndDeleted",
    	query = "select ds from DeliveryCertificate ds where ds.deliveryCertificateId=:deliveryCertificateId and ds.dataObject.deleted = :deleted"
    )
})
public class DeliveryCertificate extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "delivery_certificate_id", nullable = false)
    @Type(type="uuid")
    private UUID deliveryCertificateId;

    @Column(name = "parent_id")
    @Type(type="uuid")
    private UUID parentId;

    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    @ManyToOne
    private Warehouse warehouse;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @JoinColumn(name = "delivery_cert_status_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Status")
    private DbResource status;
    
    @Column(name = "delivery_certificate_number")
    @Property(title="Serial Number", editable=false, propertyValidator=@PropertyValidator(required=false))
    private BigInteger deliveryCertificateNumber;

    @Column(name = "delivery_certificate_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Certificate date", editable=false, propertyValidator=@PropertyValidator(required=false))
    private Date deliveryCertificateDate;

    @JoinColumn(name = "recipient_id", referencedColumnName = "business_partner_id", nullable=false)
    @ManyToOne
    private BusinessPartner recipient;

    @Column(name = "recipient_name", nullable=false)
    @Property(title="Recipient", editable=false, propertyValidator=@PropertyValidator(required=false))
    private String recipientName;
    
    @JoinColumn(name = "recipient_branch_id")
    @ManyToOne
    @Property(title="Recipient Branch", customDisplay="${recipientBranch.addressName}")
    private Address recipientBranch;
    
    @Column(name = "recipient_branch_name")
    @Property(title="Recipient Branch Name", editable=false)
    private String recipientBranchName;

    @JoinColumn(name = "recipient_contact_id")
    @ManyToOne
    @Property(title="Recipient Contact", customDisplay="${recipientContact.contact.displayName}")
    private ContactPerson recipientContact;

    @Column(name = "recipient_contact_name")
    @Property(title="Recipient Contact Name", editable=false)
    private String recipientContactName;

    @JoinColumn(name = "delivery_cert_method_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Delivery Method")
    private DbResource deliveryCertificateMethodType;

    @JoinColumn(name = "delivery_cert_reason_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Reason")
    private DbResource deliveryCertificateReason;

    @Transient
    private DeliveryCertificateAssignment documentAssignment;
    
    public DeliveryCertificateAssignment getDocumentAssignment() {
        return documentAssignment;
    }

    public void setDocumentAssignment(DeliveryCertificateAssignment documentAssignment) {
        this.documentAssignment = documentAssignment;
    }
    
    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.DATE)
    @Property(title="Creation Time", editable=false)
    private Date creationTime;

    @JoinColumn(name = "creator_id")
    @ManyToOne
    private Person creator;

    @Column(name = "creator_name", nullable = false)
    @Property(title="Creator", editable=false)
    private String creatorName;

    @JoinColumn(name = "creator_organization_id", nullable = false)
    @ManyToOne
    @Property(title="Creator Organization", customDisplay="${creatorOrganization.organizationName}")
    private Organization creatorOrganization;
    
    @Column(name="creator_organization_name")
    @Property(title="Creator Organization Name", visible=false, editable=false)
    private String creatorOrganizationName;
    
    @JoinColumn(name = "creator_branch_id")
    @ManyToOne
    @Property(title="Creator Branch", customDisplay="${creatorBranch.addressName}")
    private Address creatorBranch;
    
    @Column(name="creator_branch_name")
    @Property(title="Creator Branch Name", visible=false, editable=false)
    private String creatorBranchName;
    
    @JoinColumn(name = "forwarder_id")
    @ManyToOne
    @Property(title="Forwarder", customDisplay="${forwarder.organizationName}")
    private Organization forwarder;
    
    @JoinColumn(name = "forwarder_branch_id")
    @ManyToOne
    @Property(title="Forwarder Branch", customDisplay="${forwarderBranch.addressName}")
    private Address forwarderBranch;

    @JoinColumn(name = "forwarder_contact_id")
    @ManyToOne
    @Property(title="Forwarder Contact", visible=false)
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

    public DeliveryCertificate(UUID deliveryCertificateId) {
        this.deliveryCertificateId = deliveryCertificateId;
    }

    public UUID getDeliveryCertificateId() {
        return deliveryCertificateId;
    }

    public void setDeliveryCertificateId(UUID deliveryCertificateId) {
        this.deliveryCertificateId = deliveryCertificateId;
    }

    @Override
    public UUID getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public BigInteger getDeliveryCertificateNumber() {
        return deliveryCertificateNumber;
    }

    public void setDeliveryCertificateNumber(BigInteger deliveryCertificateNumber) {
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
        this.recipient = recipient;
        if(recipient != null){
            this.setRecipientName(recipient.getDisplayName());
        }
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    
    public Address getRecipientBranch() {
        return recipientBranch;
    }

    public void setRecipientBranch(Address recipientAddress) {
        this.recipientBranch = recipientAddress;
        if(recipientBranch != null){
            this.setRecipientBranchName(recipientAddress.getAddressName());
        }
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
        this.setCreatorName(creator.getDisplayName());
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
    public Address getCreatorBranch() {
        return creatorBranch;
    }

    public void setCreatorBranch(Address creatorBranch) {
        this.creatorBranch = creatorBranch;
        if(creatorBranch != null){
            this.setCreatorBranchName(creatorBranch.getAddressName());
        }
    }

    public Organization getCreatorOrganization() {
        return creatorOrganization;
    }

    public void setCreatorOrganization(Organization creatorOrganization) {
        this.creatorOrganization = creatorOrganization;
        if(creatorOrganization != null){
            this.setCreatorOrganizationName(creatorOrganization.getOrganizationName());
        }
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
    
    public Address getForwarderBranch() {
        return forwarderBranch;
    }

    public void setForwarderBrunch(Address forwarderBranch) {
        this.forwarderBranch = forwarderBranch;
    }

    public String getForwarderContactName() {
        return forwarderContactName;
    }

    public void setForwarderContactName(String forwarderContactName) {
        this.forwarderContactName = forwarderContactName;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public ContactPerson getRecipientContact() {
        return recipientContact;
    }

    public void setRecipientContact(ContactPerson recipientContact) {
        this.recipientContact = recipientContact;
        if(recipientContact != null && recipientContact.getPerson() != null){
            this.setRecipientContactName(recipientContact.getPerson().getDisplayName());
        }
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

    public String getCreatorBranchName() {
        return creatorBranchName;
    }

    public void setCreatorBranchName(String creatorBranchName) {
        this.creatorBranchName = creatorBranchName;
    }

    public String getCreatorOrganizationName() {
        return creatorOrganizationName;
    }

    public void setCreatorOrganizationName(String creatorOrganizationName) {
        this.creatorOrganizationName = creatorOrganizationName;
    }

    public String getRecipientBranchName() {
        return recipientBranchName;
    }

    public void setRecipientBranchName(String recipientBranchName) {
        this.recipientBranchName = recipientBranchName;
    }
    
    public DbResource getStatus() {
		return status;
	}

	public void setStatus(DbResource status) {
		this.status = status;
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
    public UUID getId() {
        return this.getDeliveryCertificateId();
    }

    @Override
    public void setId(UUID id) {
        this.setDeliveryCertificateId(id);
    }

    @Override
    public String getInfo() {
        return "" + getDeliveryCertificateNumber();
    }

    @PrePersist
    public void initialize(){
        
        Date now = new Date();
        //this.setDeliveryCertificateDate(now);
        this.setCreationTime(now);
        //this.setDeliveryCertificateNumber(UUID.valueOf(now.getTime()));
        
        if(this.getCreationTime() == null){
        	this.setCreationTime(new Date());
        }
        
        if(this.deliveryCertificateMethodType.getEnumValue().equals(DeliveryCertificateMethodType.InPlace)){
            this.setForwarder(null);
            this.setForwarderBrunch(null);
            this.setForwarderContact(null);
            this.setForwarderContactName(null);
            this.setForwarderName(null);
        }
    }
	
}
