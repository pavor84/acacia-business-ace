package com.cosmos.acacia.crm.gui.warehouse;

import java.util.UUID;
import java.util.List;

import javax.ejb.EJB;
import javax.swing.JOptionPane;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.WarehouseListRemote;
import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	02.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class WarehouseListPanel extends AbstractTablePanel<Warehouse> {

    private EntityProperties entityProps;

    @EJB
    private WarehouseListRemote formSession;
    private BindingGroup bindingGroup;

    /**
     * @param parentDataObject
     */
    public WarehouseListPanel(UUID parentDataObjectId) {
        super(parentDataObjectId);
    }

    @Override
    protected void initData() {
        super.initData();

        setSpecialCaption("enterWarehouse.Action.text");
        setVisible(Button.Special, true);


        entityProps = getFormSession().getWarehouseEntityProperties();

        refreshDataTable(entityProps);
    }

    protected WarehouseListRemote getFormSession() {
        if (formSession == null)
            formSession = getBean(WarehouseListRemote.class);

        return formSession;
    }

    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps){
        if ( bindingGroup!=null )
            bindingGroup.unbind();

        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();

        JTableBinding tableBinding = table.bind(bindingGroup, getList(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @SuppressWarnings("unchecked")
    private List getList() {
        return getFormSession().listWarehousesByName(getParentDataObjectId());
    }

    /**
     * @see com.cosmos.acacia.gui.AbstractTablePanel#canCreate()
     */
    @Override
    public boolean canCreate() {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(Warehouse rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Warehouse rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Warehouse rowObject) {
        try{
          getFormSession().deleteWarehouse(rowObject);
          return true;
        }catch ( Exception e ){
            JOptionPane.showMessageDialog(this,
                getResourceMap().getString("WarehouseListPanel.err.cantDelete"),
                getResourceMap().getString("WarehouseListPanel.err.cantDeleteTitle"),
                JOptionPane.DEFAULT_OPTION);
            return false;
        }
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Warehouse modifyRow(Warehouse rowObject) {
        return onEditEntity(rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Warehouse newRow() {
        Warehouse w = getFormSession().newWarehouse(getParentDataObjectId());
        return onEditEntity(w);
    }

    private Warehouse onEditEntity(Warehouse w) {
        WarehousePanel editPanel = new WarehousePanel(w);
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return (Warehouse) editPanel.getSelectedValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        refreshDataTable(entityProps);

        return t;
    }

    @Override
    public void specialAction() {
        Warehouse warehouse = (Warehouse) getDataTable().getSelectedRowObject();
        if ( warehouse==null ){
            log.warn("Enter warehouse action can't be executed: Warehouse instance expected but "+getSelectedRowObject()+" found! ");
            return;
        }

        WarehouseProductListPanel warehouseProductList = new WarehouseProductListPanel(getParentDataObjectId(), warehouse);
        warehouseProductList.showFrame(this);
    }
}
