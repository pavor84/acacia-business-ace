/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
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

import com.cosmos.resource.TextResource;

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
        @NamedQuery
            (
         	name = "Organization.findByParentDataObjectAndDeleted",
         	query = "select o from Organization o where o.dataObject.parentDataObject = :parentDataObject and o.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
    		name = "Organization.findByParentDataObjectIsNullAndDeleted",
    		query = "select o from Organization o where o.dataObject.parentDataObject is null and o.dataObject.deleted = :deleted"
            ),
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
    implements Serializable, TextResource
{

    private static final long serialVersionUID = 1L;
    
    @Column(name = "organization_name", nullable = false)
    @Property(title="Organization Name")
    private String organizationName;

    @Column(name = "nickname")
    @Property(title="Nickname")
    private String nickname;

    @Column(name = "vat_number")
    @Property(title="VAT Number")
    private String vatNumber;

    @Column(name = "unique_identifier_code")
    @Property(title="Unique Identifier Code")
    private String uniqueIdentifierCode;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Registration Date")
    private Date registrationDate;

    @Column(name = "share_capital")
    @Property(title="Share Capital")
    private BigDecimal shareCapital;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "registration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Registration Address", customDisplay="${registrationAddress.addressName}")
    private Address registrationAddress;

    @JoinColumn(name = "administration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Administration Address", customDisplay="${administrationAddress.addressName}")
    private Address administrationAddress;

    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Currency", customDisplay="${currency.text}")
    private DbResource currency;

    @JoinColumn(name = "registration_organization_id", referencedColumnName = "organization_id")
    @ManyToOne
    @Property(title="Registration Organization", customDisplay="${registrationOrganization.organizationName}")
    private Organization registrationOrganization;

    @JoinColumn(name = "organization_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Organization Type", customDisplay="${organizationType.text}")
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

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
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

    @Override
    public String getDisplayName() {
        return toShortText();
    }
    
    @Override
    public String toShortText() {
        return getOrganizationName();
    }
    
    @Override
    public String toText() {
        return null;
    }
}
