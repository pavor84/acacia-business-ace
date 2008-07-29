/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

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

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "warehouses")
@NamedQueries({ 
    @NamedQuery
    ( 
        /**
         * Get all mask formats for a given name - at most one should exist
         * Parameters:
         * - name - the name of the pattern mask
         * - parentDataObjectId - not null
         */
        name = "Warehouse.findByAddressName",
        query = "select w from Warehouse w where " +
        		"w.dataObject.deleted = false and w.dataObject.parentDataObjectId = :parentDataObjectId "+
        		"order by w.address.addressName asc"
    ),
    @NamedQuery
    ( 
        /**
         * Get all mask formats for a given name - at most one should exist
         * Parameters:
         * - name - the name of the pattern mask
         * - parentDataObjectId - not null
         */
        name = "Warehouse.findByAddress",
        query = "select w from Warehouse w where " +
                "w.dataObject.deleted = false and w.dataObject.parentDataObjectId = :parentDataObjectId "+
        		"and w.address = :address"
    )
})
public class Warehouse extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "warehouse_id", nullable = false)
    private BigInteger warehouseId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @Property(title="Location", customDisplay="${address.addressName}", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address address;
    
    @Property(title="Person in charge", customDisplay="${warehouseman.firstName} ${warehouseman.lastName}", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "warehouseman_id")
    @ManyToOne
    private Person warehouseman;
    
    @Property(title="Description")
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "warehouse_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public Warehouse() {
    }

    public Warehouse(BigInteger warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigInteger getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(BigInteger warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public Person getWarehouseman() {
        return warehouseman;
    }

    public void setWarehouseman(Person warehouseman) {
        this.warehouseman = warehouseman;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (warehouseId != null ? warehouseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Warehouse)) {
            return false;
        }
        Warehouse other = (Warehouse) object;
        if ((this.warehouseId == null && other.warehouseId != null) || (this.warehouseId != null && !this.warehouseId.equals(other.warehouseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Warehouse[warehouseId=" + warehouseId + "]";
    }

    @Override
    public BigInteger getId() {
        return getWarehouseId();
    }

    @Override
    public void setId(BigInteger id) {
        setWarehouseId(id);
    }
    
    @Override
    public String getInfo() {
        return getAddress().getInfo();
    }
}
