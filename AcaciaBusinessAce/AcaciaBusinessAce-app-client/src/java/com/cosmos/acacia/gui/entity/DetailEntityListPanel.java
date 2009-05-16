/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.entity.EntityService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miro
 */
public class DetailEntityListPanel<E extends DataObjectBean, I extends DataObjectBean>
        extends AbstractEntityListPanel<I> {

    public DetailEntityListPanel(EntityPanel<E> mainEntityPanel, Class<I> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    protected E getMainEntity() {
        return (E) getMainEntityPanel().getEntity();
    }

    protected EntityService getMainEntityService() {
        return getMainEntityPanel().getEntityService();
    }

    @Override
    protected List<E> getEntities() {
        E mainEntity;
        if ((mainEntity = getMainEntity()) != null && mainEntity.getId() != null) {
            return getEntityService().getEntityItems(mainEntity, getEntityClass());
        }

        return new ArrayList<E>();
    }

    @Override
    protected I newEntity() {
        E mainEntity;
        if((mainEntity = getMainEntity()) != null && mainEntity.getId() == null) {
            try {
                mainEntity = getEntityService().save(mainEntity);
            } catch(Exception ex) {
                handleException("Save mainEntity: " + mainEntity, ex);
            }
        }

        return (I) getEntityService().newItem(mainEntity, getEntityClass());
    }

    @Override
    public boolean isDetailEntity() {
        return true;
    }

}