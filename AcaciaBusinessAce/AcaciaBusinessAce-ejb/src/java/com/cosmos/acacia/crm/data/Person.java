/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@NamedQueries({@NamedQuery(name = "Person.findByPersonId", query = "SELECT p FROM Person p WHERE p.personId = :personId"), @NamedQuery(name = "Person.findByParentId", query = "SELECT p FROM Person p WHERE p.parentId = :parentId"), @NamedQuery(name = "Person.findByFirstName", query = "SELECT p FROM Person p WHERE p.firstName = :firstName"), @NamedQuery(name = "Person.findBySecondName", query = "SELECT p FROM Person p WHERE p.secondName = :secondName"), @NamedQuery(name = "Person.findByLastName", query = "SELECT p FROM Person p WHERE p.lastName = :lastName"), @NamedQuery(name = "Person.findByExtraName", query = "SELECT p FROM Person p WHERE p.extraName = :extraName"), @NamedQuery(name = "Person.findByPersonalUniqueId", query = "SELECT p FROM Person p WHERE p.personalUniqueId = :personalUniqueId"), @NamedQuery(name = "Person.findByBirthDate", query = "SELECT p FROM Person p WHERE p.birthDate = :birthDate"), @NamedQuery(name = "Person.findByDescription", query = "SELECT p FROM Person p WHERE p.description = :description")})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "person_id", nullable = false)
    private Long personId;
    @Column(name = "parent_id")
    private Long parentId;
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
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "supplierContactId")
    private Collection<PurchaseOrder> purchaseOrderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creatorId")
    private Collection<PurchaseOrder> purchaseOrderCollection1;
    @OneToMany(mappedBy = "senderId")
    private Collection<PurchaseOrder> purchaseOrderCollection2;
    @JoinColumn(name = "birth_place_city_id", referencedColumnName = "city_id")
    @ManyToOne
    private City birthPlaceCityId;
    @JoinColumn(name = "birth_place_country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country birthPlaceCountryId;
    @JoinColumn(name = "person_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    @JoinColumn(name = "gender_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource genderId;

    public Person() {
    }

    public Person(Long personId) {
        this.personId = personId;
    }

    public Person(Long personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
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

    public Collection<PurchaseOrder> getPurchaseOrderCollection() {
        return purchaseOrderCollection;
    }

    public void setPurchaseOrderCollection(Collection<PurchaseOrder> purchaseOrderCollection) {
        this.purchaseOrderCollection = purchaseOrderCollection;
    }

    public Collection<PurchaseOrder> getPurchaseOrderCollection1() {
        return purchaseOrderCollection1;
    }

    public void setPurchaseOrderCollection1(Collection<PurchaseOrder> purchaseOrderCollection1) {
        this.purchaseOrderCollection1 = purchaseOrderCollection1;
    }

    public Collection<PurchaseOrder> getPurchaseOrderCollection2() {
        return purchaseOrderCollection2;
    }

    public void setPurchaseOrderCollection2(Collection<PurchaseOrder> purchaseOrderCollection2) {
        this.purchaseOrderCollection2 = purchaseOrderCollection2;
    }

    public City getBirthPlaceCityId() {
        return birthPlaceCityId;
    }

    public void setBirthPlaceCityId(City birthPlaceCityId) {
        this.birthPlaceCityId = birthPlaceCityId;
    }

    public Country getBirthPlaceCountryId() {
        return birthPlaceCountryId;
    }

    public void setBirthPlaceCountryId(Country birthPlaceCountryId) {
        this.birthPlaceCountryId = birthPlaceCountryId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getGenderId() {
        return genderId;
    }

    public void setGenderId(DbResource genderId) {
        this.genderId = genderId;
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
