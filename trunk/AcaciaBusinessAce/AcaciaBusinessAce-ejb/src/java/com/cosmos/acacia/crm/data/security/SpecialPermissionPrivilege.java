/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBLabel;
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
@DiscriminatorValue(value=Privilege.ENTITY_DATA_OBJECT_TYPE_ID)
public class SpecialPermissionPrivilege extends Privilege implements Serializable {

    @JoinColumn(name = "special_permission_id", referencedColumnName = "resource_id")
    @ManyToOne(optional = false)
    @Property(title="Permission",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.SpecialPermission"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Permission:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource specialPermission;

    public SpecialPermissionPrivilege(BigInteger privilegeId) {
        super(ENTITY_DATA_OBJECT_TYPE_ID, privilegeId);
    }

    public SpecialPermissionPrivilege() {
        super(ENTITY_DATA_OBJECT_TYPE_ID);
    }

    @Override
    public DbResource getSpecialPermission() {
        return specialPermission;
    }

    public void setSpecialPermission(DbResource specialPermission) {
        this.specialPermission = specialPermission;
    }
}
