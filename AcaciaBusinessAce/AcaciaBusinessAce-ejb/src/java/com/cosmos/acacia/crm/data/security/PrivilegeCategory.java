/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.crm.bl.security.SecurityServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "privilege_categories", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_id", "category_name"})
})
@NamedQueries({
    @NamedQuery(
        name = "PrivilegeCategory.findAll",
        query = "SELECT p FROM PrivilegeCategory p" +
                " WHERE" +
                "  p.organization = :organization" +
                " ORDER BY p.categoryName"
    )
})
@Form(
    serviceClass=SecurityServiceRemote.class
)
public class PrivilegeCategory extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "privilege_category_id", nullable = false, precision = 19, scale = 0)
    private BigInteger privilegeCategoryId;

    @Basic(optional = false)
    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    private Organization organization;

    @JoinColumn(name = "privilege_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource privilegeType;

    @JoinColumn(name = "privilege_category_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public PrivilegeCategory() {
    }

    public PrivilegeCategory(BigInteger privilegeCategoryId) {
        this.privilegeCategoryId = privilegeCategoryId;
    }

    public PrivilegeCategory(BigInteger privilegeCategoryId, String categoryName) {
        this.privilegeCategoryId = privilegeCategoryId;
        this.categoryName = categoryName;
    }

    public BigInteger getPrivilegeCategoryId() {
        return privilegeCategoryId;
    }

    public void setPrivilegeCategoryId(BigInteger privilegeCategoryId) {
        this.privilegeCategoryId = privilegeCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public DbResource getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(DbResource privilegeType) {
        this.privilegeType = privilegeType;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public BigInteger getId() {
        return getPrivilegeCategoryId();
    }

    @Override
    public void setId(BigInteger id) {
        setPrivilegeCategoryId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(organization != null) {
            return organization.getId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return getCategoryName();
    }
}
