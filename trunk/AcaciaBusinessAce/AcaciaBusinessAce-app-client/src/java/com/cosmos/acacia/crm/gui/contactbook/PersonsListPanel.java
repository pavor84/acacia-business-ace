/*
 * PersonsListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.impl.PersonsListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Panel for listing existing persons, giving CRUD options
 *
 * @author  Bozhidar Bozhanov
 */
public class PersonsListPanel extends AbstractTablePanel {
    
    /** Creates new form PersonsListPanel */
    public PersonsListPanel(DataObject parentDataObject)
    {
        super(parentDataObject);
    }

    @EJB
    private PersonsListRemote formSession;

    private BindingGroup personsBindingGroup;
    private List<Person> persons;

    @Override
    protected void initData() {
        super.initData();
        setVisible(Button.Select, false);
        personsBindingGroup = new BindingGroup();
        AcaciaTable personsTable = getDataTable();
        JTableBinding tableBinding = personsTable.bind(personsBindingGroup, getPersons(), getPersonEntityProperties());

        personsBindingGroup.bind();

        personsTable.setEditable(false);
    }

    protected List<Person> getPersons()
    {
        if(persons == null)
        {
            persons = getFormSession().getPersons(getParentDataObject());
        }

        return persons;
    }

    protected EntityProperties getPersonEntityProperties()
    {
        return getFormSession().getPersonEntityProperties();
    }

    protected PersonsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = getBean(PersonsListRemote.class);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deletePerson(Person person)
    {
        return getFormSession().deletePerson(person);
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
         if(rowObject != null)
        {
            deletePerson((Person) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            PersonPanel personPanel = new PersonPanel((Person)rowObject);
            DialogResponse response = personPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return personPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        PersonPanel personPanel = new PersonPanel(getParentDataObject());
        DialogResponse response = personPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return personPanel.getSelectedValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
        
        if (personsBindingGroup != null)
            personsBindingGroup.unbind();
        
        persons = null;
        
        initData();
        
        return t;
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