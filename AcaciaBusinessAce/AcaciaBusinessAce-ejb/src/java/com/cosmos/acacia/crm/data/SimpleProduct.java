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
            ),
        @NamedQuery
        (
            /**
             * Parameters:
             * - pricelistId - the price-list id, not null
             */
            name = "SimpleProduct.findNotIncludedProductsInPricelist",
            query = "select p from SimpleProduct p where p.dataObject.deleted = false and " +
            		"not exists (select i from PricelistItem i where i.dataObject.parentDataObjectId = :pricelistId and i.product=p)"
		)
    })
public class SimpleProduct
    extends Product
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;

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

    @JoinColumn(name = "transport_percent_id", referencedColumnName = "percent_value_id")
    @ManyToOne(optional=false)
    private ProductPercentValue transportPercentValue;

    @JoinColumn(name = "discount_percent_id", referencedColumnName = "percent_value_id")
    @ManyToOne(optional=false)
    private ProductPercentValue discountPercentValue;

    @JoinColumn(name = "profit_percent_id", referencedColumnName = "percent_value_id")
    @ManyToOne(optional=false)
    private ProductPercentValue profitPercentValue;

    @JoinColumn(name = "customs_duty_percent_id", referencedColumnName = "percent_value_id")
    @ManyToOne(optional=false)
    private ProductPercentValue customsDutyPercentValue;

    @JoinColumn(name = "excise_duty_percent_id", referencedColumnName = "percent_value_id")
    @ManyToOne(optional=false)
    private ProductPercentValue exciseDutyPercentValue;

    @Column(name = "transport_value", precision = 19, scale = 4)
    private BigDecimal transportValue;
    
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
        validationType=ValidationType.NUMBER_RANGE, minValue=0d))
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

    @Transient
    @Property(title="Total Discount %", editable=false)
    private BigDecimal totalDiscountPercent;

    @Transient
    @Property(title="Total Discount Value", editable=false)
    private BigDecimal totalDiscountValue;

    @Transient
    @Property(title="Total Profit %", editable=false)
    private BigDecimal totalProfitPercent;

    @Transient
    @Property(title="Total Profit Value", editable=false)
    private BigDecimal totalProfitValue;

    @Transient
    @Property(title="Purchase Price", editable=false)
    private BigDecimal purchasePrice;

    @Transient
    @Property(title="Transport Price", editable=false)
    private BigDecimal transportPrice;

    @Transient
    @Property(title="Base Duty Price", editable=false)
    private BigDecimal baseDutyPrice;

    @Transient
    @Property(title="Customs Duty Percent", editable=false)
    private BigDecimal customsDutyPercent;

    @Transient
    @Property(title="Customs Duty Value", editable=false)
    private BigDecimal customsDutyValue;

    @Transient
    @Property(title="Excise Duty Percent", editable=false)
    private BigDecimal exciseDutyPercent;

    @Transient
    @Property(title="Excise Duty Value", editable=false)
    private BigDecimal exciseDutyValue;

    @Transient
    @Property(title="Cost Price", editable=false)
    private BigDecimal costPrice;

    @Transient
    @Property(title="Sales Price", editable=false)
    private BigDecimal salePrice;

    
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

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public String getProductDisplay() {
        String codeFormatted = getCodeFormatted();
        String name = getProductName();
        if (codeFormatted == null) {
            codeFormatted = "";
        } else {
            codeFormatted += " ";
        }
        if (name == null) {
            name = "";
        }
        String categoryName = "";
        if (getCategory() != null) {
            categoryName = getCategory().getCategoryName();
        }
        return codeFormatted + " " + name + ", " + categoryName;
    }

    public ProductPercentValue getCustomsDutyPercentValue() {
        return customsDutyPercentValue;
    }

    public void setCustomsDutyPercentValue(ProductPercentValue customsDutyPercentValue) {
        this.customsDutyPercentValue = customsDutyPercentValue;
    }

    public ProductPercentValue getDiscountPercentValue() {
        return discountPercentValue;
    }

    public void setDiscountPercentValue(ProductPercentValue discountPercentValue) {
        this.discountPercentValue = discountPercentValue;
    }

    public ProductPercentValue getExciseDutyPercentValue() {
        return exciseDutyPercentValue;
    }

    public void setExciseDutyPercentValue(ProductPercentValue exciseDutyPercentValue) {
        this.exciseDutyPercentValue = exciseDutyPercentValue;
    }

    public ProductPercentValue getProfitPercentValue() {
        return profitPercentValue;
    }

    public void setProfitPercentValue(ProductPercentValue profitPercentValue) {
        this.profitPercentValue = profitPercentValue;
    }

    public ProductPercentValue getTransportPercentValue() {
        return transportPercentValue;
    }

    public void setTransportPercentValue(ProductPercentValue transportPercentValue) {
        this.transportPercentValue = transportPercentValue;
    }

    public BigDecimal getTransportValue() {
        return transportValue;
    }

    public void setTransportValue(BigDecimal transportValue) {
        this.transportValue = transportValue;
    }

    public BigDecimal getTotalDiscountPercent() {
        BigDecimal categoryDiscount;
        if(category != null)
            categoryDiscount = category.getDiscountPercent();
        else
            categoryDiscount = null;

        BigDecimal productDiscount = null;
        if(discountPercentValue != null)
            productDiscount = discountPercentValue.getPercentValue();
        else
            productDiscount = null;

        if(categoryDiscount != null && productDiscount != null) {
            BigDecimal sumDiscount = categoryDiscount.add(productDiscount, MATH_CONTEXT);
            BigDecimal multipliedDiscount = categoryDiscount.multiply(productDiscount, MATH_CONTEXT);
            return sumDiscount.subtract(multipliedDiscount, MATH_CONTEXT);
        }

        if(categoryDiscount != null)
            return categoryDiscount;

        if(productDiscount != null)
            return productDiscount;

        return null;
    }

    public void setTotalDiscountPercent(BigDecimal totalDiscount) {
    }

    public BigDecimal getTotalDiscountValue() {
        BigDecimal discount;
        if(listPrice == null || (discount = getTotalDiscountPercent()) == null)
            return null;

        return listPrice.multiply(discount, MATH_CONTEXT);
    }

    public void setTotalDiscountValue(BigDecimal totalDiscountValue) {
    }

    public BigDecimal getPurchasePrice() {
        if(listPrice == null)
            return null;

        BigDecimal discountValue;
        if((discountValue = getTotalDiscountValue()) == null)
            return listPrice;

        return listPrice.subtract(discountValue, MATH_CONTEXT);
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
    }

    public BigDecimal getTransportPrice() {
        BigDecimal price = getPurchasePrice();
        BigDecimal transportPercent;
        if(transportPercentValue != null)
            transportPercent = transportPercentValue.getPercentValue();
        else
            transportPercent = null;

        BigDecimal value;
        if((value = getTransportPrice(transportValue, transportPercent, price)) != null)
            return value;

        if(category == null)
            return null;

        return getTransportPrice(
                category.getTransportValue(), category.getTransportPercent(), price);
    }

    private BigDecimal getTransportPrice(BigDecimal value, BigDecimal percent, BigDecimal price) {
        if(value != null)
            return value;

        if(percent == null || price == null)
            return null;

        return price.multiply(percent, MATH_CONTEXT);
    }

    public void setTransportPrice(BigDecimal transportPrice) {
    }

    public BigDecimal getBaseDutyPrice() {
        BigDecimal price;
        if((price = getPurchasePrice()) == null)
            return null;

        BigDecimal transport;
        if((transport = getTransportPrice()) == null)
            return price;

        return price.add(transport, MATH_CONTEXT);
    }

    public void setBaseDutyPrice(BigDecimal baseDutyPrice) {
    }

    public BigDecimal getCustomsDutyPercent() {
        if(customsDutyPercentValue != null)
            return customsDutyPercentValue.getPercentValue();

        BigDecimal percent;
        if(category != null && (percent = category.getCustomsDutyPercent()) != null)
            return percent;

        return null;
    }

    public void setCustomsDutyPercent(BigDecimal customsDutyPercent) {
    }

    public BigDecimal getCustomsDutyValue() {
        BigDecimal price;
        BigDecimal percent;
        if((price = getBaseDutyPrice()) == null || (percent = getCustomsDutyPercent()) == null)
            return null;

        return price.multiply(percent, MATH_CONTEXT);
    }

    public void setCustomsDutyValue(BigDecimal customsDutyValue) {
    }

    public BigDecimal getExciseDutyPercent() {
        if(exciseDutyPercentValue != null)
            return exciseDutyPercentValue.getPercentValue();

        BigDecimal percent;
        if(category != null && (percent = category.getExciseDutyPercent()) != null)
            return percent;

        return null;
    }

    public void setExciseDutyPercent(BigDecimal exciseDutyPercent) {
    }

    public BigDecimal getExciseDutyValue() {
        BigDecimal price;
        BigDecimal percent;
        if((price = getBaseDutyPrice()) == null || (percent = getExciseDutyPercent()) == null)
            return null;

        return price.multiply(percent, MATH_CONTEXT);
    }

    public void setExciseDutyValue(BigDecimal exciseDutyValue) {
    }

    public BigDecimal getCostPrice() {
        BigDecimal price;
        if((price = getBaseDutyPrice()) == null)
            return null;

        BigDecimal duty;
        if((duty = getCustomsDutyValue()) != null)
            price = price.add(duty, MATH_CONTEXT);

        if((duty = getExciseDutyValue()) != null)
            price = price.add(duty, MATH_CONTEXT);

        return price;
    }

    public void setCostPrice(BigDecimal costPrice) {
    }

    public BigDecimal getTotalProfitPercent() {
        BigDecimal percent;
        if(profitPercentValue != null)
            percent = profitPercentValue.getPercentValue();
        else
            percent = null;

        BigDecimal value;
        if(category != null && (value = category.getProfitPercent()) != null) {
            if(percent == null)
                return value;

            return percent.add(value, MATH_CONTEXT);
        }

        return percent;
    }

    public void setTotalProfitPercent(BigDecimal totalProfitPercent) {
    }

    public BigDecimal getTotalProfitValue() {
        BigDecimal price;
        if((price = getCostPrice()) == null)
            return null;

        BigDecimal profit;
        if((profit = getTotalProfitPercent()) == null)
            return null;

        return price.multiply(profit, MATH_CONTEXT).divide(BigDecimal.ONE.subtract(profit, MATH_CONTEXT), MATH_CONTEXT);
    }

    public void setTotalProfitValue(BigDecimal totalProfitValue) {
    }

    @Override
    public BigDecimal getSalePrice() {
        BigDecimal price;
        if((price = getCostPrice()) == null)
            return null;

        BigDecimal profit;
        if((profit = getTotalProfitPercent()) == null)
            return price;

        return price.divide(BigDecimal.ONE.subtract(profit, MATH_CONTEXT), MATH_CONTEXT);
    }

    @Override
    public void setSalePrice(BigDecimal salePrice) {
    }
}
