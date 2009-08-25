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
import com.cosmos.resource.TextResource;
import java.io.Serializable;
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
@Table(name = "assembling_categories")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "AssemblingCategory.findByParentCategory",
                query = "select t1 from AssemblingCategory t1" +
                        " where t1.parentId = :parentId" +
                        " and t1.parentCategory = :parentCategory" +
                        " and t1.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "AssemblingCategory.findByParentCategoryIsNull",
                query = "select t1 from AssemblingCategory t1" +
                        " where t1.parentId = :parentId" +
                        " and t1.parentCategory is null" +
                        " and t1.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "AssemblingCategory.findChildCountByParentCategory",
                query = "select count(t1) from AssemblingCategory t1" +
                        " where t1.parentId = :parentId" +
                        " and t1.parentCategory = :parentCategory" +
                        " and t1.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "AssemblingCategory.findChildCountByParentCategoryIsNull",
                query = "select count(t1) from AssemblingCategory t1" +
                        " where t1.parentId = :parentId" +
                        " and t1.parentCategory is null" +
                        " and t1.dataObject.deleted = :deleted"
            )
    })
public class AssemblingCategory
    extends DataObjectBean
    implements Serializable, TextResource
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "assembling_category_id", nullable = false)
    @Property(title="Assembling Category Id", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID assemblingCategoryId;

    @Column(name = "parent_id")
    @Type(type="uuid")
    private UUID parentId;

    @Column(name = "category_code", nullable = false)
    @Property(title="Category Code", propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=50))
    private String categoryCode;

    @Column(name = "category_name", nullable = false)
    @Property(title="Category Name", propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=100))
    private String categoryName;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @ManyToOne
    @JoinColumn(name="parent_category_id")
    @Property(title="Parent Category", customDisplay="${parentCategory.categoryName}")
    private AssemblingCategory parentCategory;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;


    public AssemblingCategory() {
    }

    public AssemblingCategory(UUID assemblingCategoryId) {
        this.assemblingCategoryId = assemblingCategoryId;
    }

    public UUID getAssemblingCategoryId() {
        return assemblingCategoryId;
    }

    public void setAssemblingCategoryId(UUID assemblingCategoryId) {
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

    public AssemblingCategory getParentCategory()
    {
        return parentCategory;
    }

    public void setParentCategory(AssemblingCategory parentCategory)
    {
        this.parentCategory = parentCategory;
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
    public UUID getParentId()
    {
        return parentId;
    }

    @Override
    public void setParentId(UUID parentId)
    {
        this.parentId = parentId;
    }

    @Override
    public UUID getId()
    {
        return getAssemblingCategoryId();
    }

    @Override
    public void setId(UUID id)
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

    @Override
    public String toText()
    {
        return getCategoryName();
    }

    @Override
    public String toShortText()
    {
        return null;
    }

    @Override
    public String getInfo()
    {
        return getCategoryName();
    }

}
