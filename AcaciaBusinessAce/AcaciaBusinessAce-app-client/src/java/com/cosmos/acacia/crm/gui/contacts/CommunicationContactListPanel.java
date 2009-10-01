/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class CommunicationContactListPanel extends DetailEntityListPanel<Address, CommunicationContact> {

    public CommunicationContactListPanel(EntityPanel<Address> mainEntityPanel) {
        super(mainEntityPanel, CommunicationContact.class, null);
    }

    public CommunicationContactListPanel(Address mainEntity) {
        super(mainEntity, CommunicationContact.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(CommunicationContact entity) {
        CommunicationContactPanel entityPanel = new CommunicationContactPanel(this, entity);
        return entityPanel;
    }
}
