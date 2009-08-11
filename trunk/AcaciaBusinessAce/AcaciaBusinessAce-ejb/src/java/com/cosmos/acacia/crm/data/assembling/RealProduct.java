/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.crm.data.product.ProductCategory;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "real_products")
@DiscriminatorValue(value="RP")
@PrimaryKeyJoinColumn(name="product_id",referencedColumnName="product_id")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "RealProduct.findBySimpleProduct",
                query = "select t1 from RealProduct t1" +
                    " where " +
                    "  t1.parentId = :parentId" +
                    "  and t1.dataObject.deleted = false" +
                    "  and t1.simpleProduct = :simpleProduct"
            ),
        @NamedQuery
            (
                name = "RealProduct.findNewSimpleProducts",
                query = "select t1 from SimpleProduct t1" +
                    " where t1.parentId = :parentId" +
                    " and t1.dataObject.deleted = false" +
                    " and t1.productId not in " +
                    "  (select t2.simpleProduct.productId from RealProduct t2" +
                    "   where " +
                    "    t2.parentId = :parentId" +
                    "    and t2.dataObject.deleted = false)"
            ),
        @NamedQuery
            (
                name = "RealProduct.findDeletedSimpleProducts",
                query = "select t1 from RealProduct t1" +
                    " where " +
                    " t1.parentId = :parentId" +
                    " and t1.dataObject.deleted = false" +
                    " and t1.simpleProduct.dataObject.deleted = true"
            )
    })
public class RealProduct
    extends VirtualProduct
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "simple_product_id", referencedColumnName = "product_id")
    @ManyToOne
    private SimpleProduct simpleProduct;


    public RealProduct()
    {
    }

    public RealProduct(SimpleProduct simpleProduct)
    {
        setSimpleProduct(simpleProduct);
    }

    public SimpleProduct getSimpleProduct() {
        return simpleProduct;
    }

    public void setSimpleProduct(SimpleProduct simpleProduct)
    {
        this.simpleProduct = simpleProduct;
        if(simpleProduct != null)
            setParentId(simpleProduct.getParentId());
        else
            setParentId(null);
    }


    @Override
    public String toString()
    {
        return "com.cosmos.acacia.crm.data.RealProduct[productId=" + getProductId() + "]";
    }

    @Override
    public String getCategoryName()
    {
        if(simpleProduct != null)
        {
            ProductCategory category;
            if((category = simpleProduct.getCategory()) != null)
                return category.getCategoryName();
        }

        return null;
    }

    @Override
    public String getProductCode()
    {
        if(simpleProduct != null)
            return simpleProduct.getProductCode();

        return null;
    }

    @Override
    public String getProductName()
    {
        if(simpleProduct != null)
            return simpleProduct.getProductName();

        return null;
    }

    @Override
    public String getProductType()
    {
        return "Product";
    }
    
    @Override
    public String getInfo()
    {
        return getProductName();
    }

}
