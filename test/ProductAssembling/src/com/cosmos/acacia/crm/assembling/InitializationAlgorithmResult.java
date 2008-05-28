/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class InitializationAlgorithmResult
    extends AlgorithmResult
    implements Serializable
{
    private Object valueAgainstConstraints;

    public InitializationAlgorithmResult(Object valueAgainstConstraints)
    {
        super(Type.Initialization);
        this.valueAgainstConstraints = valueAgainstConstraints;
    }

    @Override
    public Object getValueAgainstConstraints()
    {
        return valueAgainstConstraints;
    }
}
