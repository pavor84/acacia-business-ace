/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.gui.entity.EntityListPanel;

/**
 *
 * @author Miro
 */
public class PurchaseInvoiceListPanel extends EntityListPanel<PurchaseInvoice> {

    public PurchaseInvoiceListPanel() {
        super(PurchaseInvoice.class);
    }
}