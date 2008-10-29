/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.LocationsListRemote;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class CountriesListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public CountriesListPanel()
    {
        super();
    }

    @EJB
    private LocationsListRemote formSession;

    private BindingGroup countriesBindingGroup;
    private List<Country> countries;
    private ContactPerson contactPerson;

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        setVisible(Button.Classify, false);

        countriesBindingGroup = new BindingGroup();
        AcaciaTable countriesTable = getDataTable();
        JTableBinding tableBinding = countriesTable.bind(countriesBindingGroup, getCountries(), getCountryEntityProperties());

        countriesBindingGroup.bind();

        countriesTable.setEditable(false);
    }

    protected List<Country> getCountries()
    {
        if(countries == null)
        {
            countries = getFormSession().getCountries();
        }

        return countries;
    }

    protected EntityProperties getCountryEntityProperties()
    {
        return getFormSession().getCountryEntityProperties();
    }

    protected LocationsListRemote getFormSession()
    {
        if(formSession == null)
            formSession = getBean(LocationsListRemote.class);

        return formSession;
    }

    protected int deleteCountry(Country country)
    {
        return getFormSession().deleteCountry(country);
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
            deleteCountry((Country) rowObject);
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (countriesBindingGroup != null)
            countriesBindingGroup.unbind();

        countries = null;

        initData();

        return t;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            CountryPanel countryPanel =
                    new CountryPanel((Country) rowObject);
            DialogResponse response = countryPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return countryPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        CountryPanel countryPanel = new CountryPanel();

        DialogResponse response = countryPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return countryPanel.getSelectedValue();
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