/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;

/**
 *
 * @author Miro
 */
public class AssemblingParameter
{
    @Property(
        title="Order Position",
        editable=false, readOnly=true, hidden=true, visible=false)
    private int sortOrder;

    @Property(
        title="Message",
        customDisplay="${assemblingMessage.messageCode}: ${assemblingMessage.messageText}?",
        editable=false, readOnly=true,
        propertyValidator=@PropertyValidator(required=true))
    private AssemblingMessage assemblingMessage;

    @Property(title="Value")
    private Object value;

    @Property(title="Data Type", editable=false, readOnly=true, visible=false, hidden=true)
    private AssemblingSchemaItem.DataType dataType;

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

    public AssemblingSchemaItem.DataType getDataType()
    {
        return dataType;
    }

    public void setDataType(AssemblingSchemaItem.DataType dataType)
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

    public int getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString()
    {
        if(assemblingMessage == null)
            return super.toString();

        return assemblingMessage.getMessageCode() + ": " + assemblingMessage.getMessageText();
    }
}
