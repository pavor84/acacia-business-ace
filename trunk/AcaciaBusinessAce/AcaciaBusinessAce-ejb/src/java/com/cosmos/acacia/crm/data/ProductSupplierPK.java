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
public class ProductSupplierPK implements Serializable {

    @Column(name = "product_id", nullable = false)
    private BigInteger productId;

    @Column(name = "supplier_id", nullable = false)
    private BigInteger supplierId;

    public ProductSupplierPK() {
    }

    public ProductSupplierPK(BigInteger productId, BigInteger supplierId) {
        this.productId = productId;
        this.supplierId = supplierId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public BigInteger getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(BigInteger supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += productId.intValue();
        hash += supplierId.intValue();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductSupplierPK)) {
            return false;
        }
        ProductSupplierPK other = (ProductSupplierPK) object;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.supplierId != other.supplierId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ProductSupplierPK[productId=" + productId + ", supplierId=" + supplierId + "]";
    }

}
