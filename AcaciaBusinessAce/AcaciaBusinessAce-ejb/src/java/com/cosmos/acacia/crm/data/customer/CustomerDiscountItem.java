/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.customer;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ProductCategory;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
select
  cdi.customer_discount_item_id,
  cdi.discount_percent,
  cdi.discriminator_id,
  cdiByCategory.category_id,
  cdiByCategory.customer_discount_id as category_cd_id,
  cdiByCategory.include_heirs,
  cdiByProduct.customer_discount_id as product_cd_id,
  cdiByProduct.product_id,
  case
   when cdiByCategory.customer_discount_item_id is not null then 1
   when cdiByProduct.customer_discount_item_id is not null then 2
   when cdi.customer_discount_item_id is not null then 0
  end as clazz_
 from customer_discount_items cdi
  left outer join customer_discount_items_by_categories cdiByCategory
   on cdi.customer_discount_item_id=cdiByCategory.customer_discount_item_id
  left outer join customer_discount_items_by_products cdiByProduct
   on cdi.customer_discount_item_id=cdiByProduct.customer_discount_item_id
 where
  cdiByCategory.customer_discount_id=1236033606093
  or cdiByProduct.customer_discount_id=1236033606093
*/

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "customer_discount_items")//, catalog = "acacia_test", schema = "public")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="discriminator_id")
@NamedQueries({
    @NamedQuery(
        name = "CustomerDiscountItem.findByCustomerDiscount",
        query = "SELECT t1" +
            " FROM CustomerDiscountItem t1" +
            " where t1.customerDiscount = :customerDiscount"
    ),
    @NamedQuery(
        name = "CustomerDiscountItem.findByParentId",
        query = "SELECT t1" +
            " FROM CustomerDiscountItem t1" +
            " where t1.dataObject.parentDataObjectId = :parentDataObjectId"
    )
})
public abstract class CustomerDiscountItem extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;

    public static final String SQL_SELECT_CUSTOMER_DISCOUNT_ITEMS_BY_CUSTOMER_DISCOUNT =
            "select" +
            "  cdi.customer_discount_item_id," +
            "  cdi.discount_percent," +
            "  cdi.discriminator_id," +
            "  cdiByCategory.category_id," +
            //"  cdiByCategory.customer_discount_id as category_cd_id," +
            "  cdiByCategory.include_heirs," +
            //"  cdiByProduct.customer_discount_id as product_cd_id," +
            "  cdiByProduct.product_id," +
            "  case" +
            "   when cdiByCategory.customer_discount_item_id is not null then 1" +
            "   when cdiByProduct.customer_discount_item_id is not null then 2" +
            "   when cdi.customer_discount_item_id is not null then 0" +
            "  end as clazz_" +
            " from customer_discount_items cdi" +
            "  left outer join customer_discount_items_by_categories cdiByCategory" +
            "   on cdi.customer_discount_item_id=cdiByCategory.customer_discount_item_id" +
            "  left outer join customer_discount_items_by_products cdiByProduct" +
            "   on cdi.customer_discount_item_id=cdiByProduct.customer_discount_item_id" +
            " where" +
            "  cdiByCategory.customer_discount_id = ?" +
            "  or cdiByProduct.customer_discount_id = ?";

    @Id
    @Basic(optional = false)
    @Column(name = "customer_discount_item_id", nullable = false)
    protected BigInteger customerDiscountItemId;

    @Property(title="Discount %", percent=true)
    @Basic(optional = false)
    @Column(name = "discount_percent", nullable = false, precision = 7, scale = 6)
    private BigDecimal discountPercent;

    @Property(title="Product", customDisplay="${product.productName}")
    @Transient
    private Product product;

    @Property(title="Category", customDisplay="${category.categoryName}")
    @Transient
    private ProductCategory category;

    @Property(title="Include Heirs")
    @Transient
    private boolean includeHeirs;

    @Transient
    @Property(title="Cutomer Price", editable=false, readOnly=true)
    private BigDecimal cutomerPrice;

    @Transient
    private BigDecimal discount;

    @Column(name = "discriminator_id", length = 2)
    private String discriminatorId;

    @JoinColumn(name = "customer_discount_item_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    @Transient
    private String className;

    public CustomerDiscountItem() {
    }

    public CustomerDiscountItem(BigInteger customerDiscountItemId) {
        this.customerDiscountItemId = customerDiscountItemId;
    }

    public CustomerDiscountItem(BigInteger customerDiscountItemId, BigDecimal discountPercent) {
        this.customerDiscountItemId = customerDiscountItemId;
        this.discountPercent = discountPercent;
    }

    public BigInteger getCustomerDiscountItemId() {
        return customerDiscountItemId;
    }

    public void setCustomerDiscountItemId(BigInteger customerDiscountItemId) {
        this.customerDiscountItemId = customerDiscountItemId;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDiscriminatorId() {
        return discriminatorId;
    }

    public void setDiscriminatorId(String discriminatorId) {
        this.discriminatorId = discriminatorId;
    }

    @JoinColumn(name = "customer_discount_id", referencedColumnName = "customer_discount_id", nullable = false)
    @ManyToOne(optional = false)
    public abstract CustomerDiscount getCustomerDiscount();
    public abstract void setCustomerDiscount(CustomerDiscount customerDiscount);

    public abstract boolean isIncludeHeirs();
    public abstract void setIncludeHeirs(boolean includeHeirs);
    public abstract ProductCategory getCategory();
    public abstract void setCategory(ProductCategory category);

    public abstract Product getProduct();
    public abstract void setProduct(Product product);

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        return (customerDiscountItemId != null ? customerDiscountItemId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDiscountItem)) {
            return false;
        }
        CustomerDiscountItem other = (CustomerDiscountItem) object;
        if ((this.customerDiscountItemId == null && other.customerDiscountItemId != null) || (this.customerDiscountItemId != null && !this.customerDiscountItemId.equals(other.customerDiscountItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(className == null) {
            className = getClass().getSimpleName();
        }

        return className + "[id=" + customerDiscountItemId + "]";
    }

    @Override
    public BigInteger getId() {
        return getCustomerDiscountItemId();
    }

    @Override
    public void setId(BigInteger id) {
        setCustomerDiscountItemId(id);
    }

    @Override
    public BigInteger getParentId() {
        CustomerDiscount customerDiscount;
        if((customerDiscount = getCustomerDiscount()) != null) {
            return customerDiscount.getCustomerDiscountId();
        }

        if(dataObject != null) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return toString();
    }

    public BigDecimal getDiscount() {
        BigDecimal value;
        if ((value = getDiscountPercent()) != null) {
            return value;
        }

        return getCustomerDiscount().getDiscountPercent();
    }

    public void setDiscount(BigDecimal discount) {
    }

    public BigDecimal getCutomerPrice() {
        BigDecimal salesPrice;
        if((product = getProduct()) == null || (salesPrice = product.getSalesPrice()) == null) {
            return null;
        }

        BigDecimal percent;
        if ((percent = getDiscount()) != null) {
            return salesPrice.subtract(salesPrice.multiply(percent, MATH_CONTEXT), MATH_CONTEXT);
        }

        return salesPrice;
    }

    public void setCutomerPrice(BigDecimal cutomerPrice) {
    }
}
