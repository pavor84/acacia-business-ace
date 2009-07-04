/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.SelectableListDialog;
import java.awt.Dimension;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public abstract class AbstractTreeTablePanel<E extends DataObjectBean> extends AbstractTreePanel<E>
        implements SelectableListDialog {

    private TablePanel tablePanel;
    private boolean editable;
    private BindingGroup bindingGroup;
    private JTableBinding tableBinding;

    public AbstractTreeTablePanel(E dataObjectBean) {
        super(dataObjectBean);
    }

    @Override
    protected void initData() {
        super.initData();
        setComponent(getTablePanel());
    }

    @Override
    protected void treeValueChanged(TreeNode treeNode) {
        refreshDataTable();
    }

    @Override
    protected List<E> getChildren(DataObjectBean parent) {
        return getChildren((E) parent, false);
    }

    protected abstract List<E> getChildren(E parent, boolean allHeirs);

    protected abstract E onEditEntity(E entity);

    protected abstract E newEntity(E parentEntity);

    protected abstract boolean deleteRow(E entity);

    protected abstract EntityProperties getEntityProperties();

    @Override
    public Object getSelectedRowObject() {
        return getTablePanel().getSelectedRowObject();
    }

    @Override
    public void setSelectedRowObject(Object selectedObject) {
        getTablePanel().setSelectedRowObject(selectedObject);
    }

    @Override
    public List getListData() {
        return getTablePanel().getListData();
    }

    @Override
    public void setEditable(boolean editable) {
        this.editable = editable;
        getTablePanel().setEditable(editable);
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setVisibleSelectButtons(boolean visible) {
        getTablePanel().setVisibleSelectButtons(visible);
    }

    public TablePanel getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new TablePanel();
        }

        return tablePanel;
    }

    protected AcaciaTable getDataTable() {
        return getTablePanel().getDataTable();
    }

    protected BindingGroup getBindingGroup() {
        if (bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    protected JTableBinding getTableBinding() {
        return tableBinding;
    }

    protected boolean canCreate() {
        return true;
    }

    protected boolean canModify(E rowObject) {
        return true;
    }

    protected boolean canDelete(E rowObject) {
        return true;
    }

    protected void initDataTable(AcaciaTable table) {
        //log.info("initDataTable()");
        EntityProperties entityProps = getEntityProperties();
        BindingGroup bg = getBindingGroup();

        E parent = (E) getSelectionDataObjectBean();
        List children = getChildren(parent, isShowAllHeirs());
        tableBinding = table.bind(
                bg,
                children,
                entityProps,
                UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bg.bind();
    }

    protected void refreshDataTable() {
        //log.info("refreshDataTable()");
        List listData = getListData();
        listData.clear();
        E parent = (E) getSelectionDataObjectBean();
        List children = getChildren(parent, isShowAllHeirs());
        listData.addAll(children);
    }

    protected void fireNodeStructureChanged() {
        fireNodeStructureChanged(getSelectionNode());
    }

    protected class TablePanel extends AbstractTablePanel<E> {

        @Override
        protected void initData() {
            super.initData();
            initDataTable(getDataTable());
            setVisible(Button.Classify, false);
            setVisibleSelectButtons(true);
            setPreferredSize(new Dimension(400, 300));
        }

        @Override
        protected boolean deleteRow(E rowObject) {
            boolean result = AbstractTreeTablePanel.this.deleteRow(rowObject);
            if (result) {
                fireNodeStructureChanged();
            }
            return result;
        }

        @Override
        protected E modifyRow(E rowObject) {
            E entity = onEditEntity(rowObject);
            if (entity != null) {
                fireNodeStructureChanged();
            }
            return entity;
        }

        @Override
        protected E newRow() {
            E entity = newEntity((E) getSelectionDataObjectBean());
            entity = onEditEntity(entity);
            if (entity != null) {
                fireNodeStructureChanged();
            }
            return entity;
        }

        @Override
        public boolean canCreate() {
            return AbstractTreeTablePanel.this.canCreate();
        }

        @Override
        public boolean canModify(E rowObject) {
            return AbstractTreeTablePanel.this.canModify(rowObject);
        }

        @Override
        public boolean canDelete(E rowObject) {
            return AbstractTreeTablePanel.this.canDelete(rowObject);
        }

        @Override
        public void close() {
            // super.close();
            AbstractTreeTablePanel.this.close();
        }

        @Override
        protected void setDialogResponse(DialogResponse response) {
            super.setDialogResponse(response);
            AbstractTreeTablePanel.this.setDialogResponse(response);
        }
    }
}
