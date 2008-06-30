/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "virtual_products")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="product_type")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "VirtualProduct.findByParentId",
                query = "select t1 from VirtualProduct t1" +
                    " where" +
                    "  t1.parentId = :parentId" +
                    "  and t1.dataObject.deleted = false"
            )
    })
public abstract class VirtualProduct
    extends DataObjectBean
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_id", nullable = false)
    private BigInteger productId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;

    @Transient
    @Property(title="Product Type", editable=false, readOnly=true)
    private String productType;

    @Transient
    @Property(title="Category Name", editable=false, readOnly=true)
    private String categoryName;

    @Transient
    @Property(title="Product Code", editable=false, readOnly=true)
    private String productCode;

    @Transient
    @Property(title="Product Name", editable=false, readOnly=true)
    private String productName;


    public VirtualProduct()
    {
    }

    public VirtualProduct(BigInteger productId) {
        this.productId = productId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public abstract String getProductType();
    public void setProductType(String productType) {}

    public abstract String getCategoryName();
    public void setCategoryName(String categoryName) {}

    public abstract String getProductCode();
    public void setProductCode(String productCode) {}

    public abstract String getProductName();
    public void setProductName(String productName) {}

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
        return "com.cosmos.acacia.crm.data.VirtualProduct[productId=" + productId + "]";
    }


    @Override
    public BigInteger getParentId()
    {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId)
    {
        this.parentId = parentId;
    }

    @Override
    public BigInteger getId()
    {
        return getProductId();
    }

    @Override
    public void setId(BigInteger id)
    {
        setProductId(id);
    }

    @Override
    public DataObject getDataObject()
    {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject)
    {
        this.dataObject = dataObject;
    }
}
