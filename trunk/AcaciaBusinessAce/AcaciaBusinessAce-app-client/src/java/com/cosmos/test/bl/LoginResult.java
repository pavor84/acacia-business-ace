package com.cosmos.test.bl;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.users.User;

public class LoginResult {

    private User user;
    private Organization org;
    private Address branch;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Organization getOrganization() {
        return org;
    }
    public void setOrganization(Organization org) {
        this.org = org;
    }
    public Address getBranch() {
        return branch;
    }
    public void setBranch(Address branch) {
        this.branch = branch;
    }


}
