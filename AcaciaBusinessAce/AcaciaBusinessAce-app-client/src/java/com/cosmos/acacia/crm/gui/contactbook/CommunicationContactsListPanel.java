/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
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
public class CommunicationContactsListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public CommunicationContactsListPanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
    }

    @EJB
    private AddressesListRemote formSession;

    private BindingGroup communicationContactsBindingGroup;
    private List<CommunicationContact> communicationContacts;
    private ContactPerson contactPerson;

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        communicationContactsBindingGroup = new BindingGroup();
        AcaciaTable communicationContactsTable = getDataTable();
        JTableBinding tableBinding = communicationContactsTable.bind(communicationContactsBindingGroup, getCommunicationContacts(), getCommunicationContactEntityProperties());

        communicationContactsBindingGroup.bind();

        communicationContactsTable.setEditable(false);
    }

    protected List<CommunicationContact> getCommunicationContacts()
    {
        if(communicationContacts == null)
        {
            communicationContacts = getFormSession().getCommunicationContacts(getParentDataObjectId());
        }

        return communicationContacts;
    }

    protected EntityProperties getCommunicationContactEntityProperties()
    {
        return getFormSession().getCommunicationContactEntityProperties();
    }

    protected AddressesListRemote getFormSession()
    {
        if(formSession == null)
            formSession = getBean(AddressesListRemote.class);

        return formSession;
    }

    protected int deleteCommunicationContact(CommunicationContact communicationContact)
    {
        return getFormSession().deleteCommunicationContact(communicationContact);
    }

     public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
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
            deleteCommunicationContact((CommunicationContact) rowObject);
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (communicationContactsBindingGroup != null)
            communicationContactsBindingGroup.unbind();

        communicationContacts = null;

        initData();

        return t;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            CommunicationContactPanel communicationContactPanel =
                    new CommunicationContactPanel((CommunicationContact) rowObject);
            DialogResponse response = communicationContactPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return communicationContactPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed())
        {
            CommunicationContactPanel communicationContactPanel = new CommunicationContactPanel(getParentDataObjectId(),
                        getContactPerson());

            DialogResponse response = communicationContactPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return communicationContactPanel.getSelectedValue();
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