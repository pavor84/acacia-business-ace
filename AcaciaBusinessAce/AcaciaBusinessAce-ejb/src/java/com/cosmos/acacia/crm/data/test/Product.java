/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "products")
@NamedQueries({@NamedQuery(name = "Product.findByProductId", query =
                                                             "SELECT p FROM Product p WHERE p.productId = :productId"),
               @NamedQuery(name = "Product.findByProductName", query =
                                                               "SELECT p FROM Product p WHERE p.productName = :productName")})
public class Product implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id", nullable = false)
    private BigDecimal productId;
    @Column(name = "product_name", nullable = false)
    private String productName;

    public Product() {
    }

    public Product(BigDecimal productId) {
        this.productId = productId;
    }

    public Product(BigDecimal productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public BigDecimal getProductId() {
        return productId;
    }

    public void setProductId(BigDecimal productId) {
        if(!isDifferent(productId, this.productId))
            return;

        BigDecimal oldProductId = this.productId;
        this.productId = productId;
        changeSupport.firePropertyChange("productId", oldProductId,
                                         productId);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if(!isDifferent(productName, this.productName))
            return;

        String oldProductName = this.productName;
        this.productName = productName;
        changeSupport.firePropertyChange("productName", oldProductName,
                                         productName);
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
        if ((this.productId == null && other.productId != null) ||
            (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.Product[productId=" + productId +
               "]";
    }

    protected boolean isDifferent(Object first, Object second)
    {
        if(first == null)
        {
            return second != null;
        }
        else if(second == null)
        {
            return first != null;
        }

        return first.equals(second);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}
