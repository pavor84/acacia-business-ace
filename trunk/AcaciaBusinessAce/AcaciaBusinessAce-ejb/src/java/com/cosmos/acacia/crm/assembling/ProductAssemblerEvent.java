/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.product.ComplexProductItem;
import java.io.Serializable;
import java.util.EventObject;

/**
 *
 * @author Miro
 */
public class ProductAssemblerEvent
    extends EventObject
    implements Serializable
{
    public enum Type
    {
        AddComplexProductItem,
    };

    private Type type;
    private ComplexProduct complexProduct;
    private ComplexProductItem complexProductItem;


    public ProductAssemblerEvent(
        ProductAssembler source,
        ComplexProduct complexProduct,
        ComplexProductItem complexProductItem)
    {
        super(source);
        this.type = Type.AddComplexProductItem;
        this.complexProduct = complexProduct;
        this.complexProductItem = complexProductItem;
    }

    public Type getType()
    {
        return type;
    }

    public ComplexProduct getComplexProduct()
    {
        return complexProduct;
    }

    public ComplexProductItem getComplexProductItem()
    {
        return complexProductItem;
    }

}
