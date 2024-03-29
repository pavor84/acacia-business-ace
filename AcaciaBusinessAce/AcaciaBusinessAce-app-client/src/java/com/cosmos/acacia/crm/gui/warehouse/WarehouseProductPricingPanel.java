/*
 * WarehouseProductPricingPanel.java
 *
 * Created on Петък, 2008, Юни 13, 18:16
 */

package com.cosmos.acacia.crm.gui.warehouse;

import org.jdesktop.application.Action;

import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.JBFormattedTextField;
import com.cosmos.swingb.JBPanel;

/**
 *
 * @author  jchan
 */
public class WarehouseProductPricingPanel extends JBPanel {

    /** Creates new form WarehouseProductPricingPanel */
    public WarehouseProductPricingPanel() {
        initComponents();
        customInitComponents();
    }

    protected void customInitComponents() {
        this.setResizable(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBPanel1 = new com.cosmos.swingb.JBPanel();
        jBLabel1 = new com.cosmos.swingb.JBLabel();
        jBTextField1 = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jBTextField2 = new com.cosmos.swingb.JBFormattedTextField();
        jBButton1 = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(WarehouseProductPricingPanel.class);
        jBPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jBPanel1.border.title"))); // NOI18N
        jBPanel1.setName("jBPanel1"); // NOI18N

        jBLabel1.setText(resourceMap.getString("Purchase Price.text")); // NOI18N
        jBLabel1.setName("Purchase Price"); // NOI18N

        jBTextField1.setText(resourceMap.getString("jBTextField1.text")); // NOI18N
        jBTextField1.setName("jBTextField1"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jBTextField2.setText(resourceMap.getString("jBTextField2.text")); // NOI18N
        jBTextField2.setName("jBTextField2"); // NOI18N

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jBTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(WarehouseProductPricingPanel.class, this);
        jBButton1.setAction(actionMap.get("closeAction")); // NOI18N
        jBButton1.setName("jBButton1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void closeAction() {
        this.close();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton jBButton1;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private com.cosmos.swingb.JBFormattedTextField jBTextField1;
    private com.cosmos.swingb.JBFormattedTextField jBTextField2;
    // End of variables declaration//GEN-END:variables
    public JBFormattedTextField getPurchasePriceField() {
        return jBTextField1;
    }

    public JBFormattedTextField getSalePriceField() {
        return jBTextField2;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class getResourceStartClass() {
        return WarehouseProductPricingPanel.class;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected Class getResourceStopClass() {
        return AcaciaPanel.class;
    }

    public void setReadonlyMode(boolean readonly) {
        jBTextField1.setEditable(!readonly);
        jBTextField2.setEditable(!readonly);
    }
}
