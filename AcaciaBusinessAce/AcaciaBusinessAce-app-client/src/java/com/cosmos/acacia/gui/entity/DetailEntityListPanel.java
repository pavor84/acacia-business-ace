/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.entity.EntityService;

/**
 *
 * @author Miro
 */
public class DetailEntityListPanel<E extends DataObjectBean, I extends DataObjectBean> {

    private MainEntityPanel<E> mainEntityPanel;
    private I entity;

    public DetailEntityListPanel(MainEntityPanel<E> mainEntityPanel, I entity) {
        this.mainEntityPanel = mainEntityPanel;
        this.entity = entity;
    }

    protected E getMainEntity() {
        return mainEntityPanel.getEntity();
    }

    protected EntityService getEntityService() {
        return mainEntityPanel.getEntityService();
    }

}
