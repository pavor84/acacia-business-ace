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
import com.cosmos.acacia.crm.data.contacts.Organization;
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
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "security_roles", catalog = "acacia", schema = "public"
/*
CREATE UNIQUE INDEX uix_security_roles_organization_role_name
  ON security_roles
  USING btree
  (organization_id, lower(security_role_name::text));
*/
)
@NamedQueries({
    @NamedQuery(
        name = SecurityRole.NQ_FIND_ALL,
        query = "SELECT t FROM SecurityRole t" +
                " WHERE" +
                "  t.organization = :organization" +
                "  and not (t.organization.businessPartnerId = t.organization.parentBusinessPartnerId" +
                "           and lower(t.securityRoleName) = lower(:supervisorRoleName))" +
                " ORDER BY t.businessUnit.businessUnitName, t.securityRoleName"
    ),
    @NamedQuery(
        name = SecurityRole.NQ_FIND_BY_SECURITY_ROLE_NAME,
        query = "SELECT t FROM SecurityRole t" +
                " WHERE" +
                "  t.organization = :organization" +
                "  and lower(t.securityRoleName) = lower(:securityRoleName)"
    ),
    @NamedQuery(
        name = SecurityRole.NQ_FIND_BY_BUSINESS_UNIT,
        query = "SELECT t FROM SecurityRole t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit" +
                "  and not (t.organization.businessPartnerId = t.organization.parentBusinessPartnerId" +
                "           and lower(t.securityRoleName) = lower(:supervisorRoleName))" +
                " ORDER BY t.securityRoleName"
    ),
    @NamedQuery(
        name = SecurityRole.NQ_FIND_BY_USER_ORGANIZATION,
        query = "SELECT t FROM SecurityRole t" +
                " WHERE" +
                "  t.organization = :organization" +
                "  and not (t.organization.businessPartnerId = t.organization.parentBusinessPartnerId" +
                "           and lower(t.securityRoleName) = lower(:supervisorRoleName))" +
                "  and t.securityRoleId not in (" +
                "   SELECT t1.securityRole.securityRoleId FROM UserSecurityRole t1" +
                "    WHERE" +
                "     t1.userOrganization = :userOrganization" +
                "  )" +
                " ORDER BY t.securityRoleName"
    )
})
@Form(
    formContainers={
        @FormContainer(
            name="privilegeList",
            title="Privileges",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
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
    public static final String NQ_FIND_BY_USER_ORGANIZATION = CLASS_NAME + ".findByUserOrganization";
    public static final String NQ_FIND_BY_SECURITY_ROLE_NAME = CLASS_NAME + ".findBySecurityRoleName";
    //
    public static final String SUPERVISOR_ROLE_NAME = "supervisorRole";

    @Id
    @Basic(optional = false)
    @Column(name = "security_role_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    private UUID securityRoleId;

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

    public SecurityRole(UUID securityRoleId) {
        this.securityRoleId = securityRoleId;
    }

    public SecurityRole(UUID securityRoleId, String securityRoleName) {
        this.securityRoleId = securityRoleId;
        this.securityRoleName = securityRoleName;
    }

    public SecurityRole(Organization organization, BusinessUnit businessUnit, String securityRoleName) {
        this.organization = organization;
        this.businessUnit = businessUnit;
        this.securityRoleName = securityRoleName;
    }

    public UUID getSecurityRoleId() {
        return securityRoleId;
    }

    public void setSecurityRoleId(UUID securityRoleId) {
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
    public UUID getId() {
        return getSecurityRoleId();
    }

    @Override
    public void setId(UUID id) {
        setSecurityRoleId(id);
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
        return getSecurityRoleName();
    }

    @Override
    public String toShortText() {
        return getInfo();
    }

    @Override
    public String toText() {
        return getInfo();
    }
}
