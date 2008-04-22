/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.util.List;

/**
 *
 * @author Miro
 */
public class UnconditionalSelectionAlgorithm
    extends AbstractAlgorithm
{
    public UnconditionalSelectionAlgorithm()
    {
        super(Type.UnconditionalSelection);
    }

    public List apply(List objects)
    {
        return objects;
    }
}
