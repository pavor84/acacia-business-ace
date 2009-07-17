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
public enum BusinessUnitType implements DatabaseResource {

    /**
     * CEO, Business Manager
     * System Administrator
     * System Customizer
     */
    Administrative,

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
    Customer,

    /**
     * 
     */
    Supplies, //Purchases

    /**
     * Vice President of Sales
     * Sales Manager
     * Salesperson
     */
    Sales,

    /**
     * Vice President of Marketing
     * Marketing Manager
     * Marketing Professional
     */
    Marketing,

    Warehouse;

    private BusinessUnitType() {
    }
    //
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
        return name();
    }

    @Override
    public String toText() {
        return name();
    }
}
