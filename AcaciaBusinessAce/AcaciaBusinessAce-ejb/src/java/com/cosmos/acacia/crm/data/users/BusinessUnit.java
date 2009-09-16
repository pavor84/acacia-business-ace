/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponent;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
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
@Table(name = "business_units", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_id", "business_unit_name"})}
)
@NamedQueries({
    @NamedQuery(
        name = BusinessUnit.NQ_FIND_ALL,
        query = "SELECT t FROM BusinessUnit t" +
                " WHERE" +
                "  t.organization = :organization" +
                " ORDER BY t.parentBusinessUnit, t.businessUnitName"
    ),
    @NamedQuery(
        name = BusinessUnit.NQ_FIND_BY_NULL_PARENT_BUSINESS_UNIT,
        query = "SELECT t FROM BusinessUnit t" +
                " WHERE" +
                "  t.organization = :organization" +
                "  and t.parentBusinessUnit is null" +
                " ORDER BY t.businessUnitName"
    ),
    @NamedQuery(
        name = BusinessUnit.NQ_FIND_BY_PARENT_BUSINESS_UNIT,
        query = "SELECT t FROM BusinessUnit t" +
                " WHERE" +
                "  t.parentBusinessUnit = :parentBusinessUnit" +
                " ORDER BY t.businessUnitName"
    )
})
@Form(
    formContainers={
        @FormContainer(
            name="addressList",
            title="Addresses",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=BusinessUnitAddress.class
        ),
        @FormContainer(
            name="jobTitleList",
            title="Job Titles",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=JobTitle.class
        )
    },
    serviceClass=UsersServiceRemote.class
)
public class BusinessUnit extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "BusinessUnit";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_NULL_PARENT_BUSINESS_UNIT = CLASS_NAME + ".findByNullParentBusinessUnit";
    public static final String NQ_FIND_BY_PARENT_BUSINESS_UNIT = CLASS_NAME + ".findByParentBusinessUnit";
    //
    public static final String ROOT_BUSINESS_UNIT = "Root";

    @Id
    @Basic(optional = false)
    @Column(name = "business_unit_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    private UUID businessUnitId;

    @Basic(optional = false)
    @Column(name = "is_root", nullable = false)
    @Property(title="Root",
        editable=false,
        readOnly=true,
        formComponent=@FormComponent(
            parentContainerName=PRIMARY_INFO,
            component=@Component(
                componentClass=JBCheckBox.class,
                text="Root",
                componentConstraints="skip 1"
            )
        )
    )
    private boolean root;

    @Basic(optional = false)
    @Column(name = "is_disabled", nullable = false)
    @Property(title="Disabled",
        formComponent=@FormComponent(
            parentContainerName=PRIMARY_INFO,
            component=@Component(
                componentClass=JBCheckBox.class,
                text="Disabled",
                componentConstraints="skip 1"
            )
        )
    )
    private boolean disabled;

    @JoinColumn(name = "business_unit_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Type",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.BusinessUnitType"
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
    private DbResource businessUnitType;

    @JoinColumn(name = "parent_business_unit_id", referencedColumnName = "business_unit_id")
    @ManyToOne
    @Property(title="Parent",
        propertyValidator=@PropertyValidator(required=true),
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.users.BusinessUnitListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Parent:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private BusinessUnit parentBusinessUnit;

    @Basic(optional = false)
    @Column(name = "business_unit_name", nullable = false, length = 100)
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
    private String businessUnitName;

    @Column(name = "division_name", length = 100)
    @Property(title="Division",
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Division:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String divisionName;

    @Column(name = "web_site", length = 255)
    @Property(title="Web Site",
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Web Site:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class,
                componentConstraints="span"
            )
        )
    )
    private String webSite;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    private Organization organization;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public BusinessUnit() {
    }

    public BusinessUnit(UUID businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public BusinessUnit(Organization organization) {
        this.organization = organization;
    }

    public UUID getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(UUID businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public BusinessUnit getParentBusinessUnit() {
        return parentBusinessUnit;
    }

    public void setParentBusinessUnit(BusinessUnit parentBusinessUnit) {
        this.parentBusinessUnit = parentBusinessUnit;
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

    public DbResource getBusinessUnitType() {
        return businessUnitType;
    }

    public void setBusinessUnitType(DbResource businessUnitType) {
        this.businessUnitType = businessUnitType;
    }

    @Override
    public UUID getId() {
        return getBusinessUnitId();
    }

    @Override
    public void setId(UUID id) {
        setBusinessUnitId(id);
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
        return getBusinessUnitName();
    }
}
