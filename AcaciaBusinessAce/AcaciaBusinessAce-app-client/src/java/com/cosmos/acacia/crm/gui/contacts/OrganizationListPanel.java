/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Miro
 */
public class OrganizationListPanel extends EntityListPanel<Organization> {

    public OrganizationListPanel() {
        this((List<Classifier>) null);
    }

    public OrganizationListPanel(Classifier classifier) {
        this(Arrays.asList(classifier));
    }

    public OrganizationListPanel(List<Classifier> classifiers) {
        super(Organization.class, classifiers);
    }

    @Override
    protected EntityPanel getEntityPanel(Organization entity) {
        return new OrganizationPanel(this, entity, getClassifiers());
    }
}
