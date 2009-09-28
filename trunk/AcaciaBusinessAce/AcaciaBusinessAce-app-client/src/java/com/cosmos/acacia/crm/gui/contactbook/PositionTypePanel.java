package com.cosmos.acacia.crm.gui.contactbook;

import java.util.UUID;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.contactbook.PositionTypesListRemote;
import com.cosmos.acacia.crm.data.contacts.PositionType;
import com.cosmos.acacia.crm.gui.users.UserGroupsListPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class PositionTypePanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(PositionTypePanel.class);

    /** Creates new form PositionTypePanel */
    public PositionTypePanel(PositionType positionType, Class ownerClass) {
        super(positionType.getDataObject().getParentDataObjectId());
        this.positionType = positionType;
        this.ownerClass = ownerClass;
        init();
    }

    /** Creates new form PositionTypePanel */
    public PositionTypePanel(UUID parentDataObjectId, Class ownerClass) {
        super(parentDataObjectId);
        this.ownerClass = ownerClass;
        init();
    }

    @Override
    protected void init()
    {
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
        nameTextField = new com.cosmos.swingb.JBTextField();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        nameLabel = new com.cosmos.swingb.JBLabel();
        groupLabel = new com.cosmos.swingb.JBLabel();
        groupComboList = new com.cosmos.acacia.gui.AcaciaComboList();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        nameTextField.setName("nameTextField"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));
        descriptionPanel.setName("descriptionPanel"); // NOI18N

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setName("jScrollPane3"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        jScrollPane3.setViewportView(descriptionTextPane);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PositionTypePanel.class);
        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

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
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(groupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(groupComboList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupComboList, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {groupComboList, nameTextField});

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.acacia.gui.AcaciaComboList groupComboList;
    private com.cosmos.swingb.JBLabel groupLabel;
    private javax.swing.JScrollPane jScrollPane3;
    private com.cosmos.swingb.JBLabel nameLabel;
    private com.cosmos.swingb.JBTextField nameTextField;
    // End of variables declaration//GEN-END:variables

    @EJB
    private PositionTypesListRemote formSession;

    private BindingGroup bindingGroup;
    private PositionType positionType;
    private Class ownerClass;
    private boolean isInternal;

    @Override
    protected void initData() {
        setResizable(false);

        log.info("initData().positionType: " + positionType);
        if(positionType == null) {
            throw new UnsupportedOperationException("ToDo");
//            positionType = getFormSession().newPositionType(getOrganizationDataObjectId());
        }

        BindingGroup bg = getBindingGroup();

        EntityProperties entityProps = getPositionTypeEntityProperties();

        nameTextField.bind(bg, positionType, entityProps.getEntityProperty("positionTypeName"));
        descriptionTextPane.bind(bg, positionType, entityProps.getEntityProperty("description"));

        groupComboList.setEnabled(true);
        UserGroupsListPanel groupsTable = new UserGroupsListPanel(getOrganizationDataObjectId());
        groupComboList.bind(bg, groupsTable, positionType, entityProps.getEntityProperty("userGroup"), "${name}");

        bg.bind();
    }

    protected PositionTypesListRemote getFormSession() {
        if(formSession == null)
            formSession = getBean(PositionTypesListRemote.class);

        return formSession;
    }

    protected EntityProperties getPositionTypeEntityProperties()
    {
        return getFormSession().getPositionTypeEntityProperties();
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
        log.info("Save: positionType: " + positionType);
//        positionType.setInternal(isInternal);
        positionType = getFormSession().savePositionType(positionType, ownerClass);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(positionType);
        if (closeAfter)
            close();
    }

    @Override
    public Object getEntity() {
        return positionType;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

    public void setParentPosition(PositionType parent) {
//        positionType.setParentPositionType(parent);
    }
}
