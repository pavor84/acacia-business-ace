/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class WarehouseProductPK implements Serializable {
    @Column(name = "warehouse_id", nullable = false)
    private long warehouseId;
    @Column(name = "product_id", nullable = false)
    private long productId;

    public WarehouseProductPK() {
    }

    public WarehouseProductPK(long warehouseId, long productId) {
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) warehouseId;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WarehouseProductPK)) {
            return false;
        }
        WarehouseProductPK other = (WarehouseProductPK) object;
        if (this.warehouseId != other.warehouseId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.WarehouseProductPK[warehouseId=" + warehouseId + ", productId=" + productId + "]";
    }

}
