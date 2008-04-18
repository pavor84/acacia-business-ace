/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.crm.data.SimpleProduct;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "real_products")
@DiscriminatorValue(value="RP")
@NamedQueries({})
public class RealProduct
    extends VirtualProduct
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "simple_product_id", referencedColumnName = "product_id")
    @ManyToOne
    private SimpleProduct simpleProduct;


    public RealProduct() {
    }

    public SimpleProduct getSimpleProduct() {
        return simpleProduct;
    }

    public void setSimpleProduct(SimpleProduct simpleProduct) {
        this.simpleProduct = simpleProduct;
    }


    @Override
    public String toString()
    {
        return "com.cosmos.acacia.crm.data.RealProduct[productId=" + getProductId() + "]";
    }

}
