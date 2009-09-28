/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.util.PersistentEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miro
 */
public class DetailEntityListPanel<E extends PersistentEntity, I extends PersistentEntity>
        extends AbstractEntityListPanel<I> {

    public DetailEntityListPanel(
            EntityPanel<E> mainEntityPanel,
            Class<I> itemEntityClass,
            Classifier classifier,
            Object... parameters) {
        super(mainEntityPanel, null, itemEntityClass, classifier, parameters);
    }

    public DetailEntityListPanel(E mainEntity, Class<I> itemEntityClass, Classifier classifier, Object... parameters) {
        super(null, mainEntity, itemEntityClass, classifier, parameters);
    }

    @Override
    public List<I> getEntities() {
        E mainEntity;
        if ((mainEntity = getMainEntity()) != null && mainEntity.getId() != null) {
            return getEntityService().getEntityItems(mainEntity, getEntityClass());
        }

        return new ArrayList<I>();
    }

    @Override
    protected E saveMainEntity() {
        return (E) super.getMainEntity();
    }

    @Override
    protected E getMainEntity() {
        return (E) super.getMainEntity();
    }

    @Override
    protected I newEntity(Class<I> entityClass) {
        if(entityClass == null) {
            return null;
        }

        E mainEntity;
        if((mainEntity = getMainEntity()) != null && mainEntity.getId() == null) {
            mainEntity = saveMainEntity();
        }

        return (I) getEntityService().newItem(mainEntity, entityClass);
    }

    @Override
    public boolean isDetailEntity() {
        return true;
    }
}
