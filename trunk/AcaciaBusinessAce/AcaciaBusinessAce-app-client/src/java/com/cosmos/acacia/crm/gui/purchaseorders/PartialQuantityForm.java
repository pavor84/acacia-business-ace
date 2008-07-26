/*
 * ParitalQuantityForm.java
 *
 * Created on Сряда, 2008, Юли 16, 14:12
 */

package com.cosmos.acacia.crm.gui.purchaseorders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.beansbinding.validation.NumericRangeValidator;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	24.07.2008
 * @author	Petar Milev
 *
 */
public class PartialQuantityForm extends AcaciaPanel {

    private BigDecimal maxQuantityToMatch;
    
    private BigDecimal quantity;
    
    /** Creates new form ParitalQuantityForm 
     * @param maxQuantityToMatch */
    public PartialQuantityForm(BigInteger parentId, BigDecimal maxQuantityToMatch) {
        super(parentId);
        this.maxQuantityToMatch = maxQuantityToMatch;
        initComponents();
        initData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        quantityField = new com.cosmos.swingb.JBTextField();
        confirmLabel = new com.cosmos.swingb.JBLabel();
        cancelButton = new com.cosmos.swingb.JBButton();
        confirmButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PartialQuantityForm.class);
        quantityField.setText(resourceMap.getString("quantityField.text")); // NOI18N
        quantityField.setName("quantityField"); // NOI18N

        confirmLabel.setText(resourceMap.getString("confirmLabel.text")); // NOI18N
        confirmLabel.setName("confirmLabel"); // NOI18N

        cancelButton.setIcon(resourceMap.getIcon("cancelButton.icon")); // NOI18N
        cancelButton.setMnemonic('L');
        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        confirmButton.setIcon(resourceMap.getIcon("confirmButton.icon")); // NOI18N
        confirmButton.setMnemonic('C');
        confirmButton.setText(resourceMap.getString("confirmButton.text")); // NOI18N
        confirmButton.setName("confirmButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(confirmLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton cancelButton;
    private com.cosmos.swingb.JBButton confirmButton;
    private com.cosmos.swingb.JBLabel confirmLabel;
    private com.cosmos.swingb.JBTextField quantityField;
    // End of variables declaration//GEN-END:variables
    @Override
    protected void initData() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConfirm();
            }
        });
        confirmButton.setEnabled(false);
        
        confirmLabel.setText(getResourceMap().getString("confirmLabel.text") +
            " (0 - " + maxQuantityToMatch.toString()+"):");
        
        BindingGroup group = new BindingGroup();
        PropertyDetails pd = new PropertyDetails("quantity", "Quantity", BigDecimal.class.getName());
        pd.setReadOnly(false);
        pd.setEditable(true);
        pd.setVisible(true);
        pd.setHiden(false);
        pd.setRequired(true);
        NumericRangeValidator validator = new NumericRangeValidator();
        validator.setFloating(true);
        validator.setMinValue(0);
        validator.setMaxValue(maxQuantityToMatch.doubleValue());
        validator.setRequired(true);
        pd.setValidator(validator);
        quantityField.bind(group, this, pd, UpdateStrategy.READ_WRITE);
        
        group.bind();
        
        group.addBindingListener(new AbstractBindingListener() {
        
            @SuppressWarnings("unchecked")
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                confirmButton.setEnabled(binding.isContentValid());
            }
        });
        
    }

    protected void onConfirm() {
        setDialogResponse(DialogResponse.SAVE);
        close();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

}
