/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.impl.BankDetailsListRemote;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class BankDetailsListPanel extends AbstractTablePanel {

    /** Creates new form BankDetailsListPanel */
    public BankDetailsListPanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
    }

    @EJB
    private BankDetailsListRemote formSession;

    private BindingGroup bankDetailsBindingGroup;
    private List<BankDetail> bankDetails;

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

    protected List<BankDetail> getBankDetails()
    {
        if(bankDetails == null)
        {
            bankDetails = getFormSession().getBankDetails(getParentDataObjectId());
        }

        return bankDetails;
    }

    protected EntityProperties getBankDetailEntityProperties()
    {
        return getFormSession().getBankDetailEntityProperties();
    }

    protected BankDetailsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(BankDetailsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deleteBankDetail(BankDetail bankDetail)
    {
        return getFormSession().deleteBankDetail(bankDetail);
    }

    @Override
    @Action
    public void selectAction(){
        super.selectAction();
        //
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
         if(rowObject != null)
        {
            deleteBankDetail((BankDetail) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            BankDetailPanel bankDetailPanel = new BankDetailPanel((BankDetail)rowObject);
            DialogResponse response = bankDetailPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return bankDetailPanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (bankDetailsBindingGroup != null)
            bankDetailsBindingGroup.unbind();

        bankDetails = null;

        initData();

        return t;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed())
        {
            BankDetailPanel bankDetailPanel = new BankDetailPanel(getParentDataObjectId());

            DialogResponse response = bankDetailPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return bankDetailPanel.getSelectedValue();
            }
        }

        return null;
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

}