/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.BusinessDocumentPanel;
import com.cosmos.acacia.util.SystemUtils;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.util.MoneyUtils;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class PurchaseInvoicePanel extends BusinessDocumentPanel<PurchaseInvoice> {

    protected static final String DEFAULT_CURRENCY_PROPERTY_NAME = "defaultCurrency";
    protected static final String DOCUMENT_CURRENCY_PROPERTY_NAME = "documentCurrency";
    protected static final String SUPPLIER_PROPERTY_NAME = "supplier";
    protected static final String TOTAL_NET_AMOUNT_PROPERTY_NAME = "totalNetAmount";
    protected static final String TOTAL_TAX_PROPERTY_NAME = "totalTax";
    protected static final String TOTAL_GROSS_AMOUNT_PROPERTY_NAME = "totalGrossAmount";
    protected static final String INVOICE_PROPERTY_NAME = "invoice";

    public PurchaseInvoicePanel(AbstractEntityListPanel entityListPanel, PurchaseInvoice entity) {
        super(entityListPanel, entity);
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
        if(SUPPLIER_PROPERTY_NAME.equals(propertyName)) {
            supplierPropertyChanged();
        } else if(DOCUMENT_CURRENCY_PROPERTY_NAME.equals(propertyName)) {
            documentCurrencyPropertyChanged(propertyName, jComponent, event);
        }
    }

    protected void supplierPropertyChanged() {
        BusinessPartner supplier = getEntity().getSupplier();
        JBComboBox comboBox = (JBComboBox)getJComponentByPropertyName(DOCUMENT_CURRENCY_PROPERTY_NAME, JBComboBox.class);
        if(supplier != null) {
            comboBox.setSelectedItem(supplier.getDefaultCurrency());
        } else {
            comboBox.setSelectedItem(null);
        }
    }

    protected void documentCurrencyPropertyChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
        if(getEntity().getId() == null) {
            return;
        }

        DbResource fromCurrency = (DbResource)event.getOldValue();
        DbResource toCurrency = (DbResource)event.getNewValue();
        if(fromCurrency == null || toCurrency == null) {
            return;
        }

        String unitPricePropertyName = PurchaseInvoiceItemPanel.RECEIVED_PRICE_PROPERTY_NAME;
        String extPricePropertyName = PurchaseInvoiceItemPanel.EXTENDED_PRICE_PROPERTY_NAME;
        PurchaseInvoice invoice = getEntity();
        List<PurchaseInvoiceItem> itemList = getEntityService().getEntityItems(invoice, PurchaseInvoiceItem.class);
        AcaciaSessionRemote session = getAcaciaSession();
        String unitPriceExpression = session.getExpression(PurchaseInvoiceItem.class, unitPricePropertyName);
        String extPriceExpression = session.getExpression(PurchaseInvoiceItem.class, extPricePropertyName);
        ELProperty elpUnitPrice = create("${" + unitPriceExpression + "}");
        ELProperty elpExtPrice = create("${" + extPriceExpression + "}");
        BigDecimal price = null;
        BigDecimal totalTax = null;
        Date now = new Date();
        MoneyUtils moneyUtils = MoneyUtils.getInstance();
        for(PurchaseInvoiceItem item : itemList) {
            item.setInvoice(invoice);
            BigDecimal unitPrice = (BigDecimal)elpUnitPrice.getValue(item);
            item.setReceivedPrice(unitPrice);
            BigDecimal extPrice = (BigDecimal)elpExtPrice.getValue(item);
            item.setExtendedPrice(extPrice);
            price = moneyUtils.add(price, extPrice);
            BigDecimal tax;
            if((tax = item.getTaxValue()) != null) {
                tax = SystemUtils.convertAmount(tax, now, fromCurrency, toCurrency);
                totalTax = moneyUtils.add(totalTax, tax);
                item.setTaxValue(tax);
            }
            getEntityService().save(item);
        }
        invoice.setTotalNetAmount(price);
        invoice.setTotalTax(totalTax);
        invoice.setTotalGrossAmount(moneyUtils.add(price, totalTax));
        saveAndRefreshUI(Arrays.asList(
                getBinding(TOTAL_NET_AMOUNT_PROPERTY_NAME, JBDecimalField.class),
                getBinding(TOTAL_TAX_PROPERTY_NAME, JBDecimalField.class),
                getBinding(TOTAL_GROSS_AMOUNT_PROPERTY_NAME, JBDecimalField.class)
                ));
        PurchaseInvoiceItemListPanel listPanel = getJComponent("itemList", PurchaseInvoiceItemListPanel.class);
        listPanel.refresh();
    }
}
