/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallback;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Miro
 */
public abstract class AlgorithmResult
    implements Serializable
{
    public enum Type
    {
        Initialization,
        Final,
        Callback,
        Exception
    }

    private Type type;

    protected AlgorithmResult(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    public List<AssemblingSchemaItemValue> getAssemblingSchemaItemValues()
    {
        throw new UnsupportedOperationException("The method invokation is not supported in this class instance.");
    }

    public ApplicationCallback[] getApplicationCallbacks()
    {
        throw new UnsupportedOperationException("The method invokation is not supported in this class instance.");
    }

    public Object getValueAgainstConstraints()
    {
        throw new UnsupportedOperationException("The method invokation is not supported in this class instance.");
    }

    public Exception getException()
    {
        throw new UnsupportedOperationException("The method invokation is not supported in this class instance.");
    }
}
