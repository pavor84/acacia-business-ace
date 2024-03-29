/*
 * RegistrationForm.java
 *
 * Created on 25 June 2008, 20:46
 */
package com.cosmos.acacia.crm.gui.users;



import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.gui.contactbook.AddressListPanel;
import com.cosmos.acacia.crm.gui.contactbook.ContactPersonsListPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.UUID;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class UserPanelOld extends BaseEntityPanel {

    /** Creates new form UserPenl */
    public UserPanelOld(UserOrganization uo) {
        super((UUID) null);
        this.userOrganization = uo;
        init();
    }

    @Override
    protected void init() {
        initComponents();
        super.init();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        branchComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        branchLabel = new com.cosmos.swingb.JBLabel();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        organizationLabel1 = new com.cosmos.swingb.JBLabel();
        userTextField = new com.cosmos.swingb.JBTextField();
        generalRightsHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        specialPermissionsHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        groupLabel = new com.cosmos.swingb.JBLabel();
        groupComboList = new com.cosmos.acacia.gui.AcaciaComboList();

        setName("Form"); // NOI18N

        branchComboList.setName("branchComboList"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(UserPanelOld.class);
        branchLabel.setText(resourceMap.getString("branchLabel.text")); // NOI18N
        branchLabel.setName("branchLabel"); // NOI18N

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        organizationLabel1.setText(resourceMap.getString("organizationLabel1.text")); // NOI18N
        organizationLabel1.setName("organizationLabel1"); // NOI18N

        userTextField.setText(resourceMap.getString("userTextField.text")); // NOI18N
        userTextField.setName("userTextField"); // NOI18N

        generalRightsHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("generalRightsHolderPanel.border.title"))); // NOI18N
        generalRightsHolderPanel.setName("generalRightsHolderPanel"); // NOI18N

        javax.swing.GroupLayout generalRightsHolderPanelLayout = new javax.swing.GroupLayout(generalRightsHolderPanel);
        generalRightsHolderPanel.setLayout(generalRightsHolderPanelLayout);
        generalRightsHolderPanelLayout.setHorizontalGroup(
            generalRightsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 756, Short.MAX_VALUE)
        );
        generalRightsHolderPanelLayout.setVerticalGroup(
            generalRightsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );

        specialPermissionsHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("specialPermissionsHolderPanel.border.title"))); // NOI18N
        specialPermissionsHolderPanel.setName("specialPermissionsHolderPanel"); // NOI18N

        javax.swing.GroupLayout specialPermissionsHolderPanelLayout = new javax.swing.GroupLayout(specialPermissionsHolderPanel);
        specialPermissionsHolderPanel.setLayout(specialPermissionsHolderPanelLayout);
        specialPermissionsHolderPanelLayout.setHorizontalGroup(
            specialPermissionsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 756, Short.MAX_VALUE)
        );
        specialPermissionsHolderPanelLayout.setVerticalGroup(
            specialPermissionsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );

        groupLabel.setText(resourceMap.getString("groupLabel.text")); // NOI18N
        groupLabel.setName("groupLabel"); // NOI18N

        groupComboList.setName("groupComboList"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generalRightsHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(organizationLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(branchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(branchComboList, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(groupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(groupComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                    .addComponent(specialPermissionsHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupComboList, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(branchComboList, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(userTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(organizationLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(branchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(groupLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generalRightsHolderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specialPermissionsHolderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaComboList branchComboList;
    private com.cosmos.swingb.JBLabel branchLabel;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.acacia.gui.TableHolderPanel generalRightsHolderPanel;
    private com.cosmos.acacia.gui.AcaciaComboList groupComboList;
    private com.cosmos.swingb.JBLabel groupLabel;
    private com.cosmos.swingb.JBLabel organizationLabel1;
    private com.cosmos.acacia.gui.TableHolderPanel specialPermissionsHolderPanel;
    private com.cosmos.swingb.JBTextField userTextField;
    // End of variables declaration//GEN-END:variables
    private BindingGroup bindingGroup;
    private UserOrganization userOrganization;
    private UsersRemote formSession;
    ContactPersonsListPanel personsTable;
    RightsListPanel rightsTable;
    RightsListPanel specialPermissionsTable;
    private Address branch;

    @Override
    protected void initData() {
        setResizable(false);

        // If not 'edit', close
        if (userOrganization == null) {
            close();
        }

        branch = userOrganization.getBranch();

        BindingGroup bg = getBindingGroup();

        final EntityProperties entityProps = getFormSession().getUserOrganizationEntityProperties();
        userTextField.setText(userOrganization.getUser().getUserName());

        branchComboList.setEnabled(true);
        AddressListPanel branchesTable = new AddressListPanel(userOrganization.getOrganization().getId());
        branchesTable.setVisible(Button.New, false);
        branchComboList.bind(bg, branchesTable, userOrganization, entityProps.getEntityProperty("branch"));

        /*
        groupComboList.setEnabled(true);
        UserGroupsListPanel groupsTable = new UserGroupsListPanel(userOrganization.getOrganization().getId());
        groupComboList.bind(bg, groupsTable, userOrganization, entityProps.getEntityProperty("userGroup"), "${name}");

        final boolean isGroupEmpty = userOrganization.getUserGroup() == null;

        groupComboList.getComboBox().addPropertyChangeListener(JBComboBox.SELECTED_ITEM, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (isGroupEmpty) {
                    UserGroup positionUserGroup = getFormSession().getUserGroupByPositionType();
                    if (positionUserGroup != null) {
                        if (JOptionPane.showConfirmDialog(UserPanel.this,
                                getResourceMap().getString("override.assign.group",
                                positionUserGroup.getName()),
                                getResourceMap().getString("selection.confirm"),
                                JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                            groupComboList.setSelectedItem(null);
                            userOrganization.setUserGroup(null);
                        }
                    }
                }
            }
        });
        */

        rightsTable = new RightsListPanel(userOrganization.getUser(), RightsListPanel.Type.GeneralRightsPanel);
        rightsTable.setVisibleButtons(2 + 4 + 8 + 16);
        generalRightsHolderPanel.add(rightsTable);

        specialPermissionsTable = new RightsListPanel(userOrganization.getUser(), RightsListPanel.Type.SpecialPermissionsPanel);
        specialPermissionsTable.setVisibleButtons(2 + 4 + 8 + 16);
        specialPermissionsHolderPanel.add(specialPermissionsTable);

        bg.bind();
        userTextField.setEnabled(false);
    }

    protected UsersRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = getBean(UsersRemote.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    @Override
    public BindingGroup getBindingGroup() {
        if (bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel1;
    }

    @Override
    public Object getEntity() {
        return userOrganization;
    }

    @Override
    public void performSave(boolean closeAfter) {
        userOrganization = getFormSession().saveUserOrganization(userOrganization);
        User u = userOrganization.getUser();

        rightsTable.setUser(u);
        specialPermissionsTable.setUser(u);

        if (userOrganization.getBranch() != null) {
            //u.setBranchName(userOrganization.getBranch().getAddressName());
        }

        if (!userOrganization.getBranch().equals(branch)) {
            getFormSession().changeBranch(u, branch, userOrganization.getBranch());
        }

        //u.setActive(userOrganization.isUserActive());

        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(userOrganization.getUser());
        if (closeAfter) {
            // Flushing modifications to the rights tables
            rightsTable.flushRights();
            specialPermissionsTable.flushRights();
            close();
        }
    }
}
