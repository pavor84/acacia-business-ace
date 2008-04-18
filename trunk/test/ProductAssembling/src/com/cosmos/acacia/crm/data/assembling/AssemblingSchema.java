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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_schemas")
@NamedQueries({})
public class AssemblingSchema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "schema_code", nullable = false)
    private String schemaCode;
    @Column(name = "schema_name", nullable = false)
    private String schemaName;
    @Column(name = "is_obsolete", nullable = false)
    private boolean isObsolete;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "category_id", referencedColumnName = "assembling_category_id")
    @ManyToOne
    private AssemblingCategory categoryId;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    @OneToOne
    private VirtualProduct virtualProduct;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assemblingSchemaId")
    private Collection<AssemblingSchemaItem> assemblingSchemaItemCollection;

    public AssemblingSchema() {
    }

    public AssemblingSchema(Long productId) {
        this.productId = productId;
    }

    public AssemblingSchema(Long productId, String schemaCode, String schemaName, boolean isObsolete) {
        this.productId = productId;
        this.schemaCode = schemaCode;
        this.schemaName = schemaName;
        this.isObsolete = isObsolete;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSchemaCode() {
        return schemaCode;
    }

    public void setSchemaCode(String schemaCode) {
        this.schemaCode = schemaCode;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public boolean getIsObsolete() {
        return isObsolete;
    }

    public void setIsObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssemblingCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(AssemblingCategory categoryId) {
        this.categoryId = categoryId;
    }

    public VirtualProduct getVirtualProduct() {
        return virtualProduct;
    }

    public void setVirtualProduct(VirtualProduct virtualProduct) {
        this.virtualProduct = virtualProduct;
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
        if (!(object instanceof AssemblingSchema)) {
            return false;
        }
        AssemblingSchema other = (AssemblingSchema) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.AssemblingSchema[productId=" + productId + "]";
    }

}
