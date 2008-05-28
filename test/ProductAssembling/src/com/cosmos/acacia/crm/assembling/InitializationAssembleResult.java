/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Miro
 */
public class InitializationAssembleResult
    extends AssembleResult
    implements Serializable
{
    private Map parameters;

    public InitializationAssembleResult(Map parameters)
    {
        super(Type.Initialization);
        this.parameters = parameters;
    }

    @Override
    public Map getParameters()
    {
        return parameters;
    }
}
