/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.TeamMember;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class TeamMemberPanel extends EntityPanel<TeamMember> {

    public TeamMemberPanel(AbstractEntityListPanel entityListPanel, TeamMember entity) {
        super(entityListPanel, entity);
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
//        if(RECEIVED_PRICE_PROPERTY_NAME.equals(propertyName)) {
//            receivedPricePropertyChanged();
//        } else if(RECEIVED_QUANTITY_PROPERTY_NAME.equals(propertyName)) {
//            receivedQuantityPropertyChanged();
//        } else if(PRODUCT_PROPERTY_NAME.equals(propertyName)) {
//            productPropertyChanged();
//        }
    }

}
