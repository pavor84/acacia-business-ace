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
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.bl.location.LocationsServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.resource.TextResource;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBTextField;
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
@Table(name = "cities"
/*
CREATE UNIQUE INDEX uix_cities_country_city_code
  ON cities
  USING btree
  (country_id, lower(city_code::text));
CREATE UNIQUE INDEX uix_cities_country_city_name
  ON cities
  USING btree
  (country_id, lower(city_name::text));
*/
)
@NamedQueries({
    @NamedQuery(
        name = City.NQ_FIND_ALL,
        query = "select t from City t" +
                " where" +
                "  t.country = :country" +
                " order by t.cityName"
    ),
    @NamedQuery(
        name = City.NQ_FIND_BY_CODE,
        query = "select t from City t" +
                " where" +
                "  t.country = :country" +
                "  and lower(t.cityCode) = lower(:cityCode)"
    ),
    @NamedQuery(
        name = City.NQ_COUNT_CITIES,
        query = "select count(t) from City t" +
                " where" +
                "  t.country = :country"
    )
})
@Form(
    serviceClass = LocationsServiceRemote.class
)
public class City extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "City";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_CODE = CLASS_NAME + ".findByCode";
    public static final String NQ_COUNT_CITIES = CLASS_NAME + ".countCities";
    //
    public static final String CODE_SOFIA = "SF";
    public static final String CODE_STARA_ZAGORA = "SZ";
    //
    @Id
    @Column(name = "city_id", nullable = false)
    @Property(title = "City Id", editable = false, readOnly = true, visible = false, hidden = true)
    @Type(type = "uuid")
    private UUID cityId;

    @Column(name = "city_name", nullable = false)
    @Property(title = "Name",
        propertyValidator = @PropertyValidator(validationType = ValidationType.LENGTH, maxLength = 64),
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
    private String cityName;

    @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable = false)
    @ManyToOne
    @Property(title = "Country", customDisplay = "${country.countryName}",
        readOnly=true, editable=false,
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.location.CountriesListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Country:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private Country country;

    @Column(name = "postal_code")
    @Property(title = "Postal code",
        propertyValidator = @PropertyValidator(validationType = ValidationType.LENGTH, maxLength = 8),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Postal Code:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String postalCode;

    @Column(name = "city_code", nullable = false)
    @Property(title = "City code",
        propertyValidator=@PropertyValidator(
            validationType = ValidationType.LENGTH, maxLength = 5, required=true
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="City Code:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String cityCode;

    @Column(name = "city_phone_code")
    @Property(title = "Phone code",
        propertyValidator=@PropertyValidator(validationType = ValidationType.LENGTH, maxLength = 6),
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
    private String cityPhoneCode;

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

    public City(Country country) {
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
