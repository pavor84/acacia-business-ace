/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

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

    public List<FunctionalHierarchy> getParentalHierarchy() {
        return getParentalHierarchy(this);
    }

    public static List<FunctionalHierarchy> getParentalHierarchy(FunctionalHierarchy currentFunctionalHierarchy) {
        List<FunctionalHierarchy> parentHierarchy = new ArrayList<FunctionalHierarchy>();
        while(currentFunctionalHierarchy != null) {
            parentHierarchy.add(currentFunctionalHierarchy);
            currentFunctionalHierarchy = currentFunctionalHierarchy.getParent();
        }

        return parentHierarchy;
    }
}
