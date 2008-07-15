/**
 * 
 */
package com.cosmos.acacia.crm.gui.purchaseorders;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListRemote;
import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	14.07.2008
 * @author	Petar Milev
 *
 */
public class PurchaseOrderListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private PurchaseOrderListRemote formSession;
    private BindingGroup bindingGroup;
    
    /**
     * @param parentDataObject
     */
    public PurchaseOrderListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }
    
    @Override
    protected void initData() {
        super.initData();
        
        entityProps = getFormSession().getListingEntityProperties();
        
        refreshDataTable(entityProps);
    }
    
    protected PurchaseOrderListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(PurchaseOrderListRemote.class);
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
        return getFormSession().listPurchaseOrders(getParentDataObjectId());
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
        getFormSession().deletePurchaseOrder((PurchaseOrder)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        PurchaseOrder o = (PurchaseOrder) rowObject;
        return onEditEntity(o);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        PurchaseOrder o = getFormSession().newPurchaseOrder(getParentDataObjectId());
        return onEditEntity(o);
    }

    private Object onEditEntity(PurchaseOrder o) {
        PurchaseOrderForm editPanel = new PurchaseOrderForm(o);
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
