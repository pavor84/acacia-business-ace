/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallback;
import com.cosmos.acacia.crm.data.ComplexProduct;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Miro
 */
public abstract class AssembleResult
    implements Serializable
{
    public enum Type
    {
        Initialization,
        Intermediate,
        Final,
        Callback
    }

    private Type type;

    protected AssembleResult(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    public ComplexProduct getComplexProduct()
    {
        throw new UnsupportedOperationException("The method invokation is not supported in this class instance.");
    }

    public ApplicationCallback[] getApplicationCallbacks()
    {
        throw new UnsupportedOperationException("The method invokation is not supported in this class instance.");
    }

    public Map getParameters()
    {
        throw new UnsupportedOperationException("The method invokation is not supported in this class instance.");
    }

}
