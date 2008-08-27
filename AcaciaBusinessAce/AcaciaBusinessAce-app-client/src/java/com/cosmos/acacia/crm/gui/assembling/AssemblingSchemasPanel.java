/*
 * AssemblingSchemasPanel.java
 *
 * Created on Сряда, 2008, Август 27, 22:30
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBSplitPane;
import com.cosmos.swingb.JBTabbedPane;
import com.cosmos.swingb.JBTitledPanel;
import java.awt.BorderLayout;
import java.util.EnumSet;
import java.util.Set;
import javax.swing.border.EmptyBorder;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author  Miro
 */
public class AssemblingSchemasPanel
    extends AcaciaPanel
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


    /** Creates new form AssemblingSchemasPanel */
    public AssemblingSchemasPanel() {
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
    private AbstractTablePanel schemasTablePanel;
    private JBComboList categoryComboList;
    private JBLabel categoryLabel;
    private JBPanel categoryPanel;

    private AbstractTablePanel schemaItemsTablePanel;
    private AbstractTablePanel itemValuesTablePanel;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        setLayout(new BorderLayout());
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
        itemValuesSplitPane.setDividerLocation(0.5);
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

    public AbstractTablePanel getSchemasTablePanel()
    {
        if(schemasTablePanel == null)
        {
            schemasTablePanel = new TempTablePanel();
        }

        return schemasTablePanel;
    }

    private AbstractTablePanel getSchemaItemsTablePanel()
    {
        if(schemaItemsTablePanel == null)
        {
            schemaItemsTablePanel = new TempTablePanel();
        }

        return schemaItemsTablePanel;
    }

    private AbstractTablePanel getItemValuesTablePanel()
    {
        if(itemValuesTablePanel == null)
        {
            itemValuesTablePanel = new TempTablePanel();
        }

        return itemValuesTablePanel;
    }

    private class TempTablePanel
        extends AbstractTablePanel
    {

        @Override
        protected boolean deleteRow(Object rowObject)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected Object modifyRow(Object rowObject)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected Object newRow()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean canCreate()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean canModify(Object rowObject)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean canDelete(Object rowObject)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
