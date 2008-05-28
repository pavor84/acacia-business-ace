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
public class ExceptionAlgorithmResult
    extends AlgorithmResult
    implements Serializable
{
    private Exception exception;

    public ExceptionAlgorithmResult(Exception exception)
    {
        super(Type.Exception);
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }
}
