/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.users.User;
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
@Table(name = "user_security_roles", catalog = "acacia", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "security_role_id"})})
@NamedQueries({@NamedQuery(name = "UserSecurityRole.findAll", query = "SELECT u FROM UserSecurityRole u"), @NamedQuery(name = "UserSecurityRole.findByUserSecurityRoleId", query = "SELECT u FROM UserSecurityRole u WHERE u.userSecurityRoleId = :userSecurityRoleId")})
public class UserSecurityRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_security_role_id", nullable = false, precision = 19, scale = 0)
    private BigDecimal userSecurityRoleId;
    @JoinColumn(name = "user_security_role_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;
    @JoinColumn(name = "security_role_id", referencedColumnName = "security_role_id", nullable = false)
    @ManyToOne(optional = false)
    private SecurityRole securityRoleId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private User userId;

    public UserSecurityRole() {
    }

    public UserSecurityRole(BigDecimal userSecurityRoleId) {
        this.userSecurityRoleId = userSecurityRoleId;
    }

    public BigDecimal getUserSecurityRoleId() {
        return userSecurityRoleId;
    }

    public void setUserSecurityRoleId(BigDecimal userSecurityRoleId) {
        this.userSecurityRoleId = userSecurityRoleId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public SecurityRole getSecurityRoleId() {
        return securityRoleId;
    }

    public void setSecurityRoleId(SecurityRole securityRoleId) {
        this.securityRoleId = securityRoleId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userSecurityRoleId != null ? userSecurityRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserSecurityRole)) {
            return false;
        }
        UserSecurityRole other = (UserSecurityRole) object;
        if ((this.userSecurityRoleId == null && other.userSecurityRoleId != null) || (this.userSecurityRoleId != null && !this.userSecurityRoleId.equals(other.userSecurityRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.UserSecurityRole[userSecurityRoleId=" + userSecurityRoleId + "]";
    }

}
