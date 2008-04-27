/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.contactbook.impl.AddressesListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class AddressListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public AddressListPanel(DataObject parentDataObject)
    {
        super(parentDataObject);
    }

    @EJB
    private AddressesListRemote formSession;

    private BindingGroup addressesBindingGroup;
    private List<Address> addresses;

    @Override
    protected void initData() {

        super.initData();

        EntityProperties entityProps = getAddressEntityProperties();

        refreshDataTable(entityProps);

        setVisible(Button.Select, false);
    }

    protected void refreshDataTable(EntityProperties entityProps)
    {
        if ( addressesBindingGroup!=null )
            addressesBindingGroup.unbind();

        addressesBindingGroup = new BindingGroup();
        AcaciaTable addressesTable = getDataTable();
        addressesTable.bind(addressesBindingGroup, getAddresses(), entityProps);

        addressesBindingGroup.bind();
        addressesTable.setEditable(false);
    }
    protected List<Address> getAddresses()
    {
        if(addresses == null)
        {
            addresses = getFormSession().getAddresses(getParentDataObject());
        }

        return addresses;
    }

    protected List<City> getCities()
    {
        return getFormSession().getCities();
    }

    protected EntityProperties getAddressEntityProperties()
    {
        return getFormSession().getAddressEntityProperties();
    }

    protected AddressesListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(AddressesListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deleteAddress(Address addresses)
    {
        return getFormSession().deleteAddress(addresses);
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
         if(rowObject != null)
        {
            deleteAddress((Address) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {

        if(rowObject != null)
        {
            AddressPanel addressePanel = new AddressPanel((Address)rowObject);
            DialogResponse response = addressePanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return addressePanel.getSelectedValue();
            }
        }
        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed())
        {
            log.info(getParentDataObject());
            AddressPanel addressPanel = new AddressPanel(getParentDataObject());
            DialogResponse response = addressPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return addressPanel.getSelectedValue();
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