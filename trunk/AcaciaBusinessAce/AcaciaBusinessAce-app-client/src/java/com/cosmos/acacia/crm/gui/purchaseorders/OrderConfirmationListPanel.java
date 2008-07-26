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

import com.cosmos.acacia.crm.bl.purchaseorder.OrderConfirmationListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.OrderConfirmation;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	14.07.2008
 * @author	Petar Milev
 *
 */
public class OrderConfirmationListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private OrderConfirmationListRemote formSession;
    private BindingGroup bindingGroup;

    private List<OrderConfirmation> list;
    
    /**
     * @param pendingConfirmations 
     * @param parentDataObject
     */
    public OrderConfirmationListPanel(BigInteger parentDataObjectId) {
        this ( parentDataObjectId, null );
    }
    
    /**
     * @param pendingConfirmations 
     * @param parentDataObject
     */
    public OrderConfirmationListPanel(BigInteger parentDataObjectId, List<OrderConfirmation> list) {
        super(parentDataObjectId);
        this.list = list;
        bindComponents();
    }
    
    protected void bindComponents(){
        entityProps = getFormSession().getListingEntityProperties();
        
        refreshDataTable(entityProps);
    }
    
    protected OrderConfirmationListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(OrderConfirmationListRemote.class);
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
        if ( list==null )
            list = getFormSession().listOrderConfirmations(getParentDataObjectId());
        return list;
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
        return isInBranch((OrderConfirmation) rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return isInBranch((OrderConfirmation) rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().deleteOrderConfirmation((OrderConfirmation)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        OrderConfirmation o = (OrderConfirmation) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        OrderConfirmation o = getFormSession().newOrderConfirmation(getParentDataObjectId());
        return showDetailForm(o, true);
    }

    private Object showDetailForm(OrderConfirmation o, boolean editable) {
        OrderConfirmationForm editPanel = new OrderConfirmationForm(o);
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
        }

        return null;
    }
    
    private boolean isInBranch(OrderConfirmation confirmation){
        Address userBranch = getUserBranch();
        if ( confirmation.getBranch().equals(userBranch) ){
            return true;
        }else
            return false;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
         
        refreshDataTable(entityProps);
        
        return t;
    }
    
    @Override
    protected void viewRow(Object rowObject) {
        OrderConfirmation o = (OrderConfirmation) rowObject;
        showDetailForm(o, false);
    }
}
