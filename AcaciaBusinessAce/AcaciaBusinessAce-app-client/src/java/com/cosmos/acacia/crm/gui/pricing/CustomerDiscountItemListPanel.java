/**
 * 
 */
package com.cosmos.acacia.crm.gui.pricing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jdesktop.application.Action;

import com.cosmos.acacia.crm.bl.impl.BaseRemote;
import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountRemote;
import com.cosmos.acacia.crm.data.CustomerDiscount;
import com.cosmos.acacia.crm.data.CustomerDiscountItem;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.gui.BaseItemForm;
import com.cosmos.acacia.crm.gui.BaseItemListPanel;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
public class CustomerDiscountItemListPanel extends BaseItemListPanel<CustomerDiscount, CustomerDiscountItem>{
    private boolean forProduct = false;
    
    public CustomerDiscountItemListPanel(CustomerDiscount parent) {
        super(parent);
    }
    
    @Override
    protected void initComponentsCustom() {
        setSpecialButtonBehavior(true);
        getButton(Button.New).setText(getResourceMap().getString("button.includeCategory"));
        getButton(Button.New).setToolTipText(getResourceMap().getString("button.includeCategory.tooltip"));
        getButton(Button.Special).setText(getResourceMap().getString("button.includeProduct"));
        getButton(Button.Special).setToolTipText(getResourceMap().getString("button.includeProduct.tooltip"));
        getButton(Button.Special).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                forProduct = true;
                newAction();
                forProduct = false;
            }
        });
        setVisible(Button.Special, true);
        setVisible(Button.Close, false);
        setVisible(Button.Refresh, false);

        if ( !getRightsManager().isAllowed(SpecialPermission.ProductPricing) )
            setReadonly();
    }
    
    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Action
    public void newAction() {
        if (canNestedOperationProceed()) {
            super.newAction();
        }
    }
    
    @Override
    protected Object modifyRow(Object rowObject) {
        CustomerDiscountItem item = (CustomerDiscountItem) rowObject;
        if ( item.getProduct()!=null )
            forProduct = true;
        Object result = super.modifyRow(rowObject);
        forProduct = false;
        return result;
    }
    
    @Override
    protected void viewRow(Object rowObject) {
        CustomerDiscountItem item = (CustomerDiscountItem) rowObject;
        if ( item.getProduct()!=null )
            forProduct = true;
        super.viewRow(rowObject);
        forProduct = false;
    }
    
    @Override
    protected BaseItemForm<CustomerDiscount, CustomerDiscountItem> createFormPanel(
                                                                               CustomerDiscountItem o) {
        return new CustomerDiscountItemForm(o, parent, forProduct);
    }

    @Override
    protected Class<? extends BaseRemote<CustomerDiscount, CustomerDiscountItem>> getFormSessionClass() {
        return CustomerDiscountRemote.class;
    }

}
