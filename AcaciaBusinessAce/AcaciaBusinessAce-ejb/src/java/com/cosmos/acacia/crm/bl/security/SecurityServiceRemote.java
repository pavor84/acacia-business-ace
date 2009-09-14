/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.security;

import com.cosmos.acacia.crm.enums.PermissionCategory;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.security.AccessRight;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface SecurityServiceRemote extends EntityService {

    boolean isAllowed(AccessRight accessRight, Class dataObjectClass);

    boolean isAllowed(AccessRight accessRight, Object dataObject);

    boolean isAllowed(AccessRight accessRight, SpecialPermission permission);

    boolean isAllowed(AccessRight accessRight, PermissionCategory permissionCategory);
}
