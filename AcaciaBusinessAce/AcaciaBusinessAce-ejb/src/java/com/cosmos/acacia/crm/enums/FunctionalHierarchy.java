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
public enum FunctionalHierarchy implements DatabaseResource {

    /**
     * Department Manager, Sales and Marketing Manager, etc.
     */
    Manager(null),

    DeputyManager(Manager),

    /**
     * Project Manager, Sales Manager, Marketing Manager, etc.
     */
    FunctionalManager(DeputyManager),

    DeputyFunctionalManager(FunctionalManager),

    SeniorOfficer(DeputyFunctionalManager),

    Officer(SeniorOfficer),

    JuniorOfficer(Officer),

    Trainee(JuniorOfficer),

    Visitor(Trainee)
    ;

    private FunctionalHierarchy(FunctionalHierarchy parent) {
        this.parent = parent;
    }
    //
    private FunctionalHierarchy parent;
    private DbResource dbResource;

    public FunctionalHierarchy getParent() {
        return parent;
    }

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
