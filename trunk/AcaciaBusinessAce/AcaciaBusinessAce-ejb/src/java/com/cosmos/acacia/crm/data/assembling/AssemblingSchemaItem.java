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
import com.cosmos.acacia.crm.data.DbResource;
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
@Table(name = "assembling_schema_items")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "AssemblingSchemaItem.findBySchemaAndMessageCode",
                query = "select t1 from AssemblingSchemaItem t1" +
                        " where t1.assemblingSchema = :assemblingSchema" +
                        " and t1.messageCode = :messageCode" +
                        " and t1.dataObject.deleted = :deleted"
            ),
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

    @Id
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Column(name = "item_id", nullable = false)
    private BigInteger itemId;

    @JoinColumn(name = "assembling_schema_id", referencedColumnName = "product_id", nullable=false)
    @ManyToOne
    @Property(title="Schema", propertyValidator=@PropertyValidator(required=true))
    private AssemblingSchema assemblingSchema;

    @JoinColumn(name = "algorithm_id", referencedColumnName = "resource_id", nullable=false)
    @ManyToOne
    @Property(title="Algorithm", propertyValidator=@PropertyValidator(required=true))
    private DbResource assemblingAlgorithm;

    @Column(name = "message_code", nullable = false)
    @Property(
        title="Message Code",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=50,
            required=true))
    private String messageCode;

    @Column(name = "message_text", nullable = false)
    @Property(
        title="Message Text",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String messageText;

    @JoinColumn(name = "data_type_id", referencedColumnName = "resource_id", nullable=false)
    @ManyToOne
    @Property(title="Data Type", propertyValidator=@PropertyValidator(required=true))
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

    public AssemblingSchemaItem(BigInteger itemId) {
        this.itemId = itemId;
    }

    public BigInteger getItemId() {
        return itemId;
    }

    public void setItemId(BigInteger itemId) {
        this.itemId = itemId;
    }

    public DbResource getDataType() {
        return dataType;
    }

    public void setDataType(DbResource dataType) {
        this.dataType = dataType;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
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

    public Object getDefaultValue() {
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

    @Override
    public BigInteger getParentId()
    {
        if(assemblingSchema != null)
            return assemblingSchema.getProductId();

        return null;
    }

    @Override
    public void setParentId(BigInteger parentId)
    {
    }

    @Override
    public BigInteger getId()
    {
        return getItemId();
    }

    @Override
    public void setId(BigInteger id)
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
}
