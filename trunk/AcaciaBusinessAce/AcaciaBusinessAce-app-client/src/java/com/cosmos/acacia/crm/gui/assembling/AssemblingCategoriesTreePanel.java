/*
 * AssemblingCategoriesTreePanel.java
 *
 * Created on Сряда, 2008, Юни 4, 23:17
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.gui.AbstractTreeEnabledTablePanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseTreePanel;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author  Miro
 */
public class AssemblingCategoriesTreePanel
    extends BaseTreePanel<AssemblingCategory>
{
    @EJB
    private static AssemblingRemote formSession;

    private AssemblingCategoryListPanel categoryListPanel;


    /** Creates new form AssemblingCategoriesTreePanel */
    public AssemblingCategoriesTreePanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
        //initComponents();
    }

    public AssemblingCategoriesTreePanel()
    {
        super(null);
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
    protected AssemblingCategory updateParent(
        AssemblingCategory newParent,
        AssemblingCategory newChildren)
    {
        try
        {
            AssemblingCategory updatedCategory = getFormSession().updateParents(newParent, newChildren);

            return updatedCategory;
        }
        catch(Exception ex)
        {
            handleException(ex);
            return null;
        }
    }

    @Override
    protected void initData()
    {
        categoryListPanel = new AssemblingCategoryListPanel(true);

        toStringConverter = new AcaciaToStringConverter("${categoryName}");
        getTree().setToStringConverter(toStringConverter);

        //load all categories and init the tree
        List<AssemblingCategory> categories = getFormSession().getAssemblingCategories(null);

        refreshTreeModel(categories);
    }

    @Override
    protected void onTableRefreshed()
    {
        //load all categories and refresh the tree
        List<AssemblingCategory> categories = getFormSession().getAssemblingCategories(null);

        refreshTreeModel(categories);
    }

    @Override
    public AbstractTreeEnabledTablePanel<AssemblingCategory> getListPanel()
    {
        return categoryListPanel;
    }

    @Override
    public AssemblingCategory getParent(AssemblingCategory child)
    {
        return getFormSession().getParent(child);
        //return child.getParentCategory();
    }

    @Override
    public void setParent(AssemblingCategory entity, AssemblingCategory parent)
    {
        //entity.setParentCategory(parent);
        if(parent != null)
            entity.setParentId(parent.getAssemblingCategoryId());
        else
            entity.setParentId(null);
    }

    @Override
    protected String getRootNodeDisplay()
    {
        return getResourceMap().getString("CategoryTree.rootNodeDisplay");
    }

    protected AssemblingRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getRemoteBean(this, AssemblingRemote.class);
        }

        return formSession;
    }

}
