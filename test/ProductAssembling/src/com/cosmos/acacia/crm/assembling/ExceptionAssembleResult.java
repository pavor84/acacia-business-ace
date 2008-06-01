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
public class ExceptionAssembleResult
    extends AssembleResult
    implements Serializable
{
    public ExceptionAssembleResult(Exception exception)
    {
        super(Type.Exception);
        setException(exception);
    }
}
