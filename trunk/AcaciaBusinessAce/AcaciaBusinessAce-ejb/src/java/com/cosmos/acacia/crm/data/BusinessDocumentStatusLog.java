/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
@Table(name = "business_document_status_log", catalog = "acacia", schema = "public")
@NamedQueries({
    @NamedQuery(
        name = "BusinessDocumentStatusLog.findAll",
        query = "SELECT b FROM BusinessDocumentStatusLog b")
})
public class BusinessDocumentStatusLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected BusinessDocumentStatusLogPK businessDocumentStatusLogPK;

    @JoinColumn(name = "document_id", referencedColumnName = "document_id",
        nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BusinessDocument businessDocument;

    @JoinColumn(name = "officer_id", referencedColumnName = "partner_id", nullable = false)
    @ManyToOne(optional = false)
    private Person officer;

    @JoinColumn(name = "document_status_id", referencedColumnName = "resource_id",
        nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbResource documentStatus;

    public BusinessDocumentStatusLog() {
    }

    public BusinessDocumentStatusLog(BusinessDocumentStatusLogPK businessDocumentStatusLogPK) {
        this.businessDocumentStatusLogPK = businessDocumentStatusLogPK;
    }

    public BusinessDocumentStatusLog(BigInteger documentId, int documentStatusId, Date actionTime) {
        this.businessDocumentStatusLogPK = new BusinessDocumentStatusLogPK(documentId, documentStatusId, actionTime);
    }

    public BusinessDocumentStatusLogPK getBusinessDocumentStatusLogPK() {
        return businessDocumentStatusLogPK;
    }

    public void setBusinessDocumentStatusLogPK(BusinessDocumentStatusLogPK businessDocumentStatusLogPK) {
        this.businessDocumentStatusLogPK = businessDocumentStatusLogPK;
    }

    private BusinessDocumentStatusLogPK getPrimaryKey() {
        if(businessDocumentStatusLogPK == null) {
            businessDocumentStatusLogPK = new BusinessDocumentStatusLogPK();
        }

        return businessDocumentStatusLogPK;
    }

    public BusinessDocument getBusinessDocument() {
        return businessDocument;
    }

    public void setBusinessDocument(BusinessDocument businessDocument) {
        this.businessDocument = businessDocument;
        if(businessDocument != null) {
            getPrimaryKey().setDocumentId(businessDocument.getDocumentId());
        } else {
            getPrimaryKey().setDocumentId(null);
        }
    }

    public Person getOfficer() {
        return officer;
    }

    public void setOfficer(Person officer) {
        this.officer = officer;
    }

    public DbResource getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DbResource documentStatus) {
        this.documentStatus = documentStatus;
        if(documentStatus != null) {
            getPrimaryKey().setDocumentStatusId(documentStatus.getResourceId());
        } else {
            getPrimaryKey().setDocumentStatusId(-1);
        }
    }

    public Date getActionTime() {
        if(businessDocumentStatusLogPK != null)
            return businessDocumentStatusLogPK.getActionTime();

        return null;
    }

    public void setActionTime(Date actionTime) {
        if(actionTime != null) {
            getPrimaryKey().setActionTime(actionTime);
        } else {
            getPrimaryKey().setActionTime(null);
        }
    }

    @Override
    public int hashCode() {
        return (businessDocumentStatusLogPK != null ? businessDocumentStatusLogPK.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessDocumentStatusLog)) {
            return false;
        }
        BusinessDocumentStatusLog other = (BusinessDocumentStatusLog) object;
        if ((this.businessDocumentStatusLogPK == null && other.businessDocumentStatusLogPK != null) || (this.businessDocumentStatusLogPK != null && !this.businessDocumentStatusLogPK.equals(other.businessDocumentStatusLogPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusinessDocumentStatusLog[businessDocumentStatusLogPK=" + businessDocumentStatusLogPK + "]";
    }
}
