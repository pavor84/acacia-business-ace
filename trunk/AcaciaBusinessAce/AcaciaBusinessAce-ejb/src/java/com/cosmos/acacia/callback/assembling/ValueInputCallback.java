/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.callback.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.util.AcaciaUtils;
import java.io.Serializable;
import javax.security.auth.callback.Callback;

/**
 *
 * @author Miro
 */
public class ValueInputCallback
    implements Callback, Serializable
{
    private AssemblingSchemaItem assemblingSchemaItem;
    private Serializable initialValue;

    @Property(title="Value", propertyValidator=@PropertyValidator(required=true))
    private Serializable value;

    public ValueInputCallback(
        AssemblingSchemaItem assemblingSchemaItem,
        Serializable initialValue)
    {
        this.assemblingSchemaItem = assemblingSchemaItem;
        this.initialValue = initialValue;
    }

    public Serializable getValue()
    {
        return value;
    }

    public void setValue(Serializable value)
    {
        this.value = AcaciaUtils.validateValue(value, assemblingSchemaItem, "itemValue");
    }

    public AssemblingSchemaItem getAssemblingSchemaItem()
    {
        return assemblingSchemaItem;
    }

    public Serializable getInitialValue()
    {
        return initialValue;
    }

}
