/*
 * PersonsListPanel.java
 *
 */
package com.cosmos.acacia.crm.gui.contactbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.PersonsListRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.gui.pricing.CustomerDiscountListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Panel for listing existing persons, giving CRUD options
 *
 * @author  Bozhidar Bozhanov
 */
public class PersonsListPanel extends AbstractTablePanel<Person> {

    public PersonsListPanel() {
        this(LocalSession.instance().getOrganization().getId());
    }

    /** Creates new form PersonsListPanel */
    public PersonsListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        postInitData();
        initComponentsCustom();
    }

    /** Creates new form PersonsListPanel */
    public PersonsListPanel(BigInteger parentDataObjectId, boolean staff) {
        super(parentDataObjectId);
        this.staff = staff;
        postInitData();
        initComponentsCustom();
    }

    private void initComponentsCustom() {
        setSpecialButtonBehavior(true);
        getButton(Button.Special).setText(getResourceMap().getString("button.discount"));
        getButton(Button.Special).setToolTipText(getResourceMap().getString("button.discount.tooltip"));
        getButton(Button.Special).addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                onDiscount();
            }
        });
    }

    protected void onDiscount() {
        BusinessPartner selected = (BusinessPartner) getDataTable().getSelectedRowObject();
        if (selected == null) {
            return;
        }
        CustomerDiscountListPanel customerDiscountForm = new CustomerDiscountListPanel(selected);
        customerDiscountForm.showFrame(this);
    }
    @EJB
    private PersonsListRemote formSession;
    private BindingGroup personsBindingGroup;
    private List<Person> persons;
    private boolean staff = false;

    @Override
    protected void initData() {
        super.initData();
    }

    protected void postInitData() {
        if (staff) {
            setTitle(getResourceMap().getString("Form.altTitle"));
        }

        personsBindingGroup = new BindingGroup();
        AcaciaTable personsTable = getDataTable();
        JTableBinding tableBinding = personsTable.bind(personsBindingGroup, getPersons(), getPersonEntityProperties());

        personsBindingGroup.bind();

        personsTable.setEditable(false);
    }

    protected List<Person> getPersons() {
        if (persons == null) {
            if (!staff) {
                persons = getFormSession().getPersons(getParentDataObjectId());
            } else {
                persons = getFormSession().getStaff();
            }
        }

        return persons;
    }

    protected EntityProperties getPersonEntityProperties() {
        return getFormSession().getPersonEntityProperties();
    }

    protected PersonsListRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = getBean(PersonsListRemote.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deletePerson(Person person) {
        return getFormSession().deletePerson(person);
    }

    @Override
    protected boolean deleteRow(Person rowObject) {
        if (rowObject != null) {
            deletePerson(rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Person modifyRow(Person rowObject) {
        if (rowObject != null) {
            PersonPanel personPanel = new PersonPanel((Person) rowObject);
            DialogResponse response = personPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (Person) personPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Person newRow() {
        PersonPanel personPanel = new PersonPanel(getParentDataObjectId());
        DialogResponse response = personPanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (Person) personPanel.getSelectedValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (personsBindingGroup != null) {
            personsBindingGroup.unbind();
        }

        persons = null;

        initData();

        return t;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Person rowObject) {
        if (rowObject instanceof DataObjectBean) {
            DataObjectBean bean = (DataObjectBean) rowObject;
            if (getClassifiersManager().isClassifiedAs(bean, "customer")) {
                setVisible(Button.Special, true);
            } else {
                setVisible(Button.Special, false);
            }
        }

        return true;
    }

    @Override
    public boolean canDelete(Person rowObject) {
        return true;
    }
}