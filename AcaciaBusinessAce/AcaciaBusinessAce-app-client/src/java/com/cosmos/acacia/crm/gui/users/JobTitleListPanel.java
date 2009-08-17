/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.users.JobTitle;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class JobTitleListPanel extends DetailEntityListPanel<DataObjectBean, JobTitle> {

    /*public JobTitleListPanel(BusinessUnit businessUnit) {
        this(new BusinessUnitListPanel().getEntityPanel(businessUnit), JobTitle.class);
    }*/

    public JobTitleListPanel(EntityPanel<DataObjectBean> mainEntityPanel) {
        this(mainEntityPanel, JobTitle.class);
    }

    public JobTitleListPanel(EntityPanel<DataObjectBean> mainEntityPanel, Class<JobTitle> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    @Override
    protected EntityPanel getEntityPanel(JobTitle entity) {
        return new JobTitlePanel(this, entity);
    }

    @Override
    public DataObjectBean getMainEntity() {
        EntityPanel mainEntityPanel;
        if((mainEntityPanel = getMainEntityPanel()) instanceof BusinessUnitPanel) {
            return super.getMainEntity();
        } else if(mainEntityPanel instanceof UserPanel) {
            return ((UserPanel) mainEntityPanel).getEntity().getBusinessUnit();
        }

        throw new UnsupportedOperationException("Unsupported mainEntityPanel=" + mainEntityPanel);
    }
}
