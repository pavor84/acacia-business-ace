/**
 * 
 */
package com.cosmos.acacia.crm.gui.payment;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.payment.CustomerPaymentRemote;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.enums.CustomerPaymentStatus;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	06.03.2009
 * @author	Petar Milev
 *
 */
public class CustomerPaymentListPanel extends AbstractTablePanel<CustomerPayment> {

    private EntityProperties entityProps;
    private CustomerPaymentRemote formSession;
    private BindingGroup bindingGroup;
    private List<CustomerPayment> list;

    public CustomerPaymentListPanel(BigInteger parentDataObjectId) {
        this(parentDataObjectId, null);
    }

    public CustomerPaymentListPanel(BigInteger parentDataObjectId, List<CustomerPayment> list) {
        this(parentDataObjectId, list, null);
    }

    public CustomerPaymentListPanel(BigInteger parentDataObjectId, List<CustomerPayment> list, EntityProperties entProperties) {
        super(parentDataObjectId);
        this.entityProps = entProperties;
        this.list = list;
        bindComponents();
    }

    protected void bindComponents() {
        if (entityProps == null) {
            entityProps = getFormSession().getListingEntityProperties();
        }

        refreshDataTable(entityProps);

        setVisible(AbstractTablePanel.Button.Classify, false);
    }

    protected CustomerPaymentRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(CustomerPaymentRemote.class);
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
            list = getFormSession().listCustomerPayments(getParentDataObjectId());
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
    public boolean canDelete(CustomerPayment rowObject) {
        return isOpen(rowObject);
    }

    protected boolean isOpen(CustomerPayment rowObject) {
        return CustomerPaymentStatus.Open.equals(rowObject.getStatus().getEnumValue());
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(CustomerPayment rowObject) {
        return isOpen(rowObject);
    }

    @Override
    public boolean canView(CustomerPayment rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(CustomerPayment rowObject) {
        getFormSession().deleteCustomerPayment((CustomerPayment) rowObject);
        return true;
    }

    @Override
    public void modifyAction() {
        super.modifyAction();
        //to refresh the view/modify state of the current row
        Object selected = getDataTable().getSelectedRowObject();
        setSelectedRowObject(null);
        setSelectedRowObject(selected);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected CustomerPayment modifyRow(CustomerPayment rowObject) {
        return showDetailForm(rowObject, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected CustomerPayment newRow() {
        if (canCreate()) {
            CustomerPayment o = getFormSession().newCustomerPayment(getParentDataObjectId());
            return (CustomerPayment) showDetailForm(o, true);
        } else {
            return null;
        }
    }

    private CustomerPayment showDetailForm(CustomerPayment o, boolean editable) {
        CustomerPaymentForm editPanel = new CustomerPaymentForm(o);
        if (!editable) {
            editPanel.setReadonly();
        }
        DialogResponse response = editPanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (CustomerPayment) editPanel.getSelectedValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        refreshDataTable(entityProps);

        return t;
    }

    @Override
    protected void viewRow(CustomerPayment rowObject) {
        showDetailForm(rowObject, false);
    }

    public void refreshList(List<CustomerPayment> list) {
        this.list = list;
        refreshDataTable(entityProps);
    }
}
