/*
 * AssemblingSchemaItemPanel.java
 *
 * Created on Събота, 2008, Юни 14, 17:59
 */
package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.DialogResponse;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.UUID;
import java.util.List;
import javax.ejb.EJB;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author  Miro
 */
public class AssemblingSchemaItemPanel
        extends BaseEntityPanel {

    @EJB
    private static AssemblingRemote formSession;
    private AssemblingSchemaItem entity;
    private EntityProperties entityProps;
    private BindingGroup bindingGroup;

    /** Creates new form AssemblingSchemaItemPanel */
    public AssemblingSchemaItemPanel() {
        super((UUID) null);
        initComponents();
    }

    public AssemblingSchemaItemPanel(AssemblingSchemaItem schemaItem) {
        super(schemaItem.getParentId());
        this.entity = schemaItem;
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

        descriptionPanel = new com.cosmos.swingb.JBPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        schemaPanel = new com.cosmos.swingb.JBPanel();
        schemaCodePanel = new com.cosmos.swingb.JBPanel();
        schemaCodeTextField = new com.cosmos.swingb.JBTextField();
        schemaCodeLabel = new com.cosmos.swingb.JBLabel();
        algorithmComboBox = new com.cosmos.swingb.JBComboBox();
        minSelectionsTextField = new com.cosmos.swingb.JBTextField();
        quantityTextField = new com.cosmos.swingb.JBTextField();
        algorithmLabel = new com.cosmos.swingb.JBLabel();
        minSelectionsLabel = new com.cosmos.swingb.JBLabel();
        quantityLabel = new com.cosmos.swingb.JBLabel();
        schemaNamePanel = new com.cosmos.swingb.JBPanel();
        schemaNameTextField = new com.cosmos.swingb.JBTextField();
        schemaNameLabel = new com.cosmos.swingb.JBLabel();
        dataTypeComboBox = new com.cosmos.swingb.JBComboBox();
        maxSelectionsTextField = new com.cosmos.swingb.JBTextField();
        defaultValueTextField = new com.cosmos.swingb.JBTextField();
        dataTypeLabel = new com.cosmos.swingb.JBLabel();
        maxSelectionsLabel = new com.cosmos.swingb.JBLabel();
        defaultValueLabel = new com.cosmos.swingb.JBLabel();
        messageComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        messageLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AssemblingSchemaItemPanel.class);
        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N
        descriptionPanel.setLayout(new java.awt.BorderLayout());

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        descriptionTextPane.setNextFocusableComponent(entityFormButtonPanel);
        descriptionScrollPane.setViewportView(descriptionTextPane);

        descriptionPanel.add(descriptionScrollPane, java.awt.BorderLayout.CENTER);

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        schemaPanel.setName("schemaPanel"); // NOI18N
        schemaPanel.setLayout(new java.awt.GridLayout(1, 0));

        schemaCodePanel.setName("schemaCodePanel"); // NOI18N

        schemaCodeTextField.setEditable(false);
        schemaCodeTextField.setName("schemaCodeTextField"); // NOI18N

        schemaCodeLabel.setText(resourceMap.getString("schemaCodeLabel.text")); // NOI18N
        schemaCodeLabel.setName("schemaCodeLabel"); // NOI18N

        algorithmComboBox.setName("algorithmComboBox"); // NOI18N
        algorithmComboBox.setNextFocusableComponent(dataTypeComboBox);

        minSelectionsTextField.setName("minSelectionsTextField"); // NOI18N
        minSelectionsTextField.setNextFocusableComponent(maxSelectionsTextField);

        quantityTextField.setName("quantityTextField"); // NOI18N
        quantityTextField.setNextFocusableComponent(defaultValueTextField);

        algorithmLabel.setText(resourceMap.getString("algorithmLabel.text")); // NOI18N
        algorithmLabel.setName("algorithmLabel"); // NOI18N

        minSelectionsLabel.setText(resourceMap.getString("minSelectionsLabel.text")); // NOI18N
        minSelectionsLabel.setName("minSelectionsLabel"); // NOI18N

        quantityLabel.setText(resourceMap.getString("quantityLabel.text")); // NOI18N
        quantityLabel.setName("quantityLabel"); // NOI18N

        javax.swing.GroupLayout schemaCodePanelLayout = new javax.swing.GroupLayout(schemaCodePanel);
        schemaCodePanel.setLayout(schemaCodePanelLayout);
        schemaCodePanelLayout.setHorizontalGroup(
            schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schemaCodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(schemaCodePanelLayout.createSequentialGroup()
                        .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(schemaCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minSelectionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(algorithmLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(algorithmComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(schemaCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addGroup(schemaCodePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(quantityTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                    .addComponent(minSelectionsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        schemaCodePanelLayout.setVerticalGroup(
            schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schemaCodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(schemaCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(schemaCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(algorithmLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(algorithmComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minSelectionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minSelectionsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schemaCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        schemaPanel.add(schemaCodePanel);

        schemaNamePanel.setName("schemaNamePanel"); // NOI18N

        schemaNameTextField.setEditable(false);
        schemaNameTextField.setName("schemaNameTextField"); // NOI18N

        schemaNameLabel.setText(resourceMap.getString("schemaNameLabel.text")); // NOI18N
        schemaNameLabel.setName("schemaNameLabel"); // NOI18N

        dataTypeComboBox.setName("dataTypeComboBox"); // NOI18N
        dataTypeComboBox.setNextFocusableComponent(minSelectionsTextField);

        maxSelectionsTextField.setName("maxSelectionsTextField"); // NOI18N
        maxSelectionsTextField.setNextFocusableComponent(quantityTextField);

        defaultValueTextField.setName("defaultValueTextField"); // NOI18N
        defaultValueTextField.setNextFocusableComponent(descriptionTextPane);

        dataTypeLabel.setText(resourceMap.getString("dataTypeLabel.text")); // NOI18N
        dataTypeLabel.setName("dataTypeLabel"); // NOI18N

        maxSelectionsLabel.setText(resourceMap.getString("maxSelectionsLabel.text")); // NOI18N
        maxSelectionsLabel.setName("maxSelectionsLabel"); // NOI18N

        defaultValueLabel.setText(resourceMap.getString("defaultValueLabel.text")); // NOI18N
        defaultValueLabel.setName("defaultValueLabel"); // NOI18N

        javax.swing.GroupLayout schemaNamePanelLayout = new javax.swing.GroupLayout(schemaNamePanel);
        schemaNamePanel.setLayout(schemaNamePanelLayout);
        schemaNamePanelLayout.setHorizontalGroup(
            schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schemaNamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(schemaNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxSelectionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(defaultValueTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(maxSelectionsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(dataTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(schemaNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                .addContainerGap())
        );
        schemaNamePanelLayout.setVerticalGroup(
            schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schemaNamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(schemaNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(schemaNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxSelectionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxSelectionsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schemaNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(defaultValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        schemaPanel.add(schemaNamePanel);

        messageComboList.setName("messageComboList"); // NOI18N

        messageLabel.setText(resourceMap.getString("messageLabel.text")); // NOI18N
        messageLabel.setName("messageLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE))
            .addComponent(schemaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(messageComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(schemaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messageComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBComboBox algorithmComboBox;
    private com.cosmos.swingb.JBLabel algorithmLabel;
    private com.cosmos.swingb.JBComboBox dataTypeComboBox;
    private com.cosmos.swingb.JBLabel dataTypeLabel;
    private com.cosmos.swingb.JBLabel defaultValueLabel;
    private com.cosmos.swingb.JBTextField defaultValueTextField;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBLabel maxSelectionsLabel;
    private com.cosmos.swingb.JBTextField maxSelectionsTextField;
    private com.cosmos.acacia.gui.AcaciaComboList messageComboList;
    private com.cosmos.swingb.JBLabel messageLabel;
    private com.cosmos.swingb.JBLabel minSelectionsLabel;
    private com.cosmos.swingb.JBTextField minSelectionsTextField;
    private com.cosmos.swingb.JBLabel quantityLabel;
    private com.cosmos.swingb.JBTextField quantityTextField;
    private com.cosmos.swingb.JBLabel schemaCodeLabel;
    private com.cosmos.swingb.JBPanel schemaCodePanel;
    private com.cosmos.swingb.JBTextField schemaCodeTextField;
    private com.cosmos.swingb.JBLabel schemaNameLabel;
    private com.cosmos.swingb.JBPanel schemaNamePanel;
    private com.cosmos.swingb.JBTextField schemaNameTextField;
    private com.cosmos.swingb.JBPanel schemaPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void initData() {
        entityProps = getFormSession().getAssemblingSchemaItemEntityProperties();
        EntityProperty propDetails;

        BindingGroup bg = getBindingGroup();

        AssemblingSchema as = entity.getAssemblingSchema();
        //schemaCodeTextField
        schemaCodeTextField.setText(as.getSchemaCode());

        //schemaNameTextField
        schemaNameTextField.setText(as.getSchemaName());

        //messageComboList
        propDetails = entityProps.getEntityProperty("assemblingMessage");
        AssemblingMessageListPanel listPanel =
                new AssemblingMessageListPanel();
        messageComboList.bind(
                bg,
                listPanel,
                entity,
                propDetails,
                "${messageCode} (${selectionText}/${inputText})",
                UpdateStrategy.READ_WRITE);
        messageComboList.addItemListener(new MessageItemListener());

        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(algorithmComboBox, resourceToStringConverter);
        AutoCompleteDecorator.decorate(dataTypeComboBox, resourceToStringConverter);

        //algorithmComboBox
        propDetails = entityProps.getEntityProperty("assemblingAlgorithm");
        algorithmComboBox.bind(bg, getAlgorithms(), entity, propDetails);

        //dataTypeComboBox
        propDetails = entityProps.getEntityProperty("dataType");
        dataTypeComboBox.bind(bg, getDataTypes(), entity, propDetails);

        //minSelectionsTextField
        propDetails = entityProps.getEntityProperty("minSelections");
        minSelectionsTextField.bind(bg, entity, propDetails);

        //maxSelectionsTextField
        propDetails = entityProps.getEntityProperty("maxSelections");
        maxSelectionsTextField.bind(bg, entity, propDetails);

        //quantityTextField
        propDetails = entityProps.getEntityProperty("quantity");
        quantityTextField.bind(bg, entity, propDetails);

        //defaultValueTextField
        propDetails = entityProps.getEntityProperty("defaultValue");
        defaultValueTextField.bind(bg, entity, propDetails);

        //descriptionTextPane
        propDetails = entityProps.getEntityProperty("description");
        descriptionTextPane.bind(bg, entity, propDetails);

        bg.bind();

        algorithmChanged();

        algorithmComboBox.addItemListener(new AlgorithmItemListener(), true);
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public void performSave(boolean closeAfter) {
        entity = getFormSession().saveSchemaItem(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter) {
            close();
        }
    }

    private List<DbResource> getAlgorithms() {
        return getFormSession().getAlgorithms();
    }

    private List<DbResource> getDataTypes() {
        return getFormSession().getDataTypes();
    }

    protected AssemblingRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(AssemblingRemote.class);
        }

        return formSession;
    }

    protected Algorithm.Type getAlgorithmType() {
        DbResource resource;
        if ((resource = (DbResource) algorithmComboBox.getSelectedItem()) == null) {
            return null;
        }

        return (Algorithm.Type) resource.getEnumValue();
    }

    private class MessageItemListener
            implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() > 0x700) {
                //refreshDataTable(entityProps);
                //setEnabled(AbstractTablePanel.Button.New, canCreate());
            }
        }
    }

    private void algorithmChanged() {
        Algorithm.Type algorithmType;
        if ((algorithmType = getAlgorithmType()) == null) {
            return;
        }

        if (Algorithm.Type.SingleSelectionAlgorithms.contains(algorithmType) ||
                Algorithm.Type.UnconditionalSelection.equals(algorithmType)) {
            minSelectionsTextField.setEnabled(false);
            maxSelectionsTextField.setEnabled(false);
        } else {
            minSelectionsTextField.setEnabled(true);
            maxSelectionsTextField.setEnabled(true);
        }

        if (Algorithm.Type.UnconditionalSelection.equals(algorithmType)) {
            messageComboList.setEnabled(false);
            defaultValueTextField.setEnabled(false);
        } else {
            messageComboList.setEnabled(true);
            defaultValueTextField.setEnabled(true);
        }
    }

    private class AlgorithmItemListener
            implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            algorithmChanged();
        }
    }
}
