/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 *
 * @author miro
 */
@Entity
@Table(name = "products")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "Product.findByParentDataObjectAndDeleted",
                query = "select p from Product p where p.dataObject.parentDataObject = :parentDataObject and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "Product.findByParentDataObjectIsNullAndDeleted",
                query = "select p from Product p where p.dataObject.parentDataObject is null and p.dataObject.deleted = :deleted"
            )
    })
public class Product
    extends DataObjectBean
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_id", nullable = false)
    @Property(title="Product Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger productId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @JoinColumn(name = "category_id", nullable=false, referencedColumnName = "product_category_id")
    @ManyToOne
    @Property(title="Category")
    private ProductCategory category;

    @Column(name = "product_name", nullable = false)
    @Property(title="Product Name", validationType=ValidationType.REQUIRED, validationRegex="[a-zA-Z]+")
    private String productName;

    @Column(name = "product_code", nullable = false)
    @Property(title="Product Code")
    private String productCode;

    @JoinColumn(name = "measure_unit_id", nullable=false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @Column(name = "is_complex", nullable = false)
    @Property(title="Is Complex")
    private boolean complex;

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
    @Property(title="Min. Quantity")
    private BigDecimal minimumQuantity = BigDecimal.ONE;

    @Column(name = "maximum_quantity")
    @Property(title="Max. Quantity")
    private BigDecimal maximumQuantity;

    @Column(name = "default_quantity")
    @Property(title="Default Quantity")
    private BigDecimal defaultQuantity;

    @Column(name = "purchase_price", nullable = false)
    @Property(title="Purchase Price")
    private BigDecimal purchasePrice;

    @Column(name = "sale_price", nullable = false)
    @Property(title="Sales Price")
    private BigDecimal salePrice;

    @Column(name = "list_price", nullable = false)
    @Property(title="List Price")
    private BigDecimal listPrice;

    @Column(name = "quantity_per_package", nullable = false)
    @Property(title="Qty per Package")
    private int quantityPerPackage = 1;

    @JoinColumn(name = "dimension_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Dimension Unit")
    private DbResource dimensionUnit;

    @Column(name = "dimension_width")
    @Property(title="Dimension Width")
    private BigDecimal dimensionWidth;

    @Column(name = "dimension_length")
    @Property(title="Dimension Length")
    private BigDecimal dimensionLength;

    @Column(name = "dimension_height")
    @Property(title="Dimension Height")
    private BigDecimal dimensionHeight;

    @JoinColumn(name = "weight_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Weight Unit")
    private DbResource weightUnit;

    @Column(name = "weight")
    @Property(title="Weight")
    private BigDecimal weight;

    @Column(name = "delivery_time")
    @Property(title="Delivery time")
    private Integer deliveryTime;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @Column(name = "producer_id")
    @Property(title="Producer Id", editable=false, readOnly=true, visible=false)
    private BigInteger producerId;

    @OneToOne
    /*@JoinColumn(
        name = "product_id",
        referencedColumnName = "data_object_id",
        insertable = false,
        updatable = false)*/
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public Product() {
    }

    public Product(BigInteger productId) {
        this.productId = productId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        firePropertyChange("category", this.category, category);
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        firePropertyChange("productName", this.productName, productName);
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        firePropertyChange("productCode", this.productCode, productCode);
        this.productCode = productCode;
    }

    public DbResource getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(DbResource measureUnit) {
        firePropertyChange("measureUnit", this.measureUnit, measureUnit);
        this.measureUnit = measureUnit;
    }

    public boolean isComplex() {
        return complex;
    }

    public void setComplex(boolean isComplex) {
        firePropertyChange("complex", this.complex, complex);
        this.complex = isComplex;
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
        if(patternMaskFormat != null)
            return patternMaskFormat;

        ProductCategory pc;
        if((pc = getCategory()) != null)
            return pc.getPatternMaskFormat();

        return null;
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        firePropertyChange("salePrice", this.salePrice, salePrice);
        this.salePrice = salePrice;
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

    public void setWeightUnitId(DbResource weightUnit) {
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

    public BigInteger getProducerId() {
        return producerId;
    }

    public void setProducerId(BigInteger producerId) {
        firePropertyChange("producerId", this.producerId, producerId);
        this.producerId = producerId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append("[productId=");
        sb.append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", productCode=").append(productCode);
        sb.append(", category=").append(category).append("]");
        DataObject dataObject = getDataObject();
        if(dataObject != null)
            sb.append(":v.").append(getDataObject().getDataObjectVersion());
        return sb.toString();
    }

    // DataObjectBean

    @Override
    public BigInteger getId() {
        return getProductId();
    }

    @Override
    public void setId(BigInteger id) {
        setProductId(id);
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


    public static Product newTestProduct(String productName, String productCode)
    {
        Product product = new Product();
        product.setProductName(productName);
        product.setProductCode(productCode);
        //product.setCategory(BigInteger.ONE);
        product.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        product.setPurchasePrice(BigDecimal.valueOf(100.20));
        product.setSalePrice(BigDecimal.valueOf(200.00));
        product.setListPrice(BigDecimal.valueOf(250.00));

        return product;
    }

}
