/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "product_suppliers")
public class ProductSupplier
    extends DataObjectBean
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ProductSupplierPK productSupplierPK;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    @ManyToOne
    private Product product;

    /**
     * The Supplier can be both Person or Organization
     */
    @Column(name = "supplier_id", insertable=false, updatable=false)
    private BigInteger supplierId;
    @Transient
    private DataObjectBean supplier;

    @Column(name = "description")
    private String description;


    public ProductSupplier() {
    }

    public ProductSupplier(ProductSupplierPK productSupplierPK) {
        this.productSupplierPK = productSupplierPK;
    }

    public ProductSupplier(BigInteger productId, BigInteger supplierId) {
        this.productSupplierPK = new ProductSupplierPK(productId, supplierId);
    }

    public ProductSupplierPK getProductSupplierPK() {
        return productSupplierPK;
    }

    public void setProductSupplierPK(ProductSupplierPK productSupplierPK) {
        this.productSupplierPK = productSupplierPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public DataObjectBean getSupplier() {
        return supplier;
    }

    public void setSupplier(DataObjectBean supplier) {
        this.supplier = supplier;
        if(supplier != null)
            setSupplierId(supplier.getId());
        else
            setSupplierId(null);
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
        hash += (productSupplierPK != null ? productSupplierPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductSupplier)) {
            return false;
        }
        ProductSupplier other = (ProductSupplier) object;
        if ((this.productSupplierPK == null && other.productSupplierPK != null) || (this.productSupplierPK != null && !this.productSupplierPK.equals(other.productSupplierPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ProductSupplier[productSupplierPK=" + productSupplierPK + "]";
    }


    @Override
    public DataObject getDataObject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BigInteger getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setId(BigInteger id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BigInteger getParentId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setParentId(BigInteger parentId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
