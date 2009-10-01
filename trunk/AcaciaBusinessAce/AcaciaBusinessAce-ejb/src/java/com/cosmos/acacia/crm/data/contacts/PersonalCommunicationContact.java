/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.crm.bl.contacts.ContactsServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "personal_communication_contacts", catalog = "acacia", schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contact_person_id", "communication_contact_id"})
    }
)
@NamedQueries({
    @NamedQuery(
        name = PersonalCommunicationContact.NQ_FIND_BY_CONTACT_PERSON,
        query = "SELECT t FROM PersonalCommunicationContact t" +
                " WHERE" +
                "  t.contactPerson = :contactPerson" +
                " ORDER BY t.communicationContact.communicationType, t.communicationContact.communicationValue"
    )
})
@Form(
    formContainers={
    },
    serviceClass=ContactsServiceRemote.class,
    entityFormClassName="",
    entityListFormClassName=""
)
public class PersonalCommunicationContact extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "PersonalCommunicationContact";
    public static final String NQ_FIND_BY_CONTACT_PERSON = CLASS_NAME + ".findByContactPerson";
    //
    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "personal_communication_contact_id", nullable = false)
    private UUID personalCommunicationContactId;

    @JoinColumn(name = "contact_person_id", referencedColumnName = "contact_person_id", nullable = false)
    @ManyToOne(optional = false)
    private ContactPerson contactPerson;

    @JoinColumn(name = "communication_contact_id", referencedColumnName = "communication_contact_id", nullable = false)
    @ManyToOne(optional = false)
    private CommunicationContact communicationContact;

    @JoinColumn(name = "personal_communication_contact_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public PersonalCommunicationContact() {
    }

    public PersonalCommunicationContact(UUID personalCommunicationContactId) {
        this.personalCommunicationContactId = personalCommunicationContactId;
    }

    public UUID getPersonalCommunicationContactId() {
        return personalCommunicationContactId;
    }

    public void setPersonalCommunicationContactId(UUID personalCommunicationContactId) {
        this.personalCommunicationContactId = personalCommunicationContactId;
    }

    public CommunicationContact getCommunicationContact() {
        return communicationContact;
    }

    public void setCommunicationContact(CommunicationContact communicationContact) {
        this.communicationContact = communicationContact;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
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
        return getPersonalCommunicationContactId();
    }

    @Override
    public void setId(UUID id) {
        setPersonalCommunicationContactId(id);
    }

    @Override
    public UUID getParentId() {
        if(contactPerson != null) {
            return contactPerson.getContactPersonId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return contactPerson + ":" + communicationContact;
    }
}
