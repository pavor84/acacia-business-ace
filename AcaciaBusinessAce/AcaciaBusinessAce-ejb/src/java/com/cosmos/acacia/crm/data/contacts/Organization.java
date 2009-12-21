/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
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
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.swingb.JBPanel;
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
                "  and t.businessPartnerId != t.parentBusinessPartnerId" +
                " order by t.organizationName"
    ),
    @NamedQuery(
        name = Organization.NQ_FIND_ALL_ORGANIZATIONS_BY_CLASSIFIERS,
        query = "select t from Organization t, ClassifiedObject t1" +
                " where" +
                "  t.parentBusinessPartnerId = :parentBusinessPartnerId" +
                "  and t.dataObject.deleted = :deleted" +
                "  and t.businessPartnerId != t.parentBusinessPartnerId" +
                "  and t.businessPartnerId = t1.classifiedObjectPK.classifiedObjectId" +
                "  and t1.classifier in (:classifiers)" +
                " order by t.organizationName"
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
@Form(
    formContainers={
        @FormContainer(
            name=Organization.BUSINESS_UNITS,
            title="Business Units",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=BusinessUnit.class,
            showCondition="isSystemOrganization(${entity})"
        )
    }
)
public class Organization extends BusinessPartner implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "Organization";
    public static final String NQ_FIND_ALL_ORGANIZATIONS = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_ALL_ORGANIZATIONS_BY_CLASSIFIERS = CLASS_NAME + ".findAllByClassifiers";
    public static final String NQ_FIND_ORGANIZATION_BY_NAME = CLASS_NAME + ".findByName";
    public static final String NQ_FIND_SYSTEM_ORGANIZATION = CLASS_NAME + ".findSystemOrganization";
    //
    public static final String BUSINESS_UNITS = "BusinessUnits";
    public static final String SECURITY_ROLES = "SecurityRoles";
    public static final String TEAMS = "Teams";
    //
    public static final int ACTIVE_INDEX = DEFAULT_CURRENCY_INDEX + Property.STEP_VALUE * 1;
    //
    @Basic(optional = false)
    @Column(name = "organization_name", nullable = false, length = 128)
    @Property(title="Organization Name",
        propertyValidator=@PropertyValidator(required=true, validationType=ValidationType.LENGTH, minLength=1, maxLength=128),
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Organization Name:"))
    )
    private String organizationName;

    @Column(name = "nickname", length = 32)
    @Property(title="Nickname",
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Nickname:"))
    )
    private String nickname;

    @Column(name = "vat_number", length = 32)
    @Property(title="VAT Number",
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="VAT Number:"))
    )
    private String vatNumber;

    @Column(name = "unique_identifier_code", length = 32)
    @Property(title="Unique Id Code",
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Unique Id Code:"))
    )
    private String uniqueIdentifierCode;

    @Column(name = "registration_code", length = 32)
    @Property(title="Registration Code",
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Registration Code:"))
    )
    private String registrationCode;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Registration Date",
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Registration Date:"))
    )
    private Date registrationDate;

    @JoinColumn(name = "registration_organization_id", referencedColumnName = "organization_id")
    @ManyToOne
    @Property(title="Registration Organization",
        selectableList=@SelectableList(
            constructorParameters={@PropertyName(getter="'RegistryAgency'", setter="classifier")}
        ),
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Registration Organization:"))
    )
    private Organization registrationOrganization;

    @JoinColumn(name = "registration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Registration Address",
        selectableList=@SelectableList(
            constructorParameters={@PropertyName(getter="${entity}", setter="businessPartner")}
        )
    )
    private Address registrationAddress;

    @JoinColumn(name = "organization_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Organization Type",
        selectableList=@SelectableList(className="com.cosmos.acacia.crm.enums.OrganizationType"),
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Organization Type:"))
    )
    private DbResource organizationType;

    @JoinColumn(name = "administration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Administration Address",
        selectableList=@SelectableList(
            constructorParameters={@PropertyName(getter="${entity}", setter="businessPartner")}
        ),
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Administration Address:"))
    )
    private Address administrationAddress;

    @JoinColumn(name = "share_capital_currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Capital Currency",
        selectableList=@SelectableList(className="com.cosmos.acacia.crm.enums.Currency"),
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Capital Currency:"))
    )
    private DbResource shareCapitalCurrency;

    @Column(name = "share_capital", precision = 19, scale = 4)
    @Property(title="Share Capital",
        formComponentPair=@FormComponentPair(firstComponent=@Component(text="Share Capital:"))
    )
    private BigDecimal shareCapital;

    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    @Property(title="Active",
        index=ACTIVE_INDEX,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(text="Active")
        )
    )
    private boolean active;

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

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public DbResource getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(DbResource organizationType) {
        this.organizationType = organizationType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("; organizationName=").append(organizationName);
        sb.append(", parentBusinessPartnerId=").append(parentBusinessPartnerId);

        return sb.toString();
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
