/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Miro
 */
public enum PermissionCategory implements DatabaseResource {

    CoreRecords,
    Contacts,
    Marketing,
    Sales,
    Supplies, //Purchases
    Service,
    BusinessManagement,
    ServiceManagement,
    Customization,
    CustomEntities,
    Administration,
    BranchAdministration,
    Miscellaneous,
    ;

    private PermissionCategory() {
    }
    //
    private DbResource dbResource;
    private Set<SpecialPermission> categorizedPermissions;

    public Set<SpecialPermission> getPermissions() {
        if (categorizedPermissions == null) {
            categorizedPermissions = new TreeSet<SpecialPermission>();
            for (SpecialPermission permission : SpecialPermission.values()) {
                if (permission.getCategory().equals(this)) {
                    categorizedPermissions.add(permission);
                }
            }

            categorizedPermissions = EnumSet.copyOf(categorizedPermissions);
        }

        return categorizedPermissions;
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
