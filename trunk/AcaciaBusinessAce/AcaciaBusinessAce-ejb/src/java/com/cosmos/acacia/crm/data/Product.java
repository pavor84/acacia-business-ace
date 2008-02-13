/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
            )
    })
public class Product
    extends DataObjectBean
    implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_id", nullable = false)
    @Property(title="Product Id", editable=false, readOnly=true, visible=false, hiden=true)
    private BigInteger productId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hiden=true)
    private BigInteger parentId;

    @Column(name = "category_id", nullable = false)
    @Property(title="Category Id", editable=false, readOnly=true, visible=false)
    private BigInteger categoryId;

    @Column(name = "product_name", nullable = false)
    @Property(title="Product Name")
    private String productName;

    @Column(name = "product_code", nullable = false)
    @Property(title="Product Code")
    private String productCode;

    @Column(name = "measure_unit_id", nullable = false)
    @Property(title="Measure Unit Id", editable=false, readOnly=true, visible=false)
    private int measureUnitId;

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

    @Column(name = "pattern_format_id")
    @Property(title="Pattern Format Id", editable=false, readOnly=true, visible=false)
    private Integer patternFormatId;

    @Column(name = "product_color")
    @Property(title="Product Color")
    private String productColor;

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

    @Column(name = "dimension_unit_id")
    @Property(title="Dimension Unit Id", editable=false, readOnly=true, visible=false)
    private Integer dimensionUnitId;

    @Column(name = "dimension_width")
    @Property(title="Dimension Width")
    private BigDecimal dimensionWidth;

    @Column(name = "dimension_length")
    @Property(title="Dimension Length")
    private BigDecimal dimensionLength;

    @Column(name = "dimension_height")
    @Property(title="Dimension Height")
    private BigDecimal dimensionHeight;

    @Column(name = "weight_unit_id")
    @Property(title="Weight Unit Id", editable=false, readOnly=true, visible=false)
    private Integer weightUnitId;

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

    public Product(BigInteger productId, BigInteger categoryId, String productName, String productCode, int measureUnitId, boolean isComplex, boolean isPurchased, boolean isSalable, boolean isObsolete, BigDecimal minimumQuantity, BigDecimal purchasePrice, BigDecimal salePrice, BigDecimal listPrice, int quantityPerPackage) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productCode = productCode;
        this.measureUnitId = measureUnitId;
        this.complex = isComplex;
        this.purchased = isPurchased;
        this.salable = isSalable;
        this.obsolete = isObsolete;
        this.minimumQuantity = minimumQuantity;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.listPrice = listPrice;
        this.quantityPerPackage = quantityPerPackage;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        firePropertyChange("productId", this.productId, productId);
        this.productId = productId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        firePropertyChange("parentId", this.parentId, parentId);
        this.parentId = parentId;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        firePropertyChange("categoryId", this.categoryId, categoryId);
        this.categoryId = categoryId;
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

    public int getMeasureUnitId() {
        return measureUnitId;
    }

    public void setMeasureUnitId(int measureUnitId) {
        firePropertyChange("measureUnitId", this.measureUnitId, measureUnitId);
        this.measureUnitId = measureUnitId;
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

    public Integer getPatternFormatId() {
        return patternFormatId;
    }

    public void setPatternFormatId(Integer patternFormatId) {
        firePropertyChange("patternFormatId", this.patternFormatId, patternFormatId);
        this.patternFormatId = patternFormatId;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
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

    public Integer getDimensionUnitId() {
        return dimensionUnitId;
    }

    public void setDimensionUnitId(Integer dimensionUnitId) {
        firePropertyChange("dimensionUnitId", this.dimensionUnitId, dimensionUnitId);
        this.dimensionUnitId = dimensionUnitId;
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

    public Integer getWeightUnitId() {
        return weightUnitId;
    }

    public void setWeightUnitId(Integer weightUnitId) {
        firePropertyChange("weightUnitId", this.weightUnitId, weightUnitId);
        this.weightUnitId = weightUnitId;
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
        sb.append(productId).append("]");
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
        product.setCategoryId(BigInteger.ONE);
        product.setMeasureUnitId(1);
        product.setPurchasePrice(BigDecimal.valueOf(100.20));
        product.setSalePrice(BigDecimal.valueOf(200.00));
        product.setListPrice(BigDecimal.valueOf(250.00));

        return product;
    }

}
