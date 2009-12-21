/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.security;

import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.PrivilegeCategory;
import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.crm.enums.BusinessUnitTypeJobTitle;
import com.cosmos.acacia.security.AccessLevel;
import com.cosmos.acacia.security.AccessRight;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface SecurityServiceLocal extends SecurityServiceRemote {

    SecurityRole getSecurityRole(JobTitle jobTitle);

    SecurityRole getSecurityRole(BusinessUnit businessUnit, String securityRoleName);

    SecurityRole getSecurityRole(Organization organization, String securityRoleName);

    SecurityRole newSecurityRole(JobTitle jobTitle);

    SecurityRole newSecurityRole(BusinessUnit businessUnit, String securityRoleName);

    void newPrivileges(JobTitle jobTitle, BusinessUnitTypeJobTitle buJobTitle);

    void setSecurityRoleAccess(
            Organization organization,
            SecurityRole securityRole,
            Package pkg,
            Map<AccessRight, AccessLevel> roleAccessMap);

    PrivilegeCategory getPrivilegeCategory(Organization organization, String categoryName);

    void initPrivilegeCategories(Organization organization);

    Privilege getPrivilege(SecurityRole securityRole, String privilegeName);

    PrivilegeRole getPrivilegeRole(Privilege privilege, AccessRight accessRight);
}
