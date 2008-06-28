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

import com.cosmos.acacia.crm.bl.contactbook.LocationsListRemote;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Country;
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
public class CitiesListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public CitiesListPanel()
    {
        super();
        postInitData();
    }

    public CitiesListPanel(Country country)
    {
        super();
        this.country = country;
        postInitData();
    }

    @EJB
    private LocationsListRemote formSession;

    private Country country;
    private BindingGroup citiesBindingGroup;
    private List<City> cities;
    private ContactPerson contactPerson;

    @Override
    protected void initData() {

        super.initData();
        setVisible(Button.Select, false);
    }

    protected void postInitData() {
        citiesBindingGroup = new BindingGroup();
        AcaciaTable citiesTable = getDataTable();
        JTableBinding tableBinding = citiesTable.bind(citiesBindingGroup, getCities(), getCityEntityProperties());

        citiesBindingGroup.bind();

        citiesTable.setEditable(false);
    }

    protected List<City> getCities() {
        if(cities == null) {
            if (country == null) {
                cities = getFormSession().getCities();
            } else {
                cities = getFormSession().getCities(country);
            }
        }

        return cities;
    }

    protected EntityProperties getCityEntityProperties()
    {
        return getFormSession().getCityEntityProperties();
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

    protected int deleteCity(City city)
    {
        return getFormSession().deleteCity(city);
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
            deleteCity((City) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            CityPanel cityPanel =
                    new CityPanel((City) rowObject);
            DialogResponse response = cityPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return cityPanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (citiesBindingGroup != null)
            citiesBindingGroup.unbind();

        cities = null;

        postInitData();

        return t;
    }

    @Override
    protected Object newRow() {
        CityPanel cityPanel = null;
        if (country != null)
            cityPanel = new CityPanel(country);
        else
            cityPanel = new CityPanel();

        DialogResponse response = cityPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return cityPanel.getSelectedValue();
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