/*
 * ExistingProductDialog.java
 *
 * Created on Сряда, 2009, Януари 14, 19:13
 */

package com.cosmos.acacia.crm.gui.pricing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import com.cosmos.acacia.gui.AcaciaPanel;

/**
 * 
 * Created	:	14.01.2009
 * @author	Petar Milev
 *
 */
public class ExistingProductDialog extends AcaciaPanel {
    
    public enum Response{
        SKIP,
        ADDNEW,
        REPLACE
    }
    
    private Response response;

    /** Creates new form ExistingProductDialog 
     * @param newDiscountDisplay 
     * @param oldDiscountDisplay 
     * @param minQuantityDisplay 
     * @param productDisplay */
    public ExistingProductDialog(String productDisplay, String minQuantityDisplay, String oldDiscountDisplay, String newDiscountDisplay) {
        initComponents();
        initComponentsCustom();
        String msg = getMessageLabel().getText();
        msg = MessageFormat.format(msg, productDisplay, minQuantityDisplay, oldDiscountDisplay, newDiscountDisplay);
        getMessageLabel().setText(msg);
    }

    private void initComponentsCustom() {
        replaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                response = Response.REPLACE;
                close();
            }
        });
        addNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                response = Response.ADDNEW;
                close();
            }
        });
        skipProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                response = Response.SKIP;
                close();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageLabel = new com.cosmos.swingb.JBLabel();
        replaceButton = new com.cosmos.swingb.JBButton();
        addNewButton = new com.cosmos.swingb.JBButton();
        skipProductButton = new com.cosmos.swingb.JBButton();
        applyForAllCheckbox = new com.cosmos.swingb.JBCheckBox();

        setName("Form"); // NOI18N

        messageLabel.setLineWrap(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ExistingProductDialog.class);
        messageLabel.setText(resourceMap.getString("messageLabel.text")); // NOI18N
        messageLabel.setName("messageLabel"); // NOI18N

        replaceButton.setMnemonic('r');
        replaceButton.setText(resourceMap.getString("replaceButton.text")); // NOI18N
        replaceButton.setName("replaceButton"); // NOI18N

        addNewButton.setMnemonic('n');
        addNewButton.setText(resourceMap.getString("addNewButton.text")); // NOI18N
        addNewButton.setName("addNewButton"); // NOI18N

        skipProductButton.setMnemonic('s');
        skipProductButton.setText(resourceMap.getString("skipProductButton.text")); // NOI18N
        skipProductButton.setName("skipProductButton"); // NOI18N

        applyForAllCheckbox.setMnemonic('a');
        applyForAllCheckbox.setText(resourceMap.getString("applyForAllCheckbox.text")); // NOI18N
        applyForAllCheckbox.setName("applyForAllCheckbox"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(applyForAllCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(replaceButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skipProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addNewButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(replaceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skipProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addNewButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(applyForAllCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton addNewButton;
    private com.cosmos.swingb.JBCheckBox applyForAllCheckbox;
    private com.cosmos.swingb.JBLabel messageLabel;
    private com.cosmos.swingb.JBButton replaceButton;
    private com.cosmos.swingb.JBButton skipProductButton;
    // End of variables declaration//GEN-END:variables
    public com.cosmos.swingb.JBButton getAddNewButton() {
        return addNewButton;
    }

    public com.cosmos.swingb.JBCheckBox getApplyForAllCheckbox() {
        return applyForAllCheckbox;
    }

    public com.cosmos.swingb.JBLabel getMessageLabel() {
        return messageLabel;
    }

    public com.cosmos.swingb.JBButton getReplaceButton() {
        return replaceButton;
    }

    public com.cosmos.swingb.JBButton getSkipProductButton() {
        return skipProductButton;
    }

    @Override
    protected void initData() {
    }

    public Response getResponse() {
        return response;
    }

}