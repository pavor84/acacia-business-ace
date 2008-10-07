/**
 * 
 */
package com.cosmos.acacia.crm.gui.invoice;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	10.09.2008
 * @author	Petar Milev
 *
 */
public class InvoiceListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private InvoiceListRemote formSession;
    private BindingGroup bindingGroup;

    private List<Invoice> list;
    
    /**
     * @param pendingConfirmations 
     * @param parentDataObject
     */
    public InvoiceListPanel(BigInteger parentDataObjectId) {
        this ( parentDataObjectId, null );
    }
    
    /**
     * @param pendingConfirmations 
     * @param parentDataObject
     */
    public InvoiceListPanel(BigInteger parentDataObjectId, List<Invoice> list) {
        super(parentDataObjectId);
        this.list = list;
        bindComponents();
    }
    
    protected void bindComponents(){
        entityProps = getFormSession().getListingEntityProperties();
        
        refreshDataTable(entityProps);
    }
    
    protected InvoiceListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(InvoiceListRemote.class);
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
            list = getFormSession().listInvoices(getParentDataObjectId());
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
        return isInBranch((Invoice) rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return isInBranch((Invoice) rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().deleteInvoice((Invoice)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        Invoice o = (Invoice) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        Invoice o = getFormSession().newInvoice(getParentDataObjectId());
        return showDetailForm(o, true);
    }

    private Object showDetailForm(Invoice o, boolean editable) {
        InvoiceForm editPanel = new InvoiceForm(o);
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
        }

        return null;
    }
    
    private boolean isInBranch(Invoice invoice){
        Address userBranch = getUserBranch();
        if ( invoice.getBranch().equals(userBranch) ){
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
        Invoice o = (Invoice) rowObject;
        showDetailForm(o, false);
    }
}