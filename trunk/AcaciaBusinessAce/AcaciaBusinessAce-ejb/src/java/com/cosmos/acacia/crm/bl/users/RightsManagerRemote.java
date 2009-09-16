package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.crm.enums.PermissionCategory;
import java.util.Set;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.security.AccessRight;

@Remote
public interface RightsManagerRemote {

    boolean isAllowed(Class dataObjectClass, AccessRight... accessRights);

    boolean isAllowed(Object dataObject, AccessRight... accessRights);

    boolean isAllowed(SpecialPermission permission, AccessRight... accessRights);

    boolean isAllowed(PermissionCategory permissionCategory, AccessRight... accessRights);

    boolean isAllowed(Set<SpecialPermission> permissions, AccessRight... accessRights);
}
