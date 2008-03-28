package com.cosmos.acacia.crm.data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "communication_contacts")
/*@NamedQueries(
	{
		@NamedQuery
         	(
         		name = "CommunicationContact.findByContactPersonId",
         		query = "select cc from CommunicationContact cc where cc.contactPersonId = :contactPersonId"
         	)
	}
)
*/
public class CommunicationContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "communication_contact_id", nullable = false)
    private BigInteger communicationContactId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "communication_type_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource communicationType;

    @Column(name = "communication_value", nullable = false)
    private String communicationValue;

    @OneToMany(mappedBy = "communicationContact")
    private List<ContactPerson> contactPersons;

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

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(DbResource communicationType) {
        this.communicationType = communicationType;
    }

    public List<ContactPerson> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
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

}
