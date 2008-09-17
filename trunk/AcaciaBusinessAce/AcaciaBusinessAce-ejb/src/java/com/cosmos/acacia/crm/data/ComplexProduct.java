/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Column(name = "product_name", nullable = false)
    private String productName;

    @JoinColumn(name = "measure_unit_id", nullable=false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @Column(name = "sale_price", nullable = false)
    @Property(title="Sales Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal salePrice;

    @JoinColumn(name = "applied_schema_id", referencedColumnName = "product_id")
    @ManyToOne
    private AssemblingSchema appliedSchema;

    @Transient
    private List<ComplexProductItem> complexProductItems;


    public ComplexProduct()
    {
        super();
    }

    public ComplexProduct(BigInteger productId)
    {
        super(productId);
    }

    @Override
    public String getProductCode() {
        return productCode;
    }

    @Override
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String getProductName()
    {
        return productName;
    }

    @Override
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    @Override
    public DbResource getMeasureUnit() {
        return measureUnit;
    }

    @Override
    public void setMeasureUnit(DbResource measureUnit) {
        firePropertyChange("measureUnit", this.measureUnit, measureUnit);
        this.measureUnit = measureUnit;
    }

    @Override
    public BigDecimal getSalePrice()
    {
        return salePrice;
    }

    @Override
    public void setSalePrice(BigDecimal salePrice)
    {
        this.salePrice = salePrice;
    }

    public AssemblingSchema getAppliedSchema() {
        return appliedSchema;
    }

    public void setAppliedSchema(AssemblingSchema appliedSchema) {
        this.appliedSchema = appliedSchema;
    }

    @Override
    public String getInfo() {
        return getProductName();
    }

    public List<ComplexProductItem> getComplexProductItems()
    {
        return complexProductItems;
    }

    public boolean addComplexProductItem(ComplexProductItem complexProductItem)
    {
        if(complexProductItems == null)
            complexProductItems = new ArrayList<ComplexProductItem>();

        return complexProductItems.add(complexProductItem);
    }
}
