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

import com.cosmos.acacia.crm.bl.contactbook.impl.AddressesListRemote;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class ContactPersonsListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public ContactPersonsListPanel(DataObject parentDataObject)
    {
        super(parentDataObject);
    }

    @EJB
    private AddressesListRemote formSession;

    private BindingGroup contactPersonsBindingGroup;
    private List<ContactPerson> contactPersons;

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

    protected List<ContactPerson> getContactPersons()
    {
        if(contactPersons == null)
        {
            contactPersons = getFormSession().getContactPersons(getParentDataObject());
        }

        return contactPersons;
    }

    protected EntityProperties getContactPersonEntityProperties()
    {
        return getFormSession().getContactPersonEntityProperties();
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

    protected int deleteContactPerson(ContactPerson contactPerson)
    {
        return getFormSession().deleteContactPerson(contactPerson);
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
            deleteContactPerson((ContactPerson) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            ContactPersonPanel contactPersonPanel = new ContactPersonPanel((ContactPerson)rowObject);
            DialogResponse response = contactPersonPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return contactPersonPanel.getSelectedValue();
            }
        }
         
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
        
        if (contactPersonsBindingGroup != null)
            contactPersonsBindingGroup.unbind();
        
        contactPersons = null;
        
        initData();
        
        return t;
    }
        
    @Override
    protected Object newRow() {
        if (canNestedOperationProceed())
        {
            ContactPersonPanel contactPersonPanel = new ContactPersonPanel(getParentDataObject());

            DialogResponse response = contactPersonPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
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

}