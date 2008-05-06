/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "assembling_categories")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "AssemblingCategory.findByCategoryCode",
                query = "select ac from AssemblingCategory ac where ac.categoryCode = :categoryCode"
            )
    })
public class AssemblingCategory
    extends DataObjectBean
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "assembling_category_id", nullable = false)
    private BigInteger assemblingCategoryId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "category_code", nullable = false)
    private String categoryCode;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public AssemblingCategory() {
    }

    public AssemblingCategory(BigInteger assemblingCategoryId) {
        this.assemblingCategoryId = assemblingCategoryId;
    }

    public BigInteger getAssemblingCategoryId() {
        return assemblingCategoryId;
    }

    public void setAssemblingCategoryId(BigInteger assemblingCategoryId) {
        this.assemblingCategoryId = assemblingCategoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assemblingCategoryId != null ? assemblingCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssemblingCategory)) {
            return false;
        }
        AssemblingCategory other = (AssemblingCategory) object;
        if ((this.assemblingCategoryId == null && other.assemblingCategoryId != null) || (this.assemblingCategoryId != null && !this.assemblingCategoryId.equals(other.assemblingCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.AssemblingCategory[assemblingCategoryId=" + assemblingCategoryId + "]";
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
        return getAssemblingCategoryId();
    }

    @Override
    public void setId(BigInteger id)
    {
        setAssemblingCategoryId(id);
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
