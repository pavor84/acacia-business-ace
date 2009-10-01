/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class ContactPersonListPanel extends DetailEntityListPanel<Address, ContactPerson> {

    public ContactPersonListPanel(EntityPanel<Address> mainEntityPanel) {
        super(mainEntityPanel, ContactPerson.class, null);
    }

    public ContactPersonListPanel(Address mainEntity) {
        super(mainEntity, ContactPerson.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(ContactPerson entity) {
        ContactPersonPanel entityPanel = new ContactPersonPanel(this, entity);
        return entityPanel;
    }
}
