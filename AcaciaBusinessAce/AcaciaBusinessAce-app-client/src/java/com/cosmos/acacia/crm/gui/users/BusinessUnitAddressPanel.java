/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.BusinessUnitAddress;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.List;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class BusinessUnitAddressPanel extends EntityPanel<BusinessUnitAddress> {

    public BusinessUnitAddressPanel(AbstractEntityListPanel entityListPanel, BusinessUnitAddress entity) {
        super(entityListPanel, entity);
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
    }

    @Override
    protected List getResources(Class<? extends Enum> enumClass, Class<? extends Enum>... enumCategoryClasses) {
        return super.getResources(enumClass, enumCategoryClasses);
    }
}
