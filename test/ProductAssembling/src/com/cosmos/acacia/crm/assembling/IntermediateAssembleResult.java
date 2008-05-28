/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.ComplexProduct;
import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class IntermediateAssembleResult
    extends AssembleResult
    implements Serializable
{
    private ComplexProduct complexProduct;

    public IntermediateAssembleResult(ComplexProduct complexProduct)
    {
        super(Type.Intermediate);
        this.complexProduct = complexProduct;
    }

    @Override
    public ComplexProduct getComplexProduct()
    {
        return complexProduct;
    }
}
