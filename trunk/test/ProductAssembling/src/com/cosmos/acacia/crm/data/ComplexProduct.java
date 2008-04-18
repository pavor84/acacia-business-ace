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
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "complex_products")
@DiscriminatorValue(value="C")
@NamedQueries({})
public class ComplexProduct
    extends Product
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "assembling_schema_info")
    private String assemblingSchemaInfo;


    public ComplexProduct()
    {
        super();
    }

    public ComplexProduct(Long productId)
    {
        super(productId);
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getAssemblingSchemaInfo() {
        return assemblingSchemaInfo;
    }

    public void setAssemblingSchemaInfo(String assemblingSchemaInfo) {
        this.assemblingSchemaInfo = assemblingSchemaInfo;
    }


    @Override
    public String toString() {
        return "test.data.ComplexProduct[productId=" + getProductId() + "]";
    }

}
