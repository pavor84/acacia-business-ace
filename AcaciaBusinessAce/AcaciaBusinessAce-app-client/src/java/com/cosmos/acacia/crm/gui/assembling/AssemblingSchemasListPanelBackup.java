/*
 * AssemblingSchemasListPanel.java
 *
 * Created on Вторник, 2008, Юни 10, 21:07
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.SelectableListDialog;
import java.awt.Dimension;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.JPanel;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author  Miro
 */
public class AssemblingSchemasListPanelBackup
    extends AcaciaPanel
    implements SelectableListDialog
{
    @EJB
    private static AssemblingRemote formSession;


    /** Creates new form AssemblingSchemasListPanel */
    public AssemblingSchemasListPanelBackup(BigInteger parentId)
    {
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

        assemblingSchemasPanel = new com.cosmos.swingb.JBPanel();
        assemblingCategoryLabel = new com.cosmos.swingb.JBLabel();
        assemblingCategoryLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        schemasTablePanel = getAssemblingSchemasTablePanel();
        assemblingSchemasSplitPane = new com.cosmos.swingb.JBSplitPane();
        schemaItemsSplitPane = new com.cosmos.swingb.JBSplitPane();
        schemaItemsTitledPanel = new com.cosmos.swingb.JBTitledPanel();
        itemValuesTitledPanel = new com.cosmos.swingb.JBTitledPanel();
        assemblingSchemasTitledPanel = new com.cosmos.swingb.JBTitledPanel();

        assemblingSchemasPanel.setName("assemblingSchemasPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AssemblingSchemasListPanelBackup.class);
        assemblingCategoryLabel.setText(resourceMap.getString("assemblingCategoryLabel.text")); // NOI18N
        assemblingCategoryLabel.setName("assemblingCategoryLabel"); // NOI18N

        assemblingCategoryLookup.setName("assemblingCategoryLookup"); // NOI18N

        schemasTablePanel.setName("schemasTablePanel"); // NOI18N

        javax.swing.GroupLayout assemblingSchemasPanelLayout = new javax.swing.GroupLayout(assemblingSchemasPanel);
        assemblingSchemasPanel.setLayout(assemblingSchemasPanelLayout);
        assemblingSchemasPanelLayout.setHorizontalGroup(
            assemblingSchemasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assemblingSchemasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(assemblingCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(assemblingCategoryLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(schemasTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );
        assemblingSchemasPanelLayout.setVerticalGroup(
            assemblingSchemasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assemblingSchemasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(assemblingSchemasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(assemblingCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assemblingCategoryLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(schemasTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
        );

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        assemblingSchemasSplitPane.setDividerLocation(450);
        assemblingSchemasSplitPane.setName("assemblingSchemasSplitPane"); // NOI18N

        schemaItemsSplitPane.setDividerLocation(300);
        schemaItemsSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        schemaItemsSplitPane.setName("schemaItemsSplitPane"); // NOI18N

        schemaItemsTitledPanel.setTitle(resourceMap.getString("schemaItemsTitledPanel.title")); // NOI18N
        schemaItemsTitledPanel.setContentContainer(getSchemaItemsTablePanel());
        schemaItemsTitledPanel.setName("schemaItemsTitledPanel"); // NOI18N
        schemaItemsSplitPane.setTopComponent(schemaItemsTitledPanel);

        itemValuesTitledPanel.setTitle(resourceMap.getString("itemValuesTitledPanel.title")); // NOI18N
        itemValuesTitledPanel.setContentContainer(getItemValuesTablePanel());
        itemValuesTitledPanel.setName("itemValuesTitledPanel"); // NOI18N
        schemaItemsSplitPane.setRightComponent(itemValuesTitledPanel);

        assemblingSchemasSplitPane.setRightComponent(schemaItemsSplitPane);

        assemblingSchemasTitledPanel.setTitle(resourceMap.getString("assemblingSchemasTitledPanel.title")); // NOI18N
        assemblingSchemasTitledPanel.setContentContainer(assemblingSchemasPanel);
        assemblingSchemasTitledPanel.setName("assemblingSchemasTitledPanel"); // NOI18N
        assemblingSchemasSplitPane.setLeftComponent(assemblingSchemasTitledPanel);

        add(assemblingSchemasSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel assemblingCategoryLabel;
    private com.cosmos.acacia.gui.AcaciaLookup assemblingCategoryLookup;
    private com.cosmos.swingb.JBPanel assemblingSchemasPanel;
    private com.cosmos.swingb.JBSplitPane assemblingSchemasSplitPane;
    private com.cosmos.swingb.JBTitledPanel assemblingSchemasTitledPanel;
    private com.cosmos.swingb.JBTitledPanel itemValuesTitledPanel;
    private com.cosmos.swingb.JBSplitPane schemaItemsSplitPane;
    private com.cosmos.swingb.JBTitledPanel schemaItemsTitledPanel;
    private com.cosmos.swingb.JBPanel schemasTablePanel;
    // End of variables declaration//GEN-END:variables


    private AssemblingSchemasTablePanel assemblingSchemasTablePanel;
    private SchemaItemsTablePanel schemaItemsTablePanel;
    private ItemValuesTablePanel itemValuesTablePanel;

    private AssemblingSchema assemblingSchema;
    private AssemblingSchemaItem assemblingSchemaItem;


    @Override
    protected void initData()
    {
        JPanel panel = getAssemblingSchemasTablePanel();
        Dimension size = panel.getPreferredSize();
        int width = size.width;
        panel = getSchemaItemsTablePanel();
        size = panel.getPreferredSize();
        if(size.width > 0)
            width = (width + size.width) / 2;
        assemblingSchemasSplitPane.setDividerLocation(width);
    }

    private AssemblingSchemasTablePanel getAssemblingSchemasTablePanel()
    {
        if(assemblingSchemasTablePanel == null)
        {
            assemblingSchemasTablePanel = new AssemblingSchemasTablePanel();
            AssemblingSchemasTableListener listener = new AssemblingSchemasTableListener();
            assemblingSchemasTablePanel.addTablePanelListener(listener);
        }

        return assemblingSchemasTablePanel;
    }

    private SchemaItemsTablePanel getSchemaItemsTablePanel()
    {
        if(schemaItemsTablePanel == null)
        {
            schemaItemsTablePanel = new SchemaItemsTablePanel();
            AssemblingSchemaItemsTableListener listener = new AssemblingSchemaItemsTableListener();
            schemaItemsTablePanel.addTablePanelListener(listener);
        }

        return schemaItemsTablePanel;
    }

    private ItemValuesTablePanel getItemValuesTablePanel()
    {
        if(itemValuesTablePanel == null)
        {
            itemValuesTablePanel = new ItemValuesTablePanel();
        }

        return itemValuesTablePanel;
    }

    public AssemblingSchema getAssemblingSchema()
    {
        return assemblingSchema;
    }

    public void setAssemblingSchema(AssemblingSchema assemblingSchema)
    {
        this.assemblingSchema = assemblingSchema;
    }

    public AssemblingSchemaItem getAssemblingSchemaItem()
    {
        return assemblingSchemaItem;
    }

    public void setAssemblingSchemaItem(AssemblingSchemaItem assemblingSchemaItem)
    {
        this.assemblingSchemaItem = assemblingSchemaItem;
    }

    protected AssemblingRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getRemoteBean(this, AssemblingRemote.class);
        }

        return formSession;
    }

    @Override
    public Object getSelectedRowObject()
    {
        AssemblingSchemasTablePanel asTablePanel = getAssemblingSchemasTablePanel();
        return asTablePanel.getSelectedRowObject();
    }

    @Override
    public void setSelectedRowObject(Object selectedObject)
    {
        AssemblingSchemasTablePanel asTablePanel = getAssemblingSchemasTablePanel();
        asTablePanel.setSelectedRowObject(selectedObject);
    }

    @Override
    public List getListData()
    {
        AssemblingSchemasTablePanel asTablePanel = getAssemblingSchemasTablePanel();
        return asTablePanel.getListData();
    }

    @Override
    public void setEditable(boolean editable)
    {
        AssemblingSchemasTablePanel asTablePanel = getAssemblingSchemasTablePanel();
        asTablePanel.setEditable(editable);
    }

    @Override
    public boolean isEditable()
    {
        AssemblingSchemasTablePanel asTablePanel = getAssemblingSchemasTablePanel();
        return asTablePanel.isEditable();
    }

    @Override
    public void setVisibleSelectButtons(boolean visible)
    {
        AssemblingSchemasTablePanel asTablePanel = getAssemblingSchemasTablePanel();
        asTablePanel.setVisibleSelectButtons(visible);
    }


    private class AssemblingSchemasTableListener
        implements TablePanelListener
    {
        @Override
        public void tablePanelClose() {}

        @Override
        public void selectionRowChanged()
        {
            AssemblingSchemasTablePanel asTablePanel = getAssemblingSchemasTablePanel();
            setAssemblingSchema((AssemblingSchema)asTablePanel.getDataTable().getSelectedRowObject());
            getSchemaItemsTablePanel().refreshDataTable();
        }

        @Override
        public void selectAction() {}

        @Override
        public void tableRefreshed() {}
    }

    private class AssemblingSchemaItemsTableListener
        implements TablePanelListener
    {
        @Override
        public void tablePanelClose() {}

        @Override
        public void selectionRowChanged()
        {
            SchemaItemsTablePanel siTablePanel = getSchemaItemsTablePanel();
            setAssemblingSchemaItem((AssemblingSchemaItem)siTablePanel.getDataTable().getSelectedRowObject());
            getItemValuesTablePanel().refreshDataTable();
        }

        @Override
        public void selectAction() {}

        @Override
        public void tableRefreshed() {}
    }

    private class AssemblingSchemasTablePanel
        extends AbstractTablePanel
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

            entityProps = getFormSession().getAssemblingSchemaEntityProperties();

            categoryBindingGroup = new BindingGroup();
            if(categorySchema == null)
                categorySchema = getFormSession().newAssemblingSchema();
            PropertyDetails propDetails = entityProps.getPropertyDetails("assemblingCategory");
            assemblingCategoryLookup.bind(new AcaciaLookupProvider()
                {
                    @Override
                    public Object showSelectionControl()
                    {
                        return onChooseCategory();
                    }
                },
                categoryBindingGroup,
                categorySchema, 
                propDetails, 
                "${categoryCode}, ${categoryName}",
                UpdateStrategy.READ_WRITE);
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

        protected AssemblingCategory onChooseCategory()
        {
            AssemblingCategoryListPanel listPanel = new AssemblingCategoryListPanel(category, true);

            log.info("onChooseCategory: " + category);

            DialogResponse response = listPanel.showDialog(this);
            if(DialogResponse.SELECT.equals(response))
            {
                AssemblingCategory selectedCategory = (AssemblingCategory)listPanel.getSelectedRowObject();
                category = selectedCategory;
                refreshDataTable(entityProps);
                log.info("selectedCategory: " + selectedCategory);

                return selectedCategory;
            }

            return null;
        }

        @Override
        protected boolean deleteRow(Object rowObject)
        {
            return true;
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
    }

    private class SchemaItemsTablePanel
        extends AbstractTablePanel
    {
        private BindingGroup bindingGroup;
        private EntityProperties entityProps;

        public SchemaItemsTablePanel()
        {
        }

        @Override
        protected void initData()
        {
            super.initData();
            setVisible(AbstractTablePanel.Button.Classify, false);
            setVisible(AbstractTablePanel.Button.Close, false);

            entityProps = getFormSession().getAssemblingSchemaItemEntityProperties();

            refreshDataTable(entityProps);
        }

        public void refreshDataTable()
        {
            refreshDataTable(entityProps);
        }

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
            AssemblingSchema as = getAssemblingSchema();
            if(as != null)
                return getFormSession().getAssemblingSchemaItems(as);
            else
                return Collections.emptyList();
        }

        @Override
        protected boolean deleteRow(Object rowObject)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected Object modifyRow(Object rowObject)
        {
            return onEditEntity((AssemblingSchemaItem)rowObject);
        }

        @Override
        protected Object newRow()
        {
            AssemblingSchemaItem asi = new AssemblingSchemaItem();
            asi.setAssemblingSchema(getAssemblingSchema());
            return onEditEntity(asi);
        }

        private Object onEditEntity(AssemblingSchemaItem asi)
        {
            AssemblingSchemaItemPanel editPanel = new AssemblingSchemaItemPanel(asi);
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
    }

    private class ItemValuesTablePanel
        extends AbstractTablePanel
    {
        private BindingGroup bindingGroup;
        private EntityProperties entityProps;

        public ItemValuesTablePanel()
        {
        }

        @Override
        protected void initData()
        {
            super.initData();
            setVisible(AbstractTablePanel.Button.Classify, false);
            setVisible(AbstractTablePanel.Button.Close, false);

            entityProps = getFormSession().getAssemblingSchemaItemValueEntityProperties();

            refreshDataTable(entityProps);
        }

        public void refreshDataTable()
        {
            refreshDataTable(entityProps);
        }

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
            AssemblingSchemaItem asi = getAssemblingSchemaItem();
            if(asi != null)
                return getFormSession().getAssemblingSchemaItemValues(asi);
            else
                return Collections.emptyList();
        }

        @Override
        protected boolean deleteRow(Object rowObject)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected Object modifyRow(Object rowObject)
        {
            return onEditEntity((AssemblingSchemaItemValue)rowObject);
        }

        @Override
        protected Object newRow()
        {
            AssemblingSchemaItemValue itemValue = new AssemblingSchemaItemValue();
            itemValue.setAssemblingSchemaItem(getAssemblingSchemaItem());
            return onEditEntity(itemValue);
        }

        private Object onEditEntity(AssemblingSchemaItemValue itemValue)
        {
            AssemblingSchemaItemValuePanel editPanel = new AssemblingSchemaItemValuePanel(itemValue);
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
    }
}
