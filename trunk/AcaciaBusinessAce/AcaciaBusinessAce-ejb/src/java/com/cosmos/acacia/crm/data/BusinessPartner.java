package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
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
    private BigInteger partnerId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @JoinColumn(name = "default_currency_id", referencedColumnName = "resource_id")
    @ManyToOne(optional=false)
    @Property(title="Default Currency", resourceDisplayInTable = ResourceDisplay.FullName)
    private DbResource defaultCurrency;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;

    /**
     * Getter for parentId
     * @return BigInteger
     */
    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    /**
     * Setter for parentId
     * @param parentId - BigInteger
     */
    @Override
    public void setParentId(BigInteger parentId) {
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
     * @return BigInteger
     */
    public BigInteger getPartnerId() {
        return partnerId;
    }

    /**
     * Setter for partnerId
     * @param partnerId - BigInteger
     */
    public void setPartnerId(BigInteger partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public BigInteger getId() {
        return getPartnerId();
    }

    @Override
    public void setId(BigInteger id) {
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
