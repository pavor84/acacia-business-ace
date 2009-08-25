/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
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
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_schema_item_values")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "AssemblingSchemaItemValue.findByAssemblingSchemaItem",
                query = "select t1 from AssemblingSchemaItemValue t1" +
                        " where t1.assemblingSchemaItem = :assemblingSchemaItem" +
                        " and t1.dataObject.deleted = :deleted"
            )
    })
public class AssemblingSchemaItemValue
    extends DataObjectBean
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "item_value_id", nullable = false)
    //@Property(title="Item Value Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID itemValueId;

    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne
    @Property(
        title="Schema Item",
        visible=false,
        propertyValidator=@PropertyValidator(required=true))
    private AssemblingSchemaItem assemblingSchemaItem;

    @JoinColumn(name = "virtual_product_id", referencedColumnName = "product_id")
    @ManyToOne
    @Property(title="Product/Schema",
        customDisplay="${virtualProduct.productCode}:${virtualProduct.productName}",
        propertyValidator=@PropertyValidator(required=true))
    private VirtualProduct virtualProduct;

    //@Lob
    @Column(name = "min_constraint")
    @Property(title="Min. Value")
    private Serializable minConstraint;

    //@Lob
    @Column(name = "max_constraint")
    @Property(title="Max. Value")
    private Serializable maxConstraint;

    @Column(name = "quantity", nullable = false)
    @Property(
        title="Quantity",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.NUMBER_RANGE,
            minValue=1d,
            required=true))
    private BigDecimal quantity = BigDecimal.ONE;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public AssemblingSchemaItemValue() {
    }

    public AssemblingSchemaItemValue(UUID itemValueId) {
        this.itemValueId = itemValueId;
    }

    public UUID getItemValueId() {
        return itemValueId;
    }

    public void setItemValueId(UUID itemValueId) {
        this.itemValueId = itemValueId;
    }

    public Serializable getMinConstraint()
    {
        return minConstraint;
    }

    public void setMinConstraint(Serializable minConstraint)
    {
        System.out.println("setMinConstraint: " + minConstraint);
        firePropertyChange("minConstraint", this.minConstraint, minConstraint);
        this.minConstraint = minConstraint;
    }

    public Serializable getMaxConstraint()
    {
        return maxConstraint;
    }

    public void setMaxConstraint(Serializable maxConstraint)
    {
        firePropertyChange("maxConstraint", this.maxConstraint, maxConstraint);
        this.maxConstraint = maxConstraint;
    }

    public BigDecimal getQuantity()
    {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        firePropertyChange("quantity", this.quantity, quantity);
        this.quantity = quantity;
    }

    public AssemblingSchemaItem getAssemblingSchemaItem()
    {
        return assemblingSchemaItem;
    }

    public void setAssemblingSchemaItem(AssemblingSchemaItem assemblingSchemaItem)
    {
        firePropertyChange("assemblingSchemaItem", this.assemblingSchemaItem, assemblingSchemaItem);
        this.assemblingSchemaItem = assemblingSchemaItem;
    }

    public VirtualProduct getVirtualProduct()
    {
        return virtualProduct;
    }

    public void setVirtualProduct(VirtualProduct virtualProduct)
    {
        firePropertyChange("virtualProduct", this.virtualProduct, virtualProduct);
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
    public UUID getParentId()
    {
        if(assemblingSchemaItem != null)
            return assemblingSchemaItem.getItemId();

        return null;
    }

    @Override
    public void setParentId(UUID parentId)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public UUID getId()
    {
        return getItemValueId();
    }

    @Override
    public void setId(UUID id)
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
    
    @Override
    public String getInfo()
    {
        return getItemValueId().toString();
    }
}
