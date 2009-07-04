/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;
import com.cosmos.util.CloneableBean;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
         		name = "Country.fetchAll",
         		query = "from Country order by countryName"
         	),
                @NamedQuery
                (
                        name = "Country.findByCountryName",
                        query = "select c from Country c where c.countryName=:countryName"
                )
	}
)
public class Country implements TextResource, CloneableBean<Country>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="CountrySequenceGenerator", sequenceName="countries_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CountrySequenceGenerator")
    @Column(name = "country_id", nullable = false)
    @Property(title="Country Id", editable=false, readOnly=true, visible=false, hidden=true)
    private Integer countryId;

    @Column(name = "country_name", nullable = false)
    @Property(title = "Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=64))
    private String countryName;

    @Column(name = "country_code_a2")
    @Property(title = "Country Code (A2)", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=2))
    private String countryCodeA2;

    @Column(name = "country_code_a3")
    @Property(title = "Country Code (A3)", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=3))
    private String countryCodeA3;

    @Column(name = "country_code_n3")
    @Property(title = "Country Code (N3)", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=3))
    private String countryCodeN3;

    @Column(name = "country_phone_code")
    @Property(title = "Phone Code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=6))
    private String countryPhoneCode;

    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title = "Currency", resourceDisplayInTable=ResourceDisplay.ShortName)
    private DbResource currency;

    @Column(name = "description")
    @Property(title = "Description")
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

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currencyId) {
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

    public String toShortText() {
        return getCountryName();
    }

    public String toText() {
        return null;
    }

    @Override
    public Country clone() {
        try {
            return (Country) super.clone();
        } catch(CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
