/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallback;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import java.io.Serializable;
import java.util.List;
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
        Callback,
        Exception
    }

    private Type type;

    private ComplexProduct complexProduct;
    private ApplicationCallback[] applicationCallbacks;
    private Map parameters;
    private Exception exception;
    private List<ComplexProductItem> complexProductItems;


    protected AssembleResult(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    public ApplicationCallback[] getApplicationCallbacks() {
        return applicationCallbacks;
    }

    public void setApplicationCallbacks(ApplicationCallback[] applicationCallbacks) {
        this.applicationCallbacks = applicationCallbacks;
    }

    public ComplexProduct getComplexProduct() {
        return complexProduct;
    }

    public void setComplexProduct(ComplexProduct complexProduct) {
        this.complexProduct = complexProduct;
    }

    public List<ComplexProductItem> getComplexProductItems() {
        return complexProductItems;
    }

    public void setComplexProductItems(List<ComplexProductItem> complexProductItems) {
        this.complexProductItems = complexProductItems;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

}
