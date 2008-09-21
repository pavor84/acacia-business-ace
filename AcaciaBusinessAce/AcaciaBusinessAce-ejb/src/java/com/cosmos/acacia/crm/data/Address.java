/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "addresses")
@NamedQueries(
    {
        @NamedQuery
             (
                name = "Address.findByParentDataObjectAndDeleted",
                query = "select a from Address a where a.dataObject.parentDataObjectId = :parentDataObjectId and a.dataObject.deleted = :deleted"
             ),
        @NamedQuery
             (
                name = "Address.findByParentDataObjectIsNullAndDeleted",
                query = "select a from Address a where a.dataObject.parentDataObjectId is null and a.dataObject.deleted = :deleted"
              ),
        @NamedQuery
            (
                name = "Address.findByNameAndParentDataObject",
                query = "select a from Address a where a.addressName = :addressName and a.dataObject.parentDataObjectId = :parentDataObjectId"
            )
    }
)
public class Address
        extends DataObjectBean
        implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "address_id", nullable = false)
    @Property(title="Address Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger addressId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "address_name", nullable = false)
    @Property(title="Name",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, required = true))
    private String addressName;

    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    @Property(title="Country", customDisplay="${country.countryName}")
    private Country country;

    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    @ManyToOne
    @Property(title="City", customDisplay="${city.cityName}")
    private City city;

    @Column(name = "postal_code")
    @Property(title="Postcal Code")
    private String postalCode;

    @Column(name = "postal_address")
    @Property(title="Postal Address")
    private String postalAddress;

    @Column(name = "description")
    @Property(title="Description", hidden=true)
    private String description;


//    @JoinColumn(name = "user_group_id", referencedColumnName = "user_group_id")
//    @OneToOne
//    @Property(title="User Group", customDisplay="${userGroup.name}")
//    private UserGroup userGroup;

    @PrimaryKeyJoinColumn
    @OneToOne
    private DataObject dataObject;

    public Address() {
    }

    public Address(BigInteger addressId) {
        this.addressId = addressId;
    }

    public BigInteger getAddressId() {
        return addressId;
    }

    public void setAddressId(BigInteger addressId) {
        this.addressId = addressId;
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

//    public UserGroup getUserGroup() {
//        return userGroup;
//    }
//
//    public void setUserGroup(UserGroup userGroup) {
//        this.userGroup = userGroup;
//    }

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

    @Override
    public BigInteger getId() {
        return getAddressId();
    }

    @Override
    public void setId(BigInteger id) {
        setAddressId(id);
    }

    @Override
    public String toShortText() {
        return getAddressName();
    }

    @Override
    public String toText() {
        return null;
    }

    @Override
    public String getInfo() {
        return getAddressName();
    }

}
