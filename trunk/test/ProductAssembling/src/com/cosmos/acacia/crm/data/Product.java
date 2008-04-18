/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Miro
 * 
    select
        product0_.product_id as product1_0_,
        product0_.product_price as product2_0_,
        product0_.product_type as product3_0_,
        product0_1_.product_code as product2_1_,
        product0_2_.product_code as product2_2_,
        case
            when product0_1_.product_id is not null
                then 1
            when product0_2_.product_id is not null
                then 2
            when product0_.product_id is not null
                then 0
        end as clazz_
    from products product0_ 
        left outer join complex_products product0_1_
            on product0_.product_id = product0_1_.product_id
        left outer join simple_products product0_2_
            on product0_.product_id = product0_2_.product_id
 *
 * 
 */
@Entity
@Table(name = "products")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="product_type")
public abstract class Product
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name="ProductsSequenceGenerator",
        sequenceName="products_seq",
        allocationSize=1)
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="ProductsSequenceGenerator")
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_price")
    private BigDecimal productPrice;


    public Product()
    {
    }

    public Product(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
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
        return "test.data.Product[productId=" + productId + "]";
    }

}

