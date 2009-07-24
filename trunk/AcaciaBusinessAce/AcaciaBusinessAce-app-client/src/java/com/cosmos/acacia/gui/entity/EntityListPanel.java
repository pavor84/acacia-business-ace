/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import java.util.List;

/**
 *
 * @author Miro
 */
public class EntityListPanel<E extends DataObjectBean> extends AbstractEntityListPanel<E> {

    public EntityListPanel(Class<E> entityClass, Object... parameters) {
        super(entityClass, parameters);
    }

    @Override
    public List<E> getEntities() {
        return getEntityService().getEntities(getEntityClass());
    }

    @Override
    protected E newEntity() {
        return (E) getEntityService().newEntity(getEntityClass());
    }

    public List<DbResource> getResources(Class<? extends Enum> enumClass) {
        return getEntityService().getResources(enumClass);
    }

    @Override
    public boolean isDetailEntity() {
        return false;
    }
}
