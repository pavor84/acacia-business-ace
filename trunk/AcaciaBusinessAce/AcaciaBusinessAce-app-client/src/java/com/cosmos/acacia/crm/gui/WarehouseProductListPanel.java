package com.cosmos.acacia.crm.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.swing.JOptionPane;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.WarehouseListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.WarehouseProduct;
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
public class WarehouseProductListPanel extends AbstractTablePanel {

    private EntityProperties entityProps;
    
    @EJB
    private WarehouseListRemote formSession;
    private BindingGroup bindingGroup;
    
    /**
     * @param parentDataObject
     */
    public WarehouseProductListPanel(DataObject parentDataObject) {
        super(parentDataObject);
    }
    
    public WarehouseProductListPanel() {
        this(null);
    }
    
    @Override
    protected void initData() {
        super.initData();
        
        entityProps = getFormSession().getWarehouseProductEntityProperties();
        
        //free quantity column - add it by hand since it's synthetic value
        addColumn(25, getResourceMap().getString("WarehouseProduct.freeQuantity.columnName"),
            null, entityProps);
        
        refreshDataTable(entityProps);
    }
    
    protected WarehouseListRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = InitialContext.doLookup(WarehouseListRemote.class.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

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
        return getFormSession().listWarehouseProducts();
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
    public boolean canDelete(Object rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        try{
          getFormSession().deleteWarehouseProduct((WarehouseProduct) rowObject);
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
    protected Object modifyRow(Object rowObject) {
        WarehouseProduct wp = (WarehouseProduct) rowObject;
        return onEditEntity(wp);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        WarehouseProduct wp = getFormSession().newWarehouseProduct();
        return onEditEntity(wp);
    }

    private Object onEditEntity(WarehouseProduct wp) {
        WarehouseProductPanel editPanel = new WarehouseProductPanel(wp);
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
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
}
