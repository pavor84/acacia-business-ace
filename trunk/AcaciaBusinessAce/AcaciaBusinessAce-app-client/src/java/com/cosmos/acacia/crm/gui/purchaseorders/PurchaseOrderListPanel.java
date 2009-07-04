/**
 * 
 */
package com.cosmos.acacia.crm.gui.purchaseorders;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	14.07.2008
 * @author	Petar Milev
 *
 */
public class PurchaseOrderListPanel extends AbstractTablePanel<PurchaseOrder> {

    private EntityProperties entityProps;
    private PurchaseOrderListRemote formSession;
    private BindingGroup bindingGroup;
    private List<PurchaseOrder> list;

    public PurchaseOrderListPanel() {
        this(LocalSession.instance().getOrganization().getId());
    }

    /**
     * @param pendingOrders 
     * @param parentDataObject
     */
    public PurchaseOrderListPanel(BigInteger parentDataObjectId) {
        this(parentDataObjectId, null);
    }

    /**
     * @param pendingOrders 
     * @param parentDataObject
     */
    public PurchaseOrderListPanel(BigInteger parentDataObjectId, List<PurchaseOrder> pendingOrders) {
        super(parentDataObjectId);
        this.list = pendingOrders;
        bindComponents();
    }

    protected void bindComponents() {
        entityProps = getFormSession().getListingEntityProperties();

        refreshDataTable(entityProps);
    }

    protected PurchaseOrderListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(PurchaseOrderListRemote.class);
        }
        return formSession;
    }

    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps) {
        if (bindingGroup != null) {
            bindingGroup.unbind();
        }

        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();

        JTableBinding tableBinding = table.bind(bindingGroup, getList(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @SuppressWarnings("unchecked")
    private List getList() {
        if (list == null) {
            list = getFormSession().getPurchaseOrders();
        }
        return list;
    }

    /**
     * @see com.cosmos.acacia.gui.AbstractTablePanel#canCreate()
     */
    @Override
    public boolean canCreate() {
        return true;
    }

    private boolean isInBranch(PurchaseOrder order) {
        Address userBranch = getUserBranch();
        if (order.getBranch().equals(userBranch)) {
            return true;
        } else {
            return false;
        }
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(PurchaseOrder rowObject) {
        return isInBranch(rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(PurchaseOrder rowObject) {
        return isInBranch(rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(PurchaseOrder rowObject) {
        getFormSession().deletePurchaseOrder(rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected PurchaseOrder modifyRow(PurchaseOrder rowObject) {
        return showDetailsForm(rowObject, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected PurchaseOrder newRow() {
        PurchaseOrder o = getFormSession().newPurchaseOrder();
        return showDetailsForm(o, true);
    }

    private PurchaseOrder showDetailsForm(PurchaseOrder o, boolean editable) {
        PurchaseOrderForm editPanel = new PurchaseOrderForm(o);
        if (!editable) {
            editPanel.setReadonly();
        }
        DialogResponse response = editPanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (PurchaseOrder) editPanel.getSelectedValue();
        }

        return null;
    }

    @Override
    protected void viewRow(PurchaseOrder rowObject) {
        showDetailsForm(rowObject, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        refreshDataTable(entityProps);

        return t;
    }
}
