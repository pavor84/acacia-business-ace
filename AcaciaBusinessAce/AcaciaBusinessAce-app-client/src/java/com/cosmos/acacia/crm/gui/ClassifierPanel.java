package com.cosmos.acacia.crm.gui;

import java.awt.Component;
import javax.ejb.EJB;
import javax.naming.InitialContext;

import javax.swing.JList;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class ClassifierPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(ClassifierPanel.class);

    /** Creates new form ContactPersonPanel */
    public ClassifierPanel(Classifier classifier) {
        super(classifier.getDataObject().getParentDataObjectId());
        this.classifier = classifier;
        init();
    }

    /** Creates new form ContactPersonPanel */
    public ClassifierPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        init();
    }

    /** Creates new form ContactPersonPanel,
     * indicating that the form has been opened while classifying an object
     */
    public ClassifierPanel() {
        super(null);
        init();
        addObjectTypeButton.setEnabled(false);
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
        codeTextField = new com.cosmos.swingb.JBTextField();
        nameTextField = new com.cosmos.swingb.JBTextField();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        codeLabel = new com.cosmos.swingb.JBLabel();
        nameLabel = new com.cosmos.swingb.JBLabel();
        groupComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        groupLabel = new com.cosmos.swingb.JBLabel();
        dataObjectTypesPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        dataObjectTypeComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        addObjectTypeButton = new com.cosmos.swingb.JBButton();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        codeTextField.setName("codeTextField"); // NOI18N

        nameTextField.setName("nameTextField"); // NOI18N
        nameTextField.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                nameTextFieldAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

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
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ClassifierPanel.class);
        codeLabel.setText(resourceMap.getString("codeLabel.text")); // NOI18N
        codeLabel.setName("codeLabel"); // NOI18N

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        groupComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        groupComboBox.setName("groupComboBox"); // NOI18N

        groupLabel.setText(resourceMap.getString("groupLabel.text")); // NOI18N
        groupLabel.setName("groupLabel"); // NOI18N

        dataObjectTypesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("dataObjectTypesPanel.border.title"))); // NOI18N
        dataObjectTypesPanel.setName("dataObjectTypesPanel"); // NOI18N

        javax.swing.GroupLayout dataObjectTypesPanelLayout = new javax.swing.GroupLayout(dataObjectTypesPanel);
        dataObjectTypesPanel.setLayout(dataObjectTypesPanelLayout);
        dataObjectTypesPanelLayout.setHorizontalGroup(
            dataObjectTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );
        dataObjectTypesPanelLayout.setVerticalGroup(
            dataObjectTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
        );

        dataObjectTypeComboBox.setName("dataObjectTypeComboBox"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(ClassifierPanel.class, this);
        addObjectTypeButton.setAction(actionMap.get("addConstraintAction")); // NOI18N
        addObjectTypeButton.setName("addObjectTypeButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(codeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(groupLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(groupComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))))
                    .addComponent(dataObjectTypesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(dataObjectTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addObjectTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dataObjectTypesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addObjectTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataObjectTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nameTextFieldAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_nameTextFieldAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton addObjectTypeButton;
    private com.cosmos.swingb.JBLabel codeLabel;
    private com.cosmos.swingb.JBTextField codeTextField;
    private com.cosmos.acacia.gui.AcaciaComboBox dataObjectTypeComboBox;
    private com.cosmos.acacia.gui.TableHolderPanel dataObjectTypesPanel;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox groupComboBox;
    private com.cosmos.swingb.JBLabel groupLabel;
    private javax.swing.JScrollPane jScrollPane6;
    private com.cosmos.swingb.JBLabel nameLabel;
    private com.cosmos.swingb.JBTextField nameTextField;
    // End of variables declaration//GEN-END:variables

    @EJB
    private ClassifiersRemote formSession;

    private EntityProperties entityProps;

    private BindingGroup classifierBindingGroup;
    private Classifier classifier;
    private DataObjectTypesListPanel dataObjectTypesTable;

    @Override
    protected void initData() {
        setResizable(false);

        log.info("initData().classifier: " + classifier);
        if(classifier == null)
        {
            classifier = getFormSession().newClassifier();
        }

        if (classifierBindingGroup == null)
            classifierBindingGroup = new BindingGroup();

        entityProps = getClassifierEntityProperties();

        codeTextField.bind(classifierBindingGroup, classifier, entityProps.getPropertyDetails("classifierCode"));
        nameTextField.bind(classifierBindingGroup, classifier, entityProps.getPropertyDetails("classifierName"));
        groupComboBox.bind(classifierBindingGroup, getClassifierGroups(), classifier, entityProps.getPropertyDetails("classifierGroup"));
        descriptionTextPane.bind(classifierBindingGroup, classifier, entityProps.getPropertyDetails("description"));

        dataObjectTypesTable = new DataObjectTypesListPanel(null, classifier);
        //dataObjectTypesTable.setVisibleButtons(8 + 16);
        dataObjectTypesTable.setVisible(AbstractTablePanel.Button.DeleteRefresh);
        dataObjectTypesPanel.add(dataObjectTypesTable);
        addNestedFormListener(dataObjectTypesTable);

        populateComboBox(dataObjectTypeComboBox, getDataObjectTypes());

        classifierBindingGroup.bind();

//        // Bypassing a strange bug
//        for (BindingListener l : classifierBindingGroup.getBindingListeners()) {
//            log.info("VALID: " + getBindingGroup().isContentValid());
//            l.targetChanged(null, null);
//        }
    }


    protected List<ClassifierGroup> getClassifierGroups() {
        return getFormSession().getClassifierGroups();
    }

    protected List<DataObjectType> getDataObjectTypes() {
        return dataObjectTypesTable.shortenDataObjectTypeNames(getFormSession().getDataObjectTypes());
    }


    public void populateComboBox(JComboBox combo, List<DataObjectType> data) {

        if (combo.getItemCount() == 0) {
            AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter("${dataObjectType}");
            AutoCompleteDecorator.decorate(combo, resourceToStringConverter);

            combo.setModel(new DefaultComboBoxModel(data.toArray()));

            combo.setRenderer(new DataObjectTypeCellRenderer());
        }
    }

    protected ClassifiersRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(ClassifiersRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected EntityProperties getClassifierEntityProperties()
    {
        return getFormSession().getClassifierEntityProperties();
    }


    @Override
    public BindingGroup getBindingGroup() {
        return classifierBindingGroup;
    }

    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: classifier: " + classifier);
        classifier = getFormSession().saveClassifier(
                classifier,
                classifier.getClassifierGroup().getDataObject().getDataObjectId());

        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(classifier);
        if (closeAfter) {
            close();
        } else {
            dataObjectTypesPanel.remove(dataObjectTypesTable);
            classifierBindingGroup.unbind();
            initData();
        }
    }

    @Override
    public Object getEntity() {
        return classifier;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Action
    public void addConstraintAction() {
        if (dataObjectTypesTable.canNestedOperationProceed()) {
            getFormSession().addDataObjectTypeConstraint(classifier, (DataObjectType) dataObjectTypeComboBox.getSelectedItem());
            dataObjectTypesTable.refreshAction();
        }
    }
}

class DataObjectTypeCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        return super.getListCellRendererComponent(list, ((DataObjectType) value).getDataObjectType(),
                index, isSelected, cellHasFocus);
    }
}
