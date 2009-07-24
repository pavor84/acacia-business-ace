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

    public DetailEntityListPanel(EntityPanel<E> mainEntityPanel, Class<I> itemEntityClass, Object... parameters) {
        super(mainEntityPanel, itemEntityClass, parameters);
    }

    public E getMainEntity() {
        return (E) getMainEntityPanel().getEntity();
    }

    protected EntityService getMainEntityService() {
        return getMainEntityPanel().getEntityService();
    }

    @Override
    public List<I> getEntities() {
        E mainEntity;
        if ((mainEntity = getMainEntity()) != null && mainEntity.getId() != null) {
            return getEntityService().getEntityItems(mainEntity, getEntityClass());
        }

        return new ArrayList<I>();
    }

    protected E saveMainEntity() {
        getMainEntityPanel().performSave(false);
        return getMainEntity();
    }

    @Override
    protected I newEntity() {
        E mainEntity;
        if((mainEntity = getMainEntity()) != null && mainEntity.getId() == null) {
            mainEntity = saveMainEntity();
        }

        return (I) getEntityService().newItem(mainEntity, getEntityClass());
    }

    @Override
    public boolean isDetailEntity() {
        return true;
    }

}
