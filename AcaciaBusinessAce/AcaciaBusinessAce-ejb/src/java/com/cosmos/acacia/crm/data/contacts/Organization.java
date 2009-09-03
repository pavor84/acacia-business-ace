/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.contacts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
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
import javax.persistence.Transient;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.resource.TextResource;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "organizations", catalog = "acacia", schema = "public"
/*CREATE UNIQUE INDEX uix_organizations_parent_business_partner_organization_name
ON organizations
USING btree
(parent_business_partner_id, lower(organization_name::text));*/
)
@DiscriminatorValue(value=BusinessPartner.PARTNER_ORGANIZATION)
@PrimaryKeyJoinColumn(name="organization_id")
@NamedQueries({
    @NamedQuery(
        name = Organization.NQ_FIND_ALL_ORGANIZATIONS,
        query = "select t from Organization t" +
                " where" +
                "  t.parentBusinessPartnerId = :parentBusinessPartnerId" +
                "  and t.dataObject.deleted = :deleted" +
                "  and not t.businessPartnerId = t.parentBusinessPartnerId"
    ),
    @NamedQuery(
        name = Organization.NQ_FIND_ORGANIZATION_BY_NAME,
        query = "select t from Organization t" +
                " where" +
                "  t.parentBusinessPartnerId = :parentBusinessPartnerId" +
                "  and t.dataObject.deleted = :deleted" +
                "  and lower(t.organizationName) = lower(:organizationName)"
    ),
    @NamedQuery(
        name = Organization.NQ_FIND_SYSTEM_ORGANIZATION,
        query = "select t from Organization t" +
                " where" +
                "  t.businessPartnerId = t.parentBusinessPartnerId"
    )
})
public class Organization extends BusinessPartner implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "Organization";
    public static final String NQ_FIND_ALL_ORGANIZATIONS = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_ORGANIZATION_BY_NAME = CLASS_NAME + ".findByName";
    public static final String NQ_FIND_SYSTEM_ORGANIZATION = CLASS_NAME + ".findSystemOrganization";

    @Basic(optional = false)
    @Column(name = "organization_name", nullable = false, length = 128)
    @Property(title="Organization Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=128))
    private String organizationName;

    @Column(name = "nickname", length = 32)
    @Property(title="Nickname")
    private String nickname;

    @Column(name = "vat_number", length = 32)
    @Property(title="VAT Number")
    private String vatNumber;

    @Column(name = "unique_identifier_code", length = 32)
    @Property(title="Unique Id Code")
    private String uniqueIdentifierCode;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Registration Date")
    private Date registrationDate;

    @Column(name = "share_capital", precision = 19, scale = 4)
    @Property(title="Share Capital")
    private BigDecimal shareCapital;

    @JoinColumn(name = "registration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Registration Address", customDisplay="${registrationAddress.addressName}")
    private Address registrationAddress;

    @JoinColumn(name = "administration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Administration Address", customDisplay="${administrationAddress.addressName}")
    private Address administrationAddress;

    @JoinColumn(name = "share_capital_currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Capital Currency", resourceDisplayInTable = ResourceDisplay.FullName)
    private DbResource shareCapitalCurrency;

    @JoinColumn(name = "registration_organization_id", referencedColumnName = "organization_id")
    @ManyToOne
    @Property(title="Registration Organization", customDisplay="${registrationOrganization.organizationName}")
    private Organization registrationOrganization;

    @JoinColumn(name = "organization_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Organization Type")
    private DbResource organizationType;

    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "parent_business_partner_id", nullable = false)
    private UUID parentBusinessPartnerId;

    @Transient
    private boolean isOwn;

    public Organization() {
        super(PARTNER_ORGANIZATION);
    }

    public Organization(UUID organizationId) {
        super(PARTNER_ORGANIZATION, organizationId);
    }

    public Organization(UUID organizationId, String organizationName) {
        this(organizationId);
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        String oldValue = this.organizationName;
        this.organizationName = organizationName;
        firePropertyChange("organizationName", oldValue, organizationName);
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

    public DbResource getShareCapitalCurrency() {
        return shareCapitalCurrency;
    }

    public void setShareCapitalCurrency(DbResource shareCapitalCurrency) {
        this.shareCapitalCurrency = shareCapitalCurrency;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public UUID getParentBusinessPartnerId() {
        UUID id;
        if((id = super.getParentBusinessPartnerId()) == null && parentBusinessPartnerId != null) {
            super.setParentBusinessPartnerId(parentBusinessPartnerId);
        } else if(id != null && parentBusinessPartnerId == null) {
            parentBusinessPartnerId = id;
        }

        return super.getParentBusinessPartnerId();
    }

    @Override
    public void setParentBusinessPartnerId(UUID parentBusinessPartnerId) {
        this.parentBusinessPartnerId = parentBusinessPartnerId;
        super.setParentBusinessPartnerId(parentBusinessPartnerId);
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

    @Override
    public String getInfo() {
        return getOrganizationName();
    }

    public boolean isOwn() {
        return isOwn;
    }

    public void setOwn(boolean isOwn) {
        this.isOwn = isOwn;
    }
}
