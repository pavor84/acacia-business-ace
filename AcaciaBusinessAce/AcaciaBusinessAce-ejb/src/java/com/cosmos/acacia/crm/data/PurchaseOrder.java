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
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "purchase_order_id", nullable = false)
    private BigInteger purchaseOrderId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address branch;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "order_number", nullable = false)
    private long orderNumber;

    /**
     * The Supplier can be both Person or Organization
     */
    @Column(name = "supplier_id")
    private BigInteger supplierId;
    @Transient
    private DataObjectBean supplier;

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @Column(name = "supplier_order_number")
    private String supplierOrderNumber;

    @JoinColumn(name = "supplier_contact_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person supplierContact;

    @Column(name = "supplier_contact_name")
    private String supplierContactName;

    @JoinColumn(name = "status_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource status;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationTime;

    @JoinColumn(name = "creator_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person creator;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @JoinColumn(name = "doc_delivery_method_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource documentDeliveryMethod;

    @Column(name = "sent_time")
    @Temporal(TemporalType.DATE)
    private Date sentTime;

    @JoinColumn(name = "sender_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person sender;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "first_delivery_time")
    @Temporal(TemporalType.DATE)
    private Date firstDeliveryTime;

    @Column(name = "last_delivery_time")
    @Temporal(TemporalType.DATE)
    private Date lastDeliveryTime;

    @Column(name = "finalizing_time")
    @Temporal(TemporalType.DATE)
    private Date finalizingTime;

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

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public DataObjectBean getSupplier() {
        return supplier;
    }

    public void setSupplier(DataObjectBean supplier) {
        this.supplier = supplier;
        if(supplier != null)
            setSupplierId(supplier.getId());
        else
            setSupplierId(null);
    }

    public BigInteger getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(BigInteger supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
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

    public Person getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(Person supplierContact) {
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

}
