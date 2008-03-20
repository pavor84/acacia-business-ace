/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.beansbinding.validation.TextLengthValidator;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "persons")
@NamedQueries(
	{
		@NamedQuery
         	(
         		name = "Person.findByParentDataObjectAndDeleted",
         		query = "select p from Person p where p.dataObject.parentDataObject = :parentDataObject and p.dataObject.deleted = :deleted"
         	),
                @NamedQuery
                (
        		name = "Person.findByParentDataObjectIsNullAndDeleted",
        		query = "select p from Person p where p.dataObject.parentDataObject is null and p.dataObject.deleted = :deleted"
                )
	}
)
public class Person
        extends  DataObjectBean
        implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "person_id", nullable = false)
    @Property(title="Person Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger personId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "first_name", nullable = false)
    @Property(title="First Name",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2))
    private String firstName;

    @Column(name = "second_name")
    @Property(title="Second Name",
            propertyValidator=@PropertyValidator(validationType=ValidationType.REGEX, regex="[a-z]"))
    private String secondName;

    @Column(name = "last_name", nullable = false)
    @Property(title="Last Name")
    private String lastName;

    @Column(name = "extra_name")
    @Property(title="Extra Name")
    private String extraName;

    @Column(name = "personal_unique_id")
    @Property(title="Unique Id")
    private String personalUniqueId;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @Property(title="Birth Date")
    private Date birthDate;

    @JoinColumn(name = "birth_place_country_id", referencedColumnName = "country_id")
    @ManyToOne
    @Property(title="Country")
    private Country birthPlaceCountry;

    @JoinColumn(name = "birth_place_city_id", referencedColumnName = "city_id")
    @ManyToOne
    @Property(title="City")
    private City birthPlaceCity;

    @JoinColumn(name = "gender_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Gender")
    private DbResource gender;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "person_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public Person() {
    }

    public Person(BigInteger personId) {
        this.personId = personId;
    }

    public BigInteger getPersonId() {
        return personId;
    }

    public void setPersonId(BigInteger personId) {
        this.personId = personId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getGender() {
        return gender;
    }

    public void setGender(DbResource gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personId != null ? personId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personId == null && other.personId != null) || (this.personId != null && !this.personId.equals(other.personId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Person[personId=" + personId + "]";
    }

    @Override
    public BigInteger getId() {
        return getPersonId();
    }

    @Override
    public void setId(BigInteger id) {
        setPersonId(id);
    }

}
