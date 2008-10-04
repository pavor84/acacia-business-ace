/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.invoice;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.InvoiceItem;
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

    /** Creates new form AddresssListPanel */
    public InvoiceItemListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
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

        entityProps = getFormSession().getItemsListEntityProperties();

        refreshDataTable(entityProps);

        setVisible(Button.Select, false);
    }

    protected void refreshDataTable(EntityProperties entityProps) {
        if (bindGroup != null)
            bindGroup.unbind();

        bindGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        table.bind(bindGroup, getItems(), entityProps);

        bindGroup.bind();
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

    @Override
    protected boolean deleteRow(Object rowObject) {
        if (rowObject != null) {
            getFormSession().deleteInvoiceItem((InvoiceItem)rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {

        if (rowObject != null && isEditable()) {
            InvoiceItemForm formPanel = new InvoiceItemForm((InvoiceItem) rowObject);
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
        Task t = super.refreshAction();

        if (bindGroup != null)
            bindGroup.unbind();

        initData();

        return t;
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
}