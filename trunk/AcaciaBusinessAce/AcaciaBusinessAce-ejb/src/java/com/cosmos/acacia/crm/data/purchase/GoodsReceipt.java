/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.purchase;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "goods_receipts", catalog = "acacia", schema = "public")
@NamedQueries({
    @NamedQuery(name = "GoodsReceipt.findAll", query = "SELECT g FROM GoodsReceipt g")
})
public class GoodsReceipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "goods_receipt_id", nullable = false)
    private BigInteger goodsReceiptId;

    @Column(name = "receipt_number")
    private Long receiptNumber;

    @Column(name = "receipt_date")
    @Temporal(TemporalType.DATE)
    private Date receiptDate;

    @JoinColumn(name = "receipt_status_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource receiptStatus;

    @JoinColumn(name = "consignee_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    private Organization consignee;

    @JoinColumn(name = "consignee_branch_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    private Address consigneeBranch;

    @JoinColumn(name = "storekeeper_id", referencedColumnName = "partner_id", nullable = false)
    @ManyToOne(optional = false)
    private Person storekeeper;

    @JoinColumn(name = "supplier_id", referencedColumnName = "partner_id", nullable = false)
    @ManyToOne(optional = false)
    private BusinessPartner supplier;

    @JoinColumn(name = "supplier_branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address supplierBranch;

    @JoinColumn(name = "supplier_contact_id", referencedColumnName = "partner_id")
    @ManyToOne
    private Person supplierContact;

    @JoinColumn(name = "related_document_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource relatedDocumentType;

    @Basic(optional = false)
    @Column(name = "related_document_id", nullable = false)
    private BigInteger relatedDocumentId;

    @JoinColumn(name = "goods_receipt_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public GoodsReceipt() {
    }

    public GoodsReceipt(BigInteger goodsReceiptId) {
        this.goodsReceiptId = goodsReceiptId;
    }

    public GoodsReceipt(BigInteger goodsReceiptId, BigInteger relatedDocumentId) {
        this.goodsReceiptId = goodsReceiptId;
        this.relatedDocumentId = relatedDocumentId;
    }

    public BigInteger getGoodsReceiptId() {
        return goodsReceiptId;
    }

    public void setGoodsReceiptId(BigInteger goodsReceiptId) {
        this.goodsReceiptId = goodsReceiptId;
    }

    public Long getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(Long receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public BigInteger getRelatedDocumentId() {
        return relatedDocumentId;
    }

    public void setRelatedDocumentId(BigInteger relatedDocumentId) {
        this.relatedDocumentId = relatedDocumentId;
    }

    public Address getConsigneeBranch() {
        return consigneeBranch;
    }

    public void setConsigneeBranch(Address consigneeBranch) {
        this.consigneeBranch = consigneeBranch;
    }

    public Address getSupplierBranch() {
        return supplierBranch;
    }

    public void setSupplierBranch(Address supplierBranch) {
        this.supplierBranch = supplierBranch;
    }

    public BusinessPartner getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessPartner supplier) {
        this.supplier = supplier;
    }

    public Organization getConsignee() {
        return consignee;
    }

    public void setConsignee(Organization consignee) {
        this.consignee = consignee;
    }

    public Person getStorekeeper() {
        return storekeeper;
    }

    public void setStorekeeper(Person storekeeper) {
        this.storekeeper = storekeeper;
    }

    public Person getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(Person supplierContact) {
        this.supplierContact = supplierContact;
    }

    public DbResource getRelatedDocumentType() {
        return relatedDocumentType;
    }

    public void setRelatedDocumentType(DbResource relatedDocumentType) {
        this.relatedDocumentType = relatedDocumentType;
    }

    public DbResource getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(DbResource receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (goodsReceiptId != null ? goodsReceiptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GoodsReceipt)) {
            return false;
        }
        GoodsReceipt other = (GoodsReceipt) object;
        if ((this.goodsReceiptId == null && other.goodsReceiptId != null) || (this.goodsReceiptId != null && !this.goodsReceiptId.equals(other.goodsReceiptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GoodsReceipt[goodsReceiptId=" + goodsReceiptId + "]";
    }
}
