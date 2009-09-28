/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class BusinessUnitListPanel extends DetailEntityListPanel<Organization, BusinessUnit> {

    public BusinessUnitListPanel() {
        this(getAcaciaSession().getOrganization());
    }

    public BusinessUnitListPanel(Organization organization) {
        super(organization, BusinessUnit.class, null);
    }

    public BusinessUnitListPanel(EntityPanel<Organization> mainEntityPanel) {
        super(mainEntityPanel, BusinessUnit.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(BusinessUnit entity) {
        return new BusinessUnitPanel(this, entity);
    }

    @Override
    public boolean canDelete(BusinessUnit rowObject) {
        if(rowObject.isRoot()) {
            return false;
        }

        return super.canDelete(rowObject);
    }

    public Organization getOrganization() {
        return getMainEntity();
    }

    public void setOrganization(Organization organization) {
        setMainEntity(organization);
        refresh();
    }
}
