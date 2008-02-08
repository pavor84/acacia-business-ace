/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.bl.impl.DataObjectsListeners;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author miro
 */
@Entity
@Table(name = "products")
@NamedQueries(
    {
        @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId"), 
        @NamedQuery(name = "Product.findByParentId", query = "SELECT p FROM Product p WHERE p.parentId = :parentId"), 
        @NamedQuery(name = "Product.findByCategoryId", query = "SELECT p FROM Product p WHERE p.categoryId = :categoryId"), 
        @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName"), 
        @NamedQuery(name = "Product.findByProductCode", query = "SELECT p FROM Product p WHERE p.productCode = :productCode"), 
        @NamedQuery(name = "Product.findByMeasureUnitId", query = "SELECT p FROM Product p WHERE p.measureUnitId = :measureUnitId"), 
        @NamedQuery(name = "Product.findByIsComplex", query = "SELECT p FROM Product p WHERE p.isComplex = :isComplex"), 
        @NamedQuery(name = "Product.findByIsPurchased", query = "SELECT p FROM Product p WHERE p.isPurchased = :isPurchased"), 
        @NamedQuery(name = "Product.findByIsSalable", query = "SELECT p FROM Product p WHERE p.isSalable = :isSalable"), 
        @NamedQuery(name = "Product.findByIsObsolete", query = "SELECT p FROM Product p WHERE p.isObsolete = :isObsolete"), 
        @NamedQuery(name = "Product.findByPatternFormatId", query = "SELECT p FROM Product p WHERE p.patternFormatId = :patternFormatId"), 
        @NamedQuery(name = "Product.findByProductColor", query = "SELECT p FROM Product p WHERE p.productColor = :productColor"), 
        @NamedQuery(name = "Product.findByMinimumQuantity", query = "SELECT p FROM Product p WHERE p.minimumQuantity = :minimumQuantity"), 
        @NamedQuery(name = "Product.findByMaximumQuantity", query = "SELECT p FROM Product p WHERE p.maximumQuantity = :maximumQuantity"), 
        @NamedQuery(name = "Product.findByDefaultQuantity", query = "SELECT p FROM Product p WHERE p.defaultQuantity = :defaultQuantity"), 
        @NamedQuery(name = "Product.findByPurchasePrice", query = "SELECT p FROM Product p WHERE p.purchasePrice = :purchasePrice"), 
        @NamedQuery(name = "Product.findBySalePrice", query = "SELECT p FROM Product p WHERE p.salePrice = :salePrice"), 
        @NamedQuery(name = "Product.findByListPrice", query = "SELECT p FROM Product p WHERE p.listPrice = :listPrice"), 
        @NamedQuery(name = "Product.findByQuantityPerPackage", query = "SELECT p FROM Product p WHERE p.quantityPerPackage = :quantityPerPackage"), 
        @NamedQuery(name = "Product.findByDimensionUnitId", query = "SELECT p FROM Product p WHERE p.dimensionUnitId = :dimensionUnitId"), 
        @NamedQuery(name = "Product.findByDimensionWidth", query = "SELECT p FROM Product p WHERE p.dimensionWidth = :dimensionWidth"), 
        @NamedQuery(name = "Product.findByDimensionHeight", query = "SELECT p FROM Product p WHERE p.dimensionHeight = :dimensionHeight"), 
        @NamedQuery(name = "Product.findByWeightUnitId", query = "SELECT p FROM Product p WHERE p.weightUnitId = :weightUnitId"), 
        @NamedQuery(name = "Product.findByWeight", query = "SELECT p FROM Product p WHERE p.weight = :weight"), 
        @NamedQuery(name = "Product.findByDeliveryTime", query = "SELECT p FROM Product p WHERE p.deliveryTime = :deliveryTime"), 
        @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"), 
        @NamedQuery(name = "Product.findByProducerId", query = "SELECT p FROM Product p WHERE p.producerId = :producerId")})
