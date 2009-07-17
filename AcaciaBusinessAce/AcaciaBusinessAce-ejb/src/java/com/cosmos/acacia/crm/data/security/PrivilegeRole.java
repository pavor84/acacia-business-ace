/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "privilege_roles", catalog = "acacia", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"privilege_id", "access_right_id"})})
@NamedQueries({@NamedQuery(name = "PrivilegeRole.findAll", query = "SELECT p FROM PrivilegeRole p"), @NamedQuery(name = "PrivilegeRole.findByPrivilegeRoleId", query = "SELECT p FROM PrivilegeRole p WHERE p.privilegeRoleId = :privilegeRoleId")})
public class PrivilegeRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "privilege_role_id", nullable = false, precision = 19, scale = 0)
    private BigDecimal privilegeRoleId;
    @JoinColumn(name = "privilege_role_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;
    @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id", nullable = false)
    @ManyToOne(optional = false)
    private Privilege privilegeId;
    @JoinColumn(name = "access_level_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource accessLevelId;
    @JoinColumn(name = "access_right_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource accessRightId;

    public PrivilegeRole() {
    }

    public PrivilegeRole(BigDecimal privilegeRoleId) {
        this.privilegeRoleId = privilegeRoleId;
    }

    public BigDecimal getPrivilegeRoleId() {
        return privilegeRoleId;
    }

    public void setPrivilegeRoleId(BigDecimal privilegeRoleId) {
        this.privilegeRoleId = privilegeRoleId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Privilege getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Privilege privilegeId) {
        this.privilegeId = privilegeId;
    }

    public DbResource getAccessLevelId() {
        return accessLevelId;
    }

    public void setAccessLevelId(DbResource accessLevelId) {
        this.accessLevelId = accessLevelId;
    }

    public DbResource getAccessRightId() {
        return accessRightId;
    }

    public void setAccessRightId(DbResource accessRightId) {
        this.accessRightId = accessRightId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (privilegeRoleId != null ? privilegeRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrivilegeRole)) {
            return false;
        }
        PrivilegeRole other = (PrivilegeRole) object;
        if ((this.privilegeRoleId == null && other.privilegeRoleId != null) || (this.privilegeRoleId != null && !this.privilegeRoleId.equals(other.privilegeRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.PrivilegeRole[privilegeRoleId=" + privilegeRoleId + "]";
    }

}
