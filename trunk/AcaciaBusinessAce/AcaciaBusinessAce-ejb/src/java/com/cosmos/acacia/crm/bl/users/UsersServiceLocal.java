/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.BusinessUnitAddress;
import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface UsersServiceLocal extends UsersServiceRemote {

    User newUser();

    List<User> getUsers(Organization organization);

    List<UserOrganization> getUserOrganizations(Organization organization);

    List<UserOrganization> getUserOrganizations(User user);

    List<UserOrganization> getPossibleManagers(UserOrganization userOrganization);

    UserOrganization getUserOrganization(User user, Organization organization);

    List<Team> getTeams(DataObjectBean parameter);

    List<UserSecurityRole> getUserSecurityRoles(UserOrganization userOrganization);

    BusinessUnit getRootBusinessUnit(Organization organization);

    BusinessUnit getBusinessUnit(Organization organization, String businessUnitName);

    List<BusinessUnit> getBusinessUnits(Organization organization);

    List<BusinessUnit> getChildrenBusinessUnits(BusinessUnit parentBusinessUnit);

    Set<BusinessUnit> getParentChildBusinessUnits(BusinessUnit currentBusinessUnit);

    List<BusinessUnitAddress> getBusinessUnitAddresses(BusinessUnit businessUnit, Object... extraParameters);

    List<JobTitle> getJobTitles(BusinessUnit businessUnit, Object... extraParameters);

    List<Organization> getActiveOrganizations(User user);

    List<Organization> getOrganizations(User user);
}
