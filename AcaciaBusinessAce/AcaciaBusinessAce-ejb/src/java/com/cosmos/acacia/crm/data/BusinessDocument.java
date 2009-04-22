/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(
    name = "business_documents", catalog = "acacia", schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"publisher_id", "document_type_id", "document_number"})
    }
)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=4, name="discriminator_id")
@NamedQueries({
})
public abstract class BusinessDocument extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    public static final String DELIVERY_CERTIFICATE = "DC";
    public static final String SALES_OFFER = "SO";
    public static final String SALES_PROFORMA_INVOICE = "SPI";
    public static final String SALES_INVOICE = "SI";
    public static final String GOODS_RECEIPT = "GR";
    public static final String PURCHASE_ORDER = "PO";
    public static final String PURCHASE_ORDER_CONFIRMATION = "POC";
    public static final String PURCHASE_INVOICE = "PI";

    @Id
    @Basic(optional = false)
    @Column(name = "document_id", nullable = false)
    protected BigInteger documentId;

    @JoinColumn(name = "document_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    protected DbResource documentType;

    @JoinColumn(name = "document_status_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    protected DbResource documentStatus;

    @Basic(optional = false)
    @Column(name = "discriminator_id", nullable = false, length = 4)
    protected String discriminatorId;

    @JoinColumn(name = "publisher_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    protected Organization publisher;

    @JoinColumn(name = "publisher_branch_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    protected Address publisherBranch;

    @JoinColumn(name = "publisher_officer_id", referencedColumnName = "partner_id", nullable = false)
    @ManyToOne(optional = false)
    protected Person publisherOfficer;

    @Column(name = "document_number")
    protected Long documentNumber;

    @Column(name = "document_date")
    @Temporal(TemporalType.DATE)
    protected Date documentDate;

    @JoinColumn(name = "customer_discount_item_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    protected DataObject dataObject;

    @Transient
    private String className;

    public BusinessDocument() {
        throw new UnsupportedOperationException();
    }

    public BusinessDocument(String discriminatorId) {
        this.discriminatorId = discriminatorId;
    }

    public BusinessDocument(String discriminatorId, BigInteger documentId) {
        this(discriminatorId);
        this.documentId = documentId;
    }

    public String getDiscriminatorId() {
        return discriminatorId;
    }

    public void setDiscriminatorId(String discriminatorId) {
        this.discriminatorId = discriminatorId;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public BigInteger getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigInteger documentId) {
        this.documentId = documentId;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DbResource getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DbResource documentStatus) {
        this.documentStatus = documentStatus;
    }

    public DbResource getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DbResource documentType) {
        this.documentType = documentType;
    }

    public Organization getPublisher() {
        return publisher;
    }

    public void setPublisher(Organization publisher) {
        this.publisher = publisher;
        if(publisher != null) {
            setParentId(publisher.getId());
        } else {
            setParentId(null);
        }
    }

    public Address getPublisherBranch() {
        return publisherBranch;
    }

    public void setPublisherBranch(Address publisherBranch) {
        this.publisherBranch = publisherBranch;
    }

    public Person getPublisherOfficer() {
        return publisherOfficer;
    }

    public void setPublisherOfficer(Person publisherOfficer) {
        this.publisherOfficer = publisherOfficer;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        return (documentId != null ? documentId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessDocument)) {
            return false;
        }

        BusinessDocument other = (BusinessDocument) object;
        if ((documentId == null && other.documentId != null) ||
                (documentId != null && !documentId.equals(other.documentId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        if(className == null) {
            className = getClass().getSimpleName();
        }

        return className + "[id=" + documentId + "]";
    }

    @Override
    public BigInteger getId() {
        return getDocumentId();
    }

    @Override
    public void setId(BigInteger id) {
        setDocumentId(id);
    }

    @Override
    public BigInteger getParentId() {
        Organization organization;
        if((organization = getPublisher()) != null) {
            return organization.getId();
        }

        if(dataObject != null) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return toString();
    }
}