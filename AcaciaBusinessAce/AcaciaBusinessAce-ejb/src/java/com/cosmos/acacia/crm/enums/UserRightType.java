package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;

/**
 * Lists the general rights
 *
 * @author Bozhidar Bozhanov
 */
public enum UserRightType implements DatabaseResource {

    READ,
    CREATE,
    MODIFY,
    DELETE,
    EXECUTE;

    private UserRightType() {
    }
    //
    private DbResource dbResource;

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
