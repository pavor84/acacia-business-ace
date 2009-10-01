/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class AddressListPanel extends DetailEntityListPanel<BusinessPartner, Address> {

    public AddressListPanel(EntityPanel<BusinessPartner> mainEntityPanel) {
        super(mainEntityPanel, Address.class, null);
    }

    public AddressListPanel(BusinessPartner mainEntity) {
        super(mainEntity, Address.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(Address entity) {
        AddressPanel entityPanel = new AddressPanel(this, entity);
        return entityPanel;
    }
}
