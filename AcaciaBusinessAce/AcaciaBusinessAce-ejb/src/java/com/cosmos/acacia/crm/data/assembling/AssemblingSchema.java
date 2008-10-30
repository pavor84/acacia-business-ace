/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DbResource;
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
                name = "AssemblingSchema.findByAssemblingCategoryAndApplicable",
                query = "select t1 from AssemblingSchema t1" +
                        " where t1.assemblingCategory = :assemblingCategory" +
                        " and t1.applicable = true" +
                        " and t1.dataObject.deleted = :deleted"
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
                name = "AssemblingSchema.findByParentIdAndApplicable",
                query = "select t1 from AssemblingSchema t1" +
                        " where t1.parentId = :parentId" +
                        " and t1.applicable = true" +
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

    @JoinColumn(name = "measure_unit_id", nullable=false, referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Measure Unit")
    private DbResource measureUnit;

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

    @Column(name = "is_applicable", nullable = false)
    @Property(title="Applicable")
    private boolean applicable;

    @Column(name = "is_obsolete", nullable = false)
    @Property(title="Obsolete")
    private boolean obsolete;

    @Column(name = "description")
    @Property(title="Description")
    private String description;


    public AssemblingSchema() {
    }

    public DbResource getMeasureUnit()
    {
        return measureUnit;
    }

    public void setMeasureUnit(DbResource measureUnit)
    {
        firePropertyChange("measureUnit", this.measureUnit, measureUnit);
        this.measureUnit = measureUnit;
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

    public boolean isApplicable() {
        return applicable;
    }

    public void setApplicable(boolean applicable) {
        this.applicable = applicable;
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
    
    @Override
    public String getInfo()
    {
        return getSchemaName();
    }
}
