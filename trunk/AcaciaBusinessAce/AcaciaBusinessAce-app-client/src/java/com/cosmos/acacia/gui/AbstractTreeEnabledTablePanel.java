package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DataObjectBean;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.cosmos.swingb.JBTree;
import com.cosmos.util.Lister;
import java.math.BigInteger;

/**
 * Base panel for table panels that will have associated trees
 *
 * @author Bozhidar Bozhanov
 *
 * @param <E> the entity this form will be used with
 */
public abstract class AbstractTreeEnabledTablePanel<E>
    extends AbstractTablePanel
{

    public AbstractTreeEnabledTablePanel(DataObjectBean dataObjectBean)
    {
        super(dataObjectBean);
    }

    public AbstractTreeEnabledTablePanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
    }


    @Override
    protected void initData() {
        super.initData();
        lister = new Lister<E>() {
            @Override
            public List<E> getList() {
                return getItems();
            }
        };
    }

    protected abstract List<E> getItems();

    protected abstract Object onEditEntity(E entity);

    public abstract void refreshDataTable();
    
    private JBTree tree;

    private Lister<E> lister;


    /**
     * This panel may be used in the context of tree control.
     * Set it up in order to be used when the hierarchy is needed.
     * Getter for tree
     * @return JBTree
     */
    public JBTree getTree() {
        return tree;
    }

    /**
     * Setter for tree
     * @param tree - JBTree
     */
    public void setTree(JBTree tree) {
        this.tree = tree;
    }

    /**
     * Getter for lister
     * @return Lister<E>
     */
    public Lister<E> getLister() {
        return lister;
    }

    /**
     * Setter for Lister
     * @param Lister - Lister<E>
     */
    public void setLister(Lister<E> lister) {
        this.lister = lister;
    }

    @SuppressWarnings("unchecked")
    protected List<E> getWithSubCategories(E rowObject) {
        List<E> result = new ArrayList<E>();
        if (getTree() != null){
            DefaultTreeModel m = (DefaultTreeModel) getTree().getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) m.getRoot();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) findTreeNodeForUserObject(root, rowObject);
            Enumeration<DefaultMutableTreeNode> nodes = node.depthFirstEnumeration();
            while ( nodes.hasMoreElements() ){
                DefaultMutableTreeNode next = nodes.nextElement();
                E cat = (E) next.getUserObject();
                result.add(cat);
            }
        }else{
            result.add(rowObject);
        }
        return result;
    }

    protected TreeNode findTreeNodeForUserObject(DefaultMutableTreeNode current, E searched) {
        if ( searched.equals(current.getUserObject()) )
                return current;
        for (int i = 0; i < current.getChildCount(); i++) {
            TreeNode c = current.getChildAt(i);
            TreeNode r = findTreeNodeForUserObject((DefaultMutableTreeNode) c, searched);
            if ( r!=null )
                return r;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object modifyRow(Object rowObject) {
        E entity = (E) rowObject;
        return onEditEntity(entity);
    }
    
    
     /**
     * Forms the error message shown when constraint violation occurs
     *
     * @param the name of the table
     * @return the message
     */
    protected String getTableReferencedMessage(String cantDeleteMessagePrefix, String table)
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
    
    
    protected void removeFromTable(List<E> withSubChildren) {
        for (E child : withSubChildren) {
            getDataTable().removeRow(child);
        }
    }

}
