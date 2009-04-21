/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.customer;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ProductCategory;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "customer_discount_items_by_products",
    //catalog = "acacia_test",
    //schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_discount_id", "product_id"})}
)
@DiscriminatorValue(value = "P")
@PrimaryKeyJoinColumn(name="customer_discount_item_id",referencedColumnName="customer_discount_item_id")
@NamedQueries({
    @NamedQuery(
        name = "CustomerDiscountItemByProduct.findByCustomerDiscountAndProduct",
        query = "SELECT t" +
            " FROM CustomerDiscountItemByProduct t" +
            " where" +
            "  t.customerDiscount = :customerDiscount" +
            "  and t.product = :product")
})
public class CustomerDiscountItemByProduct extends CustomerDiscountItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Property(title="Product", propertyValidator=@PropertyValidator(required=true), customDisplay="${product.productName}")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @ManyToOne(optional = false)
    private Product product;

    @JoinColumn(name = "customer_discount_id", referencedColumnName = "customer_discount_id", nullable = false)
    @ManyToOne(optional = false)
    private CustomerDiscount customerDiscount;

    public CustomerDiscountItemByProduct() {
    }

    public CustomerDiscountItemByProduct(BigInteger customerDiscountItemId) {
        super(customerDiscountItemId);
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
    public Product getProduct() {
        return product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public ProductCategory getCategory() {
        return null;
    }

    @Override
    public void setCategory(ProductCategory category) {
    }

    @Override
    public boolean isIncludeHeirs() {
        return false;
    }

    @Override
    public void setIncludeHeirs(boolean includeHeirs) {
    }

}
