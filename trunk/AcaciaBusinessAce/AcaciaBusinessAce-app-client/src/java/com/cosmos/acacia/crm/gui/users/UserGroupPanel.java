package com.cosmos.acacia.crm.gui.users;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.data.users.UserGroup;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.UUID;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class UserGroupPanel extends BaseEntityPanel {

    @SuppressWarnings("hiding")
    private Logger log = Logger.getLogger(UserGroupPanel.class);

    /** Creates new form ContactPersonPanel */
    public UserGroupPanel(UserGroup userGroup) {
        super(userGroup.getDataObject().getParentDataObjectId());
        this.userGroup = userGroup;
        init();
    }

    /** Creates new form ContactPersonPanel */
    public UserGroupPanel(UUID parentDataObjectId) {
        super(parentDataObjectId);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        groupName = new com.cosmos.swingb.JBLabel();
        nameTextField = new com.cosmos.swingb.JBTextField();
        generalRightsHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        specialPermissionsHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));
        descriptionPanel.setName("descriptionPanel"); // NOI18N

        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane6.setName("jScrollPane6"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        jScrollPane6.setViewportView(descriptionTextPane);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(UserGroupPanel.class);
        groupName.setText(resourceMap.getString("groupName.text")); // NOI18N
        groupName.setName("groupName"); // NOI18N

        nameTextField.setName("nameTextField"); // NOI18N

        generalRightsHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("generalRightsHolderPanel.border.title"))); // NOI18N
        generalRightsHolderPanel.setName("generalRightsHolderPanel"); // NOI18N

        javax.swing.GroupLayout generalRightsHolderPanelLayout = new javax.swing.GroupLayout(generalRightsHolderPanel);
        generalRightsHolderPanel.setLayout(generalRightsHolderPanelLayout);
        generalRightsHolderPanelLayout.setHorizontalGroup(
            generalRightsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 904, Short.MAX_VALUE)
        );
        generalRightsHolderPanelLayout.setVerticalGroup(
            generalRightsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );

        specialPermissionsHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("specialPermissionsHolderPanel.border.title"))); // NOI18N
        specialPermissionsHolderPanel.setName("specialPermissionsHolderPanel"); // NOI18N

        javax.swing.GroupLayout specialPermissionsHolderPanelLayout = new javax.swing.GroupLayout(specialPermissionsHolderPanel);
        specialPermissionsHolderPanel.setLayout(specialPermissionsHolderPanelLayout);
        specialPermissionsHolderPanelLayout.setHorizontalGroup(
            specialPermissionsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 904, Short.MAX_VALUE)
        );
        specialPermissionsHolderPanelLayout.setVerticalGroup(
            specialPermissionsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 174, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(generalRightsHolderPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(specialPermissionsHolderPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(groupName, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generalRightsHolderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specialPermissionsHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.acacia.gui.TableHolderPanel generalRightsHolderPanel;
    private com.cosmos.swingb.JBLabel groupName;
    private javax.swing.JScrollPane jScrollPane6;
    private com.cosmos.swingb.JBTextField nameTextField;
    private com.cosmos.acacia.gui.TableHolderPanel specialPermissionsHolderPanel;
    // End of variables declaration//GEN-END:variables
    @EJB
    private UserRightsRemote formSession;
    private EntityProperties entityProps;
    private BindingGroup bindingGroup;
    private UserGroup userGroup;
    private RightsListPanel rightsTable;
    private RightsListPanel specialPermissionsTable;

    @Override
    protected void initData() {
        setResizable(false);

        log.info("initData().userGroup: " + userGroup);

        if (userGroup == null) {
            userGroup = getFormSession().newUserGroup();
        }

        BindingGroup bg = getBindingGroup();

        entityProps = getUserGroupEntityProperties();

        nameTextField.bind(bg, userGroup, entityProps.getPropertyDetails("name"));
        descriptionTextPane.bind(bg, userGroup, entityProps.getPropertyDetails("description"));

        rightsTable = new RightsListPanel(userGroup, RightsListPanel.Type.GeneralRightsPanel);
        rightsTable.setVisibleButtons(2 + 4 + 8 + 16);
        generalRightsHolderPanel.add(rightsTable);
        addNestedFormListener(rightsTable);


        specialPermissionsTable = new RightsListPanel(userGroup, RightsListPanel.Type.SpecialPermissionsPanel);
        specialPermissionsTable.setVisibleButtons(2 + 4 + 8 + 16);
        specialPermissionsHolderPanel.add(specialPermissionsTable);
        addNestedFormListener(specialPermissionsTable);

        bg.bind();
    }

    protected UserRightsRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(UserRightsRemote.class);
        }

        return formSession;
    }

    protected EntityProperties getUserGroupEntityProperties() {
        return getFormSession().getUserGroupEntityProperties();
    }

    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: userGroup: " + userGroup);
        userGroup = getFormSession().saveUserGroup(userGroup);
        rightsTable.setUserGroup(userGroup);
        specialPermissionsTable.setUserGroup(userGroup);

        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(userGroup);
        if (closeAfter) {
            // Flushing modifications to the rights tables
            rightsTable.flushRights();
            specialPermissionsTable.flushRights();
            close();
        }
    }

    @Override
    public Object getEntity() {
        return userGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }
}
