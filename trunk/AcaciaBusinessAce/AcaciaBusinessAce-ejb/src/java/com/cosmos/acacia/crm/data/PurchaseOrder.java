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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "purchase_orders")
@NamedQueries(
    {
        /**
         * Parameters:
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "PurchaseOrder.findForParentAndDeleted",
                query = "select po from PurchaseOrder po where po.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and po.dataObject.deleted = :deleted"
            ),
        /**
         * Parameters:
         *  - parentDataObjectId - not null, the parent object id
         *  - supplier - not null
         */
        @NamedQuery
            (
                name = "PurchaseOrder.maxSupplierOrderNumberForSupplier",
                query = "select max(po.supplierOrderNumber) from PurchaseOrder po where po.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and po.supplier = :supplier"
            ),
        /**
         * Parameters:
         *  - branch - not null
         */
        @NamedQuery
            (
                name = "PurchaseOrder.maxOrderNumberForBranch",
                query = "select max(po.orderNumber) from PurchaseOrder po where po.branch = :branch "
            ),
        /**
         * Parameters:
         *  - parentDataObjectId - not null
         */
        @NamedQuery
            (
                name = "PurchaseOrder.maxOrderNumber",
                query = "select max(po.orderNumber) from PurchaseOrder po where po.dataObject.parentDataObjectId = :parentDataObjectId "
            ),
        /**
         *
         * Parameters:
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         *  - status_sent - should be the value {@link PurchaseOrderStatus#Sent#getDbResource()}
         *  - status_partlyConfirmed - should be the value {@link PurchaseOrderStatus#PartlyConfirmed#getDbResource()}
         *  - supplier - may be null
         *  - branch - may be null
         */
        @NamedQuery(
            name = "PurchaseOrder.findPendingOrdersBySupplier",
            query = "select po from PurchaseOrder po" +
                    " where po.dataObject.parentDataObjectId = :parentDataObjectId" +
                    "  and po.dataObject.deleted = :deleted" +
                    "  and (po.status = :status_sent or po.status = :status_partlyConfirmed)" +
                    "  and (po.supplier = :supplier or :supplier is null)" +
                    "  and po.branch = :branch"
        ),
        @NamedQuery(
            name = "PurchaseOrder.findAllPendingOrders",
            query = "select po from PurchaseOrder po" +
                    " where po.dataObject.parentDataObjectId = :parentDataObjectId" +
                    "  and po.dataObject.deleted = :deleted" +
                    "  and (po.status = :status_sent or po.status = :status_partlyConfirmed)" +
                    "  and (po.supplier = :supplier or :supplier is null)" +
                    "  and po.branch = :branch"
        )
    })
public class PurchaseOrder extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "purchase_order_id", nullable = false)
    private BigInteger purchaseOrderId;

    @Column(name = "parent_id", precision=0, length=18)
    private BigInteger parentId;

    @Property(title="Order Number", editable=false)
    @Column(name = "order_number", nullable = false)
    private BigInteger orderNumber;

    @Property(title="Supp. Order Number", editable=false)
    @Column(name = "supplier_order_number")
    private BigInteger supplierOrderNumber;

    @Property(title="Branch", customDisplay="${branch.addressName}", editable=false)
    @JoinColumn(name = "branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address branch;

    @Property(title="Branch Name", editable=false)
    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Property(title="Status", readOnly=true, propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "status_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource status;
    
    @Property(title="Delivery Status", readOnly=true)
    @ManyToOne
    private DbResource deliveryStatus;
 
    @Property(title="Delivery Method", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "doc_delivery_method_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource documentDeliveryMethod;

    /**
     * The Supplier can be both Person or Organization
     */
    @Property(title="Supplier", propertyValidator=@PropertyValidator(required=true), customDisplay="${supplier.displayName}")
    @ManyToOne
    private BusinessPartner supplier;

    @Property(title="Supplier Name", editable=false)
    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @Property(title="Supplier Contact", propertyValidator=@PropertyValidator(required=true), customDisplay="${supplierContact.contact.displayName}")
    @JoinColumn(name = "supplier_contact_id")
    @ManyToOne
    private ContactPerson supplierContact;

    @Property(title="Contact Name")
    @Column(name = "supplier_contact_name")
    private String supplierContactName;

    @Property(title="Creator", customDisplay="${creator.displayName}")
    @JoinColumn(name = "creator_id")
    @ManyToOne
    private Person creator;

    @Property(title="Creator Name", editable=false)
    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Property(title="Created At", editable=false)
    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationTime;

    @Property(title="Sender", customDisplay="${sender.displayName}")
    @JoinColumn(name = "sender_id")
    @ManyToOne
    private Person sender;

    @Property(title="Sender Name", editable=false)
    @Column(name = "sender_name")
    private String senderName;

    @Property(title="Sent At", editable=false)
    @Column(name = "sent_time")
    @Temporal(TemporalType.DATE)
    private Date sentTime;

    @Property(title="Finalized At", editable=false)
    @Column(name = "finalizing_time")
    @Temporal(TemporalType.DATE)
    private Date finalizingTime;

    @Property(title="First Delivery", editable=false)
    @Column(name = "first_delivery_time")
    @Temporal(TemporalType.DATE)
    private Date firstDeliveryTime;

    @Property(title="Last Delivery", editable=false)
    @Column(name = "last_delivery_time")
    @Temporal(TemporalType.DATE)
    private Date lastDeliveryTime;

    @Column(name = "notes")
    @Property(title="Notes")
    private String notes;

    @JoinColumn(name = "purchase_order_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public PurchaseOrder() {
    }

    public PurchaseOrder(BigInteger purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public BigInteger getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(BigInteger purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Date getFirstDeliveryTime() {
        return firstDeliveryTime;
    }

    public void setFirstDeliveryTime(Date firstDeliveryTime) {
        this.firstDeliveryTime = firstDeliveryTime;
    }

    public Date getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public void setLastDeliveryTime(Date lastDeliveryTime) {
        this.lastDeliveryTime = lastDeliveryTime;
    }

    public Date getFinalizingTime() {
        return finalizingTime;
    }

    public void setFinalizingTime(Date finalizingTime) {
        this.finalizingTime = finalizingTime;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public ContactPerson getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(ContactPerson supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierContactName() {
        return supplierContactName;
    }

    public void setSupplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
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

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public DbResource getStatus() {
        return status;
    }

    public void setStatus(DbResource status) {
        this.status = status;
    }

    public DbResource getDocumentDeliveryMethod() {
        return documentDeliveryMethod;
    }

    public void setDocumentDeliveryMethod(DbResource documentDeliveryMethod) {
        this.documentDeliveryMethod = documentDeliveryMethod;
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        this.branch = branch;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchaseOrderId != null ? purchaseOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseOrder)) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) object;
        if ((this.purchaseOrderId == null && other.purchaseOrderId != null) || (this.purchaseOrderId != null && !this.purchaseOrderId.equals(other.purchaseOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.PurchaseOrder[purchaseOrderId=" + purchaseOrderId + "]";
    }

    @Override
    public BigInteger getId() {
        return purchaseOrderId;
    }

    @Override
    public String getInfo() {
        return orderNumber+": "+supplierOrderNumber;
    }

    @Override
    public void setId(BigInteger id) {
        purchaseOrderId = id;
    }

    public BusinessPartner getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessPartner supplier) {
        this.supplier = supplier;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public BigInteger getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(BigInteger orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigInteger getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(BigInteger supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public DbResource getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DbResource deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

}
