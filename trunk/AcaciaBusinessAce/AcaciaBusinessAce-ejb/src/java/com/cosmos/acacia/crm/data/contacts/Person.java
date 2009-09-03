/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.contacts;

import java.io.Serializable;
import java.util.UUID;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.resource.TextResource;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "persons", catalog = "acacia", schema = "public"
/*
CONSTRAINT check_persons_country_peronal_unique_id CHECK(
 personal_unique_id IS NULL
 OR
 personal_unique_id IS NOT NULL AND birth_place_country_id IS NOT NULL
)
CREATE UNIQUE INDEX uix_persons_personal_unuque_id
 ON persons
 USING btree(
  parent_business_partner_id,
  birth_place_country_id,
  lower(personal_unique_id::text)
);
CREATE UNIQUE INDEX uix_persons_names_birth_date_city
 ON persons
 USING btree(
  parent_business_partner_id,
  lower(first_name::text),
  lower(last_name::text),
  lower(second_name::text),
  lower(extra_name::text),
  birth_date,
  birth_place_city_id
);
*/
)
@PrimaryKeyJoinColumn(name="person_id")
@DiscriminatorValue(value=BusinessPartner.PARTNER_PERSON)
@NamedQueries({
    @NamedQuery(
        name = Person.NQ_FIND_ALL_PERSONS,
        query = "select t from Person t" +
                " where" +
                "  t.parentBusinessPartnerId = :parentBusinessPartnerId" +
                "  and t.dataObject.deleted = :deleted"
    )
})
public class Person extends BusinessPartner implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "Person";
    public static final String NQ_FIND_ALL_PERSONS = CLASS_NAME + ".findAll";

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 24)
    @Property(title="First Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=24))
    private String firstName;

    @Column(name = "second_name", length = 24)
    @Property(title="Second Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=24))
    private String secondName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 24)
    @Property(title="Last Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=24))
    private String lastName;

    @Column(name = "extra_name", length = 24)
    @Property(title="Extra Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=24))
    private String extraName;

    @Column(name = "personal_unique_id", length = 16)
    @Property(title="Unique Id")
    private String personalUniqueId;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Birth Date")
    private Date birthDate;

    @JoinColumn(name = "birth_place_country_id", referencedColumnName = "country_id")
    @ManyToOne
    @Property(title="Country", customDisplay="${birthPlaceCountry.countryName}")
    private Country birthPlaceCountry;

    @JoinColumn(name = "birth_place_city_id", referencedColumnName = "city_id")
    @ManyToOne
    @Property(title="City", customDisplay="${birthPlaceCity.cityName}")
    private City birthPlaceCity;

    @JoinColumn(name = "gender_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Gender")
    private DbResource gender;

    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "parent_business_partner_id", nullable = false)
    private UUID parentBusinessPartnerId;

    public Person() {
        super(PARTNER_PERSON);
    }

    public Person(UUID personId) {
        super(PARTNER_PERSON, personId);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExtraName() {
        return extraName;
    }

    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }

    public String getPersonalUniqueId() {
        return personalUniqueId;
    }

    public void setPersonalUniqueId(String personalUniqueId) {
        this.personalUniqueId = personalUniqueId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public City getBirthPlaceCity() {
        return birthPlaceCity;
    }

    public void setBirthPlaceCity(City birthPlaceCity) {
        this.birthPlaceCity = birthPlaceCity;
    }

    public Country getBirthPlaceCountry() {
        return birthPlaceCountry;
    }

    public void setBirthPlaceCountry(Country birthPlaceCountry) {
        this.birthPlaceCountry = birthPlaceCountry;
    }

    public DbResource getGender() {
        return gender;
    }

    public void setGender(DbResource gender) {
        this.gender = gender;
    }

    @Override
    public UUID getParentBusinessPartnerId() {
        UUID id;
        if((id = super.getParentBusinessPartnerId()) == null && parentBusinessPartnerId != null) {
            super.setParentBusinessPartnerId(parentBusinessPartnerId);
        } else if(id != null && parentBusinessPartnerId == null) {
            parentBusinessPartnerId = id;
        }

        return super.getParentBusinessPartnerId();
    }

    @Override
    public void setParentBusinessPartnerId(UUID parentBusinessPartnerId) {
        this.parentBusinessPartnerId = parentBusinessPartnerId;
        super.setParentBusinessPartnerId(parentBusinessPartnerId);
    }

    @Override
    public String toShortText() {
        return toText();
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder(4 * 24 + 3);
        if(firstName != null) {
            sb.append(firstName);
        }
        if(sb.length() > 0) {
            sb.append(' ');
        }
        if(secondName != null) {
            sb.append(secondName);
        }
        if(sb.length() > 0) {
            sb.append(' ');
        }
        if(lastName != null) {
            sb.append(lastName);
        }
        if(sb.length() > 0) {
            sb.append(' ');
        }
        if(extraName != null) {
            sb.append(extraName);
        }

        return sb.toString();
    }

    @Transient
    private String displayName;

    public void setDisplayName(String displayName) {
    }

    @Override
    public String getDisplayName() {
        return toText();
    }

    @Override
    public String getInfo() {
        return toText();
    }
}
