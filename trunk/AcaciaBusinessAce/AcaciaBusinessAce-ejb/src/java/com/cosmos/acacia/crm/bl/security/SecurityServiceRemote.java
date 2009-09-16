/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.security;

import com.cosmos.acacia.crm.enums.PermissionCategory;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.security.AccessRight;
import java.util.Set;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface SecurityServiceRemote extends EntityService {

    boolean isAllowed(Class dataObjectClass, AccessRight... accessRights);

    boolean isAllowed(Object dataObject, AccessRight... accessRights);

    boolean isAllowed(SpecialPermission permission, AccessRight... accessRights);

    boolean isAllowed(PermissionCategory permissionCategory, AccessRight... accessRights);

    boolean isAllowed(Set<SpecialPermission> permissions, AccessRight... accessRights);
}
