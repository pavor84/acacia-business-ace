/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.TeamMember;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class TeamMemberListPanel extends DetailEntityListPanel<Team, TeamMember> {

    public TeamMemberListPanel(EntityPanel<Team> mainEntityPanel) {
        super(mainEntityPanel, TeamMember.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(TeamMember entity) {
        return new TeamMemberPanel(this, entity);
    }
}
