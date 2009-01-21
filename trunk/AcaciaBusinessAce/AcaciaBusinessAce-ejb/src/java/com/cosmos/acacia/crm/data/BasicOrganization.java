/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

/**
 *
 * @author Miro
 */
public class BasicOrganization {

    @Property(title="Organization Name",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH, minLength=1, maxLength=120, required=true))
    private String organizationName;

    @Property(title="Nickname")
    private String nickname;

    @Property(title="VAT Number",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH, minLength=1, maxLength=16, required=true))
    private String vatNumber;

    @Property(title="Unique Identifier Code")
    private String uniqueIdentifierCode;

    @Property(title="City", customDisplay="${city.cityName}")
    private City city;

    @Property(title="Postcal Code")
    private String postalCode;

    @Property(title="Postal Address",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH, minLength=1, maxLength=128, required=true))
    private String postalAddress;

    @Property(title="First Name",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH, minLength=1, maxLength=24, required=true))
    private String firstName;

    @Property(title="Second Name",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH, minLength=1, maxLength=24))
    private String secondName;

    @Property(title="Last Name",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH, minLength=1, maxLength=24, required=true))
    private String lastName;

    @Property(title="Extra Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=24))
    private String extraName;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getExtraName() {
        return extraName;
    }

    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUniqueIdentifierCode() {
        return uniqueIdentifierCode;
    }

    public void setUniqueIdentifierCode(String uniqueIdentifierCode) {
        this.uniqueIdentifierCode = uniqueIdentifierCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

}
