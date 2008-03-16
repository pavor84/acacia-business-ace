/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "countries")
@NamedQueries(
	{
		@NamedQuery
         	(
         		name = "Country.findAll",
         		query = "from Country "
         	)
	}
)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "country_id", nullable = false)
    private Integer countryId;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "country_code_a2")
    private String countryCodeA2;

    @Column(name = "country_code_a3")
    private String countryCodeA3;

    @Column(name = "country_code_n3")
    private String countryCodeN3;

    @Column(name = "country_phone_code")
    private String countryPhoneCode;

    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    @ManyToOne
    private Currency currency;

    @Column(name = "description")
    private String description;


    public Country() {
    }

    public Country(Integer countryId) {
        this.countryId = countryId;
    }

    public Country(Integer countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCodeA2() {
        return countryCodeA2;
    }

    public void setCountryCodeA2(String countryCodeA2) {
        this.countryCodeA2 = countryCodeA2;
    }

    public String getCountryCodeA3() {
        return countryCodeA3;
    }

    public void setCountryCodeA3(String countryCodeA3) {
        this.countryCodeA3 = countryCodeA3;
    }

    public String getCountryCodeN3() {
        return countryCodeN3;
    }

    public void setCountryCodeN3(String countryCodeN3) {
        this.countryCodeN3 = countryCodeN3;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currencyId) {
        this.currency = currencyId;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryId != null ? countryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.countryId == null && other.countryId != null) || (this.countryId != null && !this.countryId.equals(other.countryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Country[countryId=" + countryId + "]";
    }

}
