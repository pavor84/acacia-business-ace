/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "organizations")
@NamedQueries({@NamedQuery(name = "Organization.findByOrganizationId", query = "SELECT o FROM Organization o WHERE o.organizationId = :organizationId"), @NamedQuery(name = "Organization.findByParentId", query = "SELECT o FROM Organization o WHERE o.parentId = :parentId"), @NamedQuery(name = "Organization.findByOrganizationName", query = "SELECT o FROM Organization o WHERE o.organizationName = :organizationName"), @NamedQuery(name = "Organization.findByNickname", query = "SELECT o FROM Organization o WHERE o.nickname = :nickname"), @NamedQuery(name = "Organization.findByVatNumber", query = "SELECT o FROM Organization o WHERE o.vatNumber = :vatNumber"), @NamedQuery(name = "Organization.findByUniqueIdentifierCode", query = "SELECT o FROM Organization o WHERE o.uniqueIdentifierCode = :uniqueIdentifierCode"), @NamedQuery(name = "Organization.findByRegistrationDate", query = "SELECT o FROM Organization o WHERE o.registrationDate = :registrationDate"), @NamedQuery(name = "Organization.findByShareCapital", query = "SELECT o FROM Organization o WHERE o.shareCapital = :shareCapital"), @NamedQuery(name = "Organization.findByDescription", query = "SELECT o FROM Organization o WHERE o.description = :description")})
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;
    @Column(name = "parent_id")
    private Long parentId;
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
    private Address registrationAddressId;
    @JoinColumn(name = "administration_address_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address administrationAddressId;
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    @ManyToOne
    private Currency currencyId;
    @JoinColumn(name = "organization_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    @OneToMany(mappedBy = "registrationOrganizationId")
    private Collection<Organization> organizationCollection;
    @JoinColumn(name = "registration_organization_id", referencedColumnName = "organization_id")
    @ManyToOne
    private Organization registrationOrganizationId;
    @JoinColumn(name = "organization_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource organizationTypeId;

    public Organization() {
    }

    public Organization(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Organization(Long organizationId, String organizationName) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Address getRegistrationAddressId() {
        return registrationAddressId;
    }

    public void setRegistrationAddressId(Address registrationAddressId) {
        this.registrationAddressId = registrationAddressId;
    }

    public Address getAdministrationAddressId() {
        return administrationAddressId;
    }

    public void setAdministrationAddressId(Address administrationAddressId) {
        this.administrationAddressId = administrationAddressId;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Collection<Organization> getOrganizationCollection() {
        return organizationCollection;
    }

    public void setOrganizationCollection(Collection<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }

    public Organization getRegistrationOrganizationId() {
        return registrationOrganizationId;
    }

    public void setRegistrationOrganizationId(Organization registrationOrganizationId) {
        this.registrationOrganizationId = registrationOrganizationId;
    }

    public DbResource getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(DbResource organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organizationId != null ? organizationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.organizationId == null && other.organizationId != null) || (this.organizationId != null && !this.organizationId.equals(other.organizationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Organization[organizationId=" + organizationId + "]";
    }

}
