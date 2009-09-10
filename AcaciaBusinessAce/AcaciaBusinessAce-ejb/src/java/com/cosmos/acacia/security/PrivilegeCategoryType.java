/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.security;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DatabaseResource;

/**
 *
 * @author Miro
 */
public enum PrivilegeCategoryType implements DatabaseResource {

    System(),
    UserDefined(),
    ;

    private PrivilegeCategoryType() {
    }

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