@EntityListeners(value={DataObjectsListeners.class})
public class Product
    extends DataObjectBean
    implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id", nullable = false)
    private BigInteger productId;
    @Column(name = "parent_id")
    private BigInteger parentId;
    @Column(name = "category_id", nullable = false)
    private BigInteger categoryId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "product_code", nullable = false)
    private String productCode;
    @Column(name = "measure_unit_id", nullable = false)
    private int measureUnitId;
    @Column(name = "is_complex", nullable = false)
    private boolean isComplex;
    @Column(name = "is_purchased", nullable = false)
    private boolean isPurchased;
    @Column(name = "is_salable", nullable = false)
    private boolean isSalable = true;
    @Column(name = "is_obsolete", nullable = false)
    private boolean isObsolete;
    @Column(name = "pattern_format_id")
    private Integer patternFormatId;
    @Column(name = "product_color")
    private String productColor;
    @Column(name = "minimum_quantity", nullable = false)
    private BigDecimal minimumQuantity = BigDecimal.ONE;
    @Column(name = "maximum_quantity")
    private BigDecimal maximumQuantity;
    @Column(name = "default_quantity")
    private BigDecimal defaultQuantity;
    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;
    @Column(name = "list_price", nullable = false)
    private BigDecimal listPrice;
    @Column(name = "quantity_per_package", nullable = false)
    private int quantityPerPackage = 1;
    @Column(name = "dimension_unit_id")
    private Integer dimensionUnitId;
    @Column(name = "dimension_width")
    private BigDecimal dimensionWidth;
    @Column(name = "dimension_height")
    private BigDecimal dimensionHeight;
    @Column(name = "weight_unit_id")
    private Integer weightUnitId;
    @Column(name = "weight")
    private BigDecimal weight;
    @Column(name = "delivery_time")
    private Integer deliveryTime;
    @Column(name = "description")
    private String description;
    @Column(name = "producer_id")
    private BigInteger producerId;
    @JoinColumn(name = "product_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
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
        this.isComplex = isComplex;
        this.isPurchased = isPurchased;
        this.isSalable = isSalable;
        this.isObsolete = isObsolete;
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
        this.productId = productId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getMeasureUnitId() {
        return measureUnitId;
    }

    public void setMeasureUnitId(int measureUnitId) {
        this.measureUnitId = measureUnitId;
    }

    public boolean getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(boolean isComplex) {
        this.isComplex = isComplex;
    }

    public boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public boolean getIsSalable() {
        return isSalable;
    }

    public void setIsSalable(boolean isSalable) {
        this.isSalable = isSalable;
    }

    public boolean getIsObsolete() {
        return isObsolete;
    }

    public void setIsObsolete(boolean isObsolete) {
        this.isObsolete = isObsolete;
    }

    public Integer getPatternFormatId() {
        return patternFormatId;
    }

    public void setPatternFormatId(Integer patternFormatId) {
        this.patternFormatId = patternFormatId;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public BigDecimal getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(BigDecimal minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public BigDecimal getMaximumQuantity() {
        return maximumQuantity;
    }

    public void setMaximumQuantity(BigDecimal maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public BigDecimal getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(BigDecimal defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public int getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public void setQuantityPerPackage(int quantityPerPackage) {
        this.quantityPerPackage = quantityPerPackage;
    }

    public Integer getDimensionUnitId() {
        return dimensionUnitId;
    }

    public void setDimensionUnitId(Integer dimensionUnitId) {
        this.dimensionUnitId = dimensionUnitId;
    }

    public BigDecimal getDimensionWidth() {
        return dimensionWidth;
    }

    public void setDimensionWidth(BigDecimal dimensionWidth) {
        this.dimensionWidth = dimensionWidth;
    }

    public BigDecimal getDimensionHeight() {
        return dimensionHeight;
    }

    public void setDimensionHeight(BigDecimal dimensionHeight) {
        this.dimensionHeight = dimensionHeight;
    }

    public Integer getWeightUnitId() {
        return weightUnitId;
    }

    public void setWeightUnitId(Integer weightUnitId) {
        this.weightUnitId = weightUnitId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getProducerId() {
        return producerId;
    }

    public void setProducerId(BigInteger producerId) {
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
        return "com.cosmos.acacia.crm.data.test1.Product[productId=" + productId + "]";
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
