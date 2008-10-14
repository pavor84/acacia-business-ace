/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 *
 * @author miro
 */
@Entity
@Table(name = "products")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="product_type")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "Product.findByParentDataObjectIsNullAndDeleted",
                query = "select p from Product p" +
                        " where p.dataObject.parentDataObjectId is NULL" +
                        " and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "Product.findByParentDataObjectAndDeleted",
                query = "select p from Product p" +
                        " where p.dataObject.parentDataObjectId = :parentDataObjectId" +
                        " and p.dataObject.deleted = :deleted"
            )
    })
public abstract class Product
    extends DataObjectBean
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_id", nullable = false)
    @Property(title="Product Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger productId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public Product() {
    }

    public Product(BigInteger productId) {
        this.productId = productId;
    }

    public abstract String getProductName();
    public abstract void setProductName(String productName);

    public abstract String getProductCode();
    public abstract void setProductCode(String productCode);

    public abstract DbResource getMeasureUnit();
    public abstract void setMeasureUnit(DbResource measureUnit);

    public abstract BigDecimal getSalePrice();
    public abstract void setSalePrice(BigDecimal salePrice);

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append("[productId=");
        sb.append(productId);
        sb.append(", parentId=").append(parentId);
        sb.append(", productName=").append(getProductName());
        sb.append(", productCode=").append(getProductCode());
        DataObject dataObject = getDataObject();
        if(dataObject != null)
            sb.append(":v.").append(getDataObject().getDataObjectVersion());
        return sb.toString();
    }

    // DataObjectBean

    @Override
    public BigInteger getId() {
        return getProductId();
    }

    @Override
    public void setId(BigInteger id) {
        setProductId(id);
    }
    
}
