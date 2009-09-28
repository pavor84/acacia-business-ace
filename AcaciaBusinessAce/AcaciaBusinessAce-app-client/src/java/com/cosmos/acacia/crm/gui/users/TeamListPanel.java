/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class TeamListPanel extends EntityListPanel<Team> {

    public TeamListPanel() {
        super(Team.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(Team entity) {
        return new TeamPanel(this, entity);
    }
}
