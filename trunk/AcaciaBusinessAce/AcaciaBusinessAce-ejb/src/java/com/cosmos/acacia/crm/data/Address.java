/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
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

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "addresses")
@NamedQueries({@NamedQuery(name = "Address.findByAddressId", query = "SELECT a FROM Address a WHERE a.addressId = :addressId"), @NamedQuery(name = "Address.findByParentId", query = "SELECT a FROM Address a WHERE a.parentId = :parentId"), @NamedQuery(name = "Address.findByAddressName", query = "SELECT a FROM Address a WHERE a.addressName = :addressName"), @NamedQuery(name = "Address.findByPostalCode", query = "SELECT a FROM Address a WHERE a.postalCode = :postalCode"), @NamedQuery(name = "Address.findByPostalAddress", query = "SELECT a FROM Address a WHERE a.postalAddress = :postalAddress"), @NamedQuery(name = "Address.findByDescription", query = "SELECT a FROM Address a WHERE a.description = :description")})
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "address_id", nullable = false)
    private Long addressId;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "address_name", nullable = false)
    private String addressName;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "postal_address")
    private String postalAddress;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addressId")
    private Collection<Warehouse> warehouseCollection;
    @OneToMany(mappedBy = "registrationAddressId")
    private Collection<Organization> organizationCollection;
    @OneToMany(mappedBy = "administrationAddressId")
    private Collection<Organization> organizationCollection1;
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    @ManyToOne
    private City cityId;
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country countryId;
    @JoinColumn(name = "address_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public Address() {
    }

    public Address(Long addressId) {
        this.addressId = addressId;
    }

    public Address(Long addressId, String addressName) {
        this.addressId = addressId;
        this.addressName = addressName;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Warehouse> getWarehouseCollection() {
        return warehouseCollection;
    }

    public void setWarehouseCollection(Collection<Warehouse> warehouseCollection) {
        this.warehouseCollection = warehouseCollection;
    }

    public Collection<Organization> getOrganizationCollection() {
        return organizationCollection;
    }

    public void setOrganizationCollection(Collection<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }

    public Collection<Organization> getOrganizationCollection1() {
        return organizationCollection1;
    }

    public void setOrganizationCollection1(Collection<Organization> organizationCollection1) {
        this.organizationCollection1 = organizationCollection1;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressId != null ? addressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressId == null && other.addressId != null) || (this.addressId != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Address[addressId=" + addressId + "]";
    }

}
