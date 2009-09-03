/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.purchase;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.document.BusinessDocument;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "goods_receipts", catalog = "acacia", schema = "public")
@DiscriminatorValue(value = BusinessDocument.GOODS_RECEIPT)
@PrimaryKeyJoinColumn(name="goods_receipt_id",referencedColumnName="document_id")
@NamedQueries({
    @NamedQuery(
        name = "GoodsReceipt.findAll",
        query = "SELECT g FROM GoodsReceipt g"
    )
})
public class GoodsReceipt extends BusinessDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "GoodsReceipt";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";

    @JoinColumn(name = "supplier_id", referencedColumnName = "business_partner_id", nullable = false)
    @ManyToOne(optional = false)
    private BusinessPartner supplier;

    @JoinColumn(name = "supplier_branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address supplierBranch;

    @JoinColumn(name = "supplier_contact_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person supplierContact;

    @JoinColumn(name = "related_document_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource relatedDocumentType;

    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "related_document_id", nullable = false)
    private UUID relatedDocumentId;

    public GoodsReceipt() {
        super(GOODS_RECEIPT);
    }

    public GoodsReceipt(UUID goodsReceiptId) {
        super(GOODS_RECEIPT, goodsReceiptId);
    }

    public UUID getRelatedDocumentId() {
        return relatedDocumentId;
    }

    public void setRelatedDocumentId(UUID relatedDocumentId) {
        this.relatedDocumentId = relatedDocumentId;
    }

    public DbResource getRelatedDocumentType() {
        return relatedDocumentType;
    }

    public void setRelatedDocumentType(DbResource relatedDocumentType) {
        this.relatedDocumentType = relatedDocumentType;
    }

    public BusinessPartner getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessPartner supplier) {
        this.supplier = supplier;
    }

    public Address getSupplierBranch() {
        return supplierBranch;
    }

    public void setSupplierBranch(Address supplierBranch) {
        this.supplierBranch = supplierBranch;
    }

    public Person getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(Person supplierContact) {
        this.supplierContact = supplierContact;
    }
}
