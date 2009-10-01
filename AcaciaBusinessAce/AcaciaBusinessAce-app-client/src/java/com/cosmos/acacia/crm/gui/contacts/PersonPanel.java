/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.List;

/**
 *
 * @author Miro
 */
public class PersonPanel extends EntityPanel<Person> {

    public PersonPanel(EntityListPanel entityListPanel, Person entity, List<Classifier> classifiers) {
        super(entityListPanel, entity, classifiers);
    }
}
