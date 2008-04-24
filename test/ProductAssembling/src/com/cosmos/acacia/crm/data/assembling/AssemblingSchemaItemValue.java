/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

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
@Table(name = "assembling_schema_item_values")
@NamedQueries({})
public class AssemblingSchemaItemValue
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name="AssemblingSchemaItemValuesSequenceGenerator",
        sequenceName="assembling_schema_item_values_seq",
        allocationSize=1)
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="AssemblingSchemaItemValuesSequenceGenerator")
    @Column(name = "item_value_id", nullable = false)
    private Long itemValueId;

    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne
    private AssemblingSchemaItem assemblingSchemaItem;

    //@Lob
    @Column(name = "min_constraint")
    private Serializable minConstraint;

    //@Lob
    @Column(name = "max_constraint")
    private Serializable maxConstraint;

    @JoinColumn(name = "virtual_product_id", referencedColumnName = "product_id")
    @ManyToOne
    private VirtualProduct virtualProduct;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity = BigDecimal.ONE;


    public AssemblingSchemaItemValue() {
    }

    public AssemblingSchemaItemValue(Long itemValueId) {
        this.itemValueId = itemValueId;
    }

    public Long getItemValueId() {
        return itemValueId;
    }

    public void setItemValueId(Long itemValueId) {
        this.itemValueId = itemValueId;
    }

    public Object getMinConstraint() {
        return minConstraint;
    }

    public void setMinConstraint(Serializable minConstraint) {
        this.minConstraint = minConstraint;
    }

    public Object getMaxConstraint() {
        return maxConstraint;
    }

    public void setMaxConstraint(Serializable maxConstraint) {
        this.maxConstraint = maxConstraint;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public AssemblingSchemaItem getAssemblingSchemaItem() {
        return assemblingSchemaItem;
    }

    public void setAssemblingSchemaItem(AssemblingSchemaItem assemblingSchemaItem) {
        this.assemblingSchemaItem = assemblingSchemaItem;
    }

    public VirtualProduct getVirtualProduct() {
        return virtualProduct;
    }

    public void setVirtualProduct(VirtualProduct virtualProduct) {
        this.virtualProduct = virtualProduct;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemValueId != null ? itemValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssemblingSchemaItemValue)) {
            return false;
        }
        AssemblingSchemaItemValue other = (AssemblingSchemaItemValue) object;
        if ((this.itemValueId == null && other.itemValueId != null) || (this.itemValueId != null && !this.itemValueId.equals(other.itemValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue[itemValueId=" + itemValueId + "]";
    }

}
