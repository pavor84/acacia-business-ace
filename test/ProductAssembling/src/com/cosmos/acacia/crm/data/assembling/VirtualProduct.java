/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "virtual_products")
@NamedQueries({})
public class VirtualProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "product_type")
    private String productType;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "virtualProduct")
    private RealProduct realProduct;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "virtualProduct")
    private AssemblingSchema assemblingSchema;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "virtualProductId")
    private Collection<AssemblingSchemaItem> assemblingSchemaItemCollection;

    public VirtualProduct() {
    }

    public VirtualProduct(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public RealProduct getRealProduct() {
        return realProduct;
    }

    public void setRealProduct(RealProduct realProduct) {
        this.realProduct = realProduct;
    }

    public AssemblingSchema getAssemblingSchema() {
        return assemblingSchema;
    }

    public void setAssemblingSchema(AssemblingSchema assemblingSchema) {
        this.assemblingSchema = assemblingSchema;
    }

    public Collection<AssemblingSchemaItem> getAssemblingSchemaItemCollection() {
        return assemblingSchemaItemCollection;
    }

    public void setAssemblingSchemaItemCollection(Collection<AssemblingSchemaItem> assemblingSchemaItemCollection) {
        this.assemblingSchemaItemCollection = assemblingSchemaItemCollection;
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
        if (!(object instanceof VirtualProduct)) {
            return false;
        }
        VirtualProduct other = (VirtualProduct) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.VirtualProduct[productId=" + productId + "]";
    }

}
