/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class TeamPanel extends EntityPanel<Team> {


    public TeamPanel(AbstractEntityListPanel entityListPanel, Team entity) {
        super(entityListPanel, entity, null);
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
//        if(SUPPLIER_PROPERTY_NAME.equals(propertyName)) {
//            supplierPropertyChanged();
//        } else if(DOCUMENT_CURRENCY_PROPERTY_NAME.equals(propertyName)) {
//            documentCurrencyPropertyChanged(propertyName, jComponent, event);
//        }
    }
}
