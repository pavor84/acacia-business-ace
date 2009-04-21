/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.purchase;

/**
 *
 * @author Miro
 */
public enum ReceiptType {

    /**
     * The goods which are returned from the customer because:
     * - are damaged
     * - the evaluation period is expired
     */
    GoodsReturn,
    /**
     * The goods of PO
     */
    PurchaseOrder,
}
