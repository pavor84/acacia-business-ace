/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
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
 * @author Miro
 */
@Entity
@Table(name = "complex_product_items")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "ComplexProductItem.findItemsByComplexProductAndDeleted",
                query = "select t from ComplexProductItem t" +
                        " where t.complexProduct = :complexProduct" +
                        " and t.dataObject.deleted = :deleted" +
                        " order by t.orderPosition"
            ),
        @NamedQuery
            (
                name = "ComplexProductItem.findItemsCountByComplexProductAndDeleted",
                query = "select count(*) from ComplexProductItem t" +
                        " where t.complexProduct = :complexProduct" +
                        " and t.dataObject.deleted = :deleted"
                        //" order by t.orderPosition"// edit: no order-by for not-grouped column
            )
    })
public class ComplexProductItem
    extends DataObjectBean
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "complex_product_item_id", nullable = false)
    private BigInteger complexProductItemId;

    @Column(name = "order_position")
    @Property(title="Order Position", editable=false, readOnly=true, visible=false, hidden=true)
    private int orderPosition;

    @Column(name = "quantity", nullable = false)
    @Property(title="Quantity", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal quantity;
    
    @Column(name = "dueQuantity")
    private BigDecimal dueQuantity;

    @Column(name = "unit_price", nullable = false)
    @Property(title="Unit Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal unitPrice;

    @Column(name = "item_price", nullable = false)
    @Property(title="Item Price", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal itemPrice;

    @JoinColumn(name = "applied_algorithm_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Applied Algorithm")
    private DbResource appliedAlgorithm;

    @Column(name = "applied_value")
    @Property(title="Applied Value")
    private Serializable appliedValue;

    @JoinColumn(name = "complex_product_id", referencedColumnName = "product_id")
    @ManyToOne
    @Property(title="Complex Product",
        editable=false, readOnly=true, visible=false, hidden=true,
        customDisplay="${complexProduct.productCode}:${complexProduct.productName}")
    private ComplexProduct complexProduct;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    @Property(title="Product",
        customDisplay="${product.productCode}:${product.productName}")
    private Product product;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public ComplexProductItem()
    {
    }

    public ComplexProductItem(BigInteger complexProductItemId)
    {
        this.complexProductItemId = complexProductItemId;
    }

    public ComplexProductItem(ComplexProduct complexProduct)
    {
        this.complexProduct = complexProduct;
    }

    public BigInteger getComplexProductItemId() {
        return complexProductItemId;
    }

    public void setComplexProductItemId(BigInteger complexProductItemId) {
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

    public DbResource getAppliedAlgorithm() {
        return appliedAlgorithm;
    }

    public void setAppliedAlgorithm(DbResource appliedAlgorithm) {
        this.appliedAlgorithm = appliedAlgorithm;
    }

    public Object getAppliedValue() {
        return appliedValue;
    }

    public void setAppliedValue(Serializable appliedValue) {
        this.appliedValue = appliedValue;
    }

    public ComplexProduct getComplexProduct()
    {
        return complexProduct;
    }

    public void setComplexProduct(ComplexProduct complexProduct)
    {
        this.complexProduct = complexProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public BigInteger getParentId()
    {
        if(complexProduct == null)
            return null;

        return complexProduct.getProductId();
    }

    @Override
    public void setParentId(BigInteger parentId)
    {
        //this.parentId = parentId;
    }

    @Override
    public BigInteger getId()
    {
        return getComplexProductItemId();
    }

    @Override
    public void setId(BigInteger id)
    {
        setComplexProductItemId(id);
    }

    @Override
    public DataObject getDataObject()
    {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject)
    {
        this.dataObject = dataObject;
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

    @Override
    public String getInfo() {
        return getComplexProduct().getProductName();
    }

    public BigDecimal getDueQuantity() {
        return dueQuantity;
    }

    public void setDueQuantity(BigDecimal dueQuantity) {
        this.dueQuantity = dueQuantity;
    }
    
}
