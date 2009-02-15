/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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
import javax.persistence.Transient;


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
                name = "SimpleProduct.findByParentDataObjectAndDeleted",
                query = "select p from SimpleProduct p where p.dataObject.parentDataObjectId = :parentDataObjectId and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "SimpleProduct.findByParentDataObjectIsNullAndDeleted",
                query = "select p from SimpleProduct p where p.dataObject.parentDataObjectId is null and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                /**
                 * Parameters:
                 * - productName - find all undeleted products with the same name (at most one should exist)
                 */
                name = "SimpleProduct.findByProductName",
                query = "select p from SimpleProduct p where p.productName like :productName and p.dataObject.deleted = false"
            ),
        @NamedQuery
            (
                /**
                 * Parameters:
                 * - productCode - find all undeleted products with the same code (at most one should exist)
                 */
                name = "SimpleProduct.findByProductCode",
                query = "select p from SimpleProduct p where p.productCode like :productCode and p.dataObject.deleted = false"
            ),
        @NamedQuery
            (
                /**
                 * Parameters:
                 * - categoryIds - Collection<BigInteger>
                 */
                name = "SimpleProduct.findByCategories",
                query = "select p from SimpleProduct p where p.dataObject.deleted = false and p.category.id in (:categoryIds)"
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

    @Column(name = "list_price", nullable = false)
    @Property(title="List Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal listPrice;
    
    @Property(title="Discount %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "discount_percent", precision=20, scale=4)
    private BigDecimal discountPercent;
    
    @ManyToOne
    private ProductPricingValue discountPricingValue;
    
    @ManyToOne
    private ProductPricingValue dutyPricingValue;
    
    @ManyToOne
    private ProductPricingValue transportPricingValue;
    
    @ManyToOne
    private ProductPricingValue profitPricingValue;
    
    @Property(title="Duty %", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000d))
    @Column(name = "duty_percent", precision=20, scale=4)
    private BigDecimal dutyPercent;
    
    @Column(name = "transport_price")
    @Property(title="Transport Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal transportPrice;
    
    @Column(name = "profit_percent")
    @Property(title="Profit Percent", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000d))
    private BigDecimal profitPercent;
    
    @Column(name = "quantity_per_package", nullable = false)
    @Property(title="Qty per Package", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private int quantityPerPackage = 1;

    @Column(name = "price_per_quantity", nullable = false)
    @Property(title="Price per Qty", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private int pricePerQuantity = 1;

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

    @Transient
    @Property(title="Cubature", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=99999d))
    private BigDecimal dimensionCubature;

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
    
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;
    
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

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        firePropertyChange("listPrice", this.listPrice, listPrice);
        this.listPrice = listPrice;
    }

    public int getPricePerQuantity() {
        return pricePerQuantity;
    }

    public void setPricePerQuantity(int pricePerQuantity) {
        this.pricePerQuantity = pricePerQuantity;
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

    public BigDecimal getDimensionCubature() {
        if(dimensionWidth == null || dimensionLength == null || dimensionHeight == null) {
            return dimensionCubature = null;
        }

        return dimensionCubature = dimensionWidth.multiply(dimensionLength).multiply(dimensionHeight);
    }

    public void setDimensionCubature(BigDecimal cubature) {
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
        if ( f==null ){
            if ( getProductCode()!=null )
                return getProductCode();
            else
                return "";
        }
            
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
    
    @Override
    public String getInfo() {
        return getProductName();
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDutyPercent() {
        return dutyPercent;
    }

    public void setDutyPercent(BigDecimal dutyPercent) {
        this.dutyPercent = dutyPercent;
    }

    public BigDecimal getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(BigDecimal transportPrice) {
        this.transportPrice = transportPrice;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    /**
     * Synthetic getter - calculates the purchase price. If some of the needed prices is not available,
     * returns null
     * @return
     */
    public BigDecimal getPurchasePrice() {
        BigDecimal listPrice = getListPrice();
        if ( listPrice==null )
            return null;
        BigDecimal discountPercent = getDiscount();
        if ( discountPercent==null )
            discountPercent = new BigDecimal(0);
        BigDecimal discountPercentDec = discountPercent.divide(new BigDecimal(100), MathContext.DECIMAL64);
        BigDecimal discountAmount = listPrice.multiply(discountPercentDec);
        BigDecimal result = listPrice.subtract(discountAmount);
        return result;
    }
    
    /**
     * Synthetic getter - calculates the purchase price. If some of the needed prices is not available,
     * returns null
     * @return
     */
    public BigDecimal getCostPrice() {
        //purchase price
        BigDecimal purchasePrice = getPurchasePrice();
        if ( purchasePrice==null )
            return null;
        BigDecimal dutyPercent = getDuty();
        //duty amount
        BigDecimal dutyAmount = null;
        if ( dutyPercent==null )
            dutyAmount = new BigDecimal(0);
        else{
            BigDecimal dutyPercentDec = dutyPercent.divide(new BigDecimal(100), MathContext.DECIMAL64);
            dutyAmount = purchasePrice.multiply(dutyPercentDec);
        }
        //transport amount
        BigDecimal transportAmount = getTransport();
        if ( transportAmount==null )
            transportAmount = new BigDecimal(0);
        
        BigDecimal result = purchasePrice.add(dutyAmount).add(transportAmount);
        
        return result;
    }
    
    /**
     * Synthetic getter - calculates the sales price. If some of the needed prices is not available,
     * returns null
     * @return
     */
    public BigDecimal getSalePrice() {
        BigDecimal costPrice = getCostPrice();
        if ( costPrice==null )
            return null;
        BigDecimal profitPercent = getProfit();
        if ( profitPercent==null )
            profitPercent = new BigDecimal(0);
        BigDecimal profitPercentDec = profitPercent.divide(new BigDecimal(100), MathContext.DECIMAL64);
        BigDecimal profitDivisor = new BigDecimal(1).subtract(profitPercentDec);
        BigDecimal result = costPrice.divide(profitDivisor, MathContext.DECIMAL64);
        return result;
    }

    public ProductPricingValue getDiscountPricingValue() {
        return discountPricingValue;
    }

    public void setDiscountPricingValue(ProductPricingValue discountPricingValue) {
        this.discountPricingValue = discountPricingValue;
    }

    public ProductPricingValue getDutyPricingValue() {
        return dutyPricingValue;
    }

    public void setDutyPricingValue(ProductPricingValue dutyPricingValue) {
        this.dutyPricingValue = dutyPricingValue;
    }

    public ProductPricingValue getTransportPricingValue() {
        return transportPricingValue;
    }

    public void setTransportPricingValue(ProductPricingValue transportPricingValue) {
        this.transportPricingValue = transportPricingValue;
    }

    public ProductPricingValue getProfitPricingValue() {
        return profitPricingValue;
    }

    public void setProfitPricingValue(ProductPricingValue profitPricingValue) {
        this.profitPricingValue = profitPricingValue;
    }
    
    /**
     * Percent value of {@link #getListPrice()}.
     * Synthetic getter - the value of {@link #getDiscountPricingValue()} or 
     * {@link #getDiscountPercent()} if the former is null.
     * @return
     */
    public BigDecimal getDiscount(){
        if ( getDiscountPricingValue()==null )
            return getDiscountPercent();
        else
            return getDiscountPricingValue().getValue();
    }
    
    /**
     * Percent value of {@link #getPurchasePrice()}
     * Synthetic getter - the value of {@link #getDutyPricingValue()} or 
     * {@link #getDutyPercent()} if the former is null.
     * @return
     */
    public BigDecimal getDuty(){
        if ( getDutyPricingValue()==null )
            return getDutyPercent();
        else
            return getDutyPricingValue().getValue();
    }
    
    /**
     * Absolute value.
     * Synthetic getter - the value of {@link #getTransportPricingValue()} as percent calculated as absolute value 
     * over {@link #getPurchasePrice()} or 
     * {@link #getTransportPrice()} if {@link #getTransportPricingValue()} is null.
     * @return
     */
    public BigDecimal getTransport(){
        if ( getTransportPricingValue()==null )
            return getTransportPrice();
        else{
            BigDecimal transportPercentDec = getTransportPricingValue().getValue().divide(new BigDecimal(100), MathContext.DECIMAL64);
            BigDecimal purchasePrice = getPurchasePrice();
            if ( purchasePrice==null )
                return null;
            BigDecimal transportValue = purchasePrice.multiply(transportPercentDec);
            
            return transportValue;
        }
    }
    
    /**
     * Percent value of {@link #getCostPrice()} inclusively.
     */
    public BigDecimal getProfit(){
        if ( getProfitPricingValue()==null ){
            return getProfitPercent();
        }else{
            return getProfitPricingValue().getValue();
        }
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    @Override
    public void setSalePrice(BigDecimal salePrice) {
        //nothing to do, this property is synthetic
    }

    public String getProductDisplay() {
        String codeFormatted = getCodeFormatted();
        String name = getProductName();
        if (codeFormatted==null)
            codeFormatted="";
        else
            codeFormatted += " ";
        if ( name==null )
            name = "";
        String categoryName = "";
        if ( getCategory()!=null ){
            categoryName = getCategory().getCategoryName();
        }
        return codeFormatted+" "+name+", "+categoryName;
    }
}
