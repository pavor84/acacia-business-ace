/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class WarehouseProductPK implements Serializable {

    @Column(name = "warehouse_id", nullable = false)
    private BigInteger warehouseId;

    @Column(name = "product_id", nullable = false)
    private BigInteger productId;


    public WarehouseProductPK() {
    }

    public WarehouseProductPK(BigInteger warehouseId, BigInteger productId) {
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    public BigInteger getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(BigInteger warehouseId) {
        this.warehouseId = warehouseId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += warehouseId.intValue();
        hash += productId.intValue();
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
