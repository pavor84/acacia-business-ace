package com.cosmos.acacia.crm.data.contacts;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.bl.contacts.ContactsServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
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
@Table(name = "communication_contacts", catalog = "acacia", schema = "public"
/*
CREATE UNIQUE INDEX uix_communication_contacts_parent_type_value_contact_person
  ON communication_contacts
  USING btree
  (address_id, communication_type_id, lower(communication_value::text));
*/
)
@NamedQueries({
    @NamedQuery(
        name = CommunicationContact.NQ_FIND_ALL,
        query = "select t from CommunicationContact t" +
                " where" +
                "  t.address = :address" +
                " ORDER BY t.communicationType, t.communicationValue"
    )/*,
    @NamedQuery(
        name = CommunicationContact.NQ_FIND_BY_COMMUNICATION_TYPE,
        query = "select cc from CommunicationContact cc" +
                " where" +
                "  cc.parentId = :parentId" +
                "  and cc.communicationType = :communicationType" +
                "  and cc.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
        name = CommunicationContact.NQ_FIND_BY_CONTACT_PERSON,
        query = "select cc from CommunicationContact cc" +
                " where" +
                "  cc.contactPerson = :contactPerson" +
                "  and cc.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
        name = "CommunicationContact.findByTypeAndContactPersonAndParentDataObject",
        query = "select cc from CommunicationContact cc" +
                " where " +
                "  cc.communicationType = :communicationType" +
                "  and cc.contactPerson = :contactPerson" +
                "  and cc.dataObject.parentDataObjectId = :parentDataObjectId"
    )*/
})
@Form(
    formContainers={
    },
    serviceClass=ContactsServiceRemote.class
)
public class CommunicationContact extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "CommunicationContact";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_COMMUNICATION_TYPE = CLASS_NAME + ".findByCommunicationType";
    public static final String NQ_FIND_BY_CONTACT_PERSON = CLASS_NAME + ".findByContactPerson";

    @Id
    @Basic(optional = false)
    @Column(name = "communication_contact_id", nullable = false)
    @Property(title="Communication Contact Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID communicationContactId;

    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Address", editable=false, readOnly=true, visible=false, hidden=true)
    private Address address;

    @JoinColumn(name = "communication_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Communication Type")
    private DbResource communicationType;

    @Basic(optional = false)
    @Column(name = "communication_value", nullable = false, length = 64)
    @Property(title="Communication Value", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=64))
    private String communicationValue;

    @JoinColumn(name = "communication_contact_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public CommunicationContact() {
    }

    public CommunicationContact(UUID communicationContactId) {
        this.communicationContactId = communicationContactId;
    }

    public CommunicationContact(Address address) {
        setAddress(address);
    }

    public UUID getCommunicationContactId() {
        return communicationContactId;
    }

    public void setCommunicationContactId(UUID communicationContactId) {
        this.communicationContactId = communicationContactId;
    }

    public String getCommunicationValue() {
        return communicationValue;
    }

    public void setCommunicationValue(String communicationValue) {
        this.communicationValue = communicationValue;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if(address != null) {
            setParentId(address.getAddressId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public UUID getParentId() {
        if(address != null) {
            return address.getAddressId();
        }

        return null;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(DbResource communicationType) {
        this.communicationType = communicationType;
    }

    @Override
    public UUID getId() {
        return getCommunicationContactId();
    }

    @Override
    public void setId(UUID id) {
        setCommunicationContactId(id);
    }

    @Override
    public String getInfo() {
        return getCommunicationType().getEnumValue().toString() + getCommunicationValue();
    }

    @Override
    public String toShortText() {
        return getInfo();
    }

    @Override
    public String toText() {
        return getInfo();
    }
}
