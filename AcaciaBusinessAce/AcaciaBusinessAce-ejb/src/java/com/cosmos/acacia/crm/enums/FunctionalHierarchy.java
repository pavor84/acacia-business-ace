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
    Manager(null, Group.Management),

    DeputyManager(Manager, Group.DeputyManagement),

    /**
     * Project Manager, Sales Manager, Marketing Manager, etc.
     */
    FunctionalManager(DeputyManager, Group.Management),

    DeputyFunctionalManager(FunctionalManager, Group.DeputyManagement),

    SeniorOfficer(DeputyFunctionalManager, Group.Service),

    Officer(SeniorOfficer, Group.Service),

    JuniorOfficer(Officer, Group.Service),

    Trainee(JuniorOfficer, Group.Guest),

    Visitor(Trainee, Group.Guest)
    ;

    public enum Group {
        Management,
        DeputyManagement,
        Service,
        Guest
    };

    private FunctionalHierarchy(FunctionalHierarchy parent, Group group) {
        this.parent = parent;
        this.group = group;
    }
    //
    private FunctionalHierarchy parent;
    private DbResource dbResource;
    private Group group;

    public FunctionalHierarchy getParent() {
        return parent;
    }

    public Group getGroup() {
        return group;
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
