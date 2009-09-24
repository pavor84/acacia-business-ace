/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.location;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.bl.location.LocationsServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBTextField;
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
 * http://en.wikipedia.org/wiki/ISO_3166-1
 * @author Miro
 */
@Entity
@Table(name = "countries", catalog = "acacia", schema = "public"
/*
CREATE UNIQUE INDEX uix_countries_code_a2
  ON countries
  USING btree
  (lower(country_code_a2::text));
CREATE UNIQUE INDEX uix_countries_code_a3
  ON countries
  USING btree
  (lower(country_code_a3::text));
CREATE UNIQUE INDEX uix_countries_country_name
  ON countries
  USING btree
  (lower(country_name::text));
*/
)
@NamedQueries({
    @NamedQuery(
        name = Country.NQ_FIND_ALL,
        query = "select t from Country t" +
                " order by t.countryName"
    ),
    @NamedQuery(
        name = Country.NQ_COUNT_COUNTRIES,
        query = "select count(t) from Country t"
    ),
    @NamedQuery(
        name = Country.NQ_FIND_BY_NAME,
        query = "select t from Country t" +
                " where" +
                "  lower(t.countryName) = lower(:countryName)"
    ),
    @NamedQuery(
        name = Country.NQ_FIND_BY_CODE_A2,
        query = "select t from Country t" +
                " where" +
                "  lower(t.countryCodeA2) = lower(:countryCodeA2)"
    ),
    @NamedQuery(
        name = Country.NQ_FIND_BY_CODE_A3,
        query = "select t from Country t" +
                " where" +
                "  lower(t.countryCodeA3) = lower(:countryCodeA3)"
    )
})
@Form(
    serviceClass=LocationsServiceRemote.class,
    entityFormClassName="com.cosmos.acacia.crm.gui.location.CountryPanel",
    entityListFormClassName="com.cosmos.acacia.crm.gui.location.CountriesListPanel"
)
public class Country extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "Country";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_COUNT_COUNTRIES = CLASS_NAME + ".countCountries";
    public static final String NQ_FIND_BY_NAME = CLASS_NAME + ".findByName";
    public static final String NQ_FIND_BY_CODE_A2 = CLASS_NAME + ".findByCodeA2";
    public static final String NQ_FIND_BY_CODE_A3 = CLASS_NAME + ".findByCodeA3";
    //
    public static final String CODE_A2_BULGARIA = "BG";
    //
    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "country_id", nullable = false)
    @Property(title="Country Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID countryId;

    @Basic(optional = false)
    @Column(name = "country_name", nullable = false, length = 64)
    @Property(title = "Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=64),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String countryName;

    @Basic(optional = false)
    @Column(name = "country_code_a2", nullable = false, length = 2)
    @Property(title = "Country Code (A2)",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=2),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Code (A2):"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String countryCodeA2;

    @Basic(optional = false)
    @Column(name = "country_code_a3", nullable = false, length = 3)
    @Property(title = "Country Code (A3)",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=3),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Code (A3):"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String countryCodeA3;

    @Column(name = "country_code_n3", length = 3)
    @Property(title = "Country Code (N3)",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=3),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Code (N3):"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String countryCodeN3;

    @Column(name = "country_phone_code", length = 6)
    @Property(title = "Phone Code",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=6),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Phone Code:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String countryPhoneCode;

    @Basic(optional = false)
    @JoinColumn(name = "currency_id", nullable = false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title = "Currency",
        resourceDisplayInTable=ResourceDisplay.ShortName,
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.Currency"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Currency:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource currency;

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
