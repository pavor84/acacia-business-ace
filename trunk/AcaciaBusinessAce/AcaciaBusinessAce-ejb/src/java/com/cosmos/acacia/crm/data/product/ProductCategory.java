/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.product;

import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;
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
@Table(name = "product_categories")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "ProductCategory.findByParentDataObjectAndDeleted",
                query = "select p from ProductCategory p where p.dataObject.parentDataObjectId = :parentDataObjectId and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "ProductCategory.findByParentDataObjectIsNullAndDeleted",
                query = "select p from ProductCategory p where p.dataObject.parentDataObjectId is null and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                /**
                 * Parameters:
                 * - categoryName - find all undeleted elements with the same name (at most one should exist)
                 */
                name = "ProductCategory.findByNameNotDeleted",
                query = "select p from ProductCategory p where p.categoryName like :categoryName and p.dataObject.deleted = false"
            ),
        /**
         * Parameters:
         * - parentIds: Collection<BigInteger>
         */
        @NamedQuery
            (
                name = "ProductCategory.findChildCategories",
                query = "select p from ProductCategory p where p.dataObject.deleted = false and p.parentCategory.productCategoryId in (:parentIds)"
            )
})
public class ProductCategory extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_category_id", nullable = false)
    @Property(title="Product Category Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger productCategoryId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @Column(name = "category_name", nullable = false)
    @Property(title="Category Name", propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=50))
    private String categoryName;
    
    @Column(name = "description")
    @Property(title="Description")
    private String description;
    
    @Property(title="Discount %", percent=true)
    @Column(name = "discount_percent", precision=8, scale=6)
    private BigDecimal discountPercent;
    
    @Property(title="Profit %", percent=true)
    @Column(name = "profit_percent", precision=8, scale=6)
    private BigDecimal profitPercent;

    @Property(title="Customs duty %", percent=true)
    @Column(name = "customs_duty_percent", precision=8, scale=6)
    private BigDecimal customsDutyPercent;

    @Property(title="Excise duty %", percent=true)
    @Column(name = "excise_duty_percent", precision=8, scale=6)
    private BigDecimal exciseDutyPercent;

    @Property(title="Transport %", percent=true)
    @Column(name = "transport_percent", precision=8, scale=6)
    private BigDecimal transportPercent;

    @Property(title="Transport value")
    @Column(name = "transport_value", precision=19, scale=4)
    private BigDecimal transportValue;

    /*@JoinColumn(name = "product_category_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;*/
    @OneToOne
    /*@JoinColumn(
        name = "product_category_id",
        referencedColumnName = "data_object_id",
        insertable = false,
        updatable = false)*/
    @PrimaryKeyJoinColumn
    private DataObject dataObject;

    @JoinColumn(name = "pattern_mask_format_id", referencedColumnName = "pattern_mask_format_id")
    @ManyToOne
    @Property(title="Pattern Mask Format", customDisplay=
        "${patternMaskFormat.patternName} (${patternMaskFormat.format})")
    private PatternMaskFormat patternMaskFormat;
    
    @ManyToOne
    @JoinColumn(name="parent_cat_id")
    @Property(title="Parent Category", customDisplay="${parentCategory.categoryName}")
    private ProductCategory parentCategory;

    public ProductCategory() {
    }

    public ProductCategory(BigInteger productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public ProductCategory(BigInteger productCategoryId, String categoryName) {
        this.productCategoryId = productCategoryId;
        this.categoryName = categoryName;
    }

    public BigInteger getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(BigInteger productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        firePropertyChange("categoryName", this.categoryName, categoryName);
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        firePropertyChange("description", this.description, description);
        this.description = description;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public PatternMaskFormat getPatternMaskFormat() {
        return patternMaskFormat;
    }

    public void setPatternMaskFormat(PatternMaskFormat patternMaskFormat) {
        firePropertyChange("patternMaskFormat", this.patternMaskFormat, patternMaskFormat);
        this.patternMaskFormat = patternMaskFormat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productCategoryId != null ? productCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCategory)) {
            return false;
        }
        ProductCategory other = (ProductCategory) object;
        if ((this.productCategoryId == null && other.productCategoryId != null) || (this.productCategoryId != null && !this.productCategoryId.equals(other.productCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ProductCategory[productCategoryId=" + productCategoryId + "]";
    }

    @Override
    public BigInteger getId() {
        return getProductCategoryId();
    }

    @Override
    public void setId(BigInteger id) {
        setProductCategoryId(id);
    }

    @Override
    public String toShortText() {
        return null;
    }

    @Override
    public String toText() {
        return getCategoryName();
    }

    /**
     * Getter for parentCategory
     * @return ProductCategory
     */
    public ProductCategory getParentCategory() {
        return parentCategory;
    }

    /**
     * Setter for parentCategory
     * @param parentCategory - ProductCategory
     */
    public void setParentCategory(ProductCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
    
    @Override
    public String getInfo() {
        return getCategoryName();
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discount) {
        firePropertyChange("discount", this.discountPercent, discount);
        this.discountPercent = discount;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profit) {
        firePropertyChange("profit", this.profitPercent, profit);
        this.profitPercent = profit;
    }

    public BigDecimal getCustomsDutyPercent() {
        return customsDutyPercent;
    }

    public void setCustomsDutyPercent(BigDecimal customsDuty) {
        firePropertyChange("customsDuty", this.customsDutyPercent, customsDuty);
        this.customsDutyPercent = customsDuty;
    }

    public BigDecimal getExciseDutyPercent() {
        return exciseDutyPercent;
    }

    public void setExciseDutyPercent(BigDecimal exciseDuty) {
        firePropertyChange("exciseDuty", this.exciseDutyPercent, exciseDuty);
        this.exciseDutyPercent = exciseDuty;
    }

    public BigDecimal getTransportPercent() {
        return transportPercent;
    }

    public void setTransportPercent(BigDecimal transportPercent) {
        firePropertyChange("transportPercent", this.transportPercent, transportPercent);
        this.transportPercent = transportPercent;
    }

    public BigDecimal getTransportValue() {
        return transportValue;
    }

    public void setTransportValue(BigDecimal transportValue) {
        firePropertyChange("transportValue", this.transportValue, transportValue);
        this.transportValue = transportValue;
    }
}
