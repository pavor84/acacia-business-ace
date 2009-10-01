/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class JobTitlePanel extends EntityPanel<JobTitle> {

    public JobTitlePanel(AbstractEntityListPanel entityListPanel, JobTitle entity) {
        super(entityListPanel, entity, null);
    }
}
