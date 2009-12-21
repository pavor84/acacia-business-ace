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
import com.cosmos.acacia.crm.data.ChildEntityBean;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.security.AccessLevel;
import com.cosmos.acacia.security.AccessRight;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBLabel;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
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
@Table(name = "privilege_roles", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"privilege_id", "access_right_id"})
})
@NamedQueries({
    @NamedQuery(
        name = PrivilegeRole.NQ_FIND_ALL,
        query = "SELECT t FROM PrivilegeRole t" +
                " WHERE" +
                "  t.privilege = :privilege" +
                " ORDER BY t.accessRight"
    ),
    @NamedQuery(
        name = PrivilegeRole.NQ_FIND_BY_PRIVILEGE_AND_ACCESS_RIGHT,
        query = "SELECT t FROM PrivilegeRole t" +
                " WHERE" +
                "  t.privilege = :privilege" +
                "  and t.accessRight = :accessRight"
    ),
    @NamedQuery(
        name = PrivilegeRole.NQ_COUNT_ROLES,
        query = "SELECT count(t) FROM PrivilegeRole t" +
                " WHERE" +
                "  t.privilege = :privilege"
    ),
    @NamedQuery(
        name = PrivilegeRole.NQ_FIND_BY_ENTITY_TYPE_AND_ACCESS_RIGHTS,
        query = "SELECT t FROM PrivilegeRole t, EntityTypePrivilege t1" +
                " WHERE" +
                "  t1.securityRole.organization = :organization" +
                "  and t1.entityDataObjectType = :entityDataObjectType" +
                "  and t.privilege = t1" +
                "  and t.accessRight in (:accessRights)" +
                " ORDER BY t.accessLevel"
    ),
    @NamedQuery(
        name = PrivilegeRole.NQ_FIND_BY_ENTITY_AND_ACCESS_RIGHTS,
        query = "SELECT t FROM PrivilegeRole t, EntityPrivilege t1" +
                " WHERE" +
                "  t1.securityRole.organization = :organization" +
                "  and t1.entityDataObject = :entityDataObject" +
                "  and t.privilege = t1" +
                "  and t.accessRight in (:accessRights)" +
                " ORDER BY t.accessLevel"
    ),
    @NamedQuery(
        name = PrivilegeRole.NQ_FIND_BY_PERMISSION_AND_ACCESS_RIGHTS,
        query = "SELECT t FROM PrivilegeRole t, SpecialPermissionPrivilege t1" +
                " WHERE" +
                "  t1.securityRole.organization = :organization" +
                "  and t1.specialPermission in (:specialPermissions)" +
                "  and t.privilege = t1" +
                "  and t.accessRight in (:accessRights)" +
                " ORDER BY t.accessLevel"
    ),
    @NamedQuery(
        name = PrivilegeRole.NQ_FIND_BY_PERMISSION_CATEGORY_AND_ACCESS_RIGHTS,
        query = "SELECT t FROM PrivilegeRole t, PermissionCategoryPrivilege t1" +
                " WHERE" +
                "  t1.securityRole.organization = :organization" +
                "  and t1.permissionCategory = :permissionCategory" +
                "  and t.privilege = t1" +
                "  and t.accessRight in (:accessRights)" +
                " ORDER BY t.accessLevel"
    )
})
@Form(
    serviceClass=SecurityServiceRemote.class
)
public class PrivilegeRole extends DataObjectBean implements Serializable, ChildEntityBean<Privilege> {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "PrivilegeRole";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_COUNT_ROLES = CLASS_NAME + ".countRoles";
    public static final String NQ_FIND_BY_ENTITY_TYPE_AND_ACCESS_RIGHTS =
            CLASS_NAME + ".findByEntityTypeAndAccessRights";
    public static final String NQ_FIND_BY_ENTITY_AND_ACCESS_RIGHTS =
            CLASS_NAME + ".findByEntityAndAccessRights";
    public static final String NQ_FIND_BY_PERMISSION_AND_ACCESS_RIGHTS =
            CLASS_NAME + ".findByPermissionAndAccessRights";
    public static final String NQ_FIND_BY_PERMISSION_CATEGORY_AND_ACCESS_RIGHTS =
            CLASS_NAME + ".findByPermissionCategoryAndAccessRights";
    public static final String NQ_FIND_BY_PRIVILEGE_AND_ACCESS_RIGHT =
            CLASS_NAME + ".findByPrivilegeAndAccessRight";
    //
    @Id
    @Basic(optional = false)
    @Column(name = "privilege_role_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    private UUID privilegeRoleId;

    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id", nullable = false)
    @ManyToOne(optional = false)
    private Privilege privilege;

    @JoinColumn(name = "access_right_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Access Right",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.security.AccessRight"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Access Right:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource accessRight;

    @JoinColumn(name = "access_level_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Access Level",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.security.AccessLevel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Access Level:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource accessLevel;

    @JoinColumn(name = "privilege_role_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public PrivilegeRole() {
    }

    public PrivilegeRole(UUID privilegeRoleId) {
        this.privilegeRoleId = privilegeRoleId;
    }

    public PrivilegeRole(Privilege privilege, DbResource accessRight, DbResource accessLevel) {
        this.privilege = privilege;
        this.accessRight = accessRight;
        this.accessLevel = accessLevel;
    }

    public UUID getPrivilegeRoleId() {
        return privilegeRoleId;
    }

    public void setPrivilegeRoleId(UUID privilegeRoleId) {
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
    public UUID getId() {
        return getPrivilegeRoleId();
    }

    @Override
    public void setId(UUID id) {
        setPrivilegeRoleId(id);
    }

    @Override
    public UUID getParentId() {
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

    @Override
    public Privilege getParentEntity() {
        return getPrivilege();
    }

    @Override
    public void setParentEntity(Privilege parentEntity) {
        setPrivilege(parentEntity);
    }
}