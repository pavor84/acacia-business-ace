/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import java.io.Serializable;
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
 */
@Entity
@Table(name = "virtual_products")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="product_type")
public abstract class VirtualProduct
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name="DataObjectsSequenceGenerator",
        sequenceName="data_objects_seq",
        allocationSize=1)
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="DataObjectsSequenceGenerator")
    @Column(name = "product_id", nullable = false)
    private Long productId;


    public VirtualProduct()
    {
    }

    public VirtualProduct(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        if (!(object instanceof VirtualProduct)) {
            return false;
        }
        VirtualProduct other = (VirtualProduct) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.VirtualProduct[productId=" + productId + "]";
    }

}
