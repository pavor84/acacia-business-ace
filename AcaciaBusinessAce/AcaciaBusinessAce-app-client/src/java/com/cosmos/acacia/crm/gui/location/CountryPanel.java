/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.location;

import com.cosmos.acacia.crm.data.contacts.Country;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class CountryPanel extends EntityPanel<Country> {

    public CountryPanel(CountriesListPanel entityListPanel, Country entity) {
        super(entityListPanel, entity);
    }
}
