/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBLabel;
import java.io.Serializable;
import java.util.UUID;
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
    @Property(title="Permission Category",
        propertyValidator=@PropertyValidator(required=true),
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.PermissionCategory"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Permission Category:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource permissionCategory;

    public PermissionCategoryPrivilege(UUID privilegeId) {
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
