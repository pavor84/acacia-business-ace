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
        categoryProfit.setPercentInclusive(true);
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

        jBLabel1 = new com.cosmos.swingb.JBLabel();
        listPriceField = new com.cosmos.swingb.JBFormattedTextField();
        currencyField = new com.cosmos.swingb.JBComboBox();
        additionalDiscountField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        additionalDiscountButton = new com.cosmos.swingb.JBButton();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        purchasePriceField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        transportPriceField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        transportButton = new com.cosmos.swingb.JBButton();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        dutyField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        dutyButton = new com.cosmos.swingb.JBButton();
        jBLabel5 = new com.cosmos.swingb.JBLabel();
        costPriceField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel6 = new com.cosmos.swingb.JBLabel();
        additionalProfitField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        additionalProfitButton = new com.cosmos.swingb.JBButton();
        jBLabel7 = new com.cosmos.swingb.JBLabel();
        salesPriceField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel8 = new com.cosmos.swingb.JBLabel();
        closeButton = new com.cosmos.swingb.JBButton();
        categoryDiscountField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        jBLabel9 = new com.cosmos.swingb.JBLabel();
        totalDiscountField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        jBLabel10 = new com.cosmos.swingb.JBLabel();
        categoryProfit = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        jBLabel11 = new com.cosmos.swingb.JBLabel();
        totalProfitField = new com.cosmos.acacia.gui.AcaciaPercentValueField();
        jBLabel12 = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductPricingPanel.class);
        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

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

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        purchasePriceField.setEditable(false);
        purchasePriceField.setName("purchasePriceField"); // NOI18N
        purchasePriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchasePriceFieldActionPerformed(evt);
            }
        });

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        transportPriceField.setName("transportPriceField"); // NOI18N

        transportButton.setMnemonic('T');
        transportButton.setText(resourceMap.getString("transportButton.text")); // NOI18N
        transportButton.setName("transportButton"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        dutyField.setName("dutyField"); // NOI18N

        dutyButton.setMnemonic('u');
        dutyButton.setText(resourceMap.getString("dutyButton.text")); // NOI18N
        dutyButton.setName("dutyButton"); // NOI18N

        jBLabel5.setText(resourceMap.getString("jBLabel5.text")); // NOI18N
        jBLabel5.setName("jBLabel5"); // NOI18N

        costPriceField.setEditable(false);
        costPriceField.setName("costPriceField"); // NOI18N
        costPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costPriceFieldActionPerformed(evt);
            }
        });

        jBLabel6.setText(resourceMap.getString("jBLabel6.text")); // NOI18N
        jBLabel6.setName("jBLabel6"); // NOI18N

        additionalProfitField.setName("additionalProfitField"); // NOI18N

        additionalProfitButton.setMnemonic('P');
        additionalProfitButton.setText(resourceMap.getString("additionalProfitButton.text")); // NOI18N
        additionalProfitButton.setName("additionalProfitButton"); // NOI18N

        jBLabel7.setText(resourceMap.getString("jBLabel7.text")); // NOI18N
        jBLabel7.setName("jBLabel7"); // NOI18N

        salesPriceField.setEditable(false);
        salesPriceField.setName("salesPriceField"); // NOI18N
        salesPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesPriceFieldActionPerformed(evt);
            }
        });

        jBLabel8.setText(resourceMap.getString("jBLabel8.text")); // NOI18N
        jBLabel8.setName("jBLabel8"); // NOI18N

        closeButton.setIcon(resourceMap.getIcon("closeButton.icon")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        categoryDiscountField.setName("categoryDiscountField"); // NOI18N

        jBLabel9.setText(resourceMap.getString("jBLabel9.text")); // NOI18N
        jBLabel9.setName("jBLabel9"); // NOI18N

        totalDiscountField.setName("totalDiscountField"); // NOI18N

        jBLabel10.setText(resourceMap.getString("jBLabel10.text")); // NOI18N
        jBLabel10.setName("jBLabel10"); // NOI18N

        categoryProfit.setName("categoryProfit"); // NOI18N

        jBLabel11.setText(resourceMap.getString("jBLabel11.text")); // NOI18N
        jBLabel11.setName("jBLabel11"); // NOI18N

        totalProfitField.setName("totalProfitField"); // NOI18N

        jBLabel12.setText(resourceMap.getString("jBLabel12.text")); // NOI18N
        jBLabel12.setName("jBLabel12"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jBLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
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
                            .addComponent(categoryProfit, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(additionalProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(totalProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(salesPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(additionalProfitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(transportButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(dutyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(additionalDiscountButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currencyField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                    .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(listPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(categoryDiscountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(additionalDiscountField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(additionalDiscountButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalDiscountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchasePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transportButton, 0, 0, Short.MAX_VALUE)
                    .addComponent(transportPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(dutyField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dutyButton, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(costPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(categoryProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(additionalProfitButton, 0, 0, Short.MAX_VALUE)
                    .addComponent(additionalProfitField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalProfitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salesPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private com.cosmos.swingb.JBButton additionalProfitButton;
    private com.cosmos.acacia.gui.AcaciaPercentValueField additionalProfitField;
    private com.cosmos.acacia.gui.AcaciaPercentValueField categoryDiscountField;
    private com.cosmos.acacia.gui.AcaciaPercentValueField categoryProfit;
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.swingb.JBFormattedTextField costPriceField;
    private com.cosmos.swingb.JBComboBox currencyField;
    private com.cosmos.swingb.JBButton dutyButton;
    private com.cosmos.acacia.gui.AcaciaPercentValueField dutyField;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel10;
    private com.cosmos.swingb.JBLabel jBLabel11;
    private com.cosmos.swingb.JBLabel jBLabel12;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBLabel jBLabel5;
    private com.cosmos.swingb.JBLabel jBLabel6;
    private com.cosmos.swingb.JBLabel jBLabel7;
    private com.cosmos.swingb.JBLabel jBLabel8;
    private com.cosmos.swingb.JBLabel jBLabel9;
    private com.cosmos.swingb.JBFormattedTextField listPriceField;
    private com.cosmos.swingb.JBFormattedTextField purchasePriceField;
    private com.cosmos.swingb.JBFormattedTextField salesPriceField;
    private com.cosmos.acacia.gui.AcaciaPercentValueField totalDiscountField;
    private com.cosmos.acacia.gui.AcaciaPercentValueField totalProfitField;
    private com.cosmos.swingb.JBButton transportButton;
    private com.cosmos.acacia.gui.AcaciaPercentValueField transportPriceField;
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
        return categoryProfit;
    }
}
