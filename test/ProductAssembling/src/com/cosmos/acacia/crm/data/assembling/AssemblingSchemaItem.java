/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
                        " where t1.assemblingSchema = :assemblingSchema and t1.messageCode = :messageCode"
            ),
        @NamedQuery
            (
                name = "AssemblingSchemaItem.findByAssemblingSchema",
                query = "select t1 from AssemblingSchemaItem t1 where t1.assemblingSchema = :assemblingSchema"
            )
    })
public class AssemblingSchemaItem
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name="AssemblingSchemaItemsSequenceGenerator",
        sequenceName="assembling_schema_items_seq",
        allocationSize=1)
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="AssemblingSchemaItemsSequenceGenerator")
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @JoinColumn(name = "assembling_schema_id", referencedColumnName = "product_id")
    @ManyToOne
    private AssemblingSchema assemblingSchema;

    @JoinColumn(name = "algorithm_id", referencedColumnName = "algorithm_id")
    @ManyToOne
    private AssemblingAlgorithm assemblingAlgorithm;

    @Column(name = "message_code", nullable = false)
    private String messageCode;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    /**
     * Data Type:
     *   Integer
     *   Decimal
     *   Date
     *   String
     */
    @Column(name = "data_type")
    private String dataType;

    @Column(name = "min_selections")
    private Integer minSelections;

    @Column(name = "max_selections")
    private Integer maxSelections;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity = BigDecimal.ONE;

    @Column(name = "default_value")
    private Serializable defaultValue;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assemblingSchemaItem", fetch=FetchType.EAGER)
    private List<AssemblingSchemaItemValue> itemValues;


    public AssemblingSchemaItem() {
    }

    public AssemblingSchemaItem(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
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

    public AssemblingAlgorithm getAssemblingAlgorithm() {
        return assemblingAlgorithm;
    }

    public void setAssemblingAlgorithm(AssemblingAlgorithm assemblingAlgorithm) {
        this.assemblingAlgorithm = assemblingAlgorithm;
    }

    public AssemblingSchema getAssemblingSchema() {
        return assemblingSchema;
    }

    public void setAssemblingSchema(AssemblingSchema assemblingSchema) {
        this.assemblingSchema = assemblingSchema;
    }

    public List<AssemblingSchemaItemValue> getItemValues() {
        return itemValues;
    }

    public void setItemValues(List<AssemblingSchemaItemValue> itemValues) {
        this.itemValues = itemValues;
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

}
