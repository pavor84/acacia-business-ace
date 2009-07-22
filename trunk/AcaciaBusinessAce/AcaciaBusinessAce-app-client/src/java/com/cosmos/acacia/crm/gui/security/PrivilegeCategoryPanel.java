/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.PrivilegeCategory;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class PrivilegeCategoryPanel extends EntityPanel<PrivilegeCategory> {


    public PrivilegeCategoryPanel(AbstractEntityListPanel entityListPanel, PrivilegeCategory entity) {
        super(entityListPanel, entity);
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
    }
}
