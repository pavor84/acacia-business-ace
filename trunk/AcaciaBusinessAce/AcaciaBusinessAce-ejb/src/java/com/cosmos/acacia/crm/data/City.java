/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "cities")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "city_id", nullable = false)
    private Integer cityId;

    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country country;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_phone_code")
    private String cityPhoneCode;

    @Column(name = "description")
    private String description;


    public City() {
    }

    public City(Integer cityId) {
        this.cityId = cityId;
    }

    public City(Integer cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
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

}
