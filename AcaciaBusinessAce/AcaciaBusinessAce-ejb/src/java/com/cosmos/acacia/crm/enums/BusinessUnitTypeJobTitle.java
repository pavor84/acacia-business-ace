/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.bl.impl.EntityServiceRemote;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Miro
 */
public enum BusinessUnitTypeJobTitle {

    // Supervisor
    Supervisor(
            BusinessUnitType.Administrative,
            FunctionalHierarchy.Manager,
            "Supervisor",
            EntityServiceRemote.SECURITY_PACKAGE),
    Visitor(
            BusinessUnitType.Administrative,
            FunctionalHierarchy.Visitor,
            "Visitor",
            EntityServiceRemote.USERS_PACKAGE),
    // Warehouse
    HeadOfStock(
            BusinessUnitType.Warehouse,
            FunctionalHierarchy.Manager,
            "Head Of Stock",
            EntityServiceRemote.WAREHOUSE_PACKAGE),
    DeputyHeadOfStock(
            BusinessUnitType.Warehouse,
            FunctionalHierarchy.DeputyManager,
            "Deputy Head Of Stock",
            EntityServiceRemote.WAREHOUSE_PACKAGE),
    Warehouseman(
            BusinessUnitType.Warehouse,
            FunctionalHierarchy.Officer,
            "Warehouseman",
            EntityServiceRemote.WAREHOUSE_PACKAGE),
    // Finance
    FinanceManager(
            BusinessUnitType.Finance,
            FunctionalHierarchy.Manager,
            "Finance Manager",
            EntityServiceRemote.ACCOUNTING_PACKAGE),
    DeputyFinanceManager(
            BusinessUnitType.Finance,
            FunctionalHierarchy.DeputyManager,
            "Deputy Finance Manager",
            EntityServiceRemote.ACCOUNTING_PACKAGE),
    FinanceOfficer(
            BusinessUnitType.Finance,
            FunctionalHierarchy.Officer,
            "Finance Officer",
            EntityServiceRemote.ACCOUNTING_PACKAGE),
    ;

    /*
     * ToDo SecurityRole
     */
    private BusinessUnitTypeJobTitle(
            BusinessUnitType businessUnitType,
            FunctionalHierarchy functionalHierarchy,
            String jobTitle,
            Package entityPackage) {
        this.businessUnitType = businessUnitType;
        this.functionalHierarchy = functionalHierarchy;
        this.jobTitle = jobTitle;
        this.entityPackage = entityPackage;
    }

    private BusinessUnitType businessUnitType;
    private FunctionalHierarchy functionalHierarchy;
    private String jobTitle;
    private Package entityPackage;

    public BusinessUnitType getBusinessUnitType() {
        return businessUnitType;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public FunctionalHierarchy getFunctionalHierarchy() {
        return functionalHierarchy;
    }

    public Package getEntityPackage() {
        return entityPackage;
    }

    private static Map<BusinessUnitType, List<BusinessUnitTypeJobTitle>> buJobTitleMap;
    public static List<BusinessUnitTypeJobTitle> getBusinessUnitJobTitles(BusinessUnitType businessUnitType) {
        if(buJobTitleMap == null) {
            buJobTitleMap = new TreeMap<BusinessUnitType, List<BusinessUnitTypeJobTitle>>();
            for(BusinessUnitTypeJobTitle buJobTitle : values()) {
                List<BusinessUnitTypeJobTitle> buJobTitles;
                BusinessUnitType buType = buJobTitle.getBusinessUnitType();
                if((buJobTitles = buJobTitleMap.get(buType)) == null) {
                    buJobTitles = new ArrayList<BusinessUnitTypeJobTitle>();
                    buJobTitleMap.put(buType, buJobTitles);
                }
                buJobTitles.add(buJobTitle);
            }
        }

        return buJobTitleMap.get(businessUnitType);
    }
}
