/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

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
public enum AccessRight
{
    Create(000000001,
            "Allows the user to create a record for the specified entity." +
            "Note: One additional stipulation exists in order to be able to " +
            "create records for an entity. As an added security measure, the " +
            "role must provide BOTH the Create and Read privileges for that " +
            "entity in order for the user to be able to create record."),

    Read(000000002,
            "Allows the user to read a record for this entity. This controls " +
            "which records are displayed on views/forms and reports."),

    Modify(000000004,
            "Allows the user to update (change) a record for this entity."),

    Delete(000000010,
            "Allows the user to delete a record for the entity."),

    Execute(000000020,
            "Allows the user to execute some action or to open this view/form."),

    Append(000000040,
            "Allows the user tp append (attach) this entity to another entity."),

    AppendTo(000000100,
            "Allows the user to append other entities to this entity. " +
            "Note: The Append and AppendTo privileges work in combination " +
            "with each other. For example, if a Note is attached to a Case, " +
            "you must have the Append priviledge on the Note and the AppendTo " +
            "privilege on the Case."),

    Assign(000000200,
            "Allows the user to assign ownership of a record for this entity " +
            "to another user."),

    Share(000000400,
            "Allows the user to share a record for this entity with another " +
            "user or team. Sharing enables another user to access a record."),
    ;

    private AccessRight(int right, String description)
    {
        this.right = right;
        this.description = description;
    }

    private int right;
    private String description;
    private String iconURL;

    public int getRight()
    {
        return right;
    }

    public String getDescription()
    {
        return description;
    }

    public static int getAccessRight(Set<AccessRight> accessRights)
    {
        int value = 0;
        for(AccessRight accessRight : accessRights)
        {
            value |= accessRight.getRight();
        }

        return value;
    }

    public static Set<AccessRight> getAccessRights(int accessRights)
    {
        if(accessRights == 0)
            return Collections.emptySet();

        HashSet<AccessRight> set = new HashSet<AccessRight>();
        Map<Integer, AccessRight> map = getAccessRightsMap();
        int position = 1;
        for(int i = 0; i < 32; i++)
        {
            AccessRight ar;
            if((accessRights & position) != 0 && (ar = map.get(position)) != null)
                set.add(ar);
            position <<= 1;
        }

        if(set.isEmpty())
            return Collections.emptySet();

        return EnumSet.copyOf(set);
    }

    private static TreeMap<Integer, AccessRight> accessRightsMap;
    public static Map<Integer, AccessRight> getAccessRightsMap()
    {
        if(accessRightsMap == null)
        {
            accessRightsMap = new TreeMap<Integer, AccessRight>();
            for(AccessRight accessRight : values())
            {
                accessRightsMap.put(accessRight.right, accessRight);
            }
        }

        return accessRightsMap;
    }
}
