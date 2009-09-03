/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;

import javax.swing.JComponent;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.LocationsListRemote;
import com.cosmos.acacia.crm.data.contacts.City;
import com.cosmos.acacia.crm.data.contacts.Country;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class CitiesListPanel extends AbstractTablePanel<City> {

    /** Creates new form AddresssListPanel */
    public CitiesListPanel() {
        super();
        postInitData();
    }

    public CitiesListPanel(Country country) {
        super();
        this.country = country;
        postInitData();
    }

    @EJB
    private LocationsListRemote formSession;

    private JBPanel countryPanel;
    private CountriesListPanel countriesListPanel;
    private AcaciaComboList countryComboList;
    private Country country;
    private City countryCity;
    private BindingGroup countryBindingGroup;

    private BindingGroup citiesBindingGroup;

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

    @Override
    protected JComponent getTopComponent() {
        if(countryPanel == null) {
            countryPanel = new JBPanel();
            countryPanel.setLayout(new BorderLayout());
            countryComboList = new AcaciaComboList();
            countryComboList.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            countryPanel.add(countryComboList, BorderLayout.CENTER);
            JBLabel countryLabel = new JBLabel();
            countryLabel.setName("country");
            countryLabel.setText(getResourceMap().getString("countryLabel.text"));
            countryLabel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            countryPanel.add(countryLabel, BorderLayout.WEST);

            countryCity = new City();
            countryBindingGroup = new BindingGroup();
            PropertyDetails propDetails = new PropertyDetails(
                    "country", "Country", Country.class.getName());
            propDetails.setColumnName("country");

            if(countriesListPanel == null)
                countriesListPanel = new CountriesListPanel();
            countryComboList.bind(countryBindingGroup, countriesListPanel, countryCity,
                    propDetails, "${countryName}", UpdateStrategy.READ_WRITE);
            countryComboList.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    onCountryChanged((Country)e.getItem());
                }
            }, true);

            countryBindingGroup.bind();
        }

        return countryPanel;
    }

    protected void onCountryChanged(Country country) {
        setCountry(country);
    }

    protected void refreshCitiesTable() {
        AcaciaTable citiesTable = getDataTable();
        List data = citiesTable.getData();
        if(data != null) {
            data.clear();
            data.addAll(getCities());
        }
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
        setEnabled(Button.New, country != null);
        refreshCitiesTable();
    }

    protected List<City> getCities() {
        return getFormSession().getCities(country);
    }

    protected EntityProperties getCityEntityProperties()
    {
        return getFormSession().getCityEntityProperties();
    }

    protected LocationsListRemote getFormSession()
    {
        if(formSession == null)
            formSession = getBean(LocationsListRemote.class);

        return formSession;
    }

    protected int deleteCity(City city)
    {
        return getFormSession().deleteCity(city);
    }

    @Override
    @Action
    public void selectAction(){
        super.selectAction();
    }

    @Override
    protected boolean deleteRow(City rowObject) {
        if (rowObject != null) {
            deleteCity(rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected City modifyRow(City rowObject) {
        if(rowObject != null) {
            CityPanel cityPanel = new CityPanel(rowObject);
            DialogResponse response = cityPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response)) {
                return (City) cityPanel.getSelectedValue();
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

        refreshCitiesTable();

        return t;
    }

    @Override
    protected City newRow() {
        CityPanel cityPanel = null;
        if (country != null) {
            cityPanel = new CityPanel(country);
        } else {
            cityPanel = new CityPanel();
        }

        DialogResponse response = cityPanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (City) cityPanel.getSelectedValue();
        }
        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(City rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(City rowObject) {
        return true;
    }
}