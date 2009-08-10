/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;

/**
 *
 * @author Miro
 */
public enum BusinessActivity implements DatabaseResource {

    CoreRecords,
    Contacts,
    Marketing,
    Sales,
    Supply,
    Warehouse,
    Service,
    BusinessManagement,
    ServiceManagement,
    Customization,
    CustomEntities;

    private BusinessActivity() {
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
