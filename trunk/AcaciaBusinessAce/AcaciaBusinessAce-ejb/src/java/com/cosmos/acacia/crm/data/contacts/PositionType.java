package com.cosmos.acacia.crm.data.contacts;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
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
@Table(name = "position_types", catalog = "acacia", schema = "public"
/*
CREATE UNIQUE INDEX uix_position_types
  ON position_types
  USING btree
  (business_partner_id, lower(position_type_name::text));
*/
)
@NamedQueries({
    @NamedQuery(
        name = PositionType.NQ_FIND_ALL,
        query = "SELECT t FROM PositionType t" +
                " WHERE" +
                "  t.businessPartner = :businessPartner" +
                " order by t.positionTypeName"
    )
})
@Form(
    serviceClass=ContactsServiceRemote.class
)
public class PositionType extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "PositionType";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    //
    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "position_type_id", nullable = false)
    @Property(title="Position Type Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID positionTypeId;

    @JoinColumn(name = "business_partner_id", referencedColumnName = "business_partner_id", nullable = false)
    @OneToOne(optional = false)
    //@Property(title="Business Partner")
    private BusinessPartner businessPartner;

    @Basic(optional = false)
    @Column(name = "position_type_name", nullable = false, length = 32)
    @Property(title="Position Type Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=32),
        formComponentPair=@FormComponentPair(secondComponent=@Component(componentConstraints="span"))
    )
    private String positionTypeName;

    @JoinColumn(name = "position_type_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public PositionType() {
    }

    public PositionType(UUID positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public PositionType(BusinessPartner businessPartner) {
        setBusinessPartner(businessPartner);
    }

    public UUID getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(UUID positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public String getPositionTypeName() {
        return positionTypeName;
    }

    public void setPositionTypeName(String positionTypeName) {
        this.positionTypeName = positionTypeName;
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
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public UUID getParentId() {
        if(businessPartner != null) {
            return businessPartner.getBusinessPartnerId();
        }

        return null;
    }

    @Override
    public UUID getId() {
        return getPositionTypeId();
    }

    @Override
    public void setId(UUID id) {
        setPositionTypeId(id);
    }

    @Override
    public String toShortText() {
        return getPositionTypeName();
    }

    @Override
    public String toText() {
        return getPositionTypeName();
    }

    @Override
    public String getInfo() {
        return getPositionTypeName();
    }
}
