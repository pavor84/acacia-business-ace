/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.document.business;

/**
 *
 * @author Miro
 */
public class BusinessDocumentException extends RuntimeException {

    public BusinessDocumentException(Throwable cause) {
        super(cause);
    }

    public BusinessDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessDocumentException(String message) {
        super(message);
    }

    public BusinessDocumentException() {
    }

}
