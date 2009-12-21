/**
 * 
 */
package com.cosmos.acacia.crm.gui.invoice;

import java.util.UUID;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.sales.SalesInvoice;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	10.09.2008
 * @author	Petar Milev
 *
 */
public class InvoiceListPanel extends AbstractTablePanel<SalesInvoice> {

    private EntityProperties entityProps;
    private InvoiceListRemote formSession;
    private BindingGroup bindingGroup;
    private List<SalesInvoice> list;
    private boolean proform;

    public InvoiceListPanel(UUID parentDataObjectId, boolean proform) {
        this(parentDataObjectId, null, proform);
    }

    public InvoiceListPanel(UUID parentDataObjectId, List<SalesInvoice> list, boolean proform) {
        super(parentDataObjectId);
        this.list = list;
        this.proform = proform;
        bindComponents();
    }

    protected void bindComponents() {
        entityProps = getFormSession().getListingEntityProperties();

        if (proform) {
            setTitle(getResourceMap().getString("Form.title.proform"));
        }

        refreshDataTable(entityProps);

        setVisible(AbstractTablePanel.Button.Classify, false);
    }

    protected InvoiceListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(InvoiceListRemote.class);
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
            list = getFormSession().listInvoices(getParentDataObjectId(), proform);
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

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(SalesInvoice rowObject) {
        InvoiceStatus status = (InvoiceStatus) rowObject.getStatus().getEnumValue();
        if (isInBranch(rowObject) && (InvoiceStatus.Open.equals(status) || InvoiceStatus.Reopen.equals(status))) {
            return true;
        }
        return false;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(SalesInvoice rowObject) {
        return isInBranch(rowObject);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(SalesInvoice rowObject) {
        getFormSession().deleteInvoice(rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected SalesInvoice modifyRow(SalesInvoice rowObject) {
        return showDetailForm(rowObject, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected SalesInvoice newRow() {
        SalesInvoice o = getFormSession().newInvoice(getParentDataObjectId());
        if (proform) {
            o.setProformaInvoice(Boolean.TRUE);
        }
        return showDetailForm(o, true);
    }

    private SalesInvoice showDetailForm(SalesInvoice o, boolean editable) {
        InvoiceForm editPanel = new InvoiceForm(o);
        if (!editable) {
            editPanel.setReadonly();
        }
        DialogResponse response = editPanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (SalesInvoice) editPanel.getSelectedValue();
        }

        return null;
    }

    private boolean isInBranch(SalesInvoice invoice) {
        Address userBranch = getUserBranch();
        if (invoice.getBranch().equals(userBranch)) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        refreshDataTable(entityProps);

        return t;
    }

    @Override
    protected void viewRow(SalesInvoice rowObject) {
        showDetailForm(rowObject, false);
    }
}
