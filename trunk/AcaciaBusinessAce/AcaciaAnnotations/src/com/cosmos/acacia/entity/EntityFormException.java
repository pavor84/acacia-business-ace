/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.entity;

/**
 *
 * @author Miro
 */
public class EntityFormException extends RuntimeException {

    public EntityFormException(Throwable cause) {
        super(cause);
    }

    public EntityFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityFormException(String message) {
        super(message);
    }

    public EntityFormException() {
    }
}
