/**
 * 
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created	:	28.12.2008
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "pricelist_items")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "PricelistItem.findForParentAndDeleted",
                query = "select p from PricelistItem p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "PricelistItem.findById",
                query = "select p from PricelistItem p where p.dataObject.dataObjectId = :pricelistItemId"
            ) 
    })
public class PricelistItem extends DataObjectBean implements Serializable{
    @Id
    @Column(name = "item_id", nullable = false)
    private BigInteger itemId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pricelist)) {
            return false;
        }
        PricelistItem other = (PricelistItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Pricelist[itemId=" + itemId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return itemId;
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public void setId(BigInteger id) {
        setItemId(id);
    }

    public BigInteger getPricelistId() {
        return itemId;
    }

    public void setPricelistId(BigInteger itemId) {
        this.itemId = itemId;
    }

    public BigInteger getItemId() {
        return itemId;
    }

    public void setItemId(BigInteger itemId) {
        this.itemId = itemId;
    }
}
