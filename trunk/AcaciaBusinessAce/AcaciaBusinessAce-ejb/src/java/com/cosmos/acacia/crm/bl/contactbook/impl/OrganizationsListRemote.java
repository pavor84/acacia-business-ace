package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling organizations
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface OrganizationsListRemote {

    /**
     * Lists all organizations for a parent.
     * Parent should be null to display all organizations
     *
     * @param parent
     * @return list of organizations
     */
    List<Organization> getOrganizations(DataObject parent);

    /**
     * Lists all currencies
     *
     * @return list of currencies
     */
    List<DbResource> getCurrencies();

    /**
     * Lists all addresses for a parent data object (Organization or Person)
     *
     * @param parent
     * @return list of addresses
     */
    List<Address> getAddresses(DataObject parent);

    /**
     * Lists all bank details for a parent data object (an Address)
     *
     * @param parent
     * @return list of bank details
     */
    List<BankDetail> getBankDetails(DataObject parent);

    /**
     * Lists all organization types
     *
     * @return list of organization types
     */
    List<DbResource> getOrganizationTypes();

    /**
     * Gets the EntityProperties of Organization
     *
     * @return the entity properties
     */
    EntityProperties getOrganizationEntityProperties();

    /**
     * Gets the EntityProperties of Address
     *
     * @return the entity properties
     */
    EntityProperties getAddressEntityProperties();

    /**
     * Gets the EntityProperties of BankDetail
     *
     * @return the entity properties
     */
    EntityProperties getBankDetailEntityProperties();

    /**
     * Creates a new Organization
     *
     * @return the newly created organization
     */
    Organization newOrganization();

    /**
     * Saves an Organization
     *
     * @param Organization
     * @return the saved organization
     */
    Organization saveOrganization(Organization Organization);

    /**
     * Deletes an Organization
     *
     * @param organization
     * @return the version of the deleted organization
     */
    int deleteOrganization(Organization organization);
}
