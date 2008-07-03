package com.cosmos.acacia.crm.data;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Bozhidar Bozhanov
 *
 */

@Entity
@Table(name = "users_organizations")
@NamedQueries( {
    @NamedQuery (
            name = "UserOrganization.findByUser",
            query = "select uo from UserOrganization uo where uo.user=:user"
    ),
    @NamedQuery (
            name = "UserOrganization.findByOrganization",
            query = "select uo from UserOrganization uo where uo.organization=:organization"
    )
})
public class UserOrganization implements Serializable {

    private static final long serialVersionUID = 5301950611457351180L;

    @EmbeddedId
    protected UserOrganizationPK userOrganizationPK;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id", insertable=false, updatable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="organization_id", referencedColumnName="organization_id", insertable=false, updatable=false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name="branch_id", referencedColumnName="address_id", insertable=false, updatable=false)
    private Address branch;
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public UserOrganizationPK getUserOrganizationPK() {
        return userOrganizationPK;
    }

    public void setUserOrganizationPK(UserOrganizationPK userOrganizationPK) {
        this.userOrganizationPK = userOrganizationPK;
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        this.branch = branch;
    }
}
