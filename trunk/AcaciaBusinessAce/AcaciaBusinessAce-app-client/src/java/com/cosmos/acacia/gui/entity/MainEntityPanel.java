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
public class MainEntityPanel<E extends DataObjectBean> {

    private E entity;
    private EntityService entityService;

    public MainEntityPanel(E entity) {
        this.entity = entity;
    }

    protected E getEntity() {
        return entity;
    }

    protected EntityService getEntityService() {
        return entityService;
    }

}
