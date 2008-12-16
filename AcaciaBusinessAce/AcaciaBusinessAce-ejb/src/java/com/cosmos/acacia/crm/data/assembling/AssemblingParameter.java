/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.crm.enums.DataType;

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
        customDisplay="${assemblingMessage.messageCode}",
        editable=false, readOnly=true,
        propertyValidator=@PropertyValidator(required=true))
    private AssemblingMessage assemblingMessage;

    @Property(title="Value")
    private Object value;

    @Property(title="Data Type", editable=false, readOnly=true, visible=false, hidden=true)
    private DataType dataType;

    @Property(
        title="Value Source",
        customDisplay="${valuesSource.accessLevel}",
        editable=false, readOnly=true)
    private AssemblingProperty valuesSource;


    public AssemblingMessage getAssemblingMessage()
    {
        return assemblingMessage;
    }

    public void setAssemblingMessage(AssemblingMessage assemblingMessage)
    {
        this.assemblingMessage = assemblingMessage;
    }

    public DataType getDataType()
    {
        return dataType;
    }

    public void setDataType(DataType dataType)
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

    public AssemblingProperty getValuesSource()
    {
        return valuesSource;
    }

    public void setValuesSource(AssemblingProperty valuesSource)
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

        return assemblingMessage.getMessageCode();
    }
}
