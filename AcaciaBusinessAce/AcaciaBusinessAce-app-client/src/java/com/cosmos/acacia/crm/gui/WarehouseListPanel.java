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
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;

/**
 * Created	:	02.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class WarehouseListPanel extends AbstractTablePanel {

    private EntityProperties entityProps;
    
    @EJB
    private WarehouseListRemote formSession;
    private BindingGroup bindingGroup;
    
    /**
     * @param parentDataObject
     */
    public WarehouseListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }
    
    public WarehouseListPanel() {
        this(null);
    }
    
    @Override
    protected void initData() {
        super.initData();
        
        entityProps = getFormSession().getWarehouseEntityProperties();
        
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
        return getFormSession().listWarehousesByName();
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
          getFormSession().deleteWarehouse((Warehouse) rowObject);
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
        Warehouse w = (Warehouse) rowObject;
        return onEditEntity(w);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        Warehouse w = getFormSession().newWarehouse(null);
        return onEditEntity(w);
    }

    private Object onEditEntity(Warehouse w) {
        WarehousePanel editPanel = new WarehousePanel(w);
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
