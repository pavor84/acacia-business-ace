/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.invoice;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.swing.JOptionPane;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.sales.SalesInvoice;
import com.cosmos.acacia.crm.data.sales.SalesInvoiceItem;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	10.07.2008
 * @author	Petar Milev
 *
 */
public class InvoiceItemListPanel extends AbstractTablePanel<SalesInvoiceItem> {

    private SalesInvoice invoice;

    /** Creates new form AddresssListPanel */
    public InvoiceItemListPanel(UUID parentDataObjectId, SalesInvoice invoice) {
        super(parentDataObjectId);
        this.invoice = invoice;
    }

    public InvoiceItemListPanel(DataObjectBean parent) {
        super(parent);
    }
    @EJB
    private InvoiceListRemote formSession;
    private BindingGroup bindGroup;
    private List<SalesInvoiceItem> items;
    private EntityProperties entityProps;

    @Override
    protected void initData() {

        super.initData();

        setSpecialCaption("button.insertFromDocument");
        setVisible(Button.Special, true);
        setSpecialButtonBehavior(true);

        entityProps = getFormSession().getItemsListEntityProperties();

        //addColumn(25, "product.codeFormatted", "Product dCode", "${product.codeFormatted}", entityProps);

        refreshDataTable(entityProps);

        setVisible(Button.Select, false);
    }

    protected void refreshDataTable(EntityProperties entityProps) {
        if (bindGroup != null) {
            bindGroup.unbind();
        }

        bindGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        table.bind(bindGroup, getItems(), entityProps, true);

        bindGroup.bind();

        //hide by default - some of the columns
        table.getColumnExt("Shipped quantity").setVisible(false);
        table.getColumnExt("Returned quantity").setVisible(false);
        table.getColumnExt("Ship Week").setVisible(false);
        table.getColumnExt("Warehouse").setVisible(false);
        table.setEditable(false);
    }

    protected List<SalesInvoiceItem> getItems() {
        if (items == null) {
            if (getParentDataObjectId() != null) {
                return getFormSession().getInvoiceItems(getParentDataObjectId());
            } else {
                return new ArrayList<SalesInvoiceItem>();
            }
        }
        return items;
    }

    protected InvoiceListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(InvoiceListRemote.class);
        }
        return formSession;
    }
    private List<SalesInvoiceItem> deletedItems = null;

    @Override
    protected boolean deleteRow(SalesInvoiceItem rowObject) {
        if (rowObject != null) {
            deletedItems = getFormSession().deleteInvoiceItem(rowObject);
            return true;
        }

        return false;
    }

    @Action
    public void deleteAction() {
        SalesInvoiceItem item = (SalesInvoiceItem) getDataTable().getSelectedRowObject();
        String warningMessage = null;
        if (getFormSession().isTemplateItem(item)) {
            warningMessage = getResourceMap().getString("deleteAction.ConfirmDialog.templateItemMessage");
        } else {
            warningMessage = getResourceMap().getString("deleteAction.ConfirmDialog.message");
        }

        if (showDeleteConfirmation(warningMessage)) {
            try {
                if (deleteRow(item)) {
                    if (deletedItems != null) {
                        for (SalesInvoiceItem deletedItem : deletedItems) {
                            getDataTable().removeRow(deletedItem);
                        }
                    } else {
                        getDataTable().removeSelectedRow();
                    }
                    fireDelete(item);
                }
            } catch (Exception ex) {
                ValidationException ve = extractValidationException(ex);
                if (ve != null) {
                    JOptionPane.showMessageDialog(this, formTableReferencedMessage(ve.getMessage()));
                } else {
                    log.error(ex);
                }
            }

        }
    }

    @Override
    protected SalesInvoiceItem modifyRow(SalesInvoiceItem rowObject) {

        if (rowObject != null && isEditable()) {
            if (rowObject.getWarehouse() == null) {
                rowObject.setWarehouse(getFormSession().getInvoiceWarehouse(invoice.getInvoiceId()));
            }
            InvoiceItemForm formPanel = new InvoiceItemForm(invoice, rowObject);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (SalesInvoiceItem) formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @Override
    protected SalesInvoiceItem newRow() {
        if (canNestedOperationProceed()) {
            log.info(getParentDataObjectId());
            SalesInvoiceItem item = getFormSession().newInvoiceItem(getParentDataObjectId());
            InvoiceItemForm formPanel = new InvoiceItemForm(invoice, item);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (SalesInvoiceItem) formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        if (bindGroup != null) {
            bindGroup.unbind();
        }

        initData();

        return super.refreshAction();
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(SalesInvoiceItem rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(SalesInvoiceItem rowObject) {
        return true;
    }

    public void refreshList(List<SalesInvoiceItem> items) {
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
    protected void viewRow(SalesInvoiceItem rowObject) {
        InvoiceItemForm formPanel = new InvoiceItemForm(invoice, rowObject);
        formPanel.setReadonly();
        formPanel.showDialog(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void specialAction() {
        if (canNestedOperationProceed()) {
            InvoiceItemListPanel invoiceItemsListPanel = new InvoiceItemListPanel(getParentDataObjectId(), invoice);
            invoiceItemsListPanel.getButtonsPanel().setVisible(false);
            InvoiceItemsCopyForm copyForm = new InvoiceItemsCopyForm(getParentDataObjectId(), invoiceItemsListPanel, invoice);
            DialogResponse response = copyForm.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                refreshAction();
            }
        }
    }
}
