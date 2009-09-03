/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.resource.TextResource;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "addresses")
@NamedQueries({
    /*@NamedQuery(
        name = "Address.findByParentDataObjectAndDeleted",
        query = "select a from Address a where a.dataObject.parentDataObjectId = :parentDataObjectId and a.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
        name = "Address.findByParentDataObjectIsNullAndDeleted",
        query = "select a from Address a where a.dataObject.parentDataObjectId is null and a.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
        name = "Address.findByNameAndParentDataObject",
        query = "select a from Address a where a.addressName = :addressName and a.dataObject.parentDataObjectId = :parentDataObjectId"
    )*/
})
public class Address extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "address_id", nullable = false)
    @Property(title="Address Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID addressId;

    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "business_partner_id", nullable = false)
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID businessPartnerId;

    @Basic(optional = false)
    @Column(name = "address_name", nullable = false, length = 64)
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

    @Column(name = "postal_code", length = 16)
    @Property(title="Postcal Code")
    private String postalCode;

    @Column(name = "postal_address", length = 128)
    @Property(title="Postal Address")
    private String postalAddress;

    @JoinColumn(name = "address_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public Address() {
    }

    public Address(UUID addressId) {
        this.addressId = addressId;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public UUID getBusinessPartnerId() {
        return businessPartnerId;
    }

    public void setBusinessPartnerId(UUID businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }

    @Override
    public UUID getParentId() {
        return getBusinessPartnerId();
    }

    @Override
    public void setParentId(UUID parentId) {
        setBusinessPartnerId(parentId);
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

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public UUID getId() {
        return getAddressId();
    }

    @Override
    public void setId(UUID id) {
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
