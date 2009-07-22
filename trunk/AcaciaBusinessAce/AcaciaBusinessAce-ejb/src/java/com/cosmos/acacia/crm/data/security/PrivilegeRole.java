/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.bl.security.SecurityServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
import java.awt.BorderLayout;
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
@Table(name = "privilege_roles", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"privilege_id", "access_right_id"})
})
@NamedQueries({
    @NamedQuery(
        name = "PrivilegeRole.findAll",
        query = "SELECT p FROM PrivilegeRole p"
    )
})
@Form(
    mainContainer=@FormContainer(
        name="mainTabbedPane",
        container=@Component(
            componentClass=JBTabbedPane.class
        )
    ),
    formContainers={
        @FormContainer(
            name="primaryInfo",
            title="Primary Info",
            container=@Component(
                componentClass=JBPanel.class
            )
        ),
        @FormContainer(
            name="notes",
            title="Notes",
            container=@Component(
                componentClass=JBPanel.class
            ),
            layout=@Layout(layoutClass=BorderLayout.class)
        )
    },
    serviceClass=SecurityServiceRemote.class
)
public class PrivilegeRole extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "privilege_role_id", nullable = false, precision = 19, scale = 0)
    private BigInteger privilegeRoleId;

    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id", nullable = false)
    @ManyToOne(optional = false)
    private Privilege privilege;

    @JoinColumn(name = "access_right_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Access Right"
    )
    private DbResource accessRight;

    @JoinColumn(name = "access_level_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Access Level"
    )
    private DbResource accessLevel;

    @JoinColumn(name = "privilege_role_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public PrivilegeRole() {
    }

    public PrivilegeRole(BigInteger privilegeRoleId) {
        this.privilegeRoleId = privilegeRoleId;
    }

    public BigInteger getPrivilegeRoleId() {
        return privilegeRoleId;
    }

    public void setPrivilegeRoleId(BigInteger privilegeRoleId) {
        this.privilegeRoleId = privilegeRoleId;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public DbResource getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(DbResource accessLevel) {
        this.accessLevel = accessLevel;
    }

    public DbResource getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(DbResource accessRight) {
        this.accessRight = accessRight;
    }

    @Override
    public BigInteger getId() {
        return getPrivilegeRoleId();
    }

    @Override
    public void setId(BigInteger id) {
        setPrivilegeRoleId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(privilege != null) {
            return privilege.getPrivilegeId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        if(privilege != null) {
            sb.append(privilege.getPrivilegeName());
        }
        sb.append(":");
        if(accessRight != null) {
            sb.append(accessRight.getEnumName());
        }
        sb.append(":");
        if(accessLevel != null) {
            sb.append(accessLevel.getEnumName());
        }

        return sb.toString();
    }
}