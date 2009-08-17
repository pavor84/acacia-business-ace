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
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface UsersServiceLocal extends UsersServiceRemote {

    List<User> getUsers(Organization organization);

    List<User> getPossibleManagers(User user);

    List<Team> getTeams(DataObjectBean parameter);

    List<UserSecurityRole> getUserSecurityRoles(User user);

    List<BusinessUnit> getBusinessUnits();

    List<BusinessUnit> getBusinessUnits(BusinessUnit parentBusinessUnit);

    List<BusinessUnitAddress> getBusinessUnitAddresses(BusinessUnit businessUnit, Object... extraParameters);

    List<JobTitle> getJobTitles(BusinessUnit businessUnit, Object... extraParameters);
}
