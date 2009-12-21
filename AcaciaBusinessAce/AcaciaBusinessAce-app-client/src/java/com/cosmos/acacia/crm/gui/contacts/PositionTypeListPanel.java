/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.PositionType;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class PositionTypeListPanel extends DetailEntityListPanel<BusinessPartner, PositionType> {

    public PositionTypeListPanel(EntityPanel<BusinessPartner> mainEntityPanel) {
        super(mainEntityPanel, PositionType.class, null);
    }

    public PositionTypeListPanel(BusinessPartner mainEntity) {
        super(mainEntity, PositionType.class, null);
    }

    public PositionTypeListPanel() {
        this(getAcaciaSession().getOrganization());
    }

    @Override
    protected EntityPanel getEntityPanel(PositionType entity) {
        PositionTypePanel entityPanel = new PositionTypePanel(this, entity);
        return entityPanel;
    }
}
