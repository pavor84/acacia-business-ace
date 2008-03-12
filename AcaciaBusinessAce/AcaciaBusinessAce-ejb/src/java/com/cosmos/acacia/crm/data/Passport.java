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
@Table(name = "passports")
public class Passport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "passport_id", nullable = false)
    private BigInteger passportId;

    @Column(name = "parent_id", nullable = false)
    private BigInteger parentId;

    @JoinColumn(name = "passport_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource passportType;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "issue_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @JoinColumn(name = "issuer_id", referencedColumnName = "organization_id", insertable = false, updatable = false)
    @ManyToOne
    private Organization issuer;

    @JoinColumn(name = "issuer_branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address issuerBranch;

    @Column(name = "additional_info")
    private String additionalInfo;

    @JoinColumn(name = "passport_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public Passport() {
    }

    public Passport(BigInteger passportId) {
        this.passportId = passportId;
    }

    public BigInteger getPassportId() {
        return passportId;
    }

    public void setPassportId(BigInteger passportId) {
        this.passportId = passportId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Organization getIssuer() {
        return issuer;
    }

    public void setIssuer(Organization issuer) {
        this.issuer = issuer;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Address getIssuerBranch() {
        return issuerBranch;
    }

    public void setIssuerBranch(Address issuerBranch) {
        this.issuerBranch = issuerBranch;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getPassportType() {
        return passportType;
    }

    public void setPassportType(DbResource passportType) {
        this.passportType = passportType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (passportId != null ? passportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Passport)) {
            return false;
        }
        Passport other = (Passport) object;
        if ((this.passportId == null && other.passportId != null) || (this.passportId != null && !this.passportId.equals(other.passportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Passport[passportId=" + passportId + "]";
    }

}
