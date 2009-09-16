/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
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
@Table(name = "user_security_roles", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_organization_id", "security_role_id"})
})
@NamedQueries({
    @NamedQuery(
        name = UserSecurityRole.NQ_FIND_ALL,
        query = "SELECT t FROM UserSecurityRole t" +
                " WHERE" +
                "  t.userOrganization = :userOrganization"
    ),
    @NamedQuery(
        name = UserSecurityRole.NQ_FIND_SECURITY_ROLES_BY_USER_ORGANIZATION,
        query = "SELECT t.securityRole FROM UserSecurityRole t" +
                " WHERE" +
                "  t.userOrganization = :userOrganization"
    )
})
@Form(
    serviceClass=UsersServiceRemote.class
)
public class UserSecurityRole extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "UserSecurityRole";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_SECURITY_ROLES_BY_USER_ORGANIZATION =
            CLASS_NAME + ".findSecurityRolesByUserOrganization";
    //
    @Id
    @Basic(optional = false)
    @Column(name = "user_security_role_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    private UUID userSecurityRoleId;

    @JoinColumn(name = "user_organization_id", referencedColumnName = "user_organization_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="User")
    private UserOrganization userOrganization;

    @JoinColumn(name = "security_role_id", referencedColumnName = "security_role_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Security Role",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.security.SecurityRoleListPanel",
            constructorParameters={
                @PropertyName(getter="user"),
                @PropertyName(getter="${entity}", setter="userSecurityRole")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Security Role:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private SecurityRole securityRole;

    @JoinColumn(name = "user_security_role_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public UserSecurityRole() {
    }

    public UserSecurityRole(UUID userSecurityRoleId) {
        this.userSecurityRoleId = userSecurityRoleId;
    }

    public UserSecurityRole(UserOrganization userOrganization, SecurityRole securityRole) {
        this.userOrganization = userOrganization;
        this.securityRole = securityRole;
    }

    public UUID getUserSecurityRoleId() {
        return userSecurityRoleId;
    }

    public void setUserSecurityRoleId(UUID userSecurityRoleId) {
        this.userSecurityRoleId = userSecurityRoleId;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public SecurityRole getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(SecurityRole securityRole) {
        this.securityRole = securityRole;
    }

    public UserOrganization getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(UserOrganization userOrganization) {
        this.userOrganization = userOrganization;
    }

    @Override
    public UUID getId() {
        return getUserSecurityRoleId();
    }

    @Override
    public void setId(UUID id) {
        setUserSecurityRoleId(id);
    }

    @Override
    public UUID getParentId() {
        if(userOrganization != null) {
            return userOrganization.getId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        if(userOrganization != null) {
            sb.append(userOrganization);
        }
        sb.append(':');
        if(securityRole != null) {
            sb.append(securityRole.getSecurityRoleName());
        }

        return sb.toString();
    }
}
