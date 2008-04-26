/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "simple_products")
@DiscriminatorValue(value="S")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "SimpleProduct.findByProductCode",
                query = "select t1 from SimpleProduct t1 where t1.productCode = :productCode"
            )
    })
public class SimpleProduct
    extends Product
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    public SimpleProduct()
    {
        super();
    }

    public SimpleProduct(Long productId)
    {
        super(productId);
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "test.data.SimpleProduct[productId=" + getProductId() + "]";
    }

}
