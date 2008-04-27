/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import java.io.Serializable;
import javax.persistence.Column;
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

    @JoinColumn(name = "applied_schema_id", referencedColumnName = "product_id")
    @ManyToOne
    private AssemblingSchema appliedSchema;


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

    public AssemblingSchema getAppliedSchema() {
        return appliedSchema;
    }

    public void setAppliedSchema(AssemblingSchema appliedSchema) {
        this.appliedSchema = appliedSchema;
    }

    @Override
    public String toString() {
        return "test.data.ComplexProduct[productId=" + getProductId() + "]";
    }

}
