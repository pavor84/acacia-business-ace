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
public class FinalAssembleResult
    extends AssembleResult
    implements Serializable
{

    public FinalAssembleResult(ComplexProduct complexProduct)
    {
        super(Type.Final);
        setComplexProduct(complexProduct);
    }

}
