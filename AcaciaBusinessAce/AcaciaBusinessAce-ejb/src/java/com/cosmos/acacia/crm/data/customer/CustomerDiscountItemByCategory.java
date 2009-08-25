/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.customer;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.product.ProductCategory;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "customer_discount_items_by_categories",
    //catalog = "acacia_test",
    //schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_discount_id", "category_id"})}
)
@DiscriminatorValue(value = "C")
@PrimaryKeyJoinColumn(name="customer_discount_item_id",referencedColumnName="customer_discount_item_id")
@NamedQueries({
    @NamedQuery(
        name = "CustomerDiscountItemByCategory.findByCustomerDiscountAndCategory",
        query = "SELECT t" +
            " FROM CustomerDiscountItemByCategory t" +
            " where" +
            "  t.customerDiscount = :customerDiscount" +
            "  and t.category = :category")
})
public class CustomerDiscountItemByCategory extends CustomerDiscountItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Property(title="Category", propertyValidator=@PropertyValidator(required=true), customDisplay="${category.categoryName}")
    @JoinColumn(name = "category_id", referencedColumnName = "product_category_id", nullable = false)
    @ManyToOne(optional = false)
    private ProductCategory category;

    @Property(title="Include Heirs")
    @Basic(optional = false)
    @Column(name = "include_heirs", nullable = false)
    private boolean includeHeirs;

    @JoinColumn(name = "customer_discount_id", referencedColumnName = "customer_discount_id", nullable = false)
    @ManyToOne(optional = false)
    private CustomerDiscount customerDiscount;

    public CustomerDiscountItemByCategory() {
    }

    public CustomerDiscountItemByCategory(UUID customerDiscountItemId) {
        this.customerDiscountItemId = customerDiscountItemId;
    }

    public CustomerDiscountItemByCategory(UUID customerDiscountItemId, boolean includeHeirs) {
        this.customerDiscountItemId = customerDiscountItemId;
        this.includeHeirs = includeHeirs;
    }

    @Override
    public boolean isIncludeHeirs() {
        return includeHeirs;
    }

    @Override
    public void setIncludeHeirs(boolean includeHeirs) {
        this.includeHeirs = includeHeirs;
    }

    @Override
    public CustomerDiscount getCustomerDiscount() {
        return customerDiscount;
    }

    @Override
    public void setCustomerDiscount(CustomerDiscount customerDiscount) {
        this.customerDiscount = customerDiscount;
        if(customerDiscount != null) {
            setParentId(customerDiscount.getCustomerDiscountId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public ProductCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        return (customerDiscountItemId != null ? customerDiscountItemId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDiscountItemByCategory)) {
            return false;
        }
        CustomerDiscountItemByCategory other = (CustomerDiscountItemByCategory) object;
        if ((this.customerDiscountItemId == null && other.customerDiscountItemId != null) || (this.customerDiscountItemId != null && !this.customerDiscountItemId.equals(other.customerDiscountItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public Product getProduct() {
        return null;
    }

    @Override
    public void setProduct(Product product) {
    }
}
