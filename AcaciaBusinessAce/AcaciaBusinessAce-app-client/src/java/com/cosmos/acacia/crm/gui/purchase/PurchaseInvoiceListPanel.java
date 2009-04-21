/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.crm.bl.purchase.PurchaseServiceRemote;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.List;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;


/**
 *
 * @author Miro
 */
public class PurchaseInvoiceListPanel extends AbstractTablePanel {

    private static PurchaseServiceRemote formSession;
    //
    private BindingGroup bindingGroup;
    private EntityProperties entityProperties;

    public PurchaseInvoiceListPanel() {
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        return formSession.deletePurchaseInvoice((PurchaseInvoice) rowObject);
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        return editRow((PurchaseInvoice) rowObject);
    }

    @Override
    protected Object newRow() {
        return editRow(formSession.newPurchaseInvoice());
    }

    protected PurchaseInvoice editRow(PurchaseInvoice purchaseInvoice) {
        if (purchaseInvoice != null) {
            PurchaseInvoicePanel entityPanel = new PurchaseInvoicePanel(purchaseInvoice);
            DialogResponse response = entityPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (PurchaseInvoice) entityPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected void initData() {
        super.initData();

        bindingGroup = getBindingGroup();
        AcaciaTable table = getDataTable();
        JTableBinding tableBinding = table.bind(
                bindingGroup,
                getPurchaseInvoices(),
                getEntityProperties(),
                UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    public EntityProperties getEntityProperties() {
        if(entityProperties == null) {
            entityProperties = getFormSession().getEntityProperties(PurchaseInvoice.class);
        }

        return entityProperties;
    }

    protected List<PurchaseInvoice> getPurchaseInvoices() {
        return getFormSession().getPurchaseInvoices();
    }

    protected PurchaseServiceRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(PurchaseServiceRemote.class);
        }

        return formSession;
    }

}
