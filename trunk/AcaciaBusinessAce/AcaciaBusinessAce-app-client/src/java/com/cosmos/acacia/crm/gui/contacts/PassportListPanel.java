/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.Passport;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class PassportListPanel extends DetailEntityListPanel<Person, Passport> {

    public PassportListPanel(EntityPanel<Person> mainEntityPanel) {
        super(mainEntityPanel, Passport.class, null);
    }

    public PassportListPanel(Person mainEntity) {
        super(mainEntity, Passport.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(Passport entity) {
        PassportPanel entityPanel = new PassportPanel(this, entity);
        return entityPanel;
    }
}
