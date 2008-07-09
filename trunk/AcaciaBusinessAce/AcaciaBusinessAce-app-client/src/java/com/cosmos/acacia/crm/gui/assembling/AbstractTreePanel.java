/*
 * AbstractTreePanel.java
 *
 * Created on Петък, 2008, Юли 4, 10:08
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.tree.AbstractDynamicTreeNode;
import com.cosmos.swingb.tree.DynamicTreeModel;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author  Miro
 */
public abstract class AbstractTreePanel<E extends DataObjectBean>
    extends AcaciaPanel
{

    /** Creates new form AbstractTreePanel */
    public AbstractTreePanel(E dataObjectBean)
    {
        super(dataObjectBean);
        init();
    }

    private void init()
    {
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

        treeSplitPane = new com.cosmos.swingb.JBSplitPane();
        treePanel = new javax.swing.JPanel();
        showAllHeirsCheckBox = new javax.swing.JCheckBox();
        treeScrollPane = new javax.swing.JScrollPane();
        tree = new com.cosmos.swingb.JBTree();
        customPanel = new javax.swing.JPanel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        treeSplitPane.setDividerLocation(200);
        treeSplitPane.setName("treeSplitPane"); // NOI18N

        treePanel.setName("treePanel"); // NOI18N
        treePanel.setLayout(new java.awt.BorderLayout());

        showAllHeirsCheckBox.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AbstractTreePanel.class);
        showAllHeirsCheckBox.setText(resourceMap.getString("showAllHeirsCheckBox.text")); // NOI18N
        showAllHeirsCheckBox.setName("showAllHeirsCheckBox"); // NOI18N
        treePanel.add(showAllHeirsCheckBox, java.awt.BorderLayout.PAGE_START);

        treeScrollPane.setName("treeScrollPane"); // NOI18N

        tree.setName("tree"); // NOI18N
        treeScrollPane.setViewportView(tree);

        treePanel.add(treeScrollPane, java.awt.BorderLayout.CENTER);

        treeSplitPane.setLeftComponent(treePanel);

        customPanel.setName("customPanel"); // NOI18N
        customPanel.setLayout(new java.awt.BorderLayout());
        treeSplitPane.setRightComponent(customPanel);

        add(treeSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel customPanel;
    private javax.swing.JCheckBox showAllHeirsCheckBox;
    private com.cosmos.swingb.JBTree tree;
    private javax.swing.JPanel treePanel;
    private javax.swing.JScrollPane treeScrollPane;
    private com.cosmos.swingb.JBSplitPane treeSplitPane;
    // End of variables declaration//GEN-END:variables


    private DynamicTreeNode rootNode;

    protected abstract List<E> getChildren(E parent);
    protected abstract int getChildCount(E parent);

    @Override
    protected void initData()
    {
        showAllHeirsCheckBox.setSelected(true);
        
        DynamicTreeModel treeModel = new DynamicTreeModel(getRootNode());
        tree.setModel(treeModel);
        tree.setSelectionRow(0);

        tree.addTreeSelectionListener(new TreeSelectionListener()
        {
            @Override
            public void valueChanged(TreeSelectionEvent event)
            {
                AbstractTreePanel.this.treeValueChanged(getSelectionNode());
            }
        });

        showAllHeirsCheckBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent event)
            {
                AbstractTreePanel.this.itemStateChanged(getSelectionNode());
            }
        });
    }

    protected void setComponent(JComponent component)
    {
        customPanel.add(component, BorderLayout.CENTER);
    }

    public boolean isShowAllHeirs()
    {
        
        return showAllHeirsCheckBox.isSelected();
    }

    public void setShowAllHeirs(boolean showAllHeirs)
    {
        showAllHeirsCheckBox.setSelected(showAllHeirs);
    }

    public TreePath getSelectionPath()
    {
        return tree.getSelectionPath();
    }

    public TreeNode getSelectionNode()
    {
        TreePath path = getSelectionPath();
        if(path != null)
            return (TreeNode)path.getLastPathComponent();

        return null;
    }

    public E getSelectionDataObjectBean()
    {
        return getDataObjectBean((AbstractDynamicTreeNode)getSelectionNode());
    }

    protected void treeValueChanged(TreeNode treeNode)
    {
    }

    protected void itemStateChanged(TreeNode treeNode)
    {
        treeValueChanged(treeNode);
    }

    protected void fireNodeStructureChanged(TreeNode treeNode)
    {
        if(treeNode != null)
            ((DefaultTreeModel)tree.getModel()).nodeStructureChanged(treeNode);
        else
            ((DefaultTreeModel)tree.getModel()).nodeStructureChanged(getRootNode());
    }

    protected AbstractDynamicTreeNode getRootNode()
    {
        if(rootNode == null)
        {
            rootNode = new DynamicTreeNode("Root");
            rootNode.setNotifyOnStructureChanged(false);
        }

        return rootNode;
    }

    protected E getDataObjectBean(AbstractDynamicTreeNode node)
    {
        if(node == null || node.isRoot())
            return null;
        else
            return (E)node.getUserObject();
    }


    private class DynamicTreeNode
        extends AbstractDynamicTreeNode
    {
        public DynamicTreeNode(String rootNodeName)
        {
            setUserObject(rootNodeName);
        }

        public DynamicTreeNode(E dataObjectBean)
        {
            setUserObject(dataObjectBean);
        }

        @Override
        public void loadChildren()
        {
            if(!areChildrenLoaded)
            {
                E dataObject;
                if(isRoot())
                    dataObject = null;
                else
                    dataObject = (E)getUserObject();
                List<E> childDataObjects = AbstractTreePanel.this.getChildren(dataObject);
                if(childDataObjects != null && childDataObjects.size() > 0)
                {
                    for(E child : childDataObjects)
                    {
                        DynamicTreeNode treeNode = new DynamicTreeNode(child);
                        add(treeNode);
                    }
                }

                areChildrenLoaded = true;
            }
        }

        @Override
        protected int getChildrenCount()
        {
            if(isRoot())
                return AbstractTreePanel.this.getChildCount(null);
            else
                return AbstractTreePanel.this.getChildCount((E)getUserObject());
        }
    }
}
