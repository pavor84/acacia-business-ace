/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Miro
 */
public class ProductSelectionRow
    implements Serializable
{
    private ConstraintRow constraintRow;

    @Property(title="Min. Value")
    private Comparable minConstraint;

    @Property(title="Max. Value")
    private Comparable maxConstraint;

    @Property(title="Product Type")
    private String productType;

    @Property(title="Product Code")
    private String productCode;

    @Property(title="Product Name")
    private String productName;

    @Property(title="Quantity")
    private BigDecimal quantity;


    public ProductSelectionRow(ConstraintRow constraintRow)
    {
        this.constraintRow = constraintRow;
        ConstraintValues constraintValues = constraintRow.getConstraintValues();
        this.minConstraint = constraintValues.getMinConstraint();
        this.maxConstraint = constraintValues.getMaxConstraint();
        AssemblingSchemaItemValue itemValue =
            constraintRow.getCorrespondingObject();
        VirtualProduct product = itemValue.getVirtualProduct();
        this.productType = product.getProductType();
        this.productCode = product.getProductCode();
        this.productName = product.getProductName();
        this.quantity = itemValue.getQuantity();
    }

    public ConstraintRow getConstraintRow()
    {
        return constraintRow;
    }

    public Comparable getMaxConstraint()
    {
        return maxConstraint;
    }

    public void setMaxConstraint(Comparable maxConstraint)
    {
        this.maxConstraint = maxConstraint;
    }

    public Comparable getMinConstraint()
    {
        return minConstraint;
    }

    public void setMinConstraint(Comparable minConstraint)
    {
        this.minConstraint = minConstraint;
    }

    public BigDecimal getQuantity()
    {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductType()
    {
        return productType;
    }

    public void setProductType(String productType)
    {
        this.productType = productType;
    }

}
