/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "organizations")
/** Will duplicate the primary key from superclass with this name in the 'organizations' table */
@PrimaryKeyJoinColumn(name="organization_id")
@NamedQueries(
    {
        /**
         * All not deleted organizations.
         */
        @NamedQuery
            (
            name = "Organization.getAllNotDeleted",
            query = "select o from Organization o where o.dataObject.deleted = false"
            )
    }
)
public class Organization
    extends BusinessPartner
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "unique_identifier_code")
    private String uniqueIdentifierCode;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column(name = "share_capital")
    private BigDecimal shareCapital;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "registration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address registrationAddress;

    @JoinColumn(name = "administration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address administrationAddress;

    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    @ManyToOne
    private Currency currency;

    @JoinColumn(name = "registration_organization_id", referencedColumnName = "organization_id")
    @ManyToOne
    private Organization registrationOrganization;

    @JoinColumn(name = "organization_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource organizationType;

    public Organization() {
    }

    public Organization(BigInteger id) {
        setPartnerId(id);
    }

    public Organization(BigInteger organizationId, String organizationName) {
        setPartnerId(organizationId);
        setOrganizationName(organizationName);
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getUniqueIdentifierCode() {
        return uniqueIdentifierCode;
    }

    public void setUniqueIdentifierCode(String uniqueIdentifierCode) {
        this.uniqueIdentifierCode = uniqueIdentifierCode;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public BigDecimal getShareCapital() {
        return shareCapital;
    }

    public void setShareCapital(BigDecimal shareCapital) {
        this.shareCapital = shareCapital;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(Address registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public Address getAdministrationAddress() {
        return administrationAddress;
    }

    public void setAdministrationAddress(Address administrationAddress) {
        this.administrationAddress = administrationAddress;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Organization getRegistrationOrganization() {
        return registrationOrganization;
    }

    public void setRegistrationOrganization(Organization registrationOrganization) {
        this.registrationOrganization = registrationOrganization;
    }

    public DbResource getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(DbResource organizationType) {
        this.organizationType = organizationType;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPartnerId() != null ? getPartnerId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.getPartnerId() == null && other.getPartnerId() != null) || (this.getPartnerId() != null && !this.getPartnerId().equals(other.getPartnerId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Organization[organizationId=" + getPartnerId() + "]";
    }
}
