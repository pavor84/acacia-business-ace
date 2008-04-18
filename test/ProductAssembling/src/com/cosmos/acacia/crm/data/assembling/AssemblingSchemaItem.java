/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_schema_items")
@NamedQueries({})
public class AssemblingSchemaItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    @Column(name = "min_constraint")
    private BigDecimal minConstraint;
    @Column(name = "max_constraint")
    private BigDecimal maxConstraint;
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;
    @Column(name = "default_value")
    private String defaultValue;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "algorithm_id", referencedColumnName = "algorithm_id")
    @ManyToOne
    private AssemblingAlgorithm algorithmId;
    @JoinColumn(name = "assembling_schema_id", referencedColumnName = "product_id")
    @ManyToOne
    private AssemblingSchema assemblingSchemaId;
    @JoinColumn(name = "virtual_product_id", referencedColumnName = "product_id")
    @ManyToOne
    private VirtualProduct virtualProductId;

    public AssemblingSchemaItem() {
    }

    public AssemblingSchemaItem(Long itemId) {
        this.itemId = itemId;
    }

    public AssemblingSchemaItem(Long itemId, BigDecimal quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getMinConstraint() {
        return minConstraint;
    }

    public void setMinConstraint(BigDecimal minConstraint) {
        this.minConstraint = minConstraint;
    }

    public BigDecimal getMaxConstraint() {
        return maxConstraint;
    }

    public void setMaxConstraint(BigDecimal maxConstraint) {
        this.maxConstraint = maxConstraint;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssemblingAlgorithm getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(AssemblingAlgorithm algorithmId) {
        this.algorithmId = algorithmId;
    }

    public AssemblingSchema getAssemblingSchemaId() {
        return assemblingSchemaId;
    }

    public void setAssemblingSchemaId(AssemblingSchema assemblingSchemaId) {
        this.assemblingSchemaId = assemblingSchemaId;
    }

    public VirtualProduct getVirtualProductId() {
        return virtualProductId;
    }

    public void setVirtualProductId(VirtualProduct virtualProductId) {
        this.virtualProductId = virtualProductId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssemblingSchemaItem)) {
            return false;
        }
        AssemblingSchemaItem other = (AssemblingSchemaItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.AssemblingSchemaItem[itemId=" + itemId + "]";
    }

}
