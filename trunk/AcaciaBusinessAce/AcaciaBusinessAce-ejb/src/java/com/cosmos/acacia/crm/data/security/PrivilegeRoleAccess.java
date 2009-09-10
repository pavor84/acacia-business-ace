/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.security.AccessLevel;
import com.cosmos.acacia.security.AccessRight;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Miro
 */
public class PrivilegeRoleAccess implements Comparator<PrivilegeRoleAccess>, Comparable<PrivilegeRoleAccess>, Serializable {

    private AccessRight accessRight;
    private AccessLevel accessLevel;

    public PrivilegeRoleAccess(AccessRight accessRight, AccessLevel accessLevel) {
        this.accessRight = accessRight;
        this.accessLevel = accessLevel;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    @Override
    public int compareTo(PrivilegeRoleAccess o) {
        return compare(this, o);
    }

    @Override
    public int compare(PrivilegeRoleAccess o1, PrivilegeRoleAccess o2) {
        if(o1 == null) {
            return -1;
        }
        if(o2 == null) {
            return 1;
        }

        int result;
        if((result = o1.accessRight.compareTo(o2.accessRight)) != 0) {
            return result;
        }

        return o1.accessLevel.compareTo(o2.accessLevel);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrivilegeRoleAccess other = (PrivilegeRoleAccess) obj;
        return compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + accessRight.hashCode();
        hash = 53 * hash + accessLevel.hashCode();
        return hash;
    }
}
