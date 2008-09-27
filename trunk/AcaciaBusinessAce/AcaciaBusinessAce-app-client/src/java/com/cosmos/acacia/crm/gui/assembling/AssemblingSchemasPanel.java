/*
 * AssemblingSchemasPanel.java
 *
 * Created on Сряда, 2008, Август 27, 22:30
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBSplitPane;
import com.cosmos.swingb.JBTabbedPane;
import com.cosmos.swingb.JBTitledPanel;
import com.cosmos.swingb.SelectableListDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.swing.border.EmptyBorder;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author  Miro
 */
public class AssemblingSchemasPanel
    extends AcaciaPanel
    implements SelectableListDialog
{
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

    @EJB
    private static AssemblingRemote formSession;


    /** Creates new form AssemblingSchemasPanel */
    public AssemblingSchemasPanel(Mode mode, AssemblingSchema assemblingSchema)
    {
        initComponents();
        initData();
        setMode(mode);
        if(assemblingSchema != null)
            setSelectedRowObject(assemblingSchema);
    }

    public AssemblingSchemasPanel(Mode mode)
    {
        this(mode, null);
    }

    public AssemblingSchemasPanel()
    {
        this(Mode.AssemblingSchema);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private Mode mode;

    private JBTabbedPane tabbedPane;
    private JBPanel schemasPanel;
    private JBPanel itemValuesTabPanel;
    private JBTitledPanel schemaItemsTitledPanel;
    private JBTitledPanel itemValuesTitledPanel;
    private JBSplitPane itemValuesSplitPane;

    // schemasPanel - Assembling Schemas Table Panel
    private AssemblingSchemasTablePanel schemasTablePanel;
    private AcaciaComboList categoryComboList;
    private JBLabel categoryLabel;
    private JBPanel categoryPanel;

    private SchemaItemsTablePanel schemaItemsTablePanel;
    private ItemValuesTablePanel itemValuesTablePanel;

    private AssemblingSchema assemblingSchema;
    private AssemblingSchemaItem assemblingSchemaItem;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
    }

    public Mode getMode()
    {
        return mode;
    }

    public void setMode(Mode mode)
    {
        this.mode = mode;
        boolean selectForm = Mode.AssembleSchemaSelect.equals(mode);
        if(selectForm)
        {
            initSelectForm();
        }
        else
        {
            initModifyForm();
        }

        AbstractTablePanel tablePanel = getSchemasTablePanel();
        tablePanel.setVisible(mode.getButtons());
        tablePanel.setEditable(!selectForm);
    }

    private void clearForm()
    {
        removeAll();

        if(tabbedPane != null)
        {
            tabbedPane.removeAll();
            tabbedPane = null;
        }

        if(schemasPanel != null)
        {
            schemasPanel.removeAll();
            schemasPanel = null;
        }

        if(itemValuesTabPanel != null)
        {
            itemValuesTabPanel.removeAll();
            itemValuesTabPanel = null;
        }

        if(schemaItemsTitledPanel != null)
        {
            schemaItemsTitledPanel.removeAll();
            schemaItemsTitledPanel = null;
        }

        if(itemValuesTitledPanel != null)
        {
            itemValuesTitledPanel.removeAll();
            itemValuesTitledPanel = null;
        }

        if(itemValuesSplitPane != null)
        {
            itemValuesSplitPane.removeAll();
            itemValuesSplitPane = null;
        }

        if(schemasTablePanel != null)
        {
            schemasTablePanel.removeAll();
            schemasTablePanel = null;
        }

        if(categoryPanel != null)
        {
            categoryPanel.removeAll();
            categoryPanel = null;
        }
        categoryComboList = null;
        categoryLabel = null;

        if(schemaItemsTablePanel != null)
        {
            schemaItemsTablePanel.removeAll();
            schemaItemsTablePanel = null;
        }

        if(itemValuesTablePanel != null)
        {
            itemValuesTablePanel.removeAll();
            itemValuesTablePanel = null;
        }
    }

    private void initSelectForm()
    {
        clearForm();
        add(getSchemasPanel(), BorderLayout.CENTER);
    }

    private void initModifyForm()
    {
        clearForm();

        ResourceMap resource = getResourceMap();

        tabbedPane = new JBTabbedPane();
        itemValuesTabPanel = new JBPanel();
        itemValuesTabPanel.setLayout(new BorderLayout());
        tabbedPane.addTab("Schemas", getSchemasPanel());
        tabbedPane.addTab("Items & Values", itemValuesTabPanel);
        tabbedPane.setEnabledAt(1, false);

        schemaItemsTitledPanel = new JBTitledPanel();
        schemaItemsTitledPanel.setTitle(resource.getString("schemaItemsTitledPanel.title")); // NOI18N
        schemaItemsTitledPanel.setContentContainer(getSchemaItemsTablePanel());
        schemaItemsTitledPanel.setName("schemaItemsTitledPanel"); // NOI18N

        itemValuesTitledPanel = new JBTitledPanel();
        itemValuesTitledPanel.setTitle(resource.getString("itemValuesTitledPanel.title")); // NOI18N
        itemValuesTitledPanel.setContentContainer(getItemValuesTablePanel());
        itemValuesTitledPanel.setName("itemValuesTitledPanel"); // NOI18N

        itemValuesSplitPane = new JBSplitPane();
        itemValuesSplitPane.setOrientation(JBSplitPane.VERTICAL_SPLIT);
        itemValuesSplitPane.setTopComponent(schemaItemsTitledPanel);
        itemValuesSplitPane.setBottomComponent(itemValuesTitledPanel);
        itemValuesSplitPane.setDividerLocation(225);
        itemValuesSplitPane.setResizeWeight(0.5);
        itemValuesTabPanel.add(itemValuesSplitPane, BorderLayout.CENTER);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public JBPanel getSchemasPanel()
    {
        if(schemasPanel == null)
        {
            schemasPanel = new JBPanel();
            schemasPanel.setLayout(new BorderLayout());

            categoryPanel = new JBPanel();
            categoryLabel = new JBLabel();
            categoryComboList = new AcaciaComboList();

            schemasPanel.setLayout(new BorderLayout());
            categoryPanel.setLayout(new BorderLayout());

            categoryLabel.setText("Category:");
            categoryLabel.setBorder(new EmptyBorder(1, 10, 1, 10));
            categoryPanel.add(categoryLabel, BorderLayout.WEST);
            categoryPanel.add(categoryComboList, BorderLayout.CENTER);

            schemasPanel.add(categoryPanel, BorderLayout.NORTH);

            schemasTablePanel = getSchemasTablePanel();
            schemasPanel.add(schemasTablePanel, BorderLayout.CENTER);
        }

        return schemasPanel;
    }

    public AssemblingSchemasTablePanel getSchemasTablePanel()
    {
        if(schemasTablePanel == null)
        {
            schemasTablePanel = new AssemblingSchemasTablePanel();
        }

        return schemasTablePanel;
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
            formSession = getBean(AssemblingRemote.class);
        }

        return formSession;
    }

    @Override
    public Object getSelectedRowObject()
    {
        AbstractTablePanel asTablePanel = getSchemasTablePanel();
        return asTablePanel.getSelectedRowObject();
    }

    @Override
    public void setSelectedRowObject(Object selectedObject)
    {
        AbstractTablePanel asTablePanel = getSchemasTablePanel();
        asTablePanel.setSelectedRowObject(selectedObject);
    }

    @Override
    public List getListData()
    {
        AbstractTablePanel asTablePanel = getSchemasTablePanel();
        return asTablePanel.getListData();
    }

    @Override
    public void setEditable(boolean editable)
    {
        AbstractTablePanel asTablePanel = getSchemasTablePanel();
        asTablePanel.setEditable(editable);
    }

    @Override
    public boolean isEditable()
    {
        AbstractTablePanel asTablePanel = getSchemasTablePanel();
        return asTablePanel.isEditable();
    }

    @Override
    public void setVisibleSelectButtons(boolean visible)
    {
        AbstractTablePanel asTablePanel = getSchemasTablePanel();
        asTablePanel.setVisibleSelectButtons(visible);
        if (visible)
            asTablePanel.setVisibleButtons(1 + 2 + 4 + 32 + 64);

    }


    private class AssemblingSchemasTableListener
        implements TablePanelListener
    {
        @Override
        public void tablePanelClose() {}

        @Override
        public void selectionRowChanged()
        {
            AbstractTablePanel asTablePanel = getSchemasTablePanel();
            AssemblingSchema as = (AssemblingSchema)asTablePanel.getDataTable().getSelectedRowObject();
            setAssemblingSchema(as);

            if(!Mode.AssembleSchemaSelect.equals(getMode()))
            {
                if(as != null)
                {
                    getSchemaItemsTablePanel().setEnabled(AbstractTablePanel.Button.New, true);
                    tabbedPane.setEnabledAt(1, true);
                }
                else
                {
                    getSchemaItemsTablePanel().setEnabled(AbstractTablePanel.Button.New, false);
                    tabbedPane.setEnabledAt(1, false);
                }

                getSchemaItemsTablePanel().refreshDataTable();
            }
        }

        @Override
        public void selectAction() {
            setSelectedRowObject(getSchemasTablePanel().getSelectedRowObject());
            setDialogResponse(DialogResponse.SELECT);
            close();
        }

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
            AssemblingSchemaItem asi = (AssemblingSchemaItem)siTablePanel.getDataTable().getSelectedRowObject();
            getItemValuesTablePanel().setEnabled(AbstractTablePanel.Button.New, asi != null);
            setAssemblingSchemaItem(asi);
            getItemValuesTablePanel().refreshDataTable();
        }

        @Override
        public void selectAction() {}

        @Override
        public void tableRefreshed() {}
    }

    private class AssemblingSchemasTablePanel
        extends AbstractTablePanel
        implements ItemListener
    {
        private BindingGroup categoryBindingGroup;
        private BindingGroup bindingGroup;
        private EntityProperties entityProps;
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

            categoryComboList.getComboBox().setPrototypeDisplayValue("123456789012345");

            entityProps = getFormSession().getAssemblingSchemaEntityProperties();

            categoryBindingGroup = new BindingGroup();
            if(categorySchema == null)
                categorySchema = getFormSession().newAssemblingSchema();
            PropertyDetails propDetails = entityProps.getPropertyDetails("assemblingCategory");
            AssemblingCategoryTreeTablePanel listPanel =
                new AssemblingCategoryTreeTablePanel(getCategory());
            categoryComboList.bind(
                categoryBindingGroup,
                listPanel,
                categorySchema,
                propDetails,
                "${categoryCode}, ${categoryName}",
                UpdateStrategy.READ_WRITE);
            categoryComboList.addItemListener(this);
            categoryBindingGroup.bind();

            addTablePanelListener(new AssemblingSchemasTableListener());
            refreshDataTable(entityProps);
        }

        public AssemblingCategory getCategory()
        {
            return (AssemblingCategory)categoryComboList.getSelectedItem();
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
            return getFormSession().getAssemblingSchemas(getCategory());
        }

        @Override
        public void itemStateChanged(ItemEvent event)
        {
            if(event.getStateChange() > 0x700)
            {
                refreshDataTable(entityProps);
                setEnabled(AbstractTablePanel.Button.New, canCreate());
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
            as.setAssemblingCategory(getCategory());
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
            return getCategory() != null;
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
            AssemblingSchemasPanel.this.setDialogResponse(getDialogResponse());
            AssemblingSchemasPanel.this.close();
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
            setEnabled(AbstractTablePanel.Button.New, false);

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
            return getFormSession().deleteAssemblingSchemaItem((AssemblingSchemaItem)rowObject);
        }

        @Override
        protected Object modifyRow(Object rowObject)
        {
            return onEditEntity((AssemblingSchemaItem)rowObject);
        }

        @Override
        protected Object newRow()
        {
            AssemblingSchemaItem asi = getFormSession().newAssemblingSchemaItem(getAssemblingSchema());
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
            setEnabled(AbstractTablePanel.Button.New, false);

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
            /*tableBinding.setEditable(true);
            table.setEditable(true);

            MaskFormattedCellEditor cellEditor;
            String mask = "##-###";
            try
            {
                cellEditor = new MaskFormattedCellEditor(mask);
            }
            catch(ParseException ex)
            {
                throw new RuntimeException(ex);
            }
            MaskFormattedCellRenderer cellRenderer =
                new MaskFormattedCellRenderer(cellEditor);
            TableColumn column = table.getColumn("Min. Value");
            column.setCellEditor(cellEditor);
            column.setCellRenderer(cellRenderer);
            column = table.getColumn("Max. Value");
            column.setCellEditor(cellEditor);
            column.setCellRenderer(cellRenderer);
            table.setDefaultEditor(Serializable.class, cellEditor);

            ProductsListPanel productsListPanel = new ProductsListPanel(null);
            PropertyDetails propDetails = entityProps.getPropertyDetails("virtualProduct");
            table.bindComboListCellEditor(bindingGroup, productsListPanel, propDetails);*/

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
            return getFormSession().deleteAssemblingSchemaItemValue((AssemblingSchemaItemValue)rowObject);
        }

        @Override
        protected Object modifyRow(Object rowObject)
        {
            return onEditEntity((AssemblingSchemaItemValue)rowObject);
        }

        @Override
        protected Object newRow()
        {
            AssemblingSchemaItemValue itemValue;
            itemValue = getFormSession().newAssemblingSchemaItemValue(getAssemblingSchemaItem());
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
