/**
 * 
 */
package com.cosmos.acacia.crm.gui.pricing;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.pricing.PricelistRemote;
import com.cosmos.acacia.crm.data.Pricelist;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	28.12.2008
 * @author	Petar Milev
 *
 */
public class PricelistListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private PricelistRemote formSession;
    private BindingGroup bindingGroup;

    private List<Pricelist> list;
    
    
    public PricelistListPanel(BigInteger parentDataObjectId) {
        this ( parentDataObjectId, null );
    }
    
    public PricelistListPanel(BigInteger parentDataObjectId, List<Pricelist> list) {
        super(parentDataObjectId);
        this.list = list;
        bindComponents();
    }
    
    protected void bindComponents(){
        entityProps = getFormSession().getListingEntityProperties();
        
        refreshDataTable(entityProps);
        
        setVisible(AbstractTablePanel.Button.Classify, false);
    }
    
    protected PricelistRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(PricelistRemote.class);
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
            list = getFormSession().listPricelists(getParentDataObjectId());
        return list;
    }

    /**
     * @see com.cosmos.acacia.gui.AbstractTablePanel#canCreate()
     */
    @Override
    public boolean canCreate() {
        return getRightsManager().isAllowed(SpecialPermission.ProductPricing);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(Object rowObject) {
        return getRightsManager().isAllowed(SpecialPermission.ProductPricing);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return getRightsManager().isAllowed(SpecialPermission.ProductPricing);
    }
    
    @Override
    public boolean canView(Object rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().deletePricelist((Pricelist)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        Pricelist o = (Pricelist) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        if ( canCreate() ){
            Pricelist o = getFormSession().newPricelist(getParentDataObjectId());
            return showDetailForm(o, true);
        }else{
            return null;
        }
    }

    private Object showDetailForm(Pricelist o, boolean editable) {
        PricelistForm editPanel = new PricelistForm(o);
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
        Pricelist o = (Pricelist) rowObject;
        showDetailForm(o, false);
    }
}