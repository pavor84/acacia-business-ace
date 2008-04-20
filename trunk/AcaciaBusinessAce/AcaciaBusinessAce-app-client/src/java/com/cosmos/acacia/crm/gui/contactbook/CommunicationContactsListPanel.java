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
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import org.jdesktop.application.Action;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class CommunicationContactsListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public CommunicationContactsListPanel(DataObject parentDataObject)
    {
        super(parentDataObject);
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
            communicationContacts = getFormSession().getCommunicationContacts(getParentDataObject());
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
        CommunicationContactPanel communicationContactPanel = new CommunicationContactPanel(getParentDataObject(),
                    getContactPerson());
        
        DialogResponse response = communicationContactPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return communicationContactPanel.getSelectedValue();
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