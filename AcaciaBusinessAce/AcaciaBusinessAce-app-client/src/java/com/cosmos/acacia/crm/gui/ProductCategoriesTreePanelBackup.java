/*
 * ProductCategoriesPanel.java
 *
 * Created on Четвъртък, 2008, Февруари 21, 17:01
 */

package com.cosmos.acacia.crm.gui;

import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;

import javax.ejb.EJB;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.product.ProductCategory;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBTree;
import com.cosmos.swingb.listeners.TableModificationListener;
import com.cosmos.util.Lister;

/**
 *
 * @author  Miro
 */
public class ProductCategoriesTreePanelBackup extends AcaciaPanel {

    private ProductCategoryListPanel categoryListPanel;

    @EJB
    private ProductsListRemote formSession;

    public TreePath contextMenuTreePath;

//    private JBPopupMenu treeContextMenu;

    /** Creates new form ProductCategoriesPanel */
    public ProductCategoriesTreePanelBackup(UUID parentDataObjectId) {
        super(parentDataObjectId);
        initComponents();
        initComponentsCustom();
        initData();
    }

    private void initComponentsCustom() {
        categoryListPanel = new ProductCategoryListPanel(getParentDataObjectId(), true);
        categoryListPanel.addTablePanelListener(new TablePanelListener() {
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
        categoryListPanel.addTableModificationListener(new TableModificationListener() {
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
        categoryTree.setOverwriteRendererIcons(true);

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(categoryListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(categoryListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );

        //listPanel.setPreferredSize(categoryListPanel.getPreferredSize());
        //listPanel.setSize(categoryListPanel.getSize());

        categoryListPanel.getDataTable().packAll();

//        treeContextMenu = new JBPopupMenu();
//
//        ActionListener menuItemListener = new TreeActionListener();
//
//        newItem = new JMenuItem();
//        modifyItem = new JMenuItem();
//        deleteItem = new JMenuItem();
//        newItem.setName("newItem");
//        newItem.setText(getResourceMap().getString("Tree.action.new"));
//        newItem.addActionListener(menuItemListener);
//
//        modifyItem.setName("modifyItem");
//        modifyItem.setText(getResourceMap().getString("Tree.action.modify"));
//        modifyItem.addActionListener(menuItemListener);
//
//        deleteItem.setName("deleteItem");
//        deleteItem.setText(getResourceMap().getString("Tree.action.delete"));
//        deleteItem.addActionListener(menuItemListener);
//
//        treeContextMenu.add(newItem);
//        treeContextMenu.add(new Separator());
//        treeContextMenu.add(modifyItem);
//        treeContextMenu.add(deleteItem);
//
//        categoryTree.addMouseListener(new PopupTrigger());

        categoryTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                //if no selection - disable the new button
                if ( e.getNewLeadSelectionPath()==null )
                    categoryListPanel.setVisible(Button.New, false);
                else if ( modificationsEnabled )
                    categoryListPanel.setVisible(Button.New, true);
                refreshTableItems();
            }

//            @Override
//            public void valueChanged(TreeSelectionEvent e) {
//                //mark the current operation event
//                treeSelectionChanging = true;
//                //reset the selection
//                categoryListPanel.getDataTable().clearSelection();
//                //check if the operation is selection or unselection - so do nothing
//                if (e.getNewLeadSelectionPath()!=null ){
//                    //valid select operation - update the table
//                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
//
//                    if ( node.getUserObject() instanceof ProductCategory ){
//                        ProductCategory c = (ProductCategory) node.getUserObject();
//                        categoryListPanel.getDataTable().setSelectedRowObject(c);
//                    }
//                }
//                //unmark the current operation
//                treeSelectionChanging = false;
//            }
        });
        categoryTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        if ( modificationsEnabled ){
            categoryTree.setDragEnabled(true);
            categoryTree.setDropMode(DropMode.ON);
            DropTarget dropTarget = new DropTarget();
            try{
                dropTarget.addDropTargetListener(new TreeDropTargetListener());
            }catch ( TooManyListenersException e ){
                e.printStackTrace();
            }
            categoryTree.setDropTarget(dropTarget);

            showAllHeirsCheck.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    refreshTableItems();
                }
            });
        }
    }

    protected void refreshTableItems() {
        boolean showAllHeirs = showAllHeirsCheck.isSelected();
        refreshTableItems(showAllHeirs);
    }

    @SuppressWarnings("unchecked")
    private void refreshTableItems(boolean showAllHeirs) {
        currentTableItems = new ArrayList<ProductCategory>();
        TreePath selectedPath = categoryTree.getSelectionPath();
        if ( selectedPath!=null ){
            DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            if ( showAllHeirs ){
                Enumeration<DefaultMutableTreeNode> e = selNode.breadthFirstEnumeration();
                if ( e.hasMoreElements() ){
                    //skip the current
                    e.nextElement();
                    while (e.hasMoreElements()){
                        DefaultMutableTreeNode cur = e.nextElement();
                        currentTableItems.add((ProductCategory) cur.getUserObject());
                    }
                }
            }else{
                for (int i = 0; i < selNode.getChildCount(); i++) {
                    DefaultMutableTreeNode cur = (DefaultMutableTreeNode) selNode.getChildAt(i);
                    currentTableItems.add((ProductCategory) cur.getUserObject());
                }
            }
        }

        //refresh the table - this will lead to Lister.getList(), which is
        //actually provided by us
        categoryListPanel.refreshDataTable();
    }

    private DefaultMutableTreeNode getRootHode(JBTree t){
        DefaultMutableTreeNode result =
            (DefaultMutableTreeNode) t.getModel().getRoot();
        return result;
    }

    private TreePath getNodePath(TreeNode t){
        DefaultMutableTreeNode r =
            (DefaultMutableTreeNode) t;
        return new TreePath(r.getPath());
    }

    protected void onTableRefreshed() {
        //load all categories and refresh the tree
        List<ProductCategory> categories = getFormSession().getProductsCategories(null);

        refreshTreeModel(categories);
    }

    protected void onTableSelectAction() {
        setDialogResponse(DialogResponse.SELECT);
        this.close();
    }

