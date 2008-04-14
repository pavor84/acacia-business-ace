package com.cosmos.acacia.crm.data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.cosmos.acacia.annotation.Property;
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
@Table(name = "communication_contacts")
@NamedQueries(
    {
        @NamedQuery
             (
                 name = "CommunicationContact.findByParentDataObjectAndDeleted",
                 query = "select cc from CommunicationContact cc where cc.dataObject.parentDataObject = :parentDataObject and cc.dataObject.deleted = :deleted"
             ),
       @NamedQuery
            (
                name = "CommunicationContact.findByParentDataObjectIsNullAndDeleted",
                query = "select cc from CommunicationContact cc where cc.dataObject.parentDataObject is null and cc.dataObject.deleted = :deleted"
             ),
        @NamedQuery
             (
                 name = "CommunicationContact.findByContactPerson",
                 query = "select cc from CommunicationContact cc where cc.contactPerson = :contactPerson and cc.dataObject.deleted = :deleted"
             )
    }
)
public class CommunicationContact extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "communication_contact_id", nullable = false)
    @Property(title="Communication Contact Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger communicationContactId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @JoinColumn(name = "communication_type_id", referencedColumnName = "resource_id", nullable=false)
    @ManyToOne
    @Property(title="Communication Type")
    private DbResource communicationType;

    @Column(name = "communication_value", nullable = false)
    @Property(title="Communication Value")
    private String communicationValue;

    @JoinColumn(name = "contact_person_id", referencedColumnName = "contact_person_id")
    @ManyToOne
    @Property(title="Contact Person")
    private ContactPerson contactPerson;

    @JoinColumn(name = "communication_contact_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public CommunicationContact() {
    }

    public CommunicationContact(BigInteger communicationContactId) {
        this.communicationContactId = communicationContactId;
    }

    public BigInteger getCommunicationContactId() {
        return communicationContactId;
    }

    public void setCommunicationContactId(BigInteger communicationContactId) {
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
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
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
    public BigInteger getId() {
        return getCommunicationContactId();
    }

    @Override
    public void setId(BigInteger id) {
        setCommunicationContactId(id);
    }

}
