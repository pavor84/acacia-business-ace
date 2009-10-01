/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class CommunicationContactPanel extends EntityPanel<CommunicationContact> {

    public CommunicationContactPanel(AbstractEntityListPanel entityListPanel, CommunicationContact entity) {
        super(entityListPanel, entity, null);
    }
}
