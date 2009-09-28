/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.util.PersistentEntity;
import java.util.List;

/**
 *
 * @author Miro
 */
public class EntityListPanel<E extends PersistentEntity> extends AbstractEntityListPanel<E> {

    public EntityListPanel(Class<E> entityClass, Classifier classifier, Object... parameters) {
        super(entityClass, classifier, parameters);
    }

    @Override
    public List<E> getEntities() {
        return getEntityService().getEntities(getEntityClass());
    }

    @Override
    protected E newEntity(Class<E> entityClass) {
        if(entityClass == null) {
            return null;
        }

        return (E) getEntityService().newEntity(entityClass);
    }

    public List<DbResource> getResources(Class<? extends Enum> enumClass) {
        return getEntityService().getResources(enumClass);
    }

    @Override
    public boolean isDetailEntity() {
        return false;
    }
}
