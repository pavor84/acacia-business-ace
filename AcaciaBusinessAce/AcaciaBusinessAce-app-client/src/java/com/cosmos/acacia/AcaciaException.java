/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia;

/**
 *
 * @author Miro
 */
public class AcaciaException extends RuntimeException {

    public AcaciaException(Throwable cause) {
        super(cause);
    }

    public AcaciaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AcaciaException(String message) {
        super(message);
    }

    public AcaciaException() {
    }
}
