/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "warehouses")
@NamedQueries({@NamedQuery(name = "Warehouse.findByWarehouseId", query = "SELECT w FROM Warehouse w WHERE w.warehouseId = :warehouseId"), @NamedQuery(name = "Warehouse.findByParentId", query = "SELECT w FROM Warehouse w WHERE w.parentId = :parentId"), @NamedQuery(name = "Warehouse.findByWarehousemanId", query = "SELECT w FROM Warehouse w WHERE w.warehousemanId = :warehousemanId"), @NamedQuery(name = "Warehouse.findByDescription", query = "SELECT w FROM Warehouse w WHERE w.description = :description")})
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "warehouseman_id")
    private Long warehousemanId;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address addressId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "warehouse")
    private Collection<WarehouseProduct> warehouseProductCollection;

    public Warehouse() {
    }

    public Warehouse(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getWarehousemanId() {
        return warehousemanId;
    }

    public void setWarehousemanId(Long warehousemanId) {
        this.warehousemanId = warehousemanId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    public Collection<WarehouseProduct> getWarehouseProductCollection() {
        return warehouseProductCollection;
    }

    public void setWarehouseProductCollection(Collection<WarehouseProduct> warehouseProductCollection) {
        this.warehouseProductCollection = warehouseProductCollection;
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

}
