/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.users.User;
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
@Table(name = "user_security_roles", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "security_role_id"})
})
@NamedQueries({
    @NamedQuery(
        name = "UserSecurityRole.findAll",
        query = "SELECT u FROM UserSecurityRole u"
    )
})
public class UserSecurityRole extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "user_security_role_id", nullable = false, precision = 19, scale = 0)
    private BigInteger userSecurityRoleId;

    @JoinColumn(name = "security_role_id", referencedColumnName = "security_role_id", nullable = false)
    @ManyToOne(optional = false)
    private SecurityRole securityRole;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "user_security_role_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public UserSecurityRole() {
    }

    public UserSecurityRole(BigInteger userSecurityRoleId) {
        this.userSecurityRoleId = userSecurityRoleId;
    }

    public BigInteger getUserSecurityRoleId() {
        return userSecurityRoleId;
    }

    public void setUserSecurityRoleId(BigInteger userSecurityRoleId) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public BigInteger getId() {
        return getUserSecurityRoleId();
    }

    @Override
    public void setId(BigInteger id) {
        setUserSecurityRoleId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(user != null) {
            return user.getId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        if(user != null) {
            sb.append(user.getUserName());
        }
        sb.append(':');
        if(securityRole != null) {
            sb.append(securityRole.getSecurityRoleName());
        }

        return sb.toString();
    }
}
