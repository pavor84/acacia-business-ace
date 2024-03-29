/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.DataObjectType;
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
@DiscriminatorValue(value=Privilege.ENTITY_DATA_OBJECT_TYPE_ID)
public class EntityTypePrivilege extends Privilege implements Serializable {

    @JoinColumn(name = "data_object_type_id", referencedColumnName = "data_object_type_id")
    @ManyToOne(optional = false)
    @Property(title="Entity Type",
        propertyValidator=@PropertyValidator(required=true)
    )
    private DataObjectType entityDataObjectType;

    public EntityTypePrivilege(UUID privilegeId) {
        super(ENTITY_DATA_OBJECT_TYPE_ID, privilegeId);
    }

    public EntityTypePrivilege() {
        super(ENTITY_DATA_OBJECT_TYPE_ID);
    }

    public EntityTypePrivilege(SecurityRole securityRole, DataObjectType entityDataObjectType) {
        this();
        setSecurityRole(securityRole);
        this.entityDataObjectType = entityDataObjectType;
    }

    @Override
    public DataObjectType getEntityDataObjectType() {
        return entityDataObjectType;
    }

    public void setEntityDataObjectType(DataObjectType entityDataObjectType) {
        this.entityDataObjectType = entityDataObjectType;
    }
}
