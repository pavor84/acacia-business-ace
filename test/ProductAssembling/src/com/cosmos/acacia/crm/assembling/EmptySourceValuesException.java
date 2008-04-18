/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

/**
 *
 * @author Miro
 */
public class EmptySourceValuesException
    extends AlgorithmException
{
    public EmptySourceValuesException()
    {
        super("Empty source values list.");
    }
}
