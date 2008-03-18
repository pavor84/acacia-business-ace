/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.LocationsListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.settings.GeneralSettings;
import com.cosmos.beansbinding.EntityProperties;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Bozhidar Bozhanov
 */
class AddressListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public AddressListPanel(DataObject parentDataObject)
    {
    	super(parentDataObject);
    }

    @EJB
    private LocationsListRemote formSession;

    private BindingGroup addressesBindingGroup;
    private List<Address> addresses;

    @Override
    protected void initData() {
        super.initData();
        setVisible(Button.Select, false);
        addressesBindingGroup = new BindingGroup();
        AcaciaTable addressesTable = getDataTable();
        JTableBinding tableBinding = addressesTable.bind(addressesBindingGroup, getAddresses(), getAddressEntityProperties());
        
        addressesBindingGroup.bind();

        addressesTable.setEditable(true);
    }

    protected List<Address> getAddresses()
    {
        if(addresses == null)
        {
            addresses = getFormSession().getAddresses(getParentDataObject());
        }

        return addresses;
    }

    protected EntityProperties getAddressEntityProperties()
    {
        //return getFormSession().getAddressEntityProperties();
        return null;
    }
    
    protected LocationsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(LocationsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deleteAddress(Address addresse)
    {
        return getFormSession().deleteAddress(addresse);
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
        /*
        if(rowObject != null)
        {
            AddressPanel addressePanel = new AddressPanel((Address)rowObject);
            DialogResponse response = addressePanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return addressePanel.getSelectedValue();
            }
        }
        */
        return null;
    }

    @Override
    protected Object newRow() {
        /*
        AddressPanel addressePanel = new AddressPanel(getParentDataObject());
        DialogResponse response = addressePanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return addressePanel.getSelectedValue();
        }
        */
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