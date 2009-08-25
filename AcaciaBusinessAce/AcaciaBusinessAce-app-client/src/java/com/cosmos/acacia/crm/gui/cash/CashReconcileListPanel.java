/**
 * 
 */
package com.cosmos.acacia.crm.gui.cash;

import java.util.UUID;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.cash.CashReconcileRemote;
import com.cosmos.acacia.crm.data.cash.CashReconcile;
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
public class CashReconcileListPanel extends AbstractTablePanel<CashReconcile> {
    
    private EntityProperties entityProps;
    
    private CashReconcileRemote formSession;
    private BindingGroup bindingGroup;
    
    private List<CashReconcile> list;
    
    public CashReconcileListPanel(UUID parentDataObjectId) {
        this ( parentDataObjectId, null );
    }
    
    public CashReconcileListPanel(UUID parentDataObjectId, List<CashReconcile> list) {
        this(parentDataObjectId, list, null);
    }
    
    public CashReconcileListPanel(UUID parentDataObjectId, List<CashReconcile> list, EntityProperties entProperties) {
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
    public boolean canDelete(CashReconcile rowObject) {
        return isDraft(rowObject);
    }
    
    protected boolean isDraft(CashReconcile rowObject){
        return DocumentStatus.Draft.equals(rowObject.getDocumentStatus().getEnumValue());
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(CashReconcile rowObject) {
        return isDraft(rowObject);
    }
    
    @Override
    public boolean canView(CashReconcile rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(CashReconcile rowObject) {
        getFormSession().deleteCashReconcile(rowObject);
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
    protected CashReconcile modifyRow(CashReconcile rowObject) {
        return showDetailForm(rowObject, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected CashReconcile newRow() {
        if ( canCreate() ){
            CashReconcile o = getFormSession().newCashReconcile(getParentDataObjectId());
            return showDetailForm(o, true);
        }else{
            return null;
        }
    }

    private CashReconcile showDetailForm(CashReconcile o, boolean editable) {
        CashReconcileForm editPanel = new CashReconcileForm(o);
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return (CashReconcile) editPanel.getSelectedValue();
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
    protected void viewRow(CashReconcile rowObject) {
        showDetailForm(rowObject, false);
    }
    
    public void refreshList(List<CashReconcile> list){
        this.list = list;
        refreshDataTable(entityProps);
    }
}
