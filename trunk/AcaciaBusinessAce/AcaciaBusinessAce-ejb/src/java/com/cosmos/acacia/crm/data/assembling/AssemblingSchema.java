/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_schemas")
@DiscriminatorValue(value="AS")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "AssemblingSchema.findBySchemaCode",
                query = "select t1 from AssemblingSchema t1 where t1.schemaCode = :schemaCode"
            ),
        @NamedQuery
            (
                name = "AssemblingSchema.findByAssemblingCategory",
                query = "select t1 from AssemblingSchema t1" +
                        " where t1.assemblingCategory = :assemblingCategory" +
                        " and t1.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "AssemblingSchema.findByParentId",
                query = "select t1 from AssemblingSchema t1" +
                        " where t1.parentId = :parentId" +
                        " and t1.dataObject.deleted = :deleted"
            )
    })
public class AssemblingSchema
    extends VirtualProduct
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "category_id", referencedColumnName = "assembling_category_id")
    @ManyToOne
    @Property(
        title="Category",
        visible=false,
        propertyValidator=@PropertyValidator(required=true))
    private AssemblingCategory assemblingCategory;

    @Column(name = "schema_code", nullable = false)
    @Property(
        title="Schema Code",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=50,
            required=true))
    private String schemaCode;

    @Column(name = "schema_name", nullable = false)
    @Property(
        title="Schema Name",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String schemaName;

    @Column(name = "is_obsolete", nullable = false)
    @Property(title="Obsolete")
    private boolean obsolete;

    @Column(name = "description")
    @Property(title="Description")
    private String description;


    public AssemblingSchema() {
    }

    public String getSchemaCode() {
        return schemaCode;
    }

    public void setSchemaCode(String schemaCode) {
        this.schemaCode = schemaCode;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssemblingCategory getAssemblingCategory() {
        return assemblingCategory;
    }

    public void setAssemblingCategory(AssemblingCategory assemblingCategory)
    {
        this.assemblingCategory = assemblingCategory;
    }


    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.AssemblingSchema[productId=" + getProductId() + "]";
    }

    @Override
    public String getCategoryName()
    {
        AssemblingCategory category;
        if((category = getAssemblingCategory()) != null)
            return category.getCategoryName();

        return null;
    }

    @Override
    public String getProductCode()
    {
        return getSchemaCode();
    }

    @Override
    public String getProductName()
    {
        return getSchemaName();
    }

    @Override
    public String getProductType()
    {
        return "Schema";
    }
}
