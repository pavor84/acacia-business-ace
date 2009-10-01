/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Miro
 */
public class PersonListPanel extends EntityListPanel<Person> {

    public PersonListPanel() {
        this((List<Classifier>) null);
    }

    public PersonListPanel(Classifier classifier) {
        this(Arrays.asList(classifier));
    }

    public PersonListPanel(List<Classifier> classifiers) {
        super(Person.class, classifiers);
    }

    @Override
    protected EntityPanel getEntityPanel(Person entity) {
        return new PersonPanel(this, entity, getClassifiers());
    }
}
