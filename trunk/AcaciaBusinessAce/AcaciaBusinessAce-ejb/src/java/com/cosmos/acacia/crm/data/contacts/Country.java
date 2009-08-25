/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "countries", catalog = "acacia", schema = "public"
/*CREATE UNIQUE INDEX uix_countries_country_name
  ON countries
  USING btree
  (lower(country_name::text));*/
)
@NamedQueries({
    @NamedQuery(
        name = Country.NQ_FIND_ALL,
        query = "from Country order by countryName"
    ),
    @NamedQuery(
        name = Country.NQ_COUNT_COUNTRIES,
        query = "select count(t) from Country t"
    )
})
public class Country extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "Country";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_COUNT_COUNTRIES = CLASS_NAME + ".countCountries";

    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "country_id", nullable = false)
    @Property(title="Country Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID countryId;

    @Basic(optional = false)
    @Column(name = "country_name", nullable = false, length = 64)
    @Property(title = "Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=64))
    private String countryName;

    @Basic(optional = false)
    @Column(name = "country_code_a2", nullable = false, length = 2)
    @Property(title = "Country Code (A2)", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=2))
    private String countryCodeA2;

    @Basic(optional = false)
    @Column(name = "country_code_a3", nullable = false, length = 3)
    @Property(title = "Country Code (A3)", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=3))
    private String countryCodeA3;

    @Column(name = "country_code_n3", length = 3)
    @Property(title = "Country Code (N3)", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=3))
    private String countryCodeN3;

    @Column(name = "country_phone_code", length = 6)
    @Property(title = "Phone Code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=6))
    private String countryPhoneCode;

    @Basic(optional = false)
    @JoinColumn(name = "currency_id", nullable = false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title = "Currency", resourceDisplayInTable=ResourceDisplay.ShortName)
    private DbResource currency;

    @Column(name = "description", length = 2147483647)
    @Property(title = "Description")
    private String description;

    @JoinColumn(name = "country_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public Country() {
    }

    public Country(UUID countryId) {
        this.countryId = countryId;
    }

    public Country(UUID countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public UUID getCountryId() {
        return countryId;
    }

    public void setCountryId(UUID countryId) {
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
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public UUID getId() {
        return getCountryId();
    }

    @Override
    public void setId(UUID id) {
        setCountryId(id);
    }

    @Override
    public UUID getParentId() {
        if((dataObject != null)) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return getCountryName();
    }
}
