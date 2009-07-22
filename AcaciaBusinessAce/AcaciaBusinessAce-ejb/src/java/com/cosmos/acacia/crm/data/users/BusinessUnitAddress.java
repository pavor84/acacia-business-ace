/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
import java.awt.BorderLayout;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "business_unit_addresses", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"business_unit_id", "address_id"})
})
@NamedQueries({
    @NamedQuery(
        name = BusinessUnitAddress.NQ_FIND_ALL,
        query = "SELECT t FROM BusinessUnitAddress t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit"
    ),
    @NamedQuery(
        name = BusinessUnitAddress.NQ_FIND_BY_ADDRESS_TYPE,
        query = "SELECT t FROM BusinessUnitAddress t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit" +
                "  and t.addressType = :addressType"
    ),
    @NamedQuery(
        name = BusinessUnitAddress.NQ_DELETE_ALL,
        query = "DELETE FROM BusinessUnitAddress t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit"
    )
})
@Form(
    mainContainer=@FormContainer(
        name="mainTabbedPane",
        container=@Component(
            componentClass=JBTabbedPane.class
        )
    ),
    formContainers={
        @FormContainer(
            name="primaryInfo",
            title="Primary Info",
            container=@Component(
                componentClass=JBPanel.class
            )
        ),
        @FormContainer(
            name="notes",
            title="Notes",
            container=@Component(
                componentClass=JBPanel.class
            ),
            layout=@Layout(layoutClass=BorderLayout.class)
        )
    },
    serviceClass=UsersServiceRemote.class
)
public class BusinessUnitAddress extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "BusinessUnitAddress";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_ADDRESS_TYPE = CLASS_NAME + ".findByAddressType";
    public static final String NQ_DELETE_ALL = CLASS_NAME + ".deleteAll";

    @Id
    @Basic(optional = false)
    @Column(name = "business_unit_address_id", nullable = false, precision = 19, scale = 0)
    private BigInteger businessUnitAddressId;

    @JoinColumn(name = "address_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Address Type",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.BusinessUnitAddressType"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="primaryInfo",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Type:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource addressType;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    private BusinessUnit businessUnit;

    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Address",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.AddressListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="primaryInfo",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Address:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private Address address;

    @JoinColumn(name = "phone_id", referencedColumnName = "communication_contact_id")
    @ManyToOne
    @Property(title="Phone",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.CommunicationContactsListPanel",
            constructorParameters={
                @PropertyName(getter="address"),
                @PropertyName(getter="'Phone'", setter="communicationType")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="primaryInfo",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Phone:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private CommunicationContact phone;

    @JoinColumn(name = "fax_id", referencedColumnName = "communication_contact_id")
    @ManyToOne
    @Property(title="Fax",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.CommunicationContactsListPanel",
            constructorParameters={
                @PropertyName(getter="address"),
                @PropertyName(getter="'Fax'", setter="communicationType")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="primaryInfo",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Fax:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private CommunicationContact fax;

    @JoinColumn(name = "email_id", referencedColumnName = "communication_contact_id")
    @ManyToOne
    @Property(title="Email",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.CommunicationContactsListPanel",
            constructorParameters={
                @PropertyName(getter="address"),
                @PropertyName(getter="'Email'", setter="communicationType")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="primaryInfo",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Email:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private CommunicationContact email;

    @JoinColumn(name = "business_unit_address_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public BusinessUnitAddress() {
    }

    public BusinessUnitAddress(BigInteger businessUnitAddressId) {
        this.businessUnitAddressId = businessUnitAddressId;
    }

    public BigInteger getBusinessUnitAddressId() {
        return businessUnitAddressId;
    }

    public void setBusinessUnitAddressId(BigInteger businessUnitAddressId) {
        this.businessUnitAddressId = businessUnitAddressId;
    }

    public DbResource getAddressType() {
        return addressType;
    }

    public void setAddressType(DbResource addressType) {
        this.addressType = addressType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public CommunicationContact getEmail() {
        return email;
    }

    public void setEmail(CommunicationContact email) {
        this.email = email;
    }

    public CommunicationContact getFax() {
        return fax;
    }

    public void setFax(CommunicationContact fax) {
        this.fax = fax;
    }

    public CommunicationContact getPhone() {
        return phone;
    }

    public void setPhone(CommunicationContact phone) {
        this.phone = phone;
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
    public BigInteger getId() {
        return getBusinessUnitAddressId();
    }

    @Override
    public void setId(BigInteger id) {
        setBusinessUnitAddressId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(businessUnit != null) {
            return businessUnit.getId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        if(addressType != null) {
            sb.append(addressType.getEnumValue());
        }
        sb.append(":");
        String value;
        if(businessUnit != null && (value = businessUnit.getBusinessUnitName()) != null) {
            sb.append(value);
        }
        sb.append("@");
        if(address != null && (value = address.getAddressName()) != null) {
            sb.append(value);
        }

        return sb.toString();
    }
}
