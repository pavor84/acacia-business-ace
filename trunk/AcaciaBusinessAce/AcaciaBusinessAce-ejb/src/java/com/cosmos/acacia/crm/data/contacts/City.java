/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;
import java.io.Serializable;
import java.util.UUID;
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
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "cities")
@NamedQueries(
    {
        @NamedQuery
             (
                 name = "City.fetchAll",
                 query = "from City"
             ),
         @NamedQuery
             (
                 name = "City.findByCountry",
                 query = "select c from City c where c.country = :country"
             ),
         @NamedQuery
            (
                name = "City.findByNameAndCountry",
                query = "select c from City c where c.country = :country and c.cityName=:cityName"
            )
    }
)
public class City
        extends DataObjectBean
        implements Serializable, TextResource
{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "city_id", nullable = false)
    @Property(title="City Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID cityId;

    @Column(name = "city_name", nullable = false)
    @Property(title="Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=64))
    private String cityName;

    @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable=false)
    @ManyToOne
    @Property(title="Country", customDisplay="${country.countryName}")
    private Country country;

    @Column(name = "postal_code")
    @Property(title="Postal code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=8))
    private String postalCode;

    @Column(name = "city_code")
    @Property(title="City code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=3))
    private String cityCode;

    @Column(name = "city_phone_code")
    @Property(title="Phone code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=6))
    private String cityPhoneCode;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @PrimaryKeyJoinColumn
    @OneToOne
    private DataObject dataObject;

    public City() {
    }

    public City(UUID cityId) {
        this.cityId = cityId;
    }

    public City(UUID cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityPhoneCode() {
        return cityPhoneCode;
    }

    public void setCityPhoneCode(String cityPhoneCode) {
        this.cityPhoneCode = cityPhoneCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cityId != null ? cityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.cityId == null && other.cityId != null) || (this.cityId != null && !this.cityId.equals(other.cityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.City[cityId=" + cityId + "]";
    }

    public String toShortText() {
       return null;
    }

    public String toText() {
        return getCityName();
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
        return getCityId();
    }

    @Override
    public void setId(UUID id) {
        setCityId(id);
    }

    @Override
    public UUID getParentId() {
        return null; //TODO
    }

    @Override
    public void setParentId(UUID parentId) {
        //TODO
    }

    @Override
    public String getInfo() {
        return getCityName();
    }
    
}
