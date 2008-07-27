package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;

/**
 * Enumeration listing all special permissions
 *
 * @author Bozhidar Bozhanov
 *
 */
public enum SpecialPermission implements DatabaseResource {
    SpecialPermission1,
    SpecialPermission2,
    ;

    private DbResource dbResource;

    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;

    }

    @Override
    public String toShortText() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toText() {
        // TODO Auto-generated method stub
        return null;
    }
}
