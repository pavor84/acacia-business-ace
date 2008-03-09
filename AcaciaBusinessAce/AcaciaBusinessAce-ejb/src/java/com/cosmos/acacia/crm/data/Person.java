/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "person_id", nullable = false)
    private BigInteger personId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "extra_name")
    private String extraName;

    @Column(name = "personal_unique_id")
    private String personalUniqueId;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @JoinColumn(name = "birth_place_country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country birthPlaceCountry;

    @JoinColumn(name = "birth_place_city_id", referencedColumnName = "city_id")
    @ManyToOne
    private City birthPlaceCity;

    @JoinColumn(name = "gender_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource gender;

    @Column(name = "description")
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

}
