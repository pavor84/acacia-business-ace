/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.TeamMember;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class TeamMemberPanel extends EntityPanel<TeamMember> {

    public TeamMemberPanel(AbstractEntityListPanel entityListPanel, TeamMember entity) {
        super(entityListPanel, entity, null);
    }
}
