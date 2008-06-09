/*
 * ProductCategoryListPanel.java
 *
 * Created on Неделя, 2008, Април 20, 15:44
 */

package com.cosmos.acacia.crm.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AbstractTreeEnabledTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;

/**
 *
 * Created	:	20.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class ProductCategoryListPanel extends AbstractTreeEnabledTablePanel<ProductCategory> {

    @EJB
    private ProductsListRemote formSession;

    private BindingGroup bindingGroup;

    private EntityProperties entityProps;

    public ProductCategoryListPanel(){
        this(null, false);
    }

    public ProductCategoryListPanel(boolean removeTableGaps){
        this(null, removeTableGaps);
    }

    @Override
    protected void initData() {
        super.initData();

        entityProps = getFormSession().getProductCategoryEntityProperties();

        refreshDataTable();
    }

    /** Creates new form ProductCategoryListPanel */
    public ProductCategoryListPanel(BigInteger parentDataObjectId, boolean removeTableContainerGaps) {
        super(parentDataObjectId);

        initComponentsCustom(removeTableContainerGaps);
    }

    private void initComponentsCustom(boolean removeTableContainerGaps) {
        //if removing the gaps - set the layout manually, with no gaps
        if ( removeTableContainerGaps ){

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

    @SuppressWarnings("unchecked")
    /**
     * When called the table is unbound, the bind again but
     * with the new list get from the lister.
     * (the lister is queried again)
     */
    @Override
    public void refreshDataTable(){
        if ( bindingGroup!=null )
            bindingGroup.unbind();

        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();

        List<ProductCategory> theList = getLister().getList();
        JTableBinding tableBinding = table.bind(bindingGroup, theList, entityProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("unused")
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

    @Override
    public void deleteAction() {
        if ( !showDeleteConfirmation(
            getResourceMap().getString("deleteAction.ConfirmDialog.message")) ){
            return;
        }

        Object rowObject = getDataTable().getSelectedRowObject();

        List<ProductCategory> withSubCategories = getWithSubCategories((ProductCategory)rowObject);
        boolean result = false;
        try{
            result = getFormSession().deleteProductCategories(withSubCategories);
        }catch (Exception e){
            ValidationException ve = extractValidationException(e);
            if (ve != null) {
                String messagePrefix = null;
                if ( withSubCategories.size()>1 ){
                    messagePrefix = getResourceMap().getString("ProductCategory.err.cantDeleteMany");
                }
                else{
                    messagePrefix = getResourceMap().getString("deleteAction.err.referenced");
                }

                String message = getTableReferencedMessage(messagePrefix, ve.getMessage());

                JOptionPane.showMessageDialog(this,
                    message,
                    getResourceMap().getString("ProductCategory.err.cantDeleteTitle"),
                    JOptionPane.DEFAULT_OPTION);
            } else {
                log.error(e);
            }
            result = false;
        }

        if ( result ){
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
        String tableUserfriendly =
            getResourceMap().getString("table.userfriendlyname."+table);
        String result = null;
        if ( tableUserfriendly==null )
            result = message + " " + table.replace('_', ' ');
        else
            result = message + " " + tableUserfriendly;
        return result;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        //does nothing - unneeded (not called), because the delete WF logic is changed
        return true;
    }

    private void removeFromTable(List<ProductCategory> withSubCategories) {
        for (ProductCategory productCategory : withSubCategories) {
            getDataTable().removeRow(productCategory);
        }
    }

    @Override
    protected Object newRow()
    {
        ProductCategory category = getFormSession().newProductCategory(null);
        ProductCategory autoParent = null;
        TreePath selection = getTree().getSelectionPath();
        if(selection != null)
        {
            DefaultMutableTreeNode selNode = (DefaultMutableTreeNode)selection.getLastPathComponent();
            Object userObject;
            if((userObject = selNode.getUserObject()) instanceof ProductCategory )
                autoParent = (ProductCategory)userObject;
        }
            //(ProductCategory) getDataTable().getSelectedRowObject();
        category.setParentCategory(autoParent);

        return onEditEntity(category);
    }

    @Override
    protected Object onEditEntity(ProductCategory category)
    {
        ProductCategoryPanel editPanel = new ProductCategoryPanel(category, category.getDataObject());
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

    protected ProductsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(ProductsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    @Override
    protected List<ProductCategory> getItems() {
        return getFormSession().getProductsCategories(null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
