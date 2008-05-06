/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_schema_item_values")
@NamedQueries({})
public class AssemblingSchemaItemValue
    extends DataObjectBean
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "item_value_id", nullable = false)
    private BigInteger itemValueId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

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

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public AssemblingSchemaItemValue() {
    }

    public AssemblingSchemaItemValue(BigInteger itemValueId) {
        this.itemValueId = itemValueId;
    }

    public BigInteger getItemValueId() {
        return itemValueId;
    }

    public void setItemValueId(BigInteger itemValueId) {
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

    @Override
    public BigInteger getParentId()
    {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId)
    {
        this.parentId = parentId;
    }

    @Override
    public BigInteger getId()
    {
        return getItemValueId();
    }

    @Override
    public void setId(BigInteger id)
    {
        setItemValueId(id);
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
}
