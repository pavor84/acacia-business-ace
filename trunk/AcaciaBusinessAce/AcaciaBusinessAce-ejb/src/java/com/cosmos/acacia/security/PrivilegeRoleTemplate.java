/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

import com.cosmos.acacia.crm.data.DataObjectEntity;
import com.cosmos.acacia.crm.enums.FunctionalHierarchy;
import java.util.EnumMap;
import java.util.Map;
import static com.cosmos.acacia.security.AccessLevel.None;
import static com.cosmos.acacia.security.AccessLevel.User;
import static com.cosmos.acacia.security.AccessLevel.BusinessUnit;
import static com.cosmos.acacia.security.AccessLevel.ParentChildBusinessUnit;
import static com.cosmos.acacia.security.AccessLevel.Organization;


/**
 *
 * @author Miro
 */
public class PrivilegeRoleTemplate {

    //
    // By access: Own, Related, Other
    // By Functional Hierarchy:
    // - Manager, FunctionalManager
    // - DeputyManager, DeputyFunctionalManager
    // - SeniorOfficer, Officer, JuniorOfficer
    // - Trainee, Visitor
    private static final Map<AccessRight, AccessLevel> MANAGER_ROLE_OWN_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> MANAGER_ROLE_RELATED_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> MANAGER_ROLE_OTHER_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    //
    private static final Map<AccessRight, AccessLevel> DEPUTY_ROLE_OWN_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> DEPUTY_ROLE_RELATED_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> DEPUTY_ROLE_OTHER_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    //
    private static final Map<AccessRight, AccessLevel> SERVICE_ROLE_OWN_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> SERVICE_ROLE_RELATED_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> SERVICE_ROLE_OTHER_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    //
    private static final Map<AccessRight, AccessLevel> GUEST_ROLE_OWN_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> GUEST_ROLE_RELATED_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    private static final Map<AccessRight, AccessLevel> GUEST_ROLE_OTHER_ACCESS_MAP =
            new EnumMap<AccessRight, AccessLevel>(AccessRight.class);
    //
    static {
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Create, ParentChildBusinessUnit);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Read, Organization);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Modify, ParentChildBusinessUnit);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Delete, BusinessUnit);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Execute, ParentChildBusinessUnit);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Append, ParentChildBusinessUnit);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.AppendTo, ParentChildBusinessUnit);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Assign, BusinessUnit);
        MANAGER_ROLE_OWN_ACCESS_MAP.put(AccessRight.Share, ParentChildBusinessUnit);
        //
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Create, BusinessUnit);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Read, Organization);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Modify, BusinessUnit);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Delete, User);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Execute, ParentChildBusinessUnit);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Append, ParentChildBusinessUnit);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.AppendTo, ParentChildBusinessUnit);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Assign, BusinessUnit);
        DEPUTY_ROLE_OWN_ACCESS_MAP.put(AccessRight.Share, ParentChildBusinessUnit);
        //
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Create, User);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Read, Organization);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Modify, BusinessUnit);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Delete, User);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Execute, BusinessUnit);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Append, ParentChildBusinessUnit);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.AppendTo, ParentChildBusinessUnit);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Assign, User);
        SERVICE_ROLE_OWN_ACCESS_MAP.put(AccessRight.Share, ParentChildBusinessUnit);
        //
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Create, User);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Read, User);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Modify, User);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Delete, User);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Execute, User);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Append, None);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.AppendTo, None);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Assign, None);
        GUEST_ROLE_OWN_ACCESS_MAP.put(AccessRight.Share, User);
        //
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Create, BusinessUnit);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Read, Organization);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Modify, BusinessUnit);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Delete, User);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Execute, BusinessUnit);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Append, BusinessUnit);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.AppendTo, BusinessUnit);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Assign, User);
        MANAGER_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Share, BusinessUnit);
        //
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Create, BusinessUnit);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Read, Organization);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Modify, BusinessUnit);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Delete, User);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Execute, BusinessUnit);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Append, BusinessUnit);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.AppendTo, BusinessUnit);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Assign, User);
        DEPUTY_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Share, BusinessUnit);
        //
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Create, User);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Read, Organization);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Modify, User);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Delete, None);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Execute, User);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Append, BusinessUnit);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.AppendTo, BusinessUnit);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Assign, None);
        SERVICE_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Share, BusinessUnit);
        //
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Create, None);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Read, BusinessUnit);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Modify, None);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Delete, None);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Execute, None);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Append, None);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.AppendTo, None);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Assign, None);
        GUEST_ROLE_RELATED_ACCESS_MAP.put(AccessRight.Share, None);
        //
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Create, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Read, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Modify, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Delete, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Execute, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Append, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.AppendTo, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Assign, None);
        MANAGER_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Share, None);
        //
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Create, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Read, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Modify, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Delete, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Execute, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Append, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.AppendTo, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Assign, None);
        DEPUTY_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Share, None);
        //
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Create, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Read, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Modify, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Delete, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Execute, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Append, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.AppendTo, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Assign, None);
        SERVICE_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Share, None);
        //
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Create, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Read, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Modify, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Delete, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Execute, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Append, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.AppendTo, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Assign, None);
        GUEST_ROLE_OTHER_ACCESS_MAP.put(AccessRight.Share, None);
    }

    public static Map<AccessRight, AccessLevel> getPrivilegeRoleMap(
            FunctionalHierarchy functionalHierarchy,
            EntityAccessType entityAccessType,
            Package entityPackage) {
        Map<AccessRight, AccessLevel> map = getPrivilegeRoleMap(functionalHierarchy, entityAccessType);
        return map;
    }

    public static Map<AccessRight, AccessLevel> getPrivilegeRoleMap(
            FunctionalHierarchy functionalHierarchy,
            EntityAccessType entityAccessType,
            Class<? extends DataObjectEntity> entityClass) {
        Map<AccessRight, AccessLevel> map = getPrivilegeRoleMap(functionalHierarchy, entityAccessType);
        return map;
    }

    public static Map<AccessRight, AccessLevel> getPrivilegeRoleMap(
            FunctionalHierarchy functionalHierarchy,
            EntityAccessType entityAccessType) {
        Map<AccessRight, AccessLevel> map = getPrivilegeRoleMap(functionalHierarchy.getGroup(), entityAccessType);
        switch(functionalHierarchy) {
            case Manager:
                break;

            case DeputyManager:
                break;

            case FunctionalManager:
                break;

            case DeputyFunctionalManager:
                break;

            case SeniorOfficer:
                break;

            case Officer:
                break;

            case JuniorOfficer:
                break;

            case Trainee:
                break;

            case Visitor:
                break;
        }

        return map;
    }

    public static Map<AccessRight, AccessLevel> getPrivilegeRoleMap(
            FunctionalHierarchy.Group functionalHierarchyGroup,
            EntityAccessType entityAccessType) {
        switch(functionalHierarchyGroup) {
            case Management:
                switch(entityAccessType) {
                    case Own:
                        return MANAGER_ROLE_OWN_ACCESS_MAP;

                    case Related:
                        return MANAGER_ROLE_RELATED_ACCESS_MAP;

                    case Other:
                        return MANAGER_ROLE_OTHER_ACCESS_MAP;
                }
                break;

            case DeputyManagement:
                switch(entityAccessType) {
                    case Own:
                        return DEPUTY_ROLE_OWN_ACCESS_MAP;

                    case Related:
                        return DEPUTY_ROLE_RELATED_ACCESS_MAP;

                    case Other:
                        return DEPUTY_ROLE_OTHER_ACCESS_MAP;
                }
                break;

            case Service:
                switch(entityAccessType) {
                    case Own:
                        return SERVICE_ROLE_OWN_ACCESS_MAP;

                    case Related:
                        return SERVICE_ROLE_RELATED_ACCESS_MAP;

                    case Other:
                        return SERVICE_ROLE_OTHER_ACCESS_MAP;
                }
                break;

            case Guest:
                switch(entityAccessType) {
                    case Own:
                        return GUEST_ROLE_OWN_ACCESS_MAP;

                    case Related:
                        return GUEST_ROLE_RELATED_ACCESS_MAP;

                    case Other:
                        return GUEST_ROLE_OTHER_ACCESS_MAP;
                }
                break;
        }

        throw new UnsupportedOperationException("Unsupported combination of functionalHierarchyGroup=" + functionalHierarchyGroup +
                " and entityAccessType=" + entityAccessType);
    }
}
