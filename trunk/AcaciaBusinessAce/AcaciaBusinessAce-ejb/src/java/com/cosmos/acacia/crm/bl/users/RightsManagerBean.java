package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.enums.PermissionCategory;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import com.cosmos.acacia.crm.bl.security.SecurityServiceLocal;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.security.AccessRight;

/**
 * The class supports both server-side and client-side invocations
 * This explains the some private wrapper methods, which distinguish
 * between the two types of invocations.
 *
 * @author Bozhidar Bozhanov
 *
 */
@Stateful
public class RightsManagerBean implements RightsManagerRemote, RightsManagerLocal {

    @EJB
    private SecurityServiceLocal securityService;

    @Override
    public boolean isAllowed(Class dataObjectClass, AccessRight... accessRights) {
        return securityService.isAllowed(dataObjectClass, accessRights);
    }

    @Override
    public boolean isAllowed(Object dataObject, AccessRight... accessRights) {
        return securityService.isAllowed(dataObject, accessRights);
    }

    @Override
    public boolean isAllowed(SpecialPermission permission, AccessRight... accessRights) {
        return securityService.isAllowed(permission, accessRights);
    }

    @Override
    public boolean isAllowed(PermissionCategory permissionCategory, AccessRight... accessRights) {
        return securityService.isAllowed(permissionCategory, accessRights);
    }

    @Override
    public boolean isAllowed(Set<SpecialPermission> permissions, AccessRight... accessRights) {
        return securityService.isAllowed(permissions, accessRights);
    }
}
