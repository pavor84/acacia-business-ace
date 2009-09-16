/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import java.util.EnumSet;
import java.util.Set;

/**
 * Access Level for a specific priviledge and record type determines which
 * records you can perform that action upon based upon the location of each
 * record owner.
 *
 * Access levels work in conjunction with Privileges.
 * - Privileges indicate what actions a user can perform on each entity
 * - Access levels define which records for that entity the user can perform
 * actions upon
 *
 * Access levels are based on a combination of:
 * - user ownership, and
 * - the business unit to which the user belongs
 *
 */

/**
    Contact
    Organization extends Contact
    Person extends Contact
    Lead, Opportunity, Organization or Person
 */

/**
 *
 * @author Miro
 */
public enum AccessLevel implements DatabaseResource {

    /**
     * You cannot perform that action on ANY records within that entity – even
     * on records that you own!
     */
    None,

    /**
     * This is level is for temporary usage during the user session.
     * It is not persisted.
     */
    Session,

    ClientContact,

    /**
     * Gives t
     * The client can be Lead, Opportunity, Organization or Person
     */
    Client,

    /**
     * Gives you the following access for an entity and privilege:
     * - Records that you own
     * - Records owned by someone else that have been shared with you.
     * - Records shared with a Team in which you are a member.
     */
    User,

    /**
     * Gives you the following access for an entity and privilege:
     * - User access, plus
     * - The ability to perform that action on records that are owned by or
     * shared with other users assigned to your business unit.
     */
    BusinessUnit,

    /**
     * This option provides BusinessUnit access, plus the ability to perform the
     * action on all records for that entity that are owned by users assigned to
     * business units subordinate to your business unit, regardless of how far
     * down in the organizational hierarchy the subordinate business units may
     * appear.
     * Gives you the following access for an entity and privilege:
     * - Business Unit access, plus
     * - The ability to perform that action on records that are owned by users
     * and shared with users who are assigned to any business unit subordinate
     * to your business unit - regardless of how deep in the organizational
     * structure the user’s business unit appears.
     *
     *        Parent Business Unit
     *               |      |
     *         +-----+      +-------------+--------------+------+
     *         |                          |              |      |
     * User Business Unit     Children Business Units   ...    ...
     */
    ParentChildBusinessUnit,

    /**
     * Gives you the following access for an entity and privilege:
     * - You can perform that action on records owned by any user within the
     * entire organization, regardless of the business unit to which the owner
     * belongs.
     * - There are no access restrictions.
     */
    Organization,

    /**
     * Gives you the following access for an entity and privilege:
     * - You can perform that action on records owned by any user within the
     * entire system.
     * - There are no access restrictions.
     */
    System,
    ;

    private AccessLevel() {
    }

    public static final Set<AccessLevel> PropertyLevels =
            EnumSet.of(Client, User, BusinessUnit, ParentChildBusinessUnit, Organization);

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
