package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Embeddable
public class UserOrganizationPK implements Serializable {

    private static final long serialVersionUID = -4356741863123651033L;

    @Column(name = "user_id", nullable = false)
    private BigInteger userId;

    @Column(name = "organization_id", nullable = false)
    private BigInteger organizationId;

    public UserOrganizationPK() {
    }

    public UserOrganizationPK(BigInteger userId, BigInteger organizationId) {
        this.userId = userId;
        this.organizationId = organizationId;
    }


    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += userId.intValue();
        hash += organizationId.intValue();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserOrganizationPK)) {
            return false;
        }
        UserOrganizationPK other = (UserOrganizationPK) object;
        if (this.userId.equals(other.userId)) {
            return false;
        }
        if (this.organizationId.equals(other.organizationId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.UserOrganizationPK[userId=" + userId + ", organizationId=" + organizationId + "]";
    }

}