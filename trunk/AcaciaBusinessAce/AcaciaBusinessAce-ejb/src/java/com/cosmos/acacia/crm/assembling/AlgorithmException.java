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
public class AlgorithmException
    extends Exception
    implements Serializable
{

    public AlgorithmException(Throwable cause) {
        super(cause);
    }

    public AlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlgorithmException(String message) {
        super(message);
    }

}
