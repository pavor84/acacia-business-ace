/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class ProductSupplierPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "product_id", nullable = false)
    private BigInteger productId;

    @Basic(optional = false)
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductSupplierPK other = (ProductSupplierPK) obj;
        if (productId == null || !productId.equals(other.productId)) {
            return false;
        }
        if (supplierId == null || !supplierId.equals(other.supplierId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.productId != null ? this.productId.hashCode() : 0);
        hash = 53 * hash + (this.supplierId != null ? this.supplierId.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
        return "ProductSupplierPK[productId=" + productId + ", supplierId=" + supplierId + "]";
    }
}
