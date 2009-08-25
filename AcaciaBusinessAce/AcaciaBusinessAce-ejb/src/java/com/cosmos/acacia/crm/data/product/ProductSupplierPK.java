/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.product;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Embeddable
public class ProductSupplierPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "product_id", nullable = false)
    @Type(type="uuid")
    private UUID productId;

    @Basic(optional = false)
    @Column(name = "supplier_id", nullable = false)
    @Type(type="uuid")
    private UUID supplierId;

    public ProductSupplierPK() {
    }

    public ProductSupplierPK(UUID productId, UUID supplierId) {
        this.productId = productId;
        this.supplierId = supplierId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
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
