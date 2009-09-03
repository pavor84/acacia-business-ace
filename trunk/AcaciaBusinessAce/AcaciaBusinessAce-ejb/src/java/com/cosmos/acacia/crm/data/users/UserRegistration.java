/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.BorderType;
import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.ComponentBorder;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.City;
import com.cosmos.acacia.crm.data.contacts.Country;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBPasswordField;
import com.cosmos.swingb.JBTextField;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Miro
 */
@Form(
    formContainers={
        @FormContainer(
            name=UserRegistration.USER_INFO,
            parentContainerName=DataObjectBean.PRIMARY_INFO,
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="User Details"
                ),
                componentConstraints="span, growx"
            )
        ),
        @FormContainer(
            name=UserRegistration.PERSONAL_INFO,
            parentContainerName=DataObjectBean.PRIMARY_INFO,
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Personal Details"
                ),
                componentConstraints="span, growx"
            )
        ),
        @FormContainer(
            name=UserRegistration.BIRTH_INFO,
            parentContainerName=UserRegistration.PERSONAL_INFO,
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Birth Details"
                ),
                componentConstraints="span, growx"
            ),
            //componentIndex=Integer.MAX_VALUE - 100
            componentIndex=EntityFormProcessor.CUSTOM_INDEX_VALUE + 400
        )
    },
    serviceClass = UsersServiceRemote.class
)
public class UserRegistration extends DataObjectBean implements Serializable {

    public static final String USER_INFO = "userInfo";
    public static final String PERSONAL_INFO = "personalInfo";
    public static final String BIRTH_INFO = "birthInfo";
    //
    private UUID userId;

    @Property(title="Email address",
        propertyValidator=@PropertyValidator(
            required=true,
            validationType=ValidationType.LENGTH, minLength=5, maxLength=64
        ),
        readOnly=true, editable=false,
        formComponentPair=@FormComponentPair(
            parentContainerName=USER_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Email:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String emailAddress;

    @Property(title="Username",
        propertyValidator=@PropertyValidator(
            required=true, 
            validationType=ValidationType.LENGTH, minLength=4, maxLength=32,
            regex="[^,]+", tooltip="username.tooltip"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=USER_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Username:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String username;

    @Property(title="Password",
        visible=false,
        propertyValidator=@PropertyValidator(
            required=true,
            validationType=ValidationType.LENGTH, minLength=6, maxLength=32,
            tooltip="password.tooltip"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=USER_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Password:"
            ),
            secondComponent=@Component(
                componentClass=JBPasswordField.class
            )
        )
    )
    private String password;

    @Property(title="Re-Password",
        visible=false,
        propertyValidator=@PropertyValidator(
            required=true,
            validationType=ValidationType.LENGTH, minLength=6, maxLength=32,
            tooltip="password.tooltip"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=USER_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Re-Password:"
            ),
            secondComponent=@Component(
                componentClass=JBPasswordField.class
            )
        )
    )
    private String rePassword;

    @Property(title="Default Currency",
        resourceDisplayInTable = ResourceDisplay.FullName,
        propertyValidator=@PropertyValidator(required=true),
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.Currency"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PERSONAL_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Currency:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource defaultCurrency;

    @Property(title = "Gender",
        resourceDisplayInTable=ResourceDisplay.ShortName,
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.Gender"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PERSONAL_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Gender:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource gender;

    @Property(title="Unique Id",
        formComponentPair=@FormComponentPair(
            parentContainerName=PERSONAL_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Unique Id:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String personalUniqueId;

    @Property(title="First Name",
        propertyValidator=@PropertyValidator(
            required=true,
            validationType=ValidationType.LENGTH, minLength=1, maxLength=24
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PERSONAL_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="First Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String firstName;

    @Property(title="Second Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=24),
        formComponentPair=@FormComponentPair(
            parentContainerName=PERSONAL_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Second Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String secondName;

    @Property(title="Last Name",
        propertyValidator=@PropertyValidator(
            required=true,
            validationType=ValidationType.LENGTH, minLength=1, maxLength=24
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PERSONAL_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Last Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String lastName;

    @Property(title="Extra Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=1, maxLength=24),
        formComponentPair=@FormComponentPair(
            parentContainerName=PERSONAL_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Extra Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String extraName;

    @Property(title="Birth Date",
        formComponentPair=@FormComponentPair(
            parentContainerName=BIRTH_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Birth Date:"
            ),
            secondComponent=@Component(
                componentClass=JBDatePicker.class
            )
        )
    )
    private Date birthDate;

    @Property(title = "Birth place country", customDisplay = "${country.countryName}",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.location.CountriesListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=BIRTH_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Country:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private Country birthPlaceCountry;

    @Property(title = "Birth place city", customDisplay = "${country.countryName}",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.location.CitiesListPanel",
            constructorParameters={
                @PropertyName(getter="birthPlaceCountry", setter="country")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=BIRTH_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="City:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private City birthPlaceCity;

    public UserRegistration(String emailAddress) {
        if(emailAddress == null) {
            throw new NullPointerException("The emailAddress can not be null.");
        }

        this.emailAddress = emailAddress;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public DbResource getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(DbResource defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
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

    public DbResource getGender() {
        return gender;
    }

    public void setGender(DbResource gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonalUniqueId() {
        return personalUniqueId;
    }

    public void setPersonalUniqueId(String personalUniqueId) {
        this.personalUniqueId = personalUniqueId;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public DataObject getDataObject() {
        return null;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public void setId(UUID id) {
    }

    @Override
    public UUID getParentId() {
        return null;
    }

    @Override
    public String getInfo() {
        return getUsername();
    }
}
