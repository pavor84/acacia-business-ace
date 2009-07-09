/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

/**
 *
 * @author Miro
 */
public enum FunctionalHierarchy {

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

    private FunctionalHierarchy parent;

    public FunctionalHierarchy getParent() {
        return parent;
    }
}
