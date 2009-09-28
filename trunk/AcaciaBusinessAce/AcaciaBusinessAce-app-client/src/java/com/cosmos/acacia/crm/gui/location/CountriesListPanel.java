/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.location;

import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class CountriesListPanel extends EntityListPanel<Country> {

    public CountriesListPanel() {
        super(Country.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(Country entity) {
        return new CountryPanel(this, entity);
    }
}
