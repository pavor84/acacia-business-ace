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

import com.cosmos.acacia.crm.bl.contactbook.impl.LocationsListRemote;
import com.cosmos.acacia.crm.data.City;
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
public class CitiesListPanel extends AbstractTablePanel {

    /** Creates new form AddresssListPanel */
    public CitiesListPanel()
    {
        super(null);
    }

    @EJB
    private LocationsListRemote formSession;

    private BindingGroup citiesBindingGroup;
    private List<City> cities;
    private ContactPerson contactPerson;

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        citiesBindingGroup = new BindingGroup();
        AcaciaTable citiesTable = getDataTable();
        JTableBinding tableBinding = citiesTable.bind(citiesBindingGroup, getCities(), getCityEntityProperties());

        citiesBindingGroup.bind();

        citiesTable.setEditable(true);
    }

    protected List<City> getCities()
    {
        if(cities == null)
        {
            cities = getFormSession().getCities();
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

    @Override
    protected Object newRow() {
        CityPanel cityPanel = new CityPanel();
        
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