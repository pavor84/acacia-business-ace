/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.gui.entity.AlterationType;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class JobTitleListPanel extends DetailEntityListPanel<BusinessUnit, JobTitle> {

    public JobTitleListPanel(EntityPanel<BusinessUnit> mainEntityPanel, Class<JobTitle> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    @Override
    protected EntityPanel getEntityPanel(JobTitle entity) {
        return new JobTitlePanel(this, entity);
    }

    @Override
    public void rowChanged(AlterationType alterationType, JobTitle oldRowObject, JobTitle newRowObject) {
        if(AlterationType.Nothing.equals(alterationType)) {
            return;
        }
    }

}
