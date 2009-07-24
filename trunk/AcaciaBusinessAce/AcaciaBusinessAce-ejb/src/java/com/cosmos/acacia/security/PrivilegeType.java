/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.security;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import java.util.Set;

/**
 *
 * @author Miro
 */
public enum PrivilegeType implements DatabaseResource {

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
    private DbResource dbResource;

    public Set<AccessLevel> getInapplicableAccessLevels() {
        return inapplicableAccessLevels;
    }

    public Set<AccessRight> getInapplicableAccessRights() {
        return inapplicableAccessRights;
    }

    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource dbResource) {
        this.dbResource = dbResource;
    }

    @Override
    public String toShortText() {
        return name();
    }

    @Override
    public String toText() {
        return name();
    }
}
