/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.annotation.BorderType;
import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.ComponentBorder;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.crm.data.location.City;
import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.bl.contacts.ContactsServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.resource.TextResource;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
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
 *
 * @author Miro
 */
@Entity
@Table(name = "addresses"
/*
CREATE UNIQUE INDEX uix_addresses_business_partner_address_name
  ON addresses
  USING btree
  (business_partner_id, lower(address_name::text));
*/
)
@NamedQueries({
    @NamedQuery(
        name=Address.NQ_FIND_ALL,
        query = "select t from Address t" +
                " where" +
                "  t.businessPartner = :businessPartner" +
                " order by t.country, t.city, t.addressName"
    ),
    @NamedQuery(
        name=Address.NQ_FIND_ALL_BY_NAME_PREFIX,
        query = "select t from Address t" +
                " where" +
                "  t.businessPartner = :businessPartner" +
                "  and t.addressName like :namePrefix" +
                " order by t.addressName"
    )/*,
    @NamedQuery(
        name = "Address.findByParentDataObjectAndDeleted",
        query = "select a from Address a" +
                " where" +
                " a.dataObject.parentDataObjectId = :parentDataObjectId and a.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
        name = "Address.findByParentDataObjectIsNullAndDeleted",
        query = "select a from Address a where a.dataObject.parentDataObjectId is null and a.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
        name = "Address.findByNameAndParentDataObject",
        query = "select a from Address a where a.addressName = :addressName and a.dataObject.parentDataObjectId = :parentDataObjectId"
    )*/
})
@Form(
    formContainers={
        @FormContainer(
            name=DataObjectBean.PRIMARY_INFO,
            title="Primary Info",
            container=@Component(
                componentClass=JBPanel.class
            ),
            layout=@Layout(extraRowsConstraints={"", "fill, grow"}),
            componentIndex=1
        ),
        @FormContainer(
            name=Address.ADDRESS_DETAILS,
            title="Address Details",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Address Details"
                ),
                componentConstraints="span, growx"
            ),
            parentContainerName=DataObjectBean.PRIMARY_INFO
        ),
        @FormContainer(
            name=Address.CONTACT_PERSONS,
            title="Contact Persons",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Contact Persons"
                ),
                componentConstraints="spanx 2, grow"
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=ContactPerson.class,
            parentContainerName=DataObjectBean.PRIMARY_INFO
        ),
        @FormContainer(
            name=Address.COMMUNICATION_CONTACTS,
            title="Communication Contacts",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Communication Contacts"
                ),
                componentConstraints="spanx 2, grow"
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=CommunicationContact.class,
            parentContainerName=DataObjectBean.PRIMARY_INFO
        ),
        @FormContainer(
            name=Address.BANK_DETAILS,
            title="Bank Details",
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=BankDetail.class
        )
    },
    serviceClass=ContactsServiceRemote.class,
    entityFormClassName="com.cosmos.acacia.crm.gui.contacts.AddressPanel",
    entityListFormClassName="com.cosmos.acacia.crm.gui.contacts.AddressListPanel"
)
public class Address extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "Address";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_ALL_BY_NAME_PREFIX = CLASS_NAME + ".findAllByNamePrefix";
    //
    public static final String NAME_HEADQUARTER = "Headquarter";
    public static final String NAME_POST_ADDRESS = "Post Address";
    public static final String NAME_REGISTRATION_ADDRESS = "Registration Address";
    public static final String NAME_BILL_TO_ADDRESS = "Bill To";
    public static final String NAME_SHIP_TO_ADDRESS = "Ship To";
    public static final String NAME_OFFICE_ADDRESS = "Office";
    public static final String NAME_HOME_ADDRESS = "Home";
    //
    public static final String ADDRESS_DETAILS = "AddressDetails";
    public static final String CONTACT_PERSONS = "ContactPersons";
    public static final String COMMUNICATION_CONTACTS = "CommunicationContacts";
    public static final String BANK_DETAILS = "BankDetails";
    public static final String POSTAL_ADDRESS = "PostalAddress";
    //
    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "address_id", nullable = false)
    @Property(title="Address Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID addressId;

    @JoinColumn(name = "business_partner_id", referencedColumnName = "business_partner_id", nullable = false)
    @ManyToOne
    @Type(type="uuid")
    @Property(title="Parent", editable=false, readOnly=true, visible=false, hidden=true)
    private BusinessPartner businessPartner;

    @Basic(optional = false)
    @Column(name = "address_name", nullable = false, length = 64)
    @Property(title="Name",
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=64, required = true),
        formComponentPair=@FormComponentPair(
            parentContainerName=ADDRESS_DETAILS,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String addressName;

    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    @Property(title = "Country", customDisplay = "${country.countryName}",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.location.CountriesListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=ADDRESS_DETAILS,
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

    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    @ManyToOne
    @Property(title="City", customDisplay="${city.cityName}",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.location.CitiesListPanel",
            constructorParameters={
                @PropertyName(getter="country")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=ADDRESS_DETAILS,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="City:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private City city;

    @Column(name = "postal_code", length = 16)
    @Property(title="Postcal Code",
        formComponentPair=@FormComponentPair(
            parentContainerName=ADDRESS_DETAILS,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Postcal Code:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String postalCode;

    @Column(name = "postal_address", length = 128)
    @Property(title="Postal Address",
        formComponentPair=@FormComponentPair(
            parentContainerName=ADDRESS_DETAILS,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Postal Address:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class,
                componentConstraints="span, growx"
            )
        )
    )
    private String postalAddress;

    @JoinColumn(name = "address_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public Address() {
    }

    public Address(BusinessPartner businessPartner) {
        setBusinessPartner(businessPartner);
    }

    public Address(UUID addressId) {
        this.addressId = addressId;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
        if(businessPartner != null) {
            setParentId(businessPartner.getBusinessPartnerId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public UUID getParentId() {
        if(businessPartner != null) {
            return businessPartner.getBusinessPartnerId();
        }

        return null;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        return getAddressId();
    }

    @Override
    public void setId(UUID id) {
        setAddressId(id);
    }

    @Override
    public String toShortText() {
        return getAddressName();
    }

    @Override
    public String toText() {
        return null;
    }

    @Override
    public String getInfo() {
        return getAddressName();
    }

}
