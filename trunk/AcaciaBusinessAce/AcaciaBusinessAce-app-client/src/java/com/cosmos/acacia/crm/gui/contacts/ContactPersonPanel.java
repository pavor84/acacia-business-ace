/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class ContactPersonPanel extends EntityPanel<ContactPerson> {

    public ContactPersonPanel(AbstractEntityListPanel entityListPanel, ContactPerson entity) {
        super(entityListPanel, entity, null);
    }
}
