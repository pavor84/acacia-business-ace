/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class BusinessUnitPanel extends EntityPanel<BusinessUnit> {


    public BusinessUnitPanel(AbstractEntityListPanel entityListPanel, BusinessUnit entity) {
        super(entityListPanel, entity);
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
    }
}
