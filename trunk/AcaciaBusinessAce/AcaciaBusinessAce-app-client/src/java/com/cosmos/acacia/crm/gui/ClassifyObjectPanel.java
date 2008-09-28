package com.cosmos.acacia.crm.gui;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaLookupListener;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.listeners.TableModificationListener;
import java.util.List;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class ClassifyObjectPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(ClassifyObjectPanel.class);

    /** Creates new form ContactPersonPanel */
    public ClassifyObjectPanel(DataObject dataObject) {
        super(null);
        dataObjectToBeClassified = dataObject;
        init();
    }

    /** Creates new form ContactPersonPanel */
    public ClassifyObjectPanel() {
        super(null);
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
        groupLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        classifiersPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        jBLabel1 = new com.cosmos.swingb.JBLabel();
        appliedClassifiersPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        removeButton = new com.cosmos.swingb.JBButton();
        applyButton = new com.cosmos.swingb.JBButton();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        groupLookup.setName("groupLookup"); // NOI18N

        classifiersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Classifiers to be applied"));
        classifiersPanel.setName("classifiersPanel"); // NOI18N

        javax.swing.GroupLayout classifiersPanelLayout = new javax.swing.GroupLayout(classifiersPanel);
        classifiersPanel.setLayout(classifiersPanelLayout);
        classifiersPanelLayout.setHorizontalGroup(
            classifiersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );
        classifiersPanelLayout.setVerticalGroup(
            classifiersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ClassifyObjectPanel.class);
        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        appliedClassifiersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Already applied classifiers"));
        appliedClassifiersPanel.setName("appliedClassifiersPanel"); // NOI18N

        javax.swing.GroupLayout appliedClassifiersPanelLayout = new javax.swing.GroupLayout(appliedClassifiersPanel);
        appliedClassifiersPanel.setLayout(appliedClassifiersPanelLayout);
        appliedClassifiersPanelLayout.setHorizontalGroup(
            appliedClassifiersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );
        appliedClassifiersPanelLayout.setVerticalGroup(
            appliedClassifiersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
        );

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(ClassifyObjectPanel.class, this);
        removeButton.setAction(actionMap.get("removeClassifierAction")); // NOI18N
        removeButton.setText(resourceMap.getString("removeButton.text")); // NOI18N
        removeButton.setName("removeButton"); // NOI18N

        applyButton.setAction(actionMap.get("applyClassifierAction")); // NOI18N
        applyButton.setText(resourceMap.getString("applyButton.text")); // NOI18N
        applyButton.setName("applyButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(groupLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                    .addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addComponent(appliedClassifiersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addComponent(classifiersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(applyButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(groupLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classifiersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(appliedClassifiersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.TableHolderPanel appliedClassifiersPanel;
    private com.cosmos.swingb.JBButton applyButton;
    private com.cosmos.acacia.gui.TableHolderPanel classifiersPanel;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.acacia.gui.AcaciaLookup groupLookup;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBButton removeButton;
    // End of variables declaration//GEN-END:variables

    @EJB
    private ClassifiersRemote formSession;

    private DataObject dataObjectToBeClassified;

    ClassifiersListPanel classifiersTable;
    ClassifiersListPanel appliedClassifiersTable;

    @Override
    protected void initData() {
        setResizable(false);

        entityFormButtonPanel.setVisible(EntityFormButtonPanel.Button.Save, false);
        entityFormButtonPanel.setVisible(EntityFormButtonPanel.Button.Problems, false);

        groupLookup.setLookupProvider(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    groupLookup.setSelectedItem(onChooseGroup());
                    groupLookup.updateText();
                    return null;
                }
            });

        groupLookup.setEnabled(true);
        groupLookup.setListener(new AcaciaLookupListener() {
            @Override
            public void valueCleared() {
                classifiersTable.setParentDataObjectId(null);
                classifiersTable.refreshAction();
            }
        });


        appliedClassifiersTable = new ClassifiersListPanel(dataObjectToBeClassified);
        //appliedClassifiersTable.setVisibleButtons(16);
        appliedClassifiersTable.setVisible(AbstractTablePanel.Button.Refresh);

        appliedClassifiersPanel.add(appliedClassifiersTable);


        classifiersTable = new ClassifiersListPanel(null, dataObjectToBeClassified.getDataObjectType());
        //classifiersTable.setVisibleButtons(2 + 4 + 8 + 16);
        classifiersTable.setVisible(AbstractTablePanel.Button.NewModifyDeleteRefresh);
        filterClassifiers();

        classifiersTable.addTablePanelListener(new RefreshListener());
        classifiersTable.addTableModificationListener(new NewClassifierListener());

        classifiersPanel.add(classifiersTable);
    }


    protected Object onChooseGroup() {
        ClassifierGroupsListPanel listPanel = new ClassifierGroupsListPanel(null);

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            ClassifierGroup selected = (ClassifierGroup) listPanel.getSelectedRowObject();
            classifiersTable.setParentDataObjectId(selected.getDataObject().getDataObjectId());
            classifiersTable.setDataObjectType(dataObjectToBeClassified.getDataObjectType());
            classifiersTable.refreshAction();
            return selected;
        } else {
            return null;
        }
    }

    protected void filterClassifiers() {
        classifiersTable.filter(appliedClassifiersTable.getClassifiers());
    }

    protected List<ClassifierGroup> getClassifierGroups() {
        return getFormSession().getClassifierGroups();
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

    protected EntityProperties getClassifiedObjectEntityProperties()
    {
        return getFormSession().getClassifiedObjectEntityProperties();
    }


    @Override
    public BindingGroup getBindingGroup() {
        return null;
    }

    @Override
    public void performSave(boolean closeAfter) {

    }

    @Override
    public Object getEntity() {
        return null;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Action
    public void applyClassifierAction() {
        getFormSession().classifyDataObject(
                dataObjectToBeClassified,
                (Classifier) classifiersTable.getDataTable().getSelectedRowObject());
        appliedClassifiersTable.refreshAction();
        filterClassifiers();
    }

    @Action
    public void removeClassifierAction() {
        Classifier classifier = (Classifier) appliedClassifiersTable.getDataTable().getSelectedRowObject();
        getFormSession().unclassifyDataObject(
                dataObjectToBeClassified,
                classifier);
        appliedClassifiersTable.refreshAction();
        classifiersTable.getDataTable().addRow(classifier);
    }

    class RefreshListener implements TablePanelListener {

        @Override
        public void tablePanelClose() {
           //
        }

        @Override
        public void selectionRowChanged() {
            //
        }

        @Override
        public void selectAction() {
            //
        }

        @Override
        public void tableRefreshed() {
            filterClassifiers();
        }
    }

    class NewClassifierListener implements TableModificationListener {

        @Override
        public void rowDeleted(Object row) {
            //
        }

        @Override
        public void rowModified(Object row) {
            //
        }

        @Override
        public void rowAdded(Object row) {
            groupLookup.clearSelectedValue();
        }
    }
}