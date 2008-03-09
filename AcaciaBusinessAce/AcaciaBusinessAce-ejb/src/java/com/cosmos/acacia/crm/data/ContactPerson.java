package com.cosmos.acacia.crm.data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "contact_persons")
public class ContactPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "contact_person_id", nullable = false)
    private BigInteger contactPersonId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "position_type_id", referencedColumnName = "position_type_id")
    @ManyToOne
    private PositionType positionType;

    @JoinColumn(name = "contact_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person contact;

    @JoinColumn(name = "communication_contact_id", referencedColumnName = "communication_contact_id")
    @ManyToOne
    private CommunicationContact communicationContact;

    @JoinColumn(name = "contact_person_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;




    public ContactPerson() {
    }

    public ContactPerson(BigInteger contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public BigInteger getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(BigInteger contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public CommunicationContact getCommunicationContact() {
        return communicationContact;
    }

    public void setCommunicationContact(CommunicationContact communicationContact) {
        this.communicationContact = communicationContact;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Person getContact() {
        return contact;
    }

    public void setContact(Person contact) {
        this.contact = contact;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contactPersonId != null ? contactPersonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactPerson)) {
            return false;
        }
        ContactPerson other = (ContactPerson) object;
        if ((this.contactPersonId == null && other.contactPersonId != null) || (this.contactPersonId != null && !this.contactPersonId.equals(other.contactPersonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ContactPerson[contactPersonId=" + contactPersonId + "]";
    }

}
