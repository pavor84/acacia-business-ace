/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.enums.AssemblingSchemaItemDataType;

/**
 *
 * @author Miro
 */
public class AssemblingParameter
{
    @Property(
        title="Message",
        customDisplay="${assemblingMessage.messageCode}: ${assemblingMessage.messageText}?",
        editable=false,
        readOnly=true,
        propertyValidator=@PropertyValidator(required=true))
    private AssemblingMessage assemblingMessage;

    @Property(title="Value")
    private Object value;

    @Property(title="Data Type", editable=false, readOnly=true)
    private AssemblingSchemaItemDataType dataType;

    @Property(title="Value Source", editable=false, readOnly=true)
    private Object valuesSource;


    public AssemblingMessage getAssemblingMessage()
    {
        return assemblingMessage;
    }

    public void setAssemblingMessage(AssemblingMessage assemblingMessage)
    {
        this.assemblingMessage = assemblingMessage;
    }

    public AssemblingSchemaItemDataType getDataType()
    {
        return dataType;
    }

    public void setDataType(AssemblingSchemaItemDataType dataType)
    {
        this.dataType = dataType;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Object getValuesSource()
    {
        return valuesSource;
    }

    public void setValuesSource(Object valuesSource)
    {
        this.valuesSource = valuesSource;
    }

    @Override
    public String toString()
    {
        if(assemblingMessage == null)
            return super.toString();

        return assemblingMessage.getMessageCode() + ": " + assemblingMessage.getMessageText();
    }
}
