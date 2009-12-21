/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.entity;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Passport;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.PositionType;
import com.cosmos.acacia.crm.data.location.City;
import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.PrivilegeCategory;
import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.BusinessUnitAddress;
import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBTextField;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Miro
 */
public enum AcaciaEntityAttributes implements EntityAttributes<Property> {

    /**
     * Java Object Data Types
     */
    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBTextField.class)
        )
    )
    StringEntity(String.class),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBDatePicker.class)
        )
    )
    DateEntity(Date.class),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBDecimalField.class)
        )
    )
    BigDecimalType(BigDecimal.class),

    @Property(title="Active",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(
                componentClass=JBCheckBox.class,
                componentConstraints="skip 1"
            )
        )
    )
    BooleanType(Boolean.class),

    /**
     * Java Primitive Data Types
     */
    @Property(title="Active",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(
                componentClass=JBCheckBox.class,
                componentConstraints="skip 1"
            )
        )
    )
    BooleanPrimitiveType(boolean.class),

    /**
     * Acacia Data Objects Types
     */
    @Property(title="Country")
    CountryTypeEntity(Country.class,
            "com.cosmos.acacia.crm.gui.location.CountryPanel",
            "com.cosmos.acacia.crm.gui.location.CountriesListPanel",
            "Countries",
            "countryName"),

    @Property(title="City")
    CityTypeEntity(City.class,
            "com.cosmos.acacia.crm.gui.location.CityPanel",
            "com.cosmos.acacia.crm.gui.location.CitiesListPanel",
            "Cities",
            "cityName"),

    @Property(title="Position Type",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Position Type:"
            ),
            secondComponent=@Component(componentClass=JBComboList.class)
        )
    )
    PositionTypeEntity(
            PositionType.class,
            "com.cosmos.acacia.crm.gui.contacts.PositionTypePanel",
            "com.cosmos.acacia.crm.gui.contacts.PositionTypeListPanel",
            "Position Types",
            "positionTypeName"),

    @Property(title="Person",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Person:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    PersonEntity(
        Person.class,
        "com.cosmos.acacia.crm.gui.contacts.PersonPanel",
        "com.cosmos.acacia.crm.gui.contacts.PersonListPanel",
        "Persons",
        "displayName"),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboList.class)
        )
    )
    OrganizationEntity(
        Organization.class,
        "com.cosmos.acacia.crm.gui.contacts.OrganizationPanel",
        "com.cosmos.acacia.crm.gui.contacts.OrganizationListPanel",
        "Organizations",
        "organizationName"
    ),

    @Property(title="Registration Address",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboList.class)
        )
    )
    AddressEntity(
        Address.class,
        "com.cosmos.acacia.crm.gui.contacts.AddressPanel",
        "com.cosmos.acacia.crm.gui.contacts.AddressListPanel",
        "Addresses",
        "addressName"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboList.class)
        )
    )
    PassportEntity(
        Passport.class,
        "com.cosmos.acacia.crm.gui.contacts.PassportPanel",
        "com.cosmos.acacia.crm.gui.contacts.PassportListPanel",
        "Passports",
        "toText"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        resourceDisplayInTable = ResourceDisplay.Name,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    UserEntity(
        User.class,
        "com.cosmos.acacia.crm.gui.users.UserPanel",
        "com.cosmos.acacia.crm.gui.users.UserListPanel",
        "Users",
        "userName"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        resourceDisplayInTable = ResourceDisplay.Name,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    UserOrganizationEntity(
        UserOrganization.class,
        "com.cosmos.acacia.crm.gui.users.UserOrganizationPanel",
        "com.cosmos.acacia.crm.gui.users.UserOrganizationListPanel",
        "Users",
        "user.userName"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    SecurityRoleEntity(
        SecurityRole.class,
        "com.cosmos.acacia.crm.gui.security.SecurityRolePanel",
        "com.cosmos.acacia.crm.gui.security.SecurityRoleListPanel",
        "Security Roles",
        "securityRoleName"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    PrivilegeCategoryEntity(
        PrivilegeCategory.class,
        "com.cosmos.acacia.crm.gui.security.PrivilegeCategoryPanel",
        "com.cosmos.acacia.crm.gui.security.PrivilegeCategoryListPanel",
        "Privilege Categories"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    PrivilegeRoleEntity(
        PrivilegeRole.class,
        "com.cosmos.acacia.crm.gui.security.PrivilegeRolePanel",
        "com.cosmos.acacia.crm.gui.security.PrivilegeRoleListPanel",
        "Privilege Roles"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    PrivilegeEntity(
        Privilege.class,
        "com.cosmos.acacia.crm.gui.security.PrivilegePanel",
        "com.cosmos.acacia.crm.gui.security.PrivilegeListPanel"
    ),

    @Property(
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    UserSecurityRoleEntity(
        UserSecurityRole.class,
        "com.cosmos.acacia.crm.gui.users.UserSecurityRolePanel",
        "com.cosmos.acacia.crm.gui.users.UserSecurityRoleListPanel",
        "User Security Roles",
        "info"
    ),

    @Property(
        title="Business Unit",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    BusinessUnitEntity(
        BusinessUnit.class,
        "com.cosmos.acacia.crm.gui.users.BusinessUnitPanel",
        "com.cosmos.acacia.crm.gui.users.BusinessUnitListPanel",
        "Business Units",
        "info"
    ),

    @Property(
        title="BU Address",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    BusinessUnitAddressEntity(
        BusinessUnitAddress.class,
        "com.cosmos.acacia.crm.gui.users.BusinessUnitAddressPanel",
        "com.cosmos.acacia.crm.gui.users.BusinessUnitAddressListPanel",
        "BU Addresses",
        "info"
    ),

    @Property(
        title="Job Title",
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    JobTitleEntity(
        JobTitle.class,
        "com.cosmos.acacia.crm.gui.users.JobTitlePanel",
        "com.cosmos.acacia.crm.gui.users.JobTitleListPanel",
        "Job Titles",
        "info"
    ),

    @Property(
        resourceDisplayInTable = ResourceDisplay.FullName,
        parentContainerName=DataObjectBean.PRIMARY_INFO,
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(componentClass=JBLabel.class),
            secondComponent=@Component(componentClass=JBComboBox.class)
        )
    )
    DbResourceEntity(DbResource.class),
    ;

    private AcaciaEntityAttributes(Class entityClass) {
        this(entityClass, null, null, null, null, null);
    }

    private AcaciaEntityAttributes(
            Class entityClass,
            String formClassName,
            String listFormClassName) {
        this(entityClass, formClassName, listFormClassName, entityClass.getSimpleName() + "s");
    }

    private AcaciaEntityAttributes(
            Class entityClass,
            String formClassName,
            String listFormClassName,
            String pluralTitle) {
        this(entityClass, formClassName, listFormClassName, pluralTitle, "info");
    }

    private AcaciaEntityAttributes(
            Class entityClass,
            String formClassName,
            String listFormClassName,
            String pluralTitle,
            String customDisplaySuffix) {
        this(entityClass, formClassName, listFormClassName,
                pluralTitle, pluralTitle + ":",
                customDisplaySuffix);
    }

    private AcaciaEntityAttributes(
            Class entityClass,
            String formClassName,
            String listFormClassName,
            String pluralTitle,
            String pluralLabelName,
            String customDisplaySuffix) {
        this.entityClass = entityClass;
        this.formClassName = formClassName;
        this.listFormClassName = listFormClassName;
        this.pluralTitle = pluralTitle;
        this.pluralLabelName = pluralLabelName;
        this.customDisplaySuffix = customDisplaySuffix;

        try {
            Field field = getClass().getField(name());
            this.property = field.getAnnotation(Property.class);
            if(property == null) {
                throw new NullPointerException("Can not find Property annotation for field '" + field.getName() + "'");
            }
        } catch(NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        } catch(SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
    //
    private Class entityClass;
    private String formClassName;
    private String listFormClassName;
    private String pluralTitle;
    private String pluralLabelName;
    private String customDisplaySuffix;
    private Property property;

    @Override
    public Class getEntityClass() {
        return entityClass;
    }

    @Override
    public String getFormClassName() {
        return formClassName;
    }

    @Override
    public String getListFormClassName() {
        return listFormClassName;
    }

    @Override
    public String getPluralLabelName() {
        return pluralLabelName;
    }

    @Override
    public String getPluralTitle() {
        return pluralTitle;
    }

    @Override
    public String getCustomDisplaySuffix() {
        return customDisplaySuffix;
    }

    @Override
    public Property getAnnotation() {
        return property;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name());
        sb.append("; annotation=").append(getAnnotation());
        sb.append(", formClassName=").append(getFormClassName());
        sb.append(", listFormClassName=").append(getListFormClassName());
        sb.append(", customDisplaySuffix=").append(getCustomDisplaySuffix());
        return sb.toString();
    }

    private static Map<String, EntityAttributes<Property>> entityAttributesMap;

    public static Map<String, EntityAttributes<Property>> getEntityAttributesMap() {
        if(entityAttributesMap == null) {
            entityAttributesMap = new TreeMap<String, EntityAttributes<Property>>();
            for(AcaciaEntityAttributes entityForm : values()) {
                entityAttributesMap.put(entityForm.getEntityClass().getName(), entityForm);
            }
        }

        return entityAttributesMap;
    }
}
