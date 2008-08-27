/*
 * AssemblingSchemaChoicePanel.java
 *
 * Created on 11 August 2008, 22:07
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.TableHolderPanel;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class AssemblingSchemaChoicePanel extends JBPanel {

    protected static Logger log = Logger.getLogger(AssemblingSchemaChoicePanel.class);

    public enum Mode
    {
        AssembleSchemaSelect(
            Button.Select,
            Button.Unselect,
            Button.Refresh,
            Button.Close),

        AssemblingSchema(
            Button.New,
            Button.Modify,
            Button.Delete,
            Button.Refresh,
            Button.Close),

        AssemblingSchemaSelect(
            Button.Select,
            Button.Unselect,
            Button.New,
            Button.Modify,
            Button.Delete,
            Button.Refresh,
            Button.Close)
        ;

        private Mode(Button firstButton, Button... restButtons)
        {
            this.buttons = EnumSet.of(firstButton, restButtons);
        }

        private Set<Button> buttons;

        public Set<Button> getButtons()
        {
            return buttons;
        }
    }

    /** Creates new form AssemblingSchemaChoicePanel */
    public AssemblingSchemaChoicePanel() {
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

        assemblingCategoryLabel = new com.cosmos.swingb.JBLabel();
        assemblingCategoryComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        schemasHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        proceedButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AssemblingSchemaChoicePanel.class);
        assemblingCategoryLabel.setText(resourceMap.getString("assemblingCategoryLabel.text")); // NOI18N
        assemblingCategoryLabel.setName("assemblingCategoryLabel"); // NOI18N

        assemblingCategoryComboList.setName("assemblingCategoryComboList"); // NOI18N

        schemasHolderPanel.setName("schemasHolderPanel"); // NOI18N

        javax.swing.GroupLayout schemasHolderPanelLayout = new javax.swing.GroupLayout(schemasHolderPanel);
        schemasHolderPanel.setLayout(schemasHolderPanelLayout);
        schemasHolderPanelLayout.setHorizontalGroup(
            schemasHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );
        schemasHolderPanelLayout.setVerticalGroup(
            schemasHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(AssemblingSchemaChoicePanel.class, this);
        proceedButton.setAction(actionMap.get("proceed")); // NOI18N
        proceedButton.setText(resourceMap.getString("proceedButton.text")); // NOI18N
        proceedButton.setName("proceedButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(schemasHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(assemblingCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(assemblingCategoryComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                    .addComponent(proceedButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(assemblingCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assemblingCategoryComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(schemasHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaComboList assemblingCategoryComboList;
    private com.cosmos.swingb.JBLabel assemblingCategoryLabel;
    private com.cosmos.swingb.JBButton proceedButton;
    private com.cosmos.acacia.gui.TableHolderPanel schemasHolderPanel;
    // End of variables declaration//GEN-END:variables


    private Mode mode;
    private AssemblingRemote formSession;
    private AssemblingSchemasTablePanel tablePanel;

    public void initData()
    {
        proceedButton.setVisible(false);
        tablePanel = new AssemblingSchemasTablePanel();
        schemasHolderPanel.add(tablePanel);
//        if (getModalityType() != ModalityType.MODELESS)
//            proceedButton.setVisible(false);
        setMode(Mode.AssembleSchemaSelect);
    }

    public Mode getMode()
    {
        return mode;
    }

    public void setMode(Mode mode)
    {
        this.mode = mode;
        tablePanel.setVisible(mode.getButtons());
    }

    protected AssemblingRemote getFormSession() {
        if (formSession == null)
            formSession = AcaciaPanel.getRemoteBean(this, AssemblingRemote.class);

        return formSession;
    }


    private class AssemblingSchemasTablePanel
        extends AbstractTablePanel
        implements ItemListener
    {
        private BindingGroup categoryBindingGroup;
        private BindingGroup bindingGroup;

        private EntityProperties entityProps;

        private AssemblingCategory category;
        private AssemblingSchema categorySchema;


        public AssemblingSchemasTablePanel()
        {
        }

        @Override
        protected void initData()
        {
            super.initData();

            setVisible(AbstractTablePanel.Button.Classify, false);
            setEnabled(AbstractTablePanel.Button.New, false);

            assemblingCategoryComboList.getComboBox().setPrototypeDisplayValue("123456789012345");

            entityProps = getFormSession().getAssemblingSchemaEntityProperties();

            categoryBindingGroup = new BindingGroup();
            if(categorySchema == null)
                categorySchema = getFormSession().newAssemblingSchema();
            PropertyDetails propDetails = entityProps.getPropertyDetails("assemblingCategory");
            //AssemblingCategoriesTreePanel listPanel = new AssemblingCategoriesTreePanel();
            AssemblingCategoryTreeTablePanel listPanel = new AssemblingCategoryTreeTablePanel(category);
            assemblingCategoryComboList.bind(
                categoryBindingGroup,
                listPanel,
                categorySchema,
                propDetails,
                "${categoryCode}, ${categoryName}",
                UpdateStrategy.READ_WRITE);
            assemblingCategoryComboList.addItemListener(this);
            categoryBindingGroup.bind();

            refreshDataTable(entityProps);
        }

        @SuppressWarnings("unchecked")
        private void refreshDataTable(EntityProperties entityProps)
        {
            if(bindingGroup != null)
                bindingGroup.unbind();

            bindingGroup = new BindingGroup();
            AcaciaTable table = getDataTable();

            JTableBinding tableBinding = table.bind(bindingGroup, getList(), entityProps, UpdateStrategy.READ);
            tableBinding.setEditable(false);

            bindingGroup.bind();
        }

        private List getList()
        {
            return getFormSession().getAssemblingSchemas(category);
        }

        @Override
        public void itemStateChanged(ItemEvent event)
        {
            if(event.getStateChange() > 0x700)
            {
                category = (AssemblingCategory)assemblingCategoryComboList.getSelectedItem();
                refreshDataTable(entityProps);
                AbstractTablePanel tp = getTablePanel();
                if(tp != null)
                    tp.setEnabled(AbstractTablePanel.Button.New, category != null);
            }
        }

        @Override
        protected boolean deleteRow(Object rowObject)
        {
            return getFormSession().deleteAssemblingSchema((AssemblingSchema)rowObject);
        }

        @Override
        protected Object modifyRow(Object rowObject)
        {
            return onEditEntity((AssemblingSchema)rowObject);
        }

        @Override
        protected Object newRow()
        {
            AssemblingSchema as = getFormSession().newAssemblingSchema();
            as.setAssemblingCategory(category);
            return onEditEntity(as);
        }

        private Object onEditEntity(AssemblingSchema as)
        {
            AssemblingSchemaPanel editPanel = new AssemblingSchemaPanel(as);
            DialogResponse response = editPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return editPanel.getSelectedValue();
            }

            return null;
        }

        @Override
        public boolean canCreate()
        {
            return true;
        }

        @Override
        public boolean canModify(Object rowObject)
        {
            return true;
        }

        @Override
        public boolean canDelete(Object rowObject)
        {
            return true;
        }

        @Override
        public void close()
        {
            AssemblingSchemaChoicePanel.this.setDialogResponse(getDialogResponse());
            AssemblingSchemaChoicePanel.this.close();
        }


    }

    public AbstractTablePanel getTablePanel() {
        return tablePanel;
    }

    public AcaciaComboList getAssemblingCategoryComboList() {
        return assemblingCategoryComboList;
    }

    public void setAssemblingCategoryComboList(AcaciaComboList assemblingCategoryComboList) {
        this.assemblingCategoryComboList = assemblingCategoryComboList;
    }

    public JBLabel getAssemblingCategoryLabel() {
        return assemblingCategoryLabel;
    }

    public void setAssemblingCategoryLabel(JBLabel assemblingCategoryLabel) {
        this.assemblingCategoryLabel = assemblingCategoryLabel;
    }

    public TableHolderPanel getSchemasHolderPanel() {
        return schemasHolderPanel;
    }

    public void setSchemasHolderPanel(TableHolderPanel schemasHolderPanel) {
        this.schemasHolderPanel = schemasHolderPanel;
    }

    public void addTableListener(TablePanelListener listener) {
        tablePanel.addTablePanelListener(listener);
    }

    @Action
    public void proceed() {
        AssemblingSchema selectedSchema = (AssemblingSchema) tablePanel.getDataTable().getSelectedRowObject();
        if (selectedSchema != null) {
            AssemblingParametersPanel paramsPanel = new AssemblingParametersPanel(selectedSchema);
            paramsPanel.showFrame();
            close();
        }
    }

    @Override
    public void close() {
        System.out.println("AssemblingSchemaChoicePanel.close()");
        try {
            ((JBPanel) getParent().getParent().getParent().getParent()).close();
        } catch (Exception ex) {
            // no JBPanel parent available
            ex.printStackTrace();
        }

        super.close();
        System.out.println("return super.close()");
    }
}
