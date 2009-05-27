/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.contactbook;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class ContactPersonsListPanel extends AbstractTablePanel {

    public ContactPersonsListPanel(Address address) {
        this(address != null ? address.getId() : (BigInteger)null);
        this.address = address;
    }


    /** Creates new form AddresssListPanel */
    public ContactPersonsListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }
    //
    @EJB
    private AddressesListRemote formSession;
    private BindingGroup contactPersonsBindingGroup;
    private List<ContactPerson> contactPersons;
    private Address address;
    /** Indicates whether the addresses are internal to the organization */
    private boolean isInternal;

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        contactPersonsBindingGroup = new BindingGroup();
        AcaciaTable contactPersonsTable = getDataTable();
        JTableBinding tableBinding = contactPersonsTable.bind(contactPersonsBindingGroup, getContactPersons(), getContactPersonEntityProperties());

        contactPersonsBindingGroup.bind();

        contactPersonsTable.setEditable(false);
    }

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

    protected List<ContactPerson> getContactPersons() {
        if (contactPersons == null) {
            contactPersons = getFormSession().getContactPersons(getParentDataObjectId());
        }

        return contactPersons;
    }

    protected EntityProperties getContactPersonEntityProperties() {
        return getFormSession().getContactPersonEntityProperties();
    }

    protected AddressesListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(AddressesListRemote.class);
        }

        return formSession;
    }

    protected int deleteContactPerson(ContactPerson contactPerson) {
        return getFormSession().deleteContactPerson(contactPerson);
    }

    @Override
    @Action
    public void selectAction() {
        super.selectAction();
        //
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        if (rowObject != null) {
            deleteContactPerson((ContactPerson) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if (rowObject != null) {
            ContactPersonPanel contactPersonPanel = new ContactPersonPanel((ContactPerson) rowObject);
            contactPersonPanel.setInternal(isInternal);
            DialogResponse response = contactPersonPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return contactPersonPanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (contactPersonsBindingGroup != null) {
            contactPersonsBindingGroup.unbind();
        }

        contactPersons = null;

        initData();

        return t;
    }

    @Override
    protected Object newRow() {
        log.info("Internal : " + isInternal);
        if (canNestedOperationProceed()) {
            ContactPersonPanel contactPersonPanel = new ContactPersonPanel(getParentDataObjectId());
            contactPersonPanel.setInternal(isInternal);

            DialogResponse response = contactPersonPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return contactPersonPanel.getSelectedValue();
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

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }
}