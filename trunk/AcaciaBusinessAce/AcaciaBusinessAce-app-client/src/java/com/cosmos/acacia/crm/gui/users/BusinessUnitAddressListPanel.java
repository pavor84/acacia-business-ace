/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.BusinessUnitAddress;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class BusinessUnitAddressListPanel extends DetailEntityListPanel<BusinessUnit, BusinessUnitAddress> {

    public BusinessUnitAddressListPanel(EntityPanel<BusinessUnit> mainEntityPanel) {
        super(mainEntityPanel, BusinessUnitAddress.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(BusinessUnitAddress entity) {
        return new BusinessUnitAddressPanel(this, entity);
    }
}
