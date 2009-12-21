/*
 * MatchAmountForm.java
 *
 * Created on Сряда, 2009, Февруари 25, 17:41
 */

package com.cosmos.acacia.crm.gui.payment;

import com.cosmos.acacia.entity.AcaciaEntityAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.MessageFormat;

import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;

import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.beansbinding.validation.NumericRangeValidator;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	24.03.2009
 * @author	Petar Milev
 *
 */
public class MatchAmountForm extends AcaciaPanel {
    
    private BigDecimal defaultAmount;
    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    /** Creates new form MatchAmountForm */
    public MatchAmountForm(BigDecimal minAmount, BigDecimal maxAmount, BigDecimal defaultAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.defaultAmount = defaultAmount;
        initComponents();
        initData();
        afterBind();
    }

    private void afterBind() {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });
    }

    protected void onOk() {
        setDialogResponse(DialogResponse.SAVE);
        close();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        matchAmountField = new com.cosmos.swingb.JBFormattedTextField();
        matchAmountLabel = new com.cosmos.swingb.JBLabel();
        okButton = new com.cosmos.swingb.JBButton();
        cancelButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(MatchAmountForm.class);
        matchAmountField.setText(resourceMap.getString("matchAmountField.text")); // NOI18N
        matchAmountField.setName("matchAmountField"); // NOI18N

        matchAmountLabel.setText(resourceMap.getString("matchAmountLabel.text")); // NOI18N
        matchAmountLabel.setName("matchAmountLabel"); // NOI18N

        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N

        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(matchAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(124, 124, 124)
                        .addComponent(matchAmountField, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matchAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matchAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton cancelButton;
    private com.cosmos.swingb.JBFormattedTextField matchAmountField;
    private com.cosmos.swingb.JBLabel matchAmountLabel;
    private com.cosmos.swingb.JBButton okButton;
    // End of variables declaration//GEN-END:variables
    
    private BindingGroup bindingGroup = new BindingGroup();
    private BigDecimal matchAmount;
    
    @Override
    protected void initData() {
        matchAmount = defaultAmount;
        
        String minStr = AcaciaUtils.getDecimalFormat().format(minAmount);
        String maxStr = AcaciaUtils.getDecimalFormat().format(maxAmount);
        String amountLabel = MessageFormat.format(getResourceMap().getString("matchAmountLabel.text"),
            minStr, maxStr);
        matchAmountLabel.setText(amountLabel);
        
        EntityProperty pd = EntityProperty.createEntityProperty("matchAmount", "", BigDecimal.class.getName(), AcaciaEntityAttributes.getEntityAttributesMap());
        pd.setRequired(true);
        NumericRangeValidator val = new NumericRangeValidator();
        val.setRequired(true);
        val.setMinValue(minAmount.doubleValue());
        val.setMaxValue(maxAmount.doubleValue());
        pd.setValidator(val);
        
        Binding binding = matchAmountField.bind(bindingGroup, this, pd, AcaciaUtils.getDecimalFormat());
        binding.addBindingListener(new AbstractBindingListener() {
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                okButton.setEnabled(binding.isContentValid());
            }
        });
        
        bindingGroup.bind();
    }

    public BigDecimal getMatchAmount() {
        return matchAmount;
    }

    public void setMatchAmount(BigDecimal matchAmount) {
        this.matchAmount = matchAmount;
    }
}
