/*
 * BaseTreePanel.java
 *
 * Created on 31 May 2008, 15:49
 */

package com.cosmos.acacia.gui;

import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBTree;
import com.cosmos.swingb.listeners.TableModificationListener;
import com.cosmos.util.Lister;

/**
 * A base panel for all tree panels.
 *
 * @author  Bozhidar Bozhanov
 */
public abstract class BaseTreePanel<E extends DataObjectBean> extends AcaciaPanel {

    /** Creates new form BaseTreePanel */
    public BaseTreePanel(DataObject parentDataObject) {
        super(parentDataObject);
        initComponents();
        initData();
        initComponentsCustom();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new com.cosmos.swingb.JBSplitPane();
        simpleListPanel = new com.cosmos.swingb.JBPanel();
        mainPanel = new com.cosmos.swingb.JBPanel();
        showAllHeirsCheck = new com.cosmos.swingb.JBCheckBox();
        jBScrollPane1 = new com.cosmos.swingb.JBScrollPane();
        treeScrollPane = new javax.swing.JScrollPane();
        tree = new com.cosmos.swingb.JBTree();

        setName("Form"); // NOI18N

        splitPane.setDividerLocation(240);
        splitPane.setContinuousLayout(true);
        splitPane.setMaximumSize(null);
        splitPane.setName("splitPane"); // NOI18N
        splitPane.setPreferredSize(new java.awt.Dimension(700, 3));

        simpleListPanel.setName("simpleListPanel"); // NOI18N
        simpleListPanel.setPreferredSize(new java.awt.Dimension(664, 0));

        javax.swing.GroupLayout simpleListPanelLayout = new javax.swing.GroupLayout(simpleListPanel);
        simpleListPanel.setLayout(simpleListPanelLayout);
        simpleListPanelLayout.setHorizontalGroup(
            simpleListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        simpleListPanelLayout.setVerticalGroup(
            simpleListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );

        splitPane.setRightComponent(simpleListPanel);

        mainPanel.setName("mainPanel"); // NOI18N

        showAllHeirsCheck.setMnemonic('S');
        showAllHeirsCheck.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(BaseTreePanel.class);
        showAllHeirsCheck.setText(resourceMap.getString("showAllHeirsCheck.text")); // NOI18N
        showAllHeirsCheck.setName("showAllHeirsCheck"); // NOI18N

        jBScrollPane1.setBorder(null);
        jBScrollPane1.setName("jBScrollPane1"); // NOI18N

        treeScrollPane.setName("treeScrollPane"); // NOI18N

        tree.setBackground(resourceMap.getColor("tree.background")); // NOI18N
        tree.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 4));
        tree.setToolTipText(resourceMap.getString("tree.toolTipText")); // NOI18N
        tree.setAutoscrolls(true);
        tree.setClosedIcon(resourceMap.getIcon("tree.closedIcon")); // NOI18N
        tree.setDragEnabled(true);
        tree.setLeafIcon(resourceMap.getIcon("tree.leafIcon")); // NOI18N
        tree.setMaximumSize(new java.awt.Dimension(30000, 30000));
        tree.setMinimumSize(null);
        tree.setName("tree"); // NOI18N
        tree.setOpenIcon(resourceMap.getIcon("tree.openIcon")); // NOI18N
        tree.setPreferredSize(new java.awt.Dimension(0, 0));
        tree.setVisibleRowCount(0);
        treeScrollPane.setViewportView(tree);

