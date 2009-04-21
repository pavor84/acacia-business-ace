/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.purchase;

/**
 *
 * @author Miro
 */
public class PurchaseInvoiceException extends RuntimeException {

    public PurchaseInvoiceException(Throwable cause) {
        super(cause);
    }

    public PurchaseInvoiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PurchaseInvoiceException(String message) {
        super(message);
    }

    public PurchaseInvoiceException() {
    }

}
