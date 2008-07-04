/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.PassportsListRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Passport;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import org.jdesktop.application.Task;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class PassportsListPanel extends AbstractTablePanel {

    /** Creates new form BankDetailsListPanel */
    public PassportsListPanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
    }
    
    public PassportsListPanel(DataObjectBean parent) {
        super(parent);
    }

    @EJB
    private PassportsListRemote formSession;

    private BindingGroup passportsBindingGroup;
    private List<Passport> passports;

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        passportsBindingGroup = new BindingGroup();
        AcaciaTable passportsTable = getDataTable();
        JTableBinding tableBinding = passportsTable.bind(passportsBindingGroup, getPassports(), getPassportEntityProperties());

        passportsBindingGroup.bind();

        passportsTable.setEditable(false);
    }

    protected List<Passport> getPassports()
    {
        if(passports == null)
        {
            passports = getFormSession().getPassports(getParentDataObjectId());
        }

        return passports;
    }

    protected EntityProperties getPassportEntityProperties()
    {
        return getFormSession().getPassportEntityProperties();
    }

    protected PassportsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(PassportsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deletePassport(Passport passport)
    {
        return getFormSession().deletePassport(passport);
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
            deletePassport((Passport) rowObject);
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (passportsBindingGroup != null)
            passportsBindingGroup.unbind();

        passports = null;

        initData();

        return t;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            PassportPanel passportPanel = new PassportPanel((Passport)rowObject);
            DialogResponse response = passportPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return passportPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed())
        {
            PassportPanel passportPanel = new PassportPanel(getParentDataObjectId());

            DialogResponse response = passportPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return passportPanel.getSelectedValue();
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