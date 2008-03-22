package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.cosmos.acacia.annotation.Property;

/**
 * Created	:	21.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Entity(name="business_partners")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class BusinessPartner extends DataObjectBean implements Serializable {
    
    @Id
    @Column(name = "partner_id", nullable = false)
    @Property(title="Partner id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger partnerId;
    
    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;
    
    @JoinColumn(name = "id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    
    /**
     * Getter for parentId
     * @return BigInteger
     */
    public BigInteger getParentId() {
        return parentId;
    }

    /**
     * Setter for parentId
     * @param parentId - BigInteger
     */
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    /**
     * Getter for dataObject
     * @return DataObject
     */
    public DataObject getDataObject() {
        return dataObject;
    }

    /**
     * Setter for dataObject
     * @param dataObject - DataObject
     */
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
}
