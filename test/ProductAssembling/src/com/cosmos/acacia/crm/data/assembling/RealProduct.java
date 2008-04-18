/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.crm.data.SimpleProduct;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "real_products")
@NamedQueries({})
public class RealProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @JoinColumn(name = "simple_product_id", referencedColumnName = "product_id")
    @ManyToOne
    private SimpleProduct simpleProductId;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    @OneToOne
    private VirtualProduct virtualProduct;

    public RealProduct() {
    }

    public RealProduct(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public SimpleProduct getSimpleProductId() {
        return simpleProductId;
    }

    public void setSimpleProductId(SimpleProduct simpleProductId) {
        this.simpleProductId = simpleProductId;
    }

    public VirtualProduct getVirtualProduct() {
        return virtualProduct;
    }

    public void setVirtualProduct(VirtualProduct virtualProduct) {
        this.virtualProduct = virtualProduct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RealProduct)) {
            return false;
        }
        RealProduct other = (RealProduct) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.RealProduct[productId=" + productId + "]";
    }

}
