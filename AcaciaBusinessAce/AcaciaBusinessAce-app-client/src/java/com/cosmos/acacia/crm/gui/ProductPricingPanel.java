/*
 * ProductPricingPanel.java
 *
 * Created on Сряда, 2008, Декември 10, 15:33
 */

package com.cosmos.acacia.crm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.cosmos.acacia.crm.data.ProductPricingValue;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.ProductPricingValue.Type;
import com.cosmos.acacia.crm.gui.pricing.ProductPricingValueListPanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaPercentValueField;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBComboBox;

/**
 * 
 * Created	:	13.12.2008
 * @author	Petar Milev
 *
 */
public class ProductPricingPanel extends AcaciaPanel {

    private SimpleProduct product;
    
    /** Creates new form ProductPricingPanel */
    public ProductPricingPanel(SimpleProduct product) {
        this.product = product;
        initComponents();
        initComponentsCustom();
    }

    private void initComponentsCustom() {
        additionalDiscountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onChoosePriceValue(ProductPricingValue.Type.DISCOUNT);}});
        transportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onChoosePriceValue(ProductPricingValue.Type.TRANSPORT);}});
        dutyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onChoosePriceValue(ProductPricingValue.Type.DUTY);}});
        additionalProfitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onChoosePriceValue(ProductPricingValue.Type.PROFIT);}});
        
        additionalProfitField.setPercentInclusive(true);
        categoryProfitField.setPercentInclusive(true);
        totalProfitField.setPercentInclusive(true);
        
        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(currencyField, resourceToStringConverter);
        
        ActionListener valueListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onValueUpdated(e);
            }
        };
        
        additionalDiscountField.addActionListener(valueListener);
        dutyField.addActionListener(valueListener);
        transportPriceField.addActionListener(valueListener);
        additionalProfitField.addActionListener(valueListener);
        
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductPricingPanel.this.close();
            }
        });
    }

    protected void onValueUpdated(ActionEvent e) {
        Type type = null;
        if ( additionalDiscountField.equals(e.getSource()) ){
            type = Type.DISCOUNT;
        }else if ( dutyField.equals(e.getSource())){
            type = Type.DUTY;
        }else if ( transportPriceField.equals(e.getSource())){
            type = Type.TRANSPORT;
        }else if ( additionalProfitField.equals(e.getSource())){
            type = Type.PROFIT;
        }
        
        if ( AcaciaPercentValueField.COMMAND_CLEAR.equals(e.getActionCommand())){
            onValueUpdated(null, type);
        }else if (Type.TRANSPORT.equals(type) && AcaciaPercentValueField.COMMAND_VALUE_EDIT.equals(e.getActionCommand())){
            product.setTransportPricingValue(null);
        }
    }

    protected void onChoosePriceValue(Type type) {
        ProductPricingValueListPanel valuesPanel = new ProductPricingValueListPanel(getOrganizationDataObjectId(), type);
        DialogResponse resp = valuesPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(resp)){
            ProductPricingValue pricingValue = (ProductPricingValue) valuesPanel.getSelectedRowObject();
            onValueUpdated(pricingValue, type);
        }else{
            ProductPricingValue oldValue = null;
            if ( Type.DISCOUNT.equals(type) )
                oldValue = product.getDiscountPricingValue();
            else if ( Type.DUTY.equals(type) )
                oldValue = product.getDutyPricingValue();
            else if ( Type.TRANSPORT.equals(type) )
                oldValue = product.getTransportPricingValue();
            else if ( Type.PROFIT.equals(type) )
                oldValue = product.getProfitPricingValue();
            for (Object ppValueObj : valuesPanel.getListData()) {
                ProductPricingValue current = (ProductPricingValue) ppValueObj;
                if ( current.getId().equals(oldValue.getId())){
                    if ( !oldValue.getValue().equals(current.getValue()))
                        onValueUpdated(current, type);
                    break;
                }
            }
        }
    }

    private void onValueUpdated(ProductPricingValue pricingValue, Type type) {
        BigDecimal numberValue = null;
        if ( pricingValue!=null ){
            numberValue = pricingValue.getValue();
        }
        if ( Type.DISCOUNT.equals(type) ){
            additionalDiscountField.setPercent(numberValue);
            product.setDiscountPricingValue(pricingValue);
            updateTotalDiscountField(true);
            updatePurchasePriceField(true);
        }
        else if ( Type.DUTY.equals(type) ){
            dutyField.setPercent(numberValue);
            product.setDutyPricingValue(pricingValue);
            updateCostPriceField(true);
        }
        else if ( Type.TRANSPORT.equals(type)){
            transportPriceField.setPercent(numberValue);
            product.setTransportPricingValue(pricingValue);
            updateCostPriceField(true);
            transportPriceField.setFreezePercent(true);
        }
        else if ( Type.PROFIT.equals(type)){
            additionalProfitField.setPercent(numberValue);
            product.setProfitPricingValue(pricingValue);
            updateTotalProfitField(true);
            updateSalePriceField(true);
        }
    }
    
    public void updateTotalProfitField(boolean valid) {
        BigDecimal totalProfit = null;
        if ( valid ){
            totalProfit = product.getTotalProfit();
        }
        
        this.getTotalProfitField().setPercent(totalProfit); 
    }

    public void updateSalePriceField(boolean valid) {
        BigDecimal salePrice = null;
        if ( valid )
            salePrice = product.getSalePrice();
        
        this.getSalesPriceField().setValue(salePrice);
    }

    public void updateCostPriceField(boolean valid) {
        BigDecimal costPrice = null;
        if ( valid )
            costPrice = product.getCostPrice();
        
        this.getCostPriceField().setValue(costPrice);
    }
    
    public void onCostPriceUpdated(boolean valid){
        BigDecimal costPrice = null;
        if ( valid )
            costPrice = product.getCostPrice();
        this.getCategoryProfitField().totalValueChanged(costPrice);
        this.getAdditionalProfitField().totalValueChanged(costPrice);
        this.getTotalProfitField().totalValueChanged(costPrice);
        
        updateSalePriceField(valid);
    }
    
    public void updateTotalDiscountField(boolean valid) {
        BigDecimal totalDiscount = null;
        if ( valid ){
            totalDiscount = product.getTotalDiscount();
        }
        
        this.getTotalDiscountField().setPercent(totalDiscount);  
    }

    public void updatePurchasePriceField(boolean valid) {
        BigDecimal purchasePrice = null;
        if ( valid ){
            purchasePrice = product.getPurchasePrice();
        }
        
        this.getPurchasePriceField().setValue(purchasePrice);  
    }
    
    public void onPurchasePriceUpdated(boolean valid) {
        BigDecimal purchasePrice = null;
        if ( valid )
            purchasePrice = product.getPurchasePrice();
        //update transport and duty calculations
        this.getTransportPriceField().totalValueChanged(purchasePrice);
        this.getDutyField().totalValueChanged(purchasePrice);
        
        //update cost price
        updateCostPriceField(valid);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPriceLabel = new com.cosmos.swingb.JBLabel();
        listPriceField = new com.cosmos.swingb.JBFormattedTextField();
        currencyField = new com.cosmos.swingb.JBComboBox();
        additionalDiscountField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        additionalDiscountButton = new com.cosmos.swingb.JBButton();
        additionalDiscountLabel = new com.cosmos.swingb.JBLabel();
        purchasePriceField = new com.cosmos.swingb.JBFormattedTextField();
        purchasePriceLabel = new com.cosmos.swingb.JBLabel();
        transportPriceField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        transportButton = new com.cosmos.swingb.JBButton();
        transportPriceLabel = new com.cosmos.swingb.JBLabel();
        dutyField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        dutyButton = new com.cosmos.swingb.JBButton();
        dutyLabel = new com.cosmos.swingb.JBLabel();
        costPriceField = new com.cosmos.swingb.JBFormattedTextField();
        costPriceLabel = new com.cosmos.swingb.JBLabel();
        additionalProfitField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        additionalProfitButton = new com.cosmos.swingb.JBButton();
        additionalProfitLabel = new com.cosmos.swingb.JBLabel();
        salesPriceField = new com.cosmos.swingb.JBFormattedTextField();
        salesPriceLabel = new com.cosmos.swingb.JBLabel();
        closeButton = new com.cosmos.swingb.JBButton();
        categoryDiscountField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        categoryDiscountLabel = new com.cosmos.swingb.JBLabel();
        totalDiscountField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        totalDiscountLabel = new com.cosmos.swingb.JBLabel();
        categoryProfitField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        categoryProfitLabel = new com.cosmos.swingb.JBLabel();
        totalProfitField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        totalProfitLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductPricingPanel.class);
        listPriceLabel.setText(resourceMap.getString("listPriceLabel.text")); // NOI18N
        listPriceLabel.setName("listPriceLabel"); // NOI18N

        listPriceField.setText(resourceMap.getString("listPriceField.text")); // NOI18N
        listPriceField.setName("listPriceField"); // NOI18N
        listPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listPriceFieldActionPerformed(evt);
            }
        });

        currencyField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BGN" }));
        currencyField.setName("currencyField"); // NOI18N

        additionalDiscountField.setName("additionalDiscountField"); // NOI18N

        additionalDiscountButton.setMnemonic('D');
        additionalDiscountButton.setText(resourceMap.getString("additionalDiscountButton.text")); // NOI18N
        additionalDiscountButton.setName("additionalDiscountButton"); // NOI18N
        additionalDiscountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                additionalDiscountButtonActionPerformed(evt);
            }
        });

        additionalDiscountLabel.setText(resourceMap.getString("additionalDiscountLabel.text")); // NOI18N
        additionalDiscountLabel.setName("additionalDiscountLabel"); // NOI18N

        purchasePriceField.setEditable(false);
        purchasePriceField.setName("purchasePriceField"); // NOI18N
        purchasePriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchasePriceFieldActionPerformed(evt);
            }
        });

        purchasePriceLabel.setText(resourceMap.getString("purchasePriceLabel.text")); // NOI18N
        purchasePriceLabel.setName("purchasePriceLabel"); // NOI18N

        transportPriceField.setName("transportPriceField"); // NOI18N

        transportButton.setMnemonic('T');
        transportButton.setText(resourceMap.getString("transportButton.text")); // NOI18N
        transportButton.setName("transportButton"); // NOI18N

        transportPriceLabel.setText(resourceMap.getString("transportPriceLabel.text")); // NOI18N
        transportPriceLabel.setName("transportPriceLabel"); // NOI18N

        dutyField.setName("dutyField"); // NOI18N

        dutyButton.setMnemonic('u');
        dutyButton.setText(resourceMap.getString("dutyButton.text")); // NOI18N
        dutyButton.setName("dutyButton"); // NOI18N

        dutyLabel.setText(resourceMap.getString("dutyLabel.text")); // NOI18N
        dutyLabel.setName("dutyLabel"); // NOI18N

        costPriceField.setEditable(false);
        costPriceField.setName("costPriceField"); // NOI18N
        costPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costPriceFieldActionPerformed(evt);
            }
        });

        costPriceLabel.setText(resourceMap.getString("costPriceLabel.text")); // NOI18N
        costPriceLabel.setName("costPriceLabel"); // NOI18N

        additionalProfitField.setName("additionalProfitField"); // NOI18N

        additionalProfitButton.setMnemonic('P');
        additionalProfitButton.setText(resourceMap.getString("additionalProfitButton.text")); // NOI18N
        additionalProfitButton.setName("additionalProfitButton"); // NOI18N

        additionalProfitLabel.setText(resourceMap.getString("additionalProfitLabel.text")); // NOI18N
        additionalProfitLabel.setName("additionalProfitLabel"); // NOI18N

        salesPriceField.setEditable(false);
        salesPriceField.setName("salesPriceField"); // NOI18N
        salesPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesPriceFieldActionPerformed(evt);
            }
        });

        salesPriceLabel.setText(resourceMap.getString("salesPriceLabel.text")); // NOI18N
        salesPriceLabel.setName("salesPriceLabel"); // NOI18N

        closeButton.setIcon(resourceMap.getIcon("closeButton.icon")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        categoryDiscountField.setName("categoryDiscountField"); // NOI18N

        categoryDiscountLabel.setText(resourceMap.getString("categoryDiscountLabel.text")); // NOI18N
        categoryDiscountLabel.setName("categoryDiscountLabel"); // NOI18N

        totalDiscountField.setName("totalDiscountField"); // NOI18N

        totalDiscountLabel.setText(resourceMap.getString("totalDiscountLabel.text")); // NOI18N
        totalDiscountLabel.setName("totalDiscountLabel"); // NOI18N

        categoryProfitField.setName("categoryProfitField"); // NOI18N

        categoryProfitLabel.setText(resourceMap.getString("categoryProfitLabel.text")); // NOI18N
        categoryProfitLabel.setName("categoryProfitLabel"); // NOI18N

        totalProfitField.setName("totalProfitField"); // NOI18N

        totalProfitLabel.setText(resourceMap.getString("totalProfitLabel.text")); // NOI18N
        totalProfitLabel.setName("totalProfitLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(categoryDiscountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(additionalDiscountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(listPriceLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(totalDiscountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(purchasePriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(transportPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(dutyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(costPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(categoryProfitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(additionalProfitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(totalProfitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(salesPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dutyField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(transportPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(listPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(categoryDiscountField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(additionalDiscountField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(totalDiscountField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(purchasePriceField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(costPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(categoryProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(additionalProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(totalProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(salesPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(additionalProfitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(transportButton, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(dutyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(additionalDiscountButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currencyField, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))
                    .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(listPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(categoryDiscountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(categoryDiscountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(additionalDiscountField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(additionalDiscountButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(additionalDiscountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalDiscountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalDiscountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(purchasePriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchasePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transportButton, 0, 0, Short.MAX_VALUE)
                    .addComponent(transportPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transportPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dutyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(dutyField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dutyButton, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(costPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(categoryProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(categoryProfitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(additionalProfitButton, 0, 0, Short.MAX_VALUE)
                    .addComponent(additionalProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(additionalProfitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalProfitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalProfitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salesPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void listPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listPriceFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_listPriceFieldActionPerformed

private void purchasePriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchasePriceFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_purchasePriceFieldActionPerformed

private void costPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costPriceFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_costPriceFieldActionPerformed

private void salesPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesPriceFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_salesPriceFieldActionPerformed

private void additionalDiscountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_additionalDiscountButtonActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_additionalDiscountButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton additionalDiscountButton;
    private com.cosmos.acacia.gui.AcaciaPercentValueField additionalDiscountField;
    private com.cosmos.swingb.JBLabel additionalDiscountLabel;
    private com.cosmos.swingb.JBButton additionalProfitButton;
    private com.cosmos.acacia.gui.AcaciaPercentValueField additionalProfitField;
    private com.cosmos.swingb.JBLabel additionalProfitLabel;
    private com.cosmos.acacia.gui.AcaciaPercentValueField categoryDiscountField;
    private com.cosmos.swingb.JBLabel categoryDiscountLabel;
    private com.cosmos.acacia.gui.AcaciaPercentValueField categoryProfitField;
    private com.cosmos.swingb.JBLabel categoryProfitLabel;
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.swingb.JBFormattedTextField costPriceField;
    private com.cosmos.swingb.JBLabel costPriceLabel;
    private com.cosmos.swingb.JBComboBox currencyField;
    private com.cosmos.swingb.JBButton dutyButton;
    private com.cosmos.acacia.gui.AcaciaPercentValueField dutyField;
    private com.cosmos.swingb.JBLabel dutyLabel;
    private com.cosmos.swingb.JBFormattedTextField listPriceField;
    private com.cosmos.swingb.JBLabel listPriceLabel;
    private com.cosmos.swingb.JBFormattedTextField purchasePriceField;
    private com.cosmos.swingb.JBLabel purchasePriceLabel;
    private com.cosmos.swingb.JBFormattedTextField salesPriceField;
    private com.cosmos.swingb.JBLabel salesPriceLabel;
    private com.cosmos.acacia.gui.AcaciaPercentValueField totalDiscountField;
    private com.cosmos.swingb.JBLabel totalDiscountLabel;
    private com.cosmos.acacia.gui.AcaciaPercentValueField totalProfitField;
    private com.cosmos.swingb.JBLabel totalProfitLabel;
    private com.cosmos.swingb.JBButton transportButton;
    private com.cosmos.acacia.gui.AcaciaPercentValueField transportPriceField;
    private com.cosmos.swingb.JBLabel transportPriceLabel;
    // End of variables declaration//GEN-END:variables
    public com.cosmos.swingb.JBFormattedTextField getPurchasePriceField() {
        return purchasePriceField;
    }

    public com.cosmos.swingb.JBFormattedTextField getSalesPriceField() {
        return salesPriceField;
    }

    public com.cosmos.acacia.gui.AcaciaPercentValueField getTransportPriceField() {
        return transportPriceField;
    }

    public com.cosmos.swingb.JBFormattedTextField getListPriceField() {
        return listPriceField;
    }

    public JBComboBox getCurrencyField() {
        return currencyField;
    }

    public com.cosmos.swingb.JBFormattedTextField getCostPriceField() {
        return costPriceField;
    }

    public com.cosmos.acacia.gui.AcaciaPercentValueField getAdditionalDiscountField() {
        return additionalDiscountField;
    }

    public com.cosmos.acacia.gui.AcaciaPercentValueField getDutyField() {
        return dutyField;
    }

    public com.cosmos.acacia.gui.AcaciaPercentValueField getAdditionalProfitField() {
        return additionalProfitField;
    }

    @Override
    protected void initData() {
    }

    public com.cosmos.acacia.gui.AcaciaPercentValueField getCategoryDiscountField() {
        return categoryDiscountField;
    }

    public com.cosmos.acacia.gui.AcaciaPercentValueField getTotalDiscountField() {
        return totalDiscountField;
    }

    public com.cosmos.acacia.gui.AcaciaPercentValueField getTotalProfitField() {
        return totalProfitField;
    }
    
    public AcaciaPercentValueField getCategoryProfitField(){
        return categoryProfitField;
    }
}
