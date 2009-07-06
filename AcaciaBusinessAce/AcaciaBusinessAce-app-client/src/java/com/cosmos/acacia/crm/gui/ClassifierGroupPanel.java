package com.cosmos.acacia.crm.gui;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class ClassifierGroupPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(ClassifierGroupPanel.class);

    /** Creates new form ContactPersonPanel */
    public ClassifierGroupPanel(ClassifierGroup classifierGroup) {
        super(classifierGroup.getDataObject().getParentDataObjectId());
        this.classifierGroup = classifierGroup;
        init();
    }

    /** Creates new form ContactPersonPanel */
    public ClassifierGroupPanel(BigInteger parentDataObjectId) {
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
        codeTextField = new com.cosmos.swingb.JBTextField();
        nameTextField = new com.cosmos.swingb.JBTextField();
        codeLabel = new com.cosmos.swingb.JBLabel();
        nameLabel = new com.cosmos.swingb.JBLabel();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        systemClassifierGroupCheckBox = new com.cosmos.swingb.JBCheckBox();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        codeTextField.setName("codeTextField"); // NOI18N

        nameTextField.setName("nameTextField"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ClassifierGroupPanel.class);
        codeLabel.setText(resourceMap.getString("codeLabel.text")); // NOI18N
        codeLabel.setName("codeLabel"); // NOI18N

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));
        descriptionPanel.setName("descriptionPanel"); // NOI18N

        jScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setName("jScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        jScrollPane.setViewportView(descriptionTextPane);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        systemClassifierGroupCheckBox.setText(resourceMap.getString("systemClassifierGroupCheckBox.text")); // NOI18N
        systemClassifierGroupCheckBox.setName("systemClassifierGroupCheckBox"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(codeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(codeTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(systemClassifierGroupCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {codeTextField, nameTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(systemClassifierGroupCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel codeLabel;
    private com.cosmos.swingb.JBTextField codeTextField;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private javax.swing.JScrollPane jScrollPane;
    private com.cosmos.swingb.JBLabel nameLabel;
    private com.cosmos.swingb.JBTextField nameTextField;
    private com.cosmos.swingb.JBCheckBox systemClassifierGroupCheckBox;
    // End of variables declaration//GEN-END:variables
    private EntityProperties entityProps;
    private BindingGroup bindingGroup;
    private ClassifierGroup classifierGroup;

    @Override
    protected void initData() {
        setResizable(false);

        boolean isNew;

        log.info("initData().classifierGroup: " + classifierGroup);
        if (classifierGroup == null) {
            classifierGroup = getClassifiersManager().newClassifierGroup();
            isNew = true;
        } else {
            isNew = false;
        }

        BindingGroup bg = getBindingGroup();

        entityProps = getClassifierGroupEntityProperties();

        codeTextField.bind(bg, classifierGroup, entityProps.getPropertyDetails("classifierGroupCode"));
        nameTextField.bind(bg, classifierGroup, entityProps.getPropertyDetails("classifierGroupName"));
        descriptionTextPane.bind(bg, classifierGroup, entityProps.getPropertyDetails("description"));
        systemClassifierGroupCheckBox.bind(bg, classifierGroup, entityProps.getPropertyDetails("isSystemGroup"));

        bg.bind();

        if (isNew) {
            if (!isAdministrator()) {
                systemClassifierGroupCheckBox.setEnabled(false);
            }
        } else {
            systemClassifierGroupCheckBox.setEnabled(false);
            if (classifierGroup.getIsSystemGroup()) {
                codeTextField.setEnabled(false);
            }
        }
    }

    protected EntityProperties getClassifierGroupEntityProperties() {
        return getClassifiersManager().getClassifierGroupEntityProperties();
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
        log.info("Save: classifierGroup: " + classifierGroup);
        classifierGroup = getClassifiersManager().saveClassifierGroup(classifierGroup);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(classifierGroup);
        if (closeAfter) {
            close();
        }
    }

    @Override
    public Object getEntity() {
        return classifierGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }
}
