/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class OrganizationPanel extends EntityPanel<Organization> {

    public OrganizationPanel(OrganizationListPanel entityListPanel, Organization entity) {
        super(entityListPanel, entity);
    }
}
