/**
 * 
 */
package com.cosmos.acacia.crm.gui.payment;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.payment.CustomerPaymentRemote;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.enums.CustomerPaymentStatus;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	06.03.2009
 * @author	Petar Milev
 *
 */
public class CustomerPaymentListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private CustomerPaymentRemote formSession;
    private BindingGroup bindingGroup;

    private List<CustomerPayment> list;
    
    public CustomerPaymentListPanel(BigInteger parentDataObjectId) {
        this ( parentDataObjectId, null );
    }
    
    public CustomerPaymentListPanel(BigInteger parentDataObjectId, List<CustomerPayment> list) {
        super(parentDataObjectId);
        this.list=list;
        bindComponents();
    }
    
    protected void bindComponents(){
        entityProps = getFormSession().getListingEntityProperties();
        
        refreshDataTable(entityProps);
        
        setVisible(AbstractTablePanel.Button.Classify, false);
    }
    
    protected CustomerPaymentRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(CustomerPaymentRemote.class);
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
            list = getFormSession().listCustomerPayments(getParentDataObjectId());
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
        return isOpen(rowObject);
    }
    
    protected boolean isOpen(Object rowObject){
        CustomerPayment entity = (CustomerPayment) rowObject;
        return CustomerPaymentStatus.Open.equals(entity.getStatus().getEnumValue());
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return isOpen(rowObject);
    }
    
    @Override
    public boolean canView(Object rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().deleteCustomerPayment((CustomerPayment)rowObject);
        return true;
    }
    
    @Override
    public void modifyAction() {
        super.modifyAction();
        //to refresh the view/modify state of the current row
        Object selected = getDataTable().getSelectedRowObject();
        setSelectedRowObject(null);
        setSelectedRowObject(selected);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        CustomerPayment o = (CustomerPayment) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        if ( canCreate() ){
            CustomerPayment o = getFormSession().newCustomerPayment(getParentDataObjectId());
            return showDetailForm(o, true);
        }else{
            return null;
        }
    }

    private Object showDetailForm(CustomerPayment o, boolean editable) {
        CustomerPaymentForm editPanel = new CustomerPaymentForm(o);
        if ( !editable )
            editPanel.setReadonly();
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
    
    @Override
    protected void viewRow(Object rowObject) {
        CustomerPayment o = (CustomerPayment) rowObject;
        showDetailForm(o, false);
    }
    
    public void refreshList(List<CustomerPayment> list){
        this.list = list;
        refreshDataTable(entityProps);
    }
}
