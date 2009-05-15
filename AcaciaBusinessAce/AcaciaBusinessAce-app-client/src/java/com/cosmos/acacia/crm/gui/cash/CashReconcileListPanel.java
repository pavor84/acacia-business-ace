/**
 * 
 */
package com.cosmos.acacia.crm.gui.cash;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.cash.CashReconcileRemote;
import com.cosmos.acacia.crm.data.CashReconcile;
import com.cosmos.acacia.crm.enums.DocumentStatus;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	28.04.2009
 * @author	Petar Milev
 *
 */
public class CashReconcileListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private CashReconcileRemote formSession;
    private BindingGroup bindingGroup;
    
    private List<CashReconcile> list;
    
    public CashReconcileListPanel(BigInteger parentDataObjectId) {
        this ( parentDataObjectId, null );
    }
    
    public CashReconcileListPanel(BigInteger parentDataObjectId, List<CashReconcile> list) {
        this(parentDataObjectId, list, null);
    }
    
    public CashReconcileListPanel(BigInteger parentDataObjectId, List<CashReconcile> list, EntityProperties entProperties) {
        super(parentDataObjectId);
        this.entityProps = entProperties;
        this.list=list;
        bindComponents();
    }
    
    protected void bindComponents(){
        if ( entityProps==null )
            entityProps = getFormSession().getListingEntityProperties();
        
        refreshDataTable(entityProps);
        
        setVisible(AbstractTablePanel.Button.Classify, false);
    }
    
    protected CashReconcileRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(CashReconcileRemote.class);
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
            list = getFormSession().listCashReconciles(getParentDataObjectId());
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
        return isDraft(rowObject);
    }
    
    protected boolean isDraft(Object rowObject){
        CashReconcile entity = (CashReconcile) rowObject;
        return DocumentStatus.Draft.equals(entity.getDocumentStatus().getEnumValue());
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return isDraft(rowObject);
    }
    
    @Override
    public boolean canView(Object rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().deleteCashReconcile((CashReconcile)rowObject);
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
        CashReconcile o = (CashReconcile) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        if ( canCreate() ){
            CashReconcile o = getFormSession().newCashReconcile(getParentDataObjectId());
            return showDetailForm(o, true);
        }else{
            return null;
        }
    }

    private Object showDetailForm(CashReconcile o, boolean editable) {
        CashReconcileForm editPanel = new CashReconcileForm(o);
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
        CashReconcile o = (CashReconcile) rowObject;
        showDetailForm(o, false);
    }
    
    public void refreshList(List<CashReconcile> list){
        this.list = list;
        refreshDataTable(entityProps);
    }
}
