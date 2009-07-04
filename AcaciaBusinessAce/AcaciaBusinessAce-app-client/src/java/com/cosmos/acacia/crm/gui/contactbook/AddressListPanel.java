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
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.City;
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
public class AddressListPanel extends AbstractTablePanel<Address> {

    public AddressListPanel(BusinessPartner businessPartner) {
        this(businessPartner != null ? businessPartner.getId() : null);
        this.businessPartner = businessPartner;
    }

    /** Creates new form AddresssListPanel */
    public AddressListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }
    //
    @EJB
    private AddressesListRemote formSession;
    private BindingGroup addressesBindingGroup;
    private List<Address> addresses;
    /** Indicates whether the addresses are internal to the organization */
    private boolean isInternal;
    //
    private BusinessPartner businessPartner;

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
        if(businessPartner != null) {
            setParentDataObjectId(businessPartner.getId());
        } else {
            setParentDataObjectId(null);
        }
        refreshAction();
    }

    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    @Override
    protected void initData() {

        setTitle(getResourceMap().getString("branches.title"));

        super.initData();

        EntityProperties entityProps = getAddressEntityProperties();

        refreshDataTable(entityProps);

        setVisible(Button.Select, false);
    }

    protected void refreshDataTable(EntityProperties entityProps) {
        if (addressesBindingGroup != null) {
            addressesBindingGroup.unbind();
        }

        addressesBindingGroup = new BindingGroup();
        AcaciaTable addressesTable = getDataTable();
        addressesTable.bind(addressesBindingGroup, getAddresses(), entityProps);

        addressesBindingGroup.bind();
        addressesTable.setEditable(false);
    }

    protected List<Address> getAddresses() {
        if (addresses == null) {
            addresses = getFormSession().getAddresses(getParentDataObjectId());
        }

        return addresses;
    }

    protected List<City> getCities() {
        return getFormSession().getCities();
    }

    protected EntityProperties getAddressEntityProperties() {
        return getFormSession().getAddressEntityProperties();
    }

    protected AddressesListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(AddressesListRemote.class);
        }

        return formSession;
    }

    protected int deleteAddress(Address addresses) {
        return getFormSession().deleteAddress(addresses);
    }

    @Override
    protected boolean deleteRow(Address rowObject) {
        if (rowObject != null) {
            deleteAddress(rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Address modifyRow(Address rowObject) {

        if (rowObject != null) {
            AddressPanel addressPanel = new AddressPanel((Address) rowObject);
            addressPanel.setInternal(isInternal);
            DialogResponse response = addressPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (Address) addressPanel.getSelectedValue();
            }
        }
        return null;
    }

    @Override
    protected Address newRow() {
        if (canNestedOperationProceed()) {
            log.info(getParentDataObjectId());
            AddressPanel addressPanel = new AddressPanel(getParentDataObjectId());
            addressPanel.setInternal(isInternal);
            DialogResponse response = addressPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (Address) addressPanel.getSelectedValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (addressesBindingGroup != null) {
            addressesBindingGroup.unbind();
        }

        addresses = null;

        initData();

        return t;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Address rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Address rowObject) {
        return true;
    }

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }
}