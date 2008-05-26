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
import com.cosmos.acacia.annotation.ValidationType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "passports")
@NamedQueries(
    {
        @NamedQuery
             (
                 name = "Passport.findByParentDataObjectAndDeleted",
                 query = "select p from Passport p where p.dataObject.parentDataObject = :parentDataObject and p.dataObject.deleted = :deleted"
             ),
                @NamedQuery
                (
                name = "Passport.findByParentDataObjectIsNullAndDeleted",
                query = "select p from Passport p where p.dataObject.parentDataObject is null and p.dataObject.deleted = :deleted"
                ),
                @NamedQuery
                (
                    name = "Passport.findByNumber",
                    query = "select p from Passport p where p.passportNumber=:number"
                )
    }
)
public class Passport extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "passport_id", nullable = false)
    @Property(title="Passport Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger passportId;

    @Column(name = "parent_id", nullable = false)
    @Property(title="Passport Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @JoinColumn(name = "passport_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Passport Type")
    private DbResource passportType;

    @Column(name = "passport_number", nullable = false)
    @Property(title="Passport Number", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=16))
    private String passportNumber;

    @Column(name = "issue_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @Property(title="Issue Date")
    private Date issueDate;

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @Property(title="Expiration Date")
    private Date expirationDate;

    @JoinColumn(name = "issuer_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne
    @Property(title="Issuer", customDisplay="${issuer.organizationName}")
    private Organization issuer;

    @JoinColumn(name = "issuer_branch_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne
    @Property(title="Issuer Branch", customDisplay="${issuerBranch.addressName}")
    private Address issuerBranch;

    @Column(name = "additional_info")
    @Property(title="Additional Info", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=255))
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

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
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
        firePropertyChange("issueDate", this.issueDate, issueDate);
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        firePropertyChange("expirationDate", this.expirationDate, expirationDate);
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

    @Override
    public BigInteger getId() {
        return getPassportId();
    }

    @Override
    public void setId(BigInteger id) {
        setPassportId(id);
    }

}
