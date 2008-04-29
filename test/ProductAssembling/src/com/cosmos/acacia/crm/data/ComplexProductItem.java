/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.data.assembling.AssemblingAlgorithm;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "complex_product_items")
@NamedQueries({})
public class ComplexProductItem
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
    @Column(name = "complex_product_item_id", nullable = false)
    private Long complexProductItemId;

    @Column(name = "order_position")
    private int orderPosition;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "item_price", nullable = false)
    private BigDecimal itemPrice;

    @JoinColumn(name = "applied_algorithm_id", referencedColumnName = "algorithm_id")
    @ManyToOne
    private AssemblingAlgorithm appliedAlgorithm;

    @Column(name = "applied_value")
    private Serializable appliedValue;

    @JoinColumn(name = "complex_product_id", referencedColumnName = "product_id")
    @ManyToOne
    private ComplexProduct complexProduct;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private Product product;


    public ComplexProductItem()
    {
    }

    public ComplexProductItem(Long complexProductItemId)
    {
        this.complexProductItemId = complexProductItemId;
    }

    public ComplexProductItem(ComplexProduct complexProduct)
    {
        this.complexProduct = complexProduct;
    }

    public Long getComplexProductItemId() {
        return complexProductItemId;
    }

    public void setComplexProductItemId(Long complexProductItemId) {
        this.complexProductItemId = complexProductItemId;
    }

    public int getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public AssemblingAlgorithm getAppliedAlgorithm() {
        return appliedAlgorithm;
    }

    public void setAppliedAlgorithm(AssemblingAlgorithm appliedAlgorithm) {
        this.appliedAlgorithm = appliedAlgorithm;
    }

    public Object getAppliedValue() {
        return appliedValue;
    }

    public void setAppliedValue(Serializable appliedValue) {
        this.appliedValue = appliedValue;
    }

    public ComplexProduct getComplexProduct() {
        return complexProduct;
    }

    public void setComplexProduct(ComplexProduct complexProduct) {
        this.complexProduct = complexProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (complexProductItemId != null ? complexProductItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComplexProductItem)) {
            return false;
        }
        ComplexProductItem other = (ComplexProductItem) object;
        if ((this.complexProductItemId == null && other.complexProductItemId != null) || (this.complexProductItemId != null && !this.complexProductItemId.equals(other.complexProductItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.ComplexProductItem[complexProductItemId=" + complexProductItemId + "]";
    }

}
