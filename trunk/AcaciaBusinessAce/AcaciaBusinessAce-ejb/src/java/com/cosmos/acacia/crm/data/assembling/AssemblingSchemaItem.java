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
import com.cosmos.acacia.crm.enums.DatabaseResource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public enum DataType
        implements DatabaseResource
    {
        IntegerType("IntegerType"),
        DecimalType("DecimalType"),
        DateType("DateType"),
        StringType("StringType");

        private DataType(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        @Override
        public DbResource getDbResource() {
            return dbResource;
        }

        @Override
        public void setDbResource(DbResource resource) {
            this.dbResource = resource;
        }

        private String name;
        private DbResource dbResource;

        @Override
        public String toShortText()
        {
            return getName();
        }

        @Override
        public String toText()
        {
            return getName();
        }

        private static SimpleDateFormat dateFormat = new SimpleDateFormat();

        public Serializable toDataType(Serializable source)
        {
            if(source == null)
                throw new IllegalArgumentException("The source can not be null.");

            if(source instanceof String)
                source = ((String)source).trim();

            switch(this)
            {
                case IntegerType:
                    if(source instanceof Integer)
                        return source;
                    if(source instanceof String)
                        return Integer.valueOf((String)source);
                    throw new NumberFormatException(String.valueOf(source));

                case DecimalType:
                    if(source instanceof BigDecimal)
                        return source;
                    if(source instanceof Number)
                        return new BigDecimal(((Number)source).doubleValue());
                    if(source instanceof String)
                        return new BigDecimal((String)source);
                    return new BigDecimal(String.valueOf(source));

                case DateType:
                    if(source instanceof Date)
                        return source;
                    try
                    {
                        if(source instanceof String)
                            return dateFormat.parse((String)source);
                        return dateFormat.parse(String.valueOf(source));
                    }
                    catch(ParseException ex)
                    {
                        throw new IllegalArgumentException(ex);
                    }

                case StringType:
                    if(source instanceof String)
                        return source;
                    return String.valueOf(source);
            }

            return null;
        }

        private static List<DbResource> dbResources;
        public static List<DbResource> getDbResources()
        {
            if(dbResources == null)
            {
                dbResources = new ArrayList<DbResource>(DataType.values().length);

                for(DataType item : DataType.values())
                {
                    dbResources.add(item.getDbResource());
                }
            }

            return dbResources;
        }
    }


    @Id
    @Property(title="Item Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Column(name = "item_id", nullable = false)
    private BigInteger itemId;

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
    public BigInteger getParentId()
    {
        if(assemblingSchema != null)
            return assemblingSchema.getProductId();

        return null;
    }

    @Override
    public void setParentId(BigInteger parentId)
    {
        throw new UnsupportedOperationException();
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
    
    @Override
    public String getInfo()
    {
        if(assemblingAlgorithm == null)
            return "";

        return assemblingAlgorithm.toString();
    }
}
