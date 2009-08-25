/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.contactbook;

import java.util.UUID;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.BankDetailsListRemote;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BankDetail;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class BankDetailsListPanel extends AbstractTablePanel<BankDetail> {

    public BankDetailsListPanel(Address address) {
        this(address != null ? address.getId() : null);
        this.address = address;
    }

    /** Creates new form BankDetailsListPanel */
    public BankDetailsListPanel(UUID parentDataObjectId) {
        super(parentDataObjectId);
    }

    @EJB
    private BankDetailsListRemote formSession;
    private BindingGroup bankDetailsBindingGroup;
    private List<BankDetail> bankDetails;
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if(address != null) {
            setParentDataObjectId(address.getId());
        } else {
            setParentDataObjectId(null);
        }
        refreshAction();
    }

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        bankDetailsBindingGroup = new BindingGroup();
        AcaciaTable bankDetailsTable = getDataTable();
        JTableBinding tableBinding = bankDetailsTable.bind(bankDetailsBindingGroup, getBankDetails(), getBankDetailEntityProperties());

        bankDetailsBindingGroup.bind();

        bankDetailsTable.setEditable(false);
    }

    protected List<BankDetail> getBankDetails() {
        if (bankDetails == null) {
            bankDetails = getFormSession().getBankDetails(getParentDataObjectId());
        }

        return bankDetails;
    }

    protected EntityProperties getBankDetailEntityProperties() {
        return getFormSession().getBankDetailEntityProperties();
    }

    protected BankDetailsListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(BankDetailsListRemote.class);
        }

        return formSession;
    }

    protected int deleteBankDetail(BankDetail bankDetail) {
        return getFormSession().deleteBankDetail(bankDetail);
    }

    @Override
    @Action
    public void selectAction() {
        super.selectAction();
        //
    }

    @Override
    protected boolean deleteRow(BankDetail rowObject) {
        if (rowObject != null) {
            deleteBankDetail(rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected BankDetail modifyRow(BankDetail rowObject) {
        if (rowObject != null) {
            BankDetailPanel bankDetailPanel = new BankDetailPanel((BankDetail) rowObject);
            DialogResponse response = bankDetailPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (BankDetail) bankDetailPanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (bankDetailsBindingGroup != null) {
            bankDetailsBindingGroup.unbind();
        }

        bankDetails = null;

        initData();

        return t;
    }

    @Override
    protected BankDetail newRow() {
        if (canNestedOperationProceed()) {
            BankDetailPanel bankDetailPanel = new BankDetailPanel(getParentDataObjectId());

            DialogResponse response = bankDetailPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (BankDetail) bankDetailPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(BankDetail rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(BankDetail rowObject) {
        return true;
    }
}