        jBScrollPane1.setViewportView(treeScrollPane);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(showAllHeirsCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
            .addComponent(jBScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showAllHeirsCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
        );

        splitPane.setLeftComponent(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBScrollPane jBScrollPane1;
    private com.cosmos.swingb.JBPanel mainPanel;
    private com.cosmos.swingb.JBCheckBox showAllHeirsCheck;
    private com.cosmos.swingb.JBPanel simpleListPanel;
    private com.cosmos.swingb.JBSplitPane splitPane;
    private com.cosmos.swingb.JBTree tree;
    private javax.swing.JScrollPane treeScrollPane;
    // End of variables declaration//GEN-END:variables

    public TreePath contextMenuTreePath;

    protected boolean modificationsEnabled = true;
    protected AcaciaToStringConverter toStringConverter;

    @Override
    protected abstract void initData();

    private List<E> currentTableItems;

    public abstract AbstractTreeEnabledTablePanel<E> getListPanel();

    public abstract E getParent(E child);

    public abstract void setParent(E entity, E parent);

    /**
     * Tries to update the model by calling the business logic.
     * On fail - shows error message to the user, and returns null.
     * On success returns the updated child
     *
     * @param newParent
     * @param newChildren
     * @return
     */
    protected abstract E updateParent(E newParent, E newChild);

    protected abstract String getRootNodeDisplay();

    protected abstract void onTableRefreshed();

    protected void initComponentsCustom() {
        
        //the list panel will benefit from the tree component because of the
        //possibility to easy lookup the children for a given node
        getListPanel().setTree(getTree());

        //let all 'list' queries are got from us
        getListPanel().setLister(getTreeLister());

        getListPanel().addTablePanelListener(new TablePanelListener() {
            @Override
            public void tablePanelClose() {
                onTablePanelClose();
            }

            @Override
            public void selectAction() {
                onTableSelectAction();
            }

            @Override
            public void tableRefreshed() {
                onTableRefreshed();
            }

            @Override
            public void selectionRowChanged() {
                //nothing to do on this event
            }
        });
        getListPanel().addTableModificationListener(new TableModificationListener() {
            @Override
            public void rowModified(Object row) {
                onRowModified(row);
            }
            @Override
            public void rowDeleted(Object row) {
                onRowDeleted(row);
            }
            @Override
            public void rowAdded(Object row) {
                onRowAdded(row);
            }
        });

        //stupid ha ? - well after 30 minutes of wondering...
        getTree().setOverwriteRendererIcons(true);

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(simpleListPanel);
        simpleListPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(getListPanel(), javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(getListPanel(), javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );


        getListPanel().getDataTable().packAll();

        getTree().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                //if no selection - disable the new button
                if ( e.getNewLeadSelectionPath()==null )
                    getListPanel().setVisible(Button.New, false);
                else if ( modificationsEnabled )
                    getListPanel().setVisible(Button.New, true);
                refreshTableItems();
            }

        });
        getTree().getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        if ( modificationsEnabled ){
            getTree().setDragEnabled(true);
            getTree().setDropMode(DropMode.ON);
            DropTarget dropTarget = new DropTarget();
            try{
                dropTarget.addDropTargetListener(getTreeDropTargetListener());
            }catch ( TooManyListenersException e ){
                e.printStackTrace();
            }
            getTree().setDropTarget(dropTarget);

            getShowAllHeirsCheckBox().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    refreshTableItems();
                }
            });
        }
    }


    public void setModificationsEnabled(boolean enabled) {
        modificationsEnabled = enabled;
        getListPanel().setVisible(Button.Delete, enabled);
        getListPanel().setVisible(Button.Modify, enabled);
        getListPanel().setVisible(Button.New, enabled);
        tree.setDragEnabled(enabled);
    }


    /**
     * Find the node model corresponding of the given cat.
     * If not found - creates new instance.
     * If the cat is null, the root node model is assumed and returned.
     * @param cat
     * @param added
     * @param root
     * @return - found node, new created node, or passed root node.
     */
    protected DefaultMutableTreeNode findOrCreateNode(E item,
                                           java.util.Map<E, DefaultMutableTreeNode> added,
                                           DefaultMutableTreeNode root) {
        //assuming root if null
        if ( item==null ){
            return root;
        }
        //find node
        DefaultMutableTreeNode result = added.get(item);
        //create new node
        if ( result==null ){
            result = new DefaultMutableTreeNode(item);
            added.put(item, result);
        }
        return result;
    }

    protected JBTree getTree() {
        return tree;
    }

    protected JBCheckBox getShowAllHeirsCheckBox(){
        return showAllHeirsCheck;
    }

    protected TreeLister getTreeLister() {
        return new TreeLister();
    }

    protected class TreeLister implements Lister<E>{

        @Override
        public List<E> getList() {
            return currentTableItems;
        }
    }

    protected void refreshTableItems() {
        boolean showAllHeirs = getShowAllHeirsCheckBox().isSelected();
        refreshTableItems(showAllHeirs);
    }

    @SuppressWarnings("unchecked")
    private void refreshTableItems(boolean showAllHeirs) {
        currentTableItems = new ArrayList<E>();
        TreePath selectedPath = getTree().getSelectionPath();
        if ( selectedPath!=null ){
            DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            if ( showAllHeirs ){
                Enumeration<DefaultMutableTreeNode> e = selNode.breadthFirstEnumeration();
                if ( e.hasMoreElements() ){
                    //skip the current
                    e.nextElement();
                    while (e.hasMoreElements()){
                        DefaultMutableTreeNode cur = e.nextElement();
                        currentTableItems.add((E) cur.getUserObject());
                    }
                }
            }else{
                for (int i = 0; i < selNode.getChildCount(); i++) {
                    DefaultMutableTreeNode cur = (DefaultMutableTreeNode) selNode.getChildAt(i);
                    currentTableItems.add((E) cur.getUserObject());
                }
            }
        }

        //refresh the table - this will lead to Lister.getList(), which is
        //actually provided by us
        getListPanel().refreshDataTable();
    }


    protected void onTablePanelClose() {
        this.close();
    }

    protected void onRowAdded(Object row) {
        E added = (E) row;
        DefaultMutableTreeNode addedNode = new DefaultMutableTreeNode(added);

        DefaultMutableTreeNode parentNode = null;
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getTree().getModel().getRoot();
        E parent = getParent(added);
        if ( parent==null )
            parentNode = root;
        else{
            parentNode = (DefaultMutableTreeNode) findTreeNodeForUserObject(root, parent);
        }

        DefaultTreeModel m = (DefaultTreeModel) getTree().getModel();

        int idx = findAppropriateIndex(parentNode, addedNode);
        m.insertNodeInto(addedNode, parentNode, idx);

        refreshTableItems();
    }


    protected void onRowDeleted(Object row) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getTree().getModel().getRoot();
        DefaultMutableTreeNode deletedNode = (DefaultMutableTreeNode)
            findTreeNodeForUserObject(root, (E) row);

        DefaultTreeModel m = (DefaultTreeModel) getTree().getModel();
        m.removeNodeFromParent(deletedNode);
    }

    @SuppressWarnings({ "cast", "unchecked" })
    protected void onRowModified(Object row) {
        E modified = (E) row;

        DefaultMutableTreeNode modifiedNode = (DefaultMutableTreeNode)
            findTreeNodeForUserObject(
                (DefaultMutableTreeNode)getTree().getModel().getRoot(),
            modified);

        DefaultTreeModel treeModel = (DefaultTreeModel) getTree().getModel();
        TreePath rowNodePath = new TreePath(
            ((DefaultMutableTreeNode)modifiedNode).getPath());

        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) modifiedNode.getParent();


        E parentInTree = null;
        try {
            parentInTree = (E) parentNode.getUserObject();
        } catch (ClassCastException ex){

        }

        boolean parentsChanged = true;
        //before null, and now null
        if ( parentInTree==null && getParent(modified)==null ){
            parentsChanged = false;
        } else if ( parentInTree!=null && parentInTree.equals(getParent(modified)) ){
            parentsChanged = false;
        } else if ( getParent(modified) != null && getParent(modified).equals(parentInTree)){
            parentsChanged = false;
        }

        //update the tree item representation
        treeModel.valueForPathChanged(rowNodePath, row);
        //also update all child user objects - setting the updated parent
        for (int i = 0; i < modifiedNode.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) modifiedNode.getChildAt(i);
            E entity = (E) child.getUserObject();
            setParent(entity, modified);
        }

        //if the parents were changed - update the tree accordingly
        if ( parentsChanged ){
            DefaultMutableTreeNode newParent = null;
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
            if (getParent(modified) == null)
                newParent = root;
            else
                newParent = (DefaultMutableTreeNode)
                findTreeNodeForUserObject(root, getParent(modified));
            updateNodeParent(newParent, modifiedNode);
        }

        //at last refresh the table, but keep the selection
        refreshTableItems();
        getListPanel().getDataTable().setSelectedRowObject(row);
    }

    protected void onTableSelectAction() {
        setDialogResponse(DialogResponse.SELECT);
        this.close();
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

    protected void dragNDropImpossible(String message) {
        JOptionPane.showConfirmDialog(
            this.getParent(),
            message,
            getResourceMap().getString("Tree.err.invalidDragNDrop"), JOptionPane.DEFAULT_OPTION);
    }

    protected void handleException(Exception e){
        ValidationException ve = extractValidationException(e);
        if ( ve!=null ){
            String validationMsg = getValidationErrorsMessage(ve);
            dragNDropImpossible(validationMsg);
        } else
            dragNDropImpossible(getResourceMap().getString("Tree.err.cantSetParents"));
    }


    protected int findAppropriateIndex(DefaultMutableTreeNode newParent,
            DefaultMutableTreeNode newChild) {
        ProductCategory child = (ProductCategory) newChild.getUserObject();

        String childStr = toStringConverter.getPreferredStringForItem(child);

        for (int i = 0; i < newParent.getChildCount(); i++) {
            DefaultMutableTreeNode curChildNode = (DefaultMutableTreeNode) newParent.getChildAt(i);
            ProductCategory curChild = (ProductCategory) curChildNode.getUserObject();

            String curChildStr = toStringConverter.getPreferredStringForItem(curChild);

            if ( childStr.compareTo(curChildStr)<=0 )
                return i;
        }

        return newParent.getChildCount();
    }

    public boolean showDragNDropWarning() {
        int option =
        JOptionPane.showConfirmDialog(this,
            getResourceMap().getString("Tree.DnDWarning"),
            getResourceMap().getString("Tree.DnDWarningTitle"),
            JOptionPane.YES_NO_OPTION);
        if ( option==JOptionPane.YES_OPTION ){
            return true;
        }
        return false;
    }

    protected void updateNodeParent(DefaultMutableTreeNode newParent, DefaultMutableTreeNode newChild) {
        DefaultTreeModel m = (DefaultTreeModel) getTree().getModel();
        m.removeNodeFromParent(newChild);

        int idx = findAppropriateIndex(newParent, newChild);
        m.insertNodeInto(newChild, newParent, idx);
    }

    protected void addItemsToTree(DefaultMutableTreeNode root, List<E> items) {

        Map<E, DefaultMutableTreeNode> added = new HashMap <E, DefaultMutableTreeNode>();

        for (E item : items) {
            E parent = getParent(item);
            DefaultMutableTreeNode catNode = findOrCreateNode(item, added, root);
            DefaultMutableTreeNode parentNode = findOrCreateNode(parent, added, root);

            int idx = findAppropriateIndex(parentNode, catNode);
            parentNode.insert(catNode, idx);
        }
    }

    protected class TreeDropTargetListener extends DropTargetAdapter {
        @Override
        public void drop(DropTargetDropEvent dtde) {

            DefaultMutableTreeNode targetNode = getNodeForEvent(dtde);
            DefaultMutableTreeNode sourceNode = getSourceNode();

            //ignore if dropped over itself
            if ( targetNode!=null && targetNode.equals(sourceNode))
                return;

            if ( targetNode.equals(sourceNode.getParent())) {
                dragNDropImpossible(getResourceMap().getString("Tree.err.alreadyChild"));
                return;
            }

            E newChild = null;
            //children ok
            try {
                newChild = (E) sourceNode.getUserObject();
            //invalid children found
            } catch (ClassCastException ex){
                dragNDropImpossible(getResourceMap().getString("Tree.err.invalidSourceObject"));
                return;
            }

            //check for parent-child cycle
            TreeNode ancestor = targetNode;
            while ( ancestor!=null ){
                if ( ancestor.equals(sourceNode) ){
                    dragNDropImpossible(getResourceMap().getString("TreeItem.err.cyclicParent"));
                    return;
                }
                ancestor = ancestor.getParent();
            }

            E updatedChild = null;

            //dropped over the root
            if ( getTree().getModel().getRoot().equals(targetNode) ){
                if ( !showDragNDropWarning() )
                    return;
                updatedChild = updateParent(null, newChild);
            }
            //dropped on some other valid item
            else {
                try {
                    if ( !showDragNDropWarning() )
                        return;
                    E newParent = (E) targetNode.getUserObject();
                    updatedChild = updateParent(newParent, newChild);
            //dropped over invalid item
                } catch (ClassCastException ex){
                    dragNDropImpossible(getResourceMap().getString("Tree.err.invalidDnDTargetNode"));
                }
            }

            //if everything is ok, update the tree model
            if ( updatedChild!=null ){
                //update the child user object
                TreePath sourcePath = getNodePath(sourceNode);
                getTree().getModel().valueForPathChanged(sourcePath, updatedChild);

                //update the tree parent-child structure
                updateNodeParent(targetNode, sourceNode);
                getTree().setSelectionPath(getNodePath(targetNode));

                //
                //categoryListPanel.getDataTable().up
            }
        }

        private DefaultMutableTreeNode getSourceNode() {
            return (DefaultMutableTreeNode)
            getTree().getSelectionPath().getLastPathComponent();
        }

        private DefaultMutableTreeNode getNodeForEvent(DropTargetDropEvent dtde) {
            Point p = dtde.getLocation();
            DropTargetContext dtc = dtde.getDropTargetContext();
            JTree tree = (JTree) dtc.getComponent();
            TreePath path = tree.getClosestPathForLocation(p.x, p.y);
            return (DefaultMutableTreeNode) path.getLastPathComponent();
        }
    }

    protected TreeDropTargetListener getTreeDropTargetListener() {
        return new TreeDropTargetListener();
    }


    protected DefaultMutableTreeNode getRootHode(JBTree t){
        DefaultMutableTreeNode result =
            (DefaultMutableTreeNode) t.getModel().getRoot();
        return result;
    }

    protected TreePath getNodePath(TreeNode t){
        DefaultMutableTreeNode r =
            (DefaultMutableTreeNode) t;
        return new TreePath(r.getPath());
    }

    protected void refreshTreeModel(List<E> list) {
        //make tree model from the list of categories
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
            getRootNodeDisplay());
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        getTree().setModel(treeModel);
        addItemsToTree(root, list);

        getTree().enableToStringCellRenderer();
        getTree().expandPath(new TreePath(root.getPath()));
        getTree().setSelectionPath(
            getNodePath(getRootHode(getTree())));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected Class getResourceStopClass() {
        return BaseTreePanel.class;
    }
    
}
