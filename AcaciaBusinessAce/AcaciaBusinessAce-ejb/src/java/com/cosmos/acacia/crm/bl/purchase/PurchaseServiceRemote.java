/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.purchase;

import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem;
import com.cosmos.beansbinding.EntityProperties;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface PurchaseServiceRemote {
    EntityProperties getEntityProperties(Class entityClass);

    List<PurchaseInvoice> getPurchaseInvoices();
    PurchaseInvoice newPurchaseInvoice();
    PurchaseInvoice savePurchaseInvoice(PurchaseInvoice purchaseInvoice);
    PurchaseInvoice completePurchaseInvoice(PurchaseInvoice purchaseInvoice);
    boolean deletePurchaseInvoice(PurchaseInvoice purchaseInvoice);

    List<PurchaseInvoiceItem> getPurchaseInvoiceItems(PurchaseInvoice purchaseInvoice);
    PurchaseInvoiceItem newPurchaseInvoiceItem(PurchaseInvoice purchaseInvoice);
    PurchaseInvoiceItem savePurchaseInvoiceItem(PurchaseInvoiceItem invoiceItem);
    boolean deletePurchaseInvoiceItem(PurchaseInvoiceItem invoiceItem);
}
