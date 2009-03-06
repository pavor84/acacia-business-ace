/**
 * 
 */
package com.cosmos.acacia.crm.gui.pricing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jdesktop.application.Action;

import com.cosmos.acacia.crm.bl.impl.BaseRemote;
import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountOldRemote;
import com.cosmos.acacia.crm.data.CustomerDiscountOld;
import com.cosmos.acacia.crm.data.CustomerDiscountItemOld;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.gui.BaseItemForm;
import com.cosmos.acacia.crm.gui.BaseItemListPanel;
import com.cosmos.swingb.JBButton;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
public class CustomerDiscountItemListOldPanel extends BaseItemListPanel<CustomerDiscountOld, CustomerDiscountItemOld>{
    private boolean forProduct = false;
    
    public CustomerDiscountItemListOldPanel(CustomerDiscountOld parent) {
        super(parent);
    }
    
    @Override
    protected void initComponentsCustom() {
        setSpecialButtonBehavior(true);

        JBButton button = getButton(Button.New);
        button.setText(getResourceMap().getString("button.includeCategory"));
        button.setToolTipText(getResourceMap().getString("button.includeCategory.tooltip"));

        button = getButton(Button.Special);
        button.setText(getResourceMap().getString("button.includeProduct"));
        button.setToolTipText(getResourceMap().getString("button.includeProduct.tooltip"));
        button.addActionListener(new ActionListener() {
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
    protected Object showDetailForm(CustomerDiscountItemOld item, boolean editable) {
        if(item.getCustomerDiscount() == null) {
            item.setCustomerDiscount(parent);
        }
        return super.showDetailForm(item, editable);
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        CustomerDiscountItemOld item = (CustomerDiscountItemOld) rowObject;
        if ( item.getProduct()!=null )
            forProduct = true;
        Object result = super.modifyRow(rowObject);
        forProduct = false;
        return result;
    }
    
    @Override
    protected void viewRow(Object rowObject) {
        CustomerDiscountItemOld item = (CustomerDiscountItemOld) rowObject;
        if ( item.getProduct()!=null )
            forProduct = true;
        super.viewRow(rowObject);
        forProduct = false;
    }
    
    @Override
    protected BaseItemForm<CustomerDiscountOld, CustomerDiscountItemOld> createFormPanel(
                                                                               CustomerDiscountItemOld o) {
        return new CustomerDiscountItemOldForm(o, parent, forProduct);
    }

    @Override
    protected Class<? extends BaseRemote<CustomerDiscountOld, CustomerDiscountItemOld>> getFormSessionClass() {
        return CustomerDiscountOldRemote.class;
    }

}
