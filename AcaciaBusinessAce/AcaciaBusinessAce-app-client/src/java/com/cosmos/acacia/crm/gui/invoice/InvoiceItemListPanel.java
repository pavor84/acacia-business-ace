/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.invoice;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.swing.JOptionPane;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
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
public class InvoiceItemListPanel extends AbstractTablePanel {

    private Invoice invoice;

    /** Creates new form AddresssListPanel */
    public InvoiceItemListPanel(BigInteger parentDataObjectId, Invoice invoice) {
        super(parentDataObjectId);
        this.invoice = invoice;
    }

    public InvoiceItemListPanel(DataObjectBean parent) {
        super(parent);
    }

    @EJB
    private InvoiceListRemote formSession;

    private BindingGroup bindGroup;

    private List<InvoiceItem> items;

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
        if (bindGroup != null)
            bindGroup.unbind();

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

    protected List<InvoiceItem> getItems() {
        if ( items==null ){
            if ( getParentDataObjectId()!=null )
                return getFormSession().getInvoiceItems(getParentDataObjectId());
            else
                return new ArrayList<InvoiceItem>();
        }
        return items;
    }

    protected InvoiceListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(InvoiceListRemote.class);
        return formSession;
    }
    
    private List<InvoiceItem> deletedItems = null;

    @Override
    protected boolean deleteRow(Object rowObject) {
        if (rowObject != null) {
            deletedItems = getFormSession().deleteInvoiceItem((InvoiceItem)rowObject);
            return true;
        }

        return false;
    }
    
    @Action
    public void deleteAction() {
        InvoiceItem item = (InvoiceItem) getDataTable().getSelectedRowObject();
        String warningMessage = null;
        if ( getFormSession().isTemplateItem(item) ){
            warningMessage = getResourceMap().getString("deleteAction.ConfirmDialog.templateItemMessage");
        }else{
            warningMessage = getResourceMap().getString("deleteAction.ConfirmDialog.message");
        }

        if ( showDeleteConfirmation(warningMessage) ){
            try {
                if(deleteRow(item))
                {
                    if ( deletedItems!=null ){
                        for (InvoiceItem deletedItem : deletedItems) {
                            getDataTable().removeRow(deletedItem);
                        }
                    }else{
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
    protected Object modifyRow(Object rowObject) {

        if (rowObject != null && isEditable()) {
            InvoiceItem item = ((InvoiceItem)rowObject);
            if ( item.getWarehouse()==null )
                item.setWarehouse(getFormSession().getInvoiceWarehouse(invoice.getInvoiceId()));
            InvoiceItemForm formPanel = new InvoiceItemForm(item);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @Override
    protected Object newRow() { 
        if (canNestedOperationProceed()) {
            log.info(getParentDataObjectId());
            InvoiceItem item = getFormSession().newInvoiceItem(getParentDataObjectId());
            InvoiceItemForm formPanel = new InvoiceItemForm(item);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        if (bindGroup != null)
            bindGroup.unbind();

        initData();

        return super.refreshAction();
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Object rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Object rowObject) {
        return true;
    }

    public void refreshList(List<InvoiceItem> items) {
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
    protected void viewRow(Object rowObject) {
        InvoiceItemForm formPanel = new InvoiceItemForm((InvoiceItem) rowObject);
        formPanel.setReadonly();
        formPanel.showDialog(this);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void specialAction() {
        if ( canNestedOperationProceed() ){
            InvoiceItemListPanel invoiceItemsListPanel = new InvoiceItemListPanel(getParentDataObjectId(), invoice);
            invoiceItemsListPanel.getButtonsPanel().setVisible(false);
            InvoiceItemsCopyForm copyForm = new InvoiceItemsCopyForm(getParentDataObjectId(), invoiceItemsListPanel, invoice);
            DialogResponse response = copyForm.showDialog(this);
            if ( DialogResponse.SAVE.equals(response) ){
                refreshAction();
            }
        }
    }
}