//    class TreeActionListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JMenuItem item = (JMenuItem) e.getSource();
//            if ( item.getName().equals("newItem") )
//                categoryListPanel.newAction();
//            else if ( item.getName().equals("modifyItem") )
//                categoryListPanel.modifyAction();
//            else if ( item.getName().equals("deleteItem") )
//                categoryListPanel.deleteAction();
//        }
//    }
//
//    class PopupTrigger extends MouseAdapter {
//        public void mouseReleased(MouseEvent e) {
//            if (e.isPopupTrigger()) {
//                int x = e.getX();
//                int y = e.getY();
//                TreePath path = categoryTree.getPathForLocation(x, y);
//
//                if (path != null) {
//                    //don't accept the root
//                    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) categoryTree.getModel().getRoot();
//                    if ( path.equals(new TreePath(rootNode.getPath())) ){
//                        modifyItem.setEnabled(false);
//                        deleteItem.setEnabled(false);
//                    }else{
//                        modifyItem.setEnabled(modificationsEnabled);
//                        deleteItem.setEnabled(modificationsEnabled);
//                    }
//                    newItem.setEnabled(modificationsEnabled);
//
//                    categoryTree.setSelectionPath(path);
//                    treeContextMenu.show(categoryTree, x, y);
//                    contextMenuTreePath = path;
//                }
//            }
//        }
//    }
//
    protected void onRowAdded(Object row) {
        ProductCategory added = (ProductCategory) row;
        DefaultMutableTreeNode addedNode = new DefaultMutableTreeNode(added);

        DefaultMutableTreeNode parentNode = null;
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) categoryTree.getModel().getRoot();
        ProductCategory parent = added.getParentCategory();
        if ( parent==null )
            parentNode = root;
        else{
            parentNode = (DefaultMutableTreeNode) findTreeNodeForUserObject(root, parent);
        }

        DefaultTreeModel m = (DefaultTreeModel) categoryTree.getModel();

        int idx = findAppropriateIndex(parentNode, addedNode);
        m.insertNodeInto(addedNode, parentNode, idx);

        refreshTableItems();

        //categoryTree.expandPath(new TreePath(parentNode.getPath()));
    }

    protected void onRowDeleted(Object row) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) categoryTree.getModel().getRoot();
        DefaultMutableTreeNode deletedNode = (DefaultMutableTreeNode)
            findTreeNodeForUserObject(root, (ProductCategory) row);

        DefaultTreeModel m = (DefaultTreeModel) categoryTree.getModel();
        m.removeNodeFromParent(deletedNode);
    }

    protected void onRowModified(Object row) {
        ProductCategory modified = (ProductCategory) row;

        DefaultMutableTreeNode modifiedNode = (DefaultMutableTreeNode)
            findTreeNodeForUserObject(
                (DefaultMutableTreeNode)categoryTree.getModel().getRoot(),
            modified);

        DefaultTreeModel treeModel = (DefaultTreeModel) categoryTree.getModel();
        TreePath rowNodePath = new TreePath(
            ((DefaultMutableTreeNode)modifiedNode).getPath());

        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) modifiedNode.getParent();

        ProductCategory parentCategoryInTree = null;
        if ( parentNode.getUserObject() instanceof ProductCategory ){
            parentCategoryInTree = (ProductCategory) parentNode.getUserObject();
        }

        boolean parentsChanged = true;
        //before null, and now null
        if ( parentCategoryInTree==null && modified.getParentCategory()==null ){
            parentsChanged = false;
        }else if ( parentCategoryInTree!=null && parentCategoryInTree.equals(modified.getParentCategory()) ){
            parentsChanged = false;
        }else if ( modified.getParentCategory()!=null && modified.getParentCategory().equals(parentCategoryInTree)){
            parentsChanged = false;
        }

        //update the tree item representation
        treeModel.valueForPathChanged(rowNodePath, row);
        //also update all child user objects - setting the updated parent
        for (int i = 0; i < modifiedNode.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) modifiedNode.getChildAt(i);
            ProductCategory cat = (ProductCategory) child.getUserObject();
            cat.setParentCategory(modified);
        }

        //if the parents were changed - update the tree accordingly
        if ( parentsChanged ){
            DefaultMutableTreeNode newParent = null;
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
            if ( modified.getParentCategory()==null )
                newParent = root;
            else
                newParent = (DefaultMutableTreeNode)
                findTreeNodeForUserObject(root, modified.getParentCategory());
            updateNodeParent(newParent, modifiedNode);
        }

        //at last refresh the table, but keep the selection
        refreshTableItems();
        categoryListPanel.getDataTable().setSelectedRowObject(row);
    }

    private class TreeDropTargetListener extends DropTargetAdapter{
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

            ProductCategory newChild = null;
            //children ok
            if ( sourceNode.getUserObject() instanceof ProductCategory ){
                newChild = (ProductCategory) sourceNode.getUserObject();
            //invalid children found
            }else{
                dragNDropImpossible(getResourceMap().getString("Tree.err.invalidSourceObject"));
                return;
            }

            //check for parent-child cycle
            TreeNode ancestor = targetNode;
            while ( ancestor!=null ){
                if ( ancestor.equals(sourceNode) ){
                    dragNDropImpossible(getResourceMap().getString("ProductCategory.err.cyclicParent"));
                    return;
                }
                ancestor = ancestor.getParent();
            }

            ProductCategory updatedChild = null;

            //dropped over the root
            if ( categoryTree.getModel().getRoot().equals(targetNode) ){
                if ( !showDragNDropWarning() )
                    return;
                updatedChild = updateParent(null, newChild);
            }
            //dropped on some other valid item
            else if ( targetNode.getUserObject() instanceof ProductCategory ){
                if ( !showDragNDropWarning() )
                    return;
                ProductCategory newParent = (ProductCategory) targetNode.getUserObject();
                updatedChild = updateParent(newParent, newChild);
            //dropped over invalid item
            }else{
                dragNDropImpossible(getResourceMap().getString("Tree.err.invalidDnDTargetNode"));
            }

            //if everything is ok, update the tree model
            if ( updatedChild!=null ){
                //update the child user object
                TreePath sourcePath = getNodePath(sourceNode);
                categoryTree.getModel().valueForPathChanged(sourcePath, updatedChild);

                //update the tree parent-child structure
                updateNodeParent(targetNode, sourceNode);
                categoryTree.setSelectionPath(getNodePath(targetNode));

                //
                //categoryListPanel.getDataTable().up
            }
        }

        private DefaultMutableTreeNode getSourceNode() {
            return (DefaultMutableTreeNode)
            categoryTree.getSelectionPath().getLastPathComponent();
        }

        private DefaultMutableTreeNode getNodeForEvent(DropTargetDropEvent dtde) {
            Point p = dtde.getLocation();
            DropTargetContext dtc = dtde.getDropTargetContext();
            JTree tree = (JTree) dtc.getComponent();
            TreePath path = tree.getClosestPathForLocation(p.x, p.y);
            return (DefaultMutableTreeNode) path.getLastPathComponent();
        }
    }

    /**
     * Mark that three selection is initiated.
     * This way we will skip re-selection of the table.
     */
