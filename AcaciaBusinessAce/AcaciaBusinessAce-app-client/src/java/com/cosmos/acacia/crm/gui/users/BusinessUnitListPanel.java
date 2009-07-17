/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class BusinessUnitListPanel extends EntityListPanel<BusinessUnit> {

    public BusinessUnitListPanel() {
        super(BusinessUnit.class);
    }

    @Override
    protected EntityPanel getEntityPanel(BusinessUnit entity) {
        return new BusinessUnitPanel(this, entity);
    }
}
