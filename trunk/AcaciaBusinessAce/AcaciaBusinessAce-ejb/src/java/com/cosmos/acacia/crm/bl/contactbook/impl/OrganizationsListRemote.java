/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.Currency;
import java.util.List;

import javax.ejb.Remote;

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

    List<Organization> getOrganizations(DataObject parent);

    List<DbResource> getCurrencies();

    List<Address> getAddresses(DataObject parent);

    List<BankDetail> getBankDetails(DataObject parent);

    List<DbResource> getOrganizationTypes();

    EntityProperties getOrganizationEntityProperties();

    EntityProperties getAddressEntityProperties();

    EntityProperties getBankDetailEntityProperties();

    Organization newOrganization();

    Organization saveOrganization(Organization Organization);

    int deleteOrganization(Organization organization);
}
