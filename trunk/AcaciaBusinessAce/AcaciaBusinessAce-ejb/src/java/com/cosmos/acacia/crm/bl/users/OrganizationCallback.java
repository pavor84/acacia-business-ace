package com.cosmos.acacia.crm.bl.users;

import java.io.Serializable;
import java.util.List;

import javax.security.auth.callback.Callback;

import com.cosmos.acacia.crm.data.contacts.Organization;

public class OrganizationCallback
    implements Callback, Serializable {

    /** A list of organizations passed to the client to choose from */
    private List<Organization> organizations;

    /** a field where the clients sets the result */
    private Organization organization;

    public OrganizationCallback() {

    }

    public OrganizationCallback(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
