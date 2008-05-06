/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

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
            )
    })
public class AssemblingSchema
    extends VirtualProduct
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "category_id", referencedColumnName = "assembling_category_id")
    @ManyToOne
    private AssemblingCategory assemblingCategory;

    @Column(name = "schema_code", nullable = false)
    private String schemaCode;

    @Column(name = "schema_name", nullable = false)
    private String schemaName;

    @Column(name = "is_obsolete", nullable = false)
    private boolean obsolete;

    @Column(name = "description")
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

    public void setAssemblingCategory(AssemblingCategory assemblingCategory) {
        this.assemblingCategory = assemblingCategory;
    }


    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.AssemblingSchema[productId=" + getProductId() + "]";
    }

}
