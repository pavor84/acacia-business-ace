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

    /**
     * CEO, Business Manager
     * System Administrator
     * System Customizer
     */
    Administration,

    /**
     *
     */
    Finance,

    /**
     * CSR Manager
     * Customer Service Representative
     * Schedule Manager
     * Scheduler
     */
    CustomerService,
    CoreRecords,
    Contacts,

    /**
     * Vice President of Marketing
     * Marketing Manager
     * Marketing Professional
     */
    Marketing,

    /**
     * Vice President of Sales
     * Sales Manager
     * Salesperson
     */
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
