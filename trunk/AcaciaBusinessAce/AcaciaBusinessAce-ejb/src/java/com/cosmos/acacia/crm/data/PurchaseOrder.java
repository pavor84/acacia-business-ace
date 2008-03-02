/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
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

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "purchase_orders")
@NamedQueries({@NamedQuery(name = "PurchaseOrder.findByPurchaseOrderId", query = "SELECT p FROM PurchaseOrder p WHERE p.purchaseOrderId = :purchaseOrderId"), @NamedQuery(name = "PurchaseOrder.findByParentId", query = "SELECT p FROM PurchaseOrder p WHERE p.parentId = :parentId"), @NamedQuery(name = "PurchaseOrder.findByOrderNumber", query = "SELECT p FROM PurchaseOrder p WHERE p.orderNumber = :orderNumber"), @NamedQuery(name = "PurchaseOrder.findBySupplierId", query = "SELECT p FROM PurchaseOrder p WHERE p.supplierId = :supplierId"), @NamedQuery(name = "PurchaseOrder.findBySupplierOrderNumber", query = "SELECT p FROM PurchaseOrder p WHERE p.supplierOrderNumber = :supplierOrderNumber"), @NamedQuery(name = "PurchaseOrder.findByCreationTime", query = "SELECT p FROM PurchaseOrder p WHERE p.creationTime = :creationTime"), @NamedQuery(name = "PurchaseOrder.findBySentTime", query = "SELECT p FROM PurchaseOrder p WHERE p.sentTime = :sentTime"), @NamedQuery(name = "PurchaseOrder.findByFirstDeliveryTime", query = "SELECT p FROM PurchaseOrder p WHERE p.firstDeliveryTime = :firstDeliveryTime"), @NamedQuery(name = "PurchaseOrder.findByLastDeliveryTime", query = "SELECT p FROM PurchaseOrder p WHERE p.lastDeliveryTime = :lastDeliveryTime"), @NamedQuery(name = "PurchaseOrder.findByFinalizingTime", query = "SELECT p FROM PurchaseOrder p WHERE p.finalizingTime = :finalizingTime")})
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "purchase_order_id", nullable = false)
    private Long purchaseOrderId;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    @JoinColumn(name = "supplier_id", referencedColumnName = "classified_object_id", insertable = false, updatable = false)
    @ManyToOne
    private ClassifiedObject supplierClassifier;

    @Column(name = "supplier_order_number")
    private String supplierOrderNumber;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date creationTime;

    @Column(name = "sent_time")
    @Temporal(TemporalType.DATE)
    private Date sentTime;

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

    @JoinColumn(name = "supplier_contact_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person supplierContactId;

    @JoinColumn(name = "creator_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person creatorId;

    @JoinColumn(name = "sender_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person senderId;

    @JoinColumn(name = "status_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource statusId;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ClassifiedObject getSupplierClassifier() {
        return supplierClassifier;
    }

    public void setSupplierClassifier(ClassifiedObject supplierClassifier) {
        this.supplierClassifier = supplierClassifier;
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

    public Person getSupplierContactId() {
        return supplierContactId;
    }

    public void setSupplierContactId(Person supplierContactId) {
        this.supplierContactId = supplierContactId;
    }

    public Person getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Person creatorId) {
        this.creatorId = creatorId;
    }

    public Person getSenderId() {
        return senderId;
    }

    public void setSenderId(Person senderId) {
        this.senderId = senderId;
    }

    public DbResource getStatusId() {
        return statusId;
    }

    public void setStatusId(DbResource statusId) {
        this.statusId = statusId;
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
