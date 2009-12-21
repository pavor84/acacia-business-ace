package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import java.io.Serializable;
import java.util.UUID;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.contacts.ContactsServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;

/**
 * Created	:	21.03.2008
 * @author	Miroslav Nachev
 * @version $Id: $
 *
 */
@Entity
@Table(name = "business_partners", catalog = "acacia", schema = "public")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="discriminator_id")
@NamedQueries({
    @NamedQuery(
        name = BusinessPartner.NQ_FIND_ALL_BUSINESS_PARTNERS,
        query = "select t from BusinessPartner t" +
                " where" +
                "  t.parentBusinessPartnerId = :parentBusinessPartnerId" +
                "  and t.dataObject.deleted = :deleted" +
                " order by t.discriminatorId, t.businessPartnerId"
    ),
    @NamedQuery(
        name = BusinessPartner.NQ_FIND_ALL_BUSINESS_PARTNERS_BY_CLASSIFIER,
        query = "select distinct t1" +
                " from BusinessPartner t1, ClassifiedObject t2" +
                " where" +
                "  t1.businessPartnerId = t2.classifiedObjectPK.classifiedObjectId" +
                "  and t1.parentBusinessPartnerId = :parentBusinessPartnerId" +
                "  and t1.dataObject.deleted = :deleted" +
                "  and t2.classifiedObjectPK.classifierId = :classifierId" +
                " order by t1.discriminatorId, t1.businessPartnerId"
    )
})
@Form(
    formContainers={
        @FormContainer(
            name=BusinessPartner.ADDRESSES,
            title="Addresses",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=Address.class
        ),
        @FormContainer(
            name=BusinessPartner.POSITION_TYPES,
            title="Position Types",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=PositionType.class
        )
    },
    serviceClass=ContactsServiceRemote.class
)
public abstract class BusinessPartner extends DataObjectBean implements Serializable {

    public static final String PARTNER_ORGANIZATION = "O";
    public static final String PARTNER_PERSON = "P";
    //
    private static final String CLASS_NAME = "BusinessPartner";
    public static final String NQ_FIND_ALL_BUSINESS_PARTNERS =
            CLASS_NAME + ".findAllBusinessPartners";
    public static final String NQ_FIND_ALL_BUSINESS_PARTNERS_BY_CLASSIFIER =
            CLASS_NAME + ".findAllBusinessPartnersByClassifier";
    //
    public static final String ADDRESSES = "Addresses";
    public static final String POSITION_TYPES = "PositionTypes";
    //
    public static final int DEFAULT_CURRENCY_INDEX =
            Property.CUSTOM_INDEX_VALUE + Property.STEP_VALUE * 100;

    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "business_partner_id", nullable = false)
    @Property(title="Partner id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID businessPartnerId;

    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "parent_business_partner_id", nullable = false)
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID parentBusinessPartnerId;

    @Basic(optional = false)
    @Column(name = "discriminator_id", nullable = false, length = 2)
    protected String discriminatorId;

    @JoinColumn(name = "default_currency_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Default Currency",
        resourceDisplayInTable = ResourceDisplay.FullName,
        index=DEFAULT_CURRENCY_INDEX,
        propertyValidator=@PropertyValidator(required=true),
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.Currency"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Default Currency:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource defaultCurrency;

    @JoinColumn(name = "business_partner_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public BusinessPartner() {
        throw new UnsupportedOperationException();
    }

    public BusinessPartner(String discriminatorId) {
        this.discriminatorId = discriminatorId;
    }

    public BusinessPartner(String discriminatorId, UUID businessPartnerId) {
        this(discriminatorId);
        this.businessPartnerId = businessPartnerId;
    }

    @Override
    public UUID getParentId() {
        return getParentBusinessPartnerId();
    }

    @Override
    public void setParentId(UUID parentId) {
        setParentBusinessPartnerId(parentId);
    }

    public UUID getParentBusinessPartnerId() {
        return parentBusinessPartnerId;
    }

    public void setParentBusinessPartnerId(UUID parentBusinessPartnerId) {
        this.parentBusinessPartnerId = parentBusinessPartnerId;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public UUID getBusinessPartnerId() {
        return businessPartnerId;
    }

    public void setBusinessPartnerId(UUID businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }

    @Override
    public UUID getId() {
        return getBusinessPartnerId();
    }

    @Override
    public void setId(UUID id) {
        setBusinessPartnerId(id);
    }

    public DbResource getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(DbResource defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getDiscriminatorId() {
        return discriminatorId;
    }

    public void setDiscriminatorId(String discriminatorId) {
        this.discriminatorId = discriminatorId;
    }

    public abstract String getDisplayName();
    
    /**
     * Dispatch method that knows the possible sub classes and makes a decision what to
     * return.
     */
    public String getUniqueCode() {
        if (this instanceof Organization) {
            return ((Organization) this).getUniqueIdentifierCode();
        } else if (this instanceof Person) {
            return ((Person) this).getPersonalUniqueId();
        }
        return null;
    }
    
    /**
     * Dispatch method that knows the possible sub classes and makes a decision what to
     * return.
     */
    public Date getBirthOrRegistration() {
        if (this instanceof Organization) {
            return ((Organization) this).getRegistrationDate();
        } else if (this instanceof Person) {
            return ((Person) this).getBirthDate();
        }
        return null;
    }
}
