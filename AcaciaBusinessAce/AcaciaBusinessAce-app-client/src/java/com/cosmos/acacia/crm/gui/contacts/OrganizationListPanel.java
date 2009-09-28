/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.List;

/**
 *
 * @author Miro
 */
public class OrganizationListPanel extends EntityListPanel<Organization> {

    public OrganizationListPanel() {
        this(null);
    }

    public OrganizationListPanel(Classifier classifier) {
        super(Organization.class, classifier);
    }

    @Override
    protected EntityPanel getEntityPanel(Organization entity) {
        return new OrganizationPanel(this, entity);
    }

    @Override
    public List<Organization> getEntities() {
        Classifier classifier;
        Class entityClass = getEntityClass();
        if((classifier = getClassifier()) != null) {
            return getEntityService().getEntities(entityClass, classifier);
        } else {
            return getEntityService().getEntities(entityClass);
        }
    }
}
