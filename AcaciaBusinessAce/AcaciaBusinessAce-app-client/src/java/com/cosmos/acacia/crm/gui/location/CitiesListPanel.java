/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.location;

import com.cosmos.acacia.crm.data.location.City;
import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class CitiesListPanel extends DetailEntityListPanel<Country, City> {

    public CitiesListPanel(EntityPanel<Country> mainEntityPanel) {
        super(mainEntityPanel, City.class);
    }

    public CitiesListPanel(Country country) {
        super(country, City.class);
    }

    @Override
    protected EntityPanel getEntityPanel(City entity) {
        return new CityPanel(this, entity);
    }

    public Country getCountry() {
        return getMainEntity();
    }

    public void setCountry(Country country) {
        setMainEntity(country);
    }
}
