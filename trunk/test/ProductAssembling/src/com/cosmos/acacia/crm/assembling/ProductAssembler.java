/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.util.Map;

/**
 *
 * @author Miro
 */
public class ProductAssembler
{
    private Map parameters;


    public AssembleResult next(AssembleResult assembleResult)
    {
        if(parameters == null)
        {
            if(!AssembleResult.Type.Initialization.equals(assembleResult))
                throw new IllegalArgumentException("The first arguments passed through AssembleResult must be Initialization (Map parameters which can not be NULL).");

            parameters = assembleResult.getParameters();
        }

        

        return null;
    }
}
