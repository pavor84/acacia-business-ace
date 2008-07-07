/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.swingb.SelectableListDialog;
import java.util.List;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Miro
 */
public abstract class AbstractTreeTablePanel<E extends DataObjectBean>
    extends AbstractTreePanel
    implements SelectableListDialog
{
    private TablePanel tablePanel;
    private boolean editable;

    public AbstractTreeTablePanel(E dataObjectBean)
    {
        super(dataObjectBean);
    }

    @Override
    protected void initData()
    {
        super.initData();
        setComponent(getTablePanel());
    }

    @Override
    protected void treeValueChanged(TreeNode treeNode)
    {
    }

    @Override
    protected final List<E> getChildren(DataObjectBean parent)
    {
        return getChildren(parent, false);
    }

    protected abstract List<E> getChildren(DataObjectBean parent, boolean allHeirs);
    protected abstract E onEditEntity(DataObjectBean entity);
    protected abstract E newEntity(DataObjectBean parentEntity);


    @Override
    public Object getSelectedRowObject()
    {
        return getTablePanel().getSelectedRowObject();
    }

    @Override
    public void setSelectedRowObject(Object selectedObject)
    {
        getTablePanel().setSelectedRowObject(selectedObject);
    }

    @Override
    public List getListData()
    {
        return getTablePanel().getListData();
    }

    @Override
    public void setEditable(boolean editable)
    {
        this.editable = editable;
        getTablePanel().setEditable(editable);
    }

    @Override
    public boolean isEditable()
    {
        return editable;
    }

    @Override
    public void setVisibleSelectButtons()
    {
        getTablePanel().setVisibleSelectButtons();
    }

    protected TablePanel getTablePanel()
    {
        if(tablePanel == null)
        {
            tablePanel = new TablePanel();
        }

        return tablePanel;
    }

    protected boolean canCreate()
    {
        return true;
    }

    protected boolean canModify(E rowObject)
    {
        return true;
    }

    protected boolean canDelete(E rowObject)
    {
        return true;
    }

    protected class TablePanel
        extends AbstractTablePanel
    {

        @Override
        protected boolean deleteRow(Object rowObject)
        {
            return true;
        }

        @Override
        protected Object modifyRow(Object rowObject)
        {
            return onEditEntity((E)rowObject);
        }

        @Override
        protected Object newRow()
        {
            return onEditEntity(newEntity(getSelectionDataObjectBean()));
        }

        @Override
        public boolean canCreate()
        {
            return AbstractTreeTablePanel.this.canCreate();
        }

        @Override
        public boolean canModify(Object rowObject)
        {
            return AbstractTreeTablePanel.this.canModify((E)rowObject);
        }

        @Override
        public boolean canDelete(Object rowObject)
        {
            return AbstractTreeTablePanel.this.canDelete((E)rowObject);
        }

    }
}
