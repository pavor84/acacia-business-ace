/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.security;

import java.util.Set;

/**
 *
 * @author Miro
 */
public enum PrivilegeType {

    UserOwnedEntity(null, null),
    OrganizationOwnedEntity(null, null),
    SystemOwnedEntity(null, null),
    GlobalTask(null, null),
    ;

    private PrivilegeType(Set<AccessRight> inapplicableAccessRights, Set<AccessLevel> inapplicableAccessLevels) {
        this.inapplicableAccessRights = inapplicableAccessRights;
        this.inapplicableAccessLevels = inapplicableAccessLevels;
    }

    private Set<AccessRight> inapplicableAccessRights;
    private Set<AccessLevel> inapplicableAccessLevels;

    public Set<AccessLevel> getInapplicableAccessLevels() {
        return inapplicableAccessLevels;
    }

    public Set<AccessRight> getInapplicableAccessRights() {
        return inapplicableAccessRights;
    }
}
