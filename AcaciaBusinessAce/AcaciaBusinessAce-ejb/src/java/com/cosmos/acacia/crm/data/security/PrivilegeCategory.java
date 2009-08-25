/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.security.SecurityServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBTextField;
import java.io.Serializable;
import java.util.UUID;
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
import org.hibernate.annotations.Type;

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
        name = PrivilegeCategory.NQ_FIND_ALL,
        query = "SELECT p FROM PrivilegeCategory p" +
                " WHERE" +
                "  p.organization = :organization" +
                " ORDER BY p.privilegeType, p.categoryName"
    ),
    @NamedQuery(
        name = PrivilegeCategory.NQ_FIND_BY_TYPE,
        query = "SELECT p FROM PrivilegeCategory p" +
                " WHERE" +
                "  p.organization = :organization" +
                "  and p.privilegeType = :privilegeType" +
                " ORDER BY p.categoryName"
    )
})
@Form(
    serviceClass=SecurityServiceRemote.class
)
public class PrivilegeCategory extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "PrivilegeCategory";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_TYPE = CLASS_NAME + ".findByType";

    @Id
    @Basic(optional = false)
    @Column(name = "privilege_category_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    private UUID privilegeCategoryId;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    private Organization organization;

    @JoinColumn(name = "privilege_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Type",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.security.PrivilegeType"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Type:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource privilegeType;

    @Basic(optional = false)
    @Column(name = "category_name", nullable = false, length = 100)
    @Property(title="Name",
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String categoryName;

    @JoinColumn(name = "privilege_category_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public PrivilegeCategory() {
    }

    public PrivilegeCategory(UUID privilegeCategoryId) {
        this.privilegeCategoryId = privilegeCategoryId;
    }

    public PrivilegeCategory(UUID privilegeCategoryId, String categoryName) {
        this.privilegeCategoryId = privilegeCategoryId;
        this.categoryName = categoryName;
    }

    public UUID getPrivilegeCategoryId() {
        return privilegeCategoryId;
    }

    public void setPrivilegeCategoryId(UUID privilegeCategoryId) {
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
    public UUID getId() {
        return getPrivilegeCategoryId();
    }

    @Override
    public void setId(UUID id) {
        setPrivilegeCategoryId(id);
    }

    @Override
    public UUID getParentId() {
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
