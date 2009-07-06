/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

import com.cosmos.acacia.crm.enums.*;
import com.cosmos.acacia.crm.data.DbResource;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Miro
 */
public enum AccessRight implements DatabaseResource {

    Create(1, //000000001,
            'C',
            "Allows the user to create a record for the specified entity." +
            "Note: One additional stipulation exists in order to be able to " +
            "create records for an entity. As an added security measure, the " +
            "role must provide BOTH the Create and Read privileges for that " +
            "entity in order for the user to be able to create record."),

    Read(1 << 1, //000000002,
            'R',
            "Allows the user to read a record for this entity. This controls " +
            "which records are displayed on views/forms and reports."),

    Modify(1 << 2, //000000004,
            'M',
            "Allows the user to update (change) a record for this entity."),

    Delete(1 << 3, //000000010,
            'D',
            "Allows the user to delete a record for the entity."),

    Execute(1 << 4, //000000020,
            'E',
            "Allows the user to execute some action or to open this view/form."),

    Append(1 << 5, //000000040,
            'A',
            "Allows the user tp append (attach) this entity to another entity."),

    AppendTo(1 << 6, //000000100,
            'T',
            "Allows the user to append other entities to this entity. " +
            "Note: The Append and AppendTo privileges work in combination " +
            "with each other. For example, if a Note is attached to a Case, " +
            "you must have the Append priviledge on the Note and the AppendTo " +
            "privilege on the Case."),

    Assign(1 << 7, //000000200,
            'N',
            "Allows the user to assign ownership of a record for this entity " +
            "to another user."),

    Share(1 << 8, //000000400,
            'S',
            "Allows the user to share a record for this entity with another " +
            "user or team. Sharing enables another user to access a record."),
    ;

    private AccessRight(int right, char initial, String description) {
        this.right = right;
        this.initial = initial;
        this.description = description;
    }

    private int right;
    private char initial;
    private String description;
    private String iconURL;
    private DbResource dbResource;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccessRight.").append(name()).append("[right=").append(right);
        sb.append(", initial=").append(initial);
        sb.append(", dbResource=").append(dbResource);
        sb.append("]");

        return sb.toString();
    }

    public int getRight() {
        return right;
    }

    public char getInitial() {
        return initial;
    }

    public String getDescription() {
        return description;
    }

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

    public static boolean isAccessRight(int accessRightsValue, AccessRight accessRight) {
        return (accessRightsValue & accessRight.getRight()) != 0;
    }

    public static int setAccessRight(int accessRightsValue, AccessRight accessRight, boolean right) {
        if(right) {
            return accessRightsValue | accessRight.getRight();
        } else {
            return accessRightsValue & ~accessRight.getRight();
        }
    }

    public static int getAccessRight(Set<AccessRight> accessRights) {
        int value = 0;
        for (AccessRight accessRight : accessRights) {
            value |= accessRight.getRight();
        }

        return value;
    }

    public static String getAccessRightsInitials(int accessRights) {
        return getAccessRightsInitials(getAccessRights(accessRights));
    }

    public static String getAccessRightsInitials(Set<AccessRight> accessRights) {
        StringBuilder sb = new StringBuilder();
        for(AccessRight right : accessRights) {
            sb.append(right.getInitial());
        }

        return sb.toString();
    }

    public static Set<AccessRight> getAccessRights(int accessRights) {
        if (accessRights == 0) {
            return Collections.emptySet();
        }

        HashSet<AccessRight> set = new HashSet<AccessRight>();
        Map<Integer, AccessRight> map = getAccessRightsMap();
        int position = 1;
        for (int i = 0; i < 64; i++) {
            AccessRight ar;
            if ((accessRights & position) != 0 && (ar = map.get(position)) != null) {
                set.add(ar);
            }
            position <<= 1;
        }

        if (set.isEmpty()) {
            return Collections.emptySet();
        }

        return EnumSet.copyOf(set);
    }
    private static TreeMap<Integer, AccessRight> accessRightsMap;

    public static Map<Integer, AccessRight> getAccessRightsMap() {
        if (accessRightsMap == null) {
            accessRightsMap = new TreeMap<Integer, AccessRight>();
            for (AccessRight accessRight : values()) {
                accessRightsMap.put(accessRight.right, accessRight);
            }
        }

        return accessRightsMap;
    }

    /*public static void main(String[] args) {
        for(AccessRight accessRight : AccessRight.values()) {
            System.out.println(accessRight);
            int right = accessRight.getRight();
            System.out.println("1. isAccessRight(right, accessRight): " + isAccessRight(right, accessRight));
            right = setAccessRight(right, accessRight, false);
            System.out.println("2. isAccessRight(right, accessRight): " + isAccessRight(right, accessRight));
            right = setAccessRight(right, accessRight, true);
            System.out.println("3. isAccessRight(right, accessRight): " + isAccessRight(right, accessRight));
            Set<AccessRight> rights = getAccessRights(right);
            System.out.println("rights: " + rights);
            System.out.println(getAccessRight(rights) + "\n");
        }
    }*/
}