//    private boolean treeSelectionChanging = false;

    private AcaciaToStringConverter categoryToStringConverter;

//    protected void onTableSelectionChanged() {
//        if ( treeSelectionChanging )
//            return;
//        ProductCategory selected = (ProductCategory) categoryListPanel.getDataTable().getSelectedRowObject();
//        TreePath path = null;
//        if ( selected!=null ){
//            DefaultMutableTreeNode root =
//                (DefaultMutableTreeNode) categoryTree.getModel().getRoot();
//            DefaultMutableTreeNode node =
//                (DefaultMutableTreeNode) findTreeNodeForUserObject(root, selected);
//            if ( node!=null )
//                path = new TreePath(node.getPath());
//        }
//
//        if ( path!=null ){
//            categoryTree.getSelectionModel().setSelectionPath(path);
//        }else{
//            categoryTree.getSelectionModel().clearSelection();
//        }
//    }

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
        DefaultTreeModel m = (DefaultTreeModel) categoryTree.getModel();
        m.removeNodeFromParent(newChild);

        int idx = findAppropriateIndex(newParent, newChild);
        m.insertNodeInto(newChild, newParent, idx);

//        categoryTree.expandPath(new TreePath(newParent.getPath()));
    }

    private int findAppropriateIndex(DefaultMutableTreeNode newParent,
                                     DefaultMutableTreeNode newChild) {
        ProductCategory child = (ProductCategory) newChild.getUserObject();

        String childStr = categoryToStringConverter.getPreferredStringForItem(child);

        for (int i = 0; i < newParent.getChildCount(); i++) {
            DefaultMutableTreeNode curChildNode = (DefaultMutableTreeNode) newParent.getChildAt(i);
            ProductCategory curChild = (ProductCategory) curChildNode.getUserObject();

            String curChildStr = categoryToStringConverter.getPreferredStringForItem(curChild);

            if ( childStr.compareTo(curChildStr)<=0 )
                return i;
        }

        return newParent.getChildCount();
    }

    /**
     * Tries to update the model by calling the business logic.
     * On fail - shows error message to the user, and returns null.
     * On success returns the updated category (child)
     *
     * @param newParent
     * @param newChildren
     * @return true on success, false otherwise
     */
    protected ProductCategory updateParent(ProductCategory newParent, ProductCategory newChildren) {
        try{
            ProductCategory updatedCategory =
            getFormSession().updateParents(newParent, newChildren);

            //categoryListPanel.updateRowObject(updatedCategory);

            return updatedCategory;
        }catch ( Exception e ){
            ValidationException ve = extractValidationException(e);
            if ( ve!=null ){
                String validationMsg = getValidationErrorsMessage(ve);
                dragNDropImpossible(validationMsg);
            }else
                dragNDropImpossible(getResourceMap().getString("Tree.err.cantSetParents"));
            return null;
        }
    }

    public void dragNDropImpossible(String message) {
        JOptionPane.showConfirmDialog(
            this.getParent(),
            message,
            getResourceMap().getString("Tree.err.invalidDragNDrop"), JOptionPane.DEFAULT_OPTION);
    }

    private TreeNode findTreeNodeForUserObject(DefaultMutableTreeNode current, ProductCategory searched) {
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

    protected void onTablePanelClose() {
        this.close();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productCategoriesSplitPane = new com.cosmos.swingb.JBSplitPane();
        listPanel = new com.cosmos.swingb.JBPanel();
        jBPanel1 = new com.cosmos.swingb.JBPanel();
        showAllHeirsCheck = new com.cosmos.swingb.JBCheckBox();
        jBScrollPane1 = new com.cosmos.swingb.JBScrollPane();
        productCategoriesTreeScrollPane = new javax.swing.JScrollPane();
        categoryTree = new com.cosmos.swingb.JBTree();

        setMaximumSize(null);
        setMinimumSize(null);
        setName("Form"); // NOI18N
        setPreferredSize(null);

        productCategoriesSplitPane.setDividerLocation(240);
        productCategoriesSplitPane.setContinuousLayout(true);
        productCategoriesSplitPane.setMaximumSize(null);
        productCategoriesSplitPane.setMinimumSize(null);
        productCategoriesSplitPane.setName("productCategoriesSplitPane"); // NOI18N
        productCategoriesSplitPane.setPreferredSize(null);

        listPanel.setName("listPanel"); // NOI18N

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 423, Short.MAX_VALUE)
        );

        productCategoriesSplitPane.setRightComponent(listPanel);

        jBPanel1.setName("jBPanel1"); // NOI18N

        showAllHeirsCheck.setMnemonic('S');
        showAllHeirsCheck.setSelected(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductCategoriesTreePanelBackup.class);
        showAllHeirsCheck.setText(resourceMap.getString("showAllHeirsCheck.text")); // NOI18N
        showAllHeirsCheck.setName("showAllHeirsCheck"); // NOI18N

        jBScrollPane1.setBorder(null);
        jBScrollPane1.setName("jBScrollPane1"); // NOI18N

        productCategoriesTreeScrollPane.setName("productCategoriesTreeScrollPane"); // NOI18N

        categoryTree.setBackground(resourceMap.getColor("categoryTree.background")); // NOI18N
        categoryTree.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 4, 4));
        categoryTree.setToolTipText(resourceMap.getString("categoryTree.toolTipText")); // NOI18N
        categoryTree.setAutoscrolls(true);
        categoryTree.setClosedIcon(resourceMap.getIcon("categoryTree.closedIcon")); // NOI18N
        categoryTree.setDragEnabled(true);
        categoryTree.setLeafIcon(resourceMap.getIcon("categoryTree.leafIcon")); // NOI18N
        categoryTree.setMaximumSize(new java.awt.Dimension(30000, 30000));
        categoryTree.setMinimumSize(null);
        categoryTree.setName("categoryTree"); // NOI18N
        categoryTree.setOpenIcon(resourceMap.getIcon("categoryTree.openIcon")); // NOI18N
        categoryTree.setPreferredSize(null);
        categoryTree.setVisibleRowCount(0);

        //productCategoriesTreeScrollPane.setViewportView(categoryTree);
        //jBScrollPane1.setViewportView(productCategoriesTreeScrollPane);
        jBScrollPane1.setViewportView(categoryTree);
        jBScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jBScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(showAllHeirsCheck, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
            .addComponent(jBScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showAllHeirsCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
        );

        productCategoriesSplitPane.setLeftComponent(jBPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productCategoriesSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productCategoriesSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBTree categoryTree;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private com.cosmos.swingb.JBScrollPane jBScrollPane1;
    private com.cosmos.swingb.JBPanel listPanel;
    private com.cosmos.swingb.JBSplitPane productCategoriesSplitPane;
    private javax.swing.JScrollPane productCategoriesTreeScrollPane;
    private com.cosmos.swingb.JBCheckBox showAllHeirsCheck;
    // End of variables declaration//GEN-END:variables

//    private JMenuItem modifyItem;
//
//    private JMenuItem deleteItem;
//
    private boolean modificationsEnabled = true;
//
//    private JMenuItem newItem;

    protected void initData()
    {
        //the list panel will benefit from the tree component because of the
        //possibility to easy lookup the children for a given node

        //categoryListPanel.setCategoriesTree(categoryTree);

        //let all 'list' queries are got from us

        //categoryListPanel.setCategoriesLister(new CategoryTreeLister());

        categoryToStringConverter = new AcaciaToStringConverter("${categoryName}");
        categoryTree.setToStringConverter(categoryToStringConverter);

        //load all categories and init the tree
        List<ProductCategory> categories = getFormSession().getProductsCategories(null);

        refreshTreeModel(categories);
    }

    private List<ProductCategory> currentTableItems;

    private class CategoryTreeLister implements Lister<ProductCategory>{

        @Override
        public List<ProductCategory> getList() {
            return currentTableItems;
        }

    }

    private void refreshTreeModel(List<ProductCategory> categories) {
        //make tree model from the list of categories
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
            getResourceMap().getString("CategoryTree.rootNodeDisplay"));
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        categoryTree.setModel(treeModel);
        addCateogriesToTree(root, categories);

        categoryTree.enableToStringCellRenderer();
        categoryTree.expandPath(new TreePath(root.getPath()));
        categoryTree.setSelectionPath(
            getNodePath(getRootHode(categoryTree)));
    }

    private void addCateogriesToTree(DefaultMutableTreeNode root, List<ProductCategory> categories) {

        java.util.Map<ProductCategory, DefaultMutableTreeNode> added = new HashMap
            <ProductCategory, DefaultMutableTreeNode>();

        for (ProductCategory cat : categories) {
            ProductCategory parentCat = cat.getParentCategory();
            DefaultMutableTreeNode catNode = findOrCreateNode(cat, added, root);
            DefaultMutableTreeNode parentNode = findOrCreateNode(parentCat, added, root);

            int idx = findAppropriateIndex(parentNode, catNode);
            parentNode.insert(catNode, idx);
        }
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
    private DefaultMutableTreeNode findOrCreateNode(ProductCategory cat,
                                           java.util.Map<ProductCategory, DefaultMutableTreeNode> added,
                                           DefaultMutableTreeNode root) {
        //assuming root if null
        if ( cat==null ){
            return root;
        }
        //find node
        DefaultMutableTreeNode result = added.get(cat);
        //create new node
        if ( result==null ){
            result = new DefaultMutableTreeNode(cat);
            added.put(cat, result);
        }
        return result;
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null)
                formSession = getBean(ProductsListRemote.class);

        return formSession;
    }

    /**
     * Getter for categoryListPanel
     * @return ProductCategoryListPanel
     */
    public ProductCategoryListPanel getCategoryListPanel() {
        return categoryListPanel;
    }

    public void setModificationsEnabled(boolean enabled) {
        modificationsEnabled = enabled;
        categoryListPanel.setVisible(Button.Delete, enabled);
        categoryListPanel.setVisible(Button.Modify, enabled);
        categoryListPanel.setVisible(Button.New, enabled);
        categoryTree.setDragEnabled(enabled);
    }
}
