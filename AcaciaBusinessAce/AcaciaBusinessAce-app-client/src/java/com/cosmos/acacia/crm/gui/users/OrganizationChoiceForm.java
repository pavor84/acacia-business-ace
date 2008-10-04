/*
 * OrganizationChoiceForm.java
 *
 * Created on 05 July 2008, 12:10
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import java.util.List;
import org.jdesktop.application.Action;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class OrganizationChoiceForm extends AcaciaPanel {

    /** Creates new form OrganizationChoiceForm */
    public OrganizationChoiceForm(List<Organization> organizations) {
        super((BigInteger) null);
        this.organizations = organizations;
        initComponents();
        if (organizations != null)
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

        organizationsComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        organizationLabel = new com.cosmos.swingb.JBLabel();
        proceedButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        organizationsComboBox.setName("organizationsComboBox"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(OrganizationChoiceForm.class);
        organizationLabel.setText(resourceMap.getString("organizationLabel.text")); // NOI18N
        organizationLabel.setName("organizationLabel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(OrganizationChoiceForm.class, this);
        proceedButton.setAction(actionMap.get("proceed")); // NOI18N
        proceedButton.setName("proceedButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(organizationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(organizationsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(organizationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(organizationsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel organizationLabel;
    private com.cosmos.acacia.gui.AcaciaComboBox organizationsComboBox;
    private com.cosmos.swingb.JBButton proceedButton;
    // End of variables declaration//GEN-END:variables

    private List<Organization> organizations;
    private String defaultOrganization;
    
    @Override
    public void initData() {
        organizationsComboBox.removeAllItems();
        int idx = -1;
        for (Organization org : organizations) {
            if (org.getOrganizationName().equals(defaultOrganization))
                idx = organizationsComboBox.getItemCount();
            
            organizationsComboBox.addItem(org);
        }
        organizationsComboBox.setSelectedIndex(idx);
    }
            
    public void init(List<Organization> data) {
        requestFocus();
        this.organizations = data;
        initData();
    }
            
    @Action
    public void proceed() {
        setDialogResponse(DialogResponse.SELECT);
        setSelectedValue(organizationsComboBox.getSelectedItem());
        close();
    }
    
    public void setDefaultOrganizationString(String defOrgStr) {
        this.defaultOrganization = defOrgStr;
    }

}