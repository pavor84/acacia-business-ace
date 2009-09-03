package com.cosmos.acacia.crm.data.contacts;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.util.UUID;
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
@Table(name = "communication_contacts")
@NamedQueries({
    @NamedQuery(
        name = CommunicationContact.NQ_FIND_ALL,
        query = "select cc from CommunicationContact cc" +
                " where" +
                "  cc.parentId = :parentId" +
                "  and cc.dataObject.deleted = :deleted"
    ),
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
    )
})
public class CommunicationContact extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "CommunicationContact";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_COMMUNICATION_TYPE = CLASS_NAME + ".findByCommunicationType";
    public static final String NQ_FIND_BY_CONTACT_PERSON = CLASS_NAME + ".findByContactPerson";

    @Id
    @Column(name = "communication_contact_id", nullable = false)
    @Property(title="Communication Contact Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID communicationContactId;

    @Column(name = "address_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID parentId;

    @JoinColumn(name = "communication_type_id", referencedColumnName = "resource_id", nullable=false)
    @ManyToOne
    @Property(title="Communication Type")
    private DbResource communicationType;

    @Column(name = "communication_value", nullable = false)
    @Property(title="Communication Value", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=64))
    private String communicationValue;

    @JoinColumn(name = "contact_person_id", referencedColumnName = "contact_person_id")
    @ManyToOne
    @Property(title="Contact Person",
        customDisplay="${contactPerson.contact.firstName} ${contactPerson.contact.secondName} ${contactPerson.contact.lastName} ${contactPerson.contact.extraName}")
    private ContactPerson contactPerson;

    @JoinColumn(name = "communication_contact_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public CommunicationContact() {
    }

    public CommunicationContact(UUID communicationContactId) {
        this.communicationContactId = communicationContactId;
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

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public UUID getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
        super.setParentId(parentId);
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
    public int hashCode() {
        int hash = 0;
        hash += (communicationContactId != null ? communicationContactId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommunicationContact)) {
            return false;
        }
        CommunicationContact other = (CommunicationContact) object;
        if ((this.communicationContactId == null && other.communicationContactId != null) || (this.communicationContactId != null && !this.communicationContactId.equals(other.communicationContactId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.CommunicationContact[communicationContactId=" + communicationContactId + "]";
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
}
