package com.cosmos.acacia.crm.data.contacts;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.resource.TextResource;
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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "contact_persons",
    uniqueConstraints=@UniqueConstraint(
        columnNames={"address_id", "person_id"}
    )
)
@NamedQueries({
    /*@NamedQuery(
       name = "ContactPerson.findByParentDataObjectAndDeleted",
       query = "select cp from ContactPerson cp where cp.dataObject.parentDataObjectId = :parentDataObjectId and cp.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
       name = "ContactPerson.findByParentDataObjectIsNullAndDeleted",
       query = "select cp from ContactPerson cp where cp.dataObject.parentDataObjectId is null and cp.dataObject.deleted = :deleted"
    ),
    @NamedQuery(
        name = "ContactPerson.findByPersonAndTypeAndParentDataObject",
        query = "select cp from ContactPerson cp where cp.contact=:person and " +
                "cp.dataObject.parentDataObjectId = :parentDataObjectId and " +
                "cp.positionType=:positionType"
    ),
    @NamedQuery(
        name = "ContactPerson.findByPersonAndParentDataObject",
        query = "select cp from ContactPerson cp where cp.contact=:person" +
                " and cp.dataObject.parentDataObjectId=:parentDataObjectId"
    ),
    @NamedQuery(
        name = "ContactPerson.findByAddressIdAndPerson",
        query = "select cp from ContactPerson cp" +
                " where" +
                "  cp.parentId=:addressId" +
                "  and cp.contact=:person" +
                "  and cp.dataObject.deleted = false"
    ),
    @NamedQuery(
        name = "ContactPerson.getCassifiedFromBranch",
        query = "select t1.contact from ContactPerson t1, ClassifiedObject t2 "+
        " where" +
        "  t1.contact.partnerId = t2.classifiedObjectPK.classifiedObjectId" +
        "  and t1.parentId = :branchId" +
        "  and t1.contact.dataObject.deleted = false" +
        "  and t2.classifiedObjectPK.classifierId = :classifierId"
    )*/
})
public class ContactPerson extends DataObjectBean implements TextResource, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "contact_person_id", nullable = false)
    @Property(title="Contact Person Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID contactPersonId;

    @Column(name = "address_id")
    @Property(title="Address Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID addressId;

    @JoinColumn(name = "position_type_id", referencedColumnName = "position_type_id")
    @ManyToOne
    @Property(title="Position Type", customDisplay="${positionType.positionTypeName}")
    private PositionType positionType;

    @JoinColumn(name = "person_id")
    @ManyToOne
    @Property(title="Person", propertyValidator=
        @PropertyValidator(required=true),
        customDisplay="${contact.displayName}")
    private Person person;

    @JoinColumn(name = "contact_person_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public ContactPerson() {
    }

    public ContactPerson(UUID contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public UUID getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(UUID contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    @Override
    public UUID getParentId() {
        return addressId;
    }

    @Override
    public void setParentId(UUID parentId) {
        this.addressId = parentId;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    @Override
    public UUID getId() {
        return getContactPersonId();
    }

    @Override
    public void setId(UUID id) {
        setContactPersonId(id);
    }

    @Override
    public String toShortText() {
        return getPerson().toShortText();
    }

    @Override
    public String toText() {
        return null;
    }

    @Override
    public String getInfo() {
        return getPerson().getDisplayName() + ": " + getPositionType().getPositionTypeName();
    }
}
