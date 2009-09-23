/*
 * OrganizationChoiceForm.java
 *
 * Created on 05 July 2008, 12:10
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.gui.AcaciaPanel;
import java.util.UUID;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class LeaveOrganizationForm extends AcaciaPanel {

    /** Creates new form OrganizationChoiceForm */
    public LeaveOrganizationForm(UUID parentId) {
        super(parentId);
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

        organizationsComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        organizationLabel = new com.cosmos.swingb.JBLabel();
        proceedButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        organizationsComboBox.setName("organizationsComboBox"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(LeaveOrganizationForm.class);
        organizationLabel.setText(resourceMap.getString("organizationLabel.text")); // NOI18N
        organizationLabel.setName("organizationLabel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(LeaveOrganizationForm.class, this);
        proceedButton.setAction(actionMap.get("leave")); // NOI18N
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
    private UsersRemote formSession;
    
    @Override
    public void initData() {
        organizationsComboBox.removeAllItems();
        int idx = -1;
        organizations = getFormSession().getActiveOrganizations(null);
        for (Organization org : organizations) {
            organizationsComboBox.addItem(org);
        }
        organizationsComboBox.setSelectedIndex(idx);
    }
 
    protected UsersRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(UsersRemote.class);
        }
        return formSession;
    }
    
    
    @Action
    public void leave() {
        try {
            Organization org = (Organization) organizationsComboBox.getSelectedItem();
            if (org != null) {
                if (JOptionPane.showConfirmDialog(this,
                        getResourceMap().getString("Leave.confirm"),
                        getResourceMap().getString("Leave.confirm.title"),
                        JOptionPane.YES_NO_OPTION) ==  JOptionPane.YES_OPTION)
                {
                    getFormSession().leaveOrganization(org);
                    JOptionPane.showMessageDialog(this, getResourceMap().getString("Leave.successful"));

                    // Quit if the current organization has been abandoned
                    if (org.equals(getAcaciaSession().getOrganization()))
                        AcaciaApplication.getApplication().exit();
                    
                    close();
                }
            }
        } catch (Exception ex) {
            handleBusinessException(ex);
        }
    }

}