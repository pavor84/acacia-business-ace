/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
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
import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_schema_items")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "AssemblingSchemaItem.findByAssemblingSchema",
                query = "select t1 from AssemblingSchemaItem t1" +
                        " where t1.assemblingSchema = :assemblingSchema" +
                        " and t1.dataObject.deleted = :deleted"
            )
    })
public class AssemblingSchemaItem
    extends DataObjectBean
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AssemblingSchemaItem.class);

    @Id
    @Property(title="Item Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Column(name = "item_id", nullable = false)
    @Type(type="uuid")
    private UUID itemId;

    @JoinColumn(name = "assembling_schema_id", referencedColumnName = "product_id", nullable=false)
    @ManyToOne
    @Property(
        title="Schema",
        visible=false,
        propertyValidator=@PropertyValidator(required=true))
    private AssemblingSchema assemblingSchema;

    @JoinColumn(name = "algorithm_id", referencedColumnName = "resource_id", nullable=false)
    @ManyToOne
    @Property(title="Algorithm", propertyValidator=@PropertyValidator(required=true))
    private DbResource assemblingAlgorithm;

    @JoinColumn(name = "message_id", referencedColumnName = "message_id")
    @ManyToOne
    @Property(title="Message")
    private AssemblingMessage assemblingMessage;

    @JoinColumn(name = "data_type_id", referencedColumnName = "resource_id", nullable=false)
    @ManyToOne
    @Property(title="Data Type",
        //customDisplay="${dataType.enumName}",
        propertyValidator=@PropertyValidator(required=true))
    private DbResource dataType;

    @Column(name = "min_selections")
    @Property(
        title="Min. Selections",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.NUMBER_RANGE,
            minValue=0d))
    private Integer minSelections;

    @Column(name = "max_selections")
    @Property(
        title="Max. Selections",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.NUMBER_RANGE,
            minValue=0d))
    private Integer maxSelections;

    @Column(name = "quantity", nullable = false)
    @Property(
        title="Quantity",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.NUMBER_RANGE,
            minValue=1d,
            required=true))
    private BigDecimal quantity = BigDecimal.ONE;

    @Column(name = "default_value")
    @Property(title="Default Value")
    private Serializable defaultValue;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public AssemblingSchemaItem() {
    }

    public AssemblingSchemaItem(UUID itemId) {
        this.itemId = itemId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public DbResource getDataType() {
        return dataType;
    }

    public void setDataType(DbResource dataType) {
        this.dataType = dataType;
    }

    public AssemblingMessage getAssemblingMessage()
    {
        return assemblingMessage;
    }

    public void setAssemblingMessage(AssemblingMessage assemblingMessage)
    {
        this.assemblingMessage = assemblingMessage;
    }

    public Integer getMinSelections() {
        return minSelections;
    }

    public void setMinSelections(Integer minSelections) {
        this.minSelections = minSelections;
    }

    public Integer getMaxSelections() {
        return maxSelections;
    }

    public void setMaxSelections(Integer maxSelections) {
        this.maxSelections = maxSelections;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Serializable getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Serializable defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DbResource getAssemblingAlgorithm() {
        return assemblingAlgorithm;
    }

    public Algorithm.Type getAlgorithmType()
    {
        if(assemblingAlgorithm == null)
            return null;

        return (Algorithm.Type)assemblingAlgorithm.getEnumValue();
    }

    public void setAssemblingAlgorithm(DbResource assemblingAlgorithm) {
        this.assemblingAlgorithm = assemblingAlgorithm;
    }

    public AssemblingSchema getAssemblingSchema() {
        return assemblingSchema;
    }

    public void setAssemblingSchema(AssemblingSchema assemblingSchema) {
        this.assemblingSchema = assemblingSchema;
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
        return "com.cosmos.acacia.crm.data.AssemblingSchemaItem[itemId=" + itemId + "]";
    }

    public String toString(boolean debug)
    {
        if(!debug)
            return toString();

        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        sb.append("\n\t assemblingSchema: ").append(assemblingSchema.toString());
        sb.append("\n\t message: ").append(assemblingMessage);
        sb.append("\n\t dataType: ").append(dataType);
        sb.append("\n\t minSelections: ").append(minSelections);
        sb.append("\n\t maxSelections: ").append(maxSelections);
        sb.append("\n\t quantity: ").append(quantity);
        sb.append("\n\t defaultValue: ").append(defaultValue);

        return sb.toString();
    }

    @Override
    public UUID getParentId()
    {
        if(assemblingSchema != null)
            return assemblingSchema.getProductId();

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
        return getItemId();
    }

    @Override
    public void setId(UUID id)
    {
        setItemId(id);
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
        if(assemblingAlgorithm == null)
            return "";

        return assemblingAlgorithm.toString();
    }
}
