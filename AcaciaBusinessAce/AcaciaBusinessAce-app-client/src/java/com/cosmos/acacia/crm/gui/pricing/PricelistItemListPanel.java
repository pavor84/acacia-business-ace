/**
 * 
 */
package com.cosmos.acacia.crm.gui.pricing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListSelectionModel;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.bl.pricing.PricelistRemote;
import com.cosmos.acacia.crm.data.Pricelist;
import com.cosmos.acacia.crm.data.PricelistItem;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.gui.ProductCategoriesTreePanel;
import com.cosmos.acacia.crm.gui.ProductsListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	12.01.2009
 * @author	Petar Milev
 */
public class PricelistItemListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private PricelistRemote formSession;
    private BindingGroup bindingGroup;
    
    private ProductsListRemote productsListRemote = getBean(ProductsListRemote.class);
    
    private Pricelist pricelist;

    private List<PricelistItem> list;
    
    
    public PricelistItemListPanel(Pricelist pricelist) {
        this ( pricelist, null );
    }
    
    public PricelistItemListPanel(Pricelist pricelist, List<PricelistItem> list) {
        super(pricelist.getPricelistId());
        this.list = list;
        this.pricelist = pricelist;
        bindComponents();
        initComponentsCustom();
    }
    
    private void initComponentsCustom() {
        setSpecialButtonBehavior(true);
        getButton(Button.New).setText(getResourceMap().getString("button.includeCategory"));
        getButton(Button.New).setToolTipText(getResourceMap().getString("button.includeCategory.tooltip"));
        getButton(Button.Special).setText(getResourceMap().getString("button.includeProducts"));
        getButton(Button.Special).setToolTipText(getResourceMap().getString("button.includeProducts.tooltip"));
        getButton(Button.Special).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onIncludeProducts();
            }
        });
        setVisible(Button.Special, true);
        setVisible(Button.Close, false);
        setVisible(Button.Refresh, false);
        
        if ( !getRightsManager().isAllowed(SpecialPermission.ProductPricing) )
            setReadonly();
    }

    protected void onIncludeCategory() {
        if (canNestedOperationProceed()) {
            ProductCategoriesTreePanel categoriesPanel = new ProductCategoriesTreePanel(getOrganizationDataObjectId());
            categoriesPanel.getShowAllHeirsCheckBox().setText(getResourceMap().getString("button.includeHeirs"));
            DialogResponse resp = categoriesPanel.showDialog(this);
            if ( DialogResponse.SELECT.equals(resp)){
                ProductCategory category = (ProductCategory) categoriesPanel.getSelectedRowObject();
                if ( category!=null ){
                    boolean includeHeirs = categoriesPanel.getShowAllHeirs();
                    List<SimpleProduct> selected = productsListRemote.getProductsForCategory(category.getId(), includeHeirs);
                    if ( selected!=null && selected.size()>0 ){
                        showPricelistItemForm(selected);
                    }else{
                        showMessageDialog("message.noProductsInCategory");
                    }
                }
            }
        }
    }

    protected void onIncludeProducts() {
        if (canNestedOperationProceed()) {
            ProductsListPanel productPanel = new ProductsListPanel(getOrganizationDataObjectId());
            productPanel.getDataTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            DialogResponse resp = productPanel.showDialog(this);
            if ( DialogResponse.SELECT.equals(resp)){
                List<SimpleProduct> selected = productPanel.getDataTable().getSelectedRowObjects();
                if ( selected!=null && selected.size()>0 ){
                    showPricelistItemForm(selected);
                }
            }
        }
    }

    private void showPricelistItemForm(List<SimpleProduct> products) {
        PricelistItemForm itemsForm = new PricelistItemForm(products, pricelist, this.getListData());
        DialogResponse dr = itemsForm.showDialog(this);
        if ( DialogResponse.SAVE.equals(dr) ){
            List<PricelistItem> items = itemsForm.getSelectedValues();
            for (PricelistItem pricelistItem : items) {
                addOrdUpdateRow(pricelistItem);
            }
        }
    }
    
    private void addOrdUpdateRow(PricelistItem pricelistItem) {
        int idx = 0;
        List<PricelistItem> items = getDataTable().getData();
        for (PricelistItem i : items) {
            if ( PricelistItemForm.compare(i.getProduct(), pricelistItem.getProduct())>0 ){
                break;
            }
            idx++;
        }
        getDataTable().addOrUpdateRow(pricelistItem, idx);
    }

    protected void bindComponents(){
        entityProps = getFormSession().getItemsListEntityProperties();
        
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
        if ( list==null ){
            if ( getParentDataObjectId()!=null )
                list = getFormSession().getPricelistItems(getParentDataObjectId());
            else
                list = new ArrayList<PricelistItem>();
        }
            
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
        getFormSession().deletePricelistItem((PricelistItem)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        PricelistItem o = (PricelistItem) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Action
    public void newAction() {
        onIncludeCategory();
    }

    private Object showDetailForm(PricelistItem o, boolean editable) {
        PricelistItemForm editPanel = new PricelistItemForm(o, pricelist, getListData());
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
        }

        return null;
    }
    
    @Override
    public void setReadonly(boolean readonly) {
        super.setReadonly(readonly);
        getButton(Button.Special).setVisible(!readonly);
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
        PricelistItem o = (PricelistItem) rowObject;
        showDetailForm(o, false);
    }

    @Override
    protected Object newRow() {
        throw new UnsupportedOperationException("New operation is specific, and should be performed outside this method");
    }

    public void setPricelist(Pricelist pricelist) {
        this.pricelist=pricelist;
    }
}
