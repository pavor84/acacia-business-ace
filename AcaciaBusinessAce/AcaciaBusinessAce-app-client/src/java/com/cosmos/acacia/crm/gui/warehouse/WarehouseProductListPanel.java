package com.cosmos.acacia.crm.gui.warehouse;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    
    //the current warehouse in which context the list of products is shown
    private Warehouse warehouse;

    private boolean readonly;
    
    private List<WarehouseProduct> list;

    private boolean allowDetailsView = true;
    
    private boolean showSummaryProducts = false;
    
    /**
     * @param parentDataObject
     */
    public WarehouseProductListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        customInit();
    }
    
    public WarehouseProductListPanel(Warehouse warehouse) {
        this.warehouse = warehouse;
        customInit();
    }
    
    public WarehouseProductListPanel(){
        super();
        customInit();
    }
    
    /**
     * If showSummaryProducts is true - 
     * shows only the columns that have meaning for summary products (for example all quantities columns).
     * Hides the columns that have no summary meaning (e.g. notes column).
     * Otherwise the same as WarehouseProductListPanel().
     * @param b
     */
    public WarehouseProductListPanel(boolean showSummaryProducts){
        super();
        this.showSummaryProducts = showSummaryProducts;
        customInit();
    }

    private void customInit() {
        entityProps = getFormSession().getWarehouseProductTableProperties();
        
        //free quantity column - add it by hand since it's synthetic value
        addColumn(25, "freeQuantity", getResourceMap().getString("WarehouseProduct.freeQuantity.columnName"),
            "${freeQuantity}", entityProps);
        //min quantity column - add it by hand since it's synthetic value
        addColumn(26, "prefferedMinQuantity", getResourceMap().getString("WarehouseProduct.minQuantity.columnName"),
            "${prefferedMinQuantity}", entityProps);
        //max quantity column - add it by hand since it's synthetic value
        addColumn(27, "prefferedMaxQuantity", getResourceMap().getString("WarehouseProduct.maxQuantity.columnName"),
            "${prefferedMaxQuantity}", entityProps);
        //default quantity column - add it by hand since it's synthetic value
        addColumn(28, "prefferedDefaultQuantity", getResourceMap().getString("WarehouseProduct.defaultQuantity.columnName"),
            "${prefferedDefaultQuantity}", entityProps);
        
        //remove the columns that doesn't have summary meaning if this is set to true
        if ( showSummaryProducts ){
            entityProps.removePropertyDetails("warehouse");
            entityProps.removePropertyDetails("notes");
            entityProps.removePropertyDetails("deliveryTime");
            entityProps.removePropertyDetails("salePrice");
        }
        
        refreshDataTable(entityProps);
    }

    @Override
    protected void initData() {
        super.initData();
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
        if ( list==null ){
            if (warehouse==null)
                list = getFormSession().listWarehouseProducts();
            else
                list = getFormSession().listWarehouseProducts(warehouse);
        }
        
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public void refreshList(List newList){
        if ( newList==null )
            throw new IllegalStateException("Null parameter is illegal!");
        
        list = newList;
        refreshDataTable(entityProps);
    }

    /**
     * @see com.cosmos.acacia.gui.AbstractTablePanel#canCreate()
     */
    @Override
    public boolean canCreate() {
        return !readonly;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(Object rowObject) {
        return !readonly;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        if ( readonly && allowDetailsView==false )
            return false;
        
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
        wp.setQuantityInStock(BigDecimal.ZERO);
        wp.setOrderedQuantity(BigDecimal.ZERO);
        wp.setSoldQuantity(BigDecimal.ZERO);
        wp.setQuantityDue(BigDecimal.ZERO);
        wp.setReservedQuantity(BigDecimal.ZERO);
        wp.setWarehouse(warehouse);
        return onEditEntity(wp);
    }

    private Object onEditEntity(WarehouseProduct wp) {
        WarehouseProductPanel editPanel = new WarehouseProductPanel(wp);
        if ( readonly ){
            editPanel.setReadonlyMode(readonly);
        }
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getEntity();
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

    /**
     * @param b
     */
    public void setReadonly(boolean readonly, boolean allowDetailsView) {
        this.readonly = readonly;
        for (Button button : Button.values()) {
            setVisible(button, !readonly);
        }
        
        this.allowDetailsView = allowDetailsView;
        if ( allowDetailsView ){
            setVisible(Button.Modify, true);
            getButton(Button.Modify).setText(getResourceMap().getString("button.view"));
        }
    }
}
