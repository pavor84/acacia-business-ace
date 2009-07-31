/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.DataObject;
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
@DiscriminatorValue(value=Privilege.ENTITY_DATA_OBJECT_ID)
public class EntityPrivilege extends Privilege implements Serializable {

    @JoinColumn(name = "data_object_id", referencedColumnName = "data_object_id")
    @ManyToOne(optional = false)
    @Property(title="Entity",
        propertyValidator=@PropertyValidator(required=true)
    )
    private DataObject entityDataObject;

    public EntityPrivilege(BigInteger privilegeId) {
        super(ENTITY_DATA_OBJECT_ID, privilegeId);
    }

    public EntityPrivilege() {
        super(ENTITY_DATA_OBJECT_ID);
    }

    @Override
    public DataObject getEntityDataObject() {
        return entityDataObject;
    }

    public void setEntityDataObject(DataObject entityDataObject) {
        this.entityDataObject = entityDataObject;
    }
}
