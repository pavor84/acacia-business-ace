/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

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

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "complex_products")
@DiscriminatorValue(value = Product.DISCRIMINATOR_COMPLEX_PRODUCT)
@PrimaryKeyJoinColumn(name = "product_id", referencedColumnName = "product_id")
@NamedQueries({})
public class ComplexProduct extends Product implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    @Column(name = "sale_price", nullable = false)
    @Property(title = "Sales Price", propertyValidator = @PropertyValidator(validationType = ValidationType.NUMBER_RANGE, minValue = 0d, maxValue = 1000000000000d))
    private BigDecimal salesPrice;
    //
    @JoinColumn(name = "applied_schema_id", referencedColumnName = "product_id")
    @ManyToOne
    private AssemblingSchema appliedSchema;
    //
    @Transient
    private List<ComplexProductItem> complexProductItems;

    public ComplexProduct() {
        super(DISCRIMINATOR_COMPLEX_PRODUCT);
    }

    public ComplexProduct(BigInteger productId) {
        super(DISCRIMINATOR_COMPLEX_PRODUCT, productId);
    }

    public String getCodeFormatted() {
        if (getProductCode() != null) {
            return getProductCode();
        } else {
            return "";
        }
    }

    @Override
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    @Override
    public void setSalesPrice(BigDecimal salesPrice) {
        firePropertyChange("salePrice", this.salesPrice, salesPrice);
        this.salesPrice = salesPrice;
    }

    public AssemblingSchema getAppliedSchema() {
        return appliedSchema;
    }

    public void setAppliedSchema(AssemblingSchema appliedSchema) {
        firePropertyChange("appliedSchema", this.appliedSchema, appliedSchema);
        this.appliedSchema = appliedSchema;
    }

    @Override
    public String getInfo() {
        return getProductName();
    }

    public List<ComplexProductItem> getComplexProductItems() {
        return complexProductItems;
    }

    public boolean addComplexProductItem(ComplexProductItem complexProductItem) {
        firePropertyChange("add.complexProductItem", null, complexProductItem);

        if (complexProductItems == null) {
            complexProductItems = new ArrayList<ComplexProductItem>();
        }

        boolean result = complexProductItems.add(complexProductItem);
        firePropertyChange("complexProductItems", this.complexProductItems, complexProductItems);
        return result;
    }

    public String toString(boolean debug) {
        if (!debug) {
            return toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        if (complexProductItems != null && complexProductItems.size() > 0) {
            sb.append("; items: ").append(complexProductItems.toString());
        }

        return sb.toString();
    }

    public void setComplexProductItems(List<ComplexProductItem> complexProductItems) {
        this.complexProductItems = complexProductItems;
    }
}
