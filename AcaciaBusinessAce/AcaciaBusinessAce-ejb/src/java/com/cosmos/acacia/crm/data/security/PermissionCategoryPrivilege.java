/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Miro
 */
@Entity
@DiscriminatorValue(value=Privilege.PERMISSION_CATEGORY_ID)
public class PermissionCategoryPrivilege extends Privilege implements Serializable {

    @JoinColumn(name = "permission_category_id", referencedColumnName = "resource_id")
    @ManyToOne(optional = false)
    private DbResource permissionCategory;

    public PermissionCategoryPrivilege(BigInteger privilegeId) {
        super(PERMISSION_CATEGORY_ID, privilegeId);
    }

    public PermissionCategoryPrivilege() {
        super(PERMISSION_CATEGORY_ID);
    }

    @Override
    public DbResource getPermissionCategory() {
        return permissionCategory;
    }

    public void setPermissionCategory(DbResource permissionCategory) {
        this.permissionCategory = permissionCategory;
    }
}
