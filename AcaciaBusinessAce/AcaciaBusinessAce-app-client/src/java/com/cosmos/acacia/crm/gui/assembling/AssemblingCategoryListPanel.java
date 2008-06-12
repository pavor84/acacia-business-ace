/*
 * AssemblingCategoryListPanel.java
 *
 * Created on Понеделник, 2008, Юни 2, 23:04
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AbstractTreeEnabledTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author  Miro
 */
public class AssemblingCategoryListPanel
    extends AbstractTreeEnabledTablePanel<AssemblingCategory>
{
    @EJB
    private static AssemblingRemote formSession;

    private BindingGroup bindingGroup;
    private EntityProperties entityProps;


    /** Creates new form AssemblingCategoryListPanel */
    public AssemblingCategoryListPanel(
            DataObjectBean dataObjectBean,
            boolean removeTableContainerGaps)
    {
        super(dataObjectBean);
        initComponentsCustom(removeTableContainerGaps);
    }

    public AssemblingCategoryListPanel(
            BigInteger parentDataObjectId,
            boolean removeTableContainerGaps)
    {
        super(parentDataObjectId);
        initComponentsCustom(removeTableContainerGaps);
    }

    public AssemblingCategoryListPanel(){
        this((BigInteger)null, false);
    }

    public AssemblingCategoryListPanel(boolean removeTableGaps){
        this((BigInteger)null, removeTableGaps);
    }

    private void initComponentsCustom(boolean removeTableContainerGaps)
    {
        //if removing the gaps - set the layout manually, with no gaps
        if(removeTableContainerGaps)
        {
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(getDataScrollPane(), javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                        .addComponent(getButtonsPanel(), javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    )
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(getDataScrollPane(), javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(getButtonsPanel(), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            );
        }
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        super.initData();

        entityProps = getFormSession().getAssemblingCategoryEntityProperties();

        refreshDataTable();
    }

    @Override
    public void refreshDataTable()
    {
        if ( bindingGroup!=null )
            bindingGroup.unbind();

        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();

        List<AssemblingCategory> theList = getLister().getList();
        JTableBinding tableBinding = table.bind(bindingGroup, theList, entityProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @Override
    public void deleteAction()
    {
        String message = getResourceMap().getString("deleteAction.ConfirmDialog.message");
        if(!showDeleteConfirmation(message))
        {
            return;
        }

        Object rowObject = getDataTable().getSelectedRowObject();

        List<AssemblingCategory> withSubCategories = getWithSubCategories((AssemblingCategory)rowObject);
        boolean result = false;
        try
        {
            result = getFormSession().deleteAssemblingCategories(withSubCategories);
        }
        catch(Exception ex)
        {
            ValidationException ve = extractValidationException(ex);
            if(ve != null)
            {
                String messagePrefix = null;
                if(withSubCategories.size() > 1)
                {
                    messagePrefix = getResourceMap().getString("AssemblingCategory.err.cantDeleteMany");
                }
                else
                {
                    messagePrefix = getResourceMap().getString("deleteAction.err.referenced");
                }

                message = getTableReferencedMessage(messagePrefix, ve.getMessage());

                JOptionPane.showMessageDialog(
                    this,
                    message,
                    getResourceMap().getString("AssemblingCategory.err.cantDeleteTitle"),
                    JOptionPane.DEFAULT_OPTION);
            }
            else
            {
                log.error(ex);
            }
            result = false;
        }

        if(result)
        {
            removeFromTable(withSubCategories);
            fireDelete(rowObject);
        }
    }

    /**
     * Forms the error message shown when constraint violation occurs
     *
     * @param the name of the table
     * @return the message
     */
    private String getTableReferencedMessage(String cantDeleteMessagePrefix, String table)
    {
        String message = cantDeleteMessagePrefix;
        String tableUserfriendly = getResourceMap().getString("table.userfriendlyname." + table);
        String result = null;
        if(tableUserfriendly == null)
            result = message + " " + table.replace('_', ' ');
        else
            result = message + " " + tableUserfriendly;
        return result;
    }

    @Override
    protected boolean deleteRow(Object rowObject)
    {
        //does nothing - unneeded (not called), because the delete WF logic is changed
        return true;
    }

    private void removeFromTable(List<AssemblingCategory> withSubCategories)
    {
        for(AssemblingCategory category : withSubCategories)
        {
            getDataTable().removeRow(category);
        }
    }

    @Override
    protected Object newRow()
    {
        AssemblingCategory category = getFormSession().newAssemblingCategory(null);
        AssemblingCategory autoParent = null;
        TreePath selection = getTree().getSelectionPath();
        if(selection != null)
        {
            DefaultMutableTreeNode selNode = (DefaultMutableTreeNode)selection.getLastPathComponent();
            if(selNode.getUserObject() instanceof AssemblingCategory)
                autoParent = (AssemblingCategory)selNode.getUserObject();
        }
            //(AssemblingCategory) getDataTable().getSelectedRowObject();
        //category.setParentCategory(autoParent);
        if(autoParent != null)
            category.setParentId(autoParent.getAssemblingCategoryId());
        else
            category.setParentId(null);

        return onEditEntity(category);
    }

    @Override
    protected Object onEditEntity(AssemblingCategory category)
    {
        AssemblingCategoryPanel editPanel = new AssemblingCategoryPanel(category);
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
        }

        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Object rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Object rowObject) {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        refreshDataTable();

        return t;
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
    protected List<AssemblingCategory> getItems()
    {
        return getFormSession().getAssemblingCategories(null);
    }

}
