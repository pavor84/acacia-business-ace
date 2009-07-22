/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.enums.CommunicationType;
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
public class CommunicationContactsListPanel extends AbstractTablePanel<CommunicationContact> {

    public CommunicationContactsListPanel(Address address) {
        this(address, null);
    }

    public CommunicationContactsListPanel(Address address, CommunicationType communicationType) {
        super(new Object[] {address, communicationType});
    }
    //
    @EJB
    private AddressesListRemote formSession;
    private BindingGroup bindingGroup;
    private ContactPerson contactPerson;

    public Address getAddress() {
        return (Address)parameters[0];
    }

    public void setAddress(Address address) {
        parameters[0] = address;
        refreshAction();
    }

    public CommunicationType getCommunicationType() {
        return (CommunicationType)parameters[1];
    }

    public void setCommunicationType(CommunicationType communicationType) {
        parameters[1] = communicationType;
        refreshAction();
    }

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        bindingGroup = new BindingGroup();
        AcaciaTable communicationContactsTable = getDataTable();
        communicationContactsTable.bind(bindingGroup, getCommunicationContacts(), getCommunicationContactEntityProperties());

        bindingGroup.bind();

        communicationContactsTable.setEditable(false);
    }

    protected List<CommunicationContact> getCommunicationContacts() {
        return getFormSession().getCommunicationContacts(getAddress(), getCommunicationType());
    }

    protected EntityProperties getCommunicationContactEntityProperties() {
        return getFormSession().getCommunicationContactEntityProperties();
    }

    protected AddressesListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(AddressesListRemote.class);
        }

        return formSession;
    }

    protected int deleteCommunicationContact(CommunicationContact communicationContact) {
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
    public void selectAction() {
        super.selectAction();
        //
    }

    @Override
    protected boolean deleteRow(CommunicationContact rowObject) {
        if (rowObject != null) {
            deleteCommunicationContact(rowObject);
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (bindingGroup != null) {
            bindingGroup.unbind();
            bindingGroup = null;
        }

        initData();

        return t;
    }

    @Override
    protected CommunicationContact modifyRow(CommunicationContact rowObject) {
        if (rowObject != null) {
            return editRow((CommunicationContact) rowObject);
        }

        return null;
    }

    @Override
    protected CommunicationContact newRow() {
        if (canNestedOperationProceed()) {
            CommunicationContact communicationContact =
                    getFormSession().newCommunicationContact(getAddress(), getCommunicationType(), getContactPerson());
            return editRow(communicationContact);
        }

        return null;
    }

    protected CommunicationContact editRow(CommunicationContact communicationContact) {
        CommunicationContactPanel panel = new CommunicationContactPanel(communicationContact);
        DialogResponse response = panel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (CommunicationContact) panel.getSelectedValue();
        }

        return null;
    }
}
