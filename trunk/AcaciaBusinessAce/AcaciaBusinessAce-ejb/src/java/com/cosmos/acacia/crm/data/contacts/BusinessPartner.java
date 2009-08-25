package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
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
import com.cosmos.acacia.annotation.ResourceDisplay;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import org.hibernate.annotations.Type;

/**
 * Created	:	21.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Entity
@Table(name="business_partners")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries(
    {
        /**
         * Get all not deleted business partners
         */
        @NamedQuery
        (
            name = "BusinessPartner.getAllNotDeleted",
            query = "select bp from BusinessPartner bp where bp.dataObject.deleted = false"
        ),
        /**
         * Get all for parent and deleted
         * Parameters:
         * - parentDataObjectId - may be null, then all partners are returned
         * - deleted - not null
         */
        @NamedQuery
            (
             name = "BusinessPartner.findForParentAndDeletedById",
             query = "select b from BusinessPartner b where " +
             		"(b.dataObject.parentDataObjectId = :parentDataObjectId or :parentDataObjectId is null) " +
             		"and b.dataObject.deleted = :deleted " +
             		"order by b.partnerId"
            ),
        @NamedQuery
            (
             name = "BusinessPartner.findByClassifier",
             query = "select distinct t1" +
                    " from BusinessPartner t1, ClassifiedObject t2" +
                    " where" +
                    "  t1.partnerId = t2.classifiedObjectPK.classifiedObjectId" +
                    "  and t1.parentId = :parentId" +
                    "  and t1.dataObject.deleted = :deleted" +
                    "  and t2.classifiedObjectPK.classifierId = :classifierId"
            ),
        @NamedQuery
        (
         name = "BusinessPartner.getAll",
         query = "select distinct t1" +
                " from BusinessPartner t1" +
                " where" +
                "  t1.parentId = :parentId" +
                "  and t1.dataObject.deleted = :deleted"
        )
    }
)
public abstract class BusinessPartner extends DataObjectBean implements Serializable {

    @Id
    @Column(name = "partner_id", nullable = false)
    @Property(title="Partner id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID partnerId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID parentId;

    @JoinColumn(name = "default_currency_id", referencedColumnName = "resource_id")
    @ManyToOne(optional=false)
    @Property(title="Default Currency",
        resourceDisplayInTable = ResourceDisplay.FullName, index=Integer.MAX_VALUE)
    private DbResource defaultCurrency;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;

    /**
     * Getter for parentId
     * @return UUID
     */
    @Override
    public UUID getParentId() {
        return parentId;
    }

    /**
     * Setter for parentId
     * @param parentId - UUID
     */
    @Override
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    /**
     * Getter for dataObject
     * @return DataObject
     */
    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    /**
     * Setter for dataObject
     * @param dataObject - DataObject
     */
    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    /**
     * Getter for partnerId
     * @return UUID
     */
    public UUID getPartnerId() {
        return partnerId;
    }

    /**
     * Setter for partnerId
     * @param partnerId - UUID
     */
    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public UUID getId() {
        return getPartnerId();
    }

    @Override
    public void setId(UUID id) {
        setPartnerId(id);
    }

    public DbResource getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(DbResource defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public abstract String getDisplayName();
    
    /**
     * Dispatch method that knows the possible sub classes and makes a decision what to
     * return.
     */
    public String getUniqueCode(){
        if ( this instanceof Organization ){
            return ((Organization)this).getUniqueIdentifierCode();
        }else if ( this instanceof Person ){
            return ((Person)this).getPersonalUniqueId();
        }
        return null;
    }
    
    /**
     * Dispatch method that knows the possible sub classes and makes a decision what to
     * return.
     */
    public Date getBirthOrRegistration(){
        if ( this instanceof Organization ){
            return ((Organization)this).getRegistrationDate();
        }else if ( this instanceof Person ){
            return ((Person)this).getBirthDate();
        }
        return null;
    }
}
