/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem;
import com.cosmos.acacia.gui.entity.AlterationType;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.util.MoneyUtils;
import java.math.BigDecimal;

/**
 *
 * @author Miro
 */
public class PurchaseInvoiceItemListPanel extends DetailEntityListPanel<PurchaseInvoice, PurchaseInvoiceItem> {

    public PurchaseInvoiceItemListPanel(EntityPanel<PurchaseInvoice> mainEntityPanel, Class<PurchaseInvoiceItem> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    @Override
    protected EntityPanel getEntityPanel(PurchaseInvoiceItem entity) {
        return new PurchaseInvoiceItemPanel(this, entity);
    }

    @Override
    public void rowChanged(AlterationType alterationType, PurchaseInvoiceItem oldRowObject, PurchaseInvoiceItem newRowObject) {
        if(AlterationType.Nothing.equals(alterationType)) {
            return;
        }

        PurchaseInvoice invoice = getMainEntity();

        BigDecimal netAmount = invoice.getTotalNetAmount();
        BigDecimal grossAmount = invoice.getTotalGrossAmount();
        BigDecimal tax = invoice.getTotalTax();
        BigDecimal quantity = invoice.getTotalQuantity();
        MoneyUtils moneyUtils = MoneyUtils.getInstance();

        if(AlterationType.Delete.equals(alterationType) || AlterationType.Modify.equals(alterationType)) {
            netAmount = moneyUtils.subtract(netAmount, oldRowObject.getExtendedPrice());
            tax = moneyUtils.subtract(tax, oldRowObject.getTaxValue());
            quantity = moneyUtils.subtract(quantity, oldRowObject.getReceivedQuantity());
        }

        if(AlterationType.Create.equals(alterationType) || AlterationType.Modify.equals(alterationType)) {
            netAmount = moneyUtils.add(netAmount, newRowObject.getExtendedPrice());
            tax = moneyUtils.add(tax, newRowObject.getTaxValue());
            quantity = moneyUtils.add(quantity, newRowObject.getReceivedQuantity());
        }

        grossAmount = moneyUtils.add(netAmount, tax);

        invoice.setTotalGrossAmount(grossAmount);
        invoice.setTotalNetAmount(netAmount);
        invoice.setTotalQuantity(quantity);
        invoice.setTotalTax(tax);

        getMainEntityPanel().performSave(false);
    }

}
