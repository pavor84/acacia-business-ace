package com.cosmos.acacia.crm.data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.resource.TextResource;
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
@NamedQueries(
    {
        @NamedQuery
        (
           name = "ContactPerson.findByParentDataObjectAndDeleted",
           query = "select cp from ContactPerson cp where cp.dataObject.parentDataObjectId = :parentDataObjectId and cp.dataObject.deleted = :deleted"
        ),
        @NamedQuery
        (
           name = "ContactPerson.findByParentDataObjectIsNullAndDeleted",
           query = "select cp from ContactPerson cp where cp.dataObject.parentDataObjectId is null and cp.dataObject.deleted = :deleted"
        ),
        @NamedQuery
        (
            name = "ContactPerson.findByPersonAndTypeAndParentDataObject",
            query = "select cp from ContactPerson cp where cp.contact=:person and " +
                    "cp.dataObject.parentDataObjectId = :parentDataObjectId and " +
                    "cp.positionType=:positionType"
        )
    }
)
public class ContactPerson
        extends DataObjectBean
        implements TextResource, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "contact_person_id", nullable = false)
    @Property(title="Contact Person Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger contactPersonId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @JoinColumn(name = "position_type_id", referencedColumnName = "position_type_id")
    @ManyToOne
    @Property(title="Position Type", customDisplay="${positionType.positionTypeName}")
    private PositionType positionType;

    @JoinColumn(name = "contact_id")
    @ManyToOne
    @Property(title="Person", propertyValidator=
        @PropertyValidator(required=true),
        customDisplay="${contact.firstName} ${contact.secondName} ${contact.lastName} ${contact.extraName}")
    private Person contact;

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

    @Override
    public BigInteger getId() {
        return getContactPersonId();
    }

    @Override
    public void setId(BigInteger id) {
        contactPersonId = id;
    }

    @Override
    public String toShortText() {
        return getContact().toShortText();
    }

    @Override
    public String toText() {
        return null;
    }

    @Override
    public String getInfo() {
        return getContact().getDisplayName() + ": " + getPositionType().getPositionTypeName();
    }
}
