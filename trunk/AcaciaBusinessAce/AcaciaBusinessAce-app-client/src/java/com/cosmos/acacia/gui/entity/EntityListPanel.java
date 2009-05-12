/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.gui.BaseEntityPanel;
import java.util.List;

/**
 *
 * @author Miro
 */
public class EntityListPanel<E extends DataObjectBean> extends AbstractEntityListPanel<E> {

    public EntityListPanel(Class<E> entityClass) {
        super(entityClass);
    }

    @Override
    protected List<E> getEntities() {
        return getEntityService().getEntities(getEntityClass());
    }

    @Override
    protected E newEntity() {
        return (E) getEntityService().newEntity(getEntityClass());
    }

    @Override
    public boolean isDetailEntity() {
        return false;
    }
}
