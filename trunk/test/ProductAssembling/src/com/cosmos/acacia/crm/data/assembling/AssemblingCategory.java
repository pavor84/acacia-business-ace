/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name="DataObjectsSequenceGenerator",
        sequenceName="data_objects_seq",
        allocationSize=1)
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="DataObjectsSequenceGenerator")
    @Column(name = "assembling_category_id", nullable = false)
    private Long assemblingCategoryId;

    @Column(name = "category_code", nullable = false)
    private String categoryCode;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;


    public AssemblingCategory() {
    }

    public AssemblingCategory(Long assemblingCategoryId) {
        this.assemblingCategoryId = assemblingCategoryId;
    }

    public AssemblingCategory(Long assemblingCategoryId, String categoryCode, String categoryName) {
        this.assemblingCategoryId = assemblingCategoryId;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public Long getAssemblingCategoryId() {
        return assemblingCategoryId;
    }

    public void setAssemblingCategoryId(Long assemblingCategoryId) {
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

}
