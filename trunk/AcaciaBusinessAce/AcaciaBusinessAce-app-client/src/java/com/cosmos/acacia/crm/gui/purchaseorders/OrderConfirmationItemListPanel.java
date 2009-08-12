/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.purchaseorders;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.purchaseorder.OrderConfirmationListRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.purchase.OrderConfirmationItem;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	20.07.2008
 * @author	Petar Milev
 *
 */
public class OrderConfirmationItemListPanel extends AbstractTablePanel<OrderConfirmationItem> {

    /** Creates new form AddresssListPanel */
    public OrderConfirmationItemListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }

    public OrderConfirmationItemListPanel(DataObjectBean parent) {
        super(parent);
    }
    @EJB
    private OrderConfirmationListRemote formSession;
    private BindingGroup bindGroup;
    private List<OrderConfirmationItem> items;
    private EntityProperties entityProps;

    @Override
    protected void initData() {

        super.initData();

        entityProps = getFormSession().getItemsListEntityProperties();
        addColumn(35, "pendingQuantity", getResourceMap().getString("column.pendingQuantity"), null, entityProps);

        refreshDataTable(entityProps);

        setVisible(Button.Select, false);
    }

    protected void refreshDataTable(EntityProperties entityProps) {
        if (bindGroup != null) {
            bindGroup.unbind();
        }

        bindGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        table.bind(bindGroup, getItems(), entityProps);

        bindGroup.bind();
        table.setEditable(false);
    }

    protected List<OrderConfirmationItem> getItems() {
        if (items == null) {
            if (getParentDataObjectId() != null) {
                return getFormSession().getOrderItems(getParentDataObjectId());
            } else {
                return new ArrayList<OrderConfirmationItem>();
            }
        }
        return items;
    }

    protected OrderConfirmationListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(OrderConfirmationListRemote.class);
        }
        return formSession;
    }

    @Override
    protected boolean deleteRow(OrderConfirmationItem rowObject) {
        if (rowObject != null) {
            getFormSession().deleteOrderItem(rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected OrderConfirmationItem modifyRow(OrderConfirmationItem rowObject) {
        if (rowObject != null && isEditable()) {
            OrderConfirmationItemForm formPanel = new OrderConfirmationItemForm(rowObject);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (OrderConfirmationItem) formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @Override
    protected OrderConfirmationItem newRow() {
        if (canNestedOperationProceed()) {
            log.info(getParentDataObjectId());
            OrderConfirmationItem item = getFormSession().newOrderItem(getParentDataObjectId());
            OrderConfirmationItemForm formPanel = new OrderConfirmationItemForm(item);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (OrderConfirmationItem) formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (bindGroup != null) {
            bindGroup.unbind();
        }

        initData();

        return t;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(OrderConfirmationItem rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(OrderConfirmationItem rowObject) {
        return true;
    }

    public void refreshList(List<OrderConfirmationItem> items) {
        this.items = items;

        refreshDataTable(entityProps);
    }

    /**
     * Makes the list panel as read-only.
     * This means that all buttons for modify operations will be hidden
     */
    public void setReadonly() {
        super.setReadonly();
        getButton(Button.Special).setVisible(false);
    }

    @Override
    protected void viewRow(OrderConfirmationItem rowObject) {
        OrderConfirmationItemForm formPanel = new OrderConfirmationItemForm((OrderConfirmationItem) rowObject);
        formPanel.setReadonly();
        formPanel.showDialog(this);
    }
}
