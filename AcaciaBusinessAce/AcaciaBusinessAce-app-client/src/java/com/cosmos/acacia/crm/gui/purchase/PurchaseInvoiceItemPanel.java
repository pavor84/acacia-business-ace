/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBDecimalField;
import java.math.BigDecimal;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class PurchaseInvoiceItemPanel extends EntityPanel<PurchaseInvoiceItem> {

    protected static final String PRODUCT_PROPERTY_NAME = "product";
    protected static final String RECEIVED_PRICE_PROPERTY_NAME = "receivedPrice";
    protected static final String RECEIVED_QUANTITY_PROPERTY_NAME = "receivedQuantity";
    protected static final String MEASURE_UNIT_PROPERTY_NAME = "measureUnit";
    protected static final String EXTENDED_PRICE_PROPERTY_NAME = "extendedPrice";

    public PurchaseInvoiceItemPanel(AbstractEntityListPanel entityListPanel, PurchaseInvoiceItem entity) {
        super(entityListPanel, entity);
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
        if(RECEIVED_PRICE_PROPERTY_NAME.equals(propertyName)) {
            receivedPricePropertyChanged();
        } else if(RECEIVED_QUANTITY_PROPERTY_NAME.equals(propertyName)) {
            receivedQuantityPropertyChanged();
        } else if(PRODUCT_PROPERTY_NAME.equals(propertyName)) {
            productPropertyChanged();
        }
    }

    protected void productPropertyChanged() {
        Product product = getEntity().getProduct();
        JBComboBox comboBox = (JBComboBox)getJComponentByPropertyName(MEASURE_UNIT_PROPERTY_NAME, JBComboBox.class);
        if(product != null) {
            comboBox.setSelectedItem(product.getMeasureUnit());
        } else {
            comboBox.setSelectedItem(null);
        }

        AcaciaSessionRemote session = getAcaciaSession();
        String expression = session.getExpression(PurchaseInvoiceItem.class, RECEIVED_PRICE_PROPERTY_NAME);
        ELProperty elProperty = create("${" + expression + "}");
        BigDecimal price = (BigDecimal)elProperty.getValue(getEntity());
        JBDecimalField decimalField = (JBDecimalField)getJComponentByPropertyName(RECEIVED_PRICE_PROPERTY_NAME, JBDecimalField.class);
        decimalField.setValue(price);
    }

    protected void receivedPricePropertyChanged() {
        calculateExtendedPrice();
    }

    protected void receivedQuantityPropertyChanged() {
        calculateExtendedPrice();
    }

    protected void calculateExtendedPrice() {
        AcaciaSessionRemote session = getAcaciaSession();
        String expression = session.getExpression(PurchaseInvoiceItem.class, EXTENDED_PRICE_PROPERTY_NAME);
        ELProperty elProperty = create("${" + expression + "}");
        BigDecimal price = (BigDecimal)elProperty.getValue(getEntity());
        JBDecimalField decimalField = (JBDecimalField)getJComponentByPropertyName(EXTENDED_PRICE_PROPERTY_NAME, JBDecimalField.class);
        decimalField.setValue(price);
    }
}
