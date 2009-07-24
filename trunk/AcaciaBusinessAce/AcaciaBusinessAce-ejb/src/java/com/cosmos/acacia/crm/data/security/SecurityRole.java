/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.security.SecurityServiceRemote;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTextField;
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
@Table(name = "security_roles", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_id", "security_role_name"})
})
@NamedQueries({
    @NamedQuery(
        name = "SecurityRole.findAll",
        query = "SELECT t FROM SecurityRole t" +
                " WHERE" +
                "  t.organization = :organization" +
                " ORDER BY t.businessUnit.businessUnitName, t.securityRoleName"
    ),
    @NamedQuery(
        name = "SecurityRole.findByBusinessUnit",
        query = "SELECT t FROM SecurityRole t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit" +
                " ORDER BY t.securityRoleName"
    )
})
@Form(
    formContainers={
        @FormContainer(
            name="privilegeList",
            title="Privileges",
            depends={"<entityForm>"},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=Privilege.class
        )
    },
    serviceClass=SecurityServiceRemote.class
)
public class SecurityRole extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "SecurityRole";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_BUSINESS_UNIT = CLASS_NAME + ".findByBusinessUnit";

    @Id
    @Basic(optional = false)
    @Column(name = "security_role_id", nullable = false, precision = 19, scale = 0)
    private BigInteger securityRoleId;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Business Unit",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.users.BusinessUnitListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Business Unit:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private BusinessUnit businessUnit;

    @Basic(optional = false)
    @Column(name = "security_role_name", nullable = false, length = 100)
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
    private String securityRoleName;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    private Organization organization;

    @JoinColumn(name = "security_role_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public SecurityRole() {
    }

    public SecurityRole(BigInteger securityRoleId) {
        this.securityRoleId = securityRoleId;
    }

    public SecurityRole(BigInteger securityRoleId, String securityRoleName) {
        this.securityRoleId = securityRoleId;
        this.securityRoleName = securityRoleName;
    }

    public BigInteger getSecurityRoleId() {
        return securityRoleId;
    }

    public void setSecurityRoleId(BigInteger securityRoleId) {
        this.securityRoleId = securityRoleId;
    }

    public String getSecurityRoleName() {
        return securityRoleName;
    }

    public void setSecurityRoleName(String securityRoleName) {
        this.securityRoleName = securityRoleName;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
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
        if(organization != null) {
            setParentId(organization.getId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public BigInteger getId() {
        return getSecurityRoleId();
    }

    @Override
    public void setId(BigInteger id) {
        setSecurityRoleId(id);
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
        return getSecurityRoleName();
    }
}
