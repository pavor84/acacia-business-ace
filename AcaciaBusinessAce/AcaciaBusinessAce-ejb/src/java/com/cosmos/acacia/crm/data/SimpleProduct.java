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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.util.CodeFormatter;
import javax.persistence.DiscriminatorValue;


/**
 *
 * @author miro
 */
@Entity
@Table(name = "simple_products")
@DiscriminatorValue(value="S")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "Product.findByParentDataObjectAndDeleted",
                query = "select p from SimpleProduct p where p.dataObject.parentDataObjectId = :parentDataObjectId and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "Product.findByParentDataObjectIsNullAndDeleted",
                query = "select p from SimpleProduct p where p.dataObject.parentDataObjectId is null and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                /**
                 * Parameters:
                 * - productName - find all undeleted products with the same name (at most one should exist)
                 */
                name = "Product.findByProductName",
                query = "select p from SimpleProduct p where p.productName like :productName and p.dataObject.deleted = false"
            ),
        @NamedQuery
            (
                /**
                 * Parameters:
                 * - productCode - find all undeleted products with the same code (at most one should exist)
                 */
                name = "Product.findByProductCode",
                query = "select p from SimpleProduct p where p.productCode like :productCode and p.dataObject.deleted = false"
            )
    })
public class SimpleProduct
    extends Product
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "category_id", nullable=false, referencedColumnName = "product_category_id")
    @ManyToOne
    @Property(title="Category", propertyValidator=@PropertyValidator(required=true))
    private ProductCategory category;

    @Column(name = "product_name", nullable = false)
    @Property(title="Product Name",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=100))
    private String productName;

    @Column(name = "product_code", nullable = false)
    @Property(title="Product Code",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=50, required=true))
    private String productCode;

    @JoinColumn(name = "measure_unit_id", nullable=false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @Column(name = "is_purchased", nullable = false)
    @Property(title="Is Purchased")
    private boolean purchased;

    @Column(name = "is_salable", nullable = false)
    @Property(title="Is Salable")
    private boolean salable = true;

    @Column(name = "is_obsolete", nullable = false)
    @Property(title="Is Obsolete")
    private boolean obsolete;

    @JoinColumn(name = "pattern_mask_format_id", referencedColumnName = "pattern_mask_format_id")
    @ManyToOne
    @Property(title="Pattern Mask Format")
    private PatternMaskFormat patternMaskFormat;

    @JoinColumn(name = "product_color_id", nullable=true, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Product Color")
    private DbResource productColor;

    @Column(name = "minimum_quantity", nullable = false)
    @Property(title="Min. Quantity", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal minimumQuantity = BigDecimal.ONE;

    @Column(name = "maximum_quantity")
    @Property(title="Max. Quantity", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal maximumQuantity;

    @Column(name = "default_quantity")
    @Property(title="Default Quantity", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal defaultQuantity;

    @Column(name = "purchase_price", nullable = false)
    @Property(title="Purchase Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal purchasePrice;

    @Column(name = "sale_price", nullable = false)
    @Property(title="Sales Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal salePrice;

    @Column(name = "list_price", nullable = false)
    @Property(title="List Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal listPrice;

    @Column(name = "quantity_per_package", nullable = false)
    @Property(title="Qty per Package", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private int quantityPerPackage = 1;

    @JoinColumn(name = "dimension_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Dimension Unit")
    private DbResource dimensionUnit;

    @Column(name = "dimension_width")
    @Property(title="Dimension Width", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=99999d))
    private BigDecimal dimensionWidth;

    @Column(name = "dimension_length")
    @Property(title="Dimension Length", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=99999d))
    private BigDecimal dimensionLength;

    @Column(name = "dimension_height")
    @Property(title="Dimension Height", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=99999d))
    private BigDecimal dimensionHeight;

    @JoinColumn(name = "weight_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Weight Unit")
    private DbResource weightUnit;

    @Column(name = "weight")
    @Property(title="Weight", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=9999999999d))
    private BigDecimal weight;

    @Column(name = "delivery_time")
    @Property(title="Delivery Time", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private Integer deliveryTime;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "producer_id")
    @ManyToOne
    @Property(title="Producer")
    private BusinessPartner producer;
    

    public SimpleProduct() {
    }

    public SimpleProduct(BigInteger productId) {
        super(productId);
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        firePropertyChange("category", this.category, category);
        this.category = category;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void setProductName(String productName) {
        firePropertyChange("productName", this.productName, productName);
        this.productName = productName;
    }

    @Override
    public String getProductCode() {
        return productCode;
    }

    @Override
    public void setProductCode(String productCode) {
        firePropertyChange("productCode", this.productCode, productCode);
        this.productCode = productCode;
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
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    @Override
    public void setSalePrice(BigDecimal salePrice) {
        firePropertyChange("salePrice", this.salePrice, salePrice);
        this.salePrice = salePrice;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean isPurchased) {
        firePropertyChange("purchased", this.purchased, purchased);
        this.purchased = isPurchased;
    }

    public boolean isSalable() {
        return salable;
    }

    public void setSalable(boolean isSalable) {
        firePropertyChange("salable", this.salable, salable);
        this.salable = isSalable;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean isObsolete) {
        firePropertyChange("obsolete", this.obsolete, obsolete);
        this.obsolete = isObsolete;
    }

    public PatternMaskFormat getPatternMaskFormat() {
        return patternMaskFormat;
    }

    public void setPatternMaskFormat(PatternMaskFormat patternMaskFormat) {
        firePropertyChange("patternMaskFormat", this.patternMaskFormat, patternMaskFormat);
        this.patternMaskFormat = patternMaskFormat;
    }

    public DbResource getProductColor() {
        return productColor;
    }

    public void setProductColor(DbResource productColor) {
        firePropertyChange("productColor", this.productColor, productColor);
        this.productColor = productColor;
    }

    public BigDecimal getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(BigDecimal minimumQuantity) {
        firePropertyChange("minimumQuantity", this.minimumQuantity, minimumQuantity);
        this.minimumQuantity = minimumQuantity;
    }

    public BigDecimal getMaximumQuantity() {
        return maximumQuantity;
    }

    public void setMaximumQuantity(BigDecimal maximumQuantity) {
        firePropertyChange("maximumQuantity", this.maximumQuantity, maximumQuantity);
        this.maximumQuantity = maximumQuantity;
    }

    public BigDecimal getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(BigDecimal defaultQuantity) {
        firePropertyChange("defaultQuantity", this.defaultQuantity, defaultQuantity);
        this.defaultQuantity = defaultQuantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        firePropertyChange("purchasePrice", this.purchasePrice, purchasePrice);
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        firePropertyChange("listPrice", this.listPrice, listPrice);
        this.listPrice = listPrice;
    }

    public int getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public void setQuantityPerPackage(int quantityPerPackage) {
        firePropertyChange("quantityPerPackage", this.quantityPerPackage, quantityPerPackage);
        this.quantityPerPackage = quantityPerPackage;
    }

    public DbResource getDimensionUnit() {
        return dimensionUnit;
    }

    public void setDimensionUnit(DbResource dimensionUnit) {
        firePropertyChange("dimensionUnit", this.dimensionUnit, dimensionUnit);
        this.dimensionUnit = dimensionUnit;
    }

    public BigDecimal getDimensionWidth() {
        return dimensionWidth;
    }

    public void setDimensionWidth(BigDecimal dimensionWidth) {
        firePropertyChange("dimensionWidth", this.dimensionWidth, dimensionWidth);
        this.dimensionWidth = dimensionWidth;
    }

    public BigDecimal getDimensionLength() {
        return dimensionLength;
    }

    public void setDimensionLength(BigDecimal dimensionLength) {
        firePropertyChange("dimensionLength", this.dimensionLength, dimensionLength);
        this.dimensionLength = dimensionLength;
    }

    public BigDecimal getDimensionHeight() {
        return dimensionHeight;
    }

    public void setDimensionHeight(BigDecimal dimensionHeight) {
        firePropertyChange("dimensionHeight", this.dimensionHeight, dimensionHeight);
        this.dimensionHeight = dimensionHeight;
    }

    public DbResource getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(DbResource weightUnit) {
        firePropertyChange("weightUnit", this.weightUnit, weightUnit);
        this.weightUnit = weightUnit;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        firePropertyChange("weight", this.weight, weight);
        this.weight = weight;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        firePropertyChange("deliveryTime", this.deliveryTime, deliveryTime);
        this.deliveryTime = deliveryTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        firePropertyChange("description", this.description, description);
        this.description = description;
    }

    /**
     * Synthetic property getter.
     * Format on every call.
     * If not setup {@link PatternMaskFormat} property - returns "";
     * If no product code entered returns "";
     * If format error occurs, prints the stack trace and returns "<FORMAT ERROR>";
     * @return - not null result
     */
    public String getCodeFormatted(){
        
        
        PatternMaskFormat f = getPatternMaskFormat();
        if ( f==null )
            return "";
        if ( f.getFormat()==null )
            return "";
        if ( getProductCode()==null )
            return "";
        try {
            CodeFormatter formatter = new CodeFormatter(f.getFormat());
            String result = formatter.getDisplayValue(getProductCode());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "<FORMAT ERROR>"; 
        }
    }


    /*@PrePersist
    public void prePersist()
    {
        System.out.println("PrePersist");
    }

    @PostPersist
    public void postPersist()
    {
        System.out.println("PostPersist");
    }

    @PreUpdate
    public void preUpdate()
    {
        System.out.println("PreUpdate");
    }

    @PostUpdate
    public void postUpdate()
    {
        System.out.println("PostUpdate");
    }

    @PreRemove
    public void preRemove()
    {
        System.out.println("PreRemove");
    }

    @PostRemove
    public void postRemove()
    {
        System.out.println("PostRemove");
    }*/

    @PostLoad
    public void postLoad()
    {
        System.out.println("PostLoad()");
    }

    public static SimpleProduct newTestProduct(String productName, String productCode)
    {
        SimpleProduct product = new SimpleProduct();
        product.setProductName(productName);
        product.setProductCode(productCode);
        //product.setCategory(BigInteger.ONE);
        product.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        product.setPurchasePrice(BigDecimal.valueOf(100.20));
        product.setSalePrice(BigDecimal.valueOf(200.00));
        product.setListPrice(BigDecimal.valueOf(250.00));

        return product;
    }

    /**
     * Setter for producer
     * @param producer - BusinessPartner
     */
    public void setProducer(BusinessPartner producer) {
        firePropertyChange("producer", this.producer, producer);
        this.producer = producer;
    }

    /**
     * Getter for producer
     * @return BusinessPartner
     */
    public BusinessPartner getProducer() {
        return producer;
    }
}